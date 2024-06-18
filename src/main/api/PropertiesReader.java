package main.api;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
  private static final String file = "config.properties";
  private static Properties properties;

  public static String getProperty(String key) {
    properties = new Properties();
    try (InputStream input = PropertiesReader.class.getClassLoader().getResourceAsStream(file)) {
      properties.load(input);
      Logger.info("Properties-Datei geladen.");
    } catch (Exception e) {
      Logger.error("Fehler beim Laden der Properties-Datei.");
      System.out.println("Fehler beim Laden der Properties-Datei.");
    }
    return properties.getProperty(key);
  }
}
