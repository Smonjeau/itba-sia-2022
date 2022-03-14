public class Response {
    private boolean solved = false;
    private int frontierNodes = 1;
    private int exploredNodes = 0;
    private Node node=null;

    public Response(boolean solved, int frontierNodes, int exploredNodes,Node node) {
        this.solved = solved;
        this.frontierNodes = frontierNodes;
        this.exploredNodes = exploredNodes;
        this.node = node;
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

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
}
