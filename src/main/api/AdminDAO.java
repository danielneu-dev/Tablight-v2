package main.api;

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

  public ArrayList<Food> getFoodList() {
    ArrayList<Food> foodList = new ArrayList<Food>();

    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM mahlzeit");

      while (resultSet.next()) {
        String name = resultSet.getString("bezeichnung");
        double price = resultSet.getDouble("preis");
        String type = resultSet.getString("typ");

        if (type.equals("Vorspeise")) {
          foodList.add(new Appetizer(name, price));
        } else if (type.equals("Hauptspeise")) {
          foodList.add(new MainCourse(name, price));
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
}
