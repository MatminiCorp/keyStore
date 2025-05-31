package com.matmini.keyStore;

import java.awt.EventQueue;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.matmini.keyStore.manager.FilesManager;
import com.matmini.keyStore.screen.Login;
import com.matmini.keyStore.screen.SimpleKeyStore;

public class Main {
  
  public static void main(String[] args) {
    EventQueue.invokeLater(new Runnable() {
      public void run() {
        try {
          new Login();
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    });
  }
  
}
