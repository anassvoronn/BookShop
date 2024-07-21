package org.nastya.dao.database;

import org.junit.jupiter.api.Test;
import org.nastya.ConnectionFactory.DatabaseConnectionFactory;
import org.nastya.dao.TransactionManager;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DatabaseTransactionManagerTest {

    private final TransactionManager manager = new DatabaseTransactionManager();
    private final DatabaseConnectionFactory factory = new DatabaseConnectionFactory(new ThreadLocal<>());

    @Test
    public void getConnection() throws SQLException {
        Connection connection = factory.getConnection();

        assertNotNull(connection);
        assertFalse(connection.isClosed());
    }

    @Test
    public void getConnectionWithManager() throws SQLException {
        manager.begin();
        Connection connection = factory.getConnection();

        assertNotNull(connection);
        assertFalse(connection.isClosed());

        manager.commit();

        assertNotNull(connection);
        assertTrue(connection.isClosed());

        Connection connection1 = factory.getConnection();

        assertNotNull(connection1);
        assertFalse(connection1.isClosed());
        assertNotEquals(connection, connection1);
    }

}