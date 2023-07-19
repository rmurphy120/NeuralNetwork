public class OutputLayer extends Layer
{
    private Node[] nodes;   //Layer of Nodes

    public OutputLayer(int prevLayerLength, int length, int nextLayerLength)
    {
        super(length);

        nodes = getNodes();
        for (int i = 0; i < length; i++)
        {
            nodes[i] = new OutputNode(prevLayerLength, nextLayerLength);
        }
    }

    public double[] calcCosts(int[] expectedOutput)     //Calculates all the costs in the layer if this is the output layer
    {
        if(nodes.length != expectedOutput.length)
        {
            System.out.println("Error: expectedOutputs is not of the correct length");
            return null;
        }

        double[] cost = new double[nodes.length];

        for(int i = 0; i < nodes.length; i++)
        {
            OutputNode out = (OutputNode)nodes[i];
            cost[i] = out.c(expectedOutput[i]);
        }
        return cost;
    }
    public double[] calcPDerivOfCRespToAForLastLayer()      //Calculates the partial derivatives of C with respect to A of the output nodes
    {
        double[] derivs = new double[nodes.length];

        for(int i = 0; i < nodes.length; i++)
        {
            OutputNode out = (OutputNode)nodes[i];
            derivs[i] = out.calcPDerivOfCRespToA();
        }

        return derivs;
    }
}
