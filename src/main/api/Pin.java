package main.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import main.java.App;

public class Pin {
  public static boolean check() {
    LoggerInterface info = new InfoDecorator(new PrintDecorator(new Logger()));
    LoggerInterface error = new ErrorDecorator(new PrintDecorator(new Logger()));

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String pin = PropertiesReader.getProperty("pin");
    String input = "";

    try {
      System.out.print("Bitte geben Sie den PIN ein: ");
      input = reader.readLine();

      if (input.equals(pin)) {
        info.writeFile("PIN korrekt.");
        App.clearConsole();
        return true;
      } else {
        App.clearConsole();
        error.writeFile("PIN falsch.");
        return false;
      }

    } catch (IOException e) {
      error.writeFile("Fehler beim Lesen der Eingabe.");
    }

    return false;
  }
}