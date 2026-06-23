package estructuras.main;

import estructuras.arbol.ArbolB;
import estructuras.arbol.b.plus.ArbolBPlus;
import estructuras.utils.Datos;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("--- Configuracion Inicial ---");
        System.out.print("Ingrese el grado minimo (t) para los arboles (ej. 3): ");
        int t = scanner.nextInt();

        ArbolB arbolB = new ArbolB(t);
        ArbolBPlus arbolBPlus = new ArbolBPlus(t);
        
        System.out.println("\n--- Precarga Automatica de Datos Aleatorios ---");
        System.out.println("Seleccione el volumen de datos a cargar:");
        System.out.println("1. Carga de prueba aleatoria (100 registros)");
        System.out.println("2. Carga optimizada avanzada (1000 registros)");
        System.out.println("3. Sin carga inicial (Empezar vacio)");
        System.out.print("Opcion de carga: ");
        int opcionCarga = scanner.nextInt();
        
        if (opcionCarga == 1) {
            Datos.cargar(arbolB, arbolBPlus, 100);
        } else if (opcionCarga == 2) {
            Datos.cargar(arbolB, arbolBPlus, 1000);
        } else {
            System.out.println("\n[SISTEMA] Iniciando con arboles vacios.");
        }

        int opcion = 0;

        while (opcion != 7) {
            System.out.println("\n===== MENU ARBOLES B Y B+ =====");
            System.out.println("1. Insertar nueva clave (Ambos)");
            System.out.println("2. Eliminar clave");
            System.out.println("3. Buscar clave puntual");
            System.out.println("4. Busqueda por Rango (Solo Arbol B+)");
            System.out.println("5. Imprimir Arbol B (Recorrido Inorden)");
            System.out.println("6. Imprimir Arbol B+ (Lista enlazada de hojas)");
            System.out.println("7. Salir");
            System.out.print("Elija una opcion: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    System.out.print("Ingrese el numero a insertar: ");
                    int claveInsercion = scanner.nextInt();
                    arbolB.insertar(claveInsercion);
                    arbolBPlus.insertar(claveInsercion);
                    System.out.println("Clave " + claveInsercion + " insertada con exito.");
                    break;
                    
                case 2:
                    System.out.print("Ingrese la clave a eliminar: ");
                    int claveEliminacion = scanner.nextInt();
                    
                    System.out.println("¿De cual arbol desea eliminar?");
                    System.out.println("1. Arbol B");
                    System.out.println("2. Arbol B+");
                    System.out.println("3. Ambos");
                    System.out.print("Opcion: ");
                    int opcEliminar = scanner.nextInt();
                    
                    if(opcEliminar == 1 || opcEliminar == 3) {
                        System.out.println("Eliminando de Arbol B...");
                        arbolB.eliminar(claveEliminacion);
                    }
                    if(opcEliminar == 2 || opcEliminar == 3) {
                        System.out.println("Eliminando de Arbol B+...");
                        arbolBPlus.eliminar(claveEliminacion);
                    }
                    System.out.println("Proceso de eliminacion finalizado.");
                    break;
                    
                case 3:
                    System.out.print("Ingrese el numero a buscar: ");
                    int claveBusqueda = scanner.nextInt();
                    
                    boolean enB = (arbolB.buscar(claveBusqueda) != null);
                    boolean enBPlus = arbolBPlus.buscar(claveBusqueda);
                    
                    System.out.println("Resultados de busqueda:");
                    System.out.println("- Arbol B: " + (enB ? "Encontrado" : "No encontrado"));
                    System.out.println("- Arbol B+: " + (enBPlus ? "Encontrado" : "No encontrado"));
                    break;
                    
                case 4:
                    System.out.print("Inicio del rango: ");
                    int r1 = scanner.nextInt();
                    System.out.print("Fin del rango: ");
                    int r2 = scanner.nextInt();
                    arbolBPlus.buscarPorRango(r1, r2);
                    break;
                    
                case 5:
                    System.out.println("\nContenido del Arbol B (Recorrido Inorden):");
                    arbolB.imprimir();
                    System.out.println();
                    break;
                    
                case 6:
                    System.out.println("\nContenido del Arbol B+ (Recorrido Lineal de Hojas):");
                    arbolBPlus.imprimirHojas();
                    break;
                    
                case 7:
                    System.out.println("Finalizando programa...");
                    break;
                    
                default:
                    System.out.println("Opcion no valida. Intente nuevamente.");
            }
        }
        scanner.close();
    }
}