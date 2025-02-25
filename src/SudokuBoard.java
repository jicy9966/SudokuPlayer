import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public class SudokuBoard extends JFrame
{
    private Sudoku sudoku;
    private Stack<Move> undo_stack = new Stack<>();
    private Stack<Move> redo_stack = new Stack<>();
    private JButton[][] button_fields = new JButton[9][9];

    private JPopupMenu popup_menu = new JPopupMenu();
    public SudokuBoard(String difficulty)
    {
        sudoku = new Sudoku(difficulty);

        setSize(500, 600);
        setLayout(new BorderLayout());

        JPanel grid_panel = new JPanel(new GridLayout(3,3,10,10));

        int row = 0;
        int col = 0;
        for (int i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 3; ++j)
            {
                JPanel subgrid_panel = new JPanel(new GridLayout(3,3,3,3));
                subgrid_panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

                for (int x = 0; x < 3; ++x)
                {
                    for (int y = 0; y < 3; ++y)
                    {
                        JButton field = new JButton();
                        field.setHorizontalAlignment(JTextField.CENTER);
                        field.setFont(new Font("Arial", Font.BOLD, 20));
                        field.setBackground(Color.WHITE);

                        if (sudoku.board[row][col] != 0)
                        {
                            field.setText(String.valueOf(sudoku.board[row][col]));
                            field.setEnabled(false);
                        } else
                        {
                            int r = row;
                            int c = col;
                            field.addActionListener(e -> inputNumber(r, c, field));
                        }

                        button_fields[row][col] = field;
                        subgrid_panel.add(field);
                        ++col;
                    }
                    col -= 3;
                    ++row;
                }
                grid_panel.add(subgrid_panel);
                row -= 3;
                col += 3;
            }
            col = 0;
            row += 3;
        }

        JPanel two_buttons = new JPanel(new FlowLayout());

        JButton undoButton = new JButton("Undo");
        JButton redoButton = new JButton("Redo");

        undoButton.addActionListener(e -> undo());
        redoButton.addActionListener(e -> redo());

        two_buttons.add(undoButton);
        two_buttons.add(redoButton);

        JPanel button_panel = new JPanel();
        button_panel.setLayout(new BoxLayout(button_panel, BoxLayout.Y_AXIS));

        JButton doneButton = new JButton("DONE");
        doneButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        doneButton.addActionListener(e -> complete_game());

        button_panel.add(two_buttons);
        button_panel.add(doneButton);
        button_panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        add(grid_panel, BorderLayout.CENTER);
        add(button_panel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void inputNumber(int row, int col, JButton field)
    {
        popup_menu = new JPopupMenu();
        for (int i = 1; i <= 9; ++i)
        {
            JMenuItem menuItem = new JMenuItem(String.valueOf(i));
            menuItem.addActionListener(e -> handleInput(row, col, field, menuItem.getText()));
            popup_menu.add(menuItem);
        }
        popup_menu.show(field, 0, field.getHeight());
    }

    private void handleInput(int row, int col, JButton field, String input)
    {
        if (!sudoku.is_valid_move(row, col, Integer.parseInt(input)))
        {
            JOptionPane.showMessageDialog(null, "Invalid Input.");
            return;
        }

        field.setText(input);

        int prev_num = sudoku.board[row][col];
        int new_num = input.isEmpty() ? 0 : Integer.parseInt(input);
        sudoku.board[row][col] = Integer.parseInt(input);

        undo_stack.push(new Move(row, col, prev_num, new_num));
        redo_stack.clear();
    }

    public void undo()
    {
        if (!undo_stack.isEmpty())
        {
            Move last_move = undo_stack.pop();
            sudoku.board[last_move.row][last_move.col] = last_move.prev_num;
            button_fields[last_move.row][last_move.col].setText(last_move.prev_num == 0 ? "" : String.valueOf(last_move.prev_num));
            redo_stack.push(last_move);
        }
    }

    public void redo()
    {
        if (!redo_stack.isEmpty())
        {
            Move last_move = redo_stack.pop();
            sudoku.board[last_move.row][last_move.col] = last_move.new_num;
            button_fields[last_move.row][last_move.col].setText(String.valueOf(last_move.new_num));
            undo_stack.push(last_move);
        }
    }

    private boolean is_game_complete()
    {
        for (int[] row : sudoku.board)
        {
            for (int num : row)
            {
                if (num == 0)
                    return false;
            }
        }

        return true;
    }

    public void complete_game()
    {
        if (is_game_complete())
        {
            JOptionPane.showMessageDialog(null, "Congratulations! You Win.");
            dispose();
        }
        else
            JOptionPane.showMessageDialog(null, "Not Complete.");
    }
}
