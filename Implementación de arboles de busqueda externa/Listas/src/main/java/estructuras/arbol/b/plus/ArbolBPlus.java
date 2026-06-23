package estructuras.arbol.b.plus;

public class ArbolBPlus {
    private NodoBPlus raiz;
    private int t;
    private int maxClaves;

    public ArbolBPlus(int t) {
        this.t = t;
        this.maxClaves = 2 * t - 1;
        this.raiz = new NodoBPlus(maxClaves, true);
    }

    // --- INSERCIÓN ---
    public void insertar(int clave) {
        NodoBPlus r = raiz;
        if (r.n == maxClaves) {
            NodoBPlus s = new NodoBPlus(maxClaves, false);
            raiz = s;
            s.hijos[0] = r;
            dividirHijo(s, 0, r);
            insertarNoLleno(s, clave);
        } else {
            insertarNoLleno(r, clave);
        }
    }

    private void insertarNoLleno(NodoBPlus x, int k) {
        int i = x.n - 1;
        if (x.hoja) {
            while (i >= 0 && x.claves[i] > k) {
                x.claves[i + 1] = x.claves[i];
                i--;
            }
            x.claves[i + 1] = k;
            x.n++;
        } else {
            while (i >= 0 && x.claves[i] > k) i--;
            i++;
            if (x.hijos[i].n == maxClaves) {
                dividirHijo(x, i, x.hijos[i]);
                if (x.claves[i] < k) i++;
            }
            insertarNoLleno(x.hijos[i], k);
        }
    }

    private void dividirHijo(NodoBPlus x, int i, NodoBPlus y) {
        NodoBPlus z = new NodoBPlus(maxClaves, y.hoja);
        z.n = t - 1;

        if (y.hoja) {
            for (int j = 0; j < t - 1; j++) z.claves[j] = y.claves[j + t];
            z.n = t; 
            z.siguiente = y.siguiente;
            y.siguiente = z;
        } else {
            for (int j = 0; j < t - 1; j++) z.claves[j] = y.claves[j + t];
            for (int j = 0; j < t; j++) z.hijos[j] = y.hijos[j + t];
        }
        
        y.n = t - 1;

        for (int j = x.n; j >= i + 1; j--) x.hijos[j + 1] = x.hijos[j];
        x.hijos[i + 1] = z;

        for (int j = x.n - 1; j >= i; j--) x.claves[j + 1] = x.claves[j];
        x.claves[i] = y.claves[t - 1];
        x.n++;
    }

    // --- BÚSQUEDAS ---
    public boolean buscar(int k) {
        NodoBPlus actual = raiz;
        while (!actual.hoja) {
            int i = 0;
            while (i < actual.n && k >= actual.claves[i]) i++;
            actual = actual.hijos[i];
        }
        for (int i = 0; i < actual.n; i++) {
            if (actual.claves[i] == k) return true;
        }
        return false;
    }

    public void buscarPorRango(int inicio, int fin) {
        NodoBPlus actual = raiz;
        while (!actual.hoja) {
            int i = 0;
            while (i < actual.n && inicio >= actual.claves[i]) i++;
            actual = actual.hijos[i];
        }

        System.out.print("Rango [" + inicio + " - " + fin + "]: ");
        boolean encontrado = false;
        
        while (actual != null) {
            for (int i = 0; i < actual.n; i++) {
                if (actual.claves[i] >= inicio && actual.claves[i] <= fin) {
                    System.out.print(actual.claves[i] + " ");
                    encontrado = true;
                } else if (actual.claves[i] > fin) {
                    System.out.println();
                    return; 
                }
            }
            actual = actual.siguiente;
        }
        if (!encontrado) System.out.print("Ningún valor en ese rango.");
        System.out.println();
    }

    // --- ELIMINACIÓN ---
    public void eliminar(int k) {
        if (raiz == null) {
            System.out.println("El árbol B+ está vacío.");
            return;
        }
        eliminarInterno(raiz, null, -1, k);
        
        // Si la raíz queda vacía pero tiene hijos, el primer hijo pasa a ser la nueva raíz
        if (raiz.n == 0 && !raiz.hoja) {
            raiz = raiz.hijos[0];
        }
    }

    private void eliminarInterno(NodoBPlus actual, NodoBPlus padre, int indiceEnPadre, int k) {
        int i = 0;
        while (i < actual.n && k > actual.claves[i]) {
            i++;
        }

        if (actual.hoja) {
            // Eliminar de la hoja si la clave existe
            boolean encontrado = false;
            for (int j = 0; j < actual.n; j++) {
                if (actual.claves[j] == k) {
                    encontrado = true;
                    // Desplazar claves matemáticamente
                    for (int x = j; x < actual.n - 1; x++) {
                        actual.claves[x] = actual.claves[x + 1];
                    }
                    actual.n--;
                    break;
                }
            }
            
            if (!encontrado) {
                System.out.println("La clave " + k + " no existe en el árbol B+.");
                return;
            }

            // Validar Underflow en la hoja
            if (actual != raiz && actual.n < t - 1) {
                balancearHoja(actual, padre, indiceEnPadre);
            }
        } else {
            // Si es nodo interno, solo usamos como índice para bajar
            if (i < actual.n && k == actual.claves[i]) {
                i++; // Desplazarse a la derecha de la clave separadora
            }
            eliminarInterno(actual.hijos[i], actual, i, k);
            
            // Validar Underflow en el nodo interno al retornar de la recursión
            if (actual != raiz && actual.n < t - 1) {
                balancearInterno(actual, padre, indiceEnPadre);
            }
        }
    }

