import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Creates new dropdown question using
 * JComboBox
 */
public class DropDown implements ActionListener {
    /**
     * existing frame
     */
    private final JFrame frame;
    /**
     * new panel that will be added to frame
     */
    private final JPanel panel;
    /**
     * button used to go to next question
     */
    private final JButton next;
    /**
     * current quiz score
     */
    private final int numCorrect;
    /**
     * current quiz summary
     */
    private final String results;
    /**
     * JComboBox with options
     */
    private final JComboBox<String> dropdown;

    /**
     * Creates a new panel with checkbox question
     * and adds it to existing frame
     * @param frame existing frame
     * @param numCorrect current score
     * @param results current quiz summary
     */
    public DropDown(JFrame frame, int numCorrect, String results) {
        this.frame = frame;
        this.numCorrect = numCorrect;
        this.results = results;

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.ORANGE);

        JLabel heading = new JLabel("Question 3: Dropdown");
        heading.setBounds(130, 20, 700, 60);
        heading.setFont(new Font("Oswald", Font.BOLD, 40));
        heading.setForeground(new Color(30, 144, 254));
        panel.add(heading);

        JLabel question = new JLabel("Skateboards usually have            wheels");
        question.setBounds(80, 250, 700, 60);
        question.setFont(new Font("Dialog", Font.BOLD, 25));
        question.setForeground(new Color(30, 144, 254));
        panel.add(question);

        dropdown = new JComboBox<>(new String[]{"1","2","3","4","8"});
        dropdown.setBounds(450, 245, 80, 75);
        panel.add(dropdown);

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
        progressBar.setValue(50);

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

            if (dropdown.getSelectedItem().equals("4")){
                new ListQuestion(frame, numCorrect+1, results +"3. Dropdown: CORRECT<br><br>");
            }
            else{
                new ListQuestion(frame,numCorrect, results +"3. Dropdown: INCORRECT<br><br>");
            }
        }
    }
}
