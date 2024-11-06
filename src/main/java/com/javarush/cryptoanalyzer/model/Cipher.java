package com.javarush.cryptoanalyzer.model;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Class responsible for encrypting and decrypting text using the Caesar Cipher algorithm.
 */
public class Cipher {
    private final char[] ALPHABET;
    private final Map<Character, Integer> charToIndexMap;

    /**
     * Constructs a Cipher instance and initializes the alphabet and character index map.
     */
    public Cipher() {
        this.ALPHABET = generateAlphabet();
        this.charToIndexMap = createCharToIndexMap();
    }

    /**
     * Returns the alphabet used for encryption and decryption.
     *
     * @return The character array representing the alphabet.
     */
    public char[] getALPHABET() {
        return ALPHABET;
    }

    /**
     * Encrypts the given text using the Caesar Cipher algorithm with the specified shift key.
     *
     * @param text The text to encrypt.
     * @param shift The number of positions to shift each character.
     * @return The encrypted text.
     */
    public String encrypt(String text, int shift) {
        if (text == null) {
            throw new IllegalArgumentException("Input text cannot be null.");
        }

        StringBuilder encryptedText = new StringBuilder();
        for (char c : text.toCharArray()) {
            int index = indexOfChar(c);
            if (index != -1) {
                int shiftedIndex = (index + shift) % ALPHABET.length;
                encryptedText.append(ALPHABET[shiftedIndex]);
            } else {
                // If a character is not in ALPHABET append it as it is
                encryptedText.append(c);
            }
        }
        return encryptedText.toString();
    }

    /**
     * Decrypts the given text using the Caesar Cipher algorithm with the specified shift key.
     *
     * @param encryptedText The text to decrypt.
     * @param shift The number of positions each character was shifted.
     * @return The decrypted text.
     */
    public String decrypt(String encryptedText, int shift) {
        if (encryptedText == null) {
            throw new IllegalArgumentException("Input text cannot be null.");
        }

        StringBuilder decryptedText = new StringBuilder();

        for (char c : encryptedText.toCharArray()) {
            int index = indexOfChar(c);
            if (index != -1) {
                int shiftedIndex = (index - shift) % ALPHABET.length;
                if (shiftedIndex < 0) {
                    shiftedIndex += ALPHABET.length;
                }
                decryptedText.append(ALPHABET[shiftedIndex]);
            } else {
                // If character is not in the alphabet, append it as is
                decryptedText.append(c);
            }
        }
        return decryptedText.toString();
    }

    /**
     * Finds the index of a character in the alphabet.
     *
     * @param c The character to find.
     * @return The index of the character, or -1 if not found.
     */
    private int indexOfChar(char c) {
        return charToIndexMap.getOrDefault(c, -1);
    }

    /**
     * Creates a mapping from characters to their indices in the alphabet.
     *
     * @return A map of characters to indices.
     */
    private Map<Character, Integer> createCharToIndexMap() {
        Map<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < ALPHABET.length; i++) {
            map.put(ALPHABET[i], i);
        }
        return map;
    }

    /**
     * Generates the alphabet used for encryption and decryption, including English and Russian letters, digits, and special characters.
     *
     * @return A character array representing the alphabet.
     */
    private char[] generateAlphabet() {
        Set<Character> characterSet = new LinkedHashSet<>();

        // Add English uppercase letters
        for (char c = 'A'; c <= 'Z'; c++) {
            characterSet.add(c);
        }
        // Add English lowercase letters
        for (char c = 'a'; c <= 'z'; c++) {
            characterSet.add(c);
        }
        // Add Russian uppercase letters
        for (char c = 'А'; c <= 'Я'; c++) {
            characterSet.add(c);
        }
        // Add Russian lowercase letters
        for (char c = 'а'; c <= 'я'; c++) {
            characterSet.add(c);
        }
        // Add digits
        for (char c = '0'; c <= '9'; c++) {
            characterSet.add(c);
        }

        // Add special characters
        String specialChars = ".,!?;:()\"'[]{}-_=+@#$%^&*<>/\\|`~ \n\t";
        for (char c : specialChars.toCharArray()) {
            characterSet.add(c);
        }

        //convert characterSet to array
        char[] alphabetArray = new char[characterSet.size()];
        int index = 0;
        for (char c : characterSet) {
            alphabetArray[index] = c;
            index++;
        }

        return alphabetArray;
    }
}
