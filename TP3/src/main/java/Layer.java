import java.util.ArrayList;
import java.util.List;

public class Layer {
    private Neuron2[] neurons;
    private int layerSize;

    public Layer(int layerSize,int prevLayerSize) {
        this.layerSize = layerSize;

        neurons = new Neuron2 [prevLayerSize];

        for (int i = 0; i < neurons.length ; i++) {
            neurons[i] = new Neuron2(prevLayerSize);
        }

    }

    public Neuron2[] getNeurons() {
        return neurons;
    }

    public int getLayerSize() {
        return layerSize;
    }
}
