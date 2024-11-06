package com.javarush.cryptoanalyzer.controller;

import com.javarush.cryptoanalyzer.model.BruteForce;
import com.javarush.cryptoanalyzer.model.Cipher;
import com.javarush.cryptoanalyzer.model.FileManager;
import com.javarush.cryptoanalyzer.model.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;


/**
 * Controller class for the Caesar Cipher Analyzer application.
 * Handles user interactions with the GUI.
 */
public class CaesarCypherController {

    // FXML components
    @FXML private TextField encryptKeyField;
    @FXML private TextField decryptKeyField;
    @FXML private TextArea bruteForceResultArea;
    @FXML private Label encryptStatusLabel;
    @FXML private Label decryptStatusLabel;
    @FXML private Label bruteForceStatusLabel;
    @FXML private ComboBox<String> languageComboBox;

    // Application components
    private Cipher cipher;
    private FileManager fileManager;
    private Validator validator;
    private Stage stage;
    private File encryptSelectedFile;
    private File decryptSelectedFile;
    private File bruteForceSelectedFile;

    /**
     * Constructor initializes the Cipher, FileManager, and Validator instances.
     */
    public CaesarCypherController() {
        this.cipher = new Cipher();
        this.fileManager = new FileManager();
        this.validator = new Validator();
    }

    /**
     * Sets the primary stage of the application.
     *
     * @param stage The primary stage.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Handles the action when the "Select Encrypt File" button is clicked.
     * Opens a file chooser dialog for selecting a file to encrypt.
     */
    @FXML
    public void onSelectEncryptFileButtonClicked () {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a File to Encrypt");
        this.encryptSelectedFile = fileChooser.showOpenDialog(stage);

        if (encryptSelectedFile != null) {
            encryptStatusLabel.setText("Selected file for encryption: " + encryptSelectedFile.getPath());
        } else {
            encryptStatusLabel.setText("File selection was cancelled.");
        }
    }

    /**
     * Handles the action when the "Select Decrypt File" button is clicked.
     * Opens a file chooser dialog for selecting a file to decrypt.
     */
    @FXML
    public void onSelectDecryptFileButtonClicked () {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a File to Decrypt");
        this.decryptSelectedFile = fileChooser.showOpenDialog(stage);

        if (decryptSelectedFile != null) {
            decryptStatusLabel.setText("Selected file for Decryption: " + decryptSelectedFile.getPath());
        } else {
            decryptStatusLabel.setText("File selection was cancelled.");
        }
    }

    /**
     * Handles the action when the "Select Brute Force File" button is clicked.
     * Opens a file chooser dialog for selecting a file to decrypt using brute force.
     */
    @FXML
    public void onBruteForceSelectFileButtonClicked() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a File for Brute Force");
        this.bruteForceSelectedFile = fileChooser.showOpenDialog(stage);

