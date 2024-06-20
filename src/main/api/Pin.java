package main.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import main.java.App;

public class Pin {
  public static boolean check() {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    String pin = PropertiesReader.getProperty("pin");
    String input = "";

    try {
      System.out.print("Bitte geben Sie den PIN ein: ");
      input = reader.readLine();

      if (input.equals(pin)) {
        Logger.info("PIN korrekt.");
        App.clearConsole();
        return true;
      } else {
        App.clearConsole();
        Logger.error("PIN falsch.");
        System.out.println("PIN falsch.");
        return false;
      }

    } catch (IOException e) {
      Logger.error("Fehler beim Lesen der Eingabe.");
      System.out.println("Fehler beim Lesen der Eingabe.");
    }

    return false;
  }
}