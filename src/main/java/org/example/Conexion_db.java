package org.example;

import java.sql.*;

public class Conexion_db {

    private Connection conexion = null;

    public Conexion_db() throws SQLException {
        try {
            conectar();
            transaccion();
        } finally {
            cerrar();
        }
    }

    public void conectar() throws SQLException {

        String jdbc = "jdbc:mysql://localhost:3306/cursoJDBC";
        String user = "estudiante";
        String password = "1234";

        conexion = DriverManager.getConnection(jdbc, user, password);
        conexion.setAutoCommit(false);
    }

    public void cerrar() throws SQLException {
        if(conexion != null) conexion.close();
    }

//    public void consulta(String apellido) throws SQLException{
//
//        String consulta = "SELECT id_alumno, nombre, apellidos FROM alumnos WHERE apellidos = ?";
//        PreparedStatement preparedStatement = conexion.prepareStatement(consulta);
//        preparedStatement.setString(1, apellido);
//        ResultSet set = preparedStatement.executeQuery();
//
//        while (set.next()) {
//            int idAlumno = set.getInt("id_alumno");
//            String nombre = set.getString("nombre");
//            String apellidos = set.getString("apellidos");
//            System.out.println("Alumno: " + idAlumno + "\n" + "Nombre y apellido: " + nombre + " " + apellidos + "\n");
//        }
//
//        set.close();
//        preparedStatement.close();
//
//    }
    public void transaccion() throws SQLException{

        final String PROFESOR = "INSERT INTO profesores(id_profesor, nombre, apellidos) VALUES(?, ?, ?)";
        final String ASIGNATURA = "INSERT INTO asignaturas(id_asignatura, nombre, profesor) VALUES(?, ?, ?)";
        PreparedStatement profesor = null, asignatura = null;

        try {

            profesor = conexion.prepareStatement(PROFESOR);
            profesor.setInt(1, 50);
            profesor.setString(2, "Pepe");
            profesor.setString(3, "Pérez");
            profesor.executeUpdate();

            asignatura = conexion.prepareStatement(ASIGNATURA);
            asignatura.setInt(1, 100);
            asignatura.setString(2, "Fundamentos de las bases de datos.");
            asignatura.setInt(3, 50);
            asignatura.executeUpdate();

            conexion.commit();
            System.out.println("Commit ejecutado");

        } catch (SQLException e) {

            conexion.rollback();
            e.printStackTrace();

        } finally {

            if (profesor != null) profesor.close();
            if (asignatura != null) asignatura.close();

        }
    }
}
