package com.picsart.bookstorelibrary.validator;

import com.picsart.bookstorelibrary.exception.CustomValidationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BorrowerCardValidator {

    private static boolean isValidString(String str) {
        return str != null && !str.isEmpty();
    }

    public static void validateCardNumber(String cardNumber) throws CustomValidationException {
        String regex = "[a-zA-Z0-9]+\\.?";
        if (isValidString(cardNumber)) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(cardNumber);
            if (!matcher.matches()) {
                throw new CustomValidationException("Invalid cardNumber");
            }

        } else {
            throw new CustomValidationException("Please insert cardNumber");
        }
    }
}
