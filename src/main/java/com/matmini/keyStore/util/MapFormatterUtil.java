package com.matmini.keyStore.util;

import java.util.Map;
import java.util.Set;

import com.matmini.keyStore.manager.Registry;

public class MapFormatterUtil {
  
  public static Object[][] toObject(Map<String, Registry> map) {
    Set<Map.Entry<String, Registry>> entrySet = map.entrySet();
    Object[][] array = new Object[entrySet.size()][5];
    
    int index = 0;
    for (Map.Entry<String, Registry> entry : entrySet) {
      Registry registry = entry.getValue();
      array[index][0] = registry.getName();
      array[index][1] = registry.getUrl();
      array[index][2] = registry.getUsername();
      array[index][3] = registry.getPassword();
      array[index][4] = registry.getNote();
      index++;
    }
    
    return array;
    
  }
  
}
