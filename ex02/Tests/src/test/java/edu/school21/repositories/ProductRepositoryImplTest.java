package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;

public class ProductRepositoryImplTest {
    final List<Product> EXPECTED_FIND_ALL_PRODUCTS = Arrays.asList(
            new Product(0L, "lemon", 120),
            new Product(1L, "corn", 100),
            new Product(2L, "potato", 60),
            new Product(3L, "tomates", 90),
            new Product(4L, "apples", 40),
            new Product(5L, "oranges", 70)
    );

    final List<Product> EXPECTED_DELETE_PRODUCT = Arrays.asList(
            new Product(0L, "lemon", 120),
            new Product(2L, "potato", 60),
            new Product(3L, "tomates", 90),
            new Product(4L, "apples", 40),
            new Product(5L, "oranges", 70)
    );

    final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(2L, "potato", 60);
    final Product EXPECTED_UPDATED_PRODUCT = new Product(4L, "apples", 80);
    final Product EXPECTED_SAVE_PRODUCT = new Product(6L, "apricots", 122);

    private EmbeddedDatabase dataBase;
    private ProductRepositoryLdbcImpl repo;

    @BeforeEach
    void init() throws SQLException {
        dataBase = new EmbeddedDatabaseBuilder()
                .setName("Test")
                .setType(HSQL)
                .addScript("/schema.sql")
                .addScript("/data.sql")
                .build();

        repo = new ProductRepositoryLdbcImpl(dataBase);
    }

    @Test
    void testFindAll() throws SQLException {
        Assertions.assertEquals(EXPECTED_FIND_ALL_PRODUCTS, repo.findAll());
    }

    @Test
    void testFindById() throws SQLException {
        Assertions.assertEquals(EXPECTED_FIND_BY_ID_PRODUCT, repo.findById(2L).get());
    }

    @Test
    void testUpdate() throws SQLException {
        repo.update(EXPECTED_UPDATED_PRODUCT);
        Assertions.assertEquals(EXPECTED_UPDATED_PRODUCT, repo.findById(4L).get());
    }

    @Test
    void testSave() throws SQLException {
        repo.save(EXPECTED_SAVE_PRODUCT);
        Assertions.assertEquals(repo.findById(6L).get(), EXPECTED_SAVE_PRODUCT);
    }

    @Test
    void testDelete() throws SQLException {
        repo.delete(1L);
        Assertions.assertEquals(EXPECTED_DELETE_PRODUCT, repo.findAll());
    }

    @AfterEach
    void close() throws SQLException {
        dataBase.getConnection().close();
        dataBase.shutdown();
    }

}
