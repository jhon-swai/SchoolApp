package com.example.schoolapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelp extends SQLiteOpenHelper {

    SQLiteDatabase db;

    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "school.db";


    public static final String TABLE_NAME = "student";
    public static final String COLUMN_NAME_STUDENT_ID = "student_id";
    public static final String COLUMN_NAME_FIRST_NAME = "first_name";
    public static final String COLUMN_NAME_LAST_NAME= "last_name";
    public static final String COLUMN_NAME_EMAIL = "email";
    public static final String COLUMN_NAME_PHONE = "phone";
    public static final String COLUMN_NAME_REGION = "region";
    public static final String COLUMN_NAME_DISTRICT = "district";
    public static final String COLUMN_NAME_WARD = "ward";
    public static final String COLUMN_NAME_BIRTH_DATE = "birth_date";
    public static final String COLUMN_NAME_GENDER = "gender";

    public static final String COLUMN_NAME_PASSWORD = "password";

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_NAME_STUDENT_ID + "TEXT PRIMARY KEY," + COLUMN_NAME_FIRST_NAME + " TEXT," + COLUMN_NAME_LAST_NAME + " TEXT," +  COLUMN_NAME_EMAIL + " TEXT," + COLUMN_NAME_PHONE + " TEXT," + COLUMN_NAME_REGION + " TEXT," + COLUMN_NAME_DISTRICT + " TEXT," + COLUMN_NAME_WARD + " TEXT," + COLUMN_NAME_BIRTH_DATE + " TEXT, "+ COLUMN_NAME_PASSWORD + " TEXT ," + COLUMN_NAME_GENDER + " TEXT )";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TABLE_NAME;

    public DatabaseHelp(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);*/
    }


    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    public long insertStudent(String regNumber, String fName, String lName, String email, String phone, String region, String district, String ward, String birthDate,String password, String gender){
        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME_STUDENT_ID,regNumber);
        values.put(COLUMN_NAME_FIRST_NAME, fName);
        values.put(COLUMN_NAME_LAST_NAME,lName);
        values.put(COLUMN_NAME_EMAIL,email);
        values.put(COLUMN_NAME_PHONE, phone);
        values.put(COLUMN_NAME_REGION, region);
        values.put(COLUMN_NAME_DISTRICT, district);
        values.put(COLUMN_NAME_WARD, ward);
        values.put(COLUMN_NAME_BIRTH_DATE, birthDate);
        values.put(COLUMN_NAME_PASSWORD, password);
        values.put(COLUMN_NAME_GENDER, gender);

        return db.insert(TABLE_NAME,null,values);
    }

/*    public String getStudentData(){
        db= this.getReadableDatabase();
        String [] columns = new String[] { COLUMN_NAME_STUDENT_ID,COLUMN_NAME_FIRST_NAME,COLUMN_NAME_LAST_NAME, COLUMN_NAME_EMAIL, COLUMN_NAME_PHONE,COLUMN_NAME_REGION,COLUMN_NAME_DISTRICT, COLUMN_NAME_WARD, COLUMN_NAME_BIRTH_DATE, COLUMN_NAME_GENDER};

        Cursor cursor = db.query(db.query(TABLE_NAME,columns,null,null,null,null,null);

    }*/
//    method to create the
/*    public void regionLocation(){

    }*/

}
