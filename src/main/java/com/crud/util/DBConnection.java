package com.crud.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class DBConnection {
  private static DBConnection instance;
  private Connection connection;
  private static final Dotenv dotenv = Dotenv.load();

  private static final String URL = dotenv.get("DB_URL");
  private static final String USER = dotenv.get("DB_USER");
  private static final String PASSWORD = dotenv.get("DB_PASSWORD");

  private DBConnection() throws SQLException {
    try {
      this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
    } catch (SQLException e) {
      System.out.println("Failuer at connecting to databsae: " + e.getMessage());
    }
  }

  public static DBConnection getInstance() throws SQLException {
    if (instance == null) {
      instance = new DBConnection();
    } else if (instance.getConnection().isClosed()) {
      instance = new DBConnection();
    }

    return instance;
  }

  public Connection getConnection() {
    return connection;
  }
}
