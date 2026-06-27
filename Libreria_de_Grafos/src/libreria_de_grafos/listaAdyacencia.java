/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package libreria_de_grafos;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Estudiantes
 */
public class listaAdyacencia {




    // Clase para representar una arista con peso
    static class Arista {
        int destino;
        int peso;

        public Arista(int destino, int peso) {
            this.destino = destino;
            this.peso = peso;
        }

        @Override
        public String toString() {
            return "(" + destino + ", Peso: " + peso + ")";
        }
    }

    private int vertices;
    private List<List<Arista>> listaAdyacencia;

    // Constructor del grafo
    public listaAdyacencia(int vertices) {
        this.vertices = vertices;
        this.listaAdyacencia = new ArrayList<>();
        
        // Inicializar las listas para cada vértice
        for (int i = 0; i < vertices; i++) {
            this.listaAdyacencia.add(new ArrayList<>());
        }
    }

    // Método para agregar una arista (No dirigido)
    public void agregarArista(int origen, int destino, int peso) {
        // Añadir arista de origen a destino
        this.listaAdyacencia.get(origen).add(new Arista(destino, peso));
        
        // Como es NO dirigido, añadimos también de destino a origen
        this.listaAdyacencia.get(destino).add(new Arista(origen, peso));
    }

    // Método para imprimir la lista de adyacencia
    public void imprimirGrafo() {
        System.out.println("--- Lista de Adyacencia del Grafo ---");
        for (int i = 0; i < vertices; i++) {
            System.out.print("Vértice " + i + " se conecta con: ");
            System.out.println(this.listaAdyacencia.get(i));
        }
    }

  
}


