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

    /**
     * Initializes and creates the bitmap from an image
     *
     * @param file The directory of the image
     */
    public Bitmap(String file) {
        this.file = file;
        this.byteArray = extractFromFile();
    }

    /**
     * @return The vector of bits that make up the image
     */
    public byte[] getByteArray() {
        return byteArray;
    }

    /**
     * Extract the bit vector indicates the image from directory
     *
     * @return The vector of bits that make up the image
     */
    private byte[] extractFromFile() {
        try {
            return Files.readAllBytes(new File(file).toPath());
        } catch (IOException ex) {
            return null;
        }
    }
}