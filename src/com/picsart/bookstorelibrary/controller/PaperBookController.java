package com.picsart.bookstorelibrary.controller;


import com.picsart.bookstorelibrary.exception.CustomValidationException;
import com.picsart.bookstorelibrary.exception.NegativeBalanceException;
import com.picsart.bookstorelibrary.model.Genre;
import com.picsart.bookstorelibrary.model.PaperBook;
import com.picsart.bookstorelibrary.service.BookService;
import com.picsart.bookstorelibrary.service.impl.BookRentServiceImpl;
import com.picsart.bookstorelibrary.service.impl.BookSellServiceImpl;
import com.picsart.bookstorelibrary.service.impl.BookServiceImpl;

import java.io.IOException;
import java.text.ParseException;
import java.util.Scanner;


public class PaperBookController {

    private BookService bookService;
    private BookSellServiceImpl bookSell;
    private BookRentServiceImpl bookRent;
    private BookController bookController;

    public PaperBookController() {
        bookService = BookServiceImpl.getInstance();
        bookSell = BookSellServiceImpl.getInstance();
        bookRent = BookRentServiceImpl.getInstance();
        bookController = new BookController();
    }

    public void createPBook(Scanner sc) {

        PaperBook pb = new PaperBook();

        try {
            System.out.println("Add Book");
            bookController.bookData(sc, pb);
            System.out.println("Enter material: ");
            String material = sc.nextLine();
            System.out.println("Enter number of copies: ");
            int numOfCopies = sc.nextInt();
            sc.nextLine();
            System.out.println("Enter true or false; isSellable: ");
            Boolean isSellable = sc.nextBoolean();
            sc.nextLine();
            System.out.println("Enter true or false; isSellable: ");
            Genre genre = bookController.genreMenu(sc);
            pb.setNumberOfCopies(numOfCopies);
            pb.setMaterial(material);
            pb.setSellable(isSellable);
            pb.setGenre(genre);
            bookService.addBook(pb);
        } catch (IOException | CustomValidationException e) {
            System.out.println(e.getMessage());
        }
    }

    public void buyBook(Scanner sc) {
        try {
            System.out.println("Enter book id you want to buy");
            Integer id = sc.nextInt();
            sc.nextLine();
            System.out.println("Enter book quantity you want to buy");
            Integer count = sc.nextInt();
            sc.nextLine();
            bookSell.sell(id, count);
            System.out.println("Book was sold");
        } catch (CustomValidationException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void returnBook(Scanner sc) {
        try {
            System.out.println("Enter book id you want to return");
            Integer soldBookId = sc.nextInt();
            sc.nextLine();
            System.out.println("Enter sell id you want to return");
            Integer soldOrderId = sc.nextInt();
            sc.nextLine();
            bookSell.returnBook(soldBookId, soldOrderId);
        } catch (IOException | CustomValidationException | ParseException e) {
            System.out.println(e.getMessage());
        }
    }


    public void checkInBook(Scanner sc) {
        try {
            System.out.println("Enter book id you want to check in");
            Integer id = sc.nextInt();
            sc.nextLine();
            System.out.println("Enter card number who want to check in");
            String cardNum = sc.nextLine();
            bookRent.checkIn(id, cardNum);
        } catch (CustomValidationException | IOException | ParseException e) {
            System.out.println(e.getMessage());
        }

    }

    public void checkOutBook(Scanner sc) {
        try {
            System.out.println("Enter book id you want to check out");
            Integer id = sc.nextInt();
            sc.nextLine();
            System.out.println("Enter card number who want to check out");
            String clientCardNum = sc.nextLine();
            bookRent.checkOut(id, clientCardNum);
        } catch (IOException | CustomValidationException | NegativeBalanceException e) {
            System.out.println(e.getMessage());
        }
    }

    public void printBookInfo(Scanner sc) {
        try {
            System.out.println("enter book id to get info");
            Integer id = sc.nextInt();
            sc.nextLine();
            printBookInfoById(id);
        } catch (IOException | CustomValidationException e) {
            System.out.println(e.getMessage());
        }
    }

    public void printBookInfoById(Integer id) throws IOException, CustomValidationException {
        PaperBook book = (PaperBook) bookService.getBookById(id);
        System.out.println(book.toString());
    }
}
