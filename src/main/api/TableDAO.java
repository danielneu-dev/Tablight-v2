package main.api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import main.java.Table;

public class TableDAO implements TableInterface {
  private Connection connection = Database.getDatabase().getConnection();

  private LoggerInterface error = new ErrorDecorator(new PrintDecorator(new Logger()));
  private LoggerInterface info = new InfoDecorator(new PrintDecorator(new Logger()));

  public ArrayList<Table> getFree() {
    ArrayList<Table> tableList = new ArrayList<Table>();

    try {
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery("SELECT * FROM tisch WHERE status = 0");

      while (resultSet.next()) {
        int tableNumber = resultSet.getInt("tischID");
        boolean status = resultSet.getBoolean("status");

        Table temp = new Table(tableNumber, status);
        tableList.add(temp);
      }
      info.writeFile("Freie Tische erfolgreich geladen.");
    } catch (SQLException e) {
      error.writeFile("Fehler beim Lesen der Datenbank.");
    }

    return tableList;
  }

  public void setStatus(int tableNumber, boolean status) {
    try {
      PreparedStatement update = connection.prepareStatement("UPDATE tisch SET status = ? WHERE tischID = ?");
      update.setBoolean(1, status);
      update.setInt(2, tableNumber);
      update.executeUpdate();

      if (status) {
        info.writeFile("Tisch " + tableNumber + " als belegt markiert.");
      } else {
        info.writeFile("Tisch " + tableNumber + " als frei markiert.");
      }
    } catch (SQLException e) {
      error.writeFile("Fehler beim Schreiben in die Datenbank.");
    }
  }
}