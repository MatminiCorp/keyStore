package com.matmini.keyStore.decypher;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.matmini.keyStore.manager.FilesManager;

public class SecretKeyValidatorTest {
  
  private static final File fileWithValidData = new File(
      "src/test/resources/test_file.json");
  
  @BeforeEach
  void resetSingletons() throws Exception {
    Field aesField = AESCipher128.class.getDeclaredField("instance");
    aesField.setAccessible(true);
    aesField.set(null, null);
    
    Field fmField = FilesManager.class.getDeclaredField("instance");
    fmField.setAccessible(true);
    fmField.set(null, null);
  }
  
  @Test
  void testValidDecryptionFromFileReturnsTrue() {
    FilesManager.getInstance(fileWithValidData);
    boolean result = SecretKeyValidator
        .isValidSecretByFile("teste".toCharArray());
    assertTrue(result, "Decryption should succeed with the correct key");
  }
  
  @Test
  void testInvalidDecryptionFromFileReturnsFalse() {
    FilesManager.getInstance(fileWithValidData);
    boolean result = SecretKeyValidator
        .isValidSecretByFile("wrong".toCharArray());
    assertFalse(result, "Decryption should fail with a wrong key");
  }
  
  @Test
  void testEmptyContentReturnsTrue() {
    File dummy = new File("empty_test_file.json");
    FilesManager.getInstance(dummy).overwriteContentAsMap(new HashMap<>());
    boolean result = SecretKeyValidator
        .isValidSecretByFile("any".toCharArray());
    assertTrue(result, "Empty content should return true");
  }
  
  @Test
  void testNullContentReturnsTrue() {
    File dummy = new File("null_test_file.json");
    FilesManager.getInstance(dummy).overwriteContentAsMap(null);
    boolean result = SecretKeyValidator
        .isValidSecretByFile("any".toCharArray());
    assertTrue(result, "Null content should return true");
  }
}
