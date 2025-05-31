package com.matmini.keyStore.screen.handlers;

import com.matmini.keyStore.manager.Registry;
import java.util.HashMap;
import java.util.Map;

public class RegistriesHandler {
  
  private static String fixedKey;
  private static Map<String, Registry> fixedMap;
  
  public static void setMockKey(String key) {
    fixedKey = key;
  }
  
  public static void setMockMap(Map<String, Registry> map) {
    fixedMap = map;
  }
  
  public static String getKeyMap(Registry registry) {
    return fixedKey;
  }
  
  public static Map<String, Registry> parseRegistryToMap(Registry registry) {
    return fixedMap != null ? fixedMap : new HashMap<String, Registry>();
  }
}
