package main.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import main.java.Food;
import main.java.Table;

public class AdminDAO implements AdminInterface {
  private Connection connection = Database.getDatabase().getConnection();
  private LoggerInterface error = new ErrorDecorator(new PrintDecorator(new Logger()));

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
      error.writeFile("Fehler beim Lesen der Datenbank.");
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
      error.writeFile("Fehler beim Schreiben in die Datenbank.");
    }
  }

  public void addTable() {
    try {
      Statement statement = connection.createStatement();
      statement.executeUpdate("INSERT INTO tisch (status) VALUES (0)");
    } catch (SQLException e) {
      error.writeFile("Fehler beim Schreiben in die Datenbank.");
    }
  }

  public void removeTable(int tableNumber) {
    try {
      PreparedStatement statement = connection.prepareStatement("DELETE FROM tisch WHERE tischID = ?");
      statement.setInt(1, tableNumber);
      statement.executeUpdate();
    } catch (SQLException e) {
      error.writeFile("Fehler beim Schreiben in die Datenbank.");
    }
  }

  public ArrayList<Food> getFoodList() {
    ArrayList<Food> foodList = new ArrayList<Food>();

    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM mahlzeit");

      while (resultSet.next()) {
        String name = resultSet.getString("bezeichnung");
        double price = resultSet.getDouble("preis");
        String type = resultSet.getString("typ");

        foodList.add(FoodFactory.createFood(type, name, price));
      }
    } catch (SQLException e) {
      error.writeFile("Fehler beim Lesen der Datenbank.");
    }

    return foodList;
  }

  public void addFood() {
    String name = "";
    double price = 0;
    String type = "";

    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
      System.out.print("Bezeichnung: ");
      name = reader.readLine().trim();
      System.out.print("Preis: ");
      price = Double.parseDouble(reader.readLine().trim());
      System.out.print("Typ (Vorspeise, Hauptspeise, Nachspeise): ");
      type = reader.readLine();

      PreparedStatement statement = connection
          .prepareStatement("INSERT INTO mahlzeit (bezeichnung, preis, typ) VALUES (?, ?, ?)");
      statement.setString(1, name);
      statement.setDouble(2, price);
      statement.setString(3, type);
      statement.executeUpdate();
    } catch (IOException e) {
      error.writeFile("Fehler beim Lesen der Eingabe.");
    } catch (SQLException e) {
      error.writeFile("Fehler beim Schreiben in die Datenbank.");
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
      error.writeFile("Fehler beim Lesen der Eingabe.");
    } catch (SQLException e) {
      error.writeFile("Fehler beim Schreiben in die Datenbank.");
    }
  }
}