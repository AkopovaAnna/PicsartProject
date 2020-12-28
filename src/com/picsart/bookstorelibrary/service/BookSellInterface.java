package com.picsart.bookstorelibrary.service;


import com.picsart.bookstorelibrary.exception.CustomValidationException;

import java.io.IOException;
import java.text.ParseException;

public interface BookSellInterface {
    void sell(Integer bookId, Integer quantity) throws CustomValidationException, IOException;

    void returnBook(Integer bookId, Integer bookSellOrderId) throws CustomValidationException, IOException, ParseException;
}
