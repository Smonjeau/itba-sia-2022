public class Layer {
    private Neuron[] neurons;
    private int layerSize;

    public Layer(int layerSize, int prevLayerSize) {
        this.layerSize = layerSize;

        neurons = new Neuron[layerSize];

        for (int i = 0; i < neurons.length ; i++) {
            neurons[i] = new Neuron(prevLayerSize);
        }

    }

    public Neuron[] getNeurons() {
        return neurons;
    }

    public int getLayerSize() {
        return layerSize;
    }
}
