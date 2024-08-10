package keyStore.screen.jframes;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;

public class ButtonEditor extends DefaultCellEditor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton button;
	private String label;
	private boolean isPushed;
	private JTable table;

	public ButtonEditor(JCheckBox checkBox, JTable table) {
		super(checkBox);
		this.table = table;
		button = new JButton("Copy");
		button.setOpaque(true);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fireEditingStopped();
			}
		});
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		label = (value == null) ? "Copy" : value.toString();
		button.setText(label);
		isPushed = true;
		return button;
	}

	@Override
	public Object getCellEditorValue() {
		if (isPushed) {
			String value = (String) table.getValueAt(table.getSelectedRow(), 0);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(value), null);
			JOptionPane.showMessageDialog(button, "Valor copiado: " + value);
		}
		isPushed = false;
		return label;
	}

	@Override
	public boolean stopCellEditing() {
		isPushed = false;
		return super.stopCellEditing();
	}

	@Override
	protected void fireEditingStopped() {
		super.fireEditingStopped();
	}
}