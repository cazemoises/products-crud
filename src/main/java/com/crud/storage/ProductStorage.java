package com.crud.storage;

import org.springframework.stereotype.Service;
import com.crud.model.Product;
import com.crud.util.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductStorage {

    public String store(String name, String description, double price) {
        String sqlQuery = "INSERT INTO product (id, name, description, price) VALUES (CAST(? AS UUID), ?, ?, ?) RETURNING id";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            
            String productId = UUID.randomUUID().toString();
            statement.setString(1, productId);
            statement.setString(2, name);
            statement.setString(3, description);
            statement.setDouble(4, price);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Product[] list() {
        List<Product> products = new ArrayList<>();
        String sqlQuery = "SELECT * FROM product";

        try (Connection connection = DBConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sqlQuery)) {

            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getString("id"));
                product.setName(resultSet.getString("name"));
                product.setDescription(resultSet.getString("description"));
                product.setPrice(resultSet.getDouble("price"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products.toArray(new Product[0]);
    }
}
