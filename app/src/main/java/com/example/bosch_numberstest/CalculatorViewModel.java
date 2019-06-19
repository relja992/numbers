package com.example.bosch_numberstest;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import java.util.LinkedList;
import java.util.List;

public class CalculatorViewModel extends ViewModel {

    public static final int SEVEN = 7;
    public static final int THREE = 3;
    public static final int DIVIDABLE_WITH_3 = 0;
    public static final int DIVIDABLE_WITH_7 = 1;
    public static final int DIVIDABLE_WITH_3_AND_7 = 2;
    public static final int NOT_DIVIDABLE_WITH_3_AND_7 = 3;
    public static final int DEFAULT_DIVIDABILLITY = NOT_DIVIDABLE_WITH_3_AND_7;

    private static int dividabillity = DEFAULT_DIVIDABILLITY;
    public static List<String> dividabillityList = new LinkedList<>();
    private static MutableLiveData<Boolean> calculationFinished = new MutableLiveData<>();

    public CalculatorViewModel() {
        calculationFinished.setValue(false);
    }

    public List<String> getDividabillityList() {
        return dividabillityList;
    }
    public static void setCalculationFinished(boolean value) {
        calculationFinished.setValue(value);
    }

    public LiveData<Boolean> calculationFinished() {
        return calculationFinished;
    }

    public void calculateDividabillity () {
        new DividabillityCalculatorAsyncTask().execute();
    }

    public static class DividabillityCalculatorAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            for (int number = 1; number <= 10000; number++) {
                dividabillity = calculateDividabillityForParticullarNumber(number);

                switch(dividabillity) {
                    case DIVIDABLE_WITH_3:
                        dividabillityList.add("my\n");
                        break;
                    case DIVIDABLE_WITH_7:
                        dividabillityList.add("SPIN\n");
                        break;
                    case DIVIDABLE_WITH_3_AND_7:
                        dividabillityList.add("mySPIN\n");
                        break;
                    case NOT_DIVIDABLE_WITH_3_AND_7:
                        dividabillityList.add(number + "\n");
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setCalculationFinished(true);
        }

        public int calculateDividabillityForParticullarNumber(int number) {
            int numberDividability;

            if(number % THREE == 0 && number % SEVEN == 0)
                numberDividability = DIVIDABLE_WITH_3_AND_7;
            else if (number % THREE == 0)
                numberDividability =  DIVIDABLE_WITH_3;
            else if (number % SEVEN == 0)
                numberDividability =  DIVIDABLE_WITH_7;
            else
                numberDividability = NOT_DIVIDABLE_WITH_3_AND_7;

            return numberDividability;
        }
    }
}
