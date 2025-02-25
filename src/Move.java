public class Move
{
    int row, col, prev_num, new_num;

    public Move (int row, int col, int prev_num, int new_num)
    {
        this.row = row;
        this.col = col;
        this.prev_num = prev_num;
        this.new_num = new_num;
    }
}
