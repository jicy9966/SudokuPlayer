import java.util.Random;

public class Sudoku
{
    public int[][] board = new int[9][9];

    public Sudoku(String difficulty)
    {
        generate_board();
        switch (difficulty)
        {
            case "easy":
                remove_numbers(5); // for testing purposes
                //remove_numbers(20);
                break;
            case "hard":
                remove_numbers(50);
                break;
            default: // "medium"
                remove_numbers(40);
        }
    }

    private boolean generate_board()
    {
        for (int row = 0; row < 9; ++row)
        {
            for (int col = 0; col < 9; ++col)
            {
                if (board[row][col] == 0)
                {
                    int[] random_array = get_random_numbers();
                    for (int num: random_array)
                    {
                        if (is_valid_move(row, col, num))
                        {
                            board[row][col] = num;
                            if (generate_board())
                                return true;
                            board[row][col] = 0; // for backtracking
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    private int[] get_random_numbers()
    {
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Random random = new Random();
        for (int i = 0; i < numbers.length; ++i)
        {
            int j = random.nextInt(numbers.length);
            int temp = numbers[i];
            numbers[i] = numbers[j];
            numbers[j] = temp;
        }
        return numbers;
    }

    public boolean is_valid_move(int row, int col, int num)
    {
        for (int i = 0; i < 9; ++i)
        {
            if (board[row][i] == num || board[i][col] == num)
                return false;
        }

        int boxRow = row / 3 * 3, boxCol = col / 3 * 3;
        for (int i = 0; i < 3; i++)
        {
            for (int j = 0; j < 3; j++)
            {
                if (board[boxRow + i][boxCol + j] == num)
                    return false;
            }
        }

        return true;
    }

    private void remove_numbers(int to_remove)
    {
        Random random = new Random();

        while (to_remove > 0)
        {
            int row = random.nextInt(9);
            int col = random.nextInt(9);

            if (board[row][col] != 0)
            {
                board[row][col] = 0;
                to_remove--;
            }
        }
    }
}
