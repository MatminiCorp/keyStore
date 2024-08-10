package keyStore.screen.jframes;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;

public abstract class GenericButtonEditor extends DefaultCellEditor {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected JButton button;
	protected String label;
	protected boolean isPushed;
	protected JTable table;

	public GenericButtonEditor(JCheckBox checkBox, JTable table, String buttonText) {
		super(checkBox);
		this.table = table;
		button = new JButton(buttonText);
		button.setOpaque(true);
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fireEditingStopped();
			}
		});
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		label = (value == null) ? button.getText() : value.toString();
		button.setText(label);
		isPushed = true;
		return button;
	}

	@Override
	public Object getCellEditorValue() {
		if (isPushed) {
			performAction(table.getSelectedRow());
		}
		isPushed = false;
		return label;
	}

	protected abstract void performAction(int row); // Define the action to be performed by the button

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
