public class Response {
    private long duration = 0;
    public boolean solved = false;
    public int frontierNodes = 1;
    public int exploredNodes = 0;
    private Node node=null;

    public Response() {}


    public Response(Node finalNode, boolean solved, int frontierNodes, int exploredNodes) {
        this.solved = solved;
        this.frontierNodes = frontierNodes;
        this.exploredNodes = exploredNodes;
        this.node = finalNode;
    }

    public Response(boolean solved) {
        this.solved = solved;
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

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getDurationInMillis() {
        return this.duration / 1000000;
    }

    @Override
    public String toString() {
        return "Response{" +
                "duration=" + duration +
                ", solved=" + solved +
                ", frontierNodes=" + frontierNodes +
                ", exploredNodes=" + exploredNodes +
                ", node=" + node +
                '}';
    }
}
