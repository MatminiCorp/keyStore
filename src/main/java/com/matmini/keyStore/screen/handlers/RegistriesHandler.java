package com.matmini.keyStore.screen.handlers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.matmini.keyStore.manager.Registry;
import com.matmini.keyStore.util.MapFormatterUtil;

public class RegistriesHandler {

	public static Object[][] parseListToObject(List<Registry> list) {
		SortedMap<String, Registry> map = new TreeMap<>();
		for (Registry registry : list) {
			map.put(getKeyMap(registry), registry);
		}
		return MapFormatterUtil.toObject(map);
	}

	public static Map<String, Registry> parseRegistryToMap(Registry registry) {
		Map<String, Registry> map = new HashMap<>();
		map.put(getKeyMap(registry), registry);
		return map;
	}

	public static String getKeyMap(Registry registry) {
		if (registry.getName() != null && registry.getUrl() != null && registry.getUsername() != null) {
			return registry.getName().concat(registry.getUrl()).concat(registry.getUsername());
		} else if (registry.getName() != null && registry.getUrl() != null) {
			return registry.getName().concat(registry.getUrl());
		} else {
			return registry.getName();
		}
	}

	public static Registry registryFromInput(String name, String url, String username, String password, String note) {
		return new Registry(name, url, username, password, note);
	}

}
