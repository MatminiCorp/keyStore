package com.matmini.keyStore.screen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

import javax.management.openmbean.KeyAlreadyExistsException;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.matmini.keyStore.decypher.AESCipher128;
import com.matmini.keyStore.manager.Registry;
import com.matmini.keyStore.manager.interfaces.KeysManagerInterface;
import com.matmini.keyStore.manager.interfaces.RegistryHandlerInterface;
import com.matmini.keyStore.manager.interfaces.TableUpdateListener;
import com.matmini.keyStore.manager.service.KeysManagerService;
import com.matmini.keyStore.manager.service.RegistryHandlerImpl;
import com.matmini.keyStore.screen.enums.SimpleKeyStoreTableEnum;
import com.matmini.keyStore.screen.handlers.RegistriesHandler;
import com.matmini.keyStore.screen.jframes.Alerts;
import com.matmini.keyStore.screen.jframes.MultiButtonEditor;
import com.matmini.keyStore.screen.jframes.MultiButtonRenderer;
import com.matmini.keyStore.util.ConstantsParameters;

public class SimpleKeyStore implements TableUpdateListener {
  
  private JFrame frame;
  private JTable registriesTable;
  private KeysManagerInterface keysManagerService;
  private JTextField userTextField;
  private JTextField passwordTextField;
  private JTextField confirmPasswordTextField;
  private JTextField websiteTextField;
  
  private JScrollPane scrollPane = new JScrollPane();
  private JTextField nameTextField;
  private JTextField noteTextField;
  
  private JTextField searchTextField;
  private JButton searchButton;
  
  public SimpleKeyStore() {
    initialize();
  }
  
