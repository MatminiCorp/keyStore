package com.matmini.keyStore.manager;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RegistryTest {
  
  @Test
  public void testConstructorAndGetters() {
    Registry registry = new Registry("GitHub", "https://github.com", "matheus",
        "secret", "My account");
    
    assertEquals("GitHub", registry.getName());
    assertEquals("https://github.com", registry.getUrl());
    assertEquals("matheus", registry.getUsername());
    assertEquals("secret", registry.getPassword());
    assertEquals("My account", registry.getNote());
  }
  
  @Test
  public void testSetName() {
    Registry registry = new Registry("", "", "", "", "");
    registry.setName("GitLab");
    assertEquals("GitLab", registry.getName());
  }
  
  @Test
  public void testSetUrl() {
    Registry registry = new Registry("", "", "", "", "");
    registry.setUrl("https://gitlab.com");
    assertEquals("https://gitlab.com", registry.getUrl());
  }
  
  @Test
  public void testSetUsername() {
    Registry registry = new Registry("", "", "", "", "");
    registry.setUsername("admin");
    assertEquals("admin", registry.getUsername());
  }
  
  @Test
  public void testSetPassword() {
    Registry registry = new Registry("", "", "", "", "");
    registry.setPassword("123456");
    assertEquals("123456", registry.getPassword());
  }
  
  @Test
  public void testSetNote() {
    Registry registry = new Registry("", "", "", "", "");
    registry.setNote("Backup");
    assertEquals("Backup", registry.getNote());
  }
  
  @Test
  public void testToString() {
    Registry registry = new Registry("GitHub", "https://github.com", "user",
        "pass", "note");
    String expected = "Registry [name=GitHub, url=https://github.com, username=user, password=pass, note=note]";
    assertEquals(expected, registry.toString());
  }
}
