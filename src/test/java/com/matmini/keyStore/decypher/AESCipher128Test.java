package com.matmini.keyStore.decypher;

import org.junit.jupiter.api.*;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class AESCipher128Test {
  
  private static final String SECRET = "testPassword123";
  private static final String WRONG_SECRET = "wrongPassword456";
  
  @BeforeEach
  void resetSingleton() throws Exception {
    Field instanceField = AESCipher128.class.getDeclaredField("instance");
    instanceField.setAccessible(true);
    instanceField.set(null, null);
  }
  
  @Test
  void testEncryptDecryptSuccess() throws Exception {
    AESCipher128 cipher = AESCipher128.getInstance(SECRET);
    String original = "mySuperSecret";
    String encrypted = cipher.encrypt(original);
    assertNotEquals(original, encrypted);
    String decrypted = cipher.decrypt(encrypted);
    assertEquals(original, decrypted);
  }
  
  @Test
  void testSingletonReturnsSameInstance() {
    AESCipher128 cipher1 = AESCipher128.getInstance(SECRET);
    AESCipher128 cipher2 = AESCipher128.getInstance("anotherSecret");
    assertSame(cipher1, cipher2,
        "Singleton should return same instance even with different secret");
  }
  
  @Test
  void testGetInstanceWithoutInitializationFails() {
    Exception exception = assertThrows(IllegalStateException.class, () -> {
      AESCipher128.getInstance();
    });
    assertTrue(exception.getMessage().contains("not initialized"));
  }
  
  @Test
  void testEncryptProducesDifferentOutputEachTimeDueToIV() throws Exception {
    AESCipher128 cipher = AESCipher128.getInstance(SECRET);
    String text = "repeatable";
    String encrypted1 = cipher.encrypt(text);
    String encrypted2 = cipher.encrypt(text);
    assertNotEquals(encrypted1, encrypted2,
        "IV randomization should produce different results");
  }
  
  @Test
  void testDecryptWithWrongSecretFailsIfSingletonIsBypassed() throws Exception {
    AESCipher128 cipher = AESCipher128.getInstance(SECRET);
    String original = "confidential";
    String encrypted = cipher.encrypt(original);
    
    Field instanceField = AESCipher128.class.getDeclaredField("instance");
    instanceField.setAccessible(true);
    instanceField.set(null, null);
    
    AESCipher128 wrongCipher = AESCipher128.getInstance(WRONG_SECRET);
    assertThrows(Exception.class, () -> wrongCipher.decrypt(encrypted));
  }
}
