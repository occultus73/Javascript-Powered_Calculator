package com.hilles.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity {

    private TextView calculation; //The small bar at the top - this shows what was passed to javascript for evaluation.
    private TextView entry; //The large text field below that - this is what the user types into.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        entry = findViewById(R.id.entry);
        calculation = findViewById(R.id.calculation);
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()){

                    //reset both our fields of all text.
                    case R.id.clear:
                        calculation.setText("");
                        entry.setText("");
                        entry.setHint("");
                        break;

                    //remove last character in the entry field (if any).
                    case R.id.backspace:
                        int length = entry.getText().toString().length();
                        if(length > 0) entry.setText(entry.getText().toString().substring(0,length-1));
                        break;

                    //submit all the keys entered into the entry field for evaluation.
                    case R.id.enter1:
                    case R.id.enter2:
                        if(entry.getText().length() > 0) {
                            calculation.setText(entry.getText());
                            entry.setText("");

                            /////////////////////////////////////////////////////////////////////////////////////////////////////////
                            //Javascript magic happens here...
                            /////////////////////////////////////////////////////////////////////////////////////////////////////////
                            Context cx = Context.enter();
                            cx.setOptimizationLevel(-1);
                            Scriptable scope = cx.initStandardObjects();
                            String javascript = "eval(" + calculation.getText() + ");"; //the eval function is technically redundant.

                            try {
                                Object result = cx.evaluateString(scope, javascript, "<cmd>", 1, null);
                                entry.setHint(cx.toString(result));
                            }
                            catch(Exception e){
                                entry.setHint("Error");
                            }
                            /////////////////////////////////////////////////////////////////////////////////////////////////////////
                            /////////////////////////////////////////////////////////////////////////////////////////////////////////
                        }
                        break;

                    //For all normal buttons: just add their key value to the entry field - that's it.
                    default:
                        entry.setText(entry.getText().toString() + ((Button)v).getText());
                }
            }
        };
        //Add the above listener to all the buttons...
        findViewById(R.id.zero).setOnClickListener(listener);
        findViewById(R.id.one).setOnClickListener(listener);
        findViewById(R.id.two).setOnClickListener(listener);
        findViewById(R.id.three).setOnClickListener(listener);
        findViewById(R.id.four).setOnClickListener(listener);
        findViewById(R.id.five).setOnClickListener(listener);
        findViewById(R.id.six).setOnClickListener(listener);
        findViewById(R.id.seven).setOnClickListener(listener);
        findViewById(R.id.eight).setOnClickListener(listener);
        findViewById(R.id.nine).setOnClickListener(listener);
        findViewById(R.id.plus).setOnClickListener(listener);
        findViewById(R.id.minus).setOnClickListener(listener);
        findViewById(R.id.multiply).setOnClickListener(listener);
        findViewById(R.id.divide).setOnClickListener(listener);
        findViewById(R.id.openBracket).setOnClickListener(listener);
        findViewById(R.id.closeBracket).setOnClickListener(listener);
        findViewById(R.id.dot).setOnClickListener(listener);
        findViewById(R.id.enter1).setOnClickListener(listener);
        findViewById(R.id.enter2).setOnClickListener(listener);
        findViewById(R.id.clear).setOnClickListener(listener);
        findViewById(R.id.backspace).setOnClickListener(listener);
        //...and we're done!
    }
}