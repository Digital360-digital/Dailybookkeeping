package com.aibt.dailybookkeeping;

import android.app.AlertDialog;
import android.app.Dialog;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class Last7dayReport extends AppCompatDialogFragment {
    View view;
    String Titel="Last 7 days Report";
    GridView gridView;
    Dashboard dashboard;
    String[] row;
    int k;
    Database database;
    Cursor cursor;
    DatePicker datePicker;
    int currentDay;
    int currentMonth;
    int currentYear;
    String date1,date2,date3,date4,date5,date6,date7;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {



        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(R.layout.show_alart_box_report, null);
        builder.setView(view).setTitle(Titel).setNegativeButton("Ok", null);
        gridView = view.findViewById(R.id.totalIncomeAndExpense);
        dashboard = new Dashboard();
        database = new Database(getContext());
        k = 0;
        /// today
        datePicker = new DatePicker(getContext());
        currentDay = datePicker.getDayOfMonth();
        currentMonth = datePicker.getMonth() + 1;
        currentYear = datePicker.getYear();
        date1 = currentDay + "/" + currentMonth + "/" + currentYear;
        date2 = currentDay-1 + "/" + currentMonth + "/" + currentYear;
        date3 = currentDay -2+ "/" + currentMonth + "/" + currentYear;
        date4 = currentDay -3+ "/" + currentMonth + "/" + currentYear;
        date5 = currentDay -4+ "/" + currentMonth + "/" + currentYear;
        date6 = currentDay-5 + "/" + currentMonth + "/" + currentYear;
        date7 = currentDay -6 + "/" + currentMonth + "/" + currentYear;


        cursor = database.report7Day(date1,date2,date3,date4,date5,date6,date7);
        row = new String[4];
        if (cursor.getCount() == 0) {
            row[0] = "no data";
            row[1] = "no data";
            row[2] = "no data";
            row[3] = "no data";
        } else {
            row = new String[cursor.getCount() * 4];
            while (cursor.moveToNext()) {
                row[k++] = cursor.getString(0);
                row[k++] = cursor.getString(1) + "$";
                row[k++] = cursor.getString(2);
                row[k++] = cursor.getString(3);
            }
        }


        // String [] stringBuffer ={"kk"};
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.grid_view_example, R.id.gridText, row);
        gridView.setAdapter(arrayAdapter);
        return builder.create();

    }
}
