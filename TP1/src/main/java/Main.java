import java.util.*;

public class Main {


    public static void main(String[] args) {


        int [][] startingState={
                {1,5,2},
                {4,0,3},
                {7,8,6}
        };




        Map<Integer,Node> nodeHashMap=new HashMap<>();
        Node root=new Node(null,startingState);
        Node currentNode=null;
//    Stack<Node>nodeStack=new Stack<>();
//    nodeStack.add(root);
        Queue<Node>nodeQueue=new LinkedList<>();
        nodeQueue.add(root);
        boolean solved=false;
        int counter=0;


        while (!solved){
            if (counter==1000){
                counter=-1;
                System.out.print(nodeHashMap.size());
                System.out.print("stack size");
//        System.out.println(nodeStack.size());

            }
            counter++;

//      currentNode=nodeStack.pop();
            currentNode=nodeQueue.poll();
            List<Node> nodes=currentNode.MakeStep();
//      for (Node n:nodes){
//        if (n.isSolved()){
//          solved=true;
//          currentNode=n;
//          break;
//        }else if (!nodeHashMap.containsKey(n.hashCode())) {
//          nodeHashMap.put(n.hashCode(),n);
//          nodeStack.add(n);
//        }
//      }

            if (currentNode.isSolved()) {
                solved = true;
                continue;
            }
            for (Node n:nodes){
                if (!nodeHashMap.containsKey(n.hashCode())) {
                    nodeHashMap.put(n.hashCode(),n);
//          nodeStack.add(n);
                    nodeQueue.add(n);
                }
            }
//      if (nodeStack.empty()){
            if (nodeQueue.isEmpty()){
                System.out.println("Cannot Find Solution");
            }



        }
        System.out.println("solution found");
        while (currentNode.prev!=null){
            System.out.println(Arrays.deepToString(currentNode.getState()));
            currentNode=currentNode.prev;
        }
        System.out.println(Arrays.deepToString(currentNode.getState()));

    }




}
