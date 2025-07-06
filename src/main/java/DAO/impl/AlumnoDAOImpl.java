package DAO.impl;

import DAO.AlumnoDAO;
import DAO.DAOException;
import model.Alumno;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class AlumnoDAOImpl implements AlumnoDAO {

    final String INSERT = "INSERT INTO alumnos(nombre, apellidos, fecha_nac) VALUES(?, ?, ?)";
    final String UPDATE = "UPDATE alumnos SET nombre = ?, apellidos = ?, fecha_nac = ? WHERE id_alumno = ?";
    final String DELETE = "DELETE FROM alumnos WHERE id_alumno = ? ";
    final String GETALL = "SELECT * FROM alumnos";
    final String GETONE = "SELECT alumno FROM alumnos WHERE id_alumno = ?";

    private Connection conexion;

    public AlumnoDAOImpl(Connection conexion) {
        this.conexion = conexion;
    }

    @Override
    public void insertar(Alumno a) throws DAOException{
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = conexion.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, a.getNombre());
            statement.setString(2, a.getApellido());
            statement.setDate(3, java.sql.Date.valueOf(a.getFechaNacimiento()));
            if (statement.executeUpdate() == 0) throw new DAOException("Puede que esto no se haya guardado");
            rs = statement.getGeneratedKeys();
            if (rs.next()) {
                a.setId(rs.getLong(1));
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
    public void modificar(Alumno a) throws DAOException{
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = conexion.prepareStatement(UPDATE);
            statement.setString(1, a.getNombre());
            statement.setString(2, a.getApellido());
            statement.setDate(3, java.sql.Date.valueOf(a.getFechaNacimiento()));
            statement.setLong(4, a.getId());
            if (statement.executeUpdate() == 0) throw new DAOException("Error al actualizar los datos del alumno.");

        } catch (SQLException ex) {
            throw new DAOException("Error al actualizar los datos del alumno.");
        }
    }

    @Override
    public void eliminar(Alumno a) throws DAOException{
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            statement = conexion.prepareStatement(DELETE);
            statement.setLong(1, a.getId());

        } catch (SQLException e) {
            throw new DAOException("Error al borrar los datos del alumno.");
        }

    }

    private Alumno convertir(ResultSet rs) throws SQLException{

        String nombre = rs.getString("nombre");
        String apellidos = rs.getString("apellidos");
        LocalDate fechaNac = rs.getDate("fecha_nac").toLocalDate();
        Alumno alumno = new Alumno(nombre, apellidos, fechaNac);
        return alumno;
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
                alumnos.add(convertir(rs));
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
        ResultSet rs = null;
        Alumno alumno = null;
        try {
            statement = conexion.prepareStatement(GETONE);
            statement.setLong(1, id);
            rs = statement.executeQuery();
            if (rs.next()) {
                alumno = convertir(rs);
            } else {
                throw new DAOException("No se ha encontrado ese registro");
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
        return alumno;
    }
    public static void main(String[] args) throws SQLException, DAOException {
        DAOManagerImpl daoManager = new DAOManagerImpl("localhost", "estudiante", "1234", "cursoJDBC");
        String nombre = "Marcos";
        String apellidos = "Perez";
        LocalDate fecha_nac = LocalDate.of(1988, 8, 20);

        Alumno alumno = new Alumno(nombre, apellidos, fecha_nac);
        daoManager.getAlumnoDAO().insertar(alumno);
    }
}
