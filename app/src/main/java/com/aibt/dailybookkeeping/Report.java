package com.aibt.dailybookkeeping;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Report extends Fragment {
    View view;
    Database database;
    TodayReport todayReport;
    Last7dayReport last7dayReport;
    ThisMonthReport thisMonthReport;
    ThisYearReport thisYearReport;
    Integer incomeamount = 0, Expenseamount = 0, TakenLoanamount = 0, GivenLoanamount = 0, Returnamount = 0, Paidamount = 0;
    Button today, thisWeek, thisMonth, thisYear,deleteButton;


    TextView totalIncomeText, totalExpenseText, totalLoanGivenText, totalLoanTakenText, totalLoanReturnText, totalLoanPaidText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.report, container, false);

        totalIncomeText = view.findViewById(R.id.reportTotalIncome);
        totalExpenseText = view.findViewById(R.id.reportLastTotalExpense);
        totalLoanGivenText = view.findViewById(R.id.reportLastTotalGivenLoan);
        totalLoanTakenText = view.findViewById(R.id.reportLastTotalTakenLoan);
        totalLoanReturnText = view.findViewById(R.id.reportLastTotalReturnLoan);
        totalLoanPaidText = view.findViewById(R.id.reportLastTotalPaidLoan);
        today = view.findViewById(R.id.reportToday);
        thisWeek=view.findViewById(R.id.reportLast7day);
        thisMonth=view.findViewById(R.id.reportLast30day);
        thisYear=view.findViewById(R.id.reportLastYear);
        deleteButton=view.findViewById(R.id.deleteId);

        database = new Database(getContext());
        incomeamount = calculationIcmone();
        Expenseamount = calculationExpense();
        GivenLoanamount = calculationGivenLoan();
        TakenLoanamount = calculationTakenLoan();
        Returnamount = calculationReturnLoan();
        Paidamount = calculationPaidLoan();
        todayReport=new TodayReport();
        last7dayReport=new Last7dayReport();
        thisMonthReport = new ThisMonthReport();
        thisYearReport=new ThisYearReport();

///////// set amount on text view
        totalIncomeText.setText(""+incomeamount+" $");
        totalExpenseText.setText(""+Expenseamount+" $");
        totalLoanGivenText.setText(""+GivenLoanamount+" $");
        totalLoanTakenText.setText(""+TakenLoanamount+" $");
        totalLoanReturnText.setText(""+Returnamount+" $");
        totalLoanPaidText.setText(""+Paidamount+" $");

        /// today report
        today.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                todayReport.show(getFragmentManager(),"todayReport");
            }
        });
        thisWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                last7dayReport.show(getFragmentManager(),"Report7days");
            }
        });
        thisMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thisMonthReport.show(getFragmentManager(),"Reportmonth");
            }
        });
        thisYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thisYearReport.show(getFragmentManager(),"reportThisYear");
            }
        });

        // Delete
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Alert")
                        .setMessage("Are you sure you want to delete all data?")
                        .setNegativeButton("No",null)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                long k= database.delete();
                                if (k>0){
                                    Toast.makeText(getContext(), "All data has been deleted", Toast.LENGTH_SHORT).show();
                                    totalIncomeText.setText("0"+" $");
                                    totalExpenseText.setText("0"+" $");
                                    totalLoanGivenText.setText("0"+" $");
                                    totalLoanTakenText.setText("0"+" $");
                                    totalLoanReturnText.setText("0"+" $");
                                    totalLoanPaidText.setText("0"+" $");

                                }
                            }
                        }).show();


            }
        });

        return view;
    }

    ///Function of Total Income
    public Integer calculationIcmone() {
        Cursor cursor;
        Integer incomeamount = 0;
        cursor = database.showIncome();
        if (cursor.getCount() == 0) {
            totalIncomeText.setText("0");
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
            totalExpenseText.setText("0");
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
            totalLoanGivenText.setText("0");
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
            totalLoanTakenText.setText("0");
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
            totalLoanReturnText.setText("0");
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
            totalLoanPaidText.setText("0");
        } else {


            while (cursor.moveToNext()) {
                amount += Integer.parseInt(cursor.getString(1));
            }
        }
        return amount;
    }


}
