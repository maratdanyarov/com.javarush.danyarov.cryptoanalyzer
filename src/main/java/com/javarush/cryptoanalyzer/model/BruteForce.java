package com.javarush.cryptoanalyzer.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Class responsible for performing brute-force decryption of Caesar Cipher encrypted files.
 */
public class BruteForce {
    private final Cipher cipher;
    private final Validator validator;

    /**
     * Constructs a BruteForce instance with the given Cipher.
     *
     * @param cipher The Cipher instance used for decryption.
     */
    public BruteForce(Cipher cipher) {
        this.cipher = cipher;
        this.validator = new Validator();
    }

    /**
     * Finds the best key by trying all possible shifts and scoring the decrypted text.
     *
     * @param inputFilePath The path to the encrypted file.
     * @param language The expected language of the decrypted text.
     * @return The key that yields the highest score, or -1 if no suitable key is found.
     * @throws IOException If an error occurs while reading the file.
     */
    public int findBestKey(String inputFilePath, String language) throws IOException {
        if (inputFilePath == null || language == null || language.isEmpty()) {
            throw new IllegalArgumentException("Invalid input or language.");
        }

        int alphabetLength = cipher.getALPHABET().length;
        int bestScore = -1;
        int bestKey = -1;

        // Try all possible keys
        for (int key = 1; key < alphabetLength; key++) {
            int normalizedKey = validator.normalizeKey(key, alphabetLength);
            int totalScore = 0;

            Path inputPath = Paths.get(inputFilePath);

            // Read the file line by line
            try (BufferedReader reader = Files.newBufferedReader(inputPath, StandardCharsets.UTF_8)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // Decrypt the line using the current key
                    String decryptedLine = cipher.decrypt(line, normalizedKey);
                    // Score the decrypted line
                    int lineScore = scoreDecryptedText(decryptedLine, language);
                    totalScore += lineScore;
                }
            }

            // Update the best key if the current total score is higher
            if (totalScore > bestScore) {
                bestScore = totalScore;
                bestKey = normalizedKey;
            }
        }

        if (bestKey != -1) {
            System.out.println("Best key found: " + bestKey);
        } else {
            System.out.println("Failed to find a suitable key using brute force.");

        }
        return bestKey;
    }

    /**
     * Scores the decrypted text based on the occurrence of common words in the specified language.
     *
     * @param text The decrypted text to score.
     * @param language The language to use for scoring.
     * @return The score representing the likelihood that the text is correctly decrypted.
     */
    private int scoreDecryptedText(String text, String language) {
        String[] commonWords;

        // Select common words based on the language
        if ("English".equalsIgnoreCase(language)) {
            commonWords = new String[]{" the ", " and ", " to ", " of ", " a ", " in ", " that ", " is ", " it ", " i "};
        } else if ("Russian".equalsIgnoreCase(language)) {
            commonWords = new String[]{" и ", " в ", " не ", " на ", " я ", " быть ", " с ", " что ", " а ", " по "};
        } else {
            // if language is not recognised, assume English as default
            commonWords = new String[]{" the ", " and ", " to ", " of ", " a ", " in ", " that ", " is ", " it ", " i "};
        }

        int score = 0;

        String lowerText = text.toLowerCase();

        // Count occurrences of common words
        for (String word : commonWords) {
            int index = lowerText.indexOf(word);
            while (index != -1) {
                score++;
                index = lowerText.indexOf(word, index + word.length());
            }
        }
        return score;
    }
}
