package com.picsart.bookstorelibrary.controller;

import com.picsart.bookstorelibrary.exception.CustomValidationException;
import com.picsart.bookstorelibrary.model.User;
import com.picsart.bookstorelibrary.service.impl.UserService;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;


public class UserController {

    private UserService userService;
    private BookMenuController bookMenuController;

    public UserController() {
        this.userService = UserService.getInstance();
        this.bookMenuController = new BookMenuController();
    }

    public void registerUser(Scanner sc) {
        try {
            User user = new User();

            System.out.println("Enter Full Name [firstName LastName]: ");
            String fullName = sc.nextLine();
            System.out.println("Enter userName: ");
            String userName = sc.nextLine();
            System.out.println("Enter email: ");
            String email = sc.nextLine();
            System.out.println("Enter password: ");
            String password = sc.nextLine();

            String[] names = fullName.split(" ");

            if (names.length > 1) {
                user.setName(names[0]);
                user.setSurName(names[1]);
            }

            user.setUserName(userName);
            user.setEmail(email);
            user.setPassword(password);

            userService.register(user);
            System.out.println(user.getUserName() + " You have successfully registered");

            System.out.println("Login to have access to menu");
        } catch (IOException | CustomValidationException | NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }
    }


//    Input: username, password
//    Result: Login success or invalid input data

    public void loginUser(Scanner sc) {
        try {
            System.out.println("Enter userName: ");
            String userName = sc.nextLine();
            System.out.println("Enter password: ");
            String password = sc.nextLine();

            userService.login(userName, password);
            System.out.println("Login success");
            bookMenuController.menuProcessing(sc);
        } catch (CustomValidationException | IOException | NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
            sc.nextLine();
        }
    }
}
