package com.crud.storage;

import org.springframework.stereotype.Service;
import com.crud.model.Product;
import com.crud.util.DBConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ProductStorage {

    public String store(String name, String description, double price, int quantity, LocalDate created_at,
            LocalDate updated_at) {
        String sqlQuery = "INSERT INTO product (id, \"name\", \"description\", price, \"quantity\", created_at, updated_at) VALUES (CAST(? AS UUID), ?, ?, ?, ?, ?, ?) RETURNING id";
        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            String productId = UUID.randomUUID().toString();
            statement.setString(1, productId);
            statement.setString(2, name);
            statement.setString(3, description);
            statement.setDouble(4, price);
            statement.setInt(5, quantity);
            statement.setDate(6, Date.valueOf(created_at));
            statement.setDate(7, updated_at != null ? Date.valueOf(updated_at) : null);

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

    public Product find(String id) {
        String sqlQuery = "SELECT * FROM product WHERE id = CAST(? AS UUID)";
        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setString(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Product product = new Product();
                    product.setId(resultSet.getString("id"));
                    product.setName(resultSet.getString("name"));
                    product.setDescription(resultSet.getString("description"));
                    product.setPrice(resultSet.getDouble("price"));
                    product.setQuantity(resultSet.getInt("quantity"));
                    product.setCreated_at(resultSet.getDate("created_at").toLocalDate());
                    product.setUpdated_at(resultSet.getDate("updated_at") != null
                            ? resultSet.getDate("updated_at").toLocalDate()
                            : null);
                    return product;
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
                product.setQuantity(resultSet.getInt("quantity"));
                product.setCreated_at(resultSet.getDate("created_at").toLocalDate());
                product.setUpdated_at(resultSet.getDate("updated_at") != null
                        ? resultSet.getDate("updated_at").toLocalDate()
                        : null);
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products.toArray(new Product[0]);
    }

    public void update(String id, String name, String description, Double price, Integer quantity, LocalDate updated_at) {
        StringBuilder sqlQuery = new StringBuilder("UPDATE product SET ");
        List<Object> params = new ArrayList<>();

        if (name != null) {
            sqlQuery.append("\"name\" = ?, ");
            params.add(name);
        }
        if (description != null) {
            sqlQuery.append("\"description\" = ?, ");
            params.add(description);
        }
        if (price != null) {
            sqlQuery.append("price = ?, ");
            params.add(price);
        }
        if (quantity != null) {
            sqlQuery.append("\"quantity\" = ?, ");
            params.add(quantity);
        }
        if (updated_at != null) {
            sqlQuery.append("updated_at = ?, ");
            params.add(Date.valueOf(updated_at));
        }

        sqlQuery.setLength(sqlQuery.length() - 2);

        sqlQuery.append(" WHERE id = CAST(? AS UUID)");

        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlQuery.toString())) {
            int index = 1;
            for (Object param : params) {
                statement.setObject(index++, param);
            }
            statement.setString(index, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void delete(String id) {
        String sqlQuery = "DELETE FROM product WHERE id = CAST(? AS UUID)";
        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setString(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}