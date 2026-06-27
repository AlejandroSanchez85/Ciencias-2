/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package libreria_de_grafos;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Estudiantes
 */
public class listaIncidencia {
    

    // Clase interna (molde) para representar cada Arista
    public static class Arista {
        int id;     // Identificador único de la arista (A0, A1, A2...)
        int u, v;   // Vértices que conecta
        int peso;   // Peso/Costo de la arista

        public Arista(int id, int u, int v, int peso) {
            this.id = id;
            this.u = u;
            this.v = v;
            this.peso = peso;
        }

        // Método para darle un formato bonito al texto de la arista
        @Override
        public String toString() {
            return "A" + id + "(une V" + u + "-V" + v + ", peso:" + peso + ")";
        }
    }

    private List<List<Arista>> listaIncidencia;
    private int numVertices;
    private int contadorAristas; // Para asignar IDs automáticos (0, 1, 2...)

    // Constructor
    public listaIncidencia(int vertices) {
        this.numVertices = vertices;
        this.contadorAristas = 0;
        this.listaIncidencia = new ArrayList<>();
        
        // Inicializamos las sublistas para cada vértice
        for (int i = 0; i < vertices; i++) {
            this.listaIncidencia.add(new ArrayList<>());
        }
    }

    // Método para agregar la arista de manera no dirigida
    public boolean agregarArista(int u, int v, int peso) {
        if (u >= 0 && u < numVertices && v >= 0 && v < numVertices) {
            // Creamos un único objeto Arista
            Arista nuevaArista = new Arista(contadorAristas, u, v, peso);
            
            // Al ser NO DIRIGIDO, la misma arista incide (toca) a ambos vértices
            listaIncidencia.get(u).add(nuevaArista);
            listaIncidencia.get(v).add(nuevaArista);
            
            contadorAristas++; // Incrementamos el ID para la siguiente arista
            return true;
        }
        return false;
    }

    // Método para mostrar la lista en consola
    public void mostrarLista() {
        System.out.println("\n--- LISTA DE INCIDENCIA RESULTANTE ---");
        for (int i = 0; i < numVertices; i++) {
            System.out.print("Vértice V" + i + " está conectado por: ");
            List<Arista> aristasDelVertice = listaIncidencia.get(i);
            
            if (aristasDelVertice.isEmpty()) {
                System.out.print("Ninguna arista.");
            } else {
                for (Arista arista : aristasDelVertice) {
                    System.out.print("[" + arista + "] ");
                }
            }
            System.out.println();
        }
    }
}
    



