import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Slider {

    /**
     *
     */
    private BufferedImage image;

    /**
     *
     */
    private int xPosition;

    /**
     *
     */
    private int yPosition;

    /**
     *
     */
    private int panelWidth;

    /**
     *
     */
    private Direction direction = Direction.RIGHT;

    /**
     *
     */
    private Rectangle boundingBox;

    /**
     *
     */
    boolean moving = true;

    /**
     *
     */
    public Slider(String fileName, int xPosition, int yPosition, int panelWidth) {
        try {
            image = ImageIO.read(new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.panelWidth = panelWidth;
        boundingBox = new Rectangle(xPosition, yPosition, image.getWidth(), image.getHeight());
    }

    /**
     *
     */
    public void draw(Graphics graphics) {

        // Draw the slider
        graphics.drawImage(image, xPosition, yPosition, null);
        boundingBox.setLocation(xPosition, yPosition);

    }  // end of draw()

    /**
     *
     */
    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     *
     */
    public int getxPosition() {
        return this.xPosition;
    }

    /**
     *
     */
    public int getyPosition() {
        return this.yPosition;
    }

    /**
     *
     */
    public void move(int distance) {
        if(moving) {
            switch(this.direction) {
            case RIGHT:
                xPosition += 5;
                boundingBox.setLocation(xPosition, yPosition);
                if(xPosition >  panelWidth - image.getWidth()) {
                    this.direction = Direction.LEFT;
                }
                break;
            case LEFT:
                xPosition -= distance;
                boundingBox.setLocation(xPosition, yPosition);
                if(xPosition < 0) {
                    this.direction = Direction.RIGHT;
                }
			case FORWARD:
				break;
			default:
				break;
            }
        }

    }

    /**
     *
     */
    public void stopMovement() {
        moving = false;
    }

    /**
     *
     */
    public void startMovement() {
        moving = true;
    }

    /**
     *
     */
    public boolean isMoving() {
        return moving;
    }

    /**
     *
     */
    public int getWidth() {
        return image.getWidth();
    }

    /**
     *
     */
    public int getHeight() {
        return image.getHeight();
    }

    /**
     *
     */
    public int getCenterX() {
        return (int) boundingBox.getCenterX();
    }

    /**
     *
     */
    public int getCenterY() {
        return (int) boundingBox.getCenterY();
    }
}
