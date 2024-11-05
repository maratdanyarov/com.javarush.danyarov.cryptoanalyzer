package com.javarush.cryptoanalyzer.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class BruteForce {
    private final Cipher cipher;
    private final Validator validator;

    public BruteForce(Cipher cipher) {
        this.cipher = cipher;
        this.validator = new Validator();
    }

    public int findBestKey(String inputFilePath, String language) throws IOException {
        if (inputFilePath == null || language == null || language.isEmpty()) {
            throw new IllegalArgumentException("Invalid input or language.");
        }

        int alphabetLength = cipher.getALPHABET().length;
        int bestScore = -1;
        int bestKey = -1;

        for (int key = 1; key < alphabetLength; key++) {
            int normalizedKey = validator.normalizeKey(key, alphabetLength);
            int totalScore = 0;

            Path inputPath = Paths.get(inputFilePath);

            try (BufferedReader reader = Files.newBufferedReader(inputPath, StandardCharsets.UTF_8)) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String decryptedLine = cipher.decrypt(line, normalizedKey);
                    int lineScore = scoreDecryptedText(decryptedLine, language);
                    totalScore += lineScore;
                }
            }

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

    private int scoreDecryptedText(String text, String language) {
        String[] commonWords;

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
