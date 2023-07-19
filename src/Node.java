public class Node
{
    private double[] weights;  //Weights connecting this node to all nodes prior
    private double bias;    //Bias of node
    private double output;  //Activated output (a) of this node. Range = (0,1)
    private double pDerivOfCRespToA;    //The partial derivative of the cost with respect to the activated output of this node

    public Node(int prevLayerLength, int nextLayerLength)   //Initializes nodes using algorithm
    {
        instantiateNormal(prevLayerLength);
    }

    public double[] getWeights()
    {
        return weights;
    }
    public double getBias()
    {
        return bias;
    }
    public double getOutput()
    {
        return output;
    }

    public void setPDerivOfCRespToA(double pDerivOfCRespToA)
    {
        this.pDerivOfCRespToA = pDerivOfCRespToA;
    }

    private void instantiateNormal(int prevLayerLength)    //Normal initialization of weights with a mean of 0 and a SD of 1. Sets biases to 0
    {
        final double sd = 1;

        weights = new double[prevLayerLength];
        for(int i = 0; i < weights.length; i++)
        {
            weights[i] = (Math.random() < .5 ? -1 : 1) * sd * Math.sqrt(2 * Math.log(1 / Math.random()));
        }

        bias = 0;
    }
    private void instantiateUniformXavier(int prevLayerLength, int nextLayerLength)
    {
        weights = new double[prevLayerLength];
        for(int i = 0; i < weights.length; i++)
        {
            double bound = Math.sqrt(6/(prevLayerLength + nextLayerLength));
            weights[i] = 2 * bound * Math.random() - bound;
        }

        bias = 0;
    }

    public double a(double[] prevLayerOutput)   //Activated output
    {
        if(weights.length != prevLayerOutput.length)
        {
            System.out.println("Error: weights are not the same length as previous layer");
            return -1;
        }

        output = sigmoid(z(prevLayerOutput));
        return output;
    }
    private double z(double[] prevLayerOutput)  //Weighted output
    {
        double sum = 0;

        for(int i = 0; i < prevLayerOutput.length; i++)
        {
            sum += prevLayerOutput[i] * weights[i] + bias;
        }

        return sum;
    }

    private static double sigmoid(double x)     //Activation function. Range = (0,1)
    {
        return 1/(1+Math.exp(-x));
    }
    private static double derivOfSigmoid(double x)     //Derivative of activation function
    {
        return sigmoid(x)*(1-sigmoid(x));
    }

    public void weightsBackProp(double alpha, double[] prevLayerOutput)     //Calculates the new weight based of the gradient
    {
        if(weights.length != prevLayerOutput.length)
        {
            System.out.println("Error: weights are not the same length as previous layer");
            return;
        }

        for(int i = 0; i < prevLayerOutput.length; i++)
        {
            weights[i] -= alpha * prevLayerOutput[i] * derivOfSigmoid(output) * pDerivOfCRespToA;
        }
    }
    public void biasBackProp(double alpha)  //Calculates the new bias based of the gradient
    {
        bias -= alpha * derivOfSigmoid(output) * pDerivOfCRespToA;
    }
    public double calcPDerivOfCRespToAForPrevLayer(int indexOfPrevLayerNode)    //Helps to find the pDerivOfCRespToA for the previous layer
    {
        if(weights.length <= indexOfPrevLayerNode)
        {
            System.out.println("Error: indexOfPrevLayerNode is too high");
            return 0;
        }

        return weights[indexOfPrevLayerNode] * derivOfSigmoid(output) * pDerivOfCRespToA;
    }
}
