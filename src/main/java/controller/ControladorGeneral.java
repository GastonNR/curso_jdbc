package controller;

import view.DetalleAlumnos;
import view.ListaAlumnosFrame;

import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControladorGeneral implements ActionListener {
    private ListaAlumnosFrame listaAlumnosFrame;
    private DetalleAlumnos detalleAlumnos;

    public ControladorGeneral() {
        this.listaAlumnosFrame = new ListaAlumnosFrame();
        listaAlumnosFrame.setVisible(true);

        listaAlumnosFrame.getBtn_nuevo().addActionListener(this);
        listaAlumnosFrame.getBtn_editar().addActionListener(this);
        listaAlumnosFrame.getBtn_borrar().addActionListener(this);
        listaAlumnosFrame.getBtn_guardar().addActionListener(this);
        listaAlumnosFrame.getBtn_cancelar().addActionListener(this);

        cargarListaAlumnos();
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == listaAlumnosFrame.getBtn_nuevo()) registrarAlumno();
        if (e.getSource() == listaAlumnosFrame.getBtn_editar()) actualizarAlumno();
        if (e.getSource() == listaAlumnosFrame.getBtn_borrar()) borrarAlumno();
        if (e.getSource() == listaAlumnosFrame.getBtn_guardar()) guardarAlumno();
        if (e.getSource() == listaAlumnosFrame.getBtn_cancelar()) cancelar();
    }
    private void registrarAlumno() {

    }

    private void actualizarAlumno() {
    }

    private void borrarAlumno() {
    }

    private void guardarAlumno() {
    }

    private void cancelar() {
    }

    private void cargarListaAlumnos() {
        String[] columnas ={"ID", "Nombre", "Apellidos", "Fecha de nacimiento"};
        DefaultTableModel modelo_tabla = new DefaultTableModel(columnas, 0);


    }

}
