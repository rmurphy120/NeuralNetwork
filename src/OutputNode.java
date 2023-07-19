public class OutputNode extends Node
{
    private int expectedOutput;     //Expected output of this node

    public OutputNode(int prevLayerLength, int nextLayerLength)
    {
        super(prevLayerLength, nextLayerLength);
    }

    public double c(int expectedOutput)    //Calculates the cost of the output of this node
    {
        this.expectedOutput = expectedOutput;
        return Math.pow(super.getOutput() - expectedOutput, 2);
    }
    public double calcPDerivOfCRespToA()      //Calculates the partial derivative of C with respect to A of this output node
    {
        return 2 * (super.getOutput() - expectedOutput);
    }
}
