package estructuras.arbol;

public class ArbolB {
    public NodoB raiz;
    public int t;

    public ArbolB(int t) {
        this.raiz = null;
        this.t = t;
    }

    public void imprimir() {
        if (raiz != null) raiz.imprimir();
        else System.out.println("Árbol vacío.");
    }

    public NodoB buscar(int k) {
        return (raiz == null) ? null : raiz.buscar(k);
    }

    // --- INSERCIÓN (Se mantiene igual) ---
    public void insertar(int k) {
        if (raiz == null) {
            raiz = new NodoB(t, true);
            raiz.claves[0] = k;
            raiz.n = 1;
        } else {
            if (raiz.n == 2 * t - 1) {
                NodoB s = new NodoB(t, false);
                s.hijos[0] = raiz;
                dividirHijo(s, 0, raiz);
                int i = (s.claves[0] < k) ? 1 : 0;
                insertarNoLleno(s.hijos[i], k);
                raiz = s;
            } else {
                insertarNoLleno(raiz, k);
            }
        }
    }

    private void insertarNoLleno(NodoB x, int k) {
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
            if (x.hijos[i].n == 2 * t - 1) {
                dividirHijo(x, i, x.hijos[i]);
                if (x.claves[i] < k) i++;
            }
            insertarNoLleno(x.hijos[i], k);
        }
    }

    private void dividirHijo(NodoB x, int i, NodoB y) {
        NodoB z = new NodoB(y.t, y.hoja);
        z.n = t - 1;
        for (int j = 0; j < t - 1; j++) z.claves[j] = y.claves[j + t];
        if (!y.hoja) {
            for (int j = 0; j < t; j++) z.hijos[j] = y.hijos[j + t];
        }
        y.n = t - 1;
        for (int j = x.n; j >= i + 1; j--) x.hijos[j + 1] = x.hijos[j];
        x.hijos[i + 1] = z;
        for (int j = x.n - 1; j >= i; j--) x.claves[j + 1] = x.claves[j];
        x.claves[i] = y.claves[t - 1];
        x.n++;
    }

    // --- ELIMINACIÓN COMPLETA (Algoritmos de Balanceo) ---
    public void eliminar(int k) {
        if (raiz == null) {
            System.out.println("El árbol está vacío.");
            return;
        }
        eliminarInterno(raiz, k);

        if (raiz.n == 0) {
            if (raiz.hoja) raiz = null;
            else raiz = raiz.hijos[0];
        }
    }

    private void eliminarInterno(NodoB x, int k) {
        int idx = encontrarClave(x, k);

        if (idx < x.n && x.claves[idx] == k) {
            if (x.hoja) eliminarDeHoja(x, idx);
            else eliminarDeNoHoja(x, idx);
        } else {
            if (x.hoja) {
                System.out.println("La clave " + k + " no existe en el árbol B.");
                return;
            }
            boolean esUltimoHijo = (idx == x.n);
            if (x.hijos[idx].n < t) llenar(x, idx);
            
            if (esUltimoHijo && idx > x.n) eliminarInterno(x.hijos[idx - 1], k);
            else eliminarInterno(x.hijos[idx], k);
        }
    }

    private int encontrarClave(NodoB x, int k) {
        int idx = 0;
        while (idx < x.n && x.claves[idx] < k) idx++;
        return idx;
    }

    private void eliminarDeHoja(NodoB x, int idx) {
        for (int i = idx + 1; i < x.n; i++) x.claves[i - 1] = x.claves[i];
        x.n--;
    }

    private void eliminarDeNoHoja(NodoB x, int idx) {
        int k = x.claves[idx];
        if (x.hijos[idx].n >= t) {
            int pred = obtenerPredecesor(x, idx);
            x.claves[idx] = pred;
            eliminarInterno(x.hijos[idx], pred);
        } else if (x.hijos[idx + 1].n >= t) {
            int suc = obtenerSucesor(x, idx);
            x.claves[idx] = suc;
            eliminarInterno(x.hijos[idx + 1], suc);
        } else {
            fusionar(x, idx);
            eliminarInterno(x.hijos[idx], k);
        }
    }

    private int obtenerPredecesor(NodoB x, int idx) {
        NodoB act = x.hijos[idx];
        while (!act.hoja) act = act.hijos[act.n];
        return act.claves[act.n - 1];
    }

    private int obtenerSucesor(NodoB x, int idx) {
        NodoB act = x.hijos[idx + 1];
        while (!act.hoja) act = act.hijos[0];
        return act.claves[0];
    }

    private void llenar(NodoB x, int idx) {
        if (idx != 0 && x.hijos[idx - 1].n >= t) prestarAnterior(x, idx);
        else if (idx != x.n && x.hijos[idx + 1].n >= t) prestarSiguiente(x, idx);
        else {
            if (idx != x.n) fusionar(x, idx);
            else fusionar(x, idx - 1);
        }
    }

    private void prestarAnterior(NodoB x, int idx) {
        NodoB hijo = x.hijos[idx];
        NodoB hermano = x.hijos[idx - 1];

        for (int i = hijo.n - 1; i >= 0; i--) hijo.claves[i + 1] = hijo.claves[i];
        if (!hijo.hoja) {
            for (int i = hijo.n; i >= 0; i--) hijo.hijos[i + 1] = hijo.hijos[i];
        }
        hijo.claves[0] = x.claves[idx - 1];
        if (!hijo.hoja) hijo.hijos[0] = hermano.hijos[hermano.n];
        x.claves[idx - 1] = hermano.claves[hermano.n - 1];
        hijo.n++;
        hermano.n--;
    }

    private void prestarSiguiente(NodoB x, int idx) {
        NodoB hijo = x.hijos[idx];
        NodoB hermano = x.hijos[idx + 1];

        hijo.claves[hijo.n] = x.claves[idx];
        if (!hijo.hoja) hijo.hijos[hijo.n + 1] = hermano.hijos[0];
        x.claves[idx] = hermano.claves[0];

        for (int i = 1; i < hermano.n; i++) hermano.claves[i - 1] = hermano.claves[i];
        if (!hermano.hoja) {
            for (int i = 1; i <= hermano.n; i++) hermano.hijos[i - 1] = hermano.hijos[i];
        }
        hijo.n++;
        hermano.n--;
    }

    private void fusionar(NodoB x, int idx) {
        NodoB hijo = x.hijos[idx];
        NodoB hermano = x.hijos[idx + 1];

        hijo.claves[t - 1] = x.claves[idx];
        for (int i = 0; i < hermano.n; i++) hijo.claves[i + t] = hermano.claves[i];
        if (!hijo.hoja) {
            for (int i = 0; i <= hermano.n; i++) hijo.hijos[i + t] = hermano.hijos[i];
        }
        for (int i = idx + 1; i < x.n; i++) x.claves[i - 1] = x.claves[i];
        for (int i = idx + 2; i <= x.n; i++) x.hijos[i - 1] = x.hijos[i];
        
        hijo.n += hermano.n + 1;
        x.n--;
    }
}