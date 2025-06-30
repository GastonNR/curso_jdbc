package model;

import java.util.Objects;

public class Matricula {
    private Long alumno;
    private Long asignatura;
    private int year;
    private Integer nota = null;

    public Matricula(Long alumno, Long asignatura, int year, Integer nota) {
        this.alumno = alumno;
        this.asignatura = asignatura;
        this.year = year;
        this.nota = nota;
    }

    public Long getAlumno() {
        return alumno;
    }

    public void setAlumno(Long alumno) {
        this.alumno = alumno;
    }

    public Long getAsignatura() {
        return asignatura;
    }

    public void setAsignatura(Long asignatura) {
        this.asignatura = asignatura;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return "Matricula{" +
                "alumno=" + alumno +
                ", asignatura=" + asignatura +
                ", year=" + year +
                ", nota=" + nota +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Matricula matricula)) return false;
        return year == matricula.year && Objects.equals(alumno, matricula.alumno) && Objects.equals(asignatura, matricula.asignatura) && Objects.equals(nota, matricula.nota);
    }

    @Override
    public int hashCode() {
        return Objects.hash(alumno, asignatura, year, nota);
    }
}
