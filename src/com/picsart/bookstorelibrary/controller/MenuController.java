package com.picsart.bookstorelibrary.controller;

import java.util.Scanner;

public class MenuController {

    private UserController controller = new UserController();

    public void menuProcessing(Scanner sc) {
        boolean tryAgain = true;

        while (tryAgain) {
            try {
                switch (menu(sc)) {
                    case 1 -> controller.registerUser(sc);
                    case 2 -> controller.loginUser(sc);
                    case 0 -> tryAgain = false;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }
    }

    private int menu(Scanner in) {

        System.out.println("\nSelect");
        System.out.println("1. Register");
        System.out.println("2. LogIn");
        System.out.println("0. Exit");
        int choice = in.nextInt();
        in.nextLine();
        if (choice > 0 && choice < 15) {
            return choice;
        } else {
            System.out.println("Enter again!");
            return 0;
        }
    }
}
