package main.ui;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import main.api.AdminDAO;
import main.api.AdminInterface;
import main.api.Logger;
import main.java.App;
import main.java.Food;
import main.java.Table;
import main.api.ErrorDecorator;
import main.api.InfoDecorator;
import main.api.LoggerInterface;
import main.api.PrintDecorator;

public class AdminMenu {
  private ArrayList<Table> tableList;
  private ArrayList<Food> foodList;
  private AdminInterface database;

  private LoggerInterface info = new InfoDecorator(new PrintDecorator(new Logger()));
  private LoggerInterface error = new ErrorDecorator(new PrintDecorator(new Logger()));

  private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

  public AdminMenu() {
    database = new AdminDAO();
    tableList = database.getAll();
    foodList = database.getFoodList();
  }

  public void show() {
    info.writeFile("Admin-Menu wird angezeigt.");
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    int option = 0;

    do {
      App.clearConsole();
      showMenu();
      try {
        String input = reader.readLine().trim();
        option = Integer.parseInt(input);

        switch (option) {
          case 1:
            info.writeFile("Option 1: Tische verwalten");
            manageTableList();
            break;
          case 2:
            info.writeFile("Option 2: Speisen verwalten");
            manageFoodList();
            break;
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
      } catch (Exception e) {
        error.writeFile("Fehler beim Lesen der Eingabe.");
      }
    } while (option != 3);
  }

  private void showMenu() {
    System.out.println("Verwaltungsmenü");
    System.out.println("[1] Tische verwalten");
    System.out.println("[2] Speisen verwalten");
    System.out.println("[3] Verwaltung beenden");
    System.out.print("> ");
  }

  private void showTableMenu() {
    System.out.println("\nTischverwaltung");
    System.out.println("[1] Tischstatus ändern");
    System.out.println("[2] Tisch hinzufügen");
    System.out.println("[3] Tisch entfernen");
    System.out.println("[4] Zurück");
    System.out.print("> ");
  }

  private void showFoodMenu() {
    System.out.println("\nSpeisenverwaltung");
    System.out.println("[1] Speise hinzufügen");
    System.out.println("[2] Speise entfernen");
    System.out.println("[3] Zurück");
    System.out.print("> ");
  }

  private void manageTableList() {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    int option = 0;

    do {
      App.clearConsole();
      showTableList();
      showTableMenu();
      try {
        String input = reader.readLine().trim();
        option = Integer.parseInt(input);

        switch (option) {
          case 1:
            App.clearConsole();
            showTableList();
            info.writeFile("Option 1: Tischstatus ändern");
            System.out.print("Tischnummer: ");
            int tableNumber = Integer.parseInt(reader.readLine().trim());
            System.out.print("Status (true(belegt)/false(frei)): ");
            boolean status = Boolean.parseBoolean(reader.readLine().trim());
            database.setStatus(tableNumber, status);
            info.writeFile("[" + tableNumber + "] wurde auf " + status + " gesetzt.");
            break;
          case 2:
            info.writeFile("Option 2: Tisch hinzufügen");
            database.addTable();
            break;
          case 3:
            info.writeFile("Option 3: Tisch entfernen");
            System.out.print("Tischnummer: ");
            int tableNumberToRemove = Integer.parseInt(reader.readLine().trim());
            database.removeTable(tableNumberToRemove);
            break;
          case 4:
            App.clearConsole();
            info.writeFile("Zurück zum Hauptmenü.");
            break;
          default:
            error.writeFile("Ungültige Zahl.");
            break;
        }
      } catch (NumberFormatException e) {
        info.writeFile("Ungültige Eingabe.");
      } catch (Exception e) {
        error.writeFile("Fehler beim Lesen der Eingabe.");
      }
    } while (option != 4);
  }

  private void manageFoodList() {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    int option = 0;

    do {
      App.clearConsole();
      showFoodList();
      showFoodMenu();

      try {
        String input = reader.readLine().trim();
        option = Integer.parseInt(input);

        switch (option) {
          case 1:
            info.writeFile("Option 1: Speise hinzufügen");
            database.addFood();
            break;
          case 2:
            info.writeFile("Option 2: Speise entfernen");
            database.removeFood();
            break;
          case 3:
            App.clearConsole();
            info.writeFile("Zurück zum Hauptmenü.");
            break;
          default:
            error.writeFile("Ungültige Zahl.");
            break;
        }
      } catch (NumberFormatException e) {
        info.writeFile("Ungültige Eingabe.");
      } catch (Exception e) {
        error.writeFile("Fehler beim Lesen der Eingabe.");
      }
    } while (option != 3);
  }

  public void showTableList() {
    tableList = database.getAll();
    for (Table table : tableList) {
      System.out.println("[" + table.getTableNumber() + "] Status: " + table.getStatus());
    }
  }

  public void showFoodList() {
    foodList = database.getFoodList();
    System.out.println("Speisekarte");
    for (int i = 0; i < foodList.size(); i++) {
      System.out.println("[" + (i + 1) + "] " + foodList.get(i).getName() + " ("
          + decimalFormat.format(foodList.get(i).getPrice()) + "€)");
    }
  }
}