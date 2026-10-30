package com.whisky.tema01;

import java.time.LocalDate;

public class Empleado {
    private String id;
    private String nombre;
    private String departamento;
    private double salario;
    private String moneda;
    private LocalDate fechaAlta;

    public String getDepartamento() {
        return departamento;
    }

    public double getSalario() {
        return salario;
    }

    public String getMoneda() {
        return moneda;
    }

    public Empleado(String id, String nombre, String departamento, double salario, String moneda, LocalDate fechaAlta) {
        this.id = id;
        this.nombre = nombre;
        this.departamento = departamento;
        this.salario = salario;
        this.moneda = moneda;
        this.fechaAlta = fechaAlta;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", departamento='" + departamento + '\'' +
                ", salario=" + salario +
                ", moneda='" + moneda + '\'' +
                ", fechaAlta='" + fechaAlta + '\'' +
                "}\n";
    }
}
