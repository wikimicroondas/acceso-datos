package com.whisky.tema01;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;

public class EjercicioDiezOnce {
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

            bos.write(END_LINE);

            while (
                    (dato2 = bis_2.read()) != -1
            ) {
                bos.write(dato2);
            }

            return concatFile;
        }
    }

}
