/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libreria_de_grafos;

/**
 *
 * @author Estudiantes
 */
public class matrizIncidencia {
    

    private int[][] matrizIncidencia;
    private int numVertices;
    private int numAristas;
    private int columnaActual; // Controla en qué arista (columna) vamos

    // Constructor: Inicializa la matriz de tamaño Vórtices x Aristas
    public matrizIncidencia(int vertices, int aristas) {
        this.numVertices = vertices;
        this.numAristas = aristas;
        this.matrizIncidencia = new int[vertices][aristas];
        this.columnaActual = 0; 
    }

    // Método para registrar una arista en su respectiva columna
    public boolean agregarArista(int u, int v, int peso) {
        // Validar que los vértices existan y que no nos hayamos pasado del límite de aristas
        if (u >= 0 && u < numVertices && v >= 0 && v < numVertices && columnaActual < numAristas) {
            
            // En la columna de la arista actual, colocamos el peso en ambos extremos
            matrizIncidencia[u][columnaActual] = peso;
            matrizIncidencia[v][columnaActual] = peso;
            
            columnaActual++; // Avanzamos a la siguiente columna (arista) para el próximo registro
            return true;
        }
        return false;
    }

    // Método para imprimir la matriz de incidencia de manera ordenada
    public void mostrarMatriz() {
        System.out.println("\n--- MATRIZ DE INCIDENCIA RESULTANTE ---");
        
        // Cabecera de columnas (Aristas: A0, A1, A2...)
        System.out.print("\t");
        for (int j = 0; j < numAristas; j++) {
            System.out.print("A" + j + "\t");
        }
        System.out.println("\n-------------------------------------------");

        // Filas de datos (Vértices)
        for (int i = 0; i < numVertices; i++) {
            System.out.print("V" + i + " |\t");
            for (int j = 0; j < numAristas; j++) {
                System.out.print(matrizIncidencia[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
    

