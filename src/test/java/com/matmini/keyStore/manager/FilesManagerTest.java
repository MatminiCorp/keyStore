package com.matmini.keyStore.manager;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.util.*;

import com.matmini.keyStore.decypher.AESCipher128;
import org.junit.jupiter.api.*;

public class FilesManagerTest {
  
  private static final String SECRET = "teste";
  private File testFile;
  
  @BeforeEach
  public void resetSingletons() throws Exception {
    Field instanceField = FilesManager.class.getDeclaredField("instance");
    instanceField.setAccessible(true);
    instanceField.set(null, null);
    
    Field filePathField = FilesManager.class.getDeclaredField("filePath");
    filePathField.setAccessible(true);
    filePathField.set(null, null);
    
    Field aesField = AESCipher128.class.getDeclaredField("instance");
    aesField.setAccessible(true);
    aesField.set(null, null);
  }
  
  @Test
  public void testGetInstanceWithFile() {
    testFile = new File("src/test/resources/test_file.json");
    FilesManager fm = FilesManager.getInstance(testFile);
    assertNotNull(fm);
    assertSame(fm, FilesManager.getInstance());
  }
  
  @Test
  public void testReadValidJsonReturnsMap() {
    testFile = new File("src/test/resources/test_file.json");
    FilesManager.getInstance(testFile);
    AESCipher128.getInstance(SECRET);
    
    Map<String, Registry> map = FilesManager.getInstance().getContentAsMap();
    assertNotNull(map);
    assertEquals(2, map.size());
    
    Registry first = map.values().iterator().next();
    assertNotNull(first.getName());
    assertNotNull(first.getPassword());
    
    try {
      String decrypted = AESCipher128.getInstance()
          .decrypt(first.getPassword());
      assertNotNull(decrypted);
    } catch (Exception e) {
      fail("Decryption failed with valid key.");
    }
  }
  
  @Test
  public void testOverwriteContentAsMapAndReadBack() throws Exception {
    File temp = new File("src/test/resources/test_overwrite.json");
    try (FileWriter w = new FileWriter(temp)) {
      w.write("{}");
    }
    
    FilesManager fm = FilesManager.getInstance(temp);
    AESCipher128 cipher = AESCipher128.getInstance(SECRET);
    
    Registry r = new Registry("github", "https://github.com", "matmini",
        cipher.encrypt("abc123"), "note");
    Map<String, Registry> map = new HashMap<>();
    map.put("key1", r);
    
    fm.overwriteContentAsMap(map);
    Map<String, Registry> result = fm.getContentAsMap();
    
    assertEquals(1, result.size());
    assertEquals("matmini", result.get("key1").getUsername());
    
    temp.delete();
  }
  
  @Test
  public void testImportOrReplaceAll() {
    File temp = new File("src/test/resources/test_import.json");
    try (FileWriter w = new FileWriter(temp)) {
      w.write("{}");
    } catch (Exception e) {
      fail("Failed to prepare temp file");
    }
    
    FilesManager fm = FilesManager.getInstance(temp);
    
    Registry r1 = new Registry("entry1", "url1", "user1", "pass1", "note1");
    Registry r2 = new Registry("entry2", "url2", "user2", "pass2", "note2");
    
    List<Registry> list = new ArrayList<>();
    list.add(r1);
    list.add(r2);
    
    fm.importOrReplaceAll(list);
    Map<String, Registry> result = fm.getContentAsMap();
    
    assertEquals(2, result.size());
    assertTrue(result.containsKey("entry1"));
    assertEquals("user1", result.get("entry1").getUsername());
    
    temp.delete();
  }
  
}
