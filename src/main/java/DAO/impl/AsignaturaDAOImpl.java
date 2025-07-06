package DAO.impl;

import DAO.AsignaturaDAO;
import DAO.DAOException;
import model.Asignatura;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AsignaturaDAOImpl implements AsignaturaDAO {

    final String INSERT = "INSERT INTO asignaturas(nombre, profesor) VALUES(?, ?)";
    final String UPDATE = "UPDATE asignaturas SET nombre = ?, profesor = ? WHERE id_asignatura = ?";
    final String DELETE = "DELETE FROM asignaturas WHERE id_asignatura = ?";
    final String GETALL = "SELECT * FROM asignaturas";
    final String GETONE = "SELECT * FROM asignaturas WHERE id_asignatura = ?";

    private Connection connection;

    public AsignaturaDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insertar(Asignatura asignatura) throws DAOException{

        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(INSERT);
            statement.setString(1, asignatura.getNombre());
            statement.setLong(2, asignatura.getIdProfesor());
            if (statement.executeUpdate() == 0) throw new DAOException("Puede que no se hayan guardado los datos");
        } catch (SQLException ex) {
            throw new DAOException("Error al registrar la asignatura");
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException ex) {
                throw new DAOException("Error al cerrar el statement" + ex);
            }
        }
    }

    @Override
    public void modificar(Asignatura asignatura) throws DAOException{
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(UPDATE);
            statement.setString(1, asignatura.getNombre());
            statement.setLong(2, asignatura.getIdProfesor());
            statement.setLong(3, asignatura.getId());
            if (statement.executeUpdate() == 0) throw new DAOException("Error al guardar los datos.");
        } catch (SQLException ex){
            throw new DAOException("Error al actualizar la asignatura");
        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException ex){
                throw new DAOException("Error al cerrar la consulta");
            }
        }
    }

    @Override
    public void eliminar(Asignatura asignatura) throws DAOException{

        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(DELETE);
            statement.setLong(1, asignatura.getId());
            if (statement.executeUpdate() == 0) throw new DAOException("Puede que la asignatura no se haya borrado");

        } catch (SQLException ex){
            throw new DAOException("Error al borrar la asignatura");

        } finally {
            try {
                if (statement != null) statement.close();
            } catch (SQLException ex) {
                throw new DAOException("Error al cerrar la consulta");
            }

        }
    }

    @Override
    public List<Asignatura> obtenerTodos() throws DAOException{

        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Asignatura> asignaturas = new ArrayList<>();

        try {

            statement = connection.prepareStatement(GETALL);
            resultSet = statement.executeQuery();

            while (resultSet.next()){
                Long id = resultSet.getLong("id_asignatura");
                String nombre = resultSet.getString("nombre");
                Long profesor = resultSet.getLong("profesor");
                Asignatura asignatura = new Asignatura(nombre, profesor);
                asignatura.setId(id);
                asignaturas.add(asignatura);

            }

            return asignaturas;

        } catch (SQLException ex) {
            throw new DAOException("Error al obtener todos las asignaturas");
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
            } catch (SQLException ex) {
                throw new DAOException("Error al cerrar la consulta");
            }
        }

    }

    @Override
    public Asignatura obtener(Long id) throws DAOException{
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Asignatura asignatura = null;

        try {
            statement = connection.prepareStatement(GETONE);
            statement.setLong(1, id);
            resultSet = statement.executeQuery();

            Long id_asignatura = resultSet.getLong("id_asignatura");
            String nombre = resultSet.getString("nombre");
            Long profesor = resultSet.getLong("profesor");
            asignatura = new Asignatura(nombre, profesor);
            asignatura.setId(id_asignatura);

            return asignatura;

        } catch (SQLException ex) {
            throw new DAOException("Error al obtener la asignatura");
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
