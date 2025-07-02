package DAO.impl;

import DAO.DAOException;
import DAO.MatriculaDAO;
import model.Matricula;

import java.sql.Connection;
import java.util.List;

public class MatriculaDAOImpl implements MatriculaDAO {

    final String INSERT = "INSERT INTO matriculas(alumno, asignatura, fecha, nota) VALUES(?, ?, ?, ?)";
    final String UPDATE = "UPDATE matriculas SET alumno = ?, asignatura = ?, fecha = ?, nota = ?";
    final String DELETE = "DELETE FROM matriculas WHERE alumno = ?";
    final String GETALUMNO = "SELECT * FROM matricula WHERE alumno = ?";
    final String GETASIGNATURA = "SELECT * FROM matricula WHERE asignatura = ?";
    final String GETYEAR = "SELECT * FROM matricula WHERE fecha = ?";

    private Connection connection;

    public MatriculaDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insertar(Matricula a) {

    }

    @Override
    public void modificar(Matricula a) {

    }

    @Override
    public void eliminar(Matricula a) {

    }

    @Override
    public List<Matricula> obtenerTodos() {
        return List.of();
    }

    @Override
    public Matricula obtener(Matricula.IdMatricula id) throws DAOException {
        return null;
    }

    //@Override
    //public Matricula obtener(Long id) {
    //   return null;
    //}

    @Override
    public List<Matricula> obtenerPorAlumno(long alumno) throws DAOException {
        return List.of();
    }

    @Override
    public List<Matricula> obtenerPorAsignatura(long asignatura) throws DAOException {
        return List.of();
    }

    @Override
    public List<Matricula> obtenerPorCurso(int curso) throws DAOException {
        return List.of();
    }
}
