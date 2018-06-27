import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Background {
	 
    /**
     * This is used to store the background image.
     */
    private Image image;
    
    /**
     * Constructs the object which takes care of the background image.
     */
    public Background(String fileName, int width, int height) {
        try {
            image = ( ImageIO.read(new File(fileName)) ).getScaledInstance(width, height, Image.SCALE_DEFAULT); // Read in the file and scale it
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * This is used to draw the background image onto the JPanel.
     */
    public void draw(Graphics graphics) { 
        graphics.drawImage(image, 0, 0, null);

    }  // end of draw()
}
