package main.api;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import main.java.Food;

public class FoodDAO implements FoodInterface {
  private Connection connection = Database.getDatabase().getConnection();

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
      Logger.error("Fehler beim Lesen der Datenbank.");
      System.out.println("Fehler beim Lesen der Datenbank.");
    }

    return foodList;
  }
}