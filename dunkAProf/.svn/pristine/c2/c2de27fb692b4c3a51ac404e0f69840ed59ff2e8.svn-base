import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable, KeyListener,  ActionListener {

    /**
    *	This is used for serialization purposes.
    */
    private static final long serialVersionUID = 1L;

    /**
    *	This is the used to store the width of the JPanel.
    */
    private static final int panelWidth = 800;

    /**
     *	This is the used to store the height of the JPanel.
     */
    private static final int panelHeight = 600;

    // This is for the animation
    private Thread animator;

    /**
     *	This is used to stop the game animation
     */
    private volatile boolean running = false;

    /**
     *
     */
    private boolean isPaused = false;

    /**
     *	This is used to indicate that the game is over.
     */
    private volatile boolean gameOver = false;


    /**
     * This is used to store the font used for text in the game.
     */
    private Font font;


    /**
     * This encapsulates information about the rendering of the font used for text in the game. 
     */
    private FontMetrics metrics;

    /**
     *
     */
    Background background;

    /**
     *
     */
    private Professor professor;

    /**
     *
     */
    private Dean dean;

    /**
     *
     */
    private Trustee trustee;

    /**
     *
     */
    private Slider slider;

    /**
     *
     */
    private Pool [] pool = new Pool [3];

    /**
     *
     */
    private Projectile projectile;

    /**
     *
     */
    private Clock clock;

    /**
     *
     */
    private Score score;

    /**
     *	This is used to store a reference to the JFrame
     */
    DunkAProf window;
    
    /**
     * This is used to store the image and location of the paused graphic.
     */
    Paused paused;
    
    // These are the global variables used for off-screen rendering
    private Graphics screenGraphics;
    private Image screenImage = null;
    
    /**
     * The JPanel's constructor. It initializes all the in-game elements.
     * 
     * @param window	A reference to the JFrame
     */
    public GamePanel(DunkAProf window) {

        // Create the background image.
        background = new Background("images/background.png",panelWidth, panelHeight);

        // Set the size of the JPanel.
        setPreferredSize( new Dimension(panelWidth, panelHeight) );

        setFocusable(true);

        // The JPanel now has focus, so it can receive key events.
        requestFocus();


        // Create game components
        professor = new Professor("images/professor.png","sounds/professorScream.wav",80, 0);
        dean = new Dean("images/dean.png","sounds/deanScream.wav",361, 0);
        trustee = new Trustee("images/Trustee/trustee","sounds/trusteeScream.wav",666, 0);
        pool[0] = new Pool("images/pool.png", 50, 196);
        pool[1] = new Pool("images/pool.png", 336, 196);
        pool[2] = new Pool("images/acidPool.png", 622, 196);
        slider = new Slider("images/slider.png", 0, 517, panelWidth);
        projectile = new Projectile("images/projectile.png", 0, 500);
        clock = new Clock("fonts/AGENCYR.TTF", 148, 34, 60);
        score = new Score(new Color(255, 247, 153), font, 20, 580);
        
       // Load the font
        try {
        	font = ( Font.createFont(Font.TRUETYPE_FONT, new File("fonts/AGENCYR.TTF")) ).deriveFont(36f);
            metrics = this.getFontMetrics(font);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        paused = new Paused(font, metrics, panelWidth, panelHeight);
        
        //The JPanel is now able to receive key events
        addKeyListener(this);
        
        this.window = window;
    }  	 											// end of GamePanel()
    
    /**
     * 
     */
    public void addNotify() {						// Wait for the JPanel to be added to the JFrame before starting.
        super.addNotify();							// creates the peer
        startGame();								// Start the thread
    }

    /**
     * This starts the JFrame thread if it isn't running.
     */
    private void startGame() { 					    // Initialize and start the thread
        if (animator == null || !running) {
            animator = new Thread(this);
            animator.start();
        }
    }												// end of startGame()
    
    /**
     * This is called by the user to stop execution of JFrame.
     */
    public void stopGame() {						
        running = false;
    }

    /**
     *  This repeatedly updates the in-game logic, renders the game to an off-screen buffer, paints the off screen buffer to the screen,
     *  and tells the thread to sleep a little.
     */
    public void run() {							    // Repeatedly update, render, sleep
        running = true;
        while(running) {
            gameUpdate();							// The game state is updated.
            gameRender();							// Render the game state to a buffer.
            paintScreen();							// Paint with the buffer.
            try {
                Thread.sleep(20);					// Sleep a bit
            } catch(InterruptedException ex) {}
        }
        if(window.isClosed()) {
            System.exit(0);							// The enclosing JFrame exits
        }
    }												// end of run()

    /**
    * Draws the game logic to the off-screen image
    */
    private void gameRender() {

        // Draw the current frame to an image buffer
        if (screenImage == null) {
            screenImage = createImage(panelWidth, panelHeight);
            if (screenImage == null) {
                System.out.println("The ScreenImage is null");
                return;
            } else {
                screenGraphics = screenImage.getGraphics();
            }
        }
        if(!isPaused) {
            // Clear the background
            background.draw(screenGraphics);

            // Draw the professor, dean, trustee, slider, pools, and projectile to the image buffer
            professor.draw(screenGraphics);
            dean.draw(screenGraphics);
            trustee.draw(screenGraphics);
            slider.draw(screenGraphics);
            pool[0].draw(screenGraphics);
            pool[1].draw(screenGraphics);
            pool[2].draw(screenGraphics);
            projectile.draw(screenGraphics);
            clock.draw(screenGraphics);
            score.draw(screenGraphics);
        } else { 						
        	
        	// Draw the game paused message to the screen
        	paused.draw(screenGraphics);
        }

        // Used for finding out the location of objects on screen during development
        //System.out.println("" + this.getMousePosition().getX() + " " + this.getMousePosition().getY() + "");


        if (gameOver) {
            this.gameOverMessage(screenGraphics);
            this.stopGame();
        }
    }

    /**
    * Updates the game logic.
    */
    private void gameUpdate() {
        if (!gameOver && !isPaused) {
            slider.move(5);

            // Align the center of the platform with the center of the projectile.
            projectile.move((slider.getxPosition() + (slider.getWidth()/2)) - (projectile.getWidth()/2),
                            (slider.getyPosition() + (slider.getHeight()/2)) - (projectile.getHeight()/2));

            // For each person, check if the projectile or pool has collided with that person. If it has, move them (dunk or undunk them).
            professor.move(pool[0], projectile,score, 15);
            dean.move(pool[1], projectile,score, 15);
            trustee.move(pool[2], projectile,score, 15);

        }
        
        // If the player has run out of time, then the game is over.
        if(clock.outOfTime()) {
            gameOver = true;
        }

    }
    
    /**
    *    Prints the game over message to the screen
    */
    private void gameOverMessage (Graphics graphics) {			// Place the game over message in the center
        String message = "Game Over. Your Score: " + score.getScore() + " Press enter to continue";
        int x = (panelWidth - metrics.stringWidth(message))/2;	// This is the code to calculate x and y values of the game over message.
        int y = (panelHeight - metrics.getHeight())/2;
        graphics.setColor(Color.yellow);
        graphics.setFont(font);
        graphics.drawString(message, x, y);
    } 															// end of gameOverMessage()

    /**
     *  Use active rendering to put the buffered image on-screen.
     */
    private void paintScreen() {
        Graphics graphics;
        try {
            graphics = this.getGraphics();
            if ( graphics != null && screenImage != null ) {
                graphics.drawImage(screenImage, 0, 0, null);
            }
            graphics.dispose();
        } catch (Exception e) {
            System.out.println("Graphics context error: " + e);
        }
    } // end of paintScreen()

    @Override
    public void keyPressed(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.VK_ENTER && gameOver) {
        	System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
    	switch(event.getKeyCode()) {
    	case KeyEvent.VK_SPACE:
    		projectile.fire();
    		break;
    	case KeyEvent.VK_P:
    		if(isPaused) {
                this.resumeGame();
            } else {
                this.pauseGame();
            } break;
    	case  KeyEvent.VK_LEFT:
    		projectile.setDirection(Direction.LEFT);
    		break;
    	case KeyEvent.VK_RIGHT:
    		projectile.setDirection(Direction.RIGHT);
    		break;
    	case KeyEvent.VK_UP:
    		projectile.setDirection(Direction.FORWARD);
    		break;
    	default:
    		break;
    	}
    }

    @Override
    public void keyTyped(KeyEvent event) {

    }

    /**
     * This is used to pause the game.
     */
    public void pauseGame() {
        isPaused = true;
        // If the game is paused, then the clock should be stopped
        clock.stopClock();

       
    }

    /**
     * This resumes the game when it is paused.
     */
    public void resumeGame() {
        isPaused = false;
        clock.resumeClock();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }

} 														// end of GamePanel class

