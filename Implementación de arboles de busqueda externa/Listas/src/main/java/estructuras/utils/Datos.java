package estructuras.utils;

import estructuras.arbol.ArbolB;
import estructuras.arbol.b.plus.ArbolBPlus;
import java.util.Random;

/**
 * Clase encargada de la generación y carga automática de valores aleatorios
 * en las estructuras de datos de tipo Árbol B y Árbol B+.
 */
public class Datos {
    
    public static void cargar(ArbolB arbolB, ArbolBPlus arbolBPlus, int cantidad) {
        System.out.println("\n[SISTEMA] Cargando " + cantidad + " claves aleatorias...");
        Random random = new Random();
        
        for (int i = 0; i < cantidad; i++) {
            // Genera un número matemático entero aleatorio entre 1 y 1000
            int numeroAleatorio = random.nextInt(1000) + 1;
            arbolB.insertar(numeroAleatorio);
            arbolBPlus.insertar(numeroAleatorio);
        }
        
        System.out.println("[SISTEMA] Carga aleatoria completada con éxito.");
    }
}