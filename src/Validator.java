import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

public class Validator {
    public boolean isValidKey(int key, char[] alphabet) {
        int normalizedKey = normalizeKey(key, alphabet.length);

        if (normalizedKey >= 0 && normalizedKey < alphabet.length) {
            return true;
        } else {
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
        Path path = Path.of(filePath);
        return Files.exists(path) && !Files.isDirectory(path);
    }

    public boolean isFileReadable(String filePath) {
        // TODO add Readability check
        File file = new File(filePath);

        return false;
    }

    public boolean isFileWritable(String filePath) {
        // TODO add Writability check
        return false;
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
