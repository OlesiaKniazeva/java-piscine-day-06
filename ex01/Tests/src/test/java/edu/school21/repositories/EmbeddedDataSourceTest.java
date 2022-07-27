package edu.school21.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.HSQL;

import java.sql.SQLException;

public class EmbeddedDataSourceTest {
    private EmbeddedDatabase dataBase;

    @BeforeEach
    void init()
    {
        dataBase = new EmbeddedDatabaseBuilder()
                .setName("Test")
                .setType(HSQL)
                .addScript("/schema.sql")
                .addScript("/data.sql")
                .build();
    }

    @Test
    void testConnection() throws SQLException {
        Assertions.assertNotNull(dataBase.getConnection());
    }

    @AfterEach
    void close() throws SQLException {
        dataBase.getConnection().close();
        dataBase.shutdown();
    }

}
