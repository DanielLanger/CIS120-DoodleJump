import java.awt.*;
import javax.swing.*;

public class Ball extends GameObject {
	final static int DIAMETER = 21;
	public double gravity =0.8;
	private int max;
	
	public Ball(int x, int y, int velocityX, int velocityY) {
		super(x, y, 0, velocityY, DIAMETER, DIAMETER);

	}
	
	public int getMax(){
		return max;
	}

	public void accelerate() {
		if (x < 0)
			x=rightBound;
		else if (x > rightBound)
			x=0;
	}

	// Bounce the ball, if necessary
	public void bounce(Intersection i) {
		switch (i) {
		case NONE: break;
		case UP: velocityY = -5; gravity=0.8; break;
		}
	}
	
	public void move(){
		super.move();	
		this.y+=this.velocityY+gravity;
	}

	public void draw(Graphics g) {
		g.fillOval(x, y, DIAMETER, DIAMETER);
		
		int x=500-y;
		if(x<0)
			x=0;
		if(x>getMax()){
			g.drawString("Score " + (x)  , 225, 15);
			max=x;
		}
		else
			g.drawString("Score " + getMax(), 225, 15);
	}
	
}
