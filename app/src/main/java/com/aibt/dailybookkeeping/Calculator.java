package com.aibt.dailybookkeeping;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import static java.lang.Math.sqrt;

public class Calculator extends AppCompatDialogFragment {
    View view;
    String Titel = "Calculator";
    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btnSum, btnSub, btnMulty, btnDivider, btnEqual, btnDot, btnDelete;
    ///
    TextView calculation;
    String textshow = "", number_one = "", past_oprator = "", current_oprator = "",Ans="",past_operator="";
    Double n1 = 0.0, n2;
    NumberFormat format, longFormate;
    int Operator_count = 0, fixt_operator = 0, dotCount = 0;
    int dotC = 0;
    Boolean operator = false;


    ////

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.calculator, null);
        builder.setView(view).setTitle(Titel).setNegativeButton("Ok", null);

        /////calculator

        calculation = view.findViewById(R.id.calculation);

        //format = new DecimalFormat("#.####");
        // longFormate = new DecimalFormat("0.#E0");


        /// calculator number  btn
        btn0 = view.findViewById(R.id.cBtn0);
        btn1 = view.findViewById(R.id.cBtn1);
        btn2 = view.findViewById(R.id.cBtn2);
        btn3 = view.findViewById(R.id.cBtn3);
        btn4 = view.findViewById(R.id.cBtn4);
        btn5 = view.findViewById(R.id.cBtn5);
        btn6 = view.findViewById(R.id.cBtn6);
        btn7 = view.findViewById(R.id.cBtn7);
        btn8 = view.findViewById(R.id.cBtn8);
        btn9 = view.findViewById(R.id.cBtn9);
        /// calculator relation  btn
        btnSum = view.findViewById(R.id.cBtnSum);
        btnSub = view.findViewById(R.id.cBtnSub);
        btnMulty = view.findViewById(R.id.cBtnMulty);
        btnDivider = view.findViewById(R.id.cBtnDivided);
        btnDot = view.findViewById(R.id.cBtnDot);
        btnEqual = view.findViewById(R.id.cBtnEqual);
        btnDelete = view.findViewById(R.id.deleteId);

        btn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number_one += "0";
                calculation.setText(number_one);

            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (operator==false) {
                    number_one += "1";
                    calculation.setText(number_one);

                }else {

                    number_one="1";
                    operator=false;
                    calculation.setText(number_one);
                    Toast.makeText(getContext(), "Operator="+operator, Toast.LENGTH_SHORT).show();
                }


            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number_one += "2";
                calculation.setText(number_one);

            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number_one += "3";
                calculation.setText(number_one);

            }
        });


        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number_one += "4";
                calculation.setText(number_one);

            }
        });


        btn5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number_one += "5";
                calculation.setText(number_one);

            }
        });


        btn6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number_one += "6";
                calculation.setText(number_one);

            }
        });


        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number_one += "7";
                calculation.setText(number_one);

            }
        });


        btn8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number_one += "8";
                calculation.setText(number_one);

            }
        });


        btn9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                number_one += "9";
                calculation.setText(number_one);

            }
        });

        btnSum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current_oprator="+";

            }
        });

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current_oprator = "-";
                operator=true;
                if (n1==0.0) {
                    n1 = Double.parseDouble(calculation.getText().toString());
                }else {
                    n2= Double.parseDouble(calculation.getText().toString());
                }
                calculationF();
            }
        });
        btnMulty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current_oprator = "*";
                operator=true;
            }
        });

        btnDivider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                current_oprator = "/";
                operator=true;

            }
        });


        return builder.create();
    }

    public String calculationF() {
        if (past_operator!="") {
            switch (past_operator) {
                case "+":
                    n1 = n1 + n2;
                    break;
                case "-":
                    n1 = n1 + n2;
                    break;

            }

            return Ans = format.format(n1).toString();
        }
        return "";
    }


}


