package DAO.impl;

import DAO.AlumnoDAO;
import DAO.DAOException;
import model.Alumno;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class AlumnoDAOImpl implements AlumnoDAO {

    final String INSERT = "INSERT INTO alumnos(id-alumno, nombre, apellidos, fecha_nac) VALUES(?, ?, ?, ?)";
    final String UPDATE = "UPDATE alumnos SET nombre = ?, apellidos = ?, fecha_nac = ? WHERE id_alumno = ?";
    final String DELETE = "DELETE FROM alumnos WHERE id_alumno = ? ";
    final String GETALL = "SELECT * FROM alumnos";
    final String GETONE = "SELCT alumno FROM alumnos WHERE id_alumno = ?";

    private Connection conexion;

    public AlumnoDAOImpl(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public void insertar(Alumno a) throws DAOException{
        PreparedStatement statement = null;

        try {

            statement = conexion.prepareStatement(INSERT);
            statement.setLong(1, a.getId());
            statement.setString(2, a.getNombre());
            statement.setString(3, a.getApellido());
            statement.setDate(4, new Date(a.getFechaNacimiento().getTime()));
            statement.executeUpdate();

        } catch (SQLException ex) {
            throw new DAOException("Error en el SQL ", ex);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex){
                    throw new DAOException("Error en el SQL", ex);
                }
            }
        }
    }

    @Override
    public void modificar(Alumno a) {

    }

    @Override
    public void eliminar(Alumno a) {

    }

    @Override
    public List<Alumno> obtenerTodos() {
        return List.of();
    }

    @Override
    public Alumno obtener(Long id) {
        return null;
    }
}
