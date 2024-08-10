package keyStore.screen.jframes;

import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import keyStore.util.ConstantsParameters;

public class MultiButtonRenderer extends DefaultTableCellRenderer {
    private static final long serialVersionUID = 1L;
    private JPanel panel;
    private JButton copyButton;
    private JButton editButton;
    private JButton removeButton;

    public MultiButtonRenderer() {
        panel = new JPanel(new GridLayout(1, 3));

		copyButton = new JButton(ConstantsParameters.ACTION_BUTTON_COPY);
		editButton = new JButton(ConstantsParameters.ACTION_BUTTON_UPDATE);
		removeButton = new JButton(ConstantsParameters.ACTION_BUTTON_DELETE);

        panel.add(copyButton);
        panel.add(editButton);
        panel.add(removeButton);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected) {
            panel.setBackground(table.getSelectionBackground());
        } else {
            panel.setBackground(table.getBackground());
        }
        return panel;
    }
}
