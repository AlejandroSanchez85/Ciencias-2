package enigma.core;

/**
 * ============================================================================
 * Clase Reflector (Umkehrwalze)
 * ============================================================================
 * Es el componente al final de los rotores. Toma la señal y la envía de vuelta
 * por una ruta diferente. Su permutación debe cumplir dos propiedades matemáticas:
 * 1. U = U^-1 (Es involutiva, si A -> B, entonces B -> A)
 * 2. U(x) != x (Nunca devuelve la misma letra, lo que impidió que una letra se 
 * cifrara como sí misma, el mayor error criptográfico de Enigma).
 */
public class Reflector {
    
    private String nombre;
    private int[] wiring;

    /**
     * Constructor del Reflector.
     * @param nombre Identificador (ej. "UKW-B").
     * @param wiringString Cadena de 26 letras (ej. "YRUHQSLDPXNGOKMIEBFZCWVJAT").
     */
    public Reflector(String nombre, String wiringString) {
        this.nombre = nombre;
        this.wiring = new int[26];
        
        for (int i = 0; i < 26; i++) {
            this.wiring[i] = Alphabet.charToInt(wiringString.charAt(i));
        }
    }

    /**
     * Refleja la señal entrante. Dado que no gira, no hay ecuaciones de desfase.
     * U(x) = Wiring[x]
     * @param c Señal de entrada (0-25).
     * @return Señal de salida (0-25).
     */
    public int reflejar(int c) {
        return this.wiring[c];
    }
}