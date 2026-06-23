package estructuras.arbol;

public class NodoB {
    public int[] claves;
    public int t; // Grado mínimo
    public NodoB[] hijos;
    public int n; // Número actual de claves
    public boolean hoja;

    public NodoB(int t, boolean hoja) {
        this.t = t;
        this.hoja = hoja;
        this.claves = new int[2 * t - 1];
        this.hijos = new NodoB[2 * t];
        this.n = 0;
    }

    // Búsqueda secuencial en el nodo
    public NodoB buscar(int k) {
        int i = 0;
        while (i < n && k > claves[i]) {
            i++;
        }
        if (i < n && claves[i] == k) {
            return this;
        }
        if (hoja) {
            return null;
        }
        return hijos[i].buscar(k);
    }

    // Recorrido inorden para visualizar el árbol
    public void imprimir() {
        int i;
        for (i = 0; i < n; i++) {
            if (!hoja) {
                hijos[i].imprimir();
            }
            System.out.print(" " + claves[i]);
        }
        if (!hoja) {
            hijos[i].imprimir();
        }
    }
}