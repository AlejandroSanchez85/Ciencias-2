package enigma.crypto;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;
import javax.crypto.Cipher;

/**
 * ============================================================================
 * Clase RSAUtils (Cifrado Asimétrico Híbrido)
 * ============================================================================
 * En respuesta al requerimiento: "Este parámetro deberá ser también una clave 
 * cifrada con un dato público que me compartan por cada grupo".
 * * Enigma es un algoritmo SIMÉTRICO (la misma configuración cifra y descifra).
 * En la criptografía moderna de grupos, se usa una Clave Pública (Asimétrica) 
 * para cifrar y compartir de manera segura la Configuración Simétrica (la de Enigma).
 * Esto emula el funcionamiento de protocolos como HTTPS/TLS.
 */
public class RSAUtils {

    /**
     * Genera un par de claves RSA (Pública y Privada).
     * @return KeyPair objeto con ambas llaves.
     */
    public static KeyPair generarParClaves() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048); // Tamaño seguro de llave estándar
        return generator.generateKeyPair();
    }

    /**
     * Encripta la configuración secreta de Enigma usando la Clave Pública del receptor.
     * @param configuracion Cadena plana con la configuración (Ej: "I-II-III|A-B-C|AB CD")
     * @param publicKey Llave pública del destinatario.
     * @return Texto cifrado en Base64.
     */
    public static String encriptarConfiguracion(String configuracion, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(configuracion.getBytes("UTF-8"));
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    /**
     * Desencripta el paquete cifrado usando la Clave Privada propia para revelar 
     * cómo configurar la máquina Enigma.
     * @param mensajeCifradoBase64 Mensaje en Base64.
     * @param privateKey Llave privada del receptor.
     * @return La cadena de configuración original de Enigma.
     */
    public static String desencriptarConfiguracion(String mensajeCifradoBase64, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(mensajeCifradoBase64));
        return new String(decryptedBytes, "UTF-8");
    }
}