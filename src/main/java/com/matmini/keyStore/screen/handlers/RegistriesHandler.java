package com.matmini.keyStore.screen.handlers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.matmini.keyStore.manager.Registry;
import com.matmini.keyStore.util.MapFormatterUtil;

public class RegistriesHandler {

	public static Object[][] parseListToObject(List<Registry> list) {
		Map<String, Registry> map = new HashMap<>();
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
		return registry.getUser().concat(registry.getWebsite());
	}

	public static Registry registryFromInput(String user, String password, String website) {
		return new Registry(user, password, website);

	}

}
