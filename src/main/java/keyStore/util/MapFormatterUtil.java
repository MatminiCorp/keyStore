package keyStore.util;

import java.util.Map;
import java.util.Set;

import keyStore.manager.Key;

public class MapFormatterUtil {

	public static Object[][] toObject(Map<String, Key> map) {
		Set<Map.Entry<String, Key>> entrySet = map.entrySet();
		Object[][] array = new Object[entrySet.size()][3];

		int index = 0;
		for (Map.Entry<String, Key> entry : entrySet) {
			Key key = entry.getValue();
			array[index][0] = key.getUser();
			array[index][1] = key.getPassword();
			array[index][2] = key.getWebsite();
			index++;
		}

		if (entrySet == null || entrySet.isEmpty()) {
			array = new Object[1][3];
			Key key = new Key("", "", "");
			array[index][0] = key.getUser();
			array[index][1] = key.getPassword();
			array[index][2] = key.getWebsite();
		}

		return array;

	}

}
