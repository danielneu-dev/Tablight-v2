package main.api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
  private String url = PropertiesReader.getProperty("database.url");
  private String user = PropertiesReader.getProperty("database.user");
  private String password = PropertiesReader.getProperty("database.password");

  protected static Connection connection;

  public Database() {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      connection = DriverManager.getConnection(url, user, password);
      Logger.info("Datenbankverbindung erfolgreich hergestellt.");
    } catch (SQLException e) {
      Logger.error("Fehler beim Herstellen der Datenbankverbindung.");
      System.out.println("Fehler beim Herstellen der Datenbankverbindung.");
    } catch (ClassNotFoundException e) {
      Logger.error("Fehler beim Laden des Treibers.");
      System.out.println("Fehler beim Laden des Treibers.");
    }
  }
}