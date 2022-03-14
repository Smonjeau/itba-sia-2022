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

    public String getHeuristic() {
        return heuristic;
    }

    public Heuristic getHeuristicProvider() {
        Heuristic heuristicProvider;
        switch (heuristic) {
            case "manhattan":
                heuristicProvider = new Manhattan();
                break;
            case "enforced order":
                heuristicProvider = new EnforcedOrder();
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

    public int[][] getBoard() {
        return board;
    }

    @Override
    public String toString() {
        return "Parámetros: " +
                "algorithm='" + algorithm + '\'' +
                ", heuristic='" + heuristic + '\'' +
                ", guess=" + guess +
                ", board=" + Arrays.deepToString(board);
    }
}
