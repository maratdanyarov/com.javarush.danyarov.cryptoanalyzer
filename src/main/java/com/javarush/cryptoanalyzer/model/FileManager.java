package com.javarush.cryptoanalyzer.model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileManager {

    private Validator validator;

    public FileManager() {
        this.validator = new Validator();
    }

    public void processFile(String inputFilePath, String outputFilePath, int key, boolean isEncrypt) throws IOException {
        if (!validator.isFileExists(inputFilePath)) {
            throw new IOException("File does not exist: " + inputFilePath);
        }

        if (!validator.isFileReadable(inputFilePath)) {
            throw new IOException("File is not readable: " + inputFilePath);
        }

        Path inputPath = Paths.get(inputFilePath);
        Path outputPath = Paths.get(outputFilePath);

        Path parentDir = outputPath.getParent();
        if (parentDir != null && (!Files.exists(parentDir) || !Files.isWritable(parentDir))) {
            throw new IOException("Cannot write to the directory: " + parentDir.toString());
        }

        Cipher cipher = new Cipher();

        try (BufferedReader reader = Files.newBufferedReader(inputPath, StandardCharsets.UTF_8);
             BufferedWriter writer = Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8, StandardOpenOption.CREATE)) {

            String line;
            while ((line = reader.readLine()) != null) {
                String processedLine;
                if (isEncrypt) {
                    processedLine = cipher.encrypt(line, key);
                } else {
                    processedLine = cipher.decrypt(line, key);
                }
                writer.write(processedLine);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new IOException("Error processing file: " + e.getMessage(), e);
        }
    }
}

