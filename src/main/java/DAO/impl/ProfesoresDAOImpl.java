package DAO.impl;

import DAO.DAOException;
import DAO.ProfesoresDAO;
import model.Profesores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProfesoresDAOImpl implements ProfesoresDAO {

    final String INSERT = "INSERT INTO profesores(nombre, apellidos) VALUES(?, ?)";
    final String UPDATE = "UPDATE profesores SET nombre = ?, apellidos = ? WHERE id_profesor = ?";
    final String DELETE = "DELETE FROM profesores WHERE id_profesor = ?";
    final String GETALL = "SELECT * FROM profesores";
    final String GETONE = "SELECT * FROM profesores WHERE id_profesor = ?";

    private Connection connection;

    public ProfesoresDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insertar(Profesores profesor) throws DAOException {
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(INSERT);
            statement.setString(1, profesor.getNombre());
            statement.setString(2, profesor.getApellidos());
            if (statement.executeUpdate() == 0) throw new DAOException("Puede que los datos no se hayan guardado");

        } catch (SQLException ex) {
            throw new DAOException("Error al registrar al profesor");
        } finally {

            try {
                if (statement != null) statement.close();

            } catch (SQLException ex) {
                throw new DAOException("Error al cerrar la consulta");
            }
        }
    }

    @Override
    public void modificar(Profesores profesor) throws DAOException{
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(INSERT);
            statement.setString(1, profesor.getNombre());
            statement.setString(2, profesor.getApellidos());
            statement.setLong(3, profesor.getId());
            if (statement.executeUpdate() == 0) throw new DAOException("Puede que los datos no se hayan guardado");

        } catch (SQLException ex) {
            throw new DAOException("Error al actualizar los datos");
        } finally {

            try {
                if (statement != null) statement.close();

            } catch (SQLException ex) {
                throw new DAOException("Error al cerrar la consulta");
            }
        }
    }

    @Override
    public void eliminar(Profesores profesor) throws DAOException{
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(INSERT);
            statement.setLong(1, profesor.getId());
            if (statement.executeUpdate() == 0) throw new DAOException("Puede que los datos no se hayan borrado");

        } catch (SQLException ex) {
            throw new DAOException("Error al borrar al profesor");
        } finally {

            try {
                if (statement != null) statement.close();

            } catch (SQLException ex) {
                throw new DAOException("Error al cerrar la consulta");
            }
        }
    }

    @Override
    public List<Profesores> obtenerTodos() throws DAOException{
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Profesores> profesores = new ArrayList<>();

        try {
            statement = connection.prepareStatement(GETALL);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Long id = resultSet.getLong("id_profesor");
                String nombre = resultSet.getString("nombre");
                String apellidos = resultSet.getString("apellidos");

                Profesores profesor = new Profesores(id, nombre, apellidos);
                profesores.add(profesor);

            }
            return profesores;
        } catch (SQLException ex) {
            throw new DAOException("Error al buscar los datos de todos los profesores");
        } finally {
            try {
                if (statement != null) statement.close();

            } catch (SQLException ex) {
                throw new DAOException("Error al cerrar la consulta");
            }

        }

    }

    @Override
    public Profesores obtener(Long id) throws DAOException{

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Profesores profesor = null;

        try {
            statement = connection.prepareStatement(GETONE);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();

            Long id_profesor = resultSet.getLong("id_profesor");
            String nombre = resultSet.getString("nombre");
            String apellidos = resultSet.getString("apellidos");

            profesor = new Profesores(id_profesor, nombre, apellidos);

            return profesor;

        } catch (SQLException ex) {
            throw new DAOException("Error al buscar los datos del profesor");
        } finally {

            try {
                if (statement != null) statement.close();

            } catch (SQLException ex) {
                throw new DAOException("Error al cerrar la consulta");
            }

        }

    }
}
