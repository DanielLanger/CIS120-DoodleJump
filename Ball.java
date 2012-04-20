import java.awt.*;

public class Ball extends GameObject {
	final static int DIAMETER = 21;
	public double gravity =0.8;
	public static double holder;
	public static double postMove=0;
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
	public boolean bounce(Intersection i) {
		switch (i) {
		case NONE: break;
		case UP: velocityY = -4; gravity=0.8; return true; 
		}
		return false;
	}
	
	public void move(){
		holder=this.y;
		super.move();	
		this.y+=this.velocityY+gravity;
		postMove=this.y;
	}

	public void draw(Graphics g) {
		g.fillOval(x, y, DIAMETER, DIAMETER);
		
		
	}
	
}
