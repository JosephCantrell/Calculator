package com.example.calculator.model;

import android.graphics.Path;
import android.util.Log;

public class Calculator {
    private String calculationString = "";
    private String Operators = "=C-B/*+";
    private String Numbers = "0123456789.";

    private static String TAG = Calculator.class.getName();     // For console information


    public void receiveInformation(String tag){

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
        else if(tag.equals("S")){
            boolean Stop = false;
            boolean foundOperator = false;
            boolean foundNegative = false;
            boolean endOfString = false;
            for(int i = calculationString.length()-1; Stop == false || i > 0; i--){
                int indexOperators = Operators.indexOf(calculationString.charAt(i));
                Log.i(TAG, "Index at "+i+" is "+indexOperators);
                Log.i(TAG, "Item at i is :" +calculationString.charAt(i));
                if(indexOperators == 6){        // If we found a negative, Remove the negative
                    Log.i(TAG, "Found negative");
                    String tempString = calculationString.substring(0,i) + "" + calculationString.substring(i+1);
                    calculationString = tempString;
                    foundNegative = true;
                    Stop = true;
                }
                if(indexOperators != -1){       // If we found an operator
                    if(foundNegative){

                    }
                    else {
                        calculationString = calculationString.substring(0, i+1) + "-" + calculationString.substring(i+1);
                        Log.i(TAG, "Found operator");
                        foundOperator = true;
                        Stop = true;
                        break;
                    }
                }
                if(i == 0){
                    if(foundNegative){

                    }
                    else {
                        calculationString = "-" + calculationString;
                        Log.i(TAG, "End of calculation string, adding negative");
                        Stop = true;
                    }
                }
            }
        }
        else
        {
            if(tag.equals("."))
            {
                boolean skipTheRest = false;
                boolean addDecimal = false;
                boolean foundDecimal = false;
                boolean foundOperator = false;

                Log.i(TAG, "Post boolean creation");
                for(int i = calculationString.length()-1; skipTheRest == false || i > 0; i--){
                    Log.i(TAG, "In for loop. I:"+i);
                    int indexNumbers = Numbers.indexOf(calculationString.charAt(i));
                    Log.i(TAG, "Char at "+i+" is "+calculationString.charAt(i));
                    if(indexNumbers == -1){         // We have reached the end of the number we are looking at (we hit an operator)
                        skipTheRest = true;
                        foundOperator = true;
                        if(!foundDecimal)               // If we found a decimal and an operator, do not add the decimal. if we did not find a decimal but we have reached the end (an operator), add the decimal
                            addDecimal = true;
                        Log.i(TAG, "Hit an operator");
                        break;
                    }
                    else if(i <= 0)                  // We reached the end of the calculation string
                    {
                        skipTheRest = true;
                        if(!foundDecimal && !foundOperator) // If we have not found the decimal or operator (initial number) then add the decimal
                            addDecimal = true;
                        Log.i(TAG, "End of the calculation string");
                    }
                    else if(indexNumbers == 10){         // There is already a decimal in the number we are looking at
                        skipTheRest = false;
                        foundDecimal = true;
                        Log.i(TAG, "Found a decimal");
                    }
                    Log.i(TAG, "Post decimal");
                }
                if(addDecimal){                     // If we get the add decimal boolean, Continue to add the decimal at the end of the calculationString.
                    calculationString = calculationString + tag;
                }
            }

            else
                calculationString = calculationString + tag;        // Add the sent tag to the current string of calculation
        }
    }

    public String getString()  { return calculationString; }

    private void delete(){

        for(int i = 0; i < calculationString.length(); i++) {
            int index = Numbers.indexOf(calculationString.charAt(i));
            if(index == -1){        // The char at i is not a number, delete the whole string
                clear();
            }
        }

        if(calculationString.length()>0){
            calculationString = calculationString.substring(0, calculationString.length()-1);   // Removes the last position
        }

    }

    private boolean equals()
    {
        if(calculationString.length()< 3)
            return false;
        int arrayNum = 0;
        String numbers[];
        int signChange[];
        String operators = "";
        double result = 0;
        double endNumbers[];
        endNumbers = new double [calculationString.length()];
        numbers = new String [calculationString.length()]; // We will never have more numbers to calculate than the length of the string
        signChange = new int [calculationString.length()];
        for(int i = 0; i < calculationString.length();i++) {        // Initializing the numbers array with values of zero
            numbers[i] = "0";
            signChange[i] = 0;
            endNumbers[i] = 0;
        }

        boolean firstDecimal = false;
        boolean doubleNegative = false;

        for(int i = 0; i < calculationString.length();i++) {
            int index = Operators.indexOf(calculationString.charAt(i)); // If we have an operator in this position we are looking at

            Log.i(TAG, "Char at "+i+" is "+calculationString.charAt(i));
            Log.i(TAG, "index: "+index);

            if (index == -1 || index == 2) // if it is not an operator, add the number to the numbers array
            {
                Log.i(TAG, "In if statement");
                if(index == 2) {
                    Log.i(TAG, "In index if statement");
                    int signIndex = Operators.indexOf(calculationString.charAt(i+1));
                    if(signIndex == 2){             // Double negative (subtracting a negative)
                        numbers[arrayNum] = "-" + numbers[arrayNum];
                        Log.i(TAG, "numbers["+arrayNum+"] = "+numbers[arrayNum]);
                        //calculationString = calculationString.substring(0,i) + "" + calculationString.substring(i+1);
                        doubleNegative = true;
                    }
                    if(!doubleNegative && signIndex != 2){       // If there is only a single negative sign and we did not find a double negative
                        numbers[arrayNum] = "-" + numbers[arrayNum];
                    }
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
            //Log.i(TAG, "numbers["+i+"] = "+numbers[i]);
            endNumbers[i] = Double.parseDouble(numbers[i]);
            Log.i(TAG, "endNumbers["+i+"] = "+endNumbers[i]);

        }


        for(int i = 0; i < operators.length(); i++){    // Combining the array list and operator list
            double first = 0;
            double second = 0;

            if(i == 0) {
                first = endNumbers[2 * i];
                second = endNumbers[(2 * i) + 1];
            }
            else
            {
                first = result;
                second = endNumbers[1+i];
            }

            Log.i(TAG, "First : "+first+" Second: "+second);


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
        //double temp = (double) Math.round(result * 10000.0)/10000.0;


        //calculationString = Float.toString((float)temp);

        calculationString = Double.toString(result);

        Log.i(TAG, "calculation string : "+calculationString);

        return true;


    }

    private void clear()
    {
        calculationString = "";
    }
}
