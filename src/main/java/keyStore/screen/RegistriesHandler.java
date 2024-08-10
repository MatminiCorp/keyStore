package keyStore.screen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import keyStore.manager.Registry;
import keyStore.util.MapFormatterUtil;

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
