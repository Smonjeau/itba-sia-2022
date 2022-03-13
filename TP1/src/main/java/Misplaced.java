
/*---------------- Misplaced Heuristic ----------------
    cuenta la cantidad de fichas las cuales no estan
    posicionadas en su lugar correcto.
-----------------------------------------------------*/
public class Misplaced implements Heuristic {

    @Override
    public double heuristic(PuzzleState puzzleState) {
        
        int [][] board = puzzleState.getBoard();
        int [][] solvedBoard = PuzzleState.solvedState.getBoard();

        int heuristic = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] != solvedBoard[i][j] && board[i][j] != 0)
                    heuristic++;
            }
        }

        return heuristic;
    }

    
}
