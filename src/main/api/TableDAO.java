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
    } catch (SQLException e) {
      Logger.error("Fehler beim Lesen der Datenbank.");
      System.out.println("Fehler beim Lesen der Datenbank.");
    }

    return tableList;
  }

  public void setStatus(int tableNumber, boolean status) {
    try {
      PreparedStatement update = connection.prepareStatement("UPDATE tisch SET status = ? WHERE tischID = ?");
      update.setBoolean(1, status);
      update.setInt(2, tableNumber);
      update.executeUpdate();
    } catch (SQLException e) {
      Logger.error("Fehler beim Schreiben in die Datenbank.");
      System.out.println("Fehler beim Schreiben in die Datenbank.");
    }
  }
}