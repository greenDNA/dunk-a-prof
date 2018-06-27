import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;


public class Paused {
	private Font font;
	private FontMetrics metrics;
	private int panelWidth;
	private int panelHeight;
	public Paused(Font font, FontMetrics metrics, int panelWidth, int panelHeight) {
		this.font = font;
		this.metrics = metrics;
		this.panelWidth = panelWidth;
		this.panelHeight = panelHeight;
		
	}
	public void draw(Graphics graphics) {
		graphics.setColor(Color.yellow);
		graphics.setFont(font);
		graphics.drawString( "Paused" , (this.panelWidth - metrics.stringWidth("Paused"))/2 , (this.panelHeight - metrics.getHeight())/2 );
	}
}
