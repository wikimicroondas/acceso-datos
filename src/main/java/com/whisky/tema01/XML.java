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

public class XML {

    static void main() {
        try {
            // 1
            Empleado[] empleados = parseEmpleados("/empleados.xml");
            IO.println(Arrays.toString(empleados));

            // 2
            printDepartmentStats(empleados);

        } catch (IOException | ParserConfigurationException | SAXException e) {
            System.err.println("Error al procesar el fichero: " + e.getMessage());
        }
    }

    // Instanciación limitada
    private XML () { }

    // 1 - 2
    public static Empleado[] parseEmpleados(String path)
            throws IOException,ParserConfigurationException,
            DateTimeParseException, NumberFormatException,SAXException {

        Empleado[] empleados = null;
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

    public static void printDepartmentStats(Empleado[] empleados) {
        IO.println("Departamento | Nº empleados | Salario medio");

    }
}
