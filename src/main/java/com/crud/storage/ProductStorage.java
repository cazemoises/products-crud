package com.crud.storage;

import java.sql.*;
import com.crud.util.DBConnection;

public class ProductStorage {

    public String store(String name, String description, double price) {
        String productId = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.getInstance().getConnection();
            String sqlQuery = "INSERT INTO product (name, description, price) VALUES (?, ?, ?) RETURNING id";
            
            statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, name);
            statement.setString(2, description);
            statement.setDouble(3, price);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                productId = resultSet.getString("id");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return productId;
    }
}
