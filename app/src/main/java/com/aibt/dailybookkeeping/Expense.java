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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Expense extends Fragment {
    View view;
    Database database;
    TodayExpense todayExpense;
    Last7dayExpense last7dayExpense;
    ThisMonthExpense thisMonthExpense;
    ThisYearExpense thisYearExpense;
    Context context;
    EditText amountTxt, sourceTxt;
    TextView dateshow;
    DatePickerDialog.OnDateSetListener mOnDateSetListener;
    ImageView dateImg;
    int currentDay;
    int currentMonth;
    int currentYear;
    String date = "", sourceName = "", calculationType = "Expense", amountText = "";
    DatePickerDialog datePickerDialog;
    DatePicker datePicker;
    Button submit, today, thisWeek, thisMonth, thisYear;
    Integer amount = 0;
    String currentDate, Dmonth = "", Dyear = "";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.expense, container, false);
        amountTxt = view.findViewById(R.id.expense_amount);
        sourceTxt = view.findViewById(R.id.expense_source);
        dateImg = view.findViewById(R.id.expense_date);
        dateshow = view.findViewById(R.id.showtxt);
        submit = view.findViewById(R.id.expense_submit);
        today = view.findViewById(R.id.expenseToday);
        thisWeek=view.findViewById(R.id.expenseLast7day);
        thisMonth=view.findViewById(R.id.expenseThisMonth);
        thisYear=view.findViewById(R.id.expenseThisYear);
        ///set date
        datePicker = new DatePicker(getContext());
        currentDay = datePicker.getDayOfMonth();
        currentMonth = datePicker.getMonth();
        currentYear = datePicker.getYear();
        date = currentDay + "/" + (currentMonth+1) + "/" + currentYear;
        Dmonth = "" + (currentMonth+1);
        Dyear = "" + currentYear;
        currentDate = date;
        dateshow.setText(date);

        database = new Database(getContext());
        todayExpense=new TodayExpense();
        last7dayExpense=new Last7dayExpense();
        thisMonthExpense = new ThisMonthExpense();
        thisYearExpense=new ThisYearExpense();


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
                date = date;
                if (amountText.isEmpty() || amountText.trim().equals("") || sourceName.isEmpty()) {
                    Toast.makeText(getContext(), "Please enter valid value", Toast.LENGTH_SHORT).show();
                } else {
                    amount += Integer.parseInt(amountText);
                    insertdata(amount, sourceName, calculationType, date);
                }


            }
        });


        ///show to day list
        today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                todayExpense.show(getFragmentManager(),"todayExpense");

            }
        });

        ///show last 7 days list
        thisWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                last7dayExpense.show(getFragmentManager(),"ExpenseWeek");
            }
        });
////last month list
        thisMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thisMonthExpense.show(getFragmentManager(),"ExpenseMonth");
            }
        });
        /// show this year list
        thisYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thisYearExpense.show(getFragmentManager(),"ExpenseYear");

            }
        });




        return view;

    }

    // Insert Data To data base
    public void insertdata(int amount, String sourceName, String calculationType, String date) {


        long row = database.insertData(amount, sourceName, calculationType, date, Dmonth,Dyear);
        if (row > 0) {
            Toast.makeText(getContext(), "Data seved", Toast.LENGTH_SHORT).show();
            amountTxt.setText("");
            sourceTxt.setText("");

        } else {
            Toast.makeText(getContext(), "Data faild!", Toast.LENGTH_SHORT).show();

        }
    }


}
