import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        Parameters parameters = objectMapper.readValue(new File(args[0]), Parameters.class);

        if (parameters.getAlgorithm() == null) {
            System.out.println("Missing algorithm parameter!");
            return;
        }

        if (parameters.getBoard() == null) {
            System.out.println("Missing board parameter!");
            return;
        }

//        [
//                [1,5,2],
//                [4,0,3],
//                [7,8,6]
//        ]
//        [
//                [1,8,2],
//                [0,4,3],
//                [7,6,5]
//        ]

        PuzzleState startingState = new PuzzleState(parameters.getBoard());
        BufferedWriter writer = new BufferedWriter(new FileWriter("logs.txt"));

        writer.write(parameters.toString());
        writer.newLine();
        writer.newLine();
        Object alg = parameters.getAlgorithm();
        if ("BPA".equals(alg)) {
            Solver.bpa(startingState, writer);
        } else if ("BPP".equals(alg)) {
            Solver.bpp(startingState, writer);
        } else if ("BPPV".equals(alg)) {
            if (parameters.getGuess() == null) {
                System.out.println("Missing guess value!");
                return;
            }
            Solver.bppv(startingState, parameters.getGuess(), writer);
        } else if ("A*".equals(alg)) {
            if (parameters.getHeuristic() == null) {
                System.out.println("Missing heuristic parameter!");
                return;
            }

            Heuristic heuristicProvider = parameters.getHeuristicProvider();
            if (heuristicProvider == null) {
                System.out.println("Invalid heuristic parameter!");
                return;
            }

            Solver.aStar(startingState, writer, heuristicProvider);
        } else if ("local".equals(alg)) {
            Heuristic heuristicProvider = parameters.getHeuristicProvider();
            if (heuristicProvider == null) {
                System.out.println("Invalid heuristic parameter!");
                return;
            }

            Solver.localHeuristic(startingState,writer,heuristicProvider,false);
        } else if("local-bt".equals(alg)){
            Heuristic heuristicProvider = parameters.getHeuristicProvider();
            if (heuristicProvider == null) {
                System.out.println("Invalid heuristic parameter!");
                return;
            }
            Solver.localHeuristic(startingState, writer,heuristicProvider,true);
        } else if ("global".equals(alg)) {
            Heuristic heuristicProvider = parameters.getHeuristicProvider();
            if (heuristicProvider == null) {
                System.out.println("Invalid heuristic parameter!");
                return;
            }

            Solver.globalHeuristic(startingState,writer,heuristicProvider);
        } else {
            System.out.println("Invalid algorithm argument!");
            return;
        }

        writer.close();
    }




}
