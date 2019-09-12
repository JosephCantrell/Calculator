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
        String ValidChars = "CSB/*+-.=0123456789";
        int index = ValidChars.indexOf(tag);
        if(index == -1){    // NOT A VALID CHAR
            Log.i(TAG, "Incorect Char Recieved: " + tag);
        }
        else
        {
            model.recieveInformation(tag);
            sendCalculation(getCalculation());
        }
    }

    public String getCalculation(){
        return model.getString();
    }

    public void sendCalculation(String calculation){
        view.setCalculatorText(calculation);
    }
}
