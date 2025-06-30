package org.example;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {

    private Connection conexion = null;
    public static void main(String[] args) throws SQLException {
        Conexion_db conexion = new Conexion_db();
    }


}