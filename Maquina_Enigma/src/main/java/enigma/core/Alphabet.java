package enigma.core;

/**
 * ============================================================================
 * Clase Alphabet
 * ============================================================================
 * Esta clase utilitaria maneja la conversión matemática fundamental del 
 * algoritmo Enigma: transformar letras (A-Z) a números enteros (0-25) y 
 * viceversa. Trabajar con el grupo Z_26 es la base de las permutaciones.
 */
public class Alphabet {
    
    /**
     * Convierte un carácter a su valor numérico en el módulo 26.
     * * @param c El carácter a convertir.
     * @return Entero entre 0 y 25.
     */
    public static int charToInt(char c) {
        // Convertimos a mayúscula por si el usuario ingresó minúsculas
        char upper = Character.toUpperCase(c);
        // Restamos el valor ASCII de 'A' (65) para normalizar a 0-25
        return upper - 'A';
    }

    /**
     * Convierte un valor numérico (módulo 26) de vuelta a un carácter.
     * * @param i El número entero entre 0 y 25.
     * @return El carácter correspondiente en mayúscula.
     */
    public static char intToChar(int i) {
        // Sumamos el valor ASCII de 'A' (65) para recuperar la letra
        return (char) (i + 'A');
    }

    /**
     * Esta función garantiza que el resultado siempre esté en el rango [0, 25].
     * * @param a El número a evaluar.
     * @return El módulo matemático estricto sobre 26.
     */
    public static int mod26(int a) {
        return (a % 26 + 26) % 26;
    }
}