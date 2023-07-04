import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Submission class creates a JPanel and places it in
 * existing JFrame. Allows quiz taker to submit after checking agreement.
 */
public class Submission implements ActionListener {
  /**
   * new panel that will be added to frame
   */
  private final JPanel panel;
  /**
   * existing JFrame
   */
  private final JFrame frame;
  /**
   * counter var to keep track of number of quiz score
   */
  private final int numCorrect;
  /**
   * String that keeps track of submission summary
   */
  private String results;
  /**
   * Agreement check box. must check this to submit
   */
  private final JRadioButton check;
  /**
   * button that will submit quiz
   */
  private final JButton submit;

  /**
   * Will create a new submission panel
   * and add it to the existing frame
   * @param frame existing JFrame
   * @param numCorrect current quiz score
   * @param results current quiz summary
   */
  public Submission(JFrame frame, int numCorrect, String results) {
    this.frame = frame;
    this.numCorrect = numCorrect;
    this.results = results;

    panel = new JPanel();
    panel.setLayout(null);
    panel.setBackground(Color.ORANGE);

    JLabel heading = new JLabel("STUDENT HONESTY AGREEMENT");
    heading.setBounds(100, 20, 700, 60);
    heading.setFont(new Font("Oswald", Font.BOLD, 30));
    heading.setForeground(new Color(30, 144, 254));
    panel.add(heading);

    JLabel agreement = new JLabel();
    agreement.setBounds(160, 60, 700, 350);
    agreement.setFont(new Font("Arial", Font.PLAIN, 20));
    agreement.setText(
        "<html>"+
            "In order to submit your test you must" + "<br><br>" +
            "Check the box to agree to the statement below:" + "<br><br>" +
            "I did not cheat or help anyone cheat." + "<br><br>" +
            "I will not share answers" + "<br><br>" +
            "Eli made an awesome Swing GUI :)" + "<br><br>" +
            "<html>"
    );
    panel.add(agreement);

    ButtonGroup bg = new ButtonGroup();

    check = new JRadioButton("I AGREE");
    check.setBounds(150, 350, 200, 80);
    check.setBackground(Color.WHITE);
    check.setFont(new Font("Dialog", Font.PLAIN, 20));
    panel.add(check);
    check.addActionListener(this);
    bg.add(check);

    submit = new JButton("SUBMIT TEST");
    submit.setBounds(350, 350, 200, 80);
    submit.setBackground(Color.WHITE);
    submit.setFont(new Font("Dialog", Font.PLAIN, 10));
    submit.addActionListener(this);

    JProgressBar progressBar = new JProgressBar();
    progressBar.setBounds(120,10,500,10);
    panel.add(progressBar);
    progressBar.setBackground(Color.white);
    progressBar.setForeground(Color.BLUE);
    progressBar.setValue(95);

    frame.getContentPane().add(panel);
    frame.setVisible(true);
  }

  /**
   * shows submit button when agreement is checked
   * and moves to end of quiz when submitted
   * @param e button pressed
   */
  @Override
  public void actionPerformed(ActionEvent e) {

    if (e.getSource() == check){
      panel.add(submit);
      frame.repaint();

    }
    if (e.getSource() == submit){
      frame.getContentPane().remove(panel);
      new EndOfQuiz(frame, numCorrect, results);
    }

  }
}
