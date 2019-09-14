package com.example.calculator.model;

import android.util.Log;

public class Calculator {
    private String calculationString = "";
    private String Operators = "=C~B/*-+";
    private String Numbers = "0123456789.";

    private static String TAG = Calculator.class.getName();     // For console information


    public void recieveInformation(String tag){

        int index = Operators.indexOf(tag);

        if(tag.equals("=")){
            if(calculationString.contains("Infinity")) {
                Log.i(TAG, "User must clear");
                calculationString = "Infinity";
            }
            else if(calculationString.contains("NaN")){
                Log.i(TAG, "User must clear");
                calculationString = "NaN";
            }
            else
                equals();
        }
        else if(tag.equals("D")){        // Delete the last char
            delete();
        }
        else if(tag.equals("C")){
            clear();
        }
        else
        {
            calculationString = calculationString + tag;        // Add the sent tag to the current string of calculation
        }
    }

    public String getString()  { return calculationString; }

    void delete(){
        if(calculationString.length()>0){
            calculationString = calculationString.substring(0, calculationString.length()-1);   // Removes the last position
        }
    }

    boolean equals()
    {
        if(calculationString == "Infinity")
        {
            return false;
        }
        if(calculationString.length()< 3)
            return false;
        int arrayNum = 0;
        String numbers[];
        int signChange[];
        String operators = "";
        float result = 0;
        float endNumbers[];
        endNumbers = new float [calculationString.length()];
        numbers = new String [calculationString.length()]; // We will never have more numbers to calculate than the length of the string
        signChange = new int [calculationString.length()];
        for(int i = 0; i < calculationString.length();i++) {        // Initializing the numbers array with values of zero
            numbers[i] = "0";
            signChange[i] = 0;
            endNumbers[i] = 0;
        }


        for(int i = 0; i < calculationString.length();i++) {
            int index = Operators.indexOf(calculationString.charAt(i)); // If we have an operator in this position we are looking at

            if (index == -1 || index == 2) // if it is not an operator, add the number to the numbers array
            {
                if(index == 2) { // Sign change
                    signChange[arrayNum] = 1;
                }
                if(index == -1) {
                    numbers[arrayNum] = numbers[arrayNum] + calculationString.charAt(i);   // Adding to the numbers array
                }
            } else {
                operators = operators + Operators.charAt(index);
                arrayNum++;
            }
        }

        for(int i = 0; i < numbers.length; i++){
            endNumbers[i] = Float.valueOf(numbers[i]);
        }

        for(int i = 0; i < signChange.length; i++){
            if(signChange[i] == 1) {
                endNumbers[i] = -1* endNumbers[i];    // Flip the sign of the number
            }
        }


        for(int i = 0; i < operators.length(); i++){    // Combining the array list and operator list
            float first = 0;
            float second = 0;

            if(i == 0) {
                first = endNumbers[2 * i];
                second = endNumbers[(2 * i) + 1];
            }
            else
            {
                first = result;
                second = endNumbers[1+i];
            }


            if(operators.charAt(i) == '/'){ // Division
                result = first / second;
            }
            if(operators.charAt(i) == '*'){ // Multiply
                result = first * second;
            }
            if(operators.charAt(i) == '-'){ // Subtract
                result = first - second;
            }
            if(operators.charAt(i) == '+'){ // Add
                result = first + second;
            }

        }
        double temp = (double) Math.round(result * 10000.0)/10000.0;


        calculationString = Float.toString((float)temp);

        Log.i(TAG, "calculation string : "+calculationString);

        return true;


    }

    void clear()
    {
        calculationString = "";
    }
}
