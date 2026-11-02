package com.whisky.tema01;

public class Nota {
    private String asignatura;
    private double nota;

    public Nota() {
    }

    public Nota(String asignatura, double nota) {
        this.asignatura = asignatura;
        this.nota = nota;
    }

    public String getAsignatura() {
        return asignatura;
    }

    public double getNota() {
        return nota;
    }

    public void setAsignatura(String asignatura) {
        this.asignatura = asignatura;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return "Nota{" +
                "asignatura='" + asignatura + '\'' +
                ", nota=" + nota +
                '}';
    }
}
