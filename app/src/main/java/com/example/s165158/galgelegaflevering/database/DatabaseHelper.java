package com.example.s165158.galgelegaflevering.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * **IGNORE THIS CLASS**, Was an attempt to use SqLite instead of PreferenceManager, but i was unable to implement it satisfyingly
 * Created by Justin on 07/11/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String TABLE_NAME = "score_table";
    private static final String COL1 = "ID";
    private static final String COL2 = "name";
    private static final String COL3 = "word";
    private static final String COL4 = "score";
    private static final String COL5 = "date";



    public DatabaseHelper(Context context) {
        super(context, TABLE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 +" TEXT, " + COL3 +" TEXT, "+COL4 +" INTEGER, "+COL5 +" DATE)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_NAME + "'");

        onCreate(db);
    }

    public boolean addData(String name, String word, int score, String date) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, name);
        Log.d(TAG, "addData: Adding " + name + " to " + TABLE_NAME);
        contentValues.put(COL3, word);
        Log.d(TAG, "addData: Adding " + word + " to " + TABLE_NAME);
        contentValues.put(COL4, score);
        Log.d(TAG, "addData: Adding " + score + " to " + TABLE_NAME);
        contentValues.put(COL5, date);
        Log.d(TAG, "addData: Adding " + date + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if data as inserted incorrectly it will return -1
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Returns all the data from database but the ID
     * @return
     */

    public ArrayList<String> getData() {
        String selectQuery = "SELECT  * FROM " + TABLE_NAME;
        SQLiteDatabase db  = this.getReadableDatabase();
        Cursor cursor      = db.rawQuery(selectQuery, null);
        String[] data      = null;
        ArrayList<String> data2 = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
//               data2.add(cursor.getInt(1);
               data2.add(String.valueOf(cursor.getInt(0)));
               data2.add(cursor.getString(1));
               data2.add(cursor.getString(2));
               data2.add(String.valueOf(cursor.getInt(4)));
               data2.add(cursor.getString(4));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return data2;
    }


    public String[] getColumn(int columnIndex){
        ArrayList<String> strings = getData();

        int arraylistsize = strings.size();
        int arraysize = strings.size()/5;

        String[] ColumnArray  = new String[arraysize];


        for(int i=0; i < arraylistsize; i+=5) {
            int row = i/5;
            ColumnArray[row] = strings.get(i+columnIndex);


        }
        return ColumnArray;
    }







    /**
     * Returns only the ID that matches the name passed in
     * @param name
     * @return
     */
    public Cursor getItemID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COL1 + " FROM " + TABLE_NAME +
                " WHERE " + COL2 + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    /**
     * Updates the name field
     * @param newName
     * @param id
     * @param oldName
     */
    public void updateName(String newName, int id, String oldName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL2 +
                " = '" + newName + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + oldName + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newName);
        db.execSQL(query);
    }

    /**
     * Delete from database
     * @param id
     * @param name
     */
    public void deleteName(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL1 + " = '" + id + "'" +
                " AND " + COL2 + " = '" + name + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + name + " from database.");
        db.execSQL(query);
    }
    public void updateWord(String newWord, int id, String oldWord){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL3 +
                " = '" + newWord + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL3 + " = '" + oldWord + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newWord);
        db.execSQL(query);
    }

    /**
     * Delete from database
     * @param id
     * @param word
     */
    public void deleteWord(int id, String word){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL1 + " = '" + id + "'" +
                " AND " + COL3 + " = '" + word + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + word + " from database.");
        db.execSQL(query);
    }

    public void updateScore(int newScore, int id, int oldScore){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL4 +
                " = '" + newScore + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL4 + " = '" + oldScore + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newScore);
        db.execSQL(query);
    }

    /**
     * Delete from database
     * @param id
     * @param score
     */
    public void deleteScore(int id, int score){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL1 + " = '" + id + "'" +
                " AND " + COL4 + " = '" + score + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + score + " from database.");
        db.execSQL(query);
    }

    public void updateDate(Date newDate, int id, Date oldDate){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_NAME + " SET " + COL5 +
                " = '" + newDate + "' WHERE " + COL1 + " = '" + id + "'" +
                " AND " + COL5 + " = '" + oldDate + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newDate);
        db.execSQL(query);
    }

    /**
     * Delete from database
     * @param id
     * @param date
     */
    public void deleteScore(int id, Date date){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COL1 + " = '" + id + "'" +
                " AND " + COL5 + " = '" + date + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + date + " from database.");
        db.execSQL(query);
    }

}











