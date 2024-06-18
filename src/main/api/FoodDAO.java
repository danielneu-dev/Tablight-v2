package main.api;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import main.java.Appetizer;
import main.java.Dessert;
import main.java.Food;
import main.java.MainCourse;

public class FoodDAO extends Database implements FoodInterface {
  private int transition_1 = 0;
  private int transition_2 = 0;

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

  public int getTransition_1() {
    return transition_1;
  }

  public int getTransition_2() {
    return transition_2;
  }
}
