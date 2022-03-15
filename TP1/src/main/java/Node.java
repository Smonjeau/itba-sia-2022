import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Node {


    public enum Direction{UP,RIGHT,DOWN,LEFT}
    Node prev;

    private int height;
    private final PuzzleState state;
    private double orderValue;



    public Node(Node prev, PuzzleState state, int height) {
        this.prev = prev;
        this.height=height;
        this.state  = state;
    }

    public List<Node> MakeStep(){
        List<Node>nodes=new ArrayList<>();
        for (Direction dir:Direction.values()) {
            Node aux=MakeStepDir(dir);
            if (aux!=null)
                nodes.add(aux);
        }
        return nodes;
    }

    private Node MakeStepDir (Direction dir){
        Pair emptySpacePosition=findEmptySpace();
        if (isValidMove(dir,emptySpacePosition)){
            int[][] newState=new int[3][3];
            for(int i = 0; i < newState.length; i++)
                newState[i] = state.getRowAt(i).clone();

            swap(dir,emptySpacePosition,newState);

//      children[dir.ordinal()]=new Node(this,newState);
            return new Node(this,new PuzzleState(newState),this.height+1);
        }
        return null;



    }


    public boolean isSolved(){
        return state.isGoalState();
    }


    private Pair findEmptySpace(){
        int[][] board = state.getBoard();
        for (int i = 0; i < board.length ; i++) {
            for (int j = 0; j < board[i].length; j++){
                if (board[i][j]==0)
                    return new Pair(i,j);
            }
        }

        throw new RuntimeException("no empty space");
    }

    private void swap(Direction dir,Pair emptySpacePosition,int[][]matrix){
        int aux;
        switch (dir) {
            case UP:
                aux = matrix[emptySpacePosition.getKey() - 1][emptySpacePosition.getValue()];
                matrix[emptySpacePosition.getKey() - 1][emptySpacePosition.getValue()] = 0;
                matrix[emptySpacePosition.getKey()][emptySpacePosition.getValue()] = aux;
                break;
            case DOWN:
                aux = matrix[emptySpacePosition.getKey() + 1][emptySpacePosition.getValue()];
                matrix[emptySpacePosition.getKey() + 1][emptySpacePosition.getValue()] = 0;
                matrix[emptySpacePosition.getKey()][emptySpacePosition.getValue()] = aux;
                break;
            case RIGHT:
                aux = matrix[emptySpacePosition.getKey()][emptySpacePosition.getValue() + 1];
                matrix[emptySpacePosition.getKey()][emptySpacePosition.getValue() + 1] = 0;
                matrix[emptySpacePosition.getKey()][emptySpacePosition.getValue()] = aux;
                break;
            case LEFT:
                aux = matrix[emptySpacePosition.getKey()][emptySpacePosition.getValue() - 1];
                matrix[emptySpacePosition.getKey()][emptySpacePosition.getValue() - 1] = 0;
                matrix[emptySpacePosition.getKey()][emptySpacePosition.getValue()] = aux;
                break;
            default:
                throw new RuntimeException("Not A Direction");
        }
    }


    private boolean isValidMove(Direction dir,Pair emptySpacePosition){

        switch (dir){
            case UP:
                if (emptySpacePosition.getKey()!=0)
                    return true;
                break;
            case DOWN:
                if (emptySpacePosition.getKey()!=2)
                    return true;
                break;
            case RIGHT:
                if (emptySpacePosition.getValue()!=2)
                    return true;
                break;
            case LEFT:
                if (emptySpacePosition.getValue()!=0)
                    return true;
                break;
            default:
                return false;
        }
        return false;
    }

    public String stateToString() {
        StringBuilder stringBuilder = new StringBuilder();
        int[][] board = state.getBoard();
        for (int[] ints : board) {
            stringBuilder.append("| ");
            for (int anInt : ints) {
                stringBuilder.append(anInt).append(" | ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public int hashCode() {
        return state.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        return node.getState().equals(state);
    }

    public Node getPrev() {
        return prev;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public PuzzleState getState() {
        return state;
    }

    public void setOrderValue(double value) {
        orderValue = value;
    }

    public double getOrderValue() {
        return orderValue;
    }

    private static class Pair {
      private final Integer key;
      private final Integer value;

      public Pair(Integer key, Integer value) {
        this.key = key;
        this.value = value;
      }

      public Integer getKey() {
        return key;
      }

      public Integer getValue() {
        return value;
      }
    }
}
