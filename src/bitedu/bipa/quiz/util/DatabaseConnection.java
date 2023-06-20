package bitedu.bipa.quiz.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection { // 싱글톤으로 커넥션 생성
    private static DatabaseConnection instance;
    private Connection connection;

    private DatabaseConnection() {
        // private constructor to prevent instantiation
    }

    public static synchronized DatabaseConnection getInstance() {
        if (instance == null) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public synchronized Connection getConnection() {
        if (connection == null) {
            // 커넥션 생성 로직

            Properties prop = new Properties();
            try (FileInputStream fileInputStream = new FileInputStream("data/db.properties")) {
                prop.load(fileInputStream);
                String jdbcURL = prop.getProperty("jdbcURL");
                String driver = prop.getProperty("driver");
                String id = prop.getProperty("id");
                String pwd = prop.getProperty("pwd");
                Class.forName(driver);
                connection = DriverManager.getConnection(jdbcURL, id, pwd);

            } catch (IOException | ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
