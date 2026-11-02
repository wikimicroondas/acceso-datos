package com.whisky.tema01;

import java.time.LocalDate;
import java.util.Arrays;

public class Alumno {
    private String id;
    private String nombre;
    private boolean matriculado;
    private LocalDate fechaNacimiento;
    private Nota[] notas;

    public Alumno(String id, String nombre, boolean matriculado, LocalDate fechaNacimiento, Nota[] notas) {
        this.id = id;
        this.nombre = nombre;
        this.matriculado = matriculado;
        this.fechaNacimiento = fechaNacimiento;
        this.notas = notas;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean isMatriculado() {
        return matriculado;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public Nota[] getNotas() {
        return notas;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setMatriculado(boolean matriculado) {
        this.matriculado = matriculado;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setNotas(Nota[] notas) {
        this.notas = notas;
    }

    @Override
    public String toString() {
        return "Alumno{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", matriculado=" + matriculado +
                ", fechaNacimiento=" + fechaNacimiento +
                ", notas=" + Arrays.toString(notas) +
                '}';
    }
}
