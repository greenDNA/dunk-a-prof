import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Dean {

    /**
     * This is used to store the dean's image
     */
    private Image image, scoreSplash;;

    //returns reference to the scoreSplash Image
    public Image getScoreSplash(){
        return scoreSplash;
    }

    //returns starting Y coordinate
    public int getStartY() {
        return startY;
    }
    //returns starting X coordinate
    public int getStartX() {
        return startX;
    }

    /**

     * These are used to store the initial x and y coordinates of the dean.
     */
    private int startX;

    private int startY;

    /**
     * These are used to store the current x and y coordinates of the dean.
     */
    private int xPosition, yPosition;

    /**
     * This is used to store and run the splash animation.
     */
    private Animation splashAnimation;

    /**
     * This is used to indicate if the dean is dunked or not.
     */
    boolean dunked = false;

    /**
     * This is used to play the dean's scream sound file.
     */
    MediaPlayer scream;

    /**
     * Constructs a new object which represents the dean on the JPanel.
     *
     * @param	fileName	-	The filename of the dean's image
     * @param	screamFile	-	The filename of the scream that will be played when the dean is dunked.
     * @param	xPosition	-	The dean's initial x coordinate on the JPanel
     * @param	yPosition	-	The dean's initial y coordinate on the JPanel
     */
    public Dean(String fileName, String screamFile, int xPosition, int yPosition) {
        try {
            image = ( ImageIO.read(new File(fileName)) ).getScaledInstance(78, 128, Image.SCALE_DEFAULT); // Read in the file and scale it
            // Reads in image containing the "splash points"
            scoreSplash = ( ImageIO.read(new File("images/score+1.png")).getScaledInstance(80, 120, Image.SCALE_DEFAULT) );
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
     * This method draws the dean's image, as well as, any animations associated with him, to the JPanel.
     *
     * @param  graphics	-	A reference to the JPanel buffer
     */
    public void draw(Graphics graphics) {

        // Draw the person
        graphics.drawImage(image, this.xPosition, this.yPosition, null);
        graphics.drawImage(splashAnimation.getImage(), 274, 146, splashAnimation.getImage().getWidth(null), splashAnimation.getImage().getHeight(null), null);

    }  // end of draw()

    /**
     * This method returns the dean's x coordinate on the JPanel.
     *
     * @return	The x coordinate of the dean on the JPanel.
     */
    public int getXPosition() {
        return this.xPosition;
    }

    /**
     * This method returns the dean's y coordinate on the JPanel.
     *
     * @return	The x coordinate of the dean on the JPanel.
     */
    public int getYPosition() {
        return this.yPosition;
    }

    /**
     * This method returns the dean's y coordinate on the JPanel.
     *
     * @return	The y coordinate of the dean on the JPanel.
     */
    public Rectangle getBoundingBox() {
        return new Rectangle(this.xPosition, this.yPosition, this.image.getWidth(null), this.image.getHeight(null));
    }


    /**
     * This method is used to indicate that the dean has been hit and will now be in a state of being dunked.
     */
    public void dunk() {
        this.scream.play();
        dunked = true;

    }

    /**
     * This method is used to check if the dean is in a state of being dunked.
     *
     * @return      True if the dean is dunked, otherwise it returns false.
     */
    public boolean isDunked() {
        return dunked;
    }

    /**
     * This method is used to modify the state of the dean game element.
     *
     * @param	pool		A reference to the object that represents the pool that the dean will be dunked into.
     * @param	projectile	A reference to the object that represents the projectile that the dean could collide with.
     * @param	score		A reference to the object that represents your overall score.
     * @param	distance	This effects the speed of movement of the dean.
     */
    public void move(Pool pool, Projectile projectile, Score score, int distance) {
        splashAnimation.update();
        if(this.isDunked()) {
            if(this.getBoundingBox().intersectsLine(pool.getMiddleLine())) {
                splashAnimation.playAnimation();
                yPosition+=distance;
            } else if(this.getBoundingBox().intersectsLine(pool.getBottomLine())) {
                this.undunk();
            } else {
                yPosition+=distance;
            }
        } else {
            if(this.collided(projectile)) {
                this.dunk();
                score.incrementScore();
            }
        }
    }

    /**
     * This method is used to indicate that the dean is no longer dunked and should be returned to his start position .
     */
    public void undunk() {
        this.xPosition = this.startX;
        this.yPosition = this.startY;
        dunked = false;

    }

    /**
     * This method is used to indicate that the dean is no longer dunked and should be returned to his start position.
     *
     * @param	projectile	-	An object which is a reference to the object which represents the projectile.
     * @return	True if the projectile has collided with the dean. Otherwise, it returns false.
     */
    public boolean collided(Projectile projectile) {
        return projectile.getBoundingBox().intersects(this.getBoundingBox());
    }
}
