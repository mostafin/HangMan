package com.example.maciej.wisielec;

import android.app.TaskStackBuilder;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import java.util.Random;
import java.util.RandomAccess;

/**
 * Created by Maciej on 10/03/2017.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Words.db";
    public static final String TABLE_NAME = "WORDS_TABLE";
    public static final String COLUMN_1 = "ID";
    public static final String COLUMN_2 = "WORDS";
    public static final String COLUMN_3 = "WORDS_CATEGORY";
    public static final String COLUMN_4 ="LETTER_AMOUNT";

    public static final String[]  All_COLUMNS = {COLUMN_1,COLUMN_2,COLUMN_3,COLUMN_4};

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null , 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("Nie OK","Ok OOK");
        try {
            db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, WORDS TEXT ,WORDS_CATEGORY TEXT, LETTER_AMOUNT INTEGER);");
            Log.d("OK","Ok OOK");
        }catch(SQLException e){
            Log.d("Nie OK",e.getMessage());
        }

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String word , String category){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_2,word);
        contentValues.put(COLUMN_3,category);
        contentValues.put(COLUMN_4,word.length());

        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result == -1){
            return false;
        }
        else {
            db.close();
            return true;
        }
    }
    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }

    public void deleteAll(){
        Cursor cursor = getAllRows();
        long rowId = cursor.getColumnIndexOrThrow(COLUMN_1);
        if(cursor.moveToFirst())
        do{
            deleteRow(cursor.getLong((int)rowId));
        }while(cursor.moveToNext());

        cursor.close();
    }

    public Cursor getAllRows(){
        SQLiteDatabase db = this.getWritableDatabase();
        String where = null;
        Cursor cursor = db.query(true,TABLE_NAME,All_COLUMNS,where,null,null,null,null,null);
        if(cursor != null)
        {
            cursor.moveToFirst();
        }
        return cursor;
    }

    public boolean deleteRow(long rowId){
        SQLiteDatabase db = this.getWritableDatabase();
        String where = COLUMN_1 +"="+rowId;
        return db.delete(TABLE_NAME,where,null)!=0;
    }

    public Cursor getWord(String category){
        SQLiteDatabase db = this.getWritableDatabase();
        String [] columns ={COLUMN_1,COLUMN_2,COLUMN_4};
        Cursor cursor;
        Random rand = new Random();
        int digit =rand.nextInt(50)+1;
        if(category.equals("Wszystkie"))
        {
             cursor = db.query(TABLE_NAME,columns,null,null,null,null,null);
            for (int i = 0; i < digit; i++) {
                cursor.moveToNext();
                if (!cursor.moveToNext())
                    cursor.moveToFirst();
            }
        }else {
            cursor = db.query(TABLE_NAME, columns, COLUMN_3 + " = '" + category + "'", null, null, null, null);

            for (int i = 0; i < digit; i++) {
                cursor.moveToNext();
                if (!cursor.moveToNext())
                    cursor.moveToFirst();
            }
        }

        //cursor.close();
        return cursor;
    }

}
