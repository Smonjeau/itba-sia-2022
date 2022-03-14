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
                continue;
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
        Node root = new Node(null, startingState,0);
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

	private static Response bppl(PuzzleState startingState,int limit) throws IOException {
//    if (start == null && print)
//        start = System.nanoTime();

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
//            if (print)
//                writer.write("Cannot Find Solution\n");
            return new Response(false,0,nodeHashMap.size(),currentNode);
        }

    }
//    if (print) {
//        long end = System.nanoTime();
//        if (response!=null)
//            printSolution(currentNode, writer, response.getFrontierNodes(), response.getExploredNodes(), end - start);
//        else
//            printSolution(currentNode, writer, nodeStack.size(), nodeHashMap.size() - nodeStack.size(), end - start);
//    }
    return new Response(true,nodeStack.size(),nodeHashMap.size()-nodeStack.size(), currentNode);

}

    public static Response bppv(PuzzleState startingState,int guess) throws IOException {
//        long start = System.nanoTime();
        int max=guess;
        int min=0;
        int current=max;
        Response aux=null;
        int cumulativeExploredNodes=0;
        while (max!=(min+1)||max==0){
            aux=bppl(startingState, current);
            cumulativeExploredNodes+=aux.getExploredNodes();
            if (!aux.isSolved())
                max=current;
            else
                min=current;
            current=(min+max)/2;

        }
//        bppl(startingState, max,true, writer, start);
        if (aux!=null)
            aux.setExploredNodes(cumulativeExploredNodes);
        return aux;
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


    private static void iterateNodeList(List<Node> list,BufferedWriter writer,Heuristic heuristic, Map<Integer,Node> nodeHashMap,long start, Response response) throws IOException {

        while(!list.isEmpty()){

            Node selectedNode = list.get(0);
            for(Node n:list){
                if(heuristic.heuristic(selectedNode.getState()) >
                        heuristic.heuristic(n.getState())){
                    selectedNode = n;
                }
            }
            if(selectedNode.isSolved()){
                long end = System.nanoTime();
                response.solved=true;
                printSolution(selectedNode,writer,response.frontierNodes-1,response.exploredNodes,end-start);
                return;
            }

            list.clear();
            if(!nodeHashMap.containsKey(selectedNode.hashCode())){
                nodeHashMap.put(selectedNode.hashCode(),selectedNode);
                List<Node> successors = selectedNode.MakeStep();
                response.exploredNodes++;
                response.frontierNodes--;
                response.frontierNodes += successors.size();
                list.addAll(successors);
            }
        }





    }

    private static void iterateNodeListWithBacktracking(List<Node> list, BufferedWriter writer,Heuristic heuristic, Map<Integer,Node> nodeHashMap,long start,Response response) throws IOException {



        Stack<List<Node>> aux = new Stack<>();
        while(!list.isEmpty() && !response.solved){
            Node selectedNode = list.get(0);
            for(Node n:list){
                if(heuristic.heuristic(selectedNode.getState()) >
                        heuristic.heuristic(n.getState())){
                    selectedNode = n;
                }

            }

            if(selectedNode.isSolved()){
                response.solved=true;
                long end = System.nanoTime();

                printSolution(selectedNode,writer,response.frontierNodes-1, response.exploredNodes, end-start);
                return;
            }

            if(!nodeHashMap.containsKey(selectedNode.hashCode())){
                nodeHashMap.put(selectedNode.hashCode(),selectedNode);
                response.exploredNodes++;
                response.frontierNodes--;
                List<Node> successors = selectedNode.MakeStep();
                response.frontierNodes += successors.size();

              //  iterateNodeListWithBacktracking(successors, writer, heuristic,nodeHashMap,start,response);

                List<Node> temp = new ArrayList<>(list);
                aux.push(temp);
                list.clear();
                list.addAll(successors);

            }else {
                list.remove(selectedNode);
                if(list.isEmpty() && !aux.isEmpty())
                    list = aux.pop();

            }


        }



    }
    public static void localHeuristic(PuzzleState initialState, BufferedWriter writer, Heuristic heuristic, boolean backtracking) throws IOException{

        long start = System.nanoTime();

        Node root = new Node(null, initialState,0);
        Map<Integer,Node> nodeHashMap = new HashMap<>();
        List<Node> list = new ArrayList<>();
        list.add(root);

       // Response response = new Response(false,1,0);

        if (backtracking)
            iterateNodeListWithBacktracking(list,writer,heuristic,nodeHashMap,start,response);
        else
            iterateNodeList(list,writer,heuristic,nodeHashMap,start,response);
        if(!response.isSolved()) {
            writer.write("Cannot Find Solution\n");
        }

    }


    public static void globalHeuristic (PuzzleState initialState, BufferedWriter writer, Heuristic heuristic) throws IOException {
        boolean foundSolution =false;
        Map<Integer,Node> nodeHashMap = new HashMap<>();
        long start = System.nanoTime();
        Node root = new Node(null,initialState,0);
        List<Node> frontier = new ArrayList<>();
        frontier.add(root);
        Node aux;
        int exploredNodesAmount=0;
        while(!frontier.isEmpty()){
            aux = frontier.remove(0);
            if(aux.isSolved()) {
                foundSolution = true;

                printSolution(aux, writer, frontier.size(), exploredNodesAmount ,System.nanoTime() - start);
                break;
            }

            if(!nodeHashMap.containsKey(aux.hashCode())){
                nodeHashMap.put(aux.hashCode(),aux);
                List<Node> successors = aux.MakeStep();
                exploredNodesAmount++;
                frontier.addAll(successors);

                frontier.sort((o1, o2) -> (int) (heuristic.heuristic(o1.getState())
                        - heuristic.heuristic(o2.getState())));
            }



        }

        if(!foundSolution){
            writer.write("Cannot find solution");
        }

    }



    private static void printSolution(Node currentNode, BufferedWriter writer, int frontierNodesAmount, int exploredNodesAmount, long duration) throws IOException {
        writer.write("Solución Encontrada\n");
        writer.write("Cantidad de nodos frontera: " + frontierNodesAmount);
        writer.newLine();
        writer.write("Cantidad de nodos explorados: " + exploredNodesAmount);
        writer.newLine();
        writer.write("Cantidad de nodos de la solución: " + currentNode.getHeight());
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
