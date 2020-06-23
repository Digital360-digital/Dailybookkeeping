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

public class ThisMonthReport extends AppCompatDialogFragment {
    View view;
    String Titel="This Month Report";
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
    String Month="";
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
        Month = ""+(datePicker.getMonth() + 1);
        cursor = database.reportMonth(Month);
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
