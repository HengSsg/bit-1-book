package bitedu.bipa.quiz.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class ConnectionManager {

    public static Connection getConnection() {
        Connection con = null;

        Properties prop = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream("/Users/jimin/workspace/bit/bit-1-book/front/db.properties")) {
            prop.load(fileInputStream);
            String jdbcURL = prop.getProperty("jdbcURL");
            String driver = prop.getProperty("driver");
            String id = prop.getProperty("id");
            String pwd = prop.getProperty("pwd");
            Class.forName(driver);
            con = DriverManager.getConnection(jdbcURL, id, pwd);

        } catch (IOException | ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    public static void closeConnection(ResultSet rs, Statement stmt, Connection con) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
