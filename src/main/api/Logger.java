package main.api;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Logger implements LoggerInterface {
  private final String filePath;

  public Logger() {
    this.filePath = "src/log.txt";
  }

  @Override
  public void writeFile(String message) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
      writer.write(message);
      writer.newLine();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}