package com.picsart.bookstorelibrary.service.impl;

import com.picsart.bookstorelibrary.exception.CustomValidationException;
import com.picsart.bookstorelibrary.model.Book;
import com.picsart.bookstorelibrary.model.Genre;
import com.picsart.bookstorelibrary.model.PaperBook;
import com.picsart.bookstorelibrary.service.BookService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookServiceImpl implements BookService {

    private static final String PAPER_BOOK_FILE_PATH = "src/com/picsart/bookstorelibrary/resources/pBook.txt";
    private static BookServiceImpl bookService;
    private FileProcessingService fileService;

    private BookServiceImpl() {
        this.fileService = FileProcessingService.getInstance();
    }


    public static BookServiceImpl getInstance() {
        if (bookService == null) {
            bookService = new BookServiceImpl();
        }
        return bookService;
    }


    @Override
    public void addBook(Book book) throws IOException, CustomValidationException {
        PaperBook pBook = (PaperBook) book;
        if (pBook.getNumberOfCopies() > 0) {
            pBook.setBookId(calculateBookId());
            fileService.exportDataToFile(PAPER_BOOK_FILE_PATH, pBook.toString());
            System.out.println("Book was added");
        } else {
            throw new CustomValidationException("book quantity should be greater 0");
        }
    }

    private Integer calculateBookId() throws IOException {
        List<PaperBook> paperBooks = getPaperBooks();
        Integer id = 0;
        if(paperBooks.size() > 0) {
            id = paperBooks.get(paperBooks.size() - 1).getBookId();
        }
        return id + 1;
    }

    @Override
    public Double calculatePrice(Double price){
        if (LocalDate.now().getMonthValue() == 1 && LocalDate.now().getDayOfMonth() == 1) {
            return (price - price * 0.2);
        }
        return price;
    }


    public List<PaperBook> getPaperBooks() throws IOException {
        List<String> usersContent = fileService.readFromFile(PAPER_BOOK_FILE_PATH);
        List<PaperBook> paperBooks = new ArrayList<>();
        for (String book : usersContent) {
            paperBooks.add(createPBook(book));
        }
        return paperBooks;
    }

    private static PaperBook createPBook(String data) {
        String[] tokens = data.split(",");
        PaperBook paperBook = new PaperBook();
        paperBook.setBookId(Integer.parseInt(tokens[0]));
        paperBook.setTitle(tokens[1]);
        paperBook.setAuthor(tokens[2]);
        paperBook.setLanguage(tokens[3]);
        paperBook.setPrice(Double.parseDouble(tokens[4]));
        paperBook.setMaterial(tokens[5]);
        paperBook.setSellable(Boolean.parseBoolean(tokens[6]));
        paperBook.setNumberOfCopies(Integer.parseInt(tokens[7]));
        paperBook.setGenre(Genre.valueOf(tokens[8]));
        return paperBook;
    }

    @Override
    public Book getBookById(Integer bookId) throws IOException, CustomValidationException {
        List<PaperBook> paperBooks = getPaperBooks();
        for (Book book : paperBooks) {
            if (book.getBookId().equals(bookId)) {
                return book;
            }
        }
        throw new CustomValidationException("Book Not Found");
    }

    @Override
    public  void updateBook(Book paperBook) throws IOException {

        List<PaperBook> oldList = getPaperBooks();
        StringBuilder updatedList = new StringBuilder();
        for (PaperBook pb : oldList) {
            PaperBook pBook = (PaperBook) paperBook;
            if (pb.getBookId().equals(pBook.getBookId())){
                pb.setNumberOfCopies(pBook.getNumberOfCopies());
            }
            updatedList.append(pb.toString());
        }

        fileService.updateFile(PAPER_BOOK_FILE_PATH, updatedList.toString());
    }

    public static boolean isValidReturnDay(Date expiredDate, Date returnedDate) {
        return expiredDate.after(returnedDate);
    }
}
