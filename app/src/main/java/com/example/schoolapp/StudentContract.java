package com.example.schoolapp;

import android.provider.BaseColumns;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class StudentContract {
    // To prevent someone from accidentally instantiating the contract class
    private StudentContract(){}
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + StudentEntry.TABLE_NAME + " (" + StudentEntry.COLUMN_NAME_STUDENT_ID + "TEXT PRIMARY KEY," + StudentEntry.COLUMN_NAME_FIRST_NAME + " TEXT," + StudentEntry.COLUMN_NAME_LAST_NAME + " TEXT," +  StudentEntry.COLUMN_NAME_EMAIL + " TEXT," + StudentEntry.COLUMN_NAME_PHONE + " TEXT," + StudentEntry.COLUMN_NAME_REGION + " TEXT," + StudentEntry.COLUMN_NAME_DISTRICT + " TEXT," + StudentEntry.COLUMN_NAME_WARD + " TEXT," + StudentEntry.COLUMN_NAME_BIRTH_DATE + " TEXT, "+ StudentEntry.COLUMN_NAME_PASSWORD + " TEXT,";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + StudentEntry.TABLE_NAME;

    /* Inner class that defines the table contents */
    public static class StudentEntry implements BaseColumns{
        public static final String TABLE_NAME = "student";
        public static final String COLUMN_NAME_STUDENT_ID = "student_id";
        public static final String COLUMN_NAME_FIRST_NAME = "first_name";
        public static final String COLUMN_NAME_LAST_NAME= "last_name";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_PHONE = "phone";
        public static final String COLUMN_NAME_REGION = "region";
        public static final String COLUMN_NAME_DISTRICT = "district";
        public static final String COLUMN_NAME_WARD = "ward";
        public static final String COLUMN_NAME_BIRTH_DATE = "ward";

        public static final String COLUMN_NAME_PASSWORD = "password";

    }
    public class StudentReaderDbHelper extends SQLiteOpenHelper {
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_NAME = "school.db";

        public StudentReaderDbHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        public void onCreate(SQLiteDatabase db) {

            db.execSQL(SQL_CREATE_ENTRIES);
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(SQL_DELETE_ENTRIES);
            onCreate(db);
        }


        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            super.onDowngrade(db, oldVersion, newVersion);
        }
    }
}
