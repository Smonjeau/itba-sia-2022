



import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class Solver {

    public static void bpa(int [][] startingState, BufferedWriter writer) throws IOException {
        Map<Integer,Node> nodeHashMap = new HashMap<>();
        Node root = new Node(null,startingState);
        Node currentNode = null;
        Queue<Node> nodeQueue = new LinkedList<>();
        nodeQueue.add(root);
        boolean solved = false;
        int counter = 0;


        while (!solved){
            if (counter == 1000){
                counter =- 1;
//                writer.write(String.valueOf(nodeHashMap.size()));
//                writer.write("stack size");

            }
            counter++;

            currentNode = nodeQueue.poll();
            List<Node> nodes = currentNode.MakeStep();

            if (currentNode.isSolved()) {
                solved = true;
                break;
            }
            for (Node n:nodes){
                if (!nodeHashMap.containsKey(n.hashCode())) {
                    nodeHashMap.put(n.hashCode(),n);
                    nodeQueue.add(n);
                }
            }
            if (nodeQueue.isEmpty()){
                writer.write("Cannot Find Solution\n");
            }



        }

        printSolution(currentNode, writer, nodeQueue.size(), nodeHashMap.size() - nodeQueue.size());
    }

    public static void bpp(int [][] startingState, BufferedWriter writer) throws IOException {
        Map<Integer,Node> nodeHashMap = new HashMap<>();
        Node root = new Node(null,startingState);
        Node currentNode = null;
        Stack<Node> nodeStack = new Stack<>();
        nodeStack.add(root);
        boolean solved = false;
        int counter = 0;


        while (!solved){
            if (counter == 1000){
                counter =- 1;
            }
            counter++;

            currentNode=nodeStack.pop();
            List<Node> nodes = currentNode.MakeStep();

            if (currentNode.isSolved()) {
                solved = true;
                continue;
            }
            for (Node n:nodes){
                if (!nodeHashMap.containsKey(n.hashCode())) {
                    nodeHashMap.put(n.hashCode(),n);
                    nodeStack.add(n);
                }
            }
            if (nodeStack.empty()){
                writer.write("Cannot Find Solution\n");
            }


        }

        printSolution(currentNode, writer, nodeStack.size(), nodeHashMap.size() - nodeStack.size());

    }

    private static void printSolution(Node currentNode, BufferedWriter writer, int frontierNodesAmount, int exploredNodesAmount) throws IOException {
        writer.write("Solution Found:\n");
        writer.write("Cantidad de nodos frontera: " + frontierNodesAmount);
        writer.newLine();
        writer.write("Cantidad de nodos explorados: " + exploredNodesAmount);
        writer.newLine();
        StringBuilder stringBuilder = new StringBuilder();
        while (currentNode.prev!=null){
            stringBuilder.insert(0, Arrays.deepToString(currentNode.getState())+"\n");
            currentNode=currentNode.prev;
        }
        stringBuilder.insert(0, Arrays.deepToString(currentNode.getState())+"\n");
        writer.write(stringBuilder.toString());
    }
}
