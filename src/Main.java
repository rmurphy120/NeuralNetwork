import java.io.IOException;

public class Main
{
    public static void main(String[] args) throws IOException {
        int[] networkSizes = {784, 700, 600, 10};
        Network net = new Network(networkSizes, .0001);

        //net.printNetwork();     //Prints network

        trainNetwork(net, 10000);

        //net.printNetwork();

        testNetwork(net, 2000);
    }

    private static void trainNetwork(Network net, int n) throws IOException     //Trains the network n times, max of 60,000
    {
        MnistMatrix[] mnistMatrix = new MnistDataReader().readData("C:\\Users\\rynom\\Downloads\\train-images.idx3-ubyte", "C:\\Users\\rynom\\Downloads\\train-labels.idx1-ubyte");
        double cost = 0;

        if(n > mnistMatrix.length)
        {
            System.out.println("Error: n is larger than the sample size");
            return;
        }

        for(int i = 0; i < n; i++)
        {
            net.feedForward(mnistMatrix[i].getDataIn1DArray());
            cost = net.calcCostOfNetwork(mnistMatrix[i].getExpectedOutput());
            net.backpropagation();
            if(i % (n/4) == 0)
                System.out.println((double)i/n*100 + "% of the way there");
        }
        System.out.println("Final cost: " + cost);
    }
    private static void testNetwork(Network net, int n) throws IOException     //Tests the network n times, max of 5,000
    {
        MnistMatrix[] mnistMatrix = new MnistDataReader().readData("C:\\Users\\rynom\\Downloads\\t10k-images.idx3-ubyte", "C:\\Users\\rynom\\Downloads\\t10k-labels.idx1-ubyte");
        int output;
        double percentCorrect = 0;

        if(n > mnistMatrix.length)
        {
            System.out.println("Error: n is larger than the sample size");
            return;
        }

        int r = (int)(Math.random() * 10);

        for(int i = 0; i < n; i++)
        {
            output = net.feedForward(mnistMatrix[i].getDataIn1DArray());
            if(output == mnistMatrix[i].getLabel())
                percentCorrect++;
            if(i % (n/10) == r)     //Pulls randomized outputs to display
                mnistMatrix[i].print(output);
        }

        System.out.println(percentCorrect/n);
    }
}