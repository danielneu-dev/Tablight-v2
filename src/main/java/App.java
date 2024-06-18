package main.java;

import main.api.Logger;
import main.ui.MainMenu;

public class App {
    public static void main(String[] args) {
        Logger.info("Programm gestartet. (Zeitpunkt: " + Logger.getTime() + ")");
        clearConsole();
        MainMenu mainMenu = new MainMenu();
        mainMenu.show();
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}