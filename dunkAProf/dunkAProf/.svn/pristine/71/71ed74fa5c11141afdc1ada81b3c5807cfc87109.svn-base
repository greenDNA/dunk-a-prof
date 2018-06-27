import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Professor {
    //Stores the person's image
    private Image image;
    // private Image dunkedImage;
    // The initial x and y positions. Used for resetting the professor to his original position.
    private int startX;
    private int startY;

    private Animation splashAnimation;

    // Stores the x and y positions of the people
    private int xPosition;
    private int yPosition;

    boolean dunked = false;
    MediaPlayer scream;

    /**
     * Constructs a new object which represents the professor on the JPanel.
     *
     * @param	fileName	-	The filename of the professor's image
     * @param	screamFile	-	The filename of the scream that will be played when the professor is dunked.
     * @param	xPosition	-	The professor's initial x coordinate on the JPanel
     * @param	yPosition	-	The professor's initial y coordinate on the JPanel
     */
    public Professor(String fileName, String screamFile, int xPosition, int yPosition) {
        try {
        	// Read in the file and scale it
            image = ( ImageIO.read(new File(fileName)) ).getScaledInstance(69, 120, Image.SCALE_DEFAULT); 
        } catch (IOException e) {
            e.printStackTrace();
        }
        splashAnimation = new Animation("images/Smoke/cloud-", 9, 4);
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.startX = xPosition;
        this.startY = yPosition;
        this.scream = new MediaPlayer(screamFile);
    }

    /**
     * This method draws the professor's image to the JPanel.
     *
     * @param  graphics	-	A reference to the JPanel buffer
     */
    public void draw(Graphics graphics) {

        // Draw the person
        graphics.drawImage(image, this.xPosition, this.yPosition, null);
        graphics.drawImage(splashAnimation.getImage(), 0, 149, splashAnimation.getImage().getWidth(null), splashAnimation.getImage().getHeight(null), null);


    }  // end of draw()

    /**
     * This method returns the professor's x coordinate on the JPanel.
     *
     * @return      The x coordinate of the professor on the JPanel.
     */
    public int getXPosition() {
        return this.xPosition;
    }

    /**
     * This method returns the professor's y coordinate on the JPanel.
     *
     * @return      The y coordinate of the professor on the JPanel.
     */
    public int getYPosition() {
        return this.yPosition;
    }

    /**
     * This method returns a rectangle which is the bounding box of the professor's image
     *
     * @return      A Rectangle object which is the bounding box of the professor's image.
     */
    public Rectangle getBoundingBox() {
        return new Rectangle(this.xPosition, this.yPosition, this.image.getWidth(null), this.image.getHeight(null));
    }

    /**
     * This method is used to indicate that the professor has been hit and will now be in a state of being dunked.
     */
    public void dunk() {
        this.scream.play();
        dunked = true;


    }

    /**
     * This method is used to check if the professor is in a state of being dunked.
     *
     * @return      True if the professor is dunked, otherwise it returns false.
     */
    public boolean isDunked() {
        return dunked;
    }

    /**
     * This method is used to modify the state of the professor game element.
     *
     * @param	pool		A reference to the object that represents the pool that the professor will be dunked into.
     * @param	projectile	A reference to the object that represents the projectile that the professor could collide with.
     * @param	score		A reference to the object that represents your overall score.
     * @param	distance	This effects the speed of movement of the professor.
     */
    public void move(Pool pool, Projectile projectile, Score score, int distance) {
        splashAnimation.update();
        if(this.isDunked()) {
            if(this.getBoundingBox().intersectsLine(pool.getMiddleLine())) {
                splashAnimation.playAnimation();
                this.yPosition+=distance;
            } else if(this.getBoundingBox().intersectsLine(pool.getBottomLine())) {
                this.undunk();
            } else {
                yPosition+=distance;
            }
        } else {
            if(collided(projectile)) {
                score.incrementScore();
                this.dunk();
            }
        }
    }

    /**
     * This method is used to indicate that the professor is no longer dunked and should be returned to his start position .
     */
    public void undunk() {
        this.yPosition = this.startY;
        this.xPosition = this.startX;
        dunked = false;
    }

    /**
     * This method is used to indicate if the projectile has collided with the professor.
     * @param	projectile	A reference to the object that represents the projectile.
     * @return	True if the projectile collided with the professor. Otherwise it returns false.
     *
     */
    public boolean collided(Projectile projectile) {
        return projectile.getBoundingBox().intersects(this.getBoundingBox());
    }
}
