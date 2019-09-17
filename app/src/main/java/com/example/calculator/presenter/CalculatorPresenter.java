package com.example.calculator.presenter;

import android.util.Log;

import com.example.calculator.model.Calculator;
import com.example.calculator.view.CalculatorView;

public class CalculatorPresenter implements Presenter {

    private static String TAG = CalculatorPresenter.class.getName();     // For console information


    private CalculatorView view;
    private Calculator model;


    public CalculatorPresenter(CalculatorView view) {
        this.view = view;
        this.model = new Calculator();
    }

    public void onCreate() {
        model = new Calculator();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onDestroy() {

    }

    public void onButtonClicked(String tag){
        String ValidChars = "CSBD/*+-.=0123456789";
        int index = ValidChars.indexOf(tag);
        if(index == -1){    // NOT A VALID CHAR
            Log.i(TAG, "Incorect Char Recieved: " + tag);
        }
        else
        {
            model.receiveInformation(tag);
            sendCalculation(getCalculation());
        }
    }

    private String getCalculation(){
        return model.getString();
    }

    private void sendCalculation(String calculation){
        view.setCalculatorText(calculation);
    }
}
