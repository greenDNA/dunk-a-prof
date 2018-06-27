import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Trustee {

    /**
     * This is used to store the trustee's animation.
     */
    Animation trusteeAnimation;

    /**
     * This is used to store and run the splash animation.
     */
    Animation splashAnimation;

    /**
     * These are used to store the initial x and y coordinates of the trustee.
     */
    private int startX, startY;

    /**
     * These are used to store the current x and y coordinates of the trustee.
     */
    private int xPosition, yPosition;

    /**
     * This is used to indicate if the trustee is dunked or not.
     */
    private boolean dunked = false;

    /**
     * This is used to play the trustee's scream sound file.
     */
    MediaPlayer scream;
    
    Image undunkImage;

    /**
     *
     */
    public Trustee(String fileName, String screamFile, int xPosition, int yPosition) {
        trusteeAnimation = new Animation(fileName, 28, 4);
        splashAnimation = new Animation("images/Smoke/cloud-", 9, 4);
        try {
			undunkImage = ( ImageIO.read(new File("images/skeleton.png")) ).getScaledInstance(69, 120, Image.SCALE_DEFAULT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.startX = xPosition;
        this.startY = yPosition;
        this.scream = new MediaPlayer(screamFile);
        trusteeAnimation.playAnimation();
        trusteeAnimation.loopAnimation();

    }

    /**
     * This method draws the trustee's image, as well as, any animations associated with him, to the JPanel.
     *
     * @param  graphics	-	A reference to the JPanel buffer
     */
    public void draw(Graphics graphics) {

        // Draw the person
        graphics.drawImage(trusteeAnimation.getImage(), this.xPosition, this.yPosition, null);
        graphics.drawImage(splashAnimation.getImage(), 557, 143, splashAnimation.getImage().getWidth(null), splashAnimation.getImage().getHeight(null), null);

    }  // end of draw()

    /**
     *
     */
    public int getXPosition() {
        return this.xPosition;
    }

    /**
     *
     */
    public int getYPosition() {
        return this.yPosition;
    }

    /**
     * This is used to return the Rectangle representing the bounding box that surrounds the image.
     */
    public Rectangle getBoundingBox() {
        return new Rectangle(this.xPosition, this.yPosition, trusteeAnimation.getImage().getWidth(null),trusteeAnimation.getImage().getHeight(null) );
    }

    public void dunk() {
        this.scream.play();
        dunked = true;

    }

    /**
     *
     */
    public boolean isDunked() {
        return dunked;
    }

    /**
     *
     */
    public void move(Pool pool, Projectile projectile, Score score, int distance) {
        //Let the animations know that they can move on to the next frame
        trusteeAnimation.update();
        splashAnimation.update();
        if(this.isDunked()) {
            if(this.getBoundingBox().intersectsLine(pool.getMiddleLine())) {
                splashAnimation.playAnimation();
                this.yPosition+=distance;
            } else if (this.getBoundingBox().intersectsLine(pool.getBottomLine())) {
                this.undunk();
            } else {
                this.yPosition+=distance;
            }
        } else {
            if(collided(projectile)) {
                score.incrementScore();
                this.dunk();
            }
        }
    }

    /**
     *
     */
    public void undunk() {
        this.xPosition = this.startX;
        this.yPosition = this.startY;
        dunked = false;

    }

    /**
     * This is used to test if the projectile collided with the trustee.
     * 
     * @param projectile  - A reference to the object which represents the projectile
     * @return	True if the projectile collided with the trustee, otherwise it returns false.
     */
    public boolean collided(Projectile projectile) {
        return projectile.getBoundingBox().intersects(this.getBoundingBox());
    }

}
