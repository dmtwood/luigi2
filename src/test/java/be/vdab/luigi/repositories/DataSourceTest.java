package be.vdab.luigi.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@JdbcTest
// creates IOC container >> spring loads DataSource-bean & JDBCTemplate-bean
class DataSourceTest {
    // DataSource is a Java lib to produce objects to use connection with DB
    private final DataSource dataSource;

    // inject the bean that needs testing: the DataSource-bean
    DataSourceTest(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Test
    void getConnection() throws SQLException {
        // try the connection of the bean
        try (Connection connection = dataSource.getConnection()){
        }
    }
}
