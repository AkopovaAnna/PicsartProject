package com.picsart.bookstorelibrary.utils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileWriter {

    public static void writeToFile(String pathname, String entityString) throws IOException {

        Path path = Paths.get(pathname);
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        Files.writeString(path,
                entityString,
                StandardCharsets.UTF_8,
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND);

    }
}
