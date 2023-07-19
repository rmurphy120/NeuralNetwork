//This class consists of random coordinates on a plane and a classification whether that point is above or below a graph
public class SimpleInput
{
    private boolean isAbove;    //Checks if point is above the graph at x
    private double x;   //Random input
    private double y;   //Random input

    public SimpleInput()
    {
        x = Math.random() * 20 - 10;    //Domain and range restricted to [-10, 10)
        y = Math.random() * 20 - 10;
        isAbove = y > cubicFunction();
    }
    private double cubicFunction()      //Sample training function
    {
        return Math.pow(x-3,3)/8 + Math.pow(x-3,2) - 5;
    }
    private double sineFunction()       //Sample training function
    {
        return 8 * Math.sin(x);
    }

    public int[] getIsAbove()   //Returns boolean isAbove in the format of an array of ints which becomes the expected output of the network
    {
        return new int[]{isAbove ? 1 : 0, isAbove ? 0 : 1};
    }
    public double[] getXAndY()   //Returns x and y in the format of an array
    {
        return new double[]{x,y};
    }
}
