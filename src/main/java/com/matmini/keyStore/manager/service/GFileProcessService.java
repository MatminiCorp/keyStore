package com.matmini.keyStore.manager.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.matmini.keyStore.manager.Registry;

public class GFileProcessService {
  
  public static List<Registry> processFile(String filePath) {
    List<Registry> registryList = new ArrayList<>();
    
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      String line;
      boolean firstLine = true;
      
      while ((line = br.readLine()) != null) {
        if (firstLine) {
          firstLine = false; // skip header
          continue;
        }
        
        String[] values = line.split(",", -1); // -1 to keep empty strings
        if (values.length >= 5) {
          Registry registry = new Registry(values[0].trim(), // name
              values[1].trim(), // url
              values[2].trim(), // username
              values[3].trim(), // password
              values[4].trim() // note
          );
          registryList.add(registry);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    
    return registryList;
  }
  
}
