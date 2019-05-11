import java.util.List;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.ArrayList;


public class kNNMain{

public static final double testfrac = 0.3;
public static final double trainfrac = 0.7;


  public static void main(String... args) throws FileNotFoundException{


    List<DataPoint> List = DataSet.readDataSet(args[0]);

/*
    DataPoint DP = List.get(5);
    double[] x = DP.getX();
    System.out.println(DP.getLabel());
    System.out.println(Arrays.toString(x));
*/

    KNNClassifier k = new KNNClassifier(3);

// -------------------------------------------------------- //

    double[] sucRate = new double[1000];
    double[] precision = new double[1000];
    double[] recall = new double[1000];

// -------------------------------------------------------- //

    for (int n = 0;n<1000;n++) {  //test kNN multiple times

// -------------------------------------- //

      for (int j = 0; j<List.size();j++) {
        DataPoint DPreset = List.get(j);          // removes the test or train label from the data,
        DPreset.setTestOrTrain("");               // otherwise it interferes with choosing new train/test data
      }

// ------------------------------------- //

      List<DataPoint> test = DataSet.getTestSet(List, testfrac);
      List<DataPoint> train = DataSet.getTrainingSet(List, trainfrac);
                                                                          // Making the test/training sets and creating
      double[] sucRatePoints = new double[test.size()];                   //places to store the points
      List<Double> precisionPoints = new ArrayList<Double>();
      List<Double> recallPoints = new ArrayList<Double>();

// ----------------------------------- //

      for (int i = 0; i<test.size(); i++) {
        sucRatePoints[i] = 0;
      }                                                // Emptying the points arrays/lists
      precisionPoints.clear();
      recallPoints.clear();

// ---------------------------------- //

      for (int i = 0; i<test.size(); i++) {           //The actual prediction test
        DataPoint DP = test.get(i);

        String prediction = k.predict(train, DP);
        if (prediction.equals(DP.getLabel())) {     // if the prediciton and the actual are the same
          sucRatePoints[i] = 1;
          if (prediction.equals("malignant")) {         // if they are the same and they are malignant
            precisionPoints.add(1.0);
            recallPoints.add(1.0);
          }
        } else {                                    // if the prediction and the actual are different
          sucRatePoints[i] = 0;
          if (prediction.equals("malignant")) {         // if we predicted malignant
            precisionPoints.add(0.0);
          } else {                                      // if we predicted benign
            recallPoints.add(0.0);
          }
        }
      }

// ------------------------------ //

      sucRate[n] = mean(sucRatePoints) * 100;
      precision[n] = mean(precisionPoints) * 100;    // getting the average rates in %
      recall[n] = mean(recallPoints) * 100;

    }
    System.out.println("Mean: " + mean(sucRate));
    System.out.println("SD: " + standardDeviation(sucRate));
    System.out.println("");
    System.out.println("Precision: " + mean(precision));
    System.out.println("SD: " + standardDeviation(precision));    //calculating and printing the Means and SDs of
    System.out.println("");                                       // success rate, precision and recall
    System.out.println("Recall: " + mean(recall));
    System.out.println("SD: " + standardDeviation(recall));

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

  public static double mean(List<Double> list){

    double sum = 0.0;

    for (double a : list){                              //I added this due to recall and precision points' being lists and not arrays
      sum += a;
    }
    return (double)sum/list.size();
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

  public static double standardDeviation(List<Double> list){

    double avg = mean(list);
    double sum = 0.0;
    for (double a : list){
      sum += Math.pow(a-avg,2);
    }
    return (double)sum/list.size();
  }

}
