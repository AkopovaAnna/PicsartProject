package com.picsart.bookstorelibrary.controller;

import com.picsart.bookstorelibrary.exception.CustomValidationException;
import com.picsart.bookstorelibrary.model.Ebook;
import com.picsart.bookstorelibrary.model.Genre;
import com.picsart.bookstorelibrary.service.impl.EbookServiceImpl;

import java.io.IOException;
import java.util.Scanner;


public class EbookController {

    private EbookServiceImpl ebookService;
    private BookController bookController;

    public EbookController() {
        this.bookController = new BookController();
        this.ebookService = EbookServiceImpl.getInstance();
    }

    public void createEBook(Scanner sc) {
        Ebook ebook = new Ebook();
        try {
            System.out.println("Add EBook");
            bookController.bookData(sc, ebook);
            System.out.println("Enter extension: ");
            String extension = sc.nextLine();
            System.out.println("Enter size: ");
            double size = sc.nextDouble();
            sc.nextLine();
            Genre genre = bookController.genreMenu(sc);
            ebook.setExtensionType(extension);
            ebook.setSize(size);
            ebook.setGenre(genre);
            ebookService.addBook(ebook);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void downloadBook(Scanner sc) {
        try {
            System.out.println("Enter book id you want to download");
            Integer id = sc.nextInt();
            sc.nextLine();
            ebookService.download(id);
        } catch (IOException | CustomValidationException e) {
            System.out.println(e.getMessage());
        }
    }

    public void printEbookInfo(Scanner sc) {
        System.out.println("enter book id to get info");
        Integer id = sc.nextInt();
        sc.nextLine();
        try {
            ebookService.printEBookInfoById(id);
        } catch (IOException | CustomValidationException e) {
            System.out.println(e.getMessage());
        }
    }

    public void getMaxDownloadedBook() throws IOException {
        System.out.println(ebookService.getMaxDownloaded());
    }
}
