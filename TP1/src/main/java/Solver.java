import java.io.IOException;
import java.util.*;

public class Solver {

    public static Response bpa(PuzzleState startingState) {
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
                return new Response(false);
            }
        }

        return new Response(currentNode, true, nodeQueue.size(), nodeHashMap.size() - nodeQueue.size());
    }

    public static Response bpp(PuzzleState startingState) throws IOException {
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
				return new Response(false);
            }


        }

        return new Response(currentNode, true, nodeStack.size(), nodeHashMap.size() - nodeStack.size());

    }

	private static Response bppl(PuzzleState startingState, int limit) {

        Map<Integer,Node> nodeHashMap = new HashMap<>();
        Node root = new Node(null,startingState,0);
        Node currentNode = null;
        Stack<Node> nodeStack = new Stack<>();
        nodeStack.add(root);
        boolean solved = false;



        while (!solved){


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
                        nodeStack.add(n);
                    }
                }
            }

            if (nodeStack.empty()){
                return new Response(currentNode,false,0, nodeHashMap.size());
            }

        }

       return new Response(currentNode,true, nodeStack.size(),nodeHashMap.size()-nodeStack.size());
    }

    public static Response bppv(PuzzleState startingState, int guess) {
//        int max=guess;
//        int min=0;
//        int current=max;
        Response aux=null;
        int cumulativeExploredNodes=0;
//        while (max>(min+1)||max==0){
//            aux=bppl(startingState, current);
//            cumulativeExploredNodes+=aux.getExploredNodes();
//            if (!aux.isSolved())
//                max=current;
//            else
//                min=current;
//            current=(min+max)/2;
//
//        }

        int current=guess;
        boolean solvable=true;
        List<Response> responses=new ArrayList<>();
        Response lastSolvedResponse=null;
        boolean incremented=false;
        while (solvable){
            aux=bppl(startingState, current);
            cumulativeExploredNodes+=aux.getExploredNodes();
            if (!aux.isSolved()||aux.getNode().getHeight()==0){
                if((aux.getNode().getHeight()>=31&&incremented)||lastSolvedResponse!=null)
                    solvable=false;


                current++;
                incremented=true;
            }else {
                if (current==aux.getNode().getHeight())
                    current--;
                else {
                    current=aux.getNode().getHeight();

                }
                lastSolvedResponse=aux;

            }



        }

        if (lastSolvedResponse!=null){
            lastSolvedResponse.setExploredNodes(cumulativeExploredNodes);
            return lastSolvedResponse;
        }
        aux.setExploredNodes(cumulativeExploredNodes);
        return aux;
    }

	public static Response aStar(PuzzleState puzzleState, Heuristic heuristicProvider) {
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
            return new Response(false);
        }

        return new Response(currentNode, true, queue.size(), exploredNodesAmount);
	}

	private static double f(Node node, Heuristic heuristicProvider) {

		double heuristicValue = heuristicProvider.heuristic(node.getState());

		// como es costo uniforme entonces height es igual al costo
		return heuristicValue + node.getHeight();
	}


    private static void iterateNodeList(List<Node> list, Heuristic heuristic, Map<Integer,Node> nodeHashMap, Response response) {

        while(!list.isEmpty()){

            Node selectedNode = list.get(0);
            for(Node n:list){
                if(heuristic.heuristic(selectedNode.getState()) >
                        heuristic.heuristic(n.getState())){
                    selectedNode = n;
                }
            }
            if(selectedNode.isSolved()){
                response.solved=true;
                response.setNode(selectedNode);
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

    private static void iterateNodeListWithBacktracking(List<Node> list, Heuristic heuristic, Map<Integer,Node> nodeHashMap, Response response) {



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
                response.setNode(selectedNode);
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

    public static Response localHeuristic(PuzzleState initialState, Heuristic heuristic, boolean backtracking) {

        Node root = new Node(null, initialState,0);
        Map<Integer,Node> nodeHashMap = new HashMap<>();
        List<Node> list = new ArrayList<>();
        list.add(root);

        Response response = new Response();

        if (backtracking)
            iterateNodeListWithBacktracking(list,heuristic,nodeHashMap,response);
        else
            iterateNodeList(list,heuristic,nodeHashMap,response);
        if(!response.solved) {
            return new Response(false);
        }

        response.frontierNodes--;
        return response;

    }


    public static Response globalHeuristic (PuzzleState initialState, Heuristic heuristic) throws IOException {
        boolean foundSolution =false;
        Map<Integer,Node> nodeHashMap = new HashMap<>();
        Node root = new Node(null,initialState,0);
        List<Node> frontier = new ArrayList<>();
        frontier.add(root);
        Node aux;
        int exploredNodesAmount=0;

        while(!frontier.isEmpty()){
            aux = frontier.remove(0);
            if(aux.isSolved()) {
                return new Response(aux, true, frontier.size(), exploredNodesAmount);
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

        return new Response(false);
    }




}
