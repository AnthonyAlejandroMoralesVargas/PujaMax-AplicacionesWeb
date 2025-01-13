package model.bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnection {
    private static Connection conn = null;

    private DBConnection() {
        String servidor = "localhost:3306";
        String database = "bidmax";
        String usuario = "root";
        String password = "";
        String url = "jdbc:mysql://" + servidor + "/" + database;

        // 1.- Me conecto con el Driver
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            conn = DriverManager.getConnection(url, usuario, password);

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        if (conn == null) {
            new DBConnection();
        }
        return conn;
    }

    public static void close(ResultSet rs) {
        try {
            rs.close();
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        }
        rs = null;

    }

    public static void close(PreparedStatement pstmt) {
        try {
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        }
    }

    public static void close() {
        if (conn != null) {
            try {
                conn.close();
                conn = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
