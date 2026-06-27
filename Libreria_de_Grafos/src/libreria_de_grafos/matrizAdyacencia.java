/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libreria_de_grafos;

/**
 *
 * @author Kevin
 */
public class matrizAdyacencia {
    
  
    private int[][] matrizAdyacencia;
    private int numVertices;

    // Constructor: Inicializa la matriz cuadrada
    public matrizAdyacencia(int vertices) {
        this.numVertices = vertices;
        this.matrizAdyacencia = new int[vertices][vertices];
    }

    // Método para añadir una arista (No dirigida y con peso)
    public boolean agregarArista(int u, int v, int peso) {
        // Validamos que los vértices existan dentro del rango
        if (u >= 0 && u < numVertices && v >= 0 && v < numVertices) {
            matrizAdyacencia[u][v] = peso;
            matrizAdyacencia[v][u] = peso; // Bidireccional
            return true; // Éxito
        }
        return false; // Error: Vértices fuera de rango
    }

    // Método para imprimir la matriz de forma limpia
    public void mostrarMatriz() {
        System.out.println("\n--- MATRIZ DE ADYACENCIA RESULTANTE ---");
        
        // Cabecera de columnas
        System.out.print("\t");
        for (int i = 0; i < numVertices; i++) {
            System.out.print("V" + i + "\t");
        }
        System.out.println("\n-------------------------------------------");

        // Filas de datos
        for (int i = 0; i < numVertices; i++) {
            System.out.print("V" + i + " |\t");
            for (int j = 0; j < numVertices; j++) {
                System.out.print(matrizAdyacencia[i][j] + "\t");
            }
            System.out.println();
        }
    }
}

    

