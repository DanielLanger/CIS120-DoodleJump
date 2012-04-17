import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Game implements Runnable {
   public void run() {
      // Top-level frame
      final JFrame frame = new JFrame("Pong");
      frame.setLocation(300, 300);

      // Main playing area
      final PongCourt court = new PongCourt();
      frame.add(court, BorderLayout.CENTER);
      
      
      // Reset button
      final JPanel panel = new JPanel();
      frame.add(panel, BorderLayout.NORTH);
      final JButton reset = new JButton("Reset");
      reset.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            court.reset();
         }
      });
      panel.add(reset);
    

      
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
