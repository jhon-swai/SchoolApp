package com.example.schoolapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.annotation.Target;
import java.util.Calendar;

public class DatabaseHelp extends SQLiteOpenHelper {

    SQLiteDatabase db;

    public static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "school.db";
    private static final String STAFF_TABLE = "staff";
    public static final String COLUMN_NAME_STAFF_ID = "staff_id";


    public static final String STUDENT_TABLE = "student";
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

    private static final String SQL_CREATE_STUDENT_ENTRIES = "CREATE TABLE " + STUDENT_TABLE + " (" + COLUMN_NAME_STUDENT_ID + "TEXT PRIMARY KEY," + COLUMN_NAME_FIRST_NAME + " TEXT," + COLUMN_NAME_LAST_NAME + " TEXT," +  COLUMN_NAME_EMAIL + " TEXT," + COLUMN_NAME_PHONE + " TEXT," + COLUMN_NAME_REGION + " TEXT," + COLUMN_NAME_DISTRICT + " TEXT," + COLUMN_NAME_WARD + " TEXT," + COLUMN_NAME_BIRTH_DATE + " TEXT, "+ COLUMN_NAME_PASSWORD + " TEXT ," + COLUMN_NAME_GENDER + " TEXT )";
    private static final String SQL_DELETE_STUDENT_ENTRIES = "DROP TABLE IF EXISTS " + STUDENT_TABLE;

    private static final String SQL_CREATE_STAFF_ENTRIES = "CREATE TABLE " + STAFF_TABLE + " (" + COLUMN_NAME_STAFF_ID + "TEXT PRIMARY KEY," + COLUMN_NAME_FIRST_NAME + " TEXT," + COLUMN_NAME_LAST_NAME + " TEXT," +  COLUMN_NAME_EMAIL + " TEXT," + COLUMN_NAME_PHONE + " TEXT," + COLUMN_NAME_REGION + " TEXT," + COLUMN_NAME_DISTRICT + " TEXT," + COLUMN_NAME_WARD + " TEXT," + COLUMN_NAME_BIRTH_DATE + " TEXT, "+ COLUMN_NAME_PASSWORD + " TEXT ," + COLUMN_NAME_GENDER + " TEXT )";
    private static final String SQL_DELETE_STAFF_ENTRIES = "DROP TABLE IF EXISTS " + STAFF_TABLE;





    private String studentOrTeacher = "Student";

    public void setStudentOrTeacher(String studentOrTeacher) {
        this.studentOrTeacher = studentOrTeacher;
    }

    public DatabaseHelp(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_STUDENT_ENTRIES);
        db.execSQL(SQL_CREATE_STAFF_ENTRIES);

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_STUDENT_ENTRIES);
        db.execSQL(SQL_DELETE_STAFF_ENTRIES);
        onCreate(db);
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

        return db.insert(STUDENT_TABLE,null,values);
    }

/*    public String getStudentData(){
        db= this.getReadableDatabase();
        String [] columns = new String[] { COLUMN_NAME_STUDENT_ID,COLUMN_NAME_FIRST_NAME,COLUMN_NAME_LAST_NAME, COLUMN_NAME_EMAIL, COLUMN_NAME_PHONE,COLUMN_NAME_REGION,COLUMN_NAME_DISTRICT, COLUMN_NAME_WARD, COLUMN_NAME_BIRTH_DATE, COLUMN_NAME_GENDER};

        Cursor cursor = db.query(db.query(STUDENT_TABLE,columns,null,null,null,null,null);

    }*/
//    method to create the
/*    public void regionLocation(){

    }*/

    public String registrationNumberGenerate(){
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int min = 0;
        int max = 99999;
        int assignedRegistrationNumber= min;

        String columns[]= {"Student"};
        columns[0] = COLUMN_NAME_STUDENT_ID;
        String table_name = STUDENT_TABLE;
        int registrationNumberFromCursorString;

        if(studentOrTeacher == "Student"){
            table_name = STUDENT_TABLE;
            columns[0] = COLUMN_NAME_STUDENT_ID;
        }
        else {
            table_name = STAFF_TABLE;
            columns[0] = COLUMN_NAME_STAFF_ID;
        }

//        The generated registration number

        db = this.getReadableDatabase();
        int count;
        String cursorString = null;

        Cursor cursor;

        try {
            cursor = db.query(STUDENT_TABLE,columns,null,null,null,null,null);

            count = cursor.getColumnCount();


            if (cursor.moveToFirst()) {
                cursorString = cursor.getString(0);
//                cursorString = cursor.getString(count-1);
            }

        } catch (Exception e){ } finally {

        }
//        Taking the last five digits of the registration number
        if(cursorString != null) {
            cursorString = cursorString.substring(8);
            registrationNumberFromCursorString = Integer.parseInt(cursorString);
            registrationNumberFromCursorString++;
            assignedRegistrationNumber = registrationNumberFromCursorString;

        }else {
            assignedRegistrationNumber++;
        }

        db.close();


        String regNumberString = Integer.toString(year) + "-04-" + String.format("%05d",assignedRegistrationNumber);
        return regNumberString;
    }



}
