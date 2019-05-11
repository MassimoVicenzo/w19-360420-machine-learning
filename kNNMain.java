import java.util.List;
import java.io.FileNotFoundException;
import java.util.Arrays;



public class kNNMain{

public static final double testfrac = 0.2;
public static final double trainfrac = 0.8;

  public static void main(String... args) throws FileNotFoundException{

    // TASK 1: Use command line arguments to point DataSet.readDataSet method to
    // the desired file. Choose a given DataPoint, and print its features and label


    List<DataPoint> List = DataSet.readDataSet(args[0]);

/*
    DataPoint DP = List.get(5);
    double[] x = DP.getX();
    System.out.println(DP.getLabel());
    System.out.println(Arrays.toString(x));
*/

    //TASK 2:Use the DataSet class to split the fullDataSet into Training and Held Out Test Dataset

    List<DataPoint> test = DataSet.getTestSet(List, testfrac);
    List<DataPoint> train = DataSet.getTrainingSet(List, trainfrac);


    // TASK 4: write a new method in DataSet.java which takes as arguments to DataPoint objects,
    // and returns the Euclidean distance between those two points (as a double)

//  System.out.print(DataSet.distanceEuclid(test.get(1), test.get(0)));  // TEST: prints distance between two values

    // TASK 5: Use the KNNClassifier class to determine the k nearest neighbors to a given DataPoint,
    // and make a print a predicted target label

    KNNClassifier k = new KNNClassifier(3);

/*
    String prediction = k.predict(List, DP);
    System.out.println(prediction);
*/

    // TASK 6: loop over the datapoints in the held out test set, and make predictions for Each
    // point based on nearest neighbors in training set. Calculate accuracy of model.




  }

  public static double mean(double[] arr){
    /*
    Method that takes as an argument an array of doubles
    Returns: average of the elements of array, as a double
    */
    double sum = 0.0;

    for (double a : arr){
      sum += a;
    }
    return (double)sum/arr.length;
  }

  public static double standardDeviation(double[] arr){
    /*
    Method that takes as an argument an array of doubles
    Returns: standard deviation of the elements of array, as a double
    Dependencies: requires the *mean* method written above
    */
    double avg = mean(arr);
    double sum = 0.0;
    for (double a : arr){
      sum += Math.pow(a-avg,2);
    }
    return (double)sum/arr.length;
  }

}
