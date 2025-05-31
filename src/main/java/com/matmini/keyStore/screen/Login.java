package com.matmini.keyStore.screen;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.matmini.keyStore.decypher.AESCipher128;
import com.matmini.keyStore.decypher.SecretKeyValidator;
import com.matmini.keyStore.manager.FilesManager;
import com.matmini.keyStore.manager.Registry;
import com.matmini.keyStore.manager.interfaces.KeysManagerInterface;
import com.matmini.keyStore.manager.interfaces.RegistryHandlerInterface;
import com.matmini.keyStore.manager.service.GFileProcessService;
import com.matmini.keyStore.manager.service.KeysManagerService;
import com.matmini.keyStore.manager.service.RegistryHandlerImpl;
import com.matmini.keyStore.screen.jframes.Alerts;
import com.matmini.keyStore.util.ConstantsParameters;
import com.matmini.keyStore.util.CsvFileDuplicator;

public class Login {
  
  private JFrame frame;
  private JPasswordField secretTextField;
  private JTextField registryFileTextField;
  
  private Preferences pref;
  
  public Login() {
    initialize();
    
    try {
      pref = Preferences.userNodeForPackage(getClass());
    } catch (Exception e) {
      pref = null;
    }
    
    if (pref != null) {
      registryFileTextField.setText(pref.get("registryFileTextField", null));
    }
  }
  
