package com.matmini.keyStore.screen.jframes;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.matmini.keyStore.screen.enums.SimpleKeyStoreTableEnum;
import com.matmini.keyStore.util.ConstantsParameters;

public class PasswordEditWizardDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CardLayout cardLayout = new CardLayout();
	private JPanel cardPanel = new JPanel(cardLayout);
	private JButton btnNext = new JButton(ConstantsParameters.ACTION_BUTTON_NEXT);
	private JButton btnPrevious = new JButton(ConstantsParameters.ACTION_BUTTON_PREVIOUS);
	private JButton btnUpdate = new JButton(ConstantsParameters.ACTION_BUTTON_UPDATE);
	private JButton btnCancel = new JButton(ConstantsParameters.ACTION_BUTTON_CANCEL);

	private int currentStep = 0;

	private final Map<String, String> fieldValues = new HashMap<>();
	private final String[] steps = { SimpleKeyStoreTableEnum.NAME.getColumn(), SimpleKeyStoreTableEnum.URL.getColumn(),
			SimpleKeyStoreTableEnum.USERNAME.getColumn(), SimpleKeyStoreTableEnum.PASSWORD.getColumn(),
			ConstantsParameters.J_LABEL_CONFIRM_PASSWORD, SimpleKeyStoreTableEnum.NOTE.getColumn() };
	private final Map<String, JTextField> inputs = new HashMap<>();
	private final Consumer<Map<String, String>> onConfirm;

	public PasswordEditWizardDialog(Frame owner, Map<String, String> initialValues,
			Consumer<Map<String, String>> onConfirm) {
		super(owner, "Edit Entry", true);
		this.onConfirm = onConfirm;
		setLayout(new BorderLayout());

		for (String key : steps) {
			cardPanel.add(buildFieldPanel(key, initialValues.get(key)), key);
		}

		add(cardPanel, BorderLayout.CENTER);
		add(buildNavigationPanel(), BorderLayout.SOUTH);

		updateButtonStates();

		pack();
		setLocationRelativeTo(owner);
		setResizable(false);
	}

	private JPanel buildFieldPanel(String field, String initialValue) {
		JPanel panel = new JPanel(new BorderLayout());
		JLabel label = new JLabel("Enter " + field + ":");
		JTextField input = SimpleKeyStoreTableEnum.PASSWORD.getColumn().equals(field)
				|| ConstantsParameters.J_LABEL_CONFIRM_PASSWORD.equals(field) ? new JPasswordField(20)
						: new JTextField(initialValue != null ? initialValue : "", 20);

		inputs.put(field, input);

		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.add(label, BorderLayout.NORTH);
		panel.add(input, BorderLayout.CENTER);
		return panel;
	}

	private JPanel buildNavigationPanel() {
		JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		navPanel.add(btnPrevious);
		navPanel.add(btnNext);
		navPanel.add(btnUpdate);
		navPanel.add(btnCancel);

		btnPrevious.addActionListener(e -> {
			if (currentStep > 0) {
				currentStep--;
				cardLayout.previous(cardPanel);
				updateButtonStates();
			}
		});

		btnNext.addActionListener(e -> {
			if (!captureCurrentField())
				return;

			if (currentStep < steps.length - 1) {
				currentStep++;
				cardLayout.next(cardPanel);
				updateButtonStates();
			} else if (currentStep == steps.length - 1) {
				if (!fieldValues.get(SimpleKeyStoreTableEnum.PASSWORD.getColumn())
						.equals(fieldValues.get(ConstantsParameters.J_LABEL_CONFIRM_PASSWORD))) {
					JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				currentStep++;
				updateButtonStates();
			}
		});

		btnUpdate.addActionListener(e -> {
			String step = steps[currentStep];
			JTextField input = inputs.get(step);
			String value = input.getText();
			fieldValues.put(step, value);
			fieldValues.remove(ConstantsParameters.J_LABEL_CONFIRM_PASSWORD);
			onConfirm.accept(fieldValues);
			dispose();
		});

		btnCancel.addActionListener(e -> dispose());

		return navPanel;
	}

	private boolean captureCurrentField() {
		String step = steps[currentStep];
		JTextField input = inputs.get(step);
		if (input == null)
			return true;
		String value = input.getText();
		if (value == null || value.trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, step + " is required", "Validation", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		fieldValues.put(step, value);
		return true;
	}

	private void updateButtonStates() {
		btnPrevious.setEnabled(currentStep > 0);

		if (currentStep < steps.length - 1) {
			btnNext.setVisible(true);
			btnUpdate.setVisible(false);
		} else {
			btnNext.setVisible(false);
			btnUpdate.setVisible(true);
		}

		btnCancel.setVisible(true);
		btnCancel.setEnabled(true);
	}

}
