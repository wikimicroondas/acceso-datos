package com.whisky.tema01;

import java.io.*;

public class GestionArchivos {

    public static File validarArchivo(File file) throws FileNotFoundException {
        if (file == null || !file.exists()) {
            throw new FileNotFoundException("El archivo o directorio es nulo");
        }
        else {
            return file;
        }
    }

    // 6
    public static void leerArchivo(File file) {

        try (
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                ) {
            int dato;
            while ((dato = bis.read()) != -1) {
                IO.println(dato);
            }
        }
        catch (FileNotFoundException fnfe) {
            IO.println("No se ha encontrado el archivo.");
        }
        catch (IOException ioe) {
            IO.println("Ha ocurrido un error inesperado en el input.");
        }
    }


    // 3 semanas
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

    public void listarDirectorio(String directorio) {
        if (directorio.isEmpty()) {
            IO.println("");
        }
    }
}
