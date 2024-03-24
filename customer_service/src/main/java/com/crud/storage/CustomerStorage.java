package com.crud.storage;

import org.springframework.stereotype.Service;
import com.crud.model.Customer;
import com.crud.util.DBConnection;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CustomerStorage {

    public String store(String name, String surname, String email, String password, LocalDate birthdate, LocalDate created_at,
            LocalDate updated_at) {
        String sqlQuery = "INSERT INTO customer (id, name, surname, email, password, birthdate, created_at, updated_at) VALUES (CAST(? AS UUID), ?, ?, ?, ?, ?, ?, ?) RETURNING id";
        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            String customerId = UUID.randomUUID().toString();
            statement.setString(1, customerId);
            statement.setString(2, name);
            statement.setString(3, surname);
            statement.setString(4, email);
            statement.setString(5, password);
            statement.setDate(6, Date.valueOf(birthdate));
            statement.setDate(7, Date.valueOf(created_at));
            statement.setDate(8, updated_at != null ? Date.valueOf(updated_at) : null);

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

    public Customer find(String id) {
        String sqlQuery = "SELECT * FROM customer WHERE id = CAST(? AS UUID)";
        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setString(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Customer customer = new Customer();
                    customer.setId(resultSet.getString("id"));
                    customer.setName(resultSet.getString("name"));
                    customer.setEmail(resultSet.getString("email"));
                    customer.setBirthdate(resultSet.getDate("birthdate").toLocalDate());
                    customer.setCreated_at(resultSet.getDate("created_at").toLocalDate());
                    customer.setUpdated_at(resultSet.getDate("updated_at") != null
                            ? resultSet.getDate("updated_at").toLocalDate()
                            : null);
                    return customer;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Customer[] list() {
        List<Customer> customers = new ArrayList<>();
        String sqlQuery = "SELECT * FROM customer";

        try (Connection connection = DBConnection.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sqlQuery)) {

            while (resultSet.next()) {
                Customer customer = new Customer();
                customer.setId(resultSet.getString("id"));
                customer.setName(resultSet.getString("name"));
                customer.setEmail(resultSet.getString("email"));
                customer.setBirthdate(resultSet.getDate("birthdate").toLocalDate());
                customer.setCreated_at(resultSet.getDate("created_at").toLocalDate());
                customer.setUpdated_at(resultSet.getDate("updated_at") != null
                        ? resultSet.getDate("updated_at").toLocalDate()
                        : null);
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers.toArray(new Customer[0]);
    }

    public void update(String id, String name, String email, LocalDate birthdate, LocalDate updated_at) {
        StringBuilder sqlQuery = new StringBuilder("UPDATE customer SET ");
        List<Object> params = new ArrayList<>();

        if (name != null) {
            sqlQuery.append("name = ?, ");
            params.add(name);
        }
        if (email != null) {
            sqlQuery.append("email = ?, ");
            params.add(email);
        }
        if (birthdate != null) {
            sqlQuery.append("birthdate = ?, ");
            params.add(Date.valueOf(birthdate));
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
        String sqlQuery = "DELETE FROM customer WHERE id = CAST(? AS UUID)";
        try (Connection connection = DBConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setString(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}