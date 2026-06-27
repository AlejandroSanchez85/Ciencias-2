/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libreria_de_grafos;

import java.util.Scanner;

/**
 *
 * @author Estudiantes
 */
public class Main {
    /*
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        // 1. Configuración de dimensiones
        System.out.print("Introduce el número de vértices (nodos): ");
        int vertices = teclado.nextInt();

        System.out.print("Introduce el número de aristas (conexiones): ");
        int aristas = teclado.nextInt();

        // Instanciamos el grafo pasando filas (vertices) y columnas (aristas)
        matrizIncidencia miGrafo = new matrizIncidencia(vertices, aristas);

        System.out.println("\n--- Registro de Aristas ---");
        System.out.println("Nota: Los vértices van desde 0 hasta " + (vertices - 1));
        
        // 2. Captura secuencial de cada arista
        for (int i = 0; i < aristas; i++) {
            System.out.println("\nConfigurando Arista A" + i + ":");
            
            System.out.print("Vértice de origen: ");
            int u = teclado.nextInt();
            
            System.out.print("Vértice de destino: ");
            int v = teclado.nextInt();
            
            System.out.print("Peso de la conexión: ");
            int peso = teclado.nextInt();

            // Enviamos los datos al objeto grafo
            boolean exito = miGrafo.agregarArista(u, v, peso);
            
            if (!exito) {
                System.out.println("¡Error! Vértices inválidos o límite excedido. Reintenta esta arista.");
                i--; // Deshacer el paso del ciclo para repetir la arista actual
            }
        }

        // 3. Imprimir el resultado final
        miGrafo.mostrarMatriz();

        teclado.close();
    }
    */
    /*
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        // 1. Configuración inicial del grafo
        System.out.print("Introduce el número de vértices (nodos): ");
        int vertices = teclado.nextInt();

        // Instanciamos el objeto Grafo pasando el tamaño
        matrizAdyacencia miGrafo = new matrizAdyacencia(vertices);

        System.out.print("Introduce el número de aristas (conexiones): ");
        int aristas = teclado.nextInt();

        System.out.println("\n--- Registro de Aristas ---");
        System.out.println("Nota: Los vértices van desde el 0 hasta el " + (vertices - 1));
        
        // 2. Captura de datos
        for (int i = 0; i < aristas; i++) {
            System.out.println("\nDatos para la Arista " + (i + 1) + ":");
            
            System.out.print("Vértice origen: ");
            int u = teclado.nextInt();
            
            System.out.print("Vértice destino: ");
            int v = teclado.nextInt();
            
            System.out.print("Peso de la conexión: ");
            int peso = teclado.nextInt();

            // Intentamos agregar la arista en el objeto grafo
            boolean exito = miGrafo.agregarArista(u, v, peso);
            
            if (!exito) {
                System.out.println("¡Error! Vértices inválidos. Intenta registrar esta arista de nuevo.");
                i--; // Repetir iteración si hubo error
            }
        }

        // 3. Mostrar el resultado delegando la acción al objeto grafo
        miGrafo.mostrarMatriz();

        teclado.close();
    }
    */
    
      // Método principal para probar el código
    public static void main(String[] args) {
        // Creamos un grafo con 5 vértices (del 0 al 4)
        int numeroVertices = 5;
        listaAdyacencia grafo = new listaAdyacencia(numeroVertices);

        // Agregamos las aristas con sus respectivos pesos
        grafo.agregarArista(0, 1, 4);
        grafo.agregarArista(0, 2, 8);
        grafo.agregarArista(1, 2, 2);
        grafo.agregarArista(1, 3, 5);
        grafo.agregarArista(2, 4, 9);
        grafo.agregarArista(3, 4, 4);

        // Mostramos el resultado
        grafo.imprimirGrafo();
    }
}
