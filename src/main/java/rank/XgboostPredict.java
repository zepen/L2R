package rank;

import ml.dmlc.xgboost4j.java.Booster;
import ml.dmlc.xgboost4j.java.DMatrix;
import ml.dmlc.xgboost4j.java.XGBoost;
import ml.dmlc.xgboost4j.java.XGBoostError;

public class XgboostPredict {

    public static void main(String [] args) throws XGBoostError {
        float[] data = new float[] {5.2f, 3.5f, 1.5f, 0.2f};
        int nrow = 1;
        int ncol = 4;
        float missing = 0.0f;
        DMatrix dmat = new DMatrix(data, nrow, ncol, missing);
        Booster booster = XGBoost.loadModel("src/main/resources/model.bin");
        float[][] predicts = booster.predict(dmat);
        for (float[] array: predicts){
            for (float values: array) {
                System.out.print(values + " ");
            }
            System.out.println();
        }
    }

}
