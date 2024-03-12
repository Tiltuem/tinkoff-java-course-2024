package edu.java.scrapper;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;
import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
public class IntegrationTest extends IntegrationEnvironment {
    @Test
    @DisplayName("tableExistsTest")
    void tableExistsTest() throws SQLException {
        Connection connection =
            DriverManager.getConnection(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword());

        DatabaseMetaData metaData = connection.getMetaData();
        List<ResultSet> resultSetList = new ArrayList<>();
        resultSetList.add(metaData.getTables(null, null, "users", null));
        resultSetList.add(metaData.getTables(null, null, "links", null));
        resultSetList.add(metaData.getTables(null, null, "sites", null));
        resultSetList.add(metaData.getTables(null, null, "user_links", null));

        for (ResultSet resultSet : resultSetList) {
            assertThat(resultSet.next()).isTrue();
        }
    }

    @Test
    @DisplayName("testExistingData")
    void testExistingData() throws SQLException {
        Connection connection =
            DriverManager.getConnection(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword());
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) AS count_users FROM users");
        resultSet.next();
        assertThat(resultSet.getInt("count_users")).isEqualTo(3);
    }
}
