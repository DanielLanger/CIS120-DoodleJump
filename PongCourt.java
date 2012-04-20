import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

@SuppressWarnings("serial")
public class PongCourt extends JComponent {
	private Ball ball;
	private List<Paddle> paddleList;
	private Random rand= new Random();
	private int interval = 20; // Milliseconds between updates.
	private Timer timer;       // Each time timer fires we animate one step.

	final int COURTWIDTH  = 600;
	final int COURTHEIGHT = 600;

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
		ball = new Ball(200, 400, 0, -5);
		int y=600;
		for(int x=0;x<8;x++){
			paddleList.add(new Paddle(COURTWIDTH+525 - rand.nextInt(1000), y));
			y-=90;

		}
		requestFocusInWindow();
		score=0;

	}

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
		ball.setBounds(getWidth(), getHeight());
		ball.move();
		for(Paddle p:paddleList){
			ball.bounce(p.intersects(ball));
		}
		repaint(); // Repaint indirectly calls paintComponent.
		ball.gravity+=0.3;	
		Game.scores.setText("Score " + score);	

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);// Paint background, border
		ball.draw(g);
		if(ball.y> COURTHEIGHT){
			g.setFont(new Font("Verdana", Font.PLAIN, 20));
			g.drawString("Your Final Score: " + score, COURTWIDTH/2-110, 15);
			timer.stop();
		}

		if(ball.postMove<ball.holder ){
			for(Paddle p: paddleList){
				
				p.y+=11;
				score+=1;

				if(p.y>COURTHEIGHT-10){
					paddleList.remove(p);
					paddleList.add(new Paddle(rand.nextInt(1150), -10));
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