  private void initialize() {
    setLookAndFeel();
    frame = new JFrame();
    frame.setTitle(ConstantsParameters.FRAME_TITLE);
    frame.getContentPane().setBackground(new Color(255, 255, 255));
    frame.setBounds(100, 100, 450, 150);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    JPanel panel = new JPanel();
    panel.setBounds(0, 0, 436, 113);
    GridBagLayout gbl_panel = new GridBagLayout();
    gbl_panel.columnWidths = new int[]{54, 276, 79, 0};
    gbl_panel.rowHeights = new int[]{19, 21, 21, 0};
    gbl_panel.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
    gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
    panel.setLayout(gbl_panel);
    
    JLabel lblNewLabel = new JLabel("Secret");
    GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
    gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
    gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
    gbc_lblNewLabel.gridx = 0;
    gbc_lblNewLabel.gridy = 0;
    panel.add(lblNewLabel, gbc_lblNewLabel);
    
    secretTextField = new JPasswordField();
    GridBagConstraints gbc_secretTextField = new GridBagConstraints();
    gbc_secretTextField.fill = GridBagConstraints.HORIZONTAL;
    gbc_secretTextField.insets = new Insets(0, 0, 5, 0);
    gbc_secretTextField.gridwidth = 2;
    gbc_secretTextField.gridx = 1;
    gbc_secretTextField.gridy = 0;
    panel.add(secretTextField, gbc_secretTextField);
    secretTextField.setColumns(10);
    
    JLabel lblNewLabel_1 = new JLabel("Registry file");
    GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
    gbc_lblNewLabel_1.anchor = GridBagConstraints.EAST;
    gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
    gbc_lblNewLabel_1.gridx = 0;
    gbc_lblNewLabel_1.gridy = 1;
    panel.add(lblNewLabel_1, gbc_lblNewLabel_1);
    
    registryFileTextField = new JTextField();
    GridBagConstraints gbc_registryFileTextField = new GridBagConstraints();
    gbc_registryFileTextField.fill = GridBagConstraints.BOTH;
    gbc_registryFileTextField.insets = new Insets(0, 0, 5, 5);
    gbc_registryFileTextField.gridx = 1;
    gbc_registryFileTextField.gridy = 1;
    panel.add(registryFileTextField, gbc_registryFileTextField);
    registryFileTextField.setColumns(30);
    
    JButton fileChooserButton = new JButton("Select File");
    GridBagConstraints gbc_fileChooserButton = new GridBagConstraints();
    gbc_fileChooserButton.fill = GridBagConstraints.BOTH;
    gbc_fileChooserButton.insets = new Insets(0, 0, 5, 0);
    gbc_fileChooserButton.gridx = 2;
    gbc_fileChooserButton.gridy = 1;
    panel.add(fileChooserButton, gbc_fileChooserButton);
    
    fileChooserButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
          File selectedFile = fileChooser.getSelectedFile();
          registryFileTextField.setText(selectedFile.getAbsolutePath());
        }
      }
    });
    frame.getContentPane().setLayout(null);
    frame.getContentPane().add(panel);
    
    JButton btnLoginButton = new JButton("Login");
    btnLoginButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          boolean isValid = validFields();
          if (!isValid) {
            return;
          }
          if (pref != null) {
            pref.put("registryFileTextField", registryFileTextField.getText());
          }
          frame.dispose();
          new SimpleKeyStore();
        } catch (Exception e2) {
          e2.printStackTrace();
        }
      }
      
    });
    GridBagConstraints gbc_btnLoginButton = new GridBagConstraints();
    gbc_btnLoginButton.insets = new Insets(0, 0, 0, 5);
    gbc_btnLoginButton.fill = GridBagConstraints.BOTH;
    gbc_btnLoginButton.gridx = 1;
    gbc_btnLoginButton.gridy = 2;
    panel.add(btnLoginButton, gbc_btnLoginButton);
    
    JButton btnEncryptButton = new JButton("Encrypt");
    btnEncryptButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          if (registryFileTextField.getText() == null
              || registryFileTextField.getText().isEmpty()) {
            Alerts.callAlertBox("Missing file Path", "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
          }
          System.out.println(registryFileTextField.getText());
          if (!Paths.get(registryFileTextField.getText()).toFile().exists()) {
            Alerts.callAlertBox("Missing file Path", "Error",
                JOptionPane.ERROR_MESSAGE);
          }
          String oldRegistryFile = registryFileTextField.getText();
          
          File file = CsvFileDuplicator
              .createEditedJsonCopy(registryFileTextField.getText());
          if (file == null) {
            Alerts.callAlertBox("Google File is empty", "Error",
                JOptionPane.ERROR_MESSAGE);
            return;
          }
          registryFileTextField.setText(file.getAbsolutePath());
          
          boolean isValid = validFields();
          if (!isValid) {
            return;
          }
          
          RegistryHandlerInterface handler = new RegistryHandlerImpl();
          KeysManagerInterface keysManagerService = new KeysManagerService(handler);
          int newPassword = JOptionPane.showConfirmDialog(panel,
              "Import Google passwords?", "Confirm Process", 0);
          if (newPassword == 0) {
            List<Registry> gRegistries = GFileProcessService
                .processFile(oldRegistryFile);
            FilesManager.getInstance(
                Paths.get(registryFileTextField.getText()).toFile());
            List<Registry> errorToImport = new ArrayList<>();
            for (Registry registry : gRegistries) {
              if (registry.getPassword() != null) {
                registry.setPassword(
                    AESCipher128.getInstance().encrypt(registry.getPassword()));
                keysManagerService.save(registry);
              } else {
                errorToImport.add(registry);
              }
            }
          } else {
            return;
          }
        } catch (Exception ex) {
          // TODO: handle exception
        }
      }
    });
    GridBagConstraints gbc_btnEncryptButton = new GridBagConstraints();
    gbc_btnEncryptButton.fill = GridBagConstraints.BOTH;
    gbc_btnEncryptButton.gridx = 2;
    gbc_btnEncryptButton.gridy = 2;
    panel.add(btnEncryptButton, gbc_btnEncryptButton);
    
    frame.setVisible(true);
  }
  
  private void setLookAndFeel() {
    try {
      String osName = System.getProperty("os.name").toLowerCase();
      if (osName.contains("windows")) {
        UIManager.setLookAndFeel(
            "com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
      } else if (osName.contains("linux")) {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
      } else {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      }
    } catch (Exception e) {
      System.err.println("Failed to set LookAndFeel: " + e.getMessage());
    }
  }
  
  private boolean validFields() throws IOException {
    if (secretTextField.getPassword() == null
        || secretTextField.getPassword().length <= 0) {
      Alerts.callAlertBox("Secret is empty", "Error",
          JOptionPane.ERROR_MESSAGE);
      return false;
    }
    if (registryFileTextField.getText() == null
        || registryFileTextField.getText().isEmpty()) {
      Alerts.callAlertBox("Missing file Path", "Error",
          JOptionPane.ERROR_MESSAGE);
      return false;
    }
    
    if (!Paths.get(registryFileTextField.getText()).toFile().exists()) {
      Files.createFile(Paths.get(registryFileTextField.getText()));
    }
    FilesManager
        .getInstance(Paths.get(registryFileTextField.getText()).toFile());
    
    if (!SecretKeyValidator
        .isValidSecretByFile(secretTextField.getPassword())) {
      Alerts.callAlertBox("Secret key does not fit on this file", "Error",
          JOptionPane.ERROR_MESSAGE);
      return false;
    }
    return true;
  }
  
}
