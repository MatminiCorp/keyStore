package com.matmini.keyStore.decypher;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class AESCipher128 {
  
  private static String secret;
  private static AESCipher128 instance;
  
  private AESCipher128() {
  }
  
  public static AESCipher128 getInstance(String secret) {
    if (instance == null) {
      instance = new AESCipher128();
      AESCipher128.secret = secret;
    }
    return instance;
  }
  
  public static AESCipher128 getInstance() {
    return instance;
  }
  
  public String encrypt(String input) throws Exception {
    byte[] salt = generateSalt();
    IvParameterSpec iv = generateIv();
    
    SecretKey key = generateSecretKey(secret, salt);
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    cipher.init(Cipher.ENCRYPT_MODE, key, iv);
    byte[] cipherText = cipher.doFinal(input.getBytes());
    byte[] cipherWithSaltIv = new byte[salt.length + iv.getIV().length
        + cipherText.length];
    System.arraycopy(salt, 0, cipherWithSaltIv, 0, salt.length);
    System.arraycopy(iv.getIV(), 0, cipherWithSaltIv, salt.length,
        iv.getIV().length);
    System.arraycopy(cipherText, 0, cipherWithSaltIv,
        salt.length + iv.getIV().length, cipherText.length);
    return Base64.getEncoder().encodeToString(cipherWithSaltIv);
  }
  
  public String decrypt(String cipherText) throws Exception {
    byte[] decodedCipherText = Base64.getDecoder().decode(cipherText);
    byte[] salt = Arrays.copyOfRange(decodedCipherText, 0, 16);
    byte[] ivBytes = Arrays.copyOfRange(decodedCipherText, 16, 32);
    byte[] actualCipherText = Arrays.copyOfRange(decodedCipherText, 32,
        decodedCipherText.length);
    SecretKey key = generateSecretKey(secret, salt);
    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
    cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(ivBytes));
    byte[] plainText = cipher.doFinal(actualCipherText);
    return new String(plainText);
  }
  
  private SecretKey generateSecretKey(String password, byte[] salt)
      throws Exception {
    SecretKeyFactory factory = SecretKeyFactory
        .getInstance("PBKDF2WithHmacSHA256");
    PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
    SecretKey secret = new SecretKeySpec(
        factory.generateSecret(spec).getEncoded(), "AES");
    return secret;
  }
  
  private byte[] generateSalt() {
    byte[] salt = new byte[16];
    new SecureRandom().nextBytes(salt);
    return salt;
  }
  
  private IvParameterSpec generateIv() {
    byte[] iv = new byte[16];
    new SecureRandom().nextBytes(iv);
    return new IvParameterSpec(iv);
  }
  
}
