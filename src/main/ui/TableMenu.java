package main.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import main.api.Logger;
import main.api.TableDAO;
import main.api.TableInterface;
import main.java.App;
import main.java.Table;

public class TableMenu {
  private ArrayList<Table> tableList;
  private TableInterface database;

  public TableMenu() {
    database = new TableDAO();
    this.tableList = database.getFree();
  }

  public int getAmount() {
    return tableList.size();
  }

  public void show() {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    boolean tableFound = false;
    System.out.println("Verfügbare Tischnummer:");

    for (Table table : tableList) {
      System.out.println("[" + table.getTableNumber() + "]");
    }
    System.out.print("> ");

    int tableNumber = 0;
    try {
      tableNumber = Integer.parseInt(reader.readLine().trim());
    } catch (NumberFormatException e) {
      Logger.error("Ungültige Eingabe.");
      System.out.println("Ungültige Eingabe.");
    } catch (IOException e) {
      Logger.error("Fehler beim Lesen der Eingabe.");
      System.out.println("Fehler beim Lesen der Eingabe.");
    }

    for (Table table : tableList) {
      if (tableNumber == table.getTableNumber()) {
        database.setStatus(tableNumber, true);
        tableFound = true;
        App.clearConsole();
        Logger.info("Tisch " + tableNumber + " wurde belegt.");
        break;
      }
    }

    if (tableFound) {
      TableSession tableSession = new TableSession(tableNumber);
      tableSession.show();
      App.clearConsole();
      database.setStatus(tableNumber, false);
    } else {
      Logger.error("Tisch nicht gefunden.");
      System.out.println("Tisch nicht gefunden.");
    }
  }
}