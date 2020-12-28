package com.picsart.bookstorelibrary.validator;

import com.picsart.bookstorelibrary.exception.CustomValidationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {

    public static boolean isValidString(String str) {
        return str != null && !str.isEmpty();
    }

    public static void validateEmail(String email) throws CustomValidationException {
        String regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";

        if (isValidString(email)) {
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(email);
            if (!matcher.matches()) {
                throw new CustomValidationException("Invalid email");
            }

        } else {
            throw new CustomValidationException("Please insert email");
        }
    }

    public static void validateFullName(String fName, String lName) throws CustomValidationException {
        String regex = "[a-zA-Z]+\\.?";

        Pattern pattern = Pattern.compile(regex);

        if (isValidString(fName) && isValidString(lName)) {

            Matcher matcherF = pattern.matcher(fName);
            Matcher matcherL = pattern.matcher(lName);
            if (!Character.isUpperCase(fName.charAt(0)) || !Character.isUpperCase(lName.charAt(0))) {
                throw new CustomValidationException("First letters should be uppercase");
            }
            if (!matcherF.matches() || !matcherL.matches()) {
                throw new CustomValidationException("only letters are allowed");
            }
        } else {
            throw new CustomValidationException("first name or last name is empty");
        }

    }

    public static void validateUserName(String userName) throws CustomValidationException {
        if (isValidString(userName)) {
            if (userName.length() < 10) { //? <=
                throw new CustomValidationException("userName should be greater 10");
            }
        } else {
            throw new CustomValidationException("Please enter username");
        }
    }

    public static void validatePassword(String password) throws CustomValidationException {
        String regex = "^(?=(?:.*[0-9]){3,})(?=.*[a-z])(?=(?:.*[A-Z]){2,}).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        if (isValidString(password)) {
            if (!matcher.matches()) {
                throw new CustomValidationException("at least 2 uppercase, 3 numbers and length greater than 8");
            }
        } else {
            throw new CustomValidationException("Please enter password");
        }
    }
}
