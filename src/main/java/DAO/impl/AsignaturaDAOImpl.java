package DAO.impl;

import DAO.AsignaturaDAO;
import model.Asignatura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

public class AsignaturaDAOImpl implements AsignaturaDAO {

    final String INSERT = "INSERT INTO asignatura(nombre, profesor) VALUES(?, ?)";
    final String UPDATE = "UPDATE asignatura SET nombre = ?, profesor = ? WHERE id_asignatura = ?";
    final String DELETE = "DELETE FROM asignatura WHERE id_asignatura";
    final String GETALL = "SELECT * FROM asignaturas";
    final String GETONE = "SELECT * FROM asignaturas WHERE id_asignatura = ?";

    private Connection connection;

    public AsignaturaDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insertar(Asignatura asignatura) {

    }

    @Override
    public void modificar(Asignatura asignatura) {

    }

    @Override
    public void eliminar(Asignatura asignatura) {

    }

    @Override
    public List<Asignatura> obtenerTodos() {
        return List.of();
    }

    @Override
    public Asignatura obtener(Long id) {
        return null;
    }
}
