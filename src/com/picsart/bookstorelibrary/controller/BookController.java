package com.picsart.bookstorelibrary.controller;

import com.picsart.bookstorelibrary.exception.CustomValidationException;
import com.picsart.bookstorelibrary.model.Book;
import com.picsart.bookstorelibrary.model.Genre;

import java.util.Scanner;

public class BookController {

    public void bookData(Scanner sc, Book book) {
        System.out.println("Enter title: ");
        String title = sc.nextLine();
        System.out.println("Enter author: ");
        String author = sc.nextLine();
        System.out.println("Enter language: ");
        String language = sc.nextLine();
        System.out.println("Enter price: ");
        double price = sc.nextDouble();
        sc.nextLine();

        book.setTitle(title);
        book.setAuthor(author);
        book.setLanguage(language);
        book.setPrice(price);
    }


    public static Genre fetchValue(String constant) throws CustomValidationException {
        for (Genre genre : Genre.values()) {
            if (genre.name().equals(constant.toUpperCase())) {
                return genre;
            }
        }
        throw new CustomValidationException("Not correct Input");
    }


    public static int genreInput(Scanner sc) {
        System.out.println("\nSelect Book Genre");
        System.out.print("1. fantasy ");
        System.out.print("2. drama ");
        System.out.print("3. programming ");
        int choice = sc.nextInt();
        sc.nextLine();
        if (choice > 0 && choice < 4) {
            return choice;
        } else {
            System.out.println("Enter correct genre!");
            return 0;
        }

    }

    public Genre genreMenu(Scanner sc) {
        boolean repeat = true;
        Genre genre = null;
        while (repeat) {
            try {
                switch (genreInput(sc)) {
                    case 1 -> {
                        genre = fetchValue("fantasy");
                        repeat = false;
                    }
                    case 2 -> {
                        genre = fetchValue("drama");
                        repeat = false;
                    }
                    case 3 -> {
                        genre = fetchValue("programming");
                        repeat = false;
                    }
                    case 0 -> repeat = true;
                }
            } catch (CustomValidationException e) {
                System.out.println(e.getMessage());
            }
        }

        return genre;
    }

}
