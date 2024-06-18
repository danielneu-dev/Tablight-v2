package main.java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import main.api.Logger;
import main.api.Pin;
import main.ui.TableMenu;

public class App {
    public static void main(String[] args) {
        Logger.info("Programm gestartet. (Zeitpunkt: " + Logger.getTime() + ")");
        clearConsole();
        System.out.println("Willkommen im Restaurant!");
        while (!Pin.check())
            ;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int option = 0;

        do {
            clearConsole();
            showMenu();
            try {
                String input = reader.readLine().trim();
                option = Integer.parseInt(input);
                Logger.info("Option: " + option);

                switch (option) {
                    case 1:
                        Logger.info("Option 1: Tisch belegen");
                        TableMenu tableMenu = new TableMenu();
                        if (tableMenu.getAmount() == 0) {
                            Logger.info("Alle Tische sind belegt.");
                            System.out.println("Alle Tische sind belegt.");
                            break;
                        } else {
                            Logger.info("Tisch verfügbar.");
                            clearConsole();
                            tableMenu.show();
                            break;
                        }
                    case 2:
                        clearConsole();
                        Logger.info("Das Programm wird beendet.\n");
                        System.out.println("Das Programm wird beendet.\n");
                        break;
                    default:
                        Logger.error("Ungültige Zahl.");
                        System.out.println("Ungültige Zahl.");
                        break;
                }
            } catch (NumberFormatException e) {
                Logger.info("Ungültige Eingabe.");
                System.out.println("Ungültige Eingabe.");
            } catch (IOException e) {
                Logger.error("Fehler beim Lesen der Eingabe.");
                System.out.println("Fehler beim Lesen der Eingabe.");
            }
        } while (option != 2);

        try {
            reader.close();
        } catch (IOException e) {
            Logger.error("Fehler beim Schließen des Readers.");
            System.out.println("Fehler beim Schließen des Readers.");
        }

    }

    private static void showMenu() {
        System.out.println("Hauptmenü");
        System.out.println("[1]: Tisch belegen");
        System.out.println("[2]: Programm beenden");
        System.out.print("> ");
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}