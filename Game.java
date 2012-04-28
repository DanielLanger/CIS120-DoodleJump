import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class Game implements Runnable {
	
public static JLabel scores;

   public void run() {
      // Top-level frame
      final JFrame frame = new JFrame("JumpUp!");
      frame.setLocation(300,100);
      frame.setBackground(Color.WHITE);

      // Main playing area
      final PongCourt court = new PongCourt();
      frame.add(court, BorderLayout.CENTER);
      
      //instructions
      final JWindow w1 = new JWindow();
	  w1.setSize(500, 200);
	  w1.setBackground(Color.WHITE);
	  w1.setLocation(350, 200);
		  JTextPane textPane = new JTextPane();
		  textPane.setBackground(Color.GREEN);
		  textPane.setText("Welcome to JumpUp! The goal of this game is to jump...up!! " +
		  		"Use the left and right arrow keys to bounce from paddle to paddle and see how high you can get! " +
		  		"Be careful though, the paddles quickly fall down after you jump, so be sure " +
		  		"to JumpUp! Watch out for the moving paddles! Try to get the yellow powerup, it will help you on your journey up! " +
		  		"Press resume game to go back to your game. You will have 3 seconds to prepare!");
		  w1.add(textPane);
  
	  //resume game button
      final JButton resume= new JButton("Resume game");
      resume.addActionListener(new ActionListener(){
    	  public void actionPerformed(ActionEvent e){
     		  w1.setVisible(false);
     		  try {
				Thread.sleep(3000);
 				court.restartTimer();
 				court.requestFocus();
				resume.setVisible(false);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}


    	  }
      });
      
      //Instruction button
      final JPanel panel = new JPanel();
      frame.add(panel, BorderLayout.NORTH);
      final JButton instructions= new JButton("Instructions");
      instructions.addActionListener(new ActionListener(){
    	  public void actionPerformed(ActionEvent e){
    		  court.stopTimer();
     		  w1.setVisible(true);
     		  resume.setVisible(true);
    	  }
      });
      
      
      // Reset button
      final JButton reset = new JButton("New Game");
      reset.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
        	court.startTimer();
            court.reset();
         }
      });
      panel.add(resume);
      resume.setVisible(false);
      panel.add(instructions);
      panel.add(reset);
     
      scores= new JLabel();
      
      scores.setText("Score " + court.score);
      panel.add(scores);
      
      
      // Put the frame on the screen
      frame.pack();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);

      // Start the game running
      court.reset();
      
      
      
      }
   
   /*
    * Get the game started!
    */
   public static void main(String[] args) {
       SwingUtilities.invokeLater(new Game());
       
   }

}
