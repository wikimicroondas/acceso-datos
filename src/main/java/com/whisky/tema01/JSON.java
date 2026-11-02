package com.whisky.tema01;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;


public class JSON {

    static void main() {

        JSON json = new JSON();
        final String ARCHIVO = "alumnos.json";

        try {
            // 5
            Alumno[] alumnos = json.parseAsignaturas(ARCHIVO);

            // 5a
            printAlumnosNotaAlta(alumnos);

            // 5b
            printAlumnoNotaMediaMasAlta(alumnos);

        } catch (IOException e) {
            System.err.println("No se ha podido leer el archivo " + ARCHIVO);
        }

    }

    // 5
    public Alumno[] parseAsignaturas(String file) throws IOException {

        Alumno[] alumnos;
        StringBuilder sb = new StringBuilder();
        try (
                InputStream is = getClass().getResourceAsStream("/" + file);
                BufferedReader br = new BufferedReader(new InputStreamReader(is,
                        StandardCharsets.UTF_8))
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            JSONTokener tokener = new JSONTokener(sb.toString());
            JSONArray jsonArray = new JSONArray(tokener); // array de objetos
            alumnos = new Alumno[jsonArray.length()];

            for (int i = 0; i < jsonArray.length(); i++) {

                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String id = jsonObject.getString("id");
                String nombre = jsonObject.getString("nombre");
                boolean matriculado = jsonObject.getBoolean("matriculado");
                LocalDate fechaNacimiento = LocalDate.parse(jsonObject.getString("fechaNacimiento"));

                JSONArray notasJsonArray = jsonObject.getJSONArray("notas");
                Nota[] notas = new Nota[notasJsonArray.length()];
                for (int j = 0; j < notas.length; j++) {
                    JSONObject jsonObjectNota = notasJsonArray.getJSONObject(j);
                    String asignatura = jsonObjectNota.getString("asignatura");
                    double nota = jsonObjectNota.getDouble("nota");
                    notas[j] = new Nota(asignatura, nota);
                }

                alumnos[i] = new Alumno(id, nombre, matriculado, fechaNacimiento, notas);
            }
        }
        return alumnos;
    }

    // 5a
    public static void printAlumnosNotaAlta(Alumno[] alumnos) {

        Nota[] notas;
        double notaMasAlta = 0;
        for (int i = 0; i < alumnos.length; i++) {
            notas = alumnos[i].getNotas();
            for (int j = 0; j < notas.length; j++) {
                notaMasAlta = Double.max(notaMasAlta, notas[j].getNota());
            }
            IO.println("--- " + alumnos[i].getNombre() + " ---");
            IO.println("Nota más alta: " + notaMasAlta);
        }
    }

    // 5b
    public static void printAlumnoNotaMediaMasAlta(Alumno[] alumnos) {

        Nota[] notas;
        int excellent;
        double avg = 0;
        for (int i = 0; i < alumnos.length; i++) {
            notas = alumnos[i].getNotas();
            for (int j = 0; j < notas.length; j++) {

            }
        }
    }
}