        if (bruteForceSelectedFile != null) {
            bruteForceStatusLabel.setText("Selected file for brute force: " + bruteForceSelectedFile.getPath());
        } else {
            bruteForceStatusLabel.setText("File selection was cancelled.");
        }
    }

    /**
     * Handles the action when the "Encrypt" button is clicked.
     * Encrypts the selected file using the provided key.
     */
    @FXML
    public void onEncryptButtonClicked() {
        if (encryptSelectedFile == null) {
            encryptStatusLabel.setText("No file selected. Please select a file first.");
            return;
        }

        try {
            int key = Integer.parseInt(encryptKeyField.getText());

            if (!validator.isValidKey(key, cipher.getALPHABET())) {
                encryptStatusLabel.setText("Invalid key. Please enter a valid integer within the acceptable range.");
                return;
            }

            key = validator.normalizeKey(key, cipher.getALPHABET().length);

            if (validator.isFileExists(encryptSelectedFile.getPath()) && validator.isFileReadable(encryptSelectedFile.getPath())) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Encrypted File");
                fileChooser.setInitialFileName("encrypted_output.txt");
                File outputFile = fileChooser.showSaveDialog(stage);

                if (outputFile != null) {
                    fileManager.processFile(encryptSelectedFile.getPath(), outputFile.getPath(), key, true);
                    encryptStatusLabel.setText("Encryption successful! File saved as " + outputFile.getName());
                } else {
                    encryptStatusLabel.setText("File save operation was cancelled.");
                }
            } else {
                encryptStatusLabel.setText("File not found or is not readable.");
            }
        } catch (NumberFormatException e) {
            encryptStatusLabel.setText("Invalid key. Please enter a valid integer.");
        } catch (IOException e) {
            encryptStatusLabel.setText("An error occurred: " + e.getMessage());
        }
    }

    /**
     * Handles the action when the "Decrypt" button is clicked.
     * Decrypts the selected file using the provided key.
     */
    @FXML
    public void onDecryptButtonClicked() {
        if (decryptSelectedFile == null) {
            decryptStatusLabel.setText("No file selected. Please select a file first.");
            return;
        }

        try {
            int key = Integer.parseInt(decryptKeyField.getText());

            if (!validator.isValidKey(key, cipher.getALPHABET())) {
                decryptStatusLabel.setText("Invalid key. Please enter a valid integer within the acceptable range.");
                return;
            }

            key = validator.normalizeKey(key, cipher.getALPHABET().length);

            if (validator.isFileExists(decryptSelectedFile.getPath())) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Decrypted File");
                fileChooser.setInitialFileName("decrypted_output.txt");
                File outputFile = fileChooser.showSaveDialog(stage);

                if (outputFile != null) {
                    fileManager.processFile(decryptSelectedFile.getPath(), outputFile.getPath(), key, false);
                    decryptStatusLabel.setText("Decryption successful! File saved as " + outputFile.getName());
                } else {
                    decryptStatusLabel.setText("File save operation was cancelled.");
                }
            } else {
                decryptStatusLabel.setText("File not found or invalid.");
            }
        } catch (NumberFormatException e) {
            decryptStatusLabel.setText("Invalid key. Please enter a valid integer.");
        } catch (IOException e) {
            decryptStatusLabel.setText("An error occurred: " + e.getMessage());
        }
    }

    /**
     * Handles the action when the "Brute Force" button is clicked.
     * Attempts to decrypt the selected file by trying all possible keys.
     */
    @FXML
    public void onBruteForceButtonClicked() {
        if (bruteForceSelectedFile == null) {
            bruteForceStatusLabel.setText("No file selected. Please select a file first.");
            return;
        }

        String selectedLanguage = languageComboBox.getValue();
        if (selectedLanguage == null || selectedLanguage.isEmpty()) {
            bruteForceStatusLabel.setText("Please select the expected language.");
            return;
        }

        try {
            BruteForce bruteForce = new BruteForce(cipher);
            int bestKey = bruteForce.findBestKey(bruteForceSelectedFile.getPath(), selectedLanguage);


            if (bestKey != -1) {
                String snippet = decryptSnippet(bruteForceSelectedFile.getPath(), bestKey);
                bruteForceResultArea.setText("Best decryption with key " + bestKey + ":\n" + snippet);
                bruteForceStatusLabel.setText("Brute force decryption completed. Best key: " + bestKey);


                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Save Decrypted File");
                alert.setHeaderText("Do you want to save the full decrypted text to a file?");
                alert.setContentText("Click OK to save, or Cancel to dismiss.");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK){
                    FileChooser fileChooser = new FileChooser();
                    fileChooser.setTitle("Save Decrypted File");
                    fileChooser.setInitialFileName("decrypted_brute_force.txt");
                    File outputFile = fileChooser.showSaveDialog(stage);

                    if (outputFile != null) {
                        fileManager.processFile(bruteForceSelectedFile.getPath(), outputFile.getPath(), bestKey, false);
                        bruteForceStatusLabel.setText("Decrypted file saved as " + outputFile.getName());
                    } else {
                        bruteForceStatusLabel.setText("File save operation was cancelled.");
                    }
                }

            } else {
                bruteForceStatusLabel.setText("Failed to decrypt the text using brute force.");
            }
        } catch (IOException e) {
            bruteForceStatusLabel.setText("An error occurred: " + e.getMessage());
        }
    }

    /**
     * Decrypts a snippet of the file for preview purposes.
     *
     * @param inputFilePath The path to the input file.
     * @param key The decryption key.
     * @return A decrypted snippet of the file content.
     * @throws IOException If an error occurs during file reading.
     */
    private String decryptSnippet(String inputFilePath, int key) throws IOException {
        StringBuilder snippetBuilder = new StringBuilder();
        Path inputPath = Paths.get(inputFilePath);

        try (BufferedReader reader = Files.newBufferedReader(inputPath, StandardCharsets.UTF_8)) {
            String line;
            while ((line = reader.readLine()) != null && snippetBuilder.length() < 500) {
                String decryptedLine = cipher.decrypt(line, key);
                snippetBuilder.append(decryptedLine).append(System.lineSeparator());
            }
        }

        return snippetBuilder.toString();
    }
}
