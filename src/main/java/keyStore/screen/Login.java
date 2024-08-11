package keyStore.screen;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.prefs.Preferences;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import keyStore.decypher.SecretKeyValidator;
import keyStore.manager.FilesManager;
import keyStore.screen.jframes.Alerts;

public class Login {

	private JFrame frame;
	private JTextField secretTextField;
	private JTextField registryFileTextField;
	
	private Preferences pref;

	public Login() {
		initialize();
		
		try {
			pref = Preferences.userNodeForPackage(getClass());
		} catch (Exception e) {
			pref = null;
		}
		
		if(pref != null) {
			registryFileTextField.setText(pref.get("registryFileTextField", null));
		}
	}

	private void initialize() {
		setLookAndFeel();
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 150);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 80, 22, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panel.setLayout(gbl_panel);

		JLabel lblNewLabel = new JLabel("Secret");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		panel.add(lblNewLabel, gbc_lblNewLabel);

		secretTextField = new JTextField();
		GridBagConstraints gbc_secretTextField = new GridBagConstraints();
		gbc_secretTextField.gridwidth = 10;
		gbc_secretTextField.insets = new Insets(0, 0, 5, 0);
		gbc_secretTextField.fill = GridBagConstraints.HORIZONTAL;
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
		gbc_registryFileTextField.gridwidth = 9;
		gbc_registryFileTextField.insets = new Insets(0, 0, 5, 5);
		gbc_registryFileTextField.fill = GridBagConstraints.BOTH;
		gbc_registryFileTextField.gridx = 1;
		gbc_registryFileTextField.gridy = 1;
		panel.add(registryFileTextField, gbc_registryFileTextField);
		registryFileTextField.setColumns(30);

		JButton fileChooserButton = new JButton("Select File");
		GridBagConstraints gbc_fileChooserButton = new GridBagConstraints();
		gbc_fileChooserButton.fill = GridBagConstraints.BOTH;
		gbc_fileChooserButton.insets = new Insets(0, 0, 5, 0);
		gbc_fileChooserButton.gridx = 10;
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

		JButton btnNewButton = new JButton("Login");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					boolean isValid = validFields();
					if (!isValid) {
						return;
					}
					if(pref != null) {
						pref.put("registryFileTextField", registryFileTextField.getText());
					}
					frame.dispose();
					new SimpleKeyStore();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}

		});
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton.gridx = 10;
		gbc_btnNewButton.gridy = 3;
		panel.add(btnNewButton, gbc_btnNewButton);

		frame.setVisible(true);
	}

	private void setLookAndFeel() {
		try {
			String osName = System.getProperty("os.name").toLowerCase();
			if (osName.contains("windows")) {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
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
		if (secretTextField.getText() == null || secretTextField.getText().isEmpty()) {
			Alerts.callAlertBox("Secret is empty", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		if (registryFileTextField.getText() == null || registryFileTextField.getText().isEmpty()) {
			Alerts.callAlertBox("Missing file Path", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		
		if (!Paths.get(registryFileTextField.getText()).toFile().exists()) {
			Files.createFile(Paths.get(registryFileTextField.getText()));
		}
		FilesManager.getInstance(Paths.get(registryFileTextField.getText()).toFile());
		
		if (!SecretKeyValidator.isValidSecretByFile(secretTextField.getText())) {
			Alerts.callAlertBox("Secret key does not fit on this file", "Error", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		return true;
	}

}
