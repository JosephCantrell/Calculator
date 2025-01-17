package com.example.calculator.model;
import android.util.Log;

public class Calculator {
    private String calculationString = "";
    private String Operators = "=C-B/*+";
    private String JustOperators = "/*-+";
    private String Numbers = "0123456789.";

    private boolean decimalPlaced = false;
    private boolean dividePlaced = false;
    private boolean multiplyPlaced = false;
    private boolean subtracionPlaced = false;
    private boolean additionPlaced = false;
    private boolean operatorPlaced = false;

    private static String TAG = Calculator.class.getName();     // For console information


    public void receiveInformation(String tag){

        int index = Operators.indexOf(tag);

        switch(tag)
        {
            case "=":
            {
                decimalPlaced = false;
                if(calculationString.contains("Infinity")) {            // Simple case situations. If the user attempts to do math with the Infinity result, the result will just be infinity. Does not crash
                    Log.i(TAG, "User must clear");
                    calculationString = "Infinity";
                }
                else if(calculationString.contains("NaN")){             // Same as the infinity result. Cannot do math with NaN. Just returns NaN
                    Log.i(TAG, "User must clear");
                    calculationString = "NaN";
                }
                else
                    equals();
                break;
            }
            case "D":
            {

                delete();
                break;
            }
            case "C":
            {
                decimalPlaced = false;
                clear();
                break;
            }
            case "S":
            {
                boolean Stop = false;
                boolean foundOperator = false;
                boolean foundNegative = false;
                boolean endOfString = false;
                if(calculationString.length() == 0){

                }
                else {
                    for (int i = calculationString.length() - 1; Stop == false || i > 0; i--) {     // Just works for flipping the signs
                        int indexOperators = Operators.indexOf(calculationString.charAt(i));
                        if (indexOperators == 2) {        // If we found a negative, Remove the negative
                            String tempString = calculationString.substring(0, i) + "" + calculationString.substring(i + 1);        // If we find a negative sign, we delete it
                            calculationString = tempString;
                            foundNegative = true;
                            Stop = true;
                        }
                        if (indexOperators != -1) {       // If we found an operator
                            if (foundNegative) {

                            } else {
                                calculationString = calculationString.substring(0, i + 1) + "-" + calculationString.substring(i + 1);       // If we find an operator, we add in a negative sign
                                foundOperator = true;
                                Stop = true;
                                break;
                            }
                        }
                        if (i == 0) {
                            if (foundNegative) {

                            } else {
                                calculationString = "-" + calculationString;        // Adding in the negative sign
                                Stop = true;
                            }
                        }
                    }
                }
                break;
            }
            case "-":
            {
                if(operatorPlaced){
                    Log.i(TAG, "User attempted to apply a second subtraction");
                }
                else
                {
                    if(calculationString.equals("")){
                        calculationString = "-";
                    }
                    else{
                    calculationString = calculationString + "+-";
                    operatorPlaced = true;

                    }
                }
                decimalPlaced = false;
                break;
            }
            case ".":
            {
                if(decimalPlaced){
                    Log.i(TAG, "Decimal already placed, skipping");
                }
                else
                {
                    calculationString = calculationString + tag;
                    decimalPlaced = true;
                }
                break;
            }
            case "+":
            {
                if(operatorPlaced){
                    Log.i(TAG, "User attempted to place a second addition symbol");
                }
                else
                {
                    calculationString = calculationString + tag;
                    operatorPlaced = true;
                }
                decimalPlaced = false;
                break;
            }
            case "*":
            {
                if(operatorPlaced){
                    Log.i(TAG, "User attempted to place a second multiplication symbol");
                }
                else
                {
                    calculationString = calculationString + tag;
                    operatorPlaced = true;
                }
                decimalPlaced = false;
                break;
            }
            case "/":
            {
                if(operatorPlaced){
                    Log.i(TAG, "User attempted to place a second division symbol");
                }
                else
                {
                    calculationString = calculationString + tag;
                    operatorPlaced = true;
                }
                decimalPlaced = false;
                break;
            }
            default:
            {
                if(calculationString.equals("0"))
                    calculationString = "";
                calculationString = calculationString + tag;        // Add the sent tag to the current string of calculation
                operatorPlaced = false;
                break;
            }
        }

            }

    public String getString()  { return calculationString; }

    private void delete(){                          // Basic delete function


        if(!calculationString.isEmpty()) {
            if (calculationString.charAt(calculationString.length() - 1) == '.') {
                decimalPlaced = false;
            } else if (calculationString.charAt(calculationString.length() - 1) == '/') {
                operatorPlaced = false;
            } else if (calculationString.charAt(calculationString.length() - 1) == '*') {
                operatorPlaced = false;
            } else if (calculationString.charAt(calculationString.length() - 1) == '-') {
                operatorPlaced = false;
            } else if (calculationString.charAt(calculationString.length() - 1) == '+') {
                operatorPlaced = false;
            }
        }

        for (int i = 0; i < calculationString.length(); i++) {      // Stepping through the calculation string to find non numbers, such as E or infinity. If these are found, clear the entire string
            int numberIndex = Numbers.indexOf(calculationString.charAt(i));
            int operatorIndex = JustOperators.indexOf(calculationString.charAt(i));
            if (numberIndex == -1 && operatorIndex == -1) {        // The char at i is not a number, delete the whole string
                clear();
            }
        }

        if(!calculationString.isEmpty()) {
            if (calculationString.equals("0")) {          // If the user deletes the initial zero, the zero is printed again. Allows for the perpetual zero being printed
                calculationString = "0";
            } else if (calculationString.length() > 0) {       // Else, just delete the last entry.
                calculationString = calculationString.substring(0, calculationString.length() - 1);   // Removes the last position
            }
        }


        Log.i(TAG, "Calculation string after delete: "+calculationString);

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

        for(int i = 0; i < calculationString.length();i++) {
            int index = Operators.indexOf(calculationString.charAt(i)); // If we have an operator in this position we are looking at

            if (index == -1 || index == 2) // if it is not an operator, add the number to the numbers array
            {
                if(index == 2){             // If we find a negative,
                    numbers[arrayNum] = "-"+numbers[arrayNum];  // Add a negative sign infront of the current number in the array
                }
                if(index == -1) {               // If we dont find any operators,
                    numbers[arrayNum] = numbers[arrayNum] + calculationString.charAt(i);   // Adding to the numbers array
                }
            } else {
                operators = operators + Operators.charAt(index);
                arrayNum++;
            }
        }

        for(int i = 0; i < numbers.length; i++){        // Taking numbers from my string array and parsing them into double so we can properly do math with them. Stored in an array list.
            endNumbers[i] = Double.parseDouble(numbers[i]);
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
            //if(operators.charAt(i) == '-'){ // Subtract
            //    result = first - second;
            //}
            if(operators.charAt(i) == '+'){ // Add
                result = first + second;
            }

        }

        calculationString = Double.toString(result);

        Log.i(TAG, "calculation string : "+calculationString);

        return true;


    }

    private void clear()
    {
        calculationString = "0";            // Print out the initial zero when cleared
    }
}
