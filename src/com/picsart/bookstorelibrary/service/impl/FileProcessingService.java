package com.picsart.bookstorelibrary.service.impl;

import com.picsart.bookstorelibrary.utils.FileReader;
import com.picsart.bookstorelibrary.utils.FileWriter;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FileProcessingService {

    private static FileProcessingService fileService;

    private FileProcessingService() {
    }

    public static FileProcessingService getInstance() {
        if (fileService == null) {
            fileService = new FileProcessingService();
        }

        return fileService;
    }

    public void exportDataToFile(String filePath, String data) throws IOException {
        FileWriter.writeToFile(filePath, data);

    }

    public List<String> readFromFile(String pathName) throws IOException {
        return FileReader.readFile(pathName);
    }

    public void updateFile(String filePath, String updated) throws IOException {
        Path path = Paths.get(filePath);
        Charset charset = StandardCharsets.UTF_8;
        Files.writeString(path, updated, charset);
    }
}
