package main.java;

public class Appetizer extends Food {
  public Appetizer(String name, double price) {
    super(name, price);
  }

  @Override
  public String toString() {
    return "Vorspeise: " + getName() + " - " + getPrice() + "€";
  }
}
