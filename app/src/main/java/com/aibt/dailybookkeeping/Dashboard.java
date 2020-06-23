package com.aibt.dailybookkeeping;

import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Dashboard extends Fragment {
    RadioGroup dashboardRadioGroup;
    RadioButton dashboardRadioButton;


    EditText dashboardAddAmount, dashboardAddSource;
    ImageView dashboardImage;
    Button dashboardSubmitBtn, dashboardTotalIncomeBtn, dashboardTotalExpenseBtn, dashboardCalculatorBtn;
    TextView dateshow, balanceShow;
    Database database;


    String date = "", sourceName = "", calculationType = "", amountTxt = "";
    DatePickerDialog datePickerDialog;
    DatePicker datePicker;
    Integer amount = 0;

    int currentDay;
    int currentMonth;
    int currentYear;
    View view;
    int radioId;
    Totalincome totalincome;
    TotaExpense totaExpense;
    Calculator calculator;
    Report report;
    Integer IncomeBalance = 0, ExpenseBalance = 0;
    Integer balence=0;
    String Dyear="",Dmonth="";

    int Token;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.dashboard, container, false);
        dashboardRadioGroup = view.findViewById(R.id.dashboard_radioGroup);
        dashboardAddAmount = view.findViewById(R.id.dashboard_addAmount);
        dashboardAddSource = view.findViewById(R.id.dashboard_SourceName);
        dashboardImage = view.findViewById(R.id.dashboard_dateImage);
        dashboardSubmitBtn = view.findViewById(R.id.dashboard_submitButton);
        dashboardTotalIncomeBtn = view.findViewById(R.id.dashboard_totalIncomeButton);
        dashboardTotalExpenseBtn = view.findViewById(R.id.dashboard_TotalExpenseButton);
        dashboardCalculatorBtn = view.findViewById(R.id.dashboard_calculatorButton);
        dateshow = view.findViewById(R.id.dashboard_showDate);
        balanceShow = view.findViewById(R.id.balanceId);


        database = new Database(getContext());
        totalincome = new Totalincome();
        totaExpense = new TotaExpense();
        calculator = new Calculator();
        //get value of current balance
        balence= Balance();
        //show Current Balance
        balanceShow.setText("" + balence);
        ///set date
        datePicker = new DatePicker(getContext());
        currentDay = datePicker.getDayOfMonth();
        currentMonth = datePicker.getMonth();
        currentYear = datePicker.getYear();
        Dmonth=""+(datePicker.getMonth()+1);
        Dyear=""+datePicker.getYear();
        date = currentDay + "/" + (currentMonth+1) + "/" + currentYear;
        dateshow.setText(date);
        /////Date select image
        dashboardImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date = dayOfMonth + "/" + (month+1) + "/" + year;
                        dateshow.setText(date);
                        Dmonth = "" + (month+1);
                        Dyear = "" + year;

                    }
                }, currentYear, currentMonth, currentDay);
                datePickerDialog.show();


            }
        });
        // Submit Button
        dashboardSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amountTxt = dashboardAddAmount.getText().toString();
                sourceName = dashboardAddSource.getText().toString();
                radioId = dashboardRadioGroup.getCheckedRadioButtonId();
                dashboardRadioButton = view.findViewById(radioId);
                date = date;

                calculationType = dashboardRadioButton.getText().toString();
                if (amountTxt.isEmpty() || amountTxt.trim().equals("") || sourceName.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter valid value", Toast.LENGTH_SHORT).show();
                } else {
                    amount += Integer.parseInt(amountTxt);

                    insertdata(amount, sourceName, calculationType, date);
                }


            }
        });
/// Total income button
        dashboardTotalIncomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totalincome.show(getFragmentManager(), "TotalIncome");
            }
        });


        ///Show Total Expense
        dashboardTotalExpenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                totaExpense.show(getFragmentManager(), "TotalIncome");


            }
        });
        /// Open Calculator
        dashboardCalculatorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculator.show(getFragmentManager(), "calculator");
            }
        });

        return view;
    }

    // Insert Data To data base
    public void insertdata(int amount, String sourceName, String calculationType, String date) {


        long row = database.insertData(amount, sourceName, calculationType, date,Dmonth,Dyear);
        if (row > 0) {
            Toast.makeText(getContext(), "Data seved", Toast.LENGTH_SHORT).show();
            dashboardAddAmount.setText("");
            dashboardAddSource.setText("");

        } else {
            Toast.makeText(getContext(), "Data faild!", Toast.LENGTH_SHORT).show();

        }
    }

    public Integer Balance() {
        Integer balance = 0;

        IncomeBalance += calculationTakenLoan();
        IncomeBalance += calculationIcmone();
        IncomeBalance += calculationReturnLoan();

        ExpenseBalance += calculationExpense();
        ExpenseBalance += calculationGivenLoan();
        ExpenseBalance += calculationPaidLoan();
        balance = IncomeBalance - ExpenseBalance;

        return balance;
    }

    ///Function of Total Income
    public Integer calculationIcmone() {
        Cursor cursor;
        Integer incomeamount = 0;
        cursor = database.showIncome();
        if (cursor.getCount() == 0) {
            incomeamount = 0;
        } else {
            while (cursor.moveToNext()) {
                incomeamount += Integer.parseInt(cursor.getString(1));
            }
        }
        return incomeamount;
    }

    public Integer calculationExpense() {
        Cursor cursor;
        Integer amount = 0;
        cursor = database.showExpense();
        if (cursor.getCount() == 0) {
             amount = 0;
        } else {
            while (cursor.moveToNext()) {
                amount += Integer.parseInt(cursor.getString(1));
            }
        }
        return amount;
    }

    public Integer calculationGivenLoan() {
        Cursor cursor;
        Integer amount = 0;
        cursor = database.showGiveloan();
        if (cursor.getCount() == 0) {
        } else {


            while (cursor.moveToNext()) {
                amount += Integer.parseInt(cursor.getString(1));
            }
        }
        return amount;
    }


    public Integer calculationTakenLoan() {
        Cursor cursor;
        Integer amount = 0;
        cursor = database.showTakeloan();
        if (cursor.getCount() == 0) {
        } else {


            while (cursor.moveToNext()) {
                amount += Integer.parseInt(cursor.getString(1));
            }
        }
        return amount;
    }

    public Integer calculationReturnLoan() {
        Cursor cursor;
        Integer amount = 0;
        cursor = database.showReturnloan();
        if (cursor.getCount() == 0) {
        } else {


            while (cursor.moveToNext()) {
                amount += Integer.parseInt(cursor.getString(1));
            }
        }
        return amount;
    }


    public Integer calculationPaidLoan() {
        Cursor cursor;
        Integer amount = 0;
        cursor = database.showPaidloan();
        if (cursor.getCount() == 0) {
        } else {


            while (cursor.moveToNext()) {
                amount += Integer.parseInt(cursor.getString(1));
            }
        }
        return amount;
    }


}



