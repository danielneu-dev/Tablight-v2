package main.api;

import main.java.Appetizer;
import main.java.Dessert;
import main.java.Food;
import main.java.MainCourse;

public class FoodFactory {
  public static Food createFood(String type, String name, double price) {
    if (type.equals("Vorspeise")) {
      return new Appetizer(name, price);
    } else if (type.equals("Hauptspeise")) {
      return new MainCourse(name, price);
    } else if (type.equals("Nachspeise")) {
      return new Dessert(name, price);
    }
    return null;
  }
}