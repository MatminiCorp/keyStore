package keyStore.screen.jframes;

import javax.swing.JOptionPane;

public class Alerts {

	public static void callAlertBox(String message, String title, int messageType) {
		JOptionPane.showMessageDialog(null, message, message, messageType);
	}

}
