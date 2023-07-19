public class Layer
{
    private Node[] nodes;   //Layer of nodes

    public Layer(int prevLayerLength, int length, int nextLayerLength)    //Initializes nodeLayer and every node in it
    {
        nodes = new Node[length];

        for (int i = 0; i < nodes.length; i++)
        {
            nodes[i] = new Node(prevLayerLength, nextLayerLength);
        }
    }
    public Layer(int length)
    {
        nodes = new Node[length];
    }   //Used for initializing the output layer

    public Node[] getNodes()
    {
        return nodes;
    }
    public double[] getOutputOfLayer()      //Returns all activated outputs of the layer
    {
        double[] outputOfLayer = new double[nodes.length];

        for(int i = 0; i < outputOfLayer.length; i++)
        {
            outputOfLayer[i] = nodes[i].getOutput();
        }

        return outputOfLayer;
    }

    public double[] layerOutput(double[] prevLayerOutput)   //Calculates and returns all the activated outputs of the layer
    {
        double[] currLayerOutput = new double[nodes.length];

        for(int i = 0; i < nodes.length; i++)
        {
            currLayerOutput[i] = nodes[i].a(prevLayerOutput);
        }

        return currLayerOutput;
    }

    public void layerBackProp(double alpha, double[] derivsOfThisLayer, double[] prevLayerOutput)   //Completes backpropagation for this layer
    {
        if(nodes.length != derivsOfThisLayer.length)
        {
            System.out.println("Error: derivsOfThisLayer is not of the correct length");
            return;
        }

        for(int i = 0; i < nodes.length; i++)
        {
            nodes[i].setPDerivOfCRespToA(derivsOfThisLayer[i]);
            nodes[i].biasBackProp(alpha);
            nodes[i].weightsBackProp(alpha, prevLayerOutput);
        }
    }
    public double[] calcSumOfPDerivsOfCRespToAForPrevLayer(int prevLayerLength)     //Note: "prev layer" refers to the layer before this in the network, not the layer before this in the sequence
    {
        double[] derivsForPrevLayer = new double[prevLayerLength];

        for(int i = 0; i < derivsForPrevLayer.length; i++)
        {
            for(Node each : nodes)
            {
                derivsForPrevLayer[i] += each.calcPDerivOfCRespToAForPrevLayer(i);
            }
        }

        return derivsForPrevLayer;
    }
}
