public class Manhattan implements Heuristic {

    @Override
    public double heuristic(PuzzleState puzzleState) {
        int [][] board = puzzleState.getBoard();

        double manhattanDistance = 0;


        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                int aux = board[i][j];
                if(aux!=0)
                    manhattanDistance += Math.abs(getExpectedRow(aux) - i) + Math.abs(getExpectedCol(aux)-j);

            }
        }

        return manhattanDistance;
    }



    private  int getExpectedRow(int num){

        if(num % 3 == 0)
            return num/3 - 1;
        return num / 3;
    }

    private  int getExpectedCol(int num){
        return (num  - 1) % 3;
    }

}
