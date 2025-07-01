package DAO;

import model.Alumno;

public interface DAOManager {

    AlumnoDAO getAlumnoDAO();
    AsignaturaDAO getAsignaturaDAO();
    MatriculaDAO getMatriculaDAO();
    ProfesoresDAO getProfesoresDAO();

}
