package com.picsart.bookstorelibrary.controller;

import java.util.Scanner;

public class BookMenuController {

    private PaperBookController pbController;
    private EbookController ebController;
    private BorrowerCardController cardController;
    private BorrowerController borrowerController;

    public BookMenuController() {
        this.pbController = new PaperBookController();
        this.ebController = new EbookController();
        this.cardController = new BorrowerCardController();
        this.borrowerController = new BorrowerController();
    }


    public void menuProcessing(Scanner sc) {
        boolean repeat = true;
        while (repeat) {
            try {
                switch (menu(sc)) {
                    case 1 -> pbController.createPBook(sc);
                    case 2 -> ebController.createEBook(sc);
                    case 3 -> cardController.createBorrowCard(sc);
                    case 4 -> borrowerController.createBorrower(sc);
                    case 5 -> pbController.buyBook(sc);
                    case 6 -> ebController.downloadBook(sc);
                    case 7 -> pbController.returnBook(sc);
                    case 8 -> pbController.checkInBook(sc);
                    case 9 -> pbController.checkOutBook(sc);
                    case 10 -> ebController.printEbookInfo(sc);
                    case 11 -> pbController.printBookInfo(sc);
                    case 12 -> ebController.getMaxDownloadedBook();
                    case 0 -> repeat = false;
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
    }


    private int menu(Scanner in) {

        System.out.println("\nSelect menu");

        System.out.println("1. Add Book");
        System.out.println("2. Add Ebook");
        System.out.println("3. Add Borrow Card");
        System.out.println("4. Add Borrower");
        System.out.println("5. Buy Book");
        System.out.println("6. Download Book");
        System.out.println("7. Return Book");
        System.out.println("8. Check in Book");
        System.out.println("9. Check out Book");
        System.out.println("10. print ebook info");
        System.out.println("11. print paper book info");
        System.out.println("12. print max downloaded ebook title");
        System.out.println("0. Exit");

        int menuNum = in.nextInt();
        in.nextLine();
        if (menuNum > 0 && menuNum < 13) {
            return menuNum;
        } else {
            System.out.println("You will exit!");
            return 0;
        }
    }
}
