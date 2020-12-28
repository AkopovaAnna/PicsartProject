package com.picsart.bookstorelibrary.controller;

import com.picsart.bookstorelibrary.exception.CustomValidationException;
import com.picsart.bookstorelibrary.model.Borrower;
import com.picsart.bookstorelibrary.service.impl.BorrowCardService;
import com.picsart.bookstorelibrary.service.impl.BorrowerService;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class BorrowerController {

    private BorrowerService borrowerService;
    private BorrowCardService cardService;

    public BorrowerController() {
        this.cardService = BorrowCardService.getInstance();
        this.borrowerService = BorrowerService.getInstance();
    }

    public void createBorrower(Scanner sc) {

        Borrower borrower = new Borrower();
        try {
            System.out.println("Add Borrower");

            System.out.println("Enter borrower card number: ");
            String cardNum = sc.nextLine();

            System.out.println("Enter borrower name: ");
            String name = sc.nextLine();
            System.out.println("Enter borrower address: ");
            String address = sc.nextLine();
            System.out.println("Enter borrower phone: ");
            String phone = sc.nextLine();

            borrower.setName(name);
            borrower.setBorrowCardId(cardService.getByCardNum(cardNum).getBorrowCardId());
            borrower.setAddress(address);
            borrower.setPhoneNumber(phone);
            borrowerService.add(borrower);

        } catch (InputMismatchException | IOException | CustomValidationException e) {
            System.out.println(e.getMessage());
            sc.nextLine();

        }
    }
}
