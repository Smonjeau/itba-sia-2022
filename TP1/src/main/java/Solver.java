import java.io.BufferedWriter;
import java.io.IOException;
import java.util.*;

public class Solver {

    public static void bpa(PuzzleState startingState, BufferedWriter writer) throws IOException {
        long start = System.nanoTime();
        Map<Integer,Node> nodeHashMap = new HashMap<>();
        Node root = new Node(null,startingState,0);
        Node currentNode = null;
        Queue<Node> nodeQueue = new LinkedList<>();
        nodeQueue.add(root);
        boolean solved = false;


        while (!solved){

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
                return;
            }



        }
        long end = System.nanoTime();
        printSolution(currentNode, writer, nodeQueue.size(), nodeHashMap.size() - nodeQueue.size(), end - start);
    }

    public static void bpp(PuzzleState startingState, BufferedWriter writer) throws IOException {
        long start = System.nanoTime();
        Map<Integer,Node> nodeHashMap = new HashMap<>();
        Node root = new Node(null,startingState,0);
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
				return;
            }


        }

        long end = System.nanoTime();
        printSolution(currentNode, writer, nodeStack.size(), nodeHashMap.size() - nodeStack.size(), end - start);

    }

	private static int bppl(PuzzleState startingState,int limit,boolean print, BufferedWriter writer, Long start) throws IOException {
        if (start == null && print)
            start = System.nanoTime();

		Map<Integer,Node> nodeHashMap = new HashMap<>();
		Node root = new Node(null,startingState,0);
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
		if (currentNode.isSolved()) {
			solved = true;
			continue;
		}
		if (currentNode.getHeight()<limit) {
			List<Node> nodes = currentNode.MakeStep();
			for (Node n:nodes){
			if (!nodeHashMap.containsKey(n.hashCode())) {
				nodeHashMap.put(n.hashCode(),n);
				nodeStack.add(n);
			}else if (nodeHashMap.get(n.hashCode()).getHeight()>(currentNode.getHeight()+1)){
				Node aux=nodeHashMap.get(n.hashCode());
				aux.setHeight(currentNode.getHeight()+1);
				aux.setPrev(currentNode);
			}
			}
		}

		if (nodeStack.empty()){
            if (print)
	            writer.write("Cannot Find Solution\n");
			return -1;
		}

		}
		if (print) {
            long end = System.nanoTime();
            printSolution(currentNode, writer, nodeStack.size(), nodeHashMap.size() - nodeStack.size(), end - start);
        }
		return currentNode.getHeight();

	}

    public static void bppv(PuzzleState startingState,int guess, BufferedWriter writer) throws IOException {
        long start = System.nanoTime();
        int max=guess;
        int min=0;
        int current=max;
        while (max!=(min+1)||max==0){
          int aux=bppl(startingState, current,false, writer, null);
          if (aux!=-1)
            max=current;
          else
            min=current;
          current=(min+max)/2;

        }
        bppl(startingState, max,true, writer, start);
    }

	public static void aStar(PuzzleState puzzleState, BufferedWriter writer, Heuristic heuristicProvider) throws IOException {
        long start = System.nanoTime();
        Comparator<Node> byOrderValue = Comparator.comparingDouble(Node::getOrderValue).thenComparingDouble(Node::hashCode);

		PriorityQueue<Node> queue = new PriorityQueue(9, byOrderValue); // TODO remove magic number
        boolean found = false;

		int exploredNodesAmount = 0;

		Node root = new Node(null, puzzleState, 0);
		root.setOrderValue(f(root, heuristicProvider));
		queue.add(root);

		Node currentNode = null;

		while (!queue.isEmpty()) {
            currentNode = queue.poll();
			if (currentNode.isSolved()) {
				found = true;
				break;
			}
			exploredNodesAmount++;

            List<Node> nodes = currentNode.MakeStep();
			for (Node node : nodes) {
                node.setOrderValue(f(node, heuristicProvider));
                node.setHeight(currentNode.getHeight() + 1);
                node.setPrev(currentNode);
                queue.add(node);
            }

//            writer.write("Heuristic: "+queue.peek().getOrderValue()+"\n"+queue.peek().stateToString()+"\n");
//            writer.write("\n-------------------------\n");
		}

        if (!found) {
            writer.write("Cannot Find Solution\n");
            return;
        }

        long end = System.nanoTime();
        printSolution(currentNode, writer, queue.size(), exploredNodesAmount, end - start);
	}

	private static double f(Node node, Heuristic heuristicProvider) {

		double heuristicValue = heuristicProvider.heuristic(node.getState());

		// como es costo uniforme entonces height es igual al costo
        // System.out.println(heuristicValue+", "+node.getHeight());
		return heuristicValue + node.getHeight(); 
	}

    private static void printSolution(Node currentNode, BufferedWriter writer, int frontierNodesAmount, int exploredNodesAmount, long duration) throws IOException {
        writer.write("Solución Encontrada\n");
        writer.write("Cantidad de nodos frontera: " + frontierNodesAmount);
        writer.newLine();
        writer.write("Cantidad de nodos explorados: " + exploredNodesAmount);
        writer.newLine();
        writer.write("Cantidad de nodos de la solucion: " + currentNode.getHeight());
        writer.newLine();
        writer.write("Tiempo de ejecución (milisegundos): "+(duration / 1000000));
        writer.newLine();
        writer.newLine();
        writer.write("Solución: \n");
        writer.newLine();
        StringBuilder stringBuilder = new StringBuilder();
        while (currentNode.prev!=null){
            stringBuilder.insert(0, currentNode.stateToString()+"\n");
            currentNode=currentNode.prev;
        }
        stringBuilder.insert(0, currentNode.stateToString()+"\n");
        writer.write(stringBuilder.toString());
    }
}
