package com.picsart.bookstorelibrary;

import com.picsart.bookstorelibrary.controller.MenuController;

import java.util.Scanner;

public class AppRunner {

    public static void main(String[] args) {
        MenuController controller = new MenuController();
        Scanner sc = new Scanner(System.in);

        controller.menuProcessing(sc);
    }
}
