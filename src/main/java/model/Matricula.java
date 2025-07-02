package model;

import java.util.Objects;

public class Matricula {

    public class IdMatricula{

        private Long alumno;
        private Long asignatura;
        private int year;

        public IdMatricula(Long alumno, Long asignatura, int year) {
            this.alumno = alumno;
            this.asignatura = asignatura;
            this.year = year;
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
    }

    private IdMatricula id;
    private Integer nota = null;

    public Matricula(IdMatricula id) {
        this.id = id;
    }

    public Matricula(Long alumno, Long asignatura, int year){
        this.id = new IdMatricula(alumno, asignatura, year);
    }

    public IdMatricula getId() {
        return id;
    }

    public void setId(IdMatricula id) {
        this.id = id;
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
                "id=" + id +
                ", nota=" + nota +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Matricula matricula)) return false;
        return Objects.equals(id, matricula.id) && Objects.equals(nota, matricula.nota);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nota);
    }
}
