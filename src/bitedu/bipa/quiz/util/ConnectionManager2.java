package bitedu.bipa.quiz.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class ConnectionManager2 {
    public static Connection connection;

    public static Connection getConnection() {
        Connection con = ConnectionManager2.connection;

            Properties prop = new Properties();


            try (InputStream input = ConnectionManager2.class
                    .getResourceAsStream("data/db.properties")) {
                prop.load(input);
                String jdbcURL = prop.getProperty("jdbcURL");
                String driver = prop.getProperty("driver");
                String id = prop.getProperty("id");
                String pwd = prop.getProperty("pwd");

                Class.forName(driver);
                con = DriverManager.getConnection(jdbcURL, id, pwd);
            } catch (ClassNotFoundException | SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
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