  private void initialize() {
    RegistryHandlerInterface handler = new RegistryHandlerImpl();
    keysManagerService = new KeysManagerService(handler);
    
    setLookAndFeel();
    frame = new JFrame();
    frame.setTitle(ConstantsParameters.FRAME_TITLE);
    frame.getContentPane().setBackground(new Color(255, 255, 255));
    frame.setBounds(200, 200, 1000, 450);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().setLayout(new BorderLayout());
    
    JTabbedPane optionsTablePane = new JTabbedPane(JTabbedPane.TOP);
    frame.getContentPane().add(optionsTablePane, BorderLayout.CENTER);
    
    JPanel topPanel = new JPanel();
    topPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));
    
    JButton logoutButton = new JButton("Logout");
    logoutButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        frame.dispose();
        new Login();
      }
    });
    
    searchTextField = new JTextField(20);
    searchButton = new JButton(ConstantsParameters.ACTION_BUTTON_SEARCH);
    searchButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        buildRegistriesTable();
      }
    });
    
    topPanel.add(new JLabel(ConstantsParameters.ACTION_BUTTON_SEARCH.concat(ConstantsParameters.SIMBOL_COLONM)));
    topPanel.add(searchTextField);
    topPanel.add(searchButton);
    topPanel.add(logoutButton);
    
    frame.getContentPane().add(topPanel, BorderLayout.NORTH);
    
    JPanel keyListPannel = new JPanel();
    optionsTablePane.addTab(ConstantsParameters.OPTIONAL_TABLE_AVAILABLEKEYS,
        null, keyListPannel, null);
    keyListPannel.setLayout(
        new FormLayout(new ColumnSpec[]{ColumnSpec.decode("500px:grow"),},
            new RowSpec[]{RowSpec.decode("255px:grow"),}));
    
    keyListPannel.add(scrollPane, "1, 1, fill, fill");
    
    buildRegistriesTable();
    
    optionsTablePane.addChangeListener(new ChangeListener() {
      @Override
      public void stateChanged(ChangeEvent e) {
        buildRegistriesTable();
      }
    });
    
    JPanel keyToolsPannel = new JPanel();
    optionsTablePane.addTab(ConstantsParameters.OPTIONAL_TABLE_TOOLS, null,
        keyToolsPannel, null);
    GridBagLayout gbl_keyToolsPannel = new GridBagLayout();
    gbl_keyToolsPannel.columnWidths = new int[]{0, 0};
    gbl_keyToolsPannel.rowHeights = new int[]{0, 0};
    gbl_keyToolsPannel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
    gbl_keyToolsPannel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
    keyToolsPannel.setLayout(gbl_keyToolsPannel);
    
    JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
    GridBagConstraints gbc_tabbedPane_1 = new GridBagConstraints();
    gbc_tabbedPane_1.fill = GridBagConstraints.BOTH;
    gbc_tabbedPane_1.gridx = 0;
    gbc_tabbedPane_1.gridy = 0;
    keyToolsPannel.add(tabbedPane_1, gbc_tabbedPane_1);
    
    JPanel panelAddRegistry = new JPanel();
    tabbedPane_1.addTab(ConstantsParameters.TABLE_PANE_ADDREGISTRY, null,
        panelAddRegistry, null);
    GridBagLayout gbl_panelAddRegistry = new GridBagLayout();
    gbl_panelAddRegistry.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
        0, 0};
    gbl_panelAddRegistry.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0};
    gbl_panelAddRegistry.columnWeights = new double[]{0.0, 1.0, 0.0, 0.0, 0.0,
        0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
    gbl_panelAddRegistry.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
        Double.MIN_VALUE};
    panelAddRegistry.setLayout(gbl_panelAddRegistry);
    
    JLabel lblName = new JLabel(ConstantsParameters.J_LABEL_NAME);
    GridBagConstraints gbc_lblName = new GridBagConstraints();
    gbc_lblName.insets = new Insets(0, 0, 5, 5);
    gbc_lblName.anchor = GridBagConstraints.EAST;
    gbc_lblName.gridx = 0;
    gbc_lblName.gridy = 0;
    panelAddRegistry.add(lblName, gbc_lblName);
    
    nameTextField = new JTextField();
    nameTextField.setColumns(10);
    GridBagConstraints gbc_nameTextField = new GridBagConstraints();
    gbc_nameTextField.gridwidth = 8;
    gbc_nameTextField.insets = new Insets(0, 0, 5, 5);
    gbc_nameTextField.fill = GridBagConstraints.HORIZONTAL;
    gbc_nameTextField.gridx = 1;
    gbc_nameTextField.gridy = 0;
    panelAddRegistry.add(nameTextField, gbc_nameTextField);
    
    JLabel lblUser = new JLabel(ConstantsParameters.J_LABEL_USER);
    GridBagConstraints gbc_lblUser = new GridBagConstraints();
    gbc_lblUser.insets = new Insets(0, 0, 5, 5);
    gbc_lblUser.anchor = GridBagConstraints.EAST;
    gbc_lblUser.gridx = 0;
    gbc_lblUser.gridy = 1;
    panelAddRegistry.add(lblUser, gbc_lblUser);
    
    userTextField = new JTextField();
    GridBagConstraints gbc_userTextField = new GridBagConstraints();
    gbc_userTextField.gridwidth = 8;
    gbc_userTextField.insets = new Insets(0, 0, 5, 5);
    gbc_userTextField.fill = GridBagConstraints.HORIZONTAL;
    gbc_userTextField.gridx = 1;
    gbc_userTextField.gridy = 1;
    panelAddRegistry.add(userTextField, gbc_userTextField);
    userTextField.setColumns(10);
    
    JLabel lblPassword = new JLabel(ConstantsParameters.J_LABEL_PASSWORD);
    GridBagConstraints gbc_lblPassword = new GridBagConstraints();
    gbc_lblPassword.anchor = GridBagConstraints.EAST;
    gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
    gbc_lblPassword.gridx = 0;
    gbc_lblPassword.gridy = 2;
    panelAddRegistry.add(lblPassword, gbc_lblPassword);
    
    passwordTextField = new JTextField();
    passwordTextField.setColumns(10);
    GridBagConstraints gbc_passwordTextField = new GridBagConstraints();
    gbc_passwordTextField.gridwidth = 8;
    gbc_passwordTextField.insets = new Insets(0, 0, 5, 5);
    gbc_passwordTextField.fill = GridBagConstraints.HORIZONTAL;
    gbc_passwordTextField.gridx = 1;
    gbc_passwordTextField.gridy = 2;
    panelAddRegistry.add(passwordTextField, gbc_passwordTextField);
    
    JLabel lblPassword2 = new JLabel(
        ConstantsParameters.J_LABEL_CONFIRM_PASSWORD);
    GridBagConstraints gbc_lblPassword2 = new GridBagConstraints();
    gbc_lblPassword2.anchor = GridBagConstraints.EAST;
    gbc_lblPassword2.insets = new Insets(0, 0, 5, 5);
    gbc_lblPassword2.gridx = 0;
    gbc_lblPassword2.gridy = 3;
    panelAddRegistry.add(lblPassword2, gbc_lblPassword2);
    
    JButton btnAddNewRegistry = new JButton(
        ConstantsParameters.ACTION_BUTTON_ADD);
    btnAddNewRegistry.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        try {
          boolean isValidRegistryFields = isValidRegistryFields();
          if (!isValidRegistryFields) {
            return;
          }
          try {
            AESCipher128 aes = AESCipher128.getInstance();
            keysManagerService.save(
                RegistriesHandler.registryFromInput(nameTextField.getText(),
                    websiteTextField.getText(), userTextField.getText(),
                    aes.encrypt(passwordTextField.getText()),
                    noteTextField.getText()));
            Alerts.callAlertBox("Registry added", "Success",
                JOptionPane.INFORMATION_MESSAGE);
          } catch (KeyAlreadyExistsException ke) {
            Alerts.callAlertBox("User just registred for this website!",
                "Error", JOptionPane.ERROR_MESSAGE);
          }
        } catch (Exception e2) {
          e2.printStackTrace();
        }
      }
      
    });
    
    confirmPasswordTextField = new JTextField();
    confirmPasswordTextField.setColumns(10);
    GridBagConstraints gbc_confirmPasswordTextField = new GridBagConstraints();
    gbc_confirmPasswordTextField.gridwidth = 8;
    gbc_confirmPasswordTextField.insets = new Insets(0, 0, 5, 5);
    gbc_confirmPasswordTextField.fill = GridBagConstraints.HORIZONTAL;
    gbc_confirmPasswordTextField.gridx = 1;
    gbc_confirmPasswordTextField.gridy = 3;
    panelAddRegistry.add(confirmPasswordTextField,
        gbc_confirmPasswordTextField);
    
    JLabel lblWebsite = new JLabel(ConstantsParameters.J_LABEL_URL);
    GridBagConstraints gbc_lblWebsite = new GridBagConstraints();
    gbc_lblWebsite.anchor = GridBagConstraints.EAST;
    gbc_lblWebsite.insets = new Insets(0, 0, 5, 5);
    gbc_lblWebsite.gridx = 0;
    gbc_lblWebsite.gridy = 4;
    panelAddRegistry.add(lblWebsite, gbc_lblWebsite);
    
    websiteTextField = new JTextField();
    websiteTextField.setColumns(10);
    GridBagConstraints gbc_websiteTextField = new GridBagConstraints();
    gbc_websiteTextField.gridwidth = 8;
    gbc_websiteTextField.insets = new Insets(0, 0, 5, 5);
    gbc_websiteTextField.fill = GridBagConstraints.HORIZONTAL;
    gbc_websiteTextField.gridx = 1;
    gbc_websiteTextField.gridy = 4;
    panelAddRegistry.add(websiteTextField, gbc_websiteTextField);
    
    JLabel lblNote = new JLabel(ConstantsParameters.J_LABEL_NOTE);
    GridBagConstraints gbc_lblNote = new GridBagConstraints();
    gbc_lblNote.anchor = GridBagConstraints.EAST;
    gbc_lblNote.insets = new Insets(0, 0, 0, 5);
    gbc_lblNote.gridx = 0;
    gbc_lblNote.gridy = 5;
    panelAddRegistry.add(lblNote, gbc_lblNote);
    
    noteTextField = new JTextField();
    noteTextField.setColumns(10);
    GridBagConstraints gbc_noteTextField = new GridBagConstraints();
    gbc_noteTextField.gridwidth = 8;
    gbc_noteTextField.insets = new Insets(0, 0, 0, 5);
    gbc_noteTextField.fill = GridBagConstraints.HORIZONTAL;
    gbc_noteTextField.gridx = 1;
    gbc_noteTextField.gridy = 5;
    panelAddRegistry.add(noteTextField, gbc_noteTextField);
    GridBagConstraints gbc_btnAddNewRegistry = new GridBagConstraints();
    gbc_btnAddNewRegistry.gridx = 10;
    gbc_btnAddNewRegistry.gridy = 5;
    panelAddRegistry.add(btnAddNewRegistry, gbc_btnAddNewRegistry);
    
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
  
  private void buildRegistriesTable() {
    registriesTable = new JTable();
    registriesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    List<Registry> all = keysManagerService.realAll();
    String search = searchTextField.getText().trim().toLowerCase();
    
    if (!search.isEmpty()) {
      all = all.stream().filter(r -> (r.getName() != null
          && r.getName().toLowerCase().contains(search))
          || (r.getUrl() != null && r.getUrl().toLowerCase().contains(search))
          || (r.getUsername() != null
              && r.getUsername().toLowerCase().contains(search)))
          .collect(Collectors.toList());
    }
    
    registriesTable.setModel(
        new DefaultTableModel(RegistriesHandler.parseListToObject(all),
            SimpleKeyStoreTableEnum.getAllColumns().toArray(new String[0])));
    scrollPane.setViewportView(registriesTable);
    
    MultiButtonEditor multiButtonEditor = new MultiButtonEditor(new JCheckBox(),
        registriesTable);
    multiButtonEditor.addTableUpdateListener(this);
    registriesTable.getColumnModel()
        .getColumn(SimpleKeyStoreTableEnum.PASSWORD.getCode())
        .setCellRenderer(new MultiButtonRenderer());
    registriesTable.getColumnModel()
        .getColumn(SimpleKeyStoreTableEnum.PASSWORD.getCode())
        .setCellEditor(multiButtonEditor);
  }
  
  private boolean isValidRegistryFields() {
    if (userTextField.getText() == null || userTextField.getText().isEmpty()
        || userTextField.getText().contains(" ")) {
      Alerts.callAlertBox("User is empty or has white space", "Error",
          JOptionPane.ERROR_MESSAGE);
      return false;
    }
    if (passwordTextField.getText() == null
        || passwordTextField.getText().isEmpty()
        || passwordTextField.getText().contains(" ")) {
      Alerts.callAlertBox("Password is empty or has white space", "Error",
          JOptionPane.ERROR_MESSAGE);
      return false;
    }
    if (confirmPasswordTextField.getText() == null
        || confirmPasswordTextField.getText().isEmpty()
        || confirmPasswordTextField.getText().contains(" ")) {
      Alerts.callAlertBox("Confirm password is empty or has white space",
          "Error", JOptionPane.ERROR_MESSAGE);
      return false;
    }
    if (websiteTextField.getText() == null
        || websiteTextField.getText().isEmpty()
        || websiteTextField.getText().contains(" ")) {
      Alerts.callAlertBox("Website is empty or has white space", "Error",
          JOptionPane.ERROR_MESSAGE);
      return false;
    }
    if (!passwordTextField.getText()
        .equals(confirmPasswordTextField.getText())) {
      Alerts.callAlertBox("Passwords are not equals", "Error",
          JOptionPane.ERROR_MESSAGE);
      return false;
    }
    return true;
  }
  
  @Override
  public void onTableUpdate() {
    buildRegistriesTable();
  }
  
}
