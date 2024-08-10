package keyStore.screen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import keyStore.manager.Key;
import keyStore.util.MapFormatterUtil;

public class RegistriesHandler {

	public static Object[][] parseListToObject(List<Key> list) {
		Map<String, Key> map = new HashMap<>();
		for (Key key2 : list) {
			map.put(getKeyMap(key2), key2);
		}
		return MapFormatterUtil.toObject(map);
	}

	public static Map<String, Key> parseKeyToMap(Key key) {
		Map<String, Key> map = new HashMap<>();
		map.put(getKeyMap(key), key);
		return map;
	}

	public static String getKeyMap(Key key) {
		return key.getUser().concat(key.getWebsite());
	}

	public static Key getKeyFromInput(String user, String password, String website) {
		return new Key(user, password, website);

	}

}
