package enigma.core;

/**
 * ============================================================================
 * Clase Plugboard (Steckerbrett)
 * ============================================================================
 * El panel de conexiones frontales. Añade la mayor cantidad de complejidad
 * matemática (combinatoria) a la máquina.
 * Intercambia pares de letras (involución) antes de que entren a los rotores
 * y después de que salgan, justo antes de iluminar la bombilla.
 */
public class Plugboard {
    
    // Arreglo que mapea cada letra a sí misma por defecto, o a su pareja si está conectada.
    private int[] mapeo;

    /**
     * Inicializa el panel sin cables (cada letra se mapea a sí misma).
     */
    public Plugboard() {
        this.mapeo = new int[26];
        for (int i = 0; i < 26; i++) {
            this.mapeo[i] = i; // Identidad: P(x) = x
        }
    }

    /**
     * Agrega un cable entre dos letras.
     * P(a) = b  y  P(b) = a
     * @param conexiones String con los pares separados por espacio (ej. "AB CD EF").
     */
    public void configurarConexiones(String conexiones) {
        // Reiniciamos al estado base
        for (int i = 0; i < 26; i++) {
            this.mapeo[i] = i;
        }
        
        if (conexiones == null || conexiones.trim().isEmpty()) {
            return; // Sin conexiones
        }

        String[] pares = conexiones.split(" ");
        for (String par : pares) {
            if (par.length() == 2) {
                int letra1 = Alphabet.charToInt(par.charAt(0));
                int letra2 = Alphabet.charToInt(par.charAt(1));
                
                // Mapeo recíproco
                this.mapeo[letra1] = letra2;
                this.mapeo[letra2] = letra1;
            }
        }
    }

    /**
     * Pasa la señal a través del panel de conexiones.
     * Como es recíproco, el método se usa tanto a la entrada como a la salida.
     * @param c Señal a intercambiar (0-25).
     * @return Señal procesada (0-25).
     */
    public int intercambiar(int c) {
        return this.mapeo[c];
    }
}