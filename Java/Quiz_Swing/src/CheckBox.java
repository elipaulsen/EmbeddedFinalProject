import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Creates a new checkbox question
 * using JCheckBox
 */
public class CheckBox implements ActionListener {
    /**
     * existing frame
     */
    private final JFrame frame;
    /**
     * new panel that will be added to frame
     */
    private final JPanel panel;
    /**
     * button to move to next question
     */
    private final JButton next;
    /**
     * Checkboxes with options
     */
    private final JCheckBox correct2, correct1, option2, option3;
    /**
     * current quiz score
     */
    private final int numCorrect;
    /**
     * current quiz summary
     */
    private final String results;

    /**
     * Creates a new panel with checkbox question
     * and adds it to existing frame
     * @param frame existing frame
     * @param numCorrect current score
     * @param results current quiz summary
     */
    public CheckBox(JFrame frame, int numCorrect, String results) {
        this.numCorrect = numCorrect;
        this.results = results;
        this.frame = frame;

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.ORANGE);

        JLabel heading = new JLabel("Question 2: Checkbox");
        heading.setBounds(130, 20, 700, 60);
        heading.setFont(new Font("Oswald", Font.BOLD, 40));
        heading.setForeground(new Color(30, 144, 254));
        panel.add(heading);

        JLabel question = new JLabel("Which animals are mammals?");
        question.setBounds(175, 100, 700, 60);
        question.setFont(new Font("Dialog", Font.BOLD, 25));
        question.setForeground(new Color(30, 144, 254));
        panel.add(question);

        correct1 = new JCheckBox("Human");
        correct1.setBounds(295, 150, 200, 80);
        correct1.setBackground(Color.WHITE);
        correct1.setFont(new Font("Dialog", Font.PLAIN, 20));
        panel.add(correct1);

        option2 = new JCheckBox("Snake");
        option2.setBounds(295, 200, 200, 80);
        option2.setBackground(Color.WHITE);
        option2.setFont(new Font("Dialog", Font.PLAIN, 20));
        panel.add(option2);

        option3 = new JCheckBox("Jellyfish");
        option3.setBounds(295, 250, 200, 80);
        option3.setBackground(Color.WHITE);
        option3.setFont(new Font("Dialog", Font.PLAIN, 20));
        panel.add(option3);

        correct2 = new JCheckBox("Cow");
        correct2.setBounds(295, 300, 200, 80);
        correct2.setBackground(Color.WHITE);
        correct2.setFont(new Font("Dialog", Font.PLAIN, 20));
        panel.add(correct2);

        next = new JButton("Next Question");
        next.setBounds(275, 400, 200, 80);
        next.setBackground(Color.WHITE);
        next.setForeground(Color.BLACK);
        next.addActionListener(this);
        panel.add(next);

        JProgressBar progressBar = new JProgressBar();
        progressBar.setBounds(120,10,500,10);
        panel.add(progressBar);
        progressBar.setBackground(Color.white);
        progressBar.setForeground(Color.BLUE);
        progressBar.setValue(25);

        this.frame.getContentPane().add(panel);
        this.frame.setVisible(true);
    }


    /**
     * Grades question and moves to next question when next is clicked
     * @param e button is pressed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == next){
            frame.getContentPane().remove(panel);
            int score = 0;
            if (!option2.isSelected() && !option3.isSelected() && !correct1.isSelected() && !correct2.isSelected()){
                new DropDown(frame,numCorrect, results +"2.     Checkbox: UNANSWERED<br><br>");
            }
            else{
                if (correct1.isSelected()){
                    score++;
                }
                if (correct2.isSelected()){
                    score++;
                }
                if (option2.isSelected()){
                    score--;
                }
                if (option3.isSelected()){
                    score--;
                }
                if (score < 0) {score = 0;}
                if (score == 2){
                    new DropDown(frame,numCorrect+score, results +"2.   Checkbox: CORRECT<br><br>");


                }
                else{
                    new DropDown(frame,numCorrect+score, results +"2.   Checkbox: PARTIAL POINTS<br><br>");
                }
            }

        }



    }
}
