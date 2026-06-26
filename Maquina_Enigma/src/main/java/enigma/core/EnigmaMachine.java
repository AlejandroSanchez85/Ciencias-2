package enigma.core;

/**
 * ============================================================================
 * Clase EnigmaMachine
 * ============================================================================
 * Representa la máquina Enigma ensamblada.
 * Combina el Plugboard, los 3 Rotores y el Reflector.
 * Contiene el algoritmo maestro de cifrado y el mecanismo del odómetro.
 */
public class EnigmaMachine {
    
    private Rotor rotorDerecho;   // Rotor Rápido
    private Rotor rotorMedio;     // Rotor Medio
    private Rotor rotorIzquierdo; // Rotor Lento
    private Reflector reflector;
    private Plugboard plugboard;

    /**
     * Ensambla la máquina con los componentes especificados.
     */
    public EnigmaMachine(Rotor izquierdo, Rotor medio, Rotor derecho, Reflector reflector, Plugboard plugboard) {
        this.rotorIzquierdo = izquierdo;
        this.rotorMedio = medio;
        this.rotorDerecho = derecho;
        this.reflector = reflector;
        this.plugboard = plugboard;
    }

    /**
     * ========================================================================
     * MECANISMO DE AVANCE (STEPPING MECHANISM - ODOMETER)
     * ========================================================================
     * Simula el avance electromecánico.
     * REGLA CLAVE: El doble paso (Double Stepping Anomaly).
     * Si el rotor medio está en muesca, empuja al izquierdo PERO TAMBIÉN 
     * avanza él mismo de nuevo.
     */
    private void avanzarRotores() {
        boolean medioEnMuesca = this.rotorMedio.estaEnMuesca();
        boolean derechoEnMuesca = this.rotorDerecho.estaEnMuesca();

        // Si el medio está en su muesca, empuja al izquierdo y a sí mismo
        if (medioEnMuesca) {
            this.rotorIzquierdo.avanzar();
            this.rotorMedio.avanzar();
        } 
        // Si el derecho está en muesca, empuja al medio
        else if (derechoEnMuesca) {
            this.rotorMedio.avanzar();
        }

        // El rotor derecho (rápido) SIEMPRE avanza con cada pulsación de tecla
        this.rotorDerecho.avanzar();
    }

    /**
     * ========================================================================
     * ECUACIÓN MAESTRA DE CIFRADO E(x)
     * ========================================================================
     * Cifra un solo carácter.
     * E(x) = P^-1 ( L^-1 ( M^-1 ( R^-1 ( U ( R ( M ( L ( P(x) ) ) ) ) ) ) ) )
     */
    public char cifrarCaracter(char c) {
        // Solo procesamos letras
        if (!Character.isLetter(c)) {
            return c; // Ignorar espacios y puntuación
        }

        // 1. AVANCE DE ROTORES (Siempre antes del contacto eléctrico)
        avanzarRotores();

        // Convertir a número entero
        int señal = Alphabet.charToInt(c);

        // 2. PANEL DE CONEXIONES (Entrada) -> P(x)
        señal = plugboard.intercambiar(señal);

        // 3. ROTORES IDA (Derecho -> Medio -> Izquierdo) -> L(M(R(x)))
        señal = rotorDerecho.forward(señal);
        señal = rotorMedio.forward(señal);
        señal = rotorIzquierdo.forward(señal);

        // 4. REFLECTOR -> U(x)
        señal = reflector.reflejar(señal);

        // 5. ROTORES VUELTA (Izquierdo -> Medio -> Derecho) -> R^-1(M^-1(L^-1(x)))
        señal = rotorIzquierdo.backward(señal);
        señal = rotorMedio.backward(señal);
        señal = rotorDerecho.backward(señal);

        // 6. PANEL DE CONEXIONES (Salida) -> P^-1(x) (Que es igual a P(x))
        señal = plugboard.intercambiar(señal);

        // Convertir de vuelta a letra
        return Alphabet.intToChar(señal);
    }

    /**
     * Cifra un string completo.
     * @param mensaje Mensaje en texto plano o cifrado.
     * @return Mensaje resultante.
     */
    public String cifrarMensaje(String mensaje) {
        StringBuilder resultado = new StringBuilder();
        for (char c : mensaje.toCharArray()) {
            if (Character.isLetter(c)) {
                resultado.append(cifrarCaracter(c));
            } else {
                resultado.append(c); // Conserva espacios si se desea
            }
        }
        return resultado.toString();
    }
    
    // Método para imprimir el estado actual visual en consola
    public String getEstadoRotores() {
        return "[" + rotorIzquierdo.getPosicionActualChar() + "]-" +
               "[" + rotorMedio.getPosicionActualChar() + "]-" +
               "[" + rotorDerecho.getPosicionActualChar() + "]";
    }
}