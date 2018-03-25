package com.quasar.rdsalg;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileSystemHelper {

    public static void createDirectoriesIfNecessary(String directory) {
        if (!new File(directory).exists())
            if (!new File(directory).mkdirs())
                throw new RuntimeException("Failed to create directory(s): " + directory);
    }

    static void saveFile(String filePath, String fileContent, boolean doOverwrite) throws IOException {
        File file = new File(filePath);
        createDirectoriesIfNecessary(file.getParentFile().getAbsolutePath());
        if (doOverwrite || !file.exists()) {
            try (FileWriter f = new FileWriter(filePath)) {
                f.write(fileContent);
                f.flush();
            }
        }
    }
}
