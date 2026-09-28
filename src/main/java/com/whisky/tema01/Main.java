package com.whisky.tema01;

import java.io.File;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Main {
    final static String DIR = "/home/whisky/Desktop/system/";
    final static String FILE = "/home/whisky/Desktop/system/tux.txt";

    static void main(String[] args) {
        //                       Ejercicios
        statusAndType(DIR);   // 1
        statusAndType(FILE);
        listContent(DIR);     // 2
        listProperties(DIR);  // 3
        listProperties(FILE); // 4
    }

    public static void statusAndType(String path) {
        File file = new File(path);
        if (!file.exists()) {
            IO.println("La ruta no corresponde a ningún archivo o carpeta.\n");
            System.exit(0);
        }
        if (file.isDirectory()) {
            IO.println("La ruta corresponde a una carpeta existente.\n");
        } else {
            IO.println("La ruta corresponde a un archivo existente.\n");
        }
    }

    public static void listContent(String path) {
        File directory = new File(path);
        if (directory.list() == null || directory.list().length == 0) {
            IO.println("\nEl directorio está vacío.");
        }
        for (String file : directory.list()) {
            IO.print("- " + file + "\n");
        }
        IO.println();
    }

    public static void listProperties(String path) {
        File file = new File(path);
        IO.println("-- Propiedades --");
        IO.println("Nombre               : " + file.getName());
        IO.println("Ruta absoluta        : " + file.getAbsolutePath());
        IO.println("Oculto               : " + file.isHidden());
        IO.println("Permiso de lectura   : " + file.canRead());
        IO.println("Permiso de escritura : " + file.canWrite());

        // Fecha
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");

        LocalDateTime lastModified = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(file.lastModified()),
                ZoneId.systemDefault()
        );
        IO.println("Última modificación  : " + lastModified.format(formatter));
        boolean modifySuccess = file.setLastModified(System.currentTimeMillis());

        if (modifySuccess) {
            LocalDateTime newLastModified = LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(file.lastModified()),
                    ZoneId.systemDefault()
            );
            IO.println("Nueva modificación   : " + newLastModified.format(formatter));
        }

        if (file.isFile()) {
            long bytes = file.length();
            IO.println("Tamaño (Bytes)       : " + bytes);
            IO.println("Tamaño (KB)          : " + String.format("%.2f", bytes / 1024.0));
            IO.println("Tamaño (MB)          : " + String.format("%.3f", bytes / (1024.0 * 1024.0)));
        }
        IO.println();
    }


}
