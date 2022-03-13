

public class ManhattanWorstCase implements Heuristic {

    @Override
    public double heuristic(PuzzleState puzzleState) {
        int[][] board = puzzleState.getBoard();

        double heuristic = 0;

        for (int[] row : board) {
            for (int value : row) {

                switch (value) {
                    case 1:
                    case 3:
                    case 7:
                    case 0:
                        heuristic += 4;
                        break;
                    case 2:
                    case 4:
                    case 6:
                    case 8:
                        heuristic += 3;
                        break;
                    default:
                        heuristic += 2;
                        break;
                }
            }
        }

        return heuristic;
    }
}
