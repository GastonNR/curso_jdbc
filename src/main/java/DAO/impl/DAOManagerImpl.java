package DAO.impl;

import DAO.*;

import javax.imageio.plugins.tiff.TIFFDirectory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAOManagerImpl implements DAOManager {

    private Connection connection;

    private AlumnoDAO alumnos = null;
    private ProfesoresDAO profesores = null;
    private MatriculaDAO matricula = null;
    private AsignaturaDAO asignatura = null;

    public DAOManagerImpl(String host, String username, String password, String database) throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:mysql://" + host + "/" + database, username, password);
    }


    @Override
    public AlumnoDAO getAlumnoDAO() {
        if (alumnos == null) alumnos = new AlumnoDAOImpl(connection);
        return alumnos;
    }

    @Override
    public AsignaturaDAO getAsignaturaDAO() {
        if (asignatura == null) asignatura = new AsignaturaDAOImpl(connection);
        return asignatura;
    }

    @Override
    public MatriculaDAO getMatriculaDAO() {
        if (matricula == null) matricula = new MatriculaDAOImpl(connection);
        return matricula;
    }

    @Override
    public ProfesoresDAO getProfesoresDAO() {
        if (profesores == null) profesores = new ProfesoresDAOImpl(connection);
        return profesores;
    }
}
