import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Creates new panel which shows
 * quiz score, summary and close quiz button
 */
public class EndOfQuiz implements ActionListener {
  /**
   * button to end quiz application
   */
  private final JButton closeTest;
  /**
   * existing frame
   */
  private final JFrame frame;

  /**
   * Creates a new panel with quiz summary in it
   * and adds to frame
   * @param frame existing frame
   * @param numCorrect final score
   * @param results final summary
   */
  public EndOfQuiz(JFrame frame, int numCorrect, String results) {
    this.frame = frame;

    JPanel panel = new JPanel();
    panel.setLayout(null);
    if (numCorrect >= 6){
      panel.setBackground(Color.GREEN);
    }
    else if (numCorrect >= 4){
      panel.setBackground(Color.PINK);
    }
    else {
      panel.setBackground(Color.RED);
    }


    JLabel heading = new JLabel("QUIZ RESULTS");
    heading.setBounds(230, 20, 700, 60);
    heading.setFont(new Font("Oswald", Font.BOLD, 40));
    heading.setForeground(Color.WHITE);
    panel.add(heading);

    JLabel score = new JLabel(numCorrect+"/9");
    score.setBounds(290, 60, 700, 100);
    score.setFont(new Font("Oswald", Font.BOLD, 80));
    score.setForeground(Color.WHITE);
    panel.add(score);

    JLabel unAnswered = new JLabel("<html>"+results+"<html>");
    unAnswered.setBounds(170, 170, 500, 300);
    unAnswered.setFont(new Font("Oswald", Font.BOLD, 25));
    unAnswered.setForeground(Color.WHITE);
    panel.add(unAnswered);


    closeTest = new JButton("Close Test");
    closeTest.setBounds(275, 420, 200, 80);
    closeTest.setBackground(Color.WHITE);
    closeTest.setForeground(Color.BLACK);
    closeTest.addActionListener(this);
    panel.add(closeTest);

    this.frame.getContentPane().add(panel);
    this.frame.setVisible(true);
  }

  /**
   * when close test is pressed
   * close test and ends process
   * @param e
   */
  @Override
  public void actionPerformed(ActionEvent e) {
    if(e.getSource() == closeTest){
      frame.setVisible(false);
      frame.dispose();
    }
  }
}
