package radikalchess.model;

/**
 * An image is an object containing a bitmap
 *
 * @author Jose Luis Molina
 * @see radikalchess.model.Bitmap
 */
public class Image {

    private final Bitmap bitmap;

    public Image(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
