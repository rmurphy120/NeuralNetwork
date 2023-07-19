import java.text.*;

public class Network
{
    private Layer[] layers;     //Network of layers
    private final int inputLength;  //Expected input length
    private final double alpha;     //The training rate of the network
    private double[] input;     //Stores the input for use in backpropagation

    public Network(int[] layerLengths, double trainingRate)  //Creates a new network and initializes every node in it
    {
        layers = new Layer[layerLengths.length - 1];

        inputLength = layerLengths[0];

        for(int i = 0; i < layers.length - 1; i++)
        {
            layers[i] = new Layer(layerLengths[i], layerLengths[i+1], layerLengths[i+2]);
        }
        layers[layers.length - 1] = new OutputLayer(layerLengths[layerLengths.length - 2], layerLengths[layerLengths.length - 1], layerLengths[layerLengths.length - 1]);

        alpha = trainingRate;
    }

    public void printNetwork()      //Prints network in a formatted fashion
    {
        NumberFormat f = new DecimalFormat("#0.00");

        for(Layer layer : layers)
        {
            for(Node node : layer.getNodes())
            {
                System.out.print("{[");

                double[] weights = node.getWeights();
                for(int i = 0; i < weights.length; i++)
                {
                    System.out.print(f.format(weights[i]));

                    if(i+1 != weights.length)
                        System.out.print(",");
                }

                System.out.print("]," + f.format(node.getBias()) + "} ");
            }

            System.out.println();
        }
    }

    public int feedForward(double[] input)     //Computes the feed forward algorithm in its entirety
    {
        if(input.length != inputLength)
        {
            System.out.println("Error: Input is not of the right length");
            return -1;
        }

        this.input = input;

        double[] prevLayerOutput = input;
        double[] currLayerOutput;

        for(Layer each : layers)
        {
            currLayerOutput = each.layerOutput(prevLayerOutput);
            prevLayerOutput = currLayerOutput;
        }

        return simplifyOutput(prevLayerOutput);
    }
    private int simplifyOutput(double[] output)     //Code to translate the machine's output to what that represents
    {
        double maxOutput = output[0];
        int index = 0;

        for(int i = 1; i < output.length; i++)
        {
            if(output[i] > maxOutput)
            {
                maxOutput = output[i];
                index = i;
            }
        }

        return index;
    }

    public double calcCostOfNetwork(int[] expectedOutput)      //Calculates the cost of the function so backpropagation can commence
    {
        OutputLayer layer = (OutputLayer)layers[layers.length - 1];
        double[] costArr = layer.calcCosts(expectedOutput);
        double cost = 0;

        for(double each : costArr)
            cost += each;

        return cost;
    }

    public void backpropagation()   //Computes the backpropagation algorithm in its entirety
    {
        OutputLayer layer = (OutputLayer)layers[layers.length - 1];
        layer.layerBackProp(alpha, layer.calcPDerivOfCRespToAForLastLayer(), layers[layers.length - 2].getOutputOfLayer());

        for(int i = layers.length - 2; i >= 0; i--)
        {
            if(i != 0)
                layers[i].layerBackProp(alpha, layers[i+1].calcSumOfPDerivsOfCRespToAForPrevLayer(layers[i].getOutputOfLayer().length), layers[i-1].getOutputOfLayer());
            else
                layers[i].layerBackProp(alpha, layers[i+1].calcSumOfPDerivsOfCRespToAForPrevLayer(layers[i].getOutputOfLayer().length), input);
        }
    }
}
