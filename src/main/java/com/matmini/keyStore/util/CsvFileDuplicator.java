package com.matmini.keyStore.util;
import java.io.File;
import java.io.IOException;

public class CsvFileDuplicator {

    public static File createEditedJsonCopy(String originalPath) {
        File originalFile = new File(originalPath);

        if (!originalFile.exists()) {
            throw new IllegalArgumentException("Original file not found: " + originalPath);
        }

        if (originalFile.length() == 0) {
            System.out.println("Original file is empty. JSON copy not created.");
            return null;
        }

        String originalName = originalFile.getName();
        int dotIndex = originalName.lastIndexOf('.');
        String baseName = (dotIndex != -1) ? originalName.substring(0, dotIndex) : originalName;

        String newName = baseName + "__edited.json";
        File newFile = new File(originalFile.getParent(), newName);

        try {
            if (!newFile.exists()) {
                boolean created = newFile.createNewFile();
                if (!created) {
                    throw new IOException("Failed to create the new JSON file.");
                }
            }
            return newFile;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create empty JSON file.");
        }
    }
}
