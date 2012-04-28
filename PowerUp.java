import java.awt.Color;
import java.awt.Graphics;


public class PowerUp extends GameObject {
	
	final static int DIAMETER = 16;

	//An object to represent a powerUp
	public PowerUp(int x, int y, int velocityX, int velocityY) {
		super(x, y, 0, velocityY, DIAMETER, DIAMETER);
	}

	@Override
	public void accelerate() {

	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillOval(x, y, DIAMETER, DIAMETER);
		g.setColor(Color.BLACK);
		g.drawString("P", x+6, y+11);
		
	}
	
	public void move(){
		super.move();
	}

}
