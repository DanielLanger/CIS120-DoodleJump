import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class PongCourt extends JComponent {
	private Ball ball;
	private Paddle paddle;
	private Paddle paddle2;
	private Paddle paddle3;

	private int interval = 35; // Milliseconds between updates.
	private Timer timer;       // Each time timer fires we animate one step.

	final int COURTWIDTH  = 300;
	final int COURTHEIGHT = 500;
	int score;
	

	
	final int BALL_VEL  = 5;  // How fast does the paddle move
	

	public PongCourt() {
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
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
		ball = new Ball(100, 0, 0, 2);
		paddle = new Paddle(COURTWIDTH+100, COURTHEIGHT);
		paddle2= new Paddle(COURTWIDTH, 300);
		paddle3= new Paddle(COURTWIDTH-100, 100);
		requestFocusInWindow();
	}

   /** Update the game one timestep by moving the ball and the paddle. */
	void tick() {
		ball.setBounds(getWidth(), getHeight());
		ball.move();
		paddle.setBounds(getWidth(), getHeight());
		paddle2.setBounds(getWidth(), getHeight());
		paddle3.setBounds(getWidth(), getHeight());
		paddle.move();
		paddle2.move();
		paddle3.move();
		ball.bounce(paddle.intersects(ball));
		ball.bounce(paddle2.intersects(ball));
		ball.bounce(paddle3.intersects(ball));
		repaint(); // Repaint indirectly calls paintComponent.
		ball.gravity+=0.2;	
		
	}

   @Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g); // Paint background, border
		ball.draw(g);
		paddle.draw(g);
		paddle2.draw(g);
		paddle3.draw(g);

	}

   @Override
	public Dimension getPreferredSize() {
		return new Dimension(COURTWIDTH, COURTHEIGHT);
    }
}
