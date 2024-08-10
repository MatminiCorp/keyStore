package keyStore;

import java.awt.EventQueue;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import keyStore.manager.FilesManager;
import keyStore.screen.SimpleKeyStore;

public class Main {
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Path file = Paths.get("C:\\clones\\keyStore\\src\\main\\resources\\pass.json");
					if (!file.toFile().exists()) {
						Files.createFile(file);
					}
					FilesManager.getInstance(file.toFile());
					new SimpleKeyStore();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
