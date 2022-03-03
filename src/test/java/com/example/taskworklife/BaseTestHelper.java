package com.example.taskworklife;

import org.testcontainers.containers.MySQLContainer;

public class BaseTestHelper {
    static MySQLContainer mySQLContainer = new MySQLContainer("mysql:latest").withDatabaseName("reservering-test").withUsername("testuser").withPassword("pass");

    static {
        mySQLContainer.start();
    }
}
