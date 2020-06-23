package com.aibt.dailybookkeeping;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    private static final String DATABSE_NAME="account.db";
    private static final int DB_VERSION=1;
    private static final String DAILY_DOCUMENT_TABLE="daily_document";
    private static final String USER_TABLE="daily_document";
    private static final String NAME="name";
    private static final String EMAIL="email";
    private static final String PHONE="phone";
    private static final String PROFESSION="profession";

    private static final String CREATE_USER_TABLE="CREATE TABLE "+USER_TABLE+" ("+NAME+" VARCHAR(100) , "+EMAIL+" VARCHAR (100) , "+PHONE+" VARCHAR (100) , "+PROFESSION+" VARCHAR(100) );";

    private static final String ID="_id";
    private static final String AMOUNT="amount";
    private static final String SOURCE_NAME="sourece_name";
    private static final String CALCULATION_TYPE="calculation_type";
    private static final String DATE="date";
    private static final String MONTH="month";
    private static final String YEAR="year";
    private static final String CREATE_INCOME_TABLE="CREATE TABLE "+DAILY_DOCUMENT_TABLE+" ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT , "+AMOUNT+" INTEGER , "+CALCULATION_TYPE+" VARCHAR (100) , "+SOURCE_NAME+" VARCHAR (100) , "+DATE+" VARCHAR , "+MONTH+" VARCHAR(10) , "+YEAR+" VARCHAR(10));";
    private static final String INCOME_QUERY="SELECT   "+DATE+" , "+AMOUNT+" , "+SOURCE_NAME+"  FROM "+DAILY_DOCUMENT_TABLE+" WHERE "+CALCULATION_TYPE+" = 'Income' ;";
    private static final String EXPENSE_QUERY="SELECT  "+DATE+" , "+AMOUNT+" , "+SOURCE_NAME+"  FROM "+DAILY_DOCUMENT_TABLE+" WHERE "+CALCULATION_TYPE+" = 'Expense' ;";
    private static final String TAKE_LOAN_QUERY="SELECT  "+DATE+" , "+AMOUNT+" , "+SOURCE_NAME+"  FROM "+DAILY_DOCUMENT_TABLE+" WHERE "+CALCULATION_TYPE+" = 'Loan Taken' ;";
    private static final String GIVE_LOAN_QUERY="SELECT  "+DATE+" , "+AMOUNT+" , "+SOURCE_NAME+"  FROM "+DAILY_DOCUMENT_TABLE+" WHERE "+CALCULATION_TYPE+" = 'Loan Given' ;";
    private static final String RETURN_LOAN_QUERY="SELECT  "+DATE+" , "+AMOUNT+" , "+SOURCE_NAME+"  FROM "+DAILY_DOCUMENT_TABLE+" WHERE "+CALCULATION_TYPE+" = 'Loan Return' ;";
    private static final String PAID_LOAN_QUERY="SELECT  "+DATE+" , "+AMOUNT+" , "+SOURCE_NAME+"  FROM "+DAILY_DOCUMENT_TABLE+" WHERE "+CALCULATION_TYPE+" = 'Loan Paid' ;";

    private static final String TABLE_EXPENSE="expense";
    private static final String EXPENSE_ID="_id";
    private static final String EXPENSE_AMOUNT="expense_amount";
    private static final String TEXPENSE_SOURCE="expense_source";
    private static final String EXPENSE_DATE="expense_date";

    private static final String TABLE_LOAN="loan";
    private static final String LOAN_ID="_id";
    private static final String LOAN_AMOUNT="loan_amount";
    private static final String LOAN_HOLDER_NAME="loan_holder_name";
    private static final String LOAN_Type="loan_type";

    Context context;
    public Database(@Nullable Context context) {
        super(context, DATABSE_NAME, null, DB_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        try {
            db.execSQL(CREATE_INCOME_TABLE);
            //db.execSQL(CREATE_USER_TABLE);
        }catch (Exception e){
            Toast.makeText(context, ""+e, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //insert data on table
    public long insertData(Integer amount,String sourec,String calculation_type, String date, String month , String year){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(AMOUNT,amount);
        contentValues.put(CALCULATION_TYPE,calculation_type);
        contentValues.put(SOURCE_NAME,sourec);
        contentValues.put(DATE,date);
        contentValues.put(MONTH,month);
        contentValues.put(YEAR,year);
        long rowId= sqLiteDatabase.insert(DAILY_DOCUMENT_TABLE,null,contentValues);
        return  rowId;
    }
    public Cursor showIncome(){
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(INCOME_QUERY,null);
        return cursor;

    }
    public Cursor showExpense(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(EXPENSE_QUERY,null);
        return cursor;

    }
    public Cursor showTakeloan(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(TAKE_LOAN_QUERY,null);
        return cursor;

    }
    public Cursor showGiveloan(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(GIVE_LOAN_QUERY,null);
        return cursor;

    }
    public Cursor showReturnloan(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(RETURN_LOAN_QUERY,null);
        return cursor;

    }
    public Cursor showPaidloan(){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(PAID_LOAN_QUERY,null);
        return cursor;

    }
    public Cursor findDay( String day ,String type){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT   "+DATE+" , "+AMOUNT+" , "+SOURCE_NAME+"  FROM "+DAILY_DOCUMENT_TABLE+" WHERE "+CALCULATION_TYPE+" = '"+type+"' AND "+DATE+"= '"+day+"';",null);
        return cursor;

    }
    public Cursor find7Day( String day1, String day2, String day3, String day4, String day5, String day6, String day7 ,String type){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT   "+DATE+" , "+AMOUNT+" , "+SOURCE_NAME+"  FROM "+DAILY_DOCUMENT_TABLE+" WHERE "+CALCULATION_TYPE+" = '"+type+"' AND ("+DATE+" = '"+day1+"'"+" OR "+DATE+" = '"+day2+"'"+" OR "+DATE+" = '"+day3+"'"+" OR "+DATE+" = '"+day4+"'"+" OR "+DATE+" = '"+day5+"'"+" OR "+DATE+" = '"+day6+"'"+" OR "+DATE+" = '"+day7+"') ;",null);
        return cursor;

    }

    public Cursor findMonth( String month ,String type){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT   "+DATE+" , "+AMOUNT+" , "+SOURCE_NAME+"  FROM "+DAILY_DOCUMENT_TABLE+" WHERE "+CALCULATION_TYPE+" = '"+type+"' AND "+MONTH+"= '"+month+"';",null);
        return cursor;

    }


    public Cursor findYear( String year ,String type){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT   "+DATE+" , "+AMOUNT+" , "+SOURCE_NAME+"  FROM "+DAILY_DOCUMENT_TABLE+" WHERE "+CALCULATION_TYPE+" = '"+type+"' AND "+YEAR+"= '"+year+"';",null);
        return cursor;

    }

    public Cursor reportToday( String date){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT   "+DATE+" , "+AMOUNT+" , "+SOURCE_NAME+" , "+CALCULATION_TYPE+"  FROM "+DAILY_DOCUMENT_TABLE+" WHERE "+DATE+"= '"+date+"';",null);
        return cursor;

    }
    public Cursor report7Day( String day1, String day2, String day3, String day4, String day5, String day6, String day7){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT   "+DATE+" , "+AMOUNT+" , "+SOURCE_NAME+" , "+CALCULATION_TYPE+"  FROM "+DAILY_DOCUMENT_TABLE+" WHERE "+DATE+" = '"+day1+"'"+" OR "+DATE+" = '"+day2+"'"+" OR "+DATE+" = '"+day3+"'"+" OR "+DATE+" = '"+day4+"'"+" OR "+DATE+" = '"+day5+"'"+" OR "+DATE+" = '"+day6+"'"+" OR "+DATE+" = '"+day7+"' ;",null);
        return cursor;

    }
    public Cursor reportMonth( String month){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT   "+DATE+" , "+AMOUNT+" , "+SOURCE_NAME+" , "+CALCULATION_TYPE+"  FROM "+DAILY_DOCUMENT_TABLE+" WHERE "+MONTH+"= '"+month+"';",null);
        return cursor;

    }
    public Cursor reportYear( String year){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT   "+DATE+" , "+AMOUNT+" , "+SOURCE_NAME+" , "+CALCULATION_TYPE+"  FROM "+DAILY_DOCUMENT_TABLE+" WHERE "+YEAR+" = '"+year+"';",null);
        return cursor;

    }

    public long delete( ){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long k = sqLiteDatabase.delete(DAILY_DOCUMENT_TABLE,null,null);
        return k;

    }





}
