package com.example.schoolapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.lang.annotation.Target;
import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class DatabaseHelp extends SQLiteOpenHelper {

    SQLiteDatabase db;

    public static final int DATABASE_VERSION = 7;
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

    private static final String SQL_CREATE_STUDENT_ENTRIES = "CREATE TABLE " + STUDENT_TABLE + " (" + COLUMN_NAME_STUDENT_ID + " TEXT PRIMARY KEY," + COLUMN_NAME_FIRST_NAME + " TEXT," + COLUMN_NAME_LAST_NAME + " TEXT," +  COLUMN_NAME_EMAIL + " TEXT," + COLUMN_NAME_PHONE + " TEXT," + COLUMN_NAME_REGION + " TEXT," + COLUMN_NAME_DISTRICT + " TEXT," + COLUMN_NAME_WARD + " TEXT," + COLUMN_NAME_BIRTH_DATE + " TEXT, "+ COLUMN_NAME_PASSWORD + " TEXT ," + COLUMN_NAME_GENDER + " TEXT )";
    private static final String SQL_DELETE_STUDENT_ENTRIES = "DROP TABLE IF EXISTS " + STUDENT_TABLE;

    private static final String SQL_CREATE_STAFF_ENTRIES = "CREATE TABLE " + STAFF_TABLE + " (" + COLUMN_NAME_STAFF_ID + " TEXT PRIMARY KEY," + COLUMN_NAME_FIRST_NAME + " TEXT," + COLUMN_NAME_LAST_NAME + " TEXT," +  COLUMN_NAME_EMAIL + " TEXT," + COLUMN_NAME_PHONE + " TEXT," + COLUMN_NAME_REGION + " TEXT," + COLUMN_NAME_DISTRICT + " TEXT," + COLUMN_NAME_WARD + " TEXT," + COLUMN_NAME_BIRTH_DATE + " TEXT, "+ COLUMN_NAME_PASSWORD + " TEXT ," + COLUMN_NAME_GENDER + " TEXT )";
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

    public void insertData(String regNumber, String fName, String lName, String email, String phone, String region, String district, String ward, String birthDate, String gender){
        db = this.getWritableDatabase();

        String password = lName + fName;
        String table_name = STUDENT_TABLE;
        ContentValues values = new ContentValues();

//        Code to store the id of the staff or the student
        if(studentOrTeacher == "Teacher") {
            table_name = STAFF_TABLE;
            values.put(COLUMN_NAME_STAFF_ID,regNumber);
        }else {
            values.put(COLUMN_NAME_STUDENT_ID,regNumber);
        }

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
        db.insert(table_name,null,values);
        db.close();
    }
    public HashMap<String,String> getStudent(String userName, String password){
        db = this.getReadableDatabase();
        HashMap<String, String> hashMap = new HashMap<>();
        String selectArgs[] = {userName};

        String columns [] = {COLUMN_NAME_STUDENT_ID, COLUMN_NAME_PASSWORD, COLUMN_NAME_FIRST_NAME, COLUMN_NAME_LAST_NAME};
        try{
            Cursor cursor = db.query(STUDENT_TABLE,columns, COLUMN_NAME_STUDENT_ID + "= ?", selectArgs,null,null, null);
//            check the students table if it has the student
            if(cursor != null){
                cursor.moveToFirst();
                int iRegNum = cursor.getColumnIndex(COLUMN_NAME_STUDENT_ID);
                int iPassword = cursor.getColumnIndex(COLUMN_NAME_PASSWORD);
                int iFname = cursor.getColumnIndex(COLUMN_NAME_FIRST_NAME);
                int iLname = cursor.getColumnIndex(COLUMN_NAME_LAST_NAME);
//                int iDOB = cursor.getColumnIndex(COLUMN_NAME_BIRTH_DATE);

                hashMap.put("regNumb", cursor.getString(iRegNum));
                hashMap.put("password", cursor.getString(iPassword));
                hashMap.put("fname", cursor.getString(iFname));
                hashMap.put("lname", cursor.getString(iLname));
//                hashMap.put("DOB", cursor.getString(iDOB));
                hashMap.put("role", "student");

            }

        } catch (Exception e){
        }
        db.close();
        return hashMap;

    }
    public String getData(){
        db = this.getReadableDatabase();
        String [] columns= new String [] {COLUMN_NAME_STUDENT_ID, COLUMN_NAME_FIRST_NAME, COLUMN_NAME_LAST_NAME};
        String result = "";

        try{
            Cursor cursor = db.query(STUDENT_TABLE, columns,null,null,null, null, null);
            int iReg = cursor.getColumnIndex(COLUMN_NAME_STUDENT_ID);
            int ifname = cursor.getColumnIndex(COLUMN_NAME_FIRST_NAME);
            int ilname = cursor.getColumnIndex(COLUMN_NAME_LAST_NAME);

            for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
                result = result +
                        "ID: " + cursor.getString(iReg) + "\n" +
                        "Name: " + cursor.getString(ifname) + " "+ cursor.getString(ilname) + "\n \n";

            }
        } catch (Exception e){}



        db.close();
        return result;
    }

    public HashMap<String,String> getStaff(String userName, String password){
        db = this.getReadableDatabase();
        HashMap<String, String> hashMap = new HashMap<>();
        String columns [] = {COLUMN_NAME_STAFF_ID, COLUMN_NAME_PASSWORD , COLUMN_NAME_FIRST_NAME, COLUMN_NAME_LAST_NAME};
        String [] selectArg = {userName};
        try{
// check the teachers table if it has the teacher
                Cursor cursor = db.query(STAFF_TABLE,columns, COLUMN_NAME_STAFF_ID + "= ?" , selectArg,null,null, null);
                if(cursor != null){
                    cursor.moveToFirst();
                    int iRegNum = cursor.getColumnIndex(COLUMN_NAME_STAFF_ID);
                    int iPassword = cursor.getColumnIndex(COLUMN_NAME_PASSWORD);
                    int iFname = cursor.getColumnIndex(COLUMN_NAME_FIRST_NAME);
                    int iLname = cursor.getColumnIndex(COLUMN_NAME_LAST_NAME);


                    hashMap.put("regNumb", cursor.getString(iRegNum));
                    hashMap.put("password", cursor.getString(iPassword));
                    hashMap.put("fname", cursor.getString(iFname));
                    hashMap.put("lname", cursor.getString(iLname));
                    hashMap.put("role", "staff");

                }

        } catch (Exception e){
        }
        db.close();
        return hashMap;

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
        String regNumberString;



        String columns[]= {"Student"};
        columns[0] = COLUMN_NAME_STUDENT_ID;
        String table_name = STUDENT_TABLE;
        int registrationNumberFromCursorString = 0;

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
            cursor = db.query(table_name,columns,null,null,null,null,null);

            count = cursor.getColumnCount();

            if (cursor.moveToLast()) {
                cursorString = cursor.getString(count-1);
                cursor.close();
            }

        } catch (Exception e){ } finally {

        }

//        Taking the last five digits of the registration number
        if(table_name == STUDENT_TABLE){
            if(cursorString != null) {
                cursorString = cursorString.substring(8);
                registrationNumberFromCursorString = Integer.parseInt(cursorString);
                registrationNumberFromCursorString++;
                assignedRegistrationNumber = registrationNumberFromCursorString;

            } else {
                assignedRegistrationNumber++;
            }

            regNumberString= Integer.toString(year) + "-04-" + String.format("%05d",assignedRegistrationNumber);
        }else {
            if(cursorString != null) {
                registrationNumberFromCursorString = Integer.parseInt(cursorString);
                registrationNumberFromCursorString++;


                assignedRegistrationNumber = registrationNumberFromCursorString;

            } else {
                assignedRegistrationNumber++;
            }

            regNumberString = String.format("%05d",assignedRegistrationNumber);
        }

        db.close();
        return regNumberString;
    }

}
