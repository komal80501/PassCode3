package com.example.hp.passcode1.activities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DbHandler extends SQLiteOpenHelper {
    Context context;
    private static final String DATABASENAME = "PassCodeDb";
    private static final int DATABASEVERSION = 1;

    public DbHandler(Context context) {
        super(context, DATABASENAME, null, DATABASEVERSION);
    }


    public static final String TABLE_PASSCODE = "passcode";
    public static final String KEY_PASSCODE_NAME = "passcode_name";
    public static final String KEY_PASSCODE_TYPE = "passcode_type";
    public static final String KEY_START_TIME = "start_time";
    public static final String KEY_END_TIME = "end_time";
    public static final String KEY_PCODE = "pcode_";
    public static final String KEY_DAYS = "days";


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_PASSCODE_TABLE = "CREATE TABLE " + TABLE_PASSCODE + "("
                + KEY_PASSCODE_NAME + " STRING PRIMARY KEY ,"
                + KEY_PASSCODE_TYPE + " TEXT,"
                + KEY_START_TIME + " TEXT,"
                + KEY_END_TIME + " TEXT,"
                + KEY_PCODE + " TEXT,"
                + KEY_DAYS + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_PASSCODE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public Boolean insert_passcode(PassCode passCodeObj)

    {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();

        contentValues.put(KEY_PASSCODE_NAME, passCodeObj.getpName());
        contentValues.put(KEY_PASSCODE_TYPE, passCodeObj.getpType());
        contentValues.put(KEY_START_TIME, passCodeObj.getStartTime());
        contentValues.put(KEY_END_TIME, passCodeObj.getEndTime());
        contentValues.put(KEY_PCODE, passCodeObj.getpCode());
        contentValues.put(KEY_DAYS, passCodeObj.getDays());

        long result = db.insert(TABLE_PASSCODE, null, contentValues);

        if (result == -1)

            return false;

        else

            return true;
    }


    public ArrayList<PassCode> getallPascode() {

        ArrayList<PassCode> passCodeList = new ArrayList<PassCode>();
        String SelectrQuery = "SELECT * from " + TABLE_PASSCODE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(SelectrQuery, null);

        if (cursor.moveToNext())

        {
            do {

                PassCode passCode = new PassCode();
                passCode.setpName(cursor.getString(cursor.getColumnIndex(KEY_PASSCODE_NAME)));
                passCode.setpType(cursor.getString(cursor.getColumnIndex(KEY_PASSCODE_TYPE)));
                passCode.setStartTime(cursor.getString(cursor.getColumnIndex(KEY_START_TIME)));
                passCode.setEndTime(cursor.getString(cursor.getColumnIndex(KEY_END_TIME)));
                passCode.setDays(cursor.getString(cursor.getColumnIndex(KEY_DAYS)));


                passCodeList.add(passCode);
            } while (cursor.moveToNext());

        }
        return passCodeList;
    }


}