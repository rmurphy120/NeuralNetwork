//Code by Turkdogan Tasdelen. Downloaded from GitHub. Some adjustments were made to allow this code to fit mine
public class MnistMatrix {

    private int [][] data;

    private int nRows;
    private int nCols;

    private int label;

    public MnistMatrix(int nRows, int nCols) {
        this.nRows = nRows;
        this.nCols = nCols;

        data = new int[nRows][nCols];
    }

    public double[] getDataIn1DArray()     //Returns the data in a one dimensional array
    {
        double[] arr = new double[data.length * data[0].length];

        for(int i = 0; i < data.length; i++)
            for(int j = 0; j < data[i].length; j++)
                arr[i*data[i].length + j] = data[i][j];
        return arr;
    }
    public int getValue(int r, int c) {
        return data[r][c];
    }

    public void setValue(int row, int col, int value) {
        data[row][col] = value;
    }

    public int getLabel() {
        return label;
    }
    public int[] getExpectedOutput()        //Returns the label in array format
    {
        int[] expectedOutput = new int[10];

        for(int i = 0; i < expectedOutput.length; i++)
        {
            if(label == i)
                expectedOutput[i] = 1;
            else
                expectedOutput[i] = 0;
        }
        return expectedOutput;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public int getNumberOfRows() {
        return nRows;
    }

    public int getNumberOfColumns() {
        return nCols;
    }

    public void print(int output)       //Prints the data point
    {
        for (int i = 0; i < nRows; i++ )
        {
            for (int j = 0; j < nCols; j++)
            {
                System.out.print(format(data[i][j]) + " ");
            }
            System.out.println();
        }
        System.out.println("Label: " + label);
        System.out.println("Guess: " + output);
    }
    private char format(int value)      //Formats the data using ascii art
    {
        if(value == 0 )
            return ' ';
        if(value < 60)
            return '-';
        if(value < 120)
            return '*';
        if(value < 180)
            return 'I';
        return 'W';
        //-*UW
    }

}