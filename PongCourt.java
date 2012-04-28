import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

@SuppressWarnings("serial")
public class PongCourt extends JComponent {
	private Ball ball;
	//private PowerUp pUp;
	private List<Paddle> paddleList;
	private List<PowerUp> pUpList;
	private Random rand= new Random();
	private int interval = 20; // Milliseconds between updates.
	private Timer timer;       // Each time timer fires we animate one step.
	final int COURTWIDTH  = 600;
	final int COURTHEIGHT = 600;
	private boolean ticker=false; //Used to determine if ball is in powerup mode

	public int score;

	final int BALL_VEL  = 6;  // How fast does the ball move


	public PongCourt() {
		//setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setFocusable(true);



		timer = new Timer(interval, new ActionListener() {
			public void actionPerformed(ActionEvent e) { tick(); }});
		timer.start(); 

		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT)
					ball.setVelocity(-BALL_VEL, ball.velocityY);
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
					ball.setVelocity(BALL_VEL, ball.velocityY);
				else if (e.getKeyCode() == KeyEvent.VK_R)
					reset();
			}

			public void keyReleased(KeyEvent e) {
				ball.setVelocity(0, ball.velocityY);
			}
		});
		// After a PongCourt object is built and installed in a container
		// hierarchy, somebody should invoke reset() to get things started... 
	}

	/** Set the state of the state of the game to its initial value and 
	    prepare the game for keyboard input. */
	public void reset() {
		paddleList= new CopyOnWriteArrayList<Paddle>();
		pUpList=new CopyOnWriteArrayList<PowerUp>();
		ball = new Ball(200, 400, 0, -5);
		int y=600;
		for(int x=0;x<8;x++){
			paddleList.add(new Paddle(COURTWIDTH+525 - rand.nextInt(1000), y));
			
			y-=90;

		}
		requestFocusInWindow();
		score=0;
		Paddle.WIDTH=75;

	}
	//These methods are used when pausing for instructions/resume
	public void stopTimer(){
		timer.stop();
	}
	public void startTimer(){
		timer.start();
	}
	public void restartTimer(){
		timer.restart();
	}
	
	/** Update the game one timestep by moving the ball and the paddle. */
	void tick() {
		//Add powerups every 8000 scores or if score is 2000
		if((score%8000==0 && score>0) || score==2000){
			pUpList.add(new PowerUp(rand.nextInt(600),0, 0, 2));
		}
		
		ball.setBounds(getWidth(), getHeight());
		ball.move();
		//Add intersects/move all paddles
		for(Paddle p:paddleList){
			ball.bounce(p.intersects(ball));
			p.setBounds(getWidth(), getHeight());
			p.move();
		}
		repaint(); // Repaint indirectly calls paintComponent.
		
		//This ticker variable is used to determine if the ball is in powerup mode
		if(ticker==false)
			ball.gravity+=0.3;	
		
		Game.scores.setText("Score " + score);

	}


	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);// Paint background, border
		ball.draw(g);
		
		//Used to draw the powerUps
		for(PowerUp pU:pUpList){
			pU.draw(g);
			pU.y+=4;
			//If if intersects with a ball, enter powerUp mode (ticker=true) and lower gravity so the ball floats up.
			if(ball.intersects(pU)==Intersection.UP ||ball.intersects(pU)==Intersection.DOWN
					|| ball.intersects(pU)==Intersection.RIGHT || ball.intersects(pU)==Intersection.LEFT){
				ticker=true;
				ball.gravity=6;
				pUpList.remove(pU);
				
			}
		}
		//Turn off powerup once the ball gets too high
		if(ball.y<10){
			ticker=false;
			ball.gravity=10;
		}
		//End the game if the ball falls below the window
		if(ball.y> COURTHEIGHT){
			for(Paddle p: paddleList)
				paddleList.remove(p);
			g.setFont(new Font("Verdana", Font.PLAIN, 20));
			g.drawString("Your Final Score: " + score, COURTWIDTH/2-110, 15);
			timer.stop();
		}



		//If the ball is moving up (determined by the inequatlity) move the paddles down to replicate ball moving up
		//This is where the paddle sizes are lowered once the score gets higher too make the game harder.
		//Once the paddles get greater than the court height, a new paddle is added to the top of the window and 
		//it begins moving down. The paddle that fell off is removed from the arraylist.
		//There is a 1 in 8 chance the new paddle has a x velocity and will move during the game.
		if(ball.postMove<ball.holder ){
			for(Paddle p: paddleList){
				p.y+=11;
				score+=1;//Score is incremented as the ball gets higher. The inequality insures the score is only incremented 
						 //when the ball reaches a new high point.
				if(score>5000){
					p.setWidth(55);
				}

				if(score>15000){
					p.setWidth(35);
				}

				if(score>25000){
					p.setWidth(25);
				}
				
				if(score>40000){
					p.setWidth(15);
				}

				if(p.y>COURTHEIGHT-10){
					paddleList.remove(p);
					paddleList.add(new Paddle(rand.nextInt(1150), -10));
					if(rand.nextInt(8)==0){
						if(rand.nextInt(2)==0)
							paddleList.get(paddleList.size()-1).setVelocity(2, 0);
						else
							paddleList.get(paddleList.size()-1).setVelocity(-2, 0);

					}
				}
			}				
		}	

		for(Paddle p: paddleList){
			p.draw(g);
		}

	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(COURTWIDTH, COURTHEIGHT);
	}
}
