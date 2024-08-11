package keyStore.decypher;

import java.util.Map;
import java.util.Map.Entry;

import keyStore.manager.FilesManager;
import keyStore.manager.Registry;

public class SecretKeyValidator {
	
	public static boolean isValidSecretByFile(String secret) {
		AESCipher128 aes = AESCipher128.getInstance(secret);
		FilesManager filesManager = FilesManager.getInstance();
		Map<String, Registry> contentAsMap = filesManager.getContentAsMap();
		
		if (contentAsMap == null || contentAsMap.isEmpty()) {
			return false;
		}
		
		try {
			Entry<String, Registry> firstEntry = contentAsMap.entrySet().iterator().next();
			aes.decrypt(firstEntry.getValue().getPassword());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
