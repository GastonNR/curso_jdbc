package controller;

import DAO.DAOException;
import model.Alumno;
import model.ServicioDeDatos;
import view.ListaAlumnosFrame;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class ControladorGeneral implements ActionListener {
    private ListaAlumnosFrame listaAlumnosFrame;
    private ServicioDeDatos servicio;

    public ControladorGeneral() {
        this.listaAlumnosFrame = new ListaAlumnosFrame();
        this.servicio = new ServicioDeDatos();
        listaAlumnosFrame.setVisible(true);

        listaAlumnosFrame.getBtn_nuevo().addActionListener(this);
        listaAlumnosFrame.getBtn_editar().addActionListener(this);
        listaAlumnosFrame.getBtn_borrar().addActionListener(this);
        listaAlumnosFrame.getBtn_guardar().addActionListener(this);
        listaAlumnosFrame.getBtn_cancelar().addActionListener(this);

        try{
            cargarListaAlumnos();

        }catch (DAOException ex){
            ex.printStackTrace();

        }
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == listaAlumnosFrame.getBtn_nuevo()) {
            try {
                registrarAlumno();
            } catch (DAOException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (e.getSource() == listaAlumnosFrame.getBtn_editar()) {
            try {
                actualizarAlumno();
            } catch (DAOException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (e.getSource() == listaAlumnosFrame.getBtn_borrar()) borrarAlumno();
        if (e.getSource() == listaAlumnosFrame.getBtn_guardar()) guardarAlumno();
        if (e.getSource() == listaAlumnosFrame.getBtn_cancelar()) cancelar();
    }
    private void registrarAlumno() throws DAOException {
        String nombre = listaAlumnosFrame.getTxt_nombre().getText();
        String apellidos = listaAlumnosFrame.getTxt_apellidos().getText();
        LocalDate fechaNac = LocalDate.parse(listaAlumnosFrame.getTxt_fechaNac().getText());
        Alumno nuevoAlumno = new Alumno(nombre, apellidos, fechaNac);

        servicio.getDaoManager().getAlumnoDAO().insertar(nuevoAlumno);

    }

    private void actualizarAlumno() throws DAOException{
        String nombre = listaAlumnosFrame.getTxt_nombre().getText();
        String apellidos = listaAlumnosFrame.getTxt_apellidos().getText();
        LocalDate fechaNac = LocalDate.parse(listaAlumnosFrame.getTxt_fechaNac().getText());
        Alumno alumnoActualizado = new Alumno(nombre, apellidos, fechaNac);

        servicio.getDaoManager().getAlumnoDAO().modificar(alumnoActualizado);
    }

    private void borrarAlumno() {
    }

    private void guardarAlumno() {
    }

    private void cancelar() {
    }

    private void cargarListaAlumnos() throws DAOException {
        String[] columnas ={"ID", "Nombre", "Apellidos", "Fecha de nacimiento"};
        DefaultTableModel modelo_tabla = new DefaultTableModel(columnas, 0);

        List<Alumno> listaAlumnos = servicio.getDaoManager().getAlumnoDAO().obtenerTodos();

        for (Alumno alumno : listaAlumnos) {
            Object[] fila = {
                    alumno.getId(),
                    alumno.getNombre(),
                    alumno.getApellido(),
                    alumno.getFechaNacimiento()
            };
            modelo_tabla.addRow(fila);

        }

        listaAlumnosFrame.getTabla_alumos().setModel(modelo_tabla);
    }

}
