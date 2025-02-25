import javax.swing.*;

public class SudokuPlayer extends JFrame
{
    public static void main(String[] args)
    {

        SwingUtilities.invokeLater(new Runnable() 
        {
            public void run() 
            {
                // begins the game with selecting game difficulty
                ChooseDifficulty frame = new ChooseDifficulty();
                frame.setTitle("Choose Game Difficulty");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
        
    }
}
