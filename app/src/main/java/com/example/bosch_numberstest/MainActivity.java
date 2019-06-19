package com.example.bosch_numberstest;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView tvNumberList;
    ProgressDialog progressDialog;
    CalculatorViewModel calculatorViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvNumberList = findViewById(R.id.tvNumberList);
        progressDialog = ProgressDialog.show(MainActivity.this, "",
                "Calculating. Please wait...", true);
        progressDialog.show();

        calculatorViewModel = ViewModelProviders.of(this).get(CalculatorViewModel.class);
        calculatorViewModel.calculationFinished().observe(this, finished -> {
                    if (finished == true) {
                        Toast.makeText(this, "" + finished.toString(), Toast.LENGTH_SHORT).show();
                        fulfillNumberList();
                    }
                }
        );

        calculatorViewModel.calculateDividabillity();
    }

    public void fulfillNumberList() {
        List<String> list = calculatorViewModel.getDividabillityList();

        for (int i = 0; i < list.size(); i++) {
            tvNumberList.append(list.get(i));
        }

        progressDialog.dismiss();
    }
}
