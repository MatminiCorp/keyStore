package keyStore.manager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.management.openmbean.InvalidKeyException;
import javax.management.openmbean.KeyAlreadyExistsException;

import keyStore.manager.FilesManager;
import keyStore.manager.Registry;
import keyStore.manager.interfaces.KeysManagerInterface;
import keyStore.screen.RegistriesHandler;

public class KeysManagerService implements KeysManagerInterface {
	
	private FilesManager filesManager = FilesManager.getInstance();

	@Override
	public void save(Registry key) {
		Map<String, Registry> currentRegistries = filesManager.getContentAsMap();
		if (currentRegistries.get(RegistriesHandler.getKeyMap(key)) != null) {
			throw new KeyAlreadyExistsException("The user '" + key.getUser() + "' already exists in the registry for link '" + key.getWebsite() + "'");
		}
		currentRegistries.putAll(RegistriesHandler.parseRegistryToMap(key));
		filesManager.overwriteContentAsMap(currentRegistries);
	}

	@Override
	public void delete(Registry key) {
		Map<String, Registry> currentRegistries = filesManager.getContentAsMap();
		if (currentRegistries.get(RegistriesHandler.getKeyMap(key)) == null) {
			throw new InvalidKeyException("The user '" + key.getUser() + "' does not exists in the registry for link '" + key.getWebsite() + "'");
		}
		currentRegistries.remove(RegistriesHandler.getKeyMap(key));
		filesManager.overwriteContentAsMap(currentRegistries);
	}

	@Override
	public List<Registry> realAll() {
		Map<String, Registry> currentRegistries = filesManager.getContentAsMap();
		List<Registry> list = new ArrayList<>(currentRegistries.values());
		return list;
	}

	@Override
	public void update(Registry registry) {
		Map<String, Registry> currentRegistries = filesManager.getContentAsMap();
		Registry oldRegistry = currentRegistries.get(RegistriesHandler.getKeyMap(registry));
		oldRegistry.setPassword(registry.getPassword());
		currentRegistries.put(RegistriesHandler.getKeyMap(registry), oldRegistry);
		filesManager.overwriteContentAsMap(currentRegistries);
	}


}
