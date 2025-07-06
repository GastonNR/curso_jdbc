package DAO.impl;

import DAO.DAOException;
import DAO.MatriculaDAO;
import model.Matricula;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MatriculaDAOImpl implements MatriculaDAO {

    final String INSERT = "INSERT INTO matriculas(alumno, asignatura, fecha, nota) VALUES(?, ?, ?, ?)";
    final String UPDATE = "UPDATE matriculas SET alumno = ?, asignatura = ?, fecha = ?, nota = ?";
    final String DELETE = "DELETE FROM matriculas WHERE alumno = ?";
    final String GETALL = "SELECT * FROM matriculas";
    final String GETONE = "SELECT * FROM maticulas WHERE alumno = ?";
    final String GETALUMNO = "SELECT * FROM matricula WHERE alumno = ?";
    final String GETASIGNATURA = "SELECT * FROM matricula WHERE asignatura = ?";
    final String GETYEAR = "SELECT * FROM matricula WHERE fecha = ?";

    private Connection connection;

    public MatriculaDAOImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void insertar(Matricula matricula) throws DAOException{
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(INSERT);
            statement.setLong(1, matricula.getId().getAlumno());
            statement.setLong(2, matricula.getId().getAsignatura());
            statement.setInt(3, matricula.getId().getYear());
            statement.setInt(4, matricula.getNota());
            if (statement.executeUpdate() == 0) throw new DAOException("Puede que la matricula no se haya registrado");

        } catch (SQLException ex) {
            throw new DAOException("Error al registrar la matricula");

        } finally {
            try {
                if (statement != null) statement.close();

            } catch (SQLException ex) {
                throw new DAOException("Error al cerrar la consulta");

            }
        }
    }

    @Override
    public void modificar(Matricula matricula) throws DAOException{
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(UPDATE);
            statement.setLong(1, matricula.getId().getAlumno());
            statement.setLong(2, matricula.getId().getAsignatura());
            statement.setInt(3, matricula.getId().getYear());
            statement.setInt(4, matricula.getNota());
            if (statement.executeUpdate() == 0) throw new DAOException("Puede que los datos no se hayan actualizado");

        } catch (SQLException ex) {
            throw new DAOException("Error al registrar los cambios");
        } finally {
            try {
                if (statement != null) statement.close();

            } catch (SQLException ex) {
                throw new DAOException("Error al cerrar la consulta");

            }
        }
    }

    @Override
    public void eliminar(Matricula matricula) throws DAOException {
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(DELETE);
            statement.setLong(1, matricula.getId().getAlumno());
            if (statement.executeUpdate() == 0) throw new DAOException("Puede que la matricula no se haya borrado");

        } catch (SQLException ex) {
            throw new DAOException("Error al borrar la matricula");
        } finally {
            try {
                if (statement != null) statement.close();

            } catch (SQLException ex) {
                throw new DAOException("Error al cerrar la consulta");

            }
        }
    }

    @Override
    public List<Matricula> obtenerTodos() throws DAOException{
        PreparedStatement statement = null;
        ResultSet rs = null;
        List<Matricula> matriculas = new ArrayList<>();

        try {
            statement = connection.prepareStatement(GETALL);
            rs = statement.executeQuery();

            while (rs.next()) {
                Long id_alumno = rs.getLong("alumno");
                Long id_asignatura = rs.getLong("asignatura");
                Integer year = rs.getInt("year");
                Integer nota = rs.getInt("nota");
                Matricula matricula = new Matricula(id_alumno, id_asignatura, year);
                matricula.setNota(nota);
                matriculas.add(matricula);

            }
            return matriculas;

        } catch (SQLException ex) {
            throw new DAOException("Error al obtener las matriculas");

        }finally {
            try {
                if (rs != null) rs.close();
                if (statement != null) statement.close();

            } catch (SQLException ex) {
                throw new DAOException("Error al cerrar la consulta");

            }

        }

    }

    @Override
    public Matricula obtener(Matricula.IdMatricula id) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Matricula matricula = null;

        try {
            statement = connection.prepareStatement(GETONE);
            statement.setLong(1, id.getAlumno());
            resultSet = statement.executeQuery();

            Long alumno = resultSet.getLong("alumno");
            Long asignatura = resultSet.getLong("asignatura");
            Integer fecha = resultSet.getInt("fecha");
            Integer nota = resultSet.getInt("nota");

            matricula = new Matricula(alumno, asignatura, fecha);
            matricula.setNota(nota);

            return matricula;

        } catch (SQLException ex) {
            throw new DAOException("Error al obtener los datos de la matricula");

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
    public List<Matricula> obtenerPorAlumno(long alumno) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Matricula> matriculas = new ArrayList<>();

        try {
            statement = connection.prepareStatement(GETALUMNO);
            statement.setLong(1, alumno);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Long id_alumno = resultSet.getLong("alumno");
                Long asignatura = resultSet.getLong("asignatura");
                Integer fecha = resultSet.getInt("fecha");
                Integer nota = resultSet.getInt("nota");

                Matricula matricula = new Matricula(id_alumno, asignatura, fecha);
                matricula.setNota(nota);

                matriculas.add(matricula);
            }

            return matriculas;

        } catch (SQLException ex) {
            throw new DAOException("Error al obtener los datos de la matricula");

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
    public List<Matricula> obtenerPorAsignatura(long asignatura) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Matricula> matriculas = new ArrayList<>();

        try {
            statement = connection.prepareStatement(GETASIGNATURA);
            statement.setLong(1, asignatura);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Long id_alumno = resultSet.getLong("alumno");
                Long id_asignatura = resultSet.getLong("asignatura");
                Integer fecha = resultSet.getInt("fecha");
                Integer nota = resultSet.getInt("nota");

                Matricula matricula = new Matricula(id_alumno, id_asignatura, fecha);
                matricula.setNota(nota);

                matriculas.add(matricula);
            }

            return matriculas;

        } catch (SQLException ex) {
            throw new DAOException("Error al obtener los datos de la matricula");

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
    public List<Matricula> obtenerPorCurso(int curso) throws DAOException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Matricula> matriculas = new ArrayList<>();

        try {
            statement = connection.prepareStatement(GETYEAR);
            statement.setInt(1, curso);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Long id_alumno = resultSet.getLong("alumno");
                Long asignatura = resultSet.getLong("asignatura");
                Integer fecha = resultSet.getInt("fecha");
                Integer nota = resultSet.getInt("nota");

                Matricula matricula = new Matricula(id_alumno, asignatura, fecha);
                matricula.setNota(nota);

                matriculas.add(matricula);
            }

            return matriculas;

        } catch (SQLException ex) {
            throw new DAOException("Error al obtener los datos de la matricula");

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
