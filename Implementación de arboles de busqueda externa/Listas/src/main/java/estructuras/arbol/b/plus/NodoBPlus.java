package estructuras.arbol.b.plus;

public class NodoBPlus {
    public int[] claves;
    public NodoBPlus[] hijos; // Punteros a los hijos
    public int n;
    public boolean hoja;
    public NodoBPlus siguiente; // Puntero a la siguiente hoja (Lista enlazada)

    public NodoBPlus(int maxClaves, boolean hoja) {
        this.hoja = hoja;
        this.claves = new int[maxClaves];
        // Los nodos internos tienen maxClaves + 1 hijos
        this.hijos = new NodoBPlus[maxClaves + 1];
        this.n = 0;
        this.siguiente = null;
    }
}