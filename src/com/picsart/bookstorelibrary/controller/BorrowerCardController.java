package com.picsart.bookstorelibrary.controller;

import com.picsart.bookstorelibrary.exception.CustomValidationException;
import com.picsart.bookstorelibrary.model.BorrowerCard;
import com.picsart.bookstorelibrary.service.impl.BorrowCardService;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;


public class BorrowerCardController {

    private BorrowCardService borrowerCardService;

    public BorrowerCardController() {
        this.borrowerCardService = BorrowCardService.getInstance();
    }

    public void createBorrowCard(Scanner sc) {

        BorrowerCard borrowerCard = new BorrowerCard();
        try {
            System.out.println("Add Borrower");

            System.out.println("Enter borrower card number: ");
            String cardNum = sc.nextLine();

            borrowerCard.setCardNumb(cardNum);
            borrowerCardService.addBorrower(borrowerCard);

        } catch (InputMismatchException | CustomValidationException | IOException e) {
            System.out.println(e.getMessage());
            sc.nextLine();

        }
    }
}
