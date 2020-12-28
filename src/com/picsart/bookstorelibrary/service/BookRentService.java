package com.picsart.bookstorelibrary.service;


import com.picsart.bookstorelibrary.exception.CustomValidationException;
import com.picsart.bookstorelibrary.exception.NegativeBalanceException;

import java.io.IOException;
import java.text.ParseException;

public interface BookRentService {
    void checkIn(Integer bookId, String cardNum) throws IOException, CustomValidationException, ParseException;
    void checkOut(Integer bookId, String cardNUmber) throws IOException, CustomValidationException, NegativeBalanceException;
}
