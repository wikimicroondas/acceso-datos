package com.whisky.tema01;

import java.io.File;
import java.io.IOException;

public class GestionArchivos {

    public boolean crearArchivo(String directorio, String archivo) {
        if (directorio.isEmpty()) {
            IO.println("El directorio no existe.");
            return false;
        }
        if (archivo.isEmpty() || !archivo.matches("^[\\w\\-]+\\.[a-zA-Z0-9]+$")) {
            IO.println("El nombre del archivo no es válido");
            return false;
        }
        File newFile = new File(directorio, archivo);

        try {
            return newFile.createNewFile();
        } catch (IOException e) {
            IO.println("Error de entrada/salida al crear el archivo: " + e.getMessage());
            return false;
        }
    }
}
