package main.java;

public class Dessert extends Food {
  public Dessert(String name, double price) {
    super(name, price);
  }

  @Override
  public String toString() {
    return "Nachspeise: " + getName() + " - " + getPrice() + "€";
  }
}
