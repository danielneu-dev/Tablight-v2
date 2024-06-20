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
import main.api.ErrorDecorator;
import main.api.InfoDecorator;
import main.api.LoggerInterface;
import main.api.PrintDecorator;

public class TableMenu {
  private ArrayList<Table> tableList;
  private TableInterface database;

  private LoggerInterface info = new InfoDecorator(new PrintDecorator(new Logger()));
  private LoggerInterface error = new ErrorDecorator(new PrintDecorator(new Logger()));

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
      error.writeFile("Ungültige Eingabe.");
    } catch (IOException e) {
      error.writeFile("Fehler beim Lesen der Eingabe.");
    }

    for (Table table : tableList) {
      if (tableNumber == table.getTableNumber()) {
        database.setStatus(tableNumber, true);
        tableFound = true;
        App.clearConsole();
        break;
      }
    }

    if (tableFound) {
      info.writeFile("Tisch " + tableNumber + " wurde belegt.");
      TableSession tableSession = new TableSession(tableNumber);
      tableSession.show();
      App.clearConsole();
      database.setStatus(tableNumber, false);
      info.writeFile("Tisch " + tableNumber + " wurde freigegeben.");
    } else {
      error.writeFile("Tisch nicht gefunden.");
    }
  }
}