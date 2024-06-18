package main.api;

import java.util.ArrayList;
import main.java.Food;
import main.java.Table;

public interface AdminInterface {
  ArrayList<Table> getAll();

  void setStatus(int tableNumber, boolean status);

  ArrayList<Food> getFoodList();
}
