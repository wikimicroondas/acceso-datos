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
            Alumno[] alumos = json.parseAsignaturas(ARCHIVO);
            for (Alumno alumo : alumos) {
                System.out.println(alumo);
            }
        } catch (IOException e) {
            System.err.println("No se ha podido leer el archivo " + ARCHIVO);
        }

    }

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
}
