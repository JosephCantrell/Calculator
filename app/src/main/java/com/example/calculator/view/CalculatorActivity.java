package com.example.calculator.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.calculator.R;
import com.example.calculator.presenter.CalculatorPresenter;

public class CalculatorActivity extends AppCompatActivity implements CalculatorView{

    private static String TAG = CalculatorActivity.class.getName();     // For console information

    private ViewGroup buttonGrid;
    private TextView CalculatorText;

    CalculatorPresenter presenter = new CalculatorPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculator);
        buttonGrid = (ViewGroup) findViewById(R.id.buttonGrid);
        CalculatorText = (TextView) findViewById(R.id.CalculatorText);
        clearCalculatorText();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_calculator, menu);
        return true;
    }

    public void onClick(View view)
    {
        Button button = (Button) view;
        String tag = button.getTag().toString();
        Log.i(TAG, "User Clicked " + tag);
        presenter.onButtonClicked(tag);
    }

    public void setCalculatorText(String text){
        CalculatorText.setText(text);
    }

    void clearCalculatorText(){
        CalculatorText.setText("0");
    }
}
