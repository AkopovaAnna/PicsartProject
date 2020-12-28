package com.picsart.bookstorelibrary.service;

import com.picsart.bookstorelibrary.exception.CustomValidationException;
import com.picsart.bookstorelibrary.model.Book;

import java.io.IOException;

public interface BookService {

    Book getBookById(Integer bookId) throws IOException, CustomValidationException;

    void addBook(Book book) throws IOException, CustomValidationException;

    Double calculatePrice(Double price);

    void updateBook(Book book) throws IOException;

}
