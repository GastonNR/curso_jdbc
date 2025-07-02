package DAO.impl;

import DAO.ProfesoresDAO;
import model.Profesores;

import java.sql.Connection;
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
    public void insertar(Profesores a) {

    }

    @Override
    public void modificar(Profesores a) {

    }

    @Override
    public void eliminar(Profesores a) {

    }

    @Override
    public List<Profesores> obtenerTodos() {
        return List.of();
    }

    @Override
    public Profesores obtener(Long id) {
        return null;
    }
}
