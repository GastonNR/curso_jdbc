package controller;

import view.DetalleAlumnos;
import view.ListaAlumnosFrame;

public class ControladorGeneral {
    private ListaAlumnosFrame listaAlumnosFrame;
    private DetalleAlumnos detalleAlumnos;

    public ControladorGeneral() {
        this.listaAlumnosFrame = new ListaAlumnosFrame();
        listaAlumnosFrame.setVisible(true);
    }
}
