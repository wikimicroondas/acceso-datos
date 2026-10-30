package com.whisky.apuntes;

import java.util.ArrayList;

public class HashMap2 {
    private String[] claves;
    private ArrayList<Pareja>[] valores;

    public HashMap2(int tamano) {
        claves = new String[tamano];
        valores = new ArrayList[tamano];

        for (int i = 0; i < valores.length; i++) {
            valores = new ArrayList[valores.length];
        }
    }

    public String get(String clave) {
        int position = hash(clave);
        for (Pareja pareja : valores[position]) {
            if (pareja.clave.equals(clave)) {
                return pareja.valor;
            }
        }
        return null;
    }

    public String put(String clave, String valor) {
        int position = hash(clave);
        // Si la clave ya existe
        for (Pareja pareja : valores[position]) {
            String valorAntiguo = pareja.valor;
            if (pareja.clave.equals(clave)) {
                pareja.setValor(valor);
                return valorAntiguo;
            }
        }
        // Si la clave no existe
        valores[position].add(new Pareja(clave, valor));
        return null;
    }

    private int hash(String clave) {
        int suma = 0;
        for (int i = 0; i < clave.length(); i++) {
            suma += (int) clave.charAt(i);
        }
        return suma % clave.length();
    }

    class Pareja {
        private String clave;
        private String valor;

        public void setValor(String valor) {
            this.valor = valor;
        }

        public Pareja(String clave, String valor) {
            this.clave = clave;
            this.valor = valor;
        }
    }
}
