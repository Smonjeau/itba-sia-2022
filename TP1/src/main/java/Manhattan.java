public class Manhattan implements Heuristic {

    @Override
    public double heuristic(PuzzleState puzzleState) {
        int [][] board = puzzleState.getBoard();

        double manhattanDistance = 0;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {

                // num % 3 - 1 = column
                // num / 3 = row
//                board[i][j]
            }
        }

        return 0;
    }
}
