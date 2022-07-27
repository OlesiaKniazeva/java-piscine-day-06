package edu.school21.repositories;

import edu.school21.models.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductRepositoryLdbcImpl {
    private Connection connection;

    ProductRepositoryLdbcImpl(DataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();
    }

    public List<Product> findAll() throws SQLException {
        String sql = "SELECT * FROM shop";
        List<Product> productList = new ArrayList<>();

        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    Product pr = new Product(resultSet.getLong("identifier"), resultSet.getString("name"),
                            resultSet.getInt("price"));
                    productList.add(pr);
                }
            }
        }
        return productList;
    }

    public Optional<Product> findById(Long id) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery("SELECT * FROM shop WHERE identifier=" + id)) {
                if (!resultSet.next()) {
                    return Optional.empty();
                }
                return Optional.of(new Product(resultSet.getLong("identifier"), resultSet.getString("name"),
                        resultSet.getInt("price")));
            }
        }
    }


    public void update(Product product) throws SQLException {
             String sql = "UPDATE shop SET name = ?, price = ? WHERE identifier = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.getName());
            statement.setInt(2, product.getPrice());
            statement.setInt(3, (int) product.getIdentifier());
            statement.executeUpdate();
        }
    }


    public void save(Product product) throws SQLException {
        String sql = "INSERT INTO shop(name, price) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, product.getName());
            statement.setInt(2, product.getPrice());

            statement.executeUpdate();
        }
    }

    public void delete (Long id) throws SQLException {
        String sql = "DELETE FROM shop WHERE identifier = " + id;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
