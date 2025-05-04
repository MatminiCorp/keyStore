package com.matmini.keyStore.manager;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.reflect.TypeToken;
import com.matmini.keyStore.util.GsonUtil;

public class FilesManager {

	private static FilesManager instance;
	private static File filePath;

	private FilesManager() {
	}

	public static FilesManager getInstance() {
		return instance;
	}

	public static FilesManager getInstance(File file) {
		if (instance == null) {
			instance = new FilesManager();
			filePath = file;
		}
		return instance;
	}

	public Map<String, Registry> getContentAsMap() {
		Map<String, Registry> map = GsonUtil.fromJson(getContentAsString(), new TypeToken<Map<String, Registry>>() {
		}.getType());
		return map != null ? map : new HashMap<>();
	}

	public void overwriteContentAsMap(Map<String, Registry> keys) {
		overwriteFile(GsonUtil.toJson(keys));
	}

	private String getContentAsString() {
		StringBuilder content = new StringBuilder();

		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			String line = "";
			while ((line = reader.readLine()) != null) {
				content.append(line).append("\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return content.toString();
	}

	private void overwriteFile(String newContent) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
			writer.write(newContent);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void importOrReplaceAll(List<Registry> newEntries) {
		Map<String, Registry> current = getContentAsMap();
		for (Registry registry : newEntries) {
			current.put(registry.getName(), registry);
		}
		overwriteContentAsMap(current);
	}
}
