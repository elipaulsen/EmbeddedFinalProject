import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Creates a new Multiple choice question using
 * JRadioButtons and ButtonGroups
 */
public class MultipleChoice implements ActionListener {

    /**
     * radio button that holds the correct answer
     */
    private final JRadioButton correct;
    /**
     * button group that holds all options
     */
    private final ButtonGroup mp;
    /**
     * button to move to next question
     */
    private final JButton next;
    /**
     * new panel that will be added to frame
     */
    private final JPanel panel;
    /**
     * existing JFrame
     */
    private final JFrame frame;

    /**
     * will create a new multiple choice question panel
     * and add it to the existing frame
     * @param frame
     */
    public MultipleChoice(JFrame frame) {

        this.frame = frame;
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.ORANGE);

        JLabel heading = new JLabel("Question 1: Multiple Choice");
        heading.setBounds(120, 20, 700, 60);
        heading.setFont(new Font("Oswald", Font.BOLD, 40));
        heading.setForeground(new Color(30, 144, 254));
        panel.add(heading);


        JLabel question = new JLabel("How many legs do spider have?");
        question.setBounds(175, 100, 700, 60);
        question.setFont(new Font("Dialog", Font.BOLD, 25));
        question.setForeground(new Color(30, 144, 254));
        panel.add(question);

        JRadioButton option1 = new JRadioButton("4");
        option1.setBounds(295, 150, 200, 80);
        option1.setBackground(Color.WHITE);
        option1.setFont(new Font("Dialog", Font.PLAIN, 20));
        panel.add(option1);

        JRadioButton option2 = new JRadioButton("18");
        option2.setBounds(295, 200, 200, 80);
        option2.setBackground(Color.WHITE);
        option2.setFont(new Font("Dialog", Font.PLAIN, 20));
        panel.add(option2);

        correct = new JRadioButton("8");
        correct.setBounds(295, 250, 200, 80);
        correct.setBackground(Color.WHITE);
        correct.setFont(new Font("Dialog", Font.PLAIN, 20));
        panel.add(correct);

        JRadioButton option4 = new JRadioButton("1700");
        option4.setBounds(295, 300, 200, 80);
        option4.setBackground(Color.WHITE);
        option4.setFont(new Font("Dialog", Font.PLAIN, 20));
        panel.add(option4);

        next = new JButton("Next Question");
        next.setBounds(275, 400, 200, 80);
        next.setBackground(Color.WHITE);
        next.setForeground(Color.BLACK);
        next.addActionListener(this);
        panel.add(next);

        mp = new ButtonGroup();
        mp.add(option1);
        mp.add(option2);
        mp.add(correct);
        mp.add(option4);

        JProgressBar progressBar = new JProgressBar();
        progressBar.setBounds(120,10,500,10);
        panel.add(progressBar);
        progressBar.setBackground(Color.white);
        progressBar.setForeground(Color.BLUE);
        progressBar.setValue(5);

        this.frame.getContentPane().add(panel);
        this.frame.setVisible(true);
    }

    /**
     * grades question and moves to checkbox question
     * when next is pressed
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == next){
            frame.getContentPane().remove(panel);
            if(correct.isSelected()){
                new CheckBox(frame, 1,"1. Mulitiple Choice: CORRECT<br><br>");
            }
            else if (mp.getSelection() == null) {
                new CheckBox(frame,0,"1. Mulitiple Choice: UNANSWERED<br><br>");
            }
            else{
                new CheckBox(frame,0,"1. Mulitiple Choice: INCORRECT<br><br>");
            }
        }
    }
}
