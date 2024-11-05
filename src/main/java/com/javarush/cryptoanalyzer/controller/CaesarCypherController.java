package com.javarush.cryptoanalyzer.controller;

import com.javarush.cryptoanalyzer.model.Cipher;
import com.javarush.cryptoanalyzer.model.FileManager;
import com.javarush.cryptoanalyzer.model.Validator;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;

public class CaesarCypherController {

    @FXML private TextField encryptKeyField;
    @FXML private TextField decryptKeyField;
    @FXML private Button encryptButton;
    @FXML private Button decryptButton;
    @FXML private Button selectFileButton;
    @FXML private Label statusLabel;

    private Cipher cipher;
    private FileManager fileManager;
    private Validator validator;
    private Stage stage;
    private File encryptSelectedFile;
    private File decryptSelectedFile;

    public CaesarCypherController() {
        // Initialize dependencies in the no-argument constructor
        this.cipher = new Cipher();
        this.fileManager = new FileManager();
        this.validator = new Validator();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }


    @FXML
    public void onSelectEncryptFileButtonClicked () {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a File to Encrypt");
        this.encryptSelectedFile = fileChooser.showOpenDialog(stage);

        if (encryptSelectedFile != null) {
            statusLabel.setText("Selected file for encryption: " + encryptSelectedFile.getPath());
        } else {
            statusLabel.setText("File selection was cancelled.");
        }
    }

    @FXML
    public void onSelectDecryptFileButtonClicked () {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a File to Decrypt");
        this.decryptSelectedFile = fileChooser.showOpenDialog(stage);

        if (decryptSelectedFile != null) {
            statusLabel.setText("Selected file for Decryption: " + decryptSelectedFile.getPath());
        } else {
            statusLabel.setText("File selection was cancelled.");
        }
    }

    @FXML
    public void onEncryptButtonClicked() {
        if (encryptSelectedFile == null) {
            statusLabel.setText("No file selected. Please select a file first.");
            return;
        }

        try {
            int key = Integer.parseInt(encryptKeyField.getText());

            if (!validator.isValidKey(key, cipher.getALPHABET())) {
                statusLabel.setText("Invalid key. Please enter a valid integer within the acceptable range.");
                return;
            }

            key = validator.normalizeKey(key, cipher.getALPHABET().length);

            if (validator.isFileExists(encryptSelectedFile.getPath()) && validator.isFileReadable(encryptSelectedFile.getPath())) {
                String content = fileManager.readFile(encryptSelectedFile.getPath());
                String encryptedText = cipher.encrypt(content, key);

                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Encrypted File");
                fileChooser.setInitialFileName("encrypted_output.txt");
                File outputFile = fileChooser.showSaveDialog(stage);

                if (outputFile != null) {
                    fileManager.writeFile(encryptedText, outputFile.getPath());
                    statusLabel.setText("Encryption successful! File saved as " + outputFile.getName());
                } else {
                    statusLabel.setText("File save operation was cancelled.");
                }
            } else {
                statusLabel.setText("File not found or is not readable.");
            }
        } catch (NumberFormatException e) {
            statusLabel.setText("Invalid key. Please enter a valid integer.");
        } catch (IOException e) {
            statusLabel.setText("An error occurred: " + e.getMessage());
        }
    }

    @FXML
    public void onDecryptButtonClicked() {
        if (decryptSelectedFile == null) {
            statusLabel.setText("No file selected. Please select a file first.");
            return;
        }

        try {
            int key = Integer.parseInt(decryptKeyField.getText());

            if (!validator.isValidKey(key, cipher.getALPHABET())) {
                statusLabel.setText("Invalid key. Please enter a valid integer within the acceptable range.");
                return;
            }

            key = validator.normalizeKey(key, cipher.getALPHABET().length);

            if (validator.isFileExists(decryptSelectedFile.getPath())) {
                String content = fileManager.readFile(decryptSelectedFile.getPath());
                String decryptedText = cipher.decrypt(content, key);

                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save Decrypted File");
                fileChooser.setInitialFileName("decrypted_output.txt");
                File outputFile = fileChooser.showSaveDialog(stage);

                if (outputFile != null) {
                    fileManager.writeFile(decryptedText, outputFile.getPath());
                    statusLabel.setText("Decryption successful! File saved as " + outputFile.getName());
                } else {
                    statusLabel.setText("File save operation was cancelled.");
                }
            } else {
                statusLabel.setText("File not found or invalid.");
            }
        } catch (NumberFormatException e) {
            statusLabel.setText("Invalid key. Please enter a valid integer.");
        } catch (IOException e) {
            statusLabel.setText("An error occurred: " + e.getMessage());
        }
    }
}