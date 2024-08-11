package com.matmini.keyStore.screen.jframes;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;

import com.matmini.keyStore.decypher.AESCipher128;
import com.matmini.keyStore.manager.Registry;
import com.matmini.keyStore.manager.interfaces.KeysManagerInterface;
import com.matmini.keyStore.manager.interfaces.TableUpdateListener;
import com.matmini.keyStore.manager.service.KeysManagerService;
import com.matmini.keyStore.util.ConstantsParameters;

public class MultiButtonEditor extends DefaultCellEditor {
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JButton copyButton;
	private JButton editButton;
	private JButton removeButton;
	private KeysManagerInterface keysManager = new KeysManagerService();
	private List<TableUpdateListener> tableUpdateListenerList = new ArrayList<>();
	private AESCipher128 aes = AESCipher128.getInstance();

	public MultiButtonEditor(JCheckBox checkBox, JTable table) {
		super(checkBox);

		panel = new JPanel(new GridLayout(1, 3));

		copyButton = new JButton(ConstantsParameters.ACTION_BUTTON_COPY);
		editButton = new JButton(ConstantsParameters.ACTION_BUTTON_UPDATE);
		removeButton = new JButton(ConstantsParameters.ACTION_BUTTON_DELETE);

		panel.add(copyButton);
		panel.add(editButton);
		panel.add(removeButton);

		copyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int row = table.getSelectedRow();
					if (row != -1) {
						String value = aes.decrypt((String) table.getValueAt(row, 1));
						Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(value), null);
						JOptionPane.showMessageDialog(panel,
								"Valor copiado para o user: " + (String) table.getValueAt(row, 0));
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});

		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int row = table.getSelectedRow();
					if (row != -1) {
						String value = (String) table.getValueAt(row, 0);
						String newValue = JOptionPane.showInputDialog(panel, "Edit value:", value);
						keysManager.update(new Registry((String) table.getValueAt(row, 0),
								aes.encrypt((String) newValue), (String) table.getValueAt(row, 2)));
						if (newValue != null) {
							notifyTableUpdate();
						}
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row != -1) {
					JPanel panel = ConfirmCommandPannel(table, row);
		            int confirm = JOptionPane.showConfirmDialog(
		                null, 
		                panel, 
		                "Remove Registry", 
		                JOptionPane.YES_NO_OPTION, 
		                JOptionPane.WARNING_MESSAGE
		            );
		            
					if (confirm == JOptionPane.YES_OPTION) {
						keysManager.delete(new Registry((String) table.getValueAt(row, 0), null,
								(String) table.getValueAt(row, 2)));
						notifyTableUpdate();
					}
				}

			}

			private JPanel ConfirmCommandPannel(JTable table, int row) {
				JPanel panel = new JPanel();
				panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

				JLabel alertLabel = new JLabel("Warning: This action cannot be undone!");
				alertLabel.setForeground(Color.RED);
				panel.add(alertLabel);

				JLabel confirmLabel = new JLabel("Are you sure that you want to remove user '" + table.getValueAt(row, 0) + "' for website '"
						+ table.getValueAt(row, 2) + "'");
				panel.add(confirmLabel);
				return panel;
			}
		});
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		return panel;
	}

	@Override
	public Object getCellEditorValue() {
		return "";
	}

	@Override
	protected void fireEditingStopped() {
		super.fireEditingStopped();
	}

	public void addTableUpdateListener(TableUpdateListener listener) {
		this.tableUpdateListenerList.add(listener);
	}

	private void notifyTableUpdate() {
		for (TableUpdateListener tableUpdateListener : tableUpdateListenerList) {
			tableUpdateListener.onTableUpdate();
		}
	}
}
