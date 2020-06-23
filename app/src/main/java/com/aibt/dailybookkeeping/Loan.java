package com.aibt.dailybookkeeping;

import android.app.DatePickerDialog;
import android.content.Context;
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

public class Loan extends Fragment {

    View view;
    Database database;
    RadioGroup dashboardRadioGroup;
    RadioButton dashboardRadioButton;

    Context context;
    EditText amountTxt, sourceTxt;
    TextView dateshow;
    DatePickerDialog.OnDateSetListener mOnDateSetListener;
    ImageView dateImg;
    int currentDay;
    int radioId;
    int currentMonth;
    int currentYear;
    String date = "", sourceName = "", calculationType = "", amountText = "";
    DatePickerDialog datePickerDialog;
    DatePicker datePicker;
    Button submit, GivenLoan, TakeLoan, ReturnLoan, PaidLoan;
    Integer amount = 0;
    GivenLoanShow givenLoanShow;
    TakenLoanShow takenLoanShow;
    ReturnLoanShow returnLoanShow;
    PaidLoanShow paidLoanShow;
    String Dmonth = "", Dyear = "";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.loan, container, false);
        dashboardRadioGroup = view.findViewById(R.id.loanRadioGroup);
        amountTxt = view.findViewById(R.id.loanAmount);
        sourceTxt = view.findViewById(R.id.loanSourec);
        dateImg = view.findViewById(R.id.showDatePicker);
        dateshow = view.findViewById(R.id.showdatetxt);
        submit = view.findViewById(R.id.loanSubmit);
        GivenLoan=view.findViewById(R.id.loanBtnGiven);
        TakeLoan=view.findViewById(R.id.loanBtnTaken);
        ReturnLoan=view.findViewById(R.id.loanBtnReturn);
        PaidLoan=view.findViewById(R.id.loanBtnPaid);


        givenLoanShow =new GivenLoanShow();
        takenLoanShow=new TakenLoanShow();
        paidLoanShow= new PaidLoanShow();
        returnLoanShow = new ReturnLoanShow();

        ///set date
        datePicker = new DatePicker(getContext());
        currentDay = datePicker.getDayOfMonth();
        currentMonth = datePicker.getMonth();
        currentYear = datePicker.getYear();
        date = currentDay + "/" + (currentMonth+1) + "/" + currentYear;
        Dmonth = "" + (currentMonth+1);
        Dyear = "" + currentYear;
        dateshow.setText(date);
        database = new Database(getContext());


        /////Date select image
        dateImg.setOnClickListener(new View.OnClickListener() {
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
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amountText = amountTxt.getText().toString();
                sourceName = sourceTxt.getText().toString();

                radioId = dashboardRadioGroup.getCheckedRadioButtonId();
                dashboardRadioButton = view.findViewById(radioId);
                date = date;

                calculationType = dashboardRadioButton.getText().toString();

                if (amountText.isEmpty() || amountText.trim().equals("") || sourceName.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter valid value", Toast.LENGTH_SHORT).show();
                } else {
                    amount += Integer.parseInt(amountText);
                    insertdata(amount, sourceName, calculationType, date);
                }


            }
        });

        GivenLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                givenLoanShow.show(getFragmentManager(), "Loan");
            }
        });

        TakeLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takenLoanShow.show(getFragmentManager(), "Loan");
            }
        });

        ReturnLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnLoanShow.show(getFragmentManager(), "Loan");
            }
        });

        PaidLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                paidLoanShow.show(getFragmentManager(), "Loan");
            }
        });


        return view;

    }

    // Insert Data To data base
    public void insertdata(int amount, String sourceName, String calculationType, String date) {


        long row = database.insertData(amount, sourceName, calculationType, date,Dmonth,Dyear);
        if (row > 0) {
            Toast.makeText(getContext(), "Data seved", Toast.LENGTH_SHORT).show();
            amountTxt.setText("");
            sourceTxt.setText("");

        } else {
            Toast.makeText(getContext(), "Data faild!", Toast.LENGTH_SHORT).show();

        }
    }
}