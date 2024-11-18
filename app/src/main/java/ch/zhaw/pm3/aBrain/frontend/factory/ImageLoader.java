package ch.zhaw.pm3.aBrain.frontend.factory;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


/**
 * Used as a helper class to load images via a path.
 *
 * @author baermlau
 */
public class ImageLoader {
    /**
     * Gets image from path.
     *
     * @param path the path
     * @return the image from path
     * @throws NullPointerException     the null pointer exception
     * @throws IllegalArgumentException the illegal argument exception
     * @throws FileNotFoundException    the file not found exception
     */
    public static Image getImageFromPath(String path) throws NullPointerException, IllegalArgumentException, FileNotFoundException {
        try {
            File imageFile = new File(path);
            return new Image(new FileInputStream(imageFile));
        } catch (NullPointerException e) {
            throw new NullPointerException(e.getCause() + ": " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e.getCause() + ": " + e.getMessage());
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException(e.getMessage());
        }
    }
}
