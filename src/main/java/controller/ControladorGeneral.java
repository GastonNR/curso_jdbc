package controller;

import DAO.DAOException;
import model.Alumno;
import model.ServicioDeDatos;
import view.ListaAlumnosFrame;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
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

        listaAlumnosFrame.getBtn_editar().setEnabled(false);
        listaAlumnosFrame.getBtn_borrar().setEnabled(false);
        listaAlumnosFrame.getBtn_guardar().setEnabled(false);

        try{
            cargarListaAlumnos();

        }catch (DAOException ex){
            ex.printStackTrace();

        }
        listaAlumnosFrame.getTabla_alumnos().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int filaSeleccionada = listaAlumnosFrame.getTabla_alumnos().getSelectedRow();
                    if (filaSeleccionada != -1) {
                        listaAlumnosFrame.getBtn_editar().setEnabled(true);
                        listaAlumnosFrame.getBtn_borrar().setEnabled(true);
                    }
                }
            }
        });
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
        if (e.getSource() == listaAlumnosFrame.getBtn_guardar()) {
            try {
                guardarAlumno();
            } catch (DAOException ex) {
                throw new RuntimeException(ex);
            }
        }
        if (e.getSource() == listaAlumnosFrame.getBtn_cancelar()) cancelar();
    }

    private void registrarAlumno() throws DAOException {
        String nombre = listaAlumnosFrame.getTxt_nombre().getText();
        String apellidos = listaAlumnosFrame.getTxt_apellidos().getText();

        int dia = (int) listaAlumnosFrame.getSp_dia().getValue();
        int mes = (int) listaAlumnosFrame.getSp_mes().getValue();
        int anio = (int) listaAlumnosFrame.getSp_anio().getValue();

        LocalDate fechaNac = LocalDate.of(anio, mes, dia);
        Alumno nuevoAlumno = new Alumno(nombre, apellidos, fechaNac);

        try {
            servicio.getDaoManager().getAlumnoDAO().insertar(nuevoAlumno);
            JOptionPane.showMessageDialog(listaAlumnosFrame, "Alumno guardado exitosamente");
            reiniciarCampos();
            cargarListaAlumnos();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(listaAlumnosFrame, "Error al guardar los datos del alumno.");

        }

    }


    private void actualizarAlumno() throws DAOException{
        Long id_alumno = getLineaSeleccionada();
        listaAlumnosFrame.getBtn_guardar().setEnabled(true);

        try {
            Alumno alumno = servicio.getDaoManager().getAlumnoDAO().obtener(id_alumno);
            if (alumno != null) {
                listaAlumnosFrame.getTxt_nombre().setText(alumno.getNombre().toString());
                listaAlumnosFrame.getTxt_apellidos().setText(alumno.getApellido().toString());
                listaAlumnosFrame.getSp_dia().setValue(alumno.getFechaNacimiento().getDayOfMonth());
                listaAlumnosFrame.getSp_mes().setValue(alumno.getFechaNacimiento().getMonthValue());
                listaAlumnosFrame.getSp_anio().setValue(alumno.getFechaNacimiento().getYear());

            }

        } catch (DAOException ex) {
            JOptionPane.showMessageDialog(listaAlumnosFrame, "Error al eliminar el alumno: ");
            System.out.println(ex);
        }

    }

    private void borrarAlumno() {
        Long idAlumno = getLineaSeleccionada();

        try {
            Alumno alumno = servicio.getDaoManager().getAlumnoDAO().obtener(idAlumno);

            if (alumno != null) {
                servicio.getDaoManager().getAlumnoDAO().eliminar(alumno);
                ((DefaultTableModel) listaAlumnosFrame.getTabla_alumnos().getModel()).removeRow(listaAlumnosFrame.getTabla_alumnos().getSelectedRow());

                JOptionPane.showMessageDialog(listaAlumnosFrame, "Alumno eliminado correctamente.");

            }
        } catch (DAOException ex) {
            JOptionPane.showMessageDialog(listaAlumnosFrame, "Error al eliminar el alumno: ");
            System.out.println(ex);

        }

    }

    private void guardarAlumno() throws DAOException {

        if (listaAlumnosFrame.getTxt_nombre() == null || listaAlumnosFrame.getTxt_apellidos() == null) {
            JOptionPane.showMessageDialog(listaAlumnosFrame, "Seleccione un alumno");
            return;
        }

        String nombre = listaAlumnosFrame.getTxt_nombre().getText();
        String apellidos = listaAlumnosFrame.getTxt_apellidos().getText();

        try {
            listaAlumnosFrame.getSp_dia().commitEdit();
            listaAlumnosFrame.getSp_mes().commitEdit();
            listaAlumnosFrame.getSp_anio().commitEdit();

        } catch (ParseException ex) {
            System.out.println("Error al leer los datos del spinner: " + ex);

        }

        int dia = (int) listaAlumnosFrame.getSp_dia().getValue();
        int mes = (int) listaAlumnosFrame.getSp_mes().getValue();
        int anio = (int) listaAlumnosFrame.getSp_anio().getValue();

        LocalDate fechaNac = LocalDate.of(anio, mes, dia);

        Long id_alumno_seleccionado = getLineaSeleccionada();

        Alumno alumnoActualizado = new Alumno(nombre, apellidos, fechaNac);
        alumnoActualizado.setId(id_alumno_seleccionado);

        servicio.getDaoManager().getAlumnoDAO().modificar(alumnoActualizado);
        JOptionPane.showMessageDialog(listaAlumnosFrame, "Datos del alumno actualizado exitosamente");
        cargarListaAlumnos();
        reiniciarCampos();
        listaAlumnosFrame.getBtn_editar().setEnabled(false);
        listaAlumnosFrame.getBtn_borrar().setEnabled(false);
        listaAlumnosFrame.getBtn_guardar().setEnabled(false);
    }

    private void cancelar() {

    }

    //Métodos privados
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

        listaAlumnosFrame.getTabla_alumnos().setModel(modelo_tabla);
    }

    private void reiniciarCampos() {
        listaAlumnosFrame.getTxt_nombre().setText("");
        listaAlumnosFrame.getTxt_apellidos().setText("");

        listaAlumnosFrame.getSp_dia().setValue(1);
        listaAlumnosFrame.getSp_mes().setValue(1);
        listaAlumnosFrame.getSp_anio().setValue(2000);
    }

    private Long getLineaSeleccionada() {
        int filaSeleccionada = listaAlumnosFrame.getTabla_alumnos().getSelectedRow();

        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(listaAlumnosFrame, "Debes seleccionar un alumno de la lista.");
            return null;
        }

        DefaultTableModel modelo = (DefaultTableModel) listaAlumnosFrame.getTabla_alumnos().getModel();
        Long idAlumno = Long.parseLong(modelo.getValueAt(filaSeleccionada, 0).toString());

        return idAlumno;
    }
}
