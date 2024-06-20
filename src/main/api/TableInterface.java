package main.api;

import java.util.ArrayList;
import main.java.Table;

public interface TableInterface {
  public ArrayList<Table> getFree();

  public void setStatus(int tableNumber, boolean status);
}