package com.whisky.tema01;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Boletin1 {
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


    private final static String CONCAT_NAME = "./concat_file.txt";
    private final static int END_LINE = '\n';
    private final static int SPACE = ' ';

    // 11
    /**
     * Concatenates the contents of two files line by line into a new file.
     *
     * @param f1 First input file.
     * @param f2 Second input file.
     * @return A new File with concatenated lines.
     * @throws FileNotFoundException If either f1 or f2 does not exist.
     * @throws FileAlreadyExistsException If concat_file.txt already exists.
     * @throws IOException If an I/O error occurs while reading or writing the file streams.
     */
    public static File concatLines(File f1, File f2)
            throws IOException {

        if (!f1.exists() || !f2.exists()) {
            throw new FileNotFoundException(
                    "Ambos archivos deben existir para ejecutar esta acción."
            );
        }
        File concatFile = new File(CONCAT_NAME);
        if (concatFile.exists()) {
            throw new FileAlreadyExistsException(
                    "El archivo concat_file.txt ya existe."
            );
        }

        try (
                BufferedInputStream bis_1 = new BufferedInputStream(new FileInputStream(f1));
                BufferedInputStream bis_2 = new BufferedInputStream(new FileInputStream(f2));
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(concatFile))
        ) {
            int dato1, dato2;
            while (true) {
                while (
                        (dato1 = bis_1.read()) != END_LINE
                                && dato1 != -1
                ) {
                    bos.write(dato1);
                }
                if (dato1 != -1) {
                    bos.write(SPACE);
                }
                while (
                        (dato2 = bis_2.read()) != END_LINE
                                && dato2 != -1
                ) {
                    bos.write(dato2);
                }

                if (dato1 == -1 && dato2 == -1) {
                    break;
                }
                bos.write(END_LINE);
            }

            return concatFile;
        }
    }

    // 10
    /**
     * Concatenates the contents of two files, one at a time, into a new file.
     *
     * @param f1 First input file.
     * @param f2 Second input file.
     * @return A new File with both contents.
     * @throws FileNotFoundException If either f1 or f2 does not exist.
     * @throws FileAlreadyExistsException If concat_file.txt already exists.
     * @throws IOException If an I/O error occurs while reading or writing the file streams.
     */
    public static File concat(File f1, File f2)
            throws IOException {

        if (!f1.exists() || !f2.exists()) {
            throw new FileNotFoundException(
                    "Ambos archivos deben existir para ejecutar esta acción."
            );
        }
        File concatFile = new File(CONCAT_NAME);
        if (concatFile.exists()) {
            throw new FileAlreadyExistsException(
                    "El archivo concat_file.txt ya existe."
            );
        }

        try (
                BufferedInputStream bis_1 = new BufferedInputStream(new FileInputStream(f1));
                BufferedInputStream bis_2 = new BufferedInputStream(new FileInputStream(f2));
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(concatFile))
        ) {
            int dato1, dato2;
            while (
                    (dato1 = bis_1.read()) != -1
            ) {
                bos.write(dato1);
            }

            bos.write(END_LINE); // Config.java << 'finals'

            while (
                    (dato2 = bis_2.read()) != -1
            ) {
                bos.write(dato2);
            }

            return concatFile;
        }
    }
}
