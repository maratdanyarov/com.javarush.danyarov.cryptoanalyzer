package com.javarush.cryptoanalyzer.model;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Class responsible for validating keys and file paths.
 */
public class Validator {
    /**
     * Checks if the provided key is valid for the given alphabet.
     *
     * @param key The key to validate.
     * @param alphabet The alphabet array.
     * @return True if the key is valid, false otherwise.
     */
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

    /**
     * Normalizes the key to ensure it falls within the range of the alphabet length.
     *
     * @param key The key to normalize.
     * @param alphabetLength The length of the alphabet.
     * @return The normalized key.
     */
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

    /**
     * Checks if the file exists and is not a directory.
     *
     * @param filePath The path to the file.
     * @return True if the file exists, false otherwise.
     */
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

    /**
     * Checks if the file is readable.
     *
     * @param filePath The path to the file.
     * @return True if the file is readable, false otherwise.
     */
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

    /**
     * Checks if the file is writable.
     *
     * @param filePath The path to the file.
     * @return True if the file is writable, false otherwise.
     */
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
}
