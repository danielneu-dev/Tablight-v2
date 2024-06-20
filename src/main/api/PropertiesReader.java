package main.api;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
  private static final String file = "config.properties";
  private static Properties properties;
  private static LoggerInterface error = new ErrorDecorator(new PrintDecorator(new Logger()));
  private static LoggerInterface info = new InfoDecorator(new Logger());

  public static String getProperty(String key) {
    properties = new Properties();
    try (InputStream input = PropertiesReader.class.getClassLoader().getResourceAsStream(file)) {
      properties.load(input);
      info.writeFile("Properties-Datei geladen.");
    } catch (Exception e) {
      error.writeFile("Fehler beim Laden der Properties-Datei.");
    }
    return properties.getProperty(key);
  }
}