package com.picsart.bookstorelibrary.service;

import com.picsart.bookstorelibrary.exception.CustomValidationException;

import java.io.IOException;

public interface EbookService extends BookService {
    void preview(Integer bookId) throws IOException, CustomValidationException;

    void download(Integer bookId) throws IOException, CustomValidationException;
}
