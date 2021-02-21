package se.lexicon.Db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    private static String URL = "jdbc:mysql://localhost:3306/todoit";
    private static String USERNAME = "root";
    private static String PASSWORD = "1234";

    public MySQLConnection() {
    }

    public static Connection getConnection() {
        Connection connection = null;

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("#### MySQL Connection is established ####");
            if (connection != null) System.out.println("The connection to database is successfully established");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;


    }
}
