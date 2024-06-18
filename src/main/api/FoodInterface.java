package main.api;

import java.util.ArrayList;
import main.java.Food;

public interface FoodInterface {
  ArrayList<Food> getFoodList();

  int getTransition_1();

  int getTransition_2();
}
