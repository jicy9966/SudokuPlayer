import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChooseDifficulty extends JFrame
{
    private JLabel prompt;
    private JRadioButton easy, medium, hard;
    private ButtonGroup difficulty;
    private JButton exit, begin;

    public ChooseDifficulty()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(40, 20, 40, 20));

        JPanel inner_panel = new JPanel();
        inner_panel.setLayout(new GridLayout(3, 1));

        JPanel prompt_frame = new JPanel();
        prompt = new JLabel("Select Difficulty: ");
        prompt_frame.add(prompt);
        inner_panel.add(prompt_frame);

        easy = new JRadioButton("Easy");
        medium = new JRadioButton("Medium");
        hard = new JRadioButton("Hard");
        difficulty = new ButtonGroup();
        difficulty.add(easy);
        difficulty.add(medium);
        difficulty.add(hard);

        JPanel radio_frame = new JPanel();
        radio_frame.add(easy);
        radio_frame.add(medium);
        radio_frame.add(hard);
        inner_panel.add(radio_frame);

        exit = new JButton("EXIT");
        exit.addActionListener(new ExitListener());

        begin = new JButton("BEGIN");
        begin.addActionListener(new BeginListener());

        JPanel button_frame = new JPanel();
        button_frame.setLayout(new BorderLayout());
        JPanel two_buttons = new JPanel();
        two_buttons.add(exit);
        two_buttons.add(begin);
        button_frame.add(two_buttons);
        inner_panel.add(button_frame);

        panel.add(inner_panel, BorderLayout.CENTER);
        add(panel);
        setSize(400, 250);
    }

    class ExitListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            dispose();
        }
    }

    class BeginListener implements ActionListener
    {
        public void actionPerformed(ActionEvent event)
        {
            dispose();
            String chosen_difficulty = "";
            for (var choice = difficulty.getElements(); choice.hasMoreElements(); )
            {
                var button = choice.nextElement();
                if (button.isSelected())
                    chosen_difficulty = button.getText();
            }
            SudokuBoard frame = new SudokuBoard(chosen_difficulty.toLowerCase());
            frame.setTitle("SUDOKU");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        }
    }

}
