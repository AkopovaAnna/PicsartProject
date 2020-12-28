package com.picsart.bookstorelibrary.service.impl;

import com.picsart.bookstorelibrary.exception.CustomValidationException;
import com.picsart.bookstorelibrary.model.Borrower;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BorrowerService {

    private static final String BORROWER_PATH_NAME = "src/com/picsart/bookstorelibrary/resources/borrower.txt";
    private static BorrowerService userService;
    private FileProcessingService fileService;

    private BorrowerService() {
        this.fileService = FileProcessingService.getInstance();
    }

    public static BorrowerService getInstance() {
        if (userService == null) {
            userService = new BorrowerService();
        }

        return userService;
    }

    public void add(Borrower borrower) throws IOException {
        borrower.setBorrowerID(calculateBorrowerId());
        fileService.exportDataToFile(BORROWER_PATH_NAME, borrower.toString());
        System.out.println("Borrower was created");
    }

    private Integer calculateBorrowerId() throws IOException {
        List<Borrower> borrowers = getBorrowers();
        Integer id = 0;
        if (borrowers.size() > 0) {
            id = borrowers.get(borrowers.size() - 1).getBorrowerID();
        }
        return id + 1;
    }

    public List<Borrower> getBorrowers() throws IOException {
        List<String> borrowerContent = fileService.readFromFile(BORROWER_PATH_NAME);
        return convertToBorrower(borrowerContent);
    }

    public List<Borrower> convertToBorrower(List<String> lines) {
        List<Borrower> borrowers = new ArrayList<>();
        for (String borrower :
                lines) {
            borrowers.add(createCard(borrower.split(",")));
        }
        return borrowers;
    }

    private static Borrower createCard(String[] tokens) {
        Borrower borrower = new Borrower();
        borrower.setBorrowerID(Integer.parseInt(tokens[0]));
        borrower.setBorrowCardId(Integer.valueOf(tokens[1]));
        borrower.setName(tokens[2]);
        borrower.setAddress(tokens[3]);
        borrower.setPhoneNumber(tokens[4]);
        return borrower;
    }

    public Borrower getBorrowerByCardId(Integer cardId) throws IOException, CustomValidationException {
        List<Borrower> borrowers = getBorrowers();
        for (Borrower borrower : borrowers) {
            if (borrower.getBorrowCardId().equals(cardId)) {
                return borrower;
            }
        }
        throw new CustomValidationException("Card not exist");
    }

    public Borrower getBorrowerById(Integer borrowerId) throws IOException, CustomValidationException {
        for (Borrower borrower : getBorrowers()) {
            if (borrower.getBorrowerID().equals(borrowerId)) {
                return borrower;
            }
        }
        throw new CustomValidationException("user not exist");
    }
}
