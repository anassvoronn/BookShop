package org.nastya.ConnectionFactory;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DatabaseConnectionFactoryTest {
    private final DatabaseConnectionFactory factory = new DatabaseConnectionFactory();
    private Connection connection1;
    private Connection connection2;

    @AfterEach
    void tearDown() {
        connection1 = null;
        connection2 = null;
    }

    @Test
    void getConnection_sameThread() {
        factory.readingFromFile();
        connection1 = factory.getConnection();
        connection2 = factory.getConnection();
        assertNotNull(connection1);
        assertNotNull(connection2);
        assertEquals(connection1, connection2);
    }

    @Test
    void getConnection_2Threads() throws InterruptedException {
        factory.readingFromFile();
        connection1 = factory.getConnection();

        Thread t2 = new Thread(() -> connection2 = factory.getConnection());
        t2.start();
        t2.join();

        assertNotNull(connection1);
        assertNotNull(connection2);
        assertNotEquals(connection1, connection2);
    }
}