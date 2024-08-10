package keyStore.util;

import java.util.Map;
import java.util.Set;

import keyStore.manager.Registry;

public class MapFormatterUtil {

	public static Object[][] toObject(Map<String, Registry> map) {
		Set<Map.Entry<String, Registry>> entrySet = map.entrySet();
		Object[][] array = new Object[entrySet.size()][3];

		int index = 0;
		for (Map.Entry<String, Registry> entry : entrySet) {
			Registry key = entry.getValue();
			array[index][0] = key.getUser();
			array[index][1] = key.getPassword();
			array[index][2] = key.getWebsite();
			index++;
		}

//		if (entrySet == null || entrySet.isEmpty()) {
//			array = new Object[1][3];
//			Registry key = new Registry("", "", "");
//			array[index][0] = key.getUser();
//			array[index][1] = key.getPassword();
//			array[index][2] = key.getWebsite();
//		}

		return array;

	}

}
