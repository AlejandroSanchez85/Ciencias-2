package cifrado_asimetrico;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HashBinarioExperimento {
	
    // 1. TEXTO -> BINARIO	
    public static String textoABinario(String texto) {
        StringBuilder binario = new StringBuilder();

        for (char c : texto.toCharArray()) {
            binario.append(
                String.format("%8s", Integer.toBinaryString(c))
                        .replace(' ', '0')
            );
        }

        return binario.toString();
    }
    
    // 2. CONTAR PATRONES 00 01 10 11
    public static int[] contarPatrones(String binario) {
        int[] p = new int[4];

        for (int i = 0; i < binario.length() - 1; i += 2) {
            String par = binario.substring(i, i + 2);

            switch (par) {
                case "00": p[0]++; break;
                case "01": p[1]++; break;
                case "10": p[2]++; break;
                case "11": p[3]++; break;
            }
        }

        return p;
    }
    
    // 3. ESTADÍSTICAS -> BINARIO
    public static String estadisticasABinario(int[] p) {
        StringBuilder sb = new StringBuilder();

        for (int val : p) {
            sb.append(
                String.format("%8s", Integer.toBinaryString(val))
                        .replace(' ', '0')
            );
        }

        return sb.toString();
    }
    
    // 4. MEZCLA BINARIA (SEPARA 1s Y 0s)
    public static String mezclar(String binario) {
        StringBuilder unos = new StringBuilder();
        StringBuilder ceros = new StringBuilder();

        for (char b : binario.toCharArray()) {
            if (b == '1') unos.append('1');
            else ceros.append('0');
        }

        return unos.toString() + ceros.toString();
    }

    // 5. HASH FINAL
    public static String generarHash(String texto) {

        String binario = textoABinario(texto);
        int[] patrones = contarPatrones(binario);
        String statsBin = estadisticasABinario(patrones);

        String mezcla = mezclar(statsBin);

        return mezcla;
    }
    
    // 6. RECONSTRUCCIÓN (SIMULADA)
    public static void reconstruir(String hash, String textoOriginal) {

        System.out.println("\n==============================");
        System.out.println(" RECONSTRUCCIÓN DEL HASH ");
        System.out.println("==============================\n");

        System.out.println("Hash recibido:");
        System.out.println(hash + "\n");

        System.out.println("Intentando reconstruir posibles textos...\n");

        List<String> intentos = new ArrayList<>();

        Random r = new Random(textoOriginal.length() * 31);

        // Generamos posibles candidatos aleatorios
        for (int i = 0; i < 10; i++) {

            StringBuilder candidato = new StringBuilder();

            for (int j = 0; j < textoOriginal.length(); j++) {

                char c = (char) (r.nextInt(26) + 'A');
                candidato.append(c);
            }

            String bin = textoABinario(candidato.toString());
            int[] p = contarPatrones(bin);
            String stats = estadisticasABinario(p);
            String mezcla = mezclar(stats);

            System.out.println("Intento " + (i + 1) + ": " + candidato);
            System.out.println("Hash: " + mezcla);

            if (mezcla.equals(hash)) {
                System.out.println("✔ COMPATIBLE\n");
            } else {
                System.out.println("✖ NO EXACTO\n");
            }

            intentos.add(candidato.toString());
        }

        System.out.println("CONCLUSIÓN:");
        System.out.println("Existen múltiples posibles entradas compatibles.");
        System.out.println("No se puede determinar el texto original de forma única.");
    }

    // 7. MAIN
    public static void main(String[] args) {

        String texto = "kjneisag@udistrital.edu.co";

        System.out.println("TEXTO ORIGINAL: " + texto);

        String hash = generarHash(texto);

        System.out.println("\nHASH GENERADO:");
        System.out.println(hash);

        reconstruir(hash, texto);
    }
}

