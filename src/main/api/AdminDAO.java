package main.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import main.java.Appetizer;
import main.java.Dessert;
import main.java.Food;
import main.java.MainCourse;
import main.java.Table;

public class AdminDAO extends Database implements AdminInterface {
  private int transition_1 = 0;
  private int transition_2 = 0;

  public ArrayList<Table> getAll() {
    ArrayList<Table> tableList = new ArrayList<Table>();

    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM tisch");

      while (resultSet.next()) {
        int tableNumber = resultSet.getInt("tischID");
        boolean status = resultSet.getBoolean("status");

        Table temp = new Table(tableNumber, status);
        tableList.add(temp);
      }
    } catch (SQLException e) {
      Logger.error("Fehler beim Lesen der Datenbank.");
      System.out.println("Fehler beim Lesen der Datenbank.");
    }

    return tableList;
  }

  public void setStatus(int tableNumber, boolean status) {
    try {
      PreparedStatement update = connection.prepareStatement("UPDATE tisch SET status = ? WHERE tischID = ?");
      update.setBoolean(1, status);
      update.setInt(2, tableNumber);
      update.executeUpdate();
    } catch (SQLException e) {
      Logger.error("Fehler beim Schreiben in die Datenbank.");
      System.out.println("Fehler beim Schreiben in die Datenbank.");
    }
  }

  public void addTable() {
    try {
      Statement statement = connection.createStatement();
      statement.executeUpdate("INSERT INTO tisch (status) VALUES (0)");
    } catch (SQLException e) {
      Logger.error("Fehler beim Schreiben in die Datenbank.");
      System.out.println("Fehler beim Schreiben in die Datenbank.");
    }
  }

  public void removeTable(int tableNumber) {
    try {
      PreparedStatement statement = connection.prepareStatement("DELETE FROM tisch WHERE tischID = ?");
      statement.setInt(1, tableNumber);
      statement.executeUpdate();
    } catch (SQLException e) {
      Logger.error("Fehler beim Schreiben in die Datenbank.");
      System.out.println("Fehler beim Schreiben in die Datenbank.");
    }
  }

  public ArrayList<Food> getFoodList() {
    ArrayList<Food> foodList = new ArrayList<Food>();
    transition_1 = 0;
    transition_2 = 0;

    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM mahlzeit ORDER BY sortieren ASC");

      while (resultSet.next()) {
        String name = resultSet.getString("bezeichnung");
        double price = resultSet.getDouble("preis");
        String type = resultSet.getString("typ");

        if (type.equals("Vorspeise")) {
          foodList.add(new Appetizer(name, price));
          transition_1++;
          transition_2++;
        } else if (type.equals("Hauptspeise")) {
          foodList.add(new MainCourse(name, price));
          transition_2++;
        } else if (type.equals("Nachspeise")) {
          foodList.add(new Dessert(name, price));
        }
      }
    } catch (SQLException e) {
      Logger.error("Fehler beim Lesen der Datenbank.");
      System.out.println("Fehler beim Lesen der Datenbank.");
    }

    return foodList;
  }

  public void addFood() {
    String name = "";
    double price = 0;
    String type = "";
    int sort = 0;

    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
      System.out.print("Bezeichnung: ");
      name = reader.readLine().trim();
      System.out.print("Preis: ");
      price = Double.parseDouble(reader.readLine().trim());
      System.out.print("Typ (Vorspeise, Hauptspeise, Nachspeise): ");
      type = reader.readLine();
      if (type.equals("Vorspeise")) {
        sort = 1;
      } else if (type.equals("Hauptspeise")) {
        sort = 2;
      } else if (type.equals("Nachspeise")) {
        sort = 3;
      }

      PreparedStatement statement = connection
          .prepareStatement("INSERT INTO mahlzeit (bezeichnung, preis, typ, sortieren) VALUES (?, ?, ?, ?)");
      Logger.info(" " + sort);
      statement.setString(1, name);
      statement.setDouble(2, price);
      statement.setString(3, type);
      statement.setInt(4, sort);
      statement.executeUpdate();
    } catch (IOException e) {
      Logger.error("Fehler beim Lesen der Eingabe.");
      System.out.println("Fehler beim Lesen der Eingabe.");
    } catch (SQLException e) {
      Logger.error("Fehler beim Schreiben in die Datenbank.");
      System.out.println("Fehler beim Schreiben in die Datenbank.");
    }
  }

  public void removeFood() {
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
      System.out.print("Nummer: ");
      int number = Integer.parseInt(reader.readLine().trim());

      PreparedStatement statement = connection.prepareStatement("DELETE FROM mahlzeit WHERE mahlzeitID = ?");
      statement.setInt(1, number);
      statement.executeUpdate();
    } catch (IOException e) {
      Logger.error("Fehler beim Lesen der Eingabe.");
      System.out.println("Fehler beim Lesen der Eingabe.");
    } catch (SQLException e) {
      Logger.error("Fehler beim Schreiben in die Datenbank.");
      System.out.println("Fehler beim Schreiben in die Datenbank.");
    }
  }

  public int getTransition_1() {
    return transition_1;
  }

  public int getTransition_2() {
    return transition_2;
  }
}
