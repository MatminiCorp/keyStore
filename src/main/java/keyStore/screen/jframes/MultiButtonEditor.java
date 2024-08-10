package keyStore.screen.jframes;

import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import keyStore.manager.Registry;
import keyStore.manager.interfaces.KeysManagerInterface;
import keyStore.manager.service.KeysManagerService;
import keyStore.screen.RegistriesHandler;
import keyStore.util.ConstantsParameters;

public class MultiButtonEditor extends DefaultCellEditor {
	private static final long serialVersionUID = 1L;
	private JPanel panel;
	private JButton copyButton;
	private JButton editButton;
	private JButton removeButton;
	private KeysManagerInterface keysManager = new KeysManagerService();

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
				int row = table.getSelectedRow();
				if (row != -1) {
					String value = (String) table.getValueAt(row, 0);
					Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(value), null);
					JOptionPane.showMessageDialog(panel, "Valor copiado: " + value);
				}
			}
		});

		editButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row != -1) {
					String value = (String) table.getValueAt(row, 0);
					String newValue = JOptionPane.showInputDialog(panel, "Edit value:", value);
					if (newValue != null) {
						table.setValueAt(newValue, row, 0);
					}
				}
			}
		});

		removeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				keysManager.delete(new Registry((String) table.getValueAt(row, 0), null, (String) table.getValueAt(row, 2)));
				if (row != -1) {
					((DefaultTableModel) table.getModel()).removeRow(row);
				}
				
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
}
