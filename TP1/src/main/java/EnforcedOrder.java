
public class EnforcedOrder implements Heuristic {

    @Override
    public double heuristic(PuzzleState puzzleState) {

        int[][] board = puzzleState.getBoard();
        int[][] expectedBoard = PuzzleState.solvedState.getBoard();

        int heuristic = 0;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != expectedBoard[i][j]) {
                    heuristic += 8 - expectedBoard[i][j] + 1;
                }
            }
        }

        return heuristic;
    }
}
