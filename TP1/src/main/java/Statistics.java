import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.*;

public class Statistics {

    public static void main(String[] args) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Boards boards = objectMapper.readValue(new File("src/main/resources/boards.json"), Boards.class);
        BufferedWriter writer = new BufferedWriter(new FileWriter("stats.csv"));

        writer.write("Type,Board Number,Millis,Height,Explored Nodes,Frontier Nodes\n");

        int id = 0;
        for (int[][] board : boards.getBoards()) {
            long start = System.nanoTime();
            Response response = Solver.bpa(new PuzzleState(board));
            response.setDuration(System.nanoTime()-start);
            writer.write(statsFormat(response, "BPA", id));
            writer.newLine();

            start = System.nanoTime();
            response = Solver.bpp(new PuzzleState(board));
            response.setDuration(System.nanoTime()-start);
            writer.write(statsFormat(response, "BPP", id));
            writer.newLine();

            start = System.nanoTime();
            response = Solver.bppv(new PuzzleState(board), 20);
            response.setDuration(System.nanoTime()-start);
            writer.write(statsFormat(response, "BPPV", id));
            writer.newLine();

            start = System.nanoTime();
            response = Solver.aStar(new PuzzleState(board), new Manhattan());
            response.setDuration(System.nanoTime()-start);
            writer.write(statsFormat(response, "A* - Manhattan", id));
            writer.newLine();

            start = System.nanoTime();
            response = Solver.aStar(new PuzzleState(board), new Misplaced());
            response.setDuration(System.nanoTime()-start);
            writer.write(statsFormat(response, "A* - Misplaced", id));
            writer.newLine();

            start = System.nanoTime();
            response = Solver.aStar(new PuzzleState(board), new EnforcedOrder());
            response.setDuration(System.nanoTime()-start);
            writer.write(statsFormat(response, "A* - EnforcedOrder", id));
            writer.newLine();

            start = System.nanoTime();
            response = Solver.localHeuristic(new PuzzleState(board), new Manhattan(), true);
            response.setDuration(System.nanoTime()-start);
            writer.write(statsFormat(response, "Local - Manhattan", id));
            writer.newLine();

            start = System.nanoTime();
            response = Solver.localHeuristic(new PuzzleState(board), new Misplaced(), true);
            response.setDuration(System.nanoTime()-start);
            writer.write(statsFormat(response, "Local - Misplaced", id));
            writer.newLine();

            start = System.nanoTime();
            response = Solver.localHeuristic(new PuzzleState(board), new EnforcedOrder(), true);
            response.setDuration(System.nanoTime()-start);
            writer.write(statsFormat(response, "Local - EnforcedOrder", id));
            writer.newLine();

            start = System.nanoTime();
            response = Solver.globalHeuristic(new PuzzleState(board), new Manhattan());
            response.setDuration(System.nanoTime()-start);
            writer.write(statsFormat(response, "Global - Manhattan", id));
            writer.newLine();

            start = System.nanoTime();
            response = Solver.globalHeuristic(new PuzzleState(board), new Misplaced());
            response.setDuration(System.nanoTime()-start);
            writer.write(statsFormat(response, "Global - Misplaced", id));
            writer.newLine();

            start = System.nanoTime();
            response = Solver.globalHeuristic(new PuzzleState(board), new EnforcedOrder());
            response.setDuration(System.nanoTime()-start);
            writer.write(statsFormat(response, "Global - EnforcedOrder", id));
            writer.newLine();
            id++;
        }

        writer.close();
    }

    private static String statsFormat(Response response, String type, int id) {
        if (response.solved)
            return type+","+id + "," + response.getDurationInMillis()+","+response.getNode().getHeight()+","+response.getExploredNodes()+","+response.getFrontierNodes();
        return "Not Solved "+type;
    }
}
