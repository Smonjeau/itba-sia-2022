import java.util.Arrays;

public class Parameters {
    String algorithm;
    String heuristic;
    Integer guess;
    int[][] board;

    public Parameters() {}

    public Parameters(String algorithm, String heuristic, Integer guess, int[][] board) {
        this.algorithm = algorithm;
        this.heuristic = heuristic;
        this.guess = guess;
        this.board = board;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public String getHeuristic() {
        return heuristic;
    }

    public Heuristic getHeuristicProvider() {
        Heuristic heuristicProvider;
        switch (heuristic) {
            case "manhattan":
                heuristicProvider = new Manhattan();
                break;
            case "worst_case":
                heuristicProvider = new ManhattanWorstCase();
                break;
            case "misplaced":
                heuristicProvider = new Misplaced();
                break;
            default:
                System.out.println("Invalid heuristic parameter!");
                return null;
        }
        return heuristicProvider;
    }

    public void setHeuristic(String heuristic) {
        this.heuristic = heuristic;
    }

    public Integer getGuess() {
        return guess;
    }

    public void setGuess(Integer guess) {
        this.guess = guess;
    }

    public int[][] getBoard() {
        return board;
    }

    public void setBoard(int[][] board) {
        this.board = board;
    }

    @Override
    public String toString() {
        return "Parametros: " +
                "algorithm='" + algorithm + '\'' +
                ", heuristic='" + heuristic + '\'' +
                ", guess=" + guess +
                ", board=" + Arrays.deepToString(board);
    }
}
