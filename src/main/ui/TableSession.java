package main.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import main.api.FoodDAO;
import main.api.FoodInterface;
import main.api.Logger;
import main.api.Pin;
import main.java.App;
import main.java.Food;
import main.api.ErrorDecorator;
import main.api.InfoDecorator;
import main.api.LoggerInterface;
import main.api.PrintDecorator;

public class TableSession {
  private int tableNumber;
  private FoodInterface databaseFood = new FoodDAO();
  private ArrayList<Food> orderTaken = new ArrayList<Food>();

  private LoggerInterface info = new InfoDecorator(new PrintDecorator(new Logger()));
  private LoggerInterface error = new ErrorDecorator(new PrintDecorator(new Logger()));

  private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

  public TableSession(int tableNumber) {
    this.tableNumber = tableNumber;
  }

  public void show() {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    int option = 0;

    do {
      System.out.println("Tischsitzung (Tisch " + tableNumber + ")");
      System.out.println("[1] Bestellung aufnehmen");
      System.out.println("[2] Bestellungen anzeigen");
      System.out.println("[3] Tischsitzung beenden");
      System.out.print("> ");

      try {
        option = Integer.parseInt(reader.readLine());
        switch (option) {
          case 1:
            takeOrder();
            break;
          case 2:
            getOrderTaken();
            break;
          case 3:
            App.clearConsole();
            System.out.println("Tischsitzung beenden");
            while (!Pin.check())
              ;
            info.writeFile("Tischsitzung beendet.");
            break;
          default:
            error.writeFile("Ungültige Eingabe.");
            break;
        }
      } catch (NumberFormatException e) {
        error.writeFile("Ungültige Eingabe.");
      } catch (IOException e) {
        error.writeFile("Fehler beim Lesen der Eingabe.");
      }
    } while (option != 3);
  }

  private void takeOrder() {
    App.clearConsole();
    ArrayList<Food> foodList = databaseFood.getFoodList();
    System.out.println("Bestellung aufnehmen");

    for (int i = 0; i < foodList.size(); i++) {
      System.out.println("[" + (i + 1) + "] " + foodList.get(i).getName() + " ("
          + decimalFormat.format(foodList.get(i).getPrice()) + "€)");
    }
    System.out.println("\n\n[" + (foodList.size() + 1) + "] Bestellung abschließen\n");

    int option = 0;
    do {
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
      System.out.print("> ");
      try {
        option = Integer.parseInt(reader.readLine());
        if (option == foodList.size() + 1) {
          info.writeFile("Bestellung abgeschlossen.");
        } else if (option < 1 || option > foodList.size()) {
          error.writeFile("Ungültige Eingabe.");
        } else {
          orderTaken.add(foodList.get(option - 1));
          info.writeFile("Bestellung aufgenommen: " + foodList.get(option - 1).getName());
        }
      } catch (NumberFormatException e) {
        error.writeFile("Ungültige Eingabe.");
      } catch (IOException e) {
        error.writeFile("Fehler beim Lesen der Eingabe.");
      }
    } while (option != foodList.size() + 1);
    App.clearConsole();
  }

  public void getOrderTaken() {
    App.clearConsole();
    System.out.println("Bestellungen (Tisch " + tableNumber + "):");
    for (int i = 0; i < orderTaken.size(); i++) {
      System.out
          .println(orderTaken.get(i).getName() + " (" + decimalFormat.format(orderTaken.get(i).getPrice()) + "€)");
    }
    System.out.println("\n--- Gesamtpreis ---\n" + decimalFormat.format(calculateTotalPrice()) + "€\n");
  }

  private double calculateTotalPrice() {
    double totalPrice = 0;
    for (int i = 0; i < orderTaken.size(); i++) {
      totalPrice += orderTaken.get(i).getPrice();
    }
    return totalPrice;
  }
}