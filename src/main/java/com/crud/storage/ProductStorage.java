package com.crud.storage;

import java.sql.*;

import org.springframework.stereotype.Service;

import com.crud.model.Product;
import com.crud.util.DBConnection;

@Service
public class ProductStorage {

    public String store(String id, String name, String description, double price) {
        String productId = null;
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DBConnection.getConnection();
            String sqlQuery = "INSERT INTO product (id, name, description, price) VALUES (CAST(? AS UUID), ?, ?, ?) RETURNING id";
            
            statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, id);
            statement.setString(2, name);
            statement.setString(3, description);    
            statement.setDouble(4, price);

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

    public Product[] list() {
        Product[] products = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {

            connection = DBConnection.getConnection();

            statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery("SELECT * FROM product");

            resultSet.last();
            int rowCount = resultSet.getRow();
            resultSet.beforeFirst();

            products = new Product[rowCount];
            int i = 0;

            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getString("id"));
                product.setName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(resultSet.getDouble("price"));
                products[i++] = product;
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

        return products;
    }

}