package com.example.calculator.model;

import android.util.Log;

public class Calculator {
    private String calculationString;
    private String Operators = "=CSB/*-+";
    private String Numbers = "0123456789.";

    private static String TAG = Calculator.class.getName();     // For console information


    public void recieveInformation(String tag){

        int index = Operators.indexOf(tag);
        if(index == -1) // NOT AN OPERATOR
        {
            calculationString = calculationString + tag;        // Add the sent tag to the current string of calculations
        }
        if(tag.equals("=")){
            equals();
        }
        if(tag.equals("D")){        // Delete the last char
            delete();
        }
        if(tag.equals("C")){
            clear();
        }
    }

    public String getString()  { return calculationString; }

    void delete(){
        if(calculationString.length()>0){
            calculationString = calculationString.substring(0, calculationString.length()-1);   // Removes the last position
        }
    }

    void equals()
    {
        boolean number = false;
        int arrayNum = 0;
        int arrayPos = 0;
        float numbers[];
        String operators = "";
        float result = 0;
        numbers = new float [calculationString.length()]; // We will never have more numbers to calculate than the length of the string
        for(int i = 0; i < calculationString.length();i++) {
            int index = Operators.indexOf(calculationString.charAt(i)); // If we have an operator in this position we are looking at
            if (index == -1) // if it is not an operator, add the number to the numbers array
            {
                numbers[arrayNum] = calculationString.charAt(i);   // Adding to the numbers array
            } else {
                operators = operators + Operators.charAt(index);
                arrayNum++;
            }
        }

        for(int i = 0; i < operators.length(); i++){    // Combining the array list and operator list
            float first = 0;
            float second = 0;

            first = numbers[2*i];
            second = numbers[(2*i)+1];

            Log.i(TAG, "First number is: "+first);
            Log.i(TAG, "Second number is: "+second);

            if(operators.charAt(i) == '/'){ // Division
                result = result + (first / second);
                Log.i(TAG, "Operator in position "+i+" is /");
                Log.i(TAG, "Result: "+result);
            }
            if(operators.charAt(i) == '*'){ // Multiply
                result = result + (first * second);
                Log.i(TAG, "Operator in position "+i+" is *");
                Log.i(TAG, "Result: "+result);
            }
            if(operators.charAt(i) == '-'){ // Subtract
                result = result + (first - second);
                Log.i(TAG, "Operator in position "+i+" is -");
                Log.i(TAG, "Result: "+result);
            }
            if(operators.charAt(i) == '+'){ // Add
                result = result + (first + second);
                Log.i(TAG, "Operator in position "+i+" is +");
                Log.i(TAG, "Result: "+result);
            }

        }

        calculationString = Float.toString(result);

        Log.i(TAG, "Calculation result: "+result);


    }

    void clear()
    {
        calculationString = "";
    }
}
