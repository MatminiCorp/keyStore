package keyStore.manager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.management.openmbean.KeyAlreadyExistsException;

import keyStore.manager.FilesManager;
import keyStore.manager.Key;
import keyStore.manager.interfaces.KeysManagerInterface;
import keyStore.screen.RegistriesHandler;

public class KeysManagerService implements KeysManagerInterface {
	
	private FilesManager filesManager = FilesManager.getInstance();

	@Override
	public void save(Key key) {
		Map<String, Key> currentRegistries = filesManager.getContentAsMap();
		if (currentRegistries.get(RegistriesHandler.getKeyMap(key)) != null) {
			throw new KeyAlreadyExistsException("The user '" + key.getUser() + "' already exists in the registry for link '" + key.getWebsite() + "'");
		}
		currentRegistries.putAll(RegistriesHandler.parseKeyToMap(key));
		filesManager.overwriteContentAsMap(currentRegistries);
	}

	@Override
	public void delete(Key key) {
		// TODO Auto-generated method stub
	}

	@Override
	public List<Key> realAll() {
		Map<String, Key> currentRegistries = filesManager.getContentAsMap();
		List<Key> list = new ArrayList<>(currentRegistries.values());
		return list;
	}

	@Override
	public void update(Key key) {
		// TODO Auto-generated method stub
	}


}
