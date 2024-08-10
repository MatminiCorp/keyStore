package keystore.manager;
import keyStore.decypher.AESCipher128;

public class AESExample {

    public static void main(String[] args) throws Exception {
    	String originalString = "Texto para criptografar";
        String chave = "teste";
        AESCipher128 aes = new AESCipher128(chave);
        // Encrypt the original string
        String encryptedString = aes.encrypt(originalString);
        System.out.println("String Criptografada: " + encryptedString);

        aes = new AESCipher128(chave);
        String decryptedString = aes.decrypt(encryptedString);
        System.out.println("String Descriptografada: " + decryptedString);
    }

}
