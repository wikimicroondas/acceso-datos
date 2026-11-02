package com.whisky.tema01;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class XML {

    static void main() {
        final String EMPLEADOS = "empleados.xml";
        final String BIBLIOTECA = "biblioteca.xml";
        final String PEDIDOS = "pedidos.xml";

        try {
            // 1
            Empleado[] empleados = parseEmpleados('/' + EMPLEADOS);
            IO.println(Arrays.toString(empleados));

            // 2
            printDepartmentStats(empleados);

            // 3
            printLibraryStats('/' + BIBLIOTECA);

            // 4
            //

        } catch (IOException | ParserConfigurationException | SAXException e) {
            System.err.println("Error al procesar el fichero: " + EMPLEADOS + e.getMessage());
        }
    }

    // Instanciación limitada
    private XML () { }

    // 1
    public static Empleado[] parseEmpleados(String path)
            throws IOException,ParserConfigurationException,
            DateTimeParseException, NumberFormatException,SAXException {

        Empleado[] empleados;
        try (InputStream is = XML.class.getResourceAsStream(path)) {

            if (is == null) {
                throw new FileNotFoundException("No se ha encontrado: " + path);
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document dom = builder.parse(is);

            dom.getDocumentElement().normalize();

            NodeList items = dom.getElementsByTagName("empleado");
            empleados = new Empleado[items.getLength()];

            for (int i = 0; i < items.getLength(); i++) {
                Node item = items.item(i);

                if (item.getNodeType() == Node.ELEMENT_NODE) {
                    Element empleado = (Element) item;
                    // <a id="attribute" />
                    String id = empleado.getAttribute("id");
                    // list > item(0) > text
                    String nombre = empleado.getElementsByTagName("nombre")
                            .item(0).getTextContent().trim();

                    String departamento = empleado.getElementsByTagName("departamento")
                            .item(0).getTextContent().trim();

                    Element salarioElement = (Element) empleado
                            .getElementsByTagName("salario")
                            .item(0);

                    String moneda = salarioElement.getAttribute("moneda");

                    double salario = Double.parseDouble(salarioElement.getTextContent());

                    LocalDate fechaAlta = LocalDate.parse(empleado.getElementsByTagName("fechaAlta")
                            .item(0).getTextContent().trim());

                    empleados[i] = new Empleado(id, nombre, departamento, salario, moneda, fechaAlta);
                }
            }

            return empleados;
        }
    }

    // 2
    public static void printDepartmentStats(Empleado[] empleados) {
        IO.println("Departamento\t\t | Nº empleados\t| Salario medio");

        Arrays.stream(empleados)
                .collect(Collectors.groupingBy(
                        Empleado::getDepartamento,
                        Collectors.summarizingDouble(Empleado::getSalario)
                ))
                .forEach((dept, stats) -> {
                    System.out.printf("%-20s | %-12d | %.2f%n",
                            dept, stats.getCount(), stats.getAverage());
                });
    }

    // 3
    public static void printLibraryStats(String path)
            throws IOException, ParserConfigurationException, SAXException {

        try (InputStream is = XML.class.getResourceAsStream(path)) {
            if (is == null) {
                throw new FileNotFoundException("No se ha encontrado: " + path);
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document dom = builder.parse(is);

            dom.getDocumentElement().normalize();

            NodeList libros = dom.getElementsByTagName("libro");
            Map<String, Integer> genreCount = new HashMap<>();

            IO.println("--- Títulos de la librería ---");
            for (int i = 0; i < libros.getLength(); i++) {

                if (libros.item(i).getNodeType() == Node.ELEMENT_NODE) {
                    Element libro = (Element) libros.item(i);

                    String titulo = libro.getElementsByTagName("titulo")
                            .item(0).getTextContent().trim();
                    IO.println("- " + titulo);

                    NodeList generos = libro.getElementsByTagName("genero");
                    for (int j = 0; j < generos.getLength(); j++) {
                        String genero = generos.item(j).getTextContent().trim();
                        genreCount.put(genero, genreCount.getOrDefault(genero, 0) + 1);
                    }
                }
            }

            IO.println("\n--- Nº Libros por género ---");
            genreCount.forEach((genre, count) -> IO.println(genre + ": " + count));
        }
    }

    // 4
    public static void validatePedidoTotal(String path, String targetId)
            throws IOException, ParserConfigurationException, SAXException {
        try (InputStream is = XML.class.getResourceAsStream(path)) {
            if (is == null){
                throw new FileNotFoundException("No se ha encontrado: " + path);
            }

            Document dom = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(is);
            dom.getDocumentElement().normalize();

            NodeList pedidos = dom.getElementsByTagName("pedido");

            for (int i = 0; i < pedidos.getLength(); i++) {
                Element pedido = (Element) pedidos.item(i);

                if (!pedido.getAttribute("id").equals(targetId)) {
                    continue;
                }

                System.out.println("--- Order ID: " + targetId + " ---");
                NodeList items = pedido.getElementsByTagName("item");
                double calculatedTotal = 0.0;

                for (int j = 0; j < items.getLength(); j++) {
                    Element item = (Element) items.item(j);

                    String descripcion = item.getElementsByTagName("descripcion")
                            .item(0).getTextContent().trim();
                    double cantidad = Double.parseDouble(
                            item.getElementsByTagName("cantidad").item(0).getTextContent().trim()
                    );
                    double precioUnit = Double.parseDouble(
                            item.getElementsByTagName("precioUnitario").item(0).getTextContent().trim()
                    );

                    calculatedTotal += (cantidad * precioUnit);
                    System.out.printf("- %s | Qty: %.0f | Unit Price: %.2f%n", descripcion, cantidad, precioUnit);
                }

                double xmlTotal = Double.parseDouble(
                        pedido.getElementsByTagName("total").item(0).getTextContent().trim()
                );

                System.out.printf("%nTotal Calculado: %.2f | Total del documento: %.2f%n", calculatedTotal, xmlTotal);

                // Float comparison using epsilon for precision safety
                boolean isValid = Math.abs(calculatedTotal - xmlTotal) < 0.001;
                IO.println("Status de validación: " + (isValid ? "VÁLIDO" : "NO VÁLIDO"));

                return;
            }
            IO.println("Order ID Solicitado [" + targetId + "] no se ha encontrado.");
        }
    }
}
