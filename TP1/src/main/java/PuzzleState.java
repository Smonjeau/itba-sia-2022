import java.util.Arrays;

public class PuzzleState {

    int[][] board;

    private static final int [][] solvedBoard = {
            {1,2,3},
            {4,5,6},
            {7,8,0}
    };

    public static final PuzzleState solvedState = new PuzzleState(solvedBoard);

    public PuzzleState(int[][] board) {
        this.board = board;
    }

    public int getValueAt(int row, int col) {
        return board[row][col];
    }

    public int[] getRowAt(int row) {
        return board[row];
    }

    public int[][] getBoard() {
        return board;
    }

    public boolean isGoalState() {
        return Arrays.deepEquals(board, solvedState.getBoard());
    }

    @Override
    public int hashCode() {
        int hash = 0;
        for (int[] ints : board) {
            for (int anInt : ints) {
                hash *= 10;
                hash += anInt;
            }
        }
        return hash;
    }


}
