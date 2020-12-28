package com.picsart.bookstorelibrary.utils;


import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileReader {

    public static List<String> readFile(String filePath) throws IOException {
        List<String> content;
        try {
            Path path = Paths.get(filePath);
            if (!Files.exists(path)) {
                content = new ArrayList<>();
            } else {
                content = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            throw new IOException("File not found " + e.getMessage());
        }
        return content;
    }
}
