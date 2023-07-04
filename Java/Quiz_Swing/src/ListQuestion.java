import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Creates new List Question using
 * JList, DefaultListModel, JScrollPane
 */
public class ListQuestion implements ActionListener {
    /**
     * existing frame
     */
    private final JFrame frame;
    /**
     * new panel that will be added to frame
     */
    private final JPanel panel;
    /**
     * button
     */
    private final JButton moveEle,reset,next;
    /**
     * jlist with pool of options
     */
    private final JList<String> jlist;
    /**
     * answer Jlist
     */
    private final JList<String> jlist2;
    /**
     * default list model used for moving items to another JScrollpane
     */
    private final DefaultListModel<String> dlm;
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
    public ListQuestion(JFrame frame, int numCorrect, String results) {
        this.frame = frame;
        this.numCorrect = numCorrect;
        this.results = results;

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.ORANGE);

        JLabel heading = new JLabel("Question 4: Lists");
        heading.setBounds(130, 20, 700, 60);
        heading.setFont(new Font("Oswald", Font.BOLD, 40));
        heading.setForeground(new Color(30, 144, 254));
        panel.add(heading);

        JLabel question = new JLabel("move all even numbers from the left box to the right box in ascending order");
        question.setBounds(10, 80, 700, 60);
        question.setFont(new Font("Oswald", Font.BOLD, 14));
        question.setForeground(new Color(30, 144, 254));
        panel.add(question);

        jlist = new JList<>(new String[]{"7","3","2","6","5","4","8","1"});
        JScrollPane pane = new JScrollPane(jlist);
        pane.setBounds(100,250,100,100);
        panel.add(pane);

        jlist2 = new JList<>(new String[10]);
        JScrollPane pane2 = new JScrollPane(jlist2);
        pane2.setBounds(540,250,100,100);
        panel.add(pane2);

        dlm = new DefaultListModel();

        moveEle = new JButton("move to even");
        moveEle.setBounds(275, 250, 200, 80);
        moveEle.setBackground(Color.WHITE);
        moveEle.setForeground(Color.BLACK);
        moveEle.addActionListener(this);
        panel.add(moveEle);

        next = new JButton("next");
        next.setBounds(540, 450, 100, 60);
        next.setBackground(Color.WHITE);
        next.setForeground(Color.BLACK);
        next.addActionListener(this);
        panel.add(next);

        reset = new JButton("reset tables");
        reset.setBounds(105, 450, 150, 60);
        reset.setBackground(Color.WHITE);
        reset.setForeground(Color.BLACK);
        reset.addActionListener(this);
        panel.add(reset);

        JProgressBar progressBar = new JProgressBar();
        progressBar.setBounds(120,10,500,10);
        panel.add(progressBar);
        progressBar.setBackground(Color.white);
        progressBar.setForeground(Color.BLUE);
        progressBar.setValue(75);

        this.frame.getContentPane().add(panel);
        this.frame.setVisible(true);
    }

    /**
     * Grades question and moves to submission page when next is clicked
     * @param e button is pressed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == moveEle){
            jlist2.setModel(dlm);
            jlist.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
            for (String sel : jlist.getSelectedValuesList()) {
                if (dlm.contains(sel)) {
                } else {
                    dlm.addElement(sel);
                }
            }

        }
        if (e.getSource() == reset){
            frame.getContentPane().remove(panel);
            new ListQuestion(frame,numCorrect, results);
        }
        if (e.getSource() == next) {
            frame.getContentPane().remove(panel);
            if (dlm.toString().equals("[2, 4, 6, 8]")){
                new Submission(frame, numCorrect+5, results +"4. Lists: CORRECT<br><br>");
            }
            else {
                if (dlm.toString().equals("[]")) {
                    new Submission(frame, numCorrect, results +"4. Lists: UNANSWERED<br><br>");
                }
                else {
                    new Submission(frame, numCorrect, results +"4. Lists: INCORRECT<br><br>");
                }
            }
        }

    }

}

