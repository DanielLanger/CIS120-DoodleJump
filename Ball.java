import java.awt.*;

public class Ball extends GameObject {
	final static int DIAMETER = 21;
	public double gravity =0.8;
	
	public Ball(int x, int y, int velocityX, int velocityY) {
		super(x, y, 0, velocityY, DIAMETER, DIAMETER);
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
		g.drawString("Score " + y , 225, 15);
	}
	
}
