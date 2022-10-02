package com.simprun.view;

import java.util.Scanner;

public class TerminalUI {
    private final Scanner scanner;

    public TerminalUI() {
        this.scanner = new Scanner(System.in);
    }

    public void print(String message) {
        System.out.print(message);
    }

    public void println(String message) {
        System.out.println(message);
    }

    public void displayList(String[] items) {
        for (int i = 0; i < items.length; i++) {
            println((i + 1) + ". " + items[i]);
        }
    }

    public int displayMenu(String[] items) {
        displayList(items);
        return promptInt("Enter your choice: ", 1, items.length);
    }

    public String prompt(String message) {
        System.out.print(message);
        return scanner.nextLine();
    }

    public int promptInt(String message, int min, int max) {
        int value;
        print(message);
        do {
            value = scanner.hasNextInt() ? scanner.nextInt() : -1;
            // consume the newline character
            scanner.nextLine();
            if (value < min || value > max) {
                System.out.println("Invalid value, please enter a value between " + min + " and " + max);
            }
        } while (value < min || value > max);
        return value;
    }
}