    private void balancearHoja(NodoBPlus hoja, NodoBPlus padre, int idx) {
        // Regla 1: Préstamo del hermano izquierdo
        if (idx > 0 && padre.hijos[idx - 1].n >= t) {
            NodoBPlus hermanoIzq = padre.hijos[idx - 1];
            for (int j = hoja.n; j > 0; j--) hoja.claves[j] = hoja.claves[j - 1];
            hoja.claves[0] = hermanoIzq.claves[hermanoIzq.n - 1];
            hoja.n++;
            hermanoIzq.n--;
            padre.claves[idx - 1] = hoja.claves[0];
            return;
        }
        // Regla 2: Préstamo del hermano derecho
        if (idx < padre.n && padre.hijos[idx + 1].n >= t) {
            NodoBPlus hermanoDer = padre.hijos[idx + 1];
            hoja.claves[hoja.n] = hermanoDer.claves[0];
            hoja.n++;
            for (int j = 0; j < hermanoDer.n - 1; j++) hermanoDer.claves[j] = hermanoDer.claves[j + 1];
            hermanoDer.n--;
            padre.claves[idx] = hermanoDer.claves[0];
            return;
        }
        // Regla 3: Fusión (Merge) con izquierdo o derecho
        if (idx > 0) {
            NodoBPlus hermanoIzq = padre.hijos[idx - 1];
            for (int j = 0; j < hoja.n; j++) hermanoIzq.claves[hermanoIzq.n + j] = hoja.claves[j];
            hermanoIzq.n += hoja.n;
            hermanoIzq.siguiente = hoja.siguiente; // Mantener lista enlazada
            eliminarClavePadre(padre, idx - 1, idx);
        } else {
            NodoBPlus hermanoDer = padre.hijos[idx + 1];
            for (int j = 0; j < hermanoDer.n; j++) hoja.claves[hoja.n + j] = hermanoDer.claves[j];
            hoja.n += hermanoDer.n;
            hoja.siguiente = hermanoDer.siguiente; // Mantener lista enlazada
            eliminarClavePadre(padre, idx, idx + 1);
        }
    }

    private void balancearInterno(NodoBPlus nodo, NodoBPlus padre, int idx) {
        // Lógica de préstamo y fusión para nodos índice (Mismo principio que B normal)
        if (idx > 0 && padre.hijos[idx - 1].n >= t) {
            NodoBPlus hermanoIzq = padre.hijos[idx - 1];
            for (int j = nodo.n; j > 0; j--) nodo.claves[j] = nodo.claves[j - 1];
            for (int j = nodo.n + 1; j > 0; j--) nodo.hijos[j] = nodo.hijos[j - 1];
            nodo.claves[0] = padre.claves[idx - 1];
            nodo.hijos[0] = hermanoIzq.hijos[hermanoIzq.n];
            nodo.n++;
            padre.claves[idx - 1] = hermanoIzq.claves[hermanoIzq.n - 1];
            hermanoIzq.n--;
            return;
        }
        if (idx < padre.n && padre.hijos[idx + 1].n >= t) {
            NodoBPlus hermanoDer = padre.hijos[idx + 1];
            nodo.claves[nodo.n] = padre.claves[idx];
            nodo.hijos[nodo.n + 1] = hermanoDer.hijos[0];
            nodo.n++;
            padre.claves[idx] = hermanoDer.claves[0];
            for (int j = 0; j < hermanoDer.n - 1; j++) hermanoDer.claves[j] = hermanoDer.claves[j + 1];
            for (int j = 0; j < hermanoDer.n; j++) hermanoDer.hijos[j] = hermanoDer.hijos[j + 1];
            hermanoDer.n--;
            return;
        }
        if (idx > 0) {
            NodoBPlus hermanoIzq = padre.hijos[idx - 1];
            hermanoIzq.claves[hermanoIzq.n] = padre.claves[idx - 1];
            for (int j = 0; j < nodo.n; j++) hermanoIzq.claves[hermanoIzq.n + 1 + j] = nodo.claves[j];
            for (int j = 0; j <= nodo.n; j++) hermanoIzq.hijos[hermanoIzq.n + 1 + j] = nodo.hijos[j];
            hermanoIzq.n += nodo.n + 1;
            eliminarClavePadre(padre, idx - 1, idx);
        } else {
            NodoBPlus hermanoDer = padre.hijos[idx + 1];
            nodo.claves[nodo.n] = padre.claves[idx];
            for (int j = 0; j < hermanoDer.n; j++) nodo.claves[nodo.n + 1 + j] = hermanoDer.claves[j];
            for (int j = 0; j <= hermanoDer.n; j++) nodo.hijos[nodo.n + 1 + j] = hermanoDer.hijos[j];
            nodo.n += hermanoDer.n + 1;
            eliminarClavePadre(padre, idx, idx + 1);
        }
    }

    private void eliminarClavePadre(NodoBPlus padre, int idxClave, int idxHijo) {
        for (int i = idxClave; i < padre.n - 1; i++) padre.claves[i] = padre.claves[i + 1];
        for (int i = idxHijo; i < padre.n; i++) padre.hijos[i] = padre.hijos[i + 1];
        padre.n--;
    }

    // --- IMPRESIÓN ---
    public void imprimirHojas() {
        if (raiz == null || raiz.n == 0) {
            System.out.println("Árbol vacío.");
            return;
        }
        NodoBPlus actual = raiz;
        while (!actual.hoja) actual = actual.hijos[0];
        
        System.out.print("Hojas (Lista B+): ");
        while (actual != null) {
            System.out.print("[");
            for (int i = 0; i < actual.n; i++) {
                System.out.print(actual.claves[i] + (i < actual.n - 1 ? "," : ""));
            }
            System.out.print("] -> ");
            actual = actual.siguiente;
        }
        System.out.println("NULL");
    }
}