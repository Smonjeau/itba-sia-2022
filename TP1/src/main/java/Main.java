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

        Response response;
        long start = System.nanoTime();
        if ("BPA".equals(alg)) {
            response = Solver.bpa(startingState);
        } else if ("BPP".equals(alg)) {
            response = Solver.bpp(startingState);
        } else if ("BPPV".equals(alg)) {
            if (parameters.getGuess() == null) {
                System.out.println("Missing guess value!");
                return;
            }
            response = Solver.bppv(startingState, parameters.getGuess());
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

            response = Solver.aStar(startingState, heuristicProvider);
        } else if ("local".equals(alg)) {
            Heuristic heuristicProvider = parameters.getHeuristicProvider();
            if (heuristicProvider == null) {
                System.out.println("Invalid heuristic parameter!");
                return;
            }

            response = Solver.localHeuristic(startingState, heuristicProvider,false);
        } else if("local-bt".equals(alg)){
            Heuristic heuristicProvider = parameters.getHeuristicProvider();
            if (heuristicProvider == null) {
                System.out.println("Invalid heuristic parameter!");
                return;
            }
            response = Solver.localHeuristic(startingState, heuristicProvider,true);
        } else if ("global".equals(alg)) {
            Heuristic heuristicProvider = parameters.getHeuristicProvider();
            if (heuristicProvider == null) {
                System.out.println("Invalid heuristic parameter!");
                return;
            }

            response = Solver.globalHeuristic(startingState, heuristicProvider);
        } else {
            System.out.println("Invalid algorithm argument!");
            return;
        }
        long end = System.nanoTime();
        response.setDuration(end - start);

        printSolution(writer, response);

        writer.close();
    }

    private static void printSolution(BufferedWriter writer, Response response) throws IOException {

        if (!response.solved) {
            writer.write("No se encontro una solución :(");
            return;
        }

        writer.write("Solución Encontrada\n");
        writer.write("Cantidad de nodos frontera: " + response.getFrontierNodes());
        writer.newLine();
        writer.write("Cantidad de nodos explorados: " + response.getExploredNodes());
        writer.newLine();
        writer.write("Cantidad de nodos de la solución: " + response.getNode().getHeight());
        writer.newLine();
        writer.write("Tiempo de ejecución (milisegundos): "+(response.getDurationInMillis()));
        writer.newLine();
        writer.newLine();
        writer.write("Solución: \n");
        writer.newLine();
        StringBuilder stringBuilder = new StringBuilder();
        Node currentNode = response.getNode();
        while (currentNode.prev!=null){
            stringBuilder.insert(0, currentNode.stateToString()+"\n");
            currentNode=currentNode.prev;
        }
        stringBuilder.insert(0, currentNode.stateToString()+"\n");
        writer.write(stringBuilder.toString());
    }


}
