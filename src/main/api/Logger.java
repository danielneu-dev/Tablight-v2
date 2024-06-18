package main.api;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
  private static final String filePath = "src/log.txt";

  public static void info(String message) {
    writeFile("[INFO] " + message);
  }

  public static void error(String message) {
    writeFile("[ERROR] " + message);
  }

  public static String getTime() {
    return java.time.LocalTime.now().toString();
  }

  private static void writeFile(String message) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
      writer.write(message);
      writer.newLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
