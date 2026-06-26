package enigma.core;

/**
 * ============================================================================
 * Clase Rotor
 * ============================================================================
 * Modela un rotor de la máquina Enigma. Cada rotor es una permutación fija
 * matemática de 26 elementos, que cambia dinámicamente con cada pulsación 
 * (desplazamiento u offset).
 */
public class Rotor {
    
    // Nombre identificador del rotor (ej. "I", "II", "III")
    private String nombre;
    
    // El cableado interno representado como una permutación (arreglo de 0 a 25)
    private int[] wiring;
    
    // El inverso del cableado para la corriente de retorno
    private int[] wiringInverso;
    
    // La posición de la muesca (notch) donde este rotor hace girar al siguiente
    private int notch;
    
    // Posición actual del rotor en la ventana (0 a 25) -> Equivalente a k en la ecuación
    private int posicionActual;
    
    // Ajuste del anillo (Ringstellung) (0 a 25) -> Cambia el cableado interno respecto al alfabeto exterior
    private int ajusteAnillo;

    /**
     * Constructor del Rotor.
     * @param nombre Identificador.
     * @param wiringString Cadena de 26 letras representando el cableado (ej. "EKMFLGDQVZNTOWYHXUSPAIBRCJ").
     * @param notch Letra de muesca que provoca el giro del siguiente rotor.
     */
    public Rotor(String nombre, String wiringString, char notch) {
        this.nombre = nombre;
        this.wiring = new int[26];
        this.wiringInverso = new int[26];
        
        // Convertimos el string a nuestro formato matemático numérico (0-25)
        for (int i = 0; i < 26; i++) {
            int charVal = Alphabet.charToInt(wiringString.charAt(i));
            this.wiring[i] = charVal;
            // Precomputamos la permutación inversa para el camino de vuelta de la corriente
            this.wiringInverso[charVal] = i; 
        }
        
        this.notch = Alphabet.charToInt(notch);
        this.posicionActual = 0; // Por defecto inicia en 'A' (0)
        this.ajusteAnillo = 0;   // Por defecto anillo en 'A' (0)
    }

    /**
     * Configura la posición inicial del rotor visible en la máquina.
     * @param c Carácter de A-Z.
     */
    public void setPosicionActual(char c) {
        this.posicionActual = Alphabet.charToInt(c);
    }

    /**
     * Configura el anillo (Ringstellung). Desplaza el núcleo del rotor.
     * @param c Carácter de A-Z.
     */
    public void setAjusteAnillo(char c) {
        this.ajusteAnillo = Alphabet.charToInt(c);
    }

    /**
     * Comprueba si el rotor está en su posición de muesca.
     * Fundamental para la anomalía de "doble paso" (double stepping).
     * @return true si la posición actual coincide con la muesca.
     */
    public boolean estaEnMuesca() {
        return this.posicionActual == this.notch;
    }

    /**
     * Ejecuta el avance (rotación) de este rotor en 1 paso (módulo 26).
     */
    public void avanzar() {
        this.posicionActual = Alphabet.mod26(this.posicionActual + 1);
    }

    /**
     * ========================================================================
     * ECUACIÓN MATEMÁTICA DE IDA (FORWARD PASS)
     * ========================================================================
     * Modela la entrada de la señal eléctrica desde la derecha hacia el reflector.
     * Fórmula: R(x) = ( W( (x + p - r) mod 26 ) - p + r ) mod 26
     * Donde: x = entrada, W = cableado, p = posición, r = anillo.
     * * @param c Señal entrante (0-25).
     * @return Señal saliente modificada (0-25).
     */
    public int forward(int c) {
        int desfase = this.posicionActual - this.ajusteAnillo;
        
        // 1. Calcular el pin de entrada afectado por la rotación
        int pinEntrada = Alphabet.mod26(c + desfase);
        
        // 2. Pasar por el cableado interno
        int pinSalida = this.wiring[pinEntrada];
        
        // 3. Compensar la rotación a la salida para contactar el siguiente elemento
        int resultado = Alphabet.mod26(pinSalida - desfase);
        
        return resultado;
    }

    /**
     * ========================================================================
     * ECUACIÓN MATEMÁTICA DE VUELTA (BACKWARD PASS)
     * ========================================================================
     * Modela la señal eléctrica rebotando desde el reflector hacia el panel.
     * Usa la permutación inversa del cableado.
     * Fórmula: R^-1(x) = ( W^-1( (x + p - r) mod 26 ) - p + r ) mod 26
     * * @param c Señal entrante (0-25).
     * @return Señal saliente modificada (0-25).
     */
    public int backward(int c) {
        int desfase = this.posicionActual - this.ajusteAnillo;
        
        int pinEntrada = Alphabet.mod26(c + desfase);
        int pinSalida = this.wiringInverso[pinEntrada]; // Usamos la inversa
        int resultado = Alphabet.mod26(pinSalida - desfase);
        
        return resultado;
    }
    
    // Método getter para el nombre (usado en la interfaz)
    public String getNombre() {
        return nombre;
    }
    
    public char getPosicionActualChar() {
        return Alphabet.intToChar(posicionActual);
    }
}