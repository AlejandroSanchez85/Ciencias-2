package enigma.main;

import enigma.core.EnigmaMachine;
import enigma.core.Plugboard;
import enigma.core.Reflector;
import enigma.core.Rotor;
import enigma.crypto.RSAUtils;
import java.security.KeyPair;
import java.util.Scanner;

/**
 * ============================================================================
 * Clase Main (Punto de Entrada y Menu Consola)
 * ============================================================================
 * Implementa una interfaz interactiva y altamente descriptiva por consola
 * que agrupa el motor Enigma y el intercambio seguro de configuracion (RSA).
 */
public class Main {

    // Diccionario historico estatico (Hardware preconstruido de 1930)
    // Rotor I
    private static final String R1_W = "EKMFLGDQVZNTOWYHXUSPAIBRCJ";
    private static final char R1_N = 'Q';
    // Rotor II
    private static final String R2_W = "AJDKSIRUXBLHWTMCQGZNPYFVOE";
    private static final char R2_N = 'E';
    // Rotor III
    private static final String R3_W = "BDFHJLCPRTXVZNYEIWGAKMUSQO";
    private static final char R3_N = 'V';
    
    // Reflector B (Umkehrwalze B)
    private static final String REF_B = "YRUHQSLDPXNGOKMIEBFZCWVJAT";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in, "UTF-8");
        boolean salir = false;

        System.out.println("=========================================================");
        System.out.println("||         SIMULADOR CRIPTOGRAFICO ENIGMA M3           ||");
        System.out.println("||        Cifrado Simetrico + Intercambio RSA          ||");
        System.out.println("=========================================================");

        while (!salir) {
            System.out.println("\nSeleccione un modulo operativo:");
            System.out.println("1. Cifrar/Descifrar mensaje directo (Configuracion manual)");
            System.out.println("2. Simular Escenario Hibrido: Compartir parametros con Clave Publica");
            System.out.println("3. Salir");
            System.out.print("> Opcion: ");
            
            String opcion = scanner.nextLine();

            try {
                switch (opcion) {
                    case "1":
                        flujoDirecto(scanner);
                        break;
                    case "2":
                        flujoClavePublica(scanner);
                        break;
                    case "3":
                        salir = true;
                        System.out.println("Apagando rotores... Sistema finalizado.");
                        break;
                    default:
                        System.out.println("Error: Ingrese 1, 2 o 3.");
                }
            } catch (Exception e) {
                System.out.println("Error Critico del Sistema: " + e.getMessage());
                e.printStackTrace();
            }
        }
        scanner.close();
    }

    /**
     * Modulo 1: Flujo estandar de la maquina Enigma manual.
     */
    private static void flujoDirecto(Scanner scanner) {
        System.out.println("\n--- CONFIGURACION MANUAL ENIGMA ---");
        
        System.out.print("Ingrese la clave inicial de rotores (Ej. 'ABC' para los 3 rotores): ");
        String posiciones = scanner.nextLine().toUpperCase();
        if (posiciones.length() < 3) posiciones = "AAA";

        System.out.print("Ingrese conexiones de panel (Plugboard) (Ej. 'AB CD EF', o deje vacio): ");
        String conexiones = scanner.nextLine().toUpperCase();

        // 1. Instanciar Hardware
        Rotor izquierdo = new Rotor("III", R3_W, R3_N);
        Rotor medio = new Rotor("II", R2_W, R2_N);
        Rotor derecho = new Rotor("I", R1_W, R1_N);
        
        izquierdo.setPosicionActual(posiciones.charAt(0));
        medio.setPosicionActual(posiciones.charAt(1));
        derecho.setPosicionActual(posiciones.charAt(2));

        Reflector reflector = new Reflector("UKW-B", REF_B);
        Plugboard plugboard = new Plugboard();
        plugboard.configurarConexiones(conexiones);

        // 2. Ensamblar Maquina
        EnigmaMachine enigma = new EnigmaMachine(izquierdo, medio, derecho, reflector, plugboard);

        System.out.println("\n[SISTEMA EN LINEA] Estado Rotores: " + enigma.getEstadoRotores());
        System.out.print("Ingrese texto a cifrar/descifrar: ");
        String texto = scanner.nextLine().toUpperCase().replaceAll("[^A-Z]", ""); // Filtro estricto A-Z

        // 3. Proceso
        System.out.println("\nProcesando paso a paso...");
        StringBuilder resultado = new StringBuilder();
        
        for (char c : texto.toCharArray()) {
            char cifrado = enigma.cifrarCaracter(c);
            resultado.append(cifrado);
            System.out.println("Entrada: " + c + " -> Salida: " + cifrado + " | Rotores: " + enigma.getEstadoRotores());
        }

        System.out.println("==================================================");
        System.out.println("RESULTADO FINAL: " + resultado.toString());
        System.out.println("==================================================");
    }

    /**
     * Modulo 2: Simulacion del requerimiento "clave cifrada con dato publico del grupo".
     * Genera RSA, encripta la config Enigma, la transmite simuladamente y la usa.
     */
    private static void flujoClavePublica(Scanner scanner) throws Exception {
        System.out.println("\n--- SIMULACION DE PROTOCOLO DE INTERCAMBIO (HIBRIDO RSA + ENIGMA) ---");
        System.out.println("Fase 1: Generando par de llaves RSA para el Cuartel General...");
        
        KeyPair llaves = RSAUtils.generarParClaves();
        System.out.println("[OK] Clave Publica y Privada generadas con exito.");

        System.out.println("\nFase 2: El grupo remitente configura la maquina Enigma...");
        String configuracionSecreta = "POSICIONES=XMC;PLUG=AQ BR CX DI";
        System.out.println("Configuracion en texto plano (La Clave de Sesion): " + configuracionSecreta);

        System.out.println("\nFase 3: Cifrando la configuracion Enigma con la CLAVE PUBLICA del Cuartel...");
        String configCifrada = RSAUtils.encriptarConfiguracion(configuracionSecreta, llaves.getPublic());
        System.out.println("-> Paquete interceptable enviado por radio (Base64):");
        System.out.println(configCifrada);

        System.out.println("\nFase 4: Cuartel General recibe y descifra usando su CLAVE PRIVADA...");
        String configRecuperada = RSAUtils.desencriptarConfiguracion(configCifrada, llaves.getPrivate());
        System.out.println("[OK] Configuracion recuperada: " + configRecuperada);

        // Parsear configuracion simulada
        String[] partes = configRecuperada.split(";");
        String pos = partes[0].split("=")[1];
        String plug = partes[1].split("=")[1];

        System.out.println("\nFase 5: Configurando Maquina Enigma Receptora...");
        Rotor r3 = new Rotor("III", R3_W, R3_N);
        Rotor r2 = new Rotor("II", R2_W, R2_N);
        Rotor r1 = new Rotor("I", R1_W, R1_N);
        
        r3.setPosicionActual(pos.charAt(0));
        r2.setPosicionActual(pos.charAt(1));
        r1.setPosicionActual(pos.charAt(2));

        Reflector ref = new Reflector("UKW-B", REF_B);
        Plugboard pb = new Plugboard();
        pb.configurarConexiones(plug);

        EnigmaMachine enigmaReceptor = new EnigmaMachine(r3, r2, r1, ref, pb);
        System.out.println("Maquina lista y sincronizada. Estado: " + enigmaReceptor.getEstadoRotores());
        
        // Prueba de transmision de mensaje de inteligencia
        System.out.print("\nIngrese un mensaje para simular la transmision: ");
        String msg = scanner.nextLine().toUpperCase().replaceAll("[^A-Z]", "");
        
        // Maquina EMISOR para probar que ambas coinciden
        Rotor em3 = new Rotor("III", R3_W, R3_N); em3.setPosicionActual(pos.charAt(0));
        Rotor em2 = new Rotor("II", R2_W, R2_N); em2.setPosicionActual(pos.charAt(1));
        Rotor em1 = new Rotor("I", R1_W, R1_N); em1.setPosicionActual(pos.charAt(2));
        Plugboard emPb = new Plugboard(); emPb.configurarConexiones(plug);
        EnigmaMachine enigmaEmisor = new EnigmaMachine(em3, em2, em1, ref, emPb);

        String interceptado = enigmaEmisor.cifrarMensaje(msg);
        System.out.println("\n[RADIO] Mensaje cifrado transitando: " + interceptado);
        
        String descifrado = enigmaReceptor.cifrarMensaje(interceptado);
        System.out.println("[HQ] Mensaje descifrado en Cuartel: " + descifrado);
    }
}