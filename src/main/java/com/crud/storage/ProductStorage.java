package com.crud.storage;

import com.crud.util.DBConnection;
import java.sql.*;

public class ProductStorage {
  public static void main(String[] args) {

    try {

      Connection connection = DBConnection.connect();

      Statement statement = connection.createStatement();

      String sqlQuery = "SELECT * FROM product";
      ResultSet resultSet = statement.executeQuery(sqlQuery);

      while (resultSet.next()) {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        double price = resultSet.getDouble("price");

        // Do something with the retrieved data
        System.out.println("Product ID: " + id);
        System.out.println("Product Name: " + name);
        System.out.println("Product Price: " + price);
      }

      resultSet.close();
      statement.close();
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}