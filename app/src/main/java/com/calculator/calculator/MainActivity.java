package com.calculator.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button
            // digits and dot
            button_one,button_two,button_three,button_four,
            button_five, button_six, button_seven, button_eight,
            button_nine, button_zero, button_dot,

            // operators
            btn_addition, btn_division, btn_modulus, btn_multiplication,
            btn_subtraction, btn_sqrt, btn_equal,

            // others. click button mrc once is recall, twice is clear memory
            btn_mrc, btn_m_plus, btn_m_minus, btn_clear;

    private EditText display_area;
    private double memory_val;
    // display_str will be cleared/updated if input is not digits, dot, m+, m-, mc;
    // expression will be cleared if input is '='
    private String display_str, expression;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initButtons();
    }

    private void initButtons() {
        display_area = (EditText)findViewById(R.id.display);
        memory_val = 0;
        display_str = "";
        expression = "";

        // digits from 0 ~ 9
        button_one = (Button)findViewById(R.id.one);
        button_two = (Button)findViewById(R.id.two);
        button_three = (Button)findViewById(R.id.three);
        button_four = (Button)findViewById(R.id.four);
        button_five = (Button)findViewById(R.id.five);
        button_six = (Button)findViewById(R.id.six);
        button_seven = (Button)findViewById(R.id.seven);
        button_eight = (Button)findViewById(R.id.eight);
        button_nine = (Button)findViewById(R.id.nine);
        button_zero = (Button)findViewById(R.id.zero);
        button_dot = (Button)findViewById(R.id.dot);

        // operators
        btn_addition = (Button)findViewById(R.id.addition);
        btn_subtraction = (Button)findViewById(R.id.subtraction);
        btn_multiplication = (Button)findViewById(R.id.multiplication);
        btn_division = (Button)findViewById(R.id.division);
        btn_modulus = (Button)findViewById(R.id.modulus);
        btn_sqrt = (Button)findViewById(R.id.sqrt);
        btn_equal = (Button)findViewById(R.id.equal);

        // others
        btn_mrc = (Button)findViewById(R.id.mrc);
        btn_m_plus = (Button)findViewById(R.id.m_plus);
        btn_m_minus = (Button)findViewById(R.id.m_minus);
        btn_clear = (Button)findViewById(R.id.clear_whole_expression);

        // digits from 0 ~ 9
        button_one.setOnClickListener(this);
        button_two.setOnClickListener(this);
        button_three.setOnClickListener(this);
        button_four.setOnClickListener(this);
        button_five.setOnClickListener(this);
        button_six.setOnClickListener(this);
        button_seven.setOnClickListener(this);
        button_eight.setOnClickListener(this);
        button_nine.setOnClickListener(this);
        button_zero.setOnClickListener(this);
        button_dot.setOnClickListener(this);

        // operators
        btn_addition.setOnClickListener(this);
        btn_division.setOnClickListener(this);
        btn_modulus.setOnClickListener(this);
        btn_multiplication.setOnClickListener(this);
        btn_subtraction.setOnClickListener(this);
        btn_sqrt.setOnClickListener(this);
        btn_equal.setOnClickListener(this);

        //others
        btn_mrc.setOnClickListener(this);
        btn_m_plus.setOnClickListener(this);
        btn_m_minus.setOnClickListener(this);
        btn_clear.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        boolean is_operator = false; // "+, -, *, /, %, sqrt"
        switch(view.getId()){
            case R.id.one:
                display_str += "1";
                expression += "1";
                break;
            case R.id.two:
                display_str += "2";
                expression += "2";
                break;
            case R.id.three:
                display_str += "3";
                expression += "3";
                break;
            case R.id.four:
                display_str += "4";
                expression += "4";
                break;
            case R.id.five:
                display_str += "5";
                expression += "5";
                break;
            case R.id.six:
                display_str += "6";
                expression += "6";
                break;
            case R.id.seven:
                display_str += "7";
                expression += "7";
                break;
            case R.id.eight:
                display_str += "8";
                expression += "8";
                break;
            case R.id.nine:
                display_str += "9";
                expression += "9";
                break;
            case R.id.zero:
                display_str += "0";
                expression += "0";
                break;
            case R.id.dot:
                display_str += ".";
                expression += ".";
                break;

            case R.id.equal:
                try {
                    display_str =  Double.toString(calculate());
                }
                catch(Exception e) {
                    //display_str = "Not a number";
                    display_str = e.toString();
                }

                display_area.setText(display_str);
                display_str = "";
                expression = "";
                is_operator = true;
                break;

            case R.id.addition:
                display_str = "";
                expression += "+";
                is_operator = true;
                break;

            case R.id.subtraction:
                display_str = "";
                expression += "-";
                is_operator = true;
                break;

            case R.id.multiplication:
                display_str = "";
                expression += "*";
                is_operator = true;
                break;

            case R.id.division:
                display_str = "";
                expression += "/";
                is_operator = true;
                break;

            case R.id.modulus:
                display_str = "";
                expression += "%";
                is_operator = true;
                break;

            case R.id.sqrt:
                display_str = "";
                expression += "√";// sqrt
                try {
                    display_str =  Double.toString(calculate());
                }
                catch(Exception e) {
                    //display_str = "Not a number";
                    display_str = e.toString();
                }
                display_area.setText(display_str);
                expression = display_str;
                is_operator = true;
                break;

            case R.id.clear_whole_expression: // C
                display_str = "";
                expression = "";
                break;

            case R.id.m_plus: // M+
                memory_val += Double.parseDouble(display_str);
                break;

            case R.id.m_minus: // M-
                memory_val -= Double.parseDouble(display_str);
                break;

            case R.id.mrc: // mrc recally value
                display_str =  Double.toString(memory_val);
                break;

            default:
                break;
        }
        if (!is_operator) display_area.setText(display_str);
    }

    double calculate() {
        double result, prev_num, number; // number before previous operator
        char oprt = '+';
        String cur_num = ""; // number after previous operator
        result = prev_num = 0;

        //display_area.setText(expression);

        expression += "#";// as end sign of the expression

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if (Character.isDigit(c) || c == '.') {
                cur_num += c;
            }
            else {
                number =  cur_num == "" ? 0: Double.parseDouble(cur_num);
                cur_num = "";
                // check previous operator
                if (oprt == '+' || oprt == '-') {
                    result +=prev_num;
                    prev_num = (oprt == '-' ? -1 : 1) * number;
                }
                else if (oprt == '/') {
                    try{
                        prev_num /= number;
                    }
                    catch(Exception e){
                       throw e;
                    }
                }
                else if (oprt == '*') {
                    prev_num *= number;
                }
                else if (oprt == '%') {
                    try{
                        prev_num %= number;
                    }
                    catch(Exception e){
                        throw e;
                    }
                }
                // x√  == sqrt(x)
                else if (oprt == '√') {
                    if (prev_num < 0) {
                        prev_num = -Math.sqrt(-prev_num);
                    }
                    else {
                        prev_num = Math.sqrt(prev_num);
                    }
                }
                oprt = c;
            }
        }

        return result + prev_num;
    }
}
