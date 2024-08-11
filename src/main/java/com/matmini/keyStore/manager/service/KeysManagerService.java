package com.matmini.keyStore.manager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.management.openmbean.InvalidKeyException;
import javax.management.openmbean.KeyAlreadyExistsException;

import com.matmini.keyStore.manager.FilesManager;
import com.matmini.keyStore.manager.Registry;
import com.matmini.keyStore.manager.interfaces.KeysManagerInterface;
import com.matmini.keyStore.screen.handlers.RegistriesHandler;

public class KeysManagerService implements KeysManagerInterface {
	
	private FilesManager filesManager = FilesManager.getInstance();

	@Override
	public void save(Registry registry) {
		Map<String, Registry> currentRegistries = filesManager.getContentAsMap();
		if (currentRegistries.get(RegistriesHandler.getKeyMap(registry)) != null) {
			throw new KeyAlreadyExistsException("The user '" + registry.getUser() + "' already exists in the registry for link '" + registry.getWebsite() + "'");
		}
		currentRegistries.putAll(RegistriesHandler.parseRegistryToMap(registry));
		filesManager.overwriteContentAsMap(currentRegistries);
	}

	@Override
	public void delete(Registry registry) {
		Map<String, Registry> currentRegistries = filesManager.getContentAsMap();
		if (currentRegistries.get(RegistriesHandler.getKeyMap(registry)) == null) {
			throw new InvalidKeyException("The user '" + registry.getUser() + "' does not exists in the registry for link '" + registry.getWebsite() + "'");
		}
		currentRegistries.remove(RegistriesHandler.getKeyMap(registry));
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
