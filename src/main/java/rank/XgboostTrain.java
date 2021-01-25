package rank;

import ml.dmlc.xgboost4j.java.Booster;
import ml.dmlc.xgboost4j.java.DMatrix;
import ml.dmlc.xgboost4j.java.XGBoost;
import ml.dmlc.xgboost4j.java.XGBoostError;

import java.util.HashMap;
import java.util.Map;


public class XgboostTrain {

    private static DMatrix trainMat = null;
    private static DMatrix testMat = null;

    public static void main(String [] args) throws XGBoostError {

        try {
            trainMat = new DMatrix("src/main/resources/train.txt");
        } catch (XGBoostError xgBoostError) {
            xgBoostError.printStackTrace();
        }
        try {
            testMat = new DMatrix("src/main/resources/test.txt");
        } catch (XGBoostError xgBoostError) {
            xgBoostError.printStackTrace();
        }

//        long[] rowHeaders = new long[] {0,2,4,7};
//        float[] data = new float[] {1f,2f,4f,3f,3f,1f,2f};
//        int[] colIndex = new int[] {0,2,0,3,0,1,2};
//        int numColumn = 4;
//        final DMatrix trainMat = new DMatrix(rowHeaders, colIndex, data, DMatrix.SparseType.CSR, numColumn);
//        final DMatrix testMat = new DMatrix(rowHeaders, colIndex, data, DMatrix.SparseType.CSR, numColumn);

        Map<String, Object> params = new HashMap<String, Object>() {
            {
                put("eta", 1.0);
                put("max_depth", 3);
                put("objective", "binary:logistic");
                put("eval_metric", "logloss");
            }
        };

        Map<String, DMatrix> watches = new HashMap<String, DMatrix>() {
            {
                put("train",trainMat);
                put("test", testMat);
            }
        };
        int nround = 10;
        try {
            Booster booster = XGBoost.train(trainMat, params, nround, watches, null, null);
            booster.saveModel("src/main/resources/model.bin");
        } catch (XGBoostError xgBoostError) {
            xgBoostError.printStackTrace();
        }
    }

}
