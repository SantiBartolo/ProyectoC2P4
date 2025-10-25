package org.example.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private static final String URL = "jdbc:sqlite:E:/CIAF/CIAF 5/Desktop/PR4C2/BDC2/BDPY2";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

}
