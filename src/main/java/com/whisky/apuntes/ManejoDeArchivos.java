package com.whisky.apuntes;

import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.util.Scanner;

public class ManejoDeArchivos {

    public ManejoDeArchivos() throws FileAlreadyExistsException, FileNotFoundException {
        String origen = "./tux.txt";
        String destino = "./tux-copia.txt";

        // Con buffer
        copiarFicheroStreamConBuffer(origen, destino, false);
    }

    public boolean copiarFicheroStreamConBuffer(String origen, String destino, boolean sobreescribir)
            throws FileAlreadyExistsException, FileNotFoundException {

        // Validación inicial
        File fileOrigen = new File(origen);
        if (!fileOrigen.exists()) {
            throw new FileNotFoundException("El fichero " + origen + " no existe");
        }
        File fileDestino = new File(destino);
        if (!sobreescribir && fileDestino.exists()) {
            throw new FileAlreadyExistsException("El destino " + destino + " ya existe");
        }

        // Prueba de escritura
        try (
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fileOrigen));
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileDestino))
        ) {
            int dato;
            while ((dato = bis.read()) != -1) {
                bos.write(dato);
            }
        }
        catch (IOException ioe) {
            return false;
        }
        return true;
    }

    public boolean ejemploCopiarFicheroBinarioSinBuffer(String origen, String destino)
            throws FileNotFoundException, FileAlreadyExistsException {
        return ejemploCopiarFicheroBinarioSinBuffer(origen, destino, false);
    }

    public boolean ejemploCopiarFicheroBinarioSinBuffer(String origen, String destino, boolean sobreescribir)
            throws FileNotFoundException, FileAlreadyExistsException {
        File fileOrigen = new File(origen);
        if (!fileOrigen.exists()) {
            throw new FileNotFoundException("El fichero " + origen + " no existe");
        }
        File fileDestino = new File(destino);
        if (!sobreescribir && fileDestino.exists()) {
            throw new FileAlreadyExistsException("El destino " + destino + " ya existe");
        }
        try (
                FileInputStream fis = new FileInputStream(fileOrigen);
                FileOutputStream fos = new FileOutputStream(fileDestino);
        ) {
            int dato;
            while ((dato = fis.read()) != -1) {
                fos.write(dato);
            }
        } catch (IOException ioe) {
            return false;
        }
        return true;
    }

    public void ejemploCopiarFicheroTextoSinBuffer(String origen, String destino) {
        File ficheroOrigen = new File(origen);
        File ficheroDestino = new File(destino);
        if (ficheroOrigen.isFile()) {
            try (
                    FileReader fileReader = new FileReader(ficheroOrigen);
                    FileWriter fileWriter = new FileWriter(ficheroDestino);
            ) {
                int caracter;
                while ((caracter = fileReader.read()) != -1) {
                    fileWriter.write(caracter);
                }
                IO.println("Fin del fichero");
            } catch (IOException ioe) {
                IO.println(ioe.getMessage());
            }
        } else {
            IO.println("El fichero no existe");
        }
    }

    public void ejemploCopiarFicheroTextoConBuffer(String origen, String destino) {
        File ficheroOrigen = new File(origen);
        File ficheroDestino = new File(destino);
        if (ficheroOrigen.isFile()) {
            try (
                    FileReader fileReader = new FileReader(ficheroOrigen);
                    BufferedReader br = new BufferedReader(fileReader);
                    FileWriter fileWriter = new FileWriter(ficheroDestino);
                    BufferedWriter bw = new BufferedWriter(fileWriter);
            ) {
                String linea;
                while ((linea = br.readLine()) != null) {
                    bw.write(linea);
                    bw.newLine();
                }
                IO.println("Fin del fichero");
            } catch (IOException ioe) {
                IO.println(ioe.getMessage());
            }
        } else {
            IO.println("El fichero no existe");
        }
    }
}