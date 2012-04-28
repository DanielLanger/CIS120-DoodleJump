import java.awt.*;

public class Paddle extends GameObject {
	final static int HEIGHT = 15;
	static int WIDTH = 75;

	public Paddle(int courtwidth, int courtheight) {
		super((courtwidth - WIDTH) / 2, courtheight - HEIGHT - 20, 0, 0, WIDTH, HEIGHT);
		
	}
	//switch direction if it's a moving paddle
	public void accelerate() {
		if(x<0){
			velocityX=2;
		}
		if(x> rightBound){
			velocityX=-2;
		}
		
	}

	public void draw(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(x, y, WIDTH, HEIGHT);
	}
	
	
	public void setWidth(int x){
		this.WIDTH=x;
	}
	
	public void move(){
		super.move();	
	}
	
	
}
