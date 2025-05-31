package com.matmini.keyStore.decypher;

import java.util.Map;
import java.util.Map.Entry;

import com.matmini.keyStore.manager.FilesManager;
import com.matmini.keyStore.manager.Registry;

public class SecretKeyValidator {
  
  public static boolean isValidSecretByFile(char[] secret) {
    AESCipher128 aes = AESCipher128.getInstance(new String(secret));
    FilesManager filesManager = FilesManager.getInstance();
    Map<String, Registry> contentAsMap = filesManager.getContentAsMap();
    
    if (contentAsMap == null || contentAsMap.isEmpty()) {
      return true;
    }
    
    try {
      Entry<String, Registry> firstEntry = contentAsMap.entrySet().iterator()
          .next();
      aes.decrypt(firstEntry.getValue().getPassword());
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
  
}
