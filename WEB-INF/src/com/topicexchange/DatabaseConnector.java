/**
    @author Foteinos Konstantinos (2023086)
*/

package com.topicexchange;

import java.sql.*;

// https://stackoverflow.com/questions/37909487/how-can-i-connect-to-mariadb-using-java

public class DatabaseConnector {

    private static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/topic_exchange";

    private static final String USER = "root";
    private static final String PASS = "?????????????????????";

    private static Connection singleton = null;

    static {
        try {
            Class.forName(JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private DatabaseConnector() {
    }

    /**
     * The connection is NOT closed automatically.
     */

    public static Connection establishConnection() {
        try {
            return DriverManager.getConnection(DB_URL, USER, PASS);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * The following method was created in order to avoid the multiple connection
     * establishment and save time. Although, we usually close the connection 
     * after its use, so i have marked this as deprecated.
     */

    @Deprecated
    public static Connection establishSingletonConnection() {
        try {
            if (singleton == null)
                singleton = DriverManager.getConnection(DB_URL, USER, PASS);
            return singleton;
        } catch (SQLException e) {
            return null;
        }
    }

}
