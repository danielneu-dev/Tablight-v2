package main.api;

import java.util.ArrayList;
import main.java.Food;
import main.java.Table;

public interface AdminInterface {
  ArrayList<Table> getAll();

  void setStatus(int tableNumber, boolean status);

  void addTable();

  void removeTable(int tableNumber);

  ArrayList<Food> getFoodList();

  void addFood();

  void removeFood();

  int getTransition_1();

  int getTransition_2();
}
