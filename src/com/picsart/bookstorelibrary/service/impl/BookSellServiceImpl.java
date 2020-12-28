package com.picsart.bookstorelibrary.service.impl;


import com.picsart.bookstorelibrary.exception.CustomValidationException;
import com.picsart.bookstorelibrary.model.BookSell;
import com.picsart.bookstorelibrary.model.PaperBook;
import com.picsart.bookstorelibrary.service.BookSellInterface;
import com.picsart.bookstorelibrary.service.BookService;
import com.picsart.bookstorelibrary.utils.DateUtils;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.picsart.bookstorelibrary.model.BookSell.DUE_DATE;
import static com.picsart.bookstorelibrary.service.impl.BookServiceImpl.isValidReturnDay;


public class BookSellServiceImpl implements BookSellInterface {

    private static final String SELL_BOOK_FILE_PATH = "src/com/picsart/bookstorelibrary/resources/sells.txt";
    private static BookSellServiceImpl sellService;
    private BookService service;
    private List<BookSell> sells;
    private FileProcessingService fileService;


    private BookSellServiceImpl() {
        this.sells = new ArrayList<>();
        this.service = BookServiceImpl.getInstance();
        this.fileService = FileProcessingService.getInstance();
    }

    public static BookSellServiceImpl getInstance() {
        if (sellService == null) {
            sellService = new BookSellServiceImpl();
        }

        return sellService;
    }

    public void addBookSell(BookSell bookSell) throws IOException {
        bookSell.setDateOfPurchase(new Date());
        bookSell.setBookSellId(calculateBookSellId());
        sells.add(bookSell);
        fileService.exportDataToFile(SELL_BOOK_FILE_PATH, bookSell.toString());

    }

    private Integer calculateBookSellId() {
        List<BookSell> bookSells = sells;
        Integer id = 0;
        if (bookSells.size() > 0) {
            id = bookSells.get(bookSells.size() - 1).getBookSellId();
        }
        return id + 1;
    }

    @Override
    public void sell(Integer bookId, Integer quantity) throws CustomValidationException, IOException {
        PaperBook paperBook = (PaperBook) service.getBookById(bookId);
        int bookCount = paperBook.getNumberOfCopies();

        if (paperBook.isSellable()) {
            if (bookCount > 0) {
                if (quantity > bookCount) {
                    throw new CustomValidationException("There are only " + bookCount + " books available");
                }
                BookSell bookSell = new BookSell();
                bookSell.setBookId(bookId);
                bookSell.setSellQuantity(quantity);
                bookSell.setSellPrice(service.calculatePrice(paperBook.getPrice()));
                addBookSell(bookSell);
                paperBook.setNumberOfCopies(bookCount - quantity);
                service.updateBook(paperBook);
            } else {
                throw new CustomValidationException("There book is not available");
            }
        } else {
            throw new CustomValidationException("There book is not sellable");
        }
    }

    @Override
    public void returnBook(Integer bookId, Integer bookSellOrderId) throws IOException, CustomValidationException, ParseException {
        boolean found = false;
        for (BookSell bookSell : getSells()) {
            if (bookSell.getBookSellId().equals(bookSellOrderId)) {
                found = true;
                Date expireDate = DateUtils.addDays(bookSell.getDateOfPurchase(), DUE_DATE);
                if (!bookSell.getBookId().equals(bookId)) {
                    throw new RuntimeException("You can't return book which wasn't bought by you");
                }
                if (!isValidReturnDay(expireDate, new Date())) {
                    System.out.println("Cannot take book, 14 days left");
                    throw new CustomValidationException("Cannot return book, 14 days left");
                } else {
                    PaperBook pb = (PaperBook) service.getBookById(bookId);
                    int count = pb.getNumberOfCopies();
                    int sellCount = bookSell.getSellQuantity();
                    pb.setNumberOfCopies(++count);
                    if (sellCount > 0) {
                        bookSell.setSellQuantity(--sellCount);
                        service.updateBook(pb);
                        updateSells(bookSell);
                    } else {
                        throw new CustomValidationException("Not sold book");
                    }
                }
            }
        }
        if (!found) {
            throw new CustomValidationException("No match found");
        }
    }

    public List<BookSell> getSells() throws IOException, ParseException {
        List<String> sellsContent = fileService.readFromFile(SELL_BOOK_FILE_PATH);
        List<BookSell> soldBooks = new ArrayList<>();
        for (String sell : sellsContent) {
            soldBooks.add(createSell(sell));
        }
        return soldBooks;
    }

    private static BookSell createSell(String data) throws ParseException {
        String[] tokens = data.split(",");
        BookSell soldBooks = new BookSell();
        soldBooks.setBookSellId(Integer.parseInt(tokens[0]));
        soldBooks.setBookId(Integer.parseInt(tokens[1]));
        soldBooks.setDateOfPurchase(DateUtils.convertStringToDate(tokens[2]));
        soldBooks.setSellPrice(Double.parseDouble(tokens[3]));
        soldBooks.setSellQuantity(Integer.parseInt(tokens[4]));
        return soldBooks;
    }

    public void updateSells(BookSell bookSell) throws IOException, ParseException {

        List<BookSell> oldList = getSells();
        StringBuilder updatedSellList = new StringBuilder();
        for (BookSell sell : oldList) {
            if (sell.getBookSellId().equals(bookSell.getBookSellId())) {
                sell.setSellQuantity(bookSell.getSellQuantity());
            }
            updatedSellList.append(sell.toString());
        }

        fileService.updateFile(SELL_BOOK_FILE_PATH, updatedSellList.toString());
    }
}
