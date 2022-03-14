public class Response {
    private boolean solved = false;
    private int frontierNodes = 1;
    private int exploredNodes = 0;
    private int height=-1;

    public Response(boolean solved, int frontierNodes, int exploredNodes, int height) {
        this.solved = solved;
        this.frontierNodes = frontierNodes;
        this.exploredNodes = exploredNodes;
        this.height = height;
    }

    public boolean isSolved() {
        return solved;
    }

    public void setSolved(boolean solved) {
        this.solved = solved;
    }

    public int getFrontierNodes() {
        return frontierNodes;
    }

    public void setFrontierNodes(int frontierNodes) {
        this.frontierNodes = frontierNodes;
    }

    public int getExploredNodes() {
        return exploredNodes;
    }

    public void setExploredNodes(int exploredNodes) {
        this.exploredNodes = exploredNodes;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
