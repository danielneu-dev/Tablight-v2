package main.java;

import main.api.Logger;
import main.ui.MainMenu;
import main.api.InfoDecorator;
import main.api.LoggerInterface;

public class App {
    private static LoggerInterface info = new InfoDecorator(new Logger());

    public static void main(String[] args) {
        clearConsole();
        info.writeFile("Programm gestartet.");
        MainMenu mainMenu = new MainMenu();
        mainMenu.show();
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}