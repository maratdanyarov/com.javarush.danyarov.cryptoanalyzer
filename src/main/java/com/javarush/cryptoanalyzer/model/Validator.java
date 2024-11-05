package com.javarush.cryptoanalyzer.model;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Validator {
    public boolean isValidKey(int key, char[] alphabet) {
        if (alphabet == null || alphabet.length == 0) {
            return false;
        }
        try {
            normalizeKey(key, alphabet.length);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public int normalizeKey(int key, int alphabetLength) {
        if (alphabetLength <= 0) {
            throw new IllegalArgumentException("Alphabet length must be positive");
        }

        int normalizedKey = key % alphabetLength;
        if (normalizedKey < 0) {
            normalizedKey += alphabetLength;
        }
        return normalizedKey;
    }

    public boolean isFileExists(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            return false;
        }
        try {
            Path path = Paths.get(filePath);
            return Files.exists(path) && !Files.isDirectory(path);
        } catch (InvalidPathException e) {
            return false;
        }
    }

    public boolean isFileReadable(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            return false;
        }
        try {
            Path path = Paths.get(filePath);
            return Files.isReadable(path);
        } catch (InvalidPathException e) {
            return false;
        }
    }

    public boolean isFileWritable(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            return false;
        }
        try {
            Path path = Paths.get(filePath);
            return Files.isWritable(path);
        } catch (InvalidPathException e) {
            return false;
        }
    }

    public boolean isValidMode(String mode) {
        // TODO add mode validation
        return false;
    }

    public boolean validateArguments(String[] args) {
        // TODO add arguments validation logic
        return false;
    }
}
