package radikalchess.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * An object that contains a vector of bits. Compose an image
 *
 * @author Jose Luis Molina
 * @see radikalchess.model.Image
 */
public class Bitmap {

    private final String file;
    private final byte[] byteArray;

    public Bitmap(String file) {
        this.file = file;
        this.byteArray = extractFromFile();
    }

    public byte[] getByteArray() {
        return byteArray;
    }

    private byte[] extractFromFile() {
        try {
            return Files.readAllBytes(new File(file).toPath());
        } catch (IOException ex) {
            return null;
        }
    }
}