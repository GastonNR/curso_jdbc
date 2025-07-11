package DAO.impl;

import DAO.AlumnoDAO;
import DAO.DAOException;
import model.Alumno;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AlumnoDAOImpl implements AlumnoDAO {

    final String INSERT = "INSERT INTO alumnos(nombre, apellidos, fecha_nac) VALUES(?, ?, ?)";
    final String UPDATE = "UPDATE alumnos SET nombre = ?, apellidos = ?, fecha_nac = ? WHERE id_alumno = ?";
    final String DELETE = "DELETE FROM alumnos WHERE id_alumno = ? ";
    final String GETALL = "SELECT * FROM alumnos";
    final String GETONE = "SELECT * FROM alumnos WHERE id_alumno = ?";

    private Connection conexion;

    public AlumnoDAOImpl(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public void insertar(Alumno alumno) throws DAOException{
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = conexion.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, alumno.getNombre());
            statement.setString(2, alumno.getApellido());
            statement.setDate(3, java.sql.Date.valueOf(alumno.getFechaNacimiento()));
            if (statement.executeUpdate() == 0) throw new DAOException("Puede que esto no se haya guardado");
            rs = statement.getGeneratedKeys();
            if (rs.next()) {
                alumno.setId(rs.getLong(1));
            } else {
                throw new DAOException("No puedo asignar ID a este alumno.");
            }
        } catch (SQLException ex) {
            throw new DAOException("Error en el SQL ", ex);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    new DAOException("Error en SQL", ex);
                }
            }
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
    public void modificar(Alumno alumno) throws DAOException{
        PreparedStatement statement = null;

        try {
            statement = conexion.prepareStatement(UPDATE);
            statement.setString(1, alumno.getNombre());
            statement.setString(2, alumno.getApellido());
            statement.setDate(3, java.sql.Date.valueOf(alumno.getFechaNacimiento()));
            statement.setLong(4, alumno.getId());
            if (statement.executeUpdate() == 0) throw new DAOException("Error al actualizar los datos del alumno.");

        } catch (SQLException ex) {
            throw new DAOException("Error al actualizar los datos del alumno.");
        }
    }

    @Override
    public void eliminar(Alumno alumno) throws DAOException{
        PreparedStatement statement = null;

        try {
            statement = conexion.prepareStatement(DELETE);
            statement.setLong(1, alumno.getId());
            if (statement.executeUpdate() == 0) throw new DAOException("Puede que el alumno no se haya borrado");

        } catch (SQLException e) {
            throw new DAOException("Error al borrar los datos del alumno.");

        }

    }

    @Override
    public List<Alumno> obtenerTodos() throws DAOException{

        PreparedStatement statement = null;
        ResultSet rs = null;
        List<Alumno> alumnos = new ArrayList<>();

        try {
            statement = conexion.prepareStatement(GETALL);
            rs = statement.executeQuery();
            while (rs.next()) {
                Long id_alumnno = rs.getLong("id_alumno");
                String nombre = rs.getString("nombre");
                String apellidos = rs.getString("apellidos");
                LocalDate fechaNac = rs.getDate("fecha_nac").toLocalDate();

                Alumno alumno = new Alumno(nombre, apellidos, fechaNac);
                alumno.setId(id_alumnno);

                alumnos.add(alumno);
            }

        } catch (SQLException ex) {
            throw new DAOException("Error en SQL", ex);
        }finally {
            if (rs != null){
                try {
                    rs.close();
                }catch (SQLException ex){
                    new DAOException("Error en SQL", ex);
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException ex) {
                    new DAOException("Error en SQL", ex);
                }
            }
        }
        return alumnos;

    }

    @Override
    public Alumno obtener(Long id) throws DAOException{
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Alumno alumno;

        try {
            statement = conexion.prepareStatement(GETONE);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Long id_alumno = resultSet.getLong("id_alumno");
                String nombre = resultSet.getString("nombre");
                String apellidos = resultSet.getString("apellidos");
                LocalDate fecha_nac = resultSet.getDate("fecha_nac").toLocalDate();

                alumno = new Alumno(nombre, apellidos, fecha_nac);
                alumno.setId(id_alumno);

                return alumno;

            } else {
                throw new DAOException("No se encontró ningún alumno con el id: " + id);
            }

        } catch (SQLException ex) {
            throw new DAOException("Error al borrar al alumno: " + ex);

        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();

            } catch (SQLException ex) {
                throw new DAOException("Error al cerrar la consulta");

            }
        }

    }

}
