package com.picsart.bookstorelibrary.service.impl;

import com.picsart.bookstorelibrary.exception.CustomValidationException;
import com.picsart.bookstorelibrary.exception.NegativeBalanceException;
import com.picsart.bookstorelibrary.model.BookRent;
import com.picsart.bookstorelibrary.model.BorrowerCard;
import com.picsart.bookstorelibrary.model.PaperBook;
import com.picsart.bookstorelibrary.service.BookRentService;
import com.picsart.bookstorelibrary.service.BookService;
import com.picsart.bookstorelibrary.utils.DateUtils;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.picsart.bookstorelibrary.model.BookRent.EXPIRATION_DUE_DATE;
import static com.picsart.bookstorelibrary.model.BookRent.PER_DAY_FINE;
import static com.picsart.bookstorelibrary.service.impl.BookServiceImpl.isValidReturnDay;


public class BookRentServiceImpl implements BookRentService {

    private static final String RENT_BOOK_FILE_PATH = "src/com/picsart/bookstorelibrary/resources/rents.txt";
    private static BookRentServiceImpl bookRentService;
    private BookService service;
    private BorrowCardService cardService;
    private BorrowerService borrowerService;
    private FileProcessingService fileService;
    private List<BookRent> bookRents;

    public BookRentServiceImpl() {
        this.bookRents = new ArrayList<>();
        this.service = BookServiceImpl.getInstance();
        this.cardService = BorrowCardService.getInstance();
        this.borrowerService = BorrowerService.getInstance();
        this.fileService = FileProcessingService.getInstance();

    }

    public static BookRentServiceImpl getInstance() {
        if (bookRentService == null) {
            bookRentService = new BookRentServiceImpl();
        }
        return bookRentService;
    }

    public void addBookRent(BookRent bookRent, Integer borrowerID) throws IOException {
        bookRent.setRentDay(new Date());
        bookRent.setBorrowerID(borrowerID);
        bookRent.setBookRentId(calculateBookRentId());
        fileService.exportDataToFile(RENT_BOOK_FILE_PATH, bookRent.toString());
    }

    private Integer calculateBookRentId() {
        List<BookRent> rents = bookRents;
        Integer id = 0;
        if (rents.size() > 0) {
            id = rents.get(rents.size() - 1).getBookRentId();
        }
        return id + 1;
    }

    @Override
    public void checkIn(Integer bookId, String cardNum) throws CustomValidationException, IOException, ParseException {
        PaperBook pb = (PaperBook) service.getBookById(bookId);
        BorrowerCard card = cardService.getByCardNum(cardNum);

        Integer borrowerID = borrowerService.getBorrowerByCardId(card.getBorrowCardId()).getBorrowerID();
        boolean needToNotify = false;
        for (BookRent br : getRents()) {
            if (br.getBookId().equals(bookId) && br.getBorrowerID().equals(borrowerID)) {
                Date expireDate = DateUtils.addDays(br.getRentDay(), EXPIRATION_DUE_DATE);
                if (!isValidReturnDay(expireDate, new Date())) {
                    double paymentAmount = calculateFee(new Date(), expireDate, PER_DAY_FINE);
                    card.setBalance(card.getBalance() - paymentAmount);

                    needToNotify = true;
                }

                int count = pb.getNumberOfCopies();
                pb.setNumberOfCopies(++count);
                service.updateBook(pb);

                if (needToNotify) {
                    throw new CustomValidationException("returned later than expected should pay money");
                }
                break;
            }
            throw new CustomValidationException("not valid book or user");
        }
    }

    @Override
    public void checkOut(Integer bookId, String cardNumber) throws IOException, CustomValidationException, NegativeBalanceException {

        PaperBook paperBook = (PaperBook) service.getBookById(bookId);
        if (!paperBook.isSellable()) {
            int quantity = paperBook.getNumberOfCopies();
            if (quantity > 0) {
                BorrowerCard card = cardService.getByCardNum(cardNumber);
                //who has debt cannot rent book
                if (card.getBalance() >= 0.0) {
                    paperBook.setNumberOfCopies(--quantity);

                    BookRent bookRent = new BookRent();
                    bookRent.setBookId(bookId);
                    Integer userID = borrowerService.getBorrowerByCardId(card.getBorrowCardId()).getBorrowerID();
                    addBookRent(bookRent, userID);
                    service.updateBook(paperBook);
                } else {
                    throw new NegativeBalanceException("not enough balance to rent book");
                }
            } else {
                throw new CustomValidationException("is not available");
            }

        } else {
            throw new CustomValidationException("This book cannot be borrowed");
        }

    }


    public double calculateFee(Date returnDate, Date dueDate, double fine) {

        long diffInMillis = Math.abs(dueDate.getTime() - returnDate.getTime());
        long difference = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);
        difference = Math.abs(difference);
        return fine * difference;
    }

    public List<BookRent> getRents() throws IOException, ParseException {
        List<String> rentsContent = fileService.readFromFile(RENT_BOOK_FILE_PATH);
        List<BookRent> rentBooks = new ArrayList<>();
        for (String rent : rentsContent) {
            rentBooks.add(createRent(rent));
        }
        return rentBooks;
    }

    private static BookRent createRent(String data) throws ParseException {
        String[] tokens = data.split(",");
        BookRent rentBooks = new BookRent();
        rentBooks.setBookRentId(Integer.parseInt(tokens[0]));
        rentBooks.setBookId(Integer.parseInt(tokens[1]));
        rentBooks.setBorrowerID(Integer.parseInt(tokens[2]));
        rentBooks.setRentDay(DateUtils.convertStringToDate(tokens[3]));
        return rentBooks;
    }
}
