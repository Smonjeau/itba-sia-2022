import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Node {


    public enum Direction{UP,RIGHT,DOWN,LEFT}
    Node prev;
//    private final Node[] children = new Node[4];

    private int height;
    private final int[][] state;

    private static final int [][] solvedState={
            {1,2,3},
            {4,5,6},
            {7,8,0}
    };



    public Node(Node prev, int[][] state,int height) {
        this.prev = prev;
        this.state = state;
        this.height=height;
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
        Pair<Integer,Integer> emptySpacePosition=findEmptySpace();
        if (isValidMove(dir,emptySpacePosition)){
            int[][] newState=new int[3][3];
            for(int i = 0; i < newState.length; i++)
                newState[i] = state[i].clone();

            swap(dir,emptySpacePosition,newState);

//      children[dir.ordinal()]=new Node(this,newState);
            return new Node(this,newState,this.height+1);
        }
        return null;



    }

    public boolean isSolved(){
        return Arrays.deepEquals(state,solvedState);
    }


    private Pair<Integer,Integer> findEmptySpace(){
        for (int i = 0; i <state.length ; i++) {
            for (int j = 0; j < state[i].length; j++){
                if (state[i][j]==0)
                    return new Pair(i,j);
            }
        }
        throw new RuntimeException("no empty space");
    }

    private void swap(Direction dir,Pair<Integer,Integer> emptySpacePosition,int[][]matrix){
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


    private boolean isValidMove(Direction dir,Pair<Integer,Integer> emptySpacePosition){

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

    public int[][] getState() {
        return state;
    }

    @Override
    public int hashCode() {
        int hash=0;
        for (int[] ints : state) {
            for (int anInt : ints) {
                hash *= 10;
                hash += anInt;
            }
        }
        return hash;
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
}
