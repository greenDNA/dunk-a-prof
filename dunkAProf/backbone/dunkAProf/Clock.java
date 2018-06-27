import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;


public class Clock  {

    /**
     * This is used to store the game's time limit
     */
    private int timeLimit;

    /**
     * This is used to store the font used for the clock.
     */
    private Font font;

    /**
     * This is used to schedule a decrement of the time every second
     */
    private Timer timer;

    /**
     * This is used to store the x coordinate of the clock.
     */
    private int xPosition;

    /**
     * This is used to store the y coordinate of the clock.
     */
    private int yPosition;

    /**
     * This is the thread called by timer every second to decrement the time.
     */
    Watch watch  = new Watch();
    
    private int timeInMilliseconds;

    /**
     * This constructs a clock object which represents
     *
     * @param  graphics	-	A reference to the JPanel buffer
     */
    public Clock(String fileName, int xPosition, int yPosition, int timeLimit) {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File(fileName));
            font = font.deriveFont(36f);
        } catch (FontFormatException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.timeInMilliseconds = timeLimit * 1000;
        timer = new Timer();
        timer.schedule(watch, 0, 1);

    }

    /**
     * This method draws the clock to the JPanel
     *
     * @param  graphics	-	A reference to the JPanel buffer
     */
    public void draw(Graphics graphics) {
        graphics.setFont(font);
        graphics.drawString("Time \t\t" + (int) Math.ceil(timeInMilliseconds/1000) + "", this.xPosition, this.yPosition);
    }

    /**
     * This method returns the game's time limit.
     *
     * @return  timeLimit	-	The game's time limit
     */
    public int getTimeLimit() {
        return timeLimit;
    }

    /**
     * This method stops the clock.
     */
    public void stopClock() {
        watch.cancel();
    }

    /**
     * This method allows you to resume the clock from where it left of when you stopped it.
     */
    public void resumeClock() {
        this.watch = new Watch();
        this.timer = new Timer();
        this.timer.schedule(watch, 0, 1);
    }
    
    /**
     * This is used to store the player's time limit has run out
     */
    public boolean outOfTime() {
        return timeInMilliseconds <= 0;
    }
    
    class Watch extends TimerTask {
        public void run() {
        	timeInMilliseconds--;
        }
    }
}
