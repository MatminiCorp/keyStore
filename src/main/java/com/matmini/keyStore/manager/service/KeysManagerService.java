package com.matmini.keyStore.manager.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
			throw new KeyAlreadyExistsException("The name '" + registry.getName()
					+ "' already exists in the registry for link '" + registry.getUrl() + "'");
		}
		currentRegistries.putAll(RegistriesHandler.parseRegistryToMap(registry));
		filesManager.overwriteContentAsMap(currentRegistries);
	}

	@Override
	public void delete(Registry registry) {
		Map<String, Registry> currentRegistries = filesManager.getContentAsMap();
		if (currentRegistries.get(RegistriesHandler.getKeyMap(registry)) == null) {
			throw new InvalidKeyException("The user '" + registry.getName()
					+ "' does not exists in the registry for link '" + registry.getUrl() + "'");
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
		String key = RegistriesHandler.getKeyMap(registry);
		Registry oldRegistry = currentRegistries.get(key);

		if (oldRegistry == null) {
			throw new IllegalArgumentException("Registry not found for update: " + key);
		}

		boolean changed = false;

		if (!equalsOrNull(oldRegistry.getName(), registry.getName())) {
			oldRegistry.setName(registry.getName());
			changed = true;
		}

		if (!equalsOrNull(oldRegistry.getUrl(), registry.getUrl())) {
			oldRegistry.setUrl(registry.getUrl());
			changed = true;
		}

		if (!equalsOrNull(oldRegistry.getUsername(), registry.getUsername())) {
			oldRegistry.setUsername(registry.getUsername());
			changed = true;
		}

		if (!equalsOrNull(oldRegistry.getPassword(), registry.getPassword())) {
			oldRegistry.setPassword(registry.getPassword());
			changed = true;
		}

		if (!equalsOrNull(oldRegistry.getNote(), registry.getNote())) {
			oldRegistry.setNote(registry.getNote());
			changed = true;
		}

		if (changed) {
			currentRegistries.put(key, oldRegistry);
			filesManager.overwriteContentAsMap(currentRegistries);
		}
	}

	private boolean equalsOrNull(String a, String b) {
		if (a == null && b == null)
			return true;
		if (a == null || b == null)
			return false;
		return a.equals(b);
	}

	@Override
	public List<Registry> realAllByNameLike(String nameFilter) {
		return realAll().stream()
				.filter(r -> r.getName() != null && r.getName().toLowerCase().contains(nameFilter.toLowerCase()))
				.collect(Collectors.toList());
	}

}
