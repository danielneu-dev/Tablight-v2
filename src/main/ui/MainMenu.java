package main.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import main.api.Logger;
import main.api.Pin;
import main.java.App;
import main.api.ErrorDecorator;
import main.api.InfoDecorator;
import main.api.LoggerInterface;
import main.api.PrintDecorator;

public class MainMenu {
  private LoggerInterface info = new InfoDecorator(new PrintDecorator(new Logger()));
  private LoggerInterface error = new ErrorDecorator(new PrintDecorator(new Logger()));

  public void show() {
    System.out.println("Willkommen im Restaurant!");
    while (!Pin.check())
      ;
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    int option = 0;

    do {
      App.clearConsole();
      showMenu();
      try {
        String input = reader.readLine().trim();
        option = Integer.parseInt(input);
        info.writeFile("Option: " + option);

        switch (option) {
          case 1:
            info.writeFile("Option 1: Verwaltungsmenü wird geöffnet.");
            AdminMenu adminMenu = new AdminMenu();
            adminMenu.show();
            break;
          case 2:
            info.writeFile("Option 2: Tisch belegen");
            TableMenu tableMenu = new TableMenu();
            if (tableMenu.getAmount() == 0) {
              info.writeFile("Alle Tische sind belegt.");
              break;
            } else {
              info.writeFile("Tisch verfügbar.");
              App.clearConsole();
              tableMenu.show();
              break;
            }
          case 3:
            App.clearConsole();
            info.writeFile("Das Programm wird beendet.\n");
            break;
          default:
            error.writeFile("Ungültige Zahl.");
            break;
        }
      } catch (NumberFormatException e) {
        info.writeFile("Ungültige Eingabe.");
      } catch (IOException e) {
        error.writeFile("Fehler beim Lesen der Eingabe.");
      }
    } while (option != 3);

    try {
      reader.close();
    } catch (IOException e) {
      error.writeFile("Fehler beim Schließen des Readers.");
    }
  }

  private static void showMenu() {
    System.out.println("Hauptmenü");
    System.out.println("[1]: Verwaltungsmenü");
    System.out.println("[2]: Tisch belegen");
    System.out.println("[3]: Programm beenden");
    System.out.print("> ");
  }
}