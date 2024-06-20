package main.api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
  private String url = PropertiesReader.getProperty("database.url");
  private String user = PropertiesReader.getProperty("database.user");
  private String password = PropertiesReader.getProperty("database.password");

  private LoggerInterface info = new InfoDecorator(new PrintDecorator(new Logger()));
  private LoggerInterface error = new ErrorDecorator(new PrintDecorator(new Logger()));

  private Connection connection;

  private Database() {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
      connection = DriverManager.getConnection(url, user, password);
      info.writeFile("Datenbankverbindung erfolgreich hergestellt.");
    } catch (SQLException e) {
      error.writeFile("Fehler beim Herstellen der Datenbankverbindung.");
    } catch (ClassNotFoundException e) {
      error.writeFile("Fehler beim Laden des Treibers.");
    }
  }

  private static Database database = new Database();

  public static Database getDatabase() {
    return database;
  }

  public Connection getConnection() {
    return connection;
  }
}