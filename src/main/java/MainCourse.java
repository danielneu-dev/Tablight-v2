package main.java;

public class MainCourse extends Food {
  public MainCourse(String name, double price) {
    super(name, price);
  }

  @Override
  public String toString() {
    return "Hauptspeise: " + getName() + " - " + getPrice() + "€";
  }
}
