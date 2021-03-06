package com.example.schoolapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class AdminActivity extends AppCompatActivity {
    DatabaseHelp databaseHelp;

    EditText editDateInput;
    DatePickerDialog datePickerDialog;
    Button saveBtn, studentBtn, staffBtn;

//    input variables
    EditText editFirstName;
    EditText editLastName;
    EditText editRegNumber;
    EditText editEmail;
    EditText editPhone;

//    Radio button variables
    RadioGroup radioGroupGender;
    RadioButton radioButtonMale;
    RadioButton radioButtonFemale;

//    Location
    private LocationDatabaseHelper mLocationDBHelper;
    private SQLiteDatabase mDb;

//    spinners
    Spinner regionSpinner;
    Spinner districtSpinner;
    Spinner wardSpinner;
    String selectedRegion;
    String selectedDistrict;
    String selectedWard;
    String selectedGender;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

//        location
        mLocationDBHelper = new LocationDatabaseHelper(this);
        try {
            mLocationDBHelper.updateDataBase();
        } catch (IOException mIOException){
            throw new Error("UnableToUpdateDatabase");
        }
        try{
            mDb = mLocationDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException){
            throw mSQLException;
        }



        editDateInput = (EditText) findViewById(R.id.date_input_admin);

        // Button
        saveBtn = (Button) findViewById(R.id.save_btn_admin);
        studentBtn =(Button) findViewById(R.id.student_btn);
        staffBtn = (Button) findViewById(R.id.staff_btn);

        editFirstName = (EditText) findViewById(R.id.first_name_input_admin);
        editLastName = (EditText) findViewById(R.id.last_name_input_admin);
        editRegNumber = (EditText) findViewById(R.id.registration_number_input_admin);
        editEmail = (EditText) findViewById(R.id.email_input_admin);
        editPhone = (EditText) findViewById(R.id.phone_input_admin);
        radioGroupGender = (RadioGroup) findViewById(R.id.radio_grp_gender_admin);
//        radioButtonMale = (RadioButton) findViewById(R.id.radio_btn_male_admin);
//        radioButtonFemale =(RadioButton) findViewById(R.id.radio_btn_female_admin);

        regionSpinner = (Spinner) findViewById(R.id.spinner_region_admin);
        districtSpinner= (Spinner) findViewById(R.id.spinner_district_admin);
        wardSpinner = (Spinner) findViewById(R.id.spinner_ward_admin);


        //initial state
        editRegNumber.setEnabled(false);
        editRegNumber.setTextColor(Color.BLACK);
        studentBtn.setBackgroundColor(Color.WHITE);
        staffBtn.setBackgroundColor(Color.LTGRAY);
        studentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentBtn.setBackgroundColor(Color.WHITE);
                staffBtn.setBackgroundColor(Color.LTGRAY);
                databaseHelp.setStudentOrTeacher("Student");
                editRegNumber.setText(databaseHelp.registrationNumberGenerate());
            }
        });
        staffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentBtn.setBackgroundColor(Color.LTGRAY);
                staffBtn.setBackgroundColor(Color.WHITE);
                databaseHelp.setStudentOrTeacher("Teacher");
                editRegNumber.setText(databaseHelp.registrationNumberGenerate());
            }
        });

//
//        check selected radio button

        radioGroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                selectedGender = (
                        (RadioButton) findViewById(radioGroupGender.getCheckedRadioButtonId())
                ).getText().toString();

            }
        });



        //        Database initialization
        databaseHelp = new DatabaseHelp(this);
        editRegNumber.setText(databaseHelp.registrationNumberGenerate());

        //getting the date
        final Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(AdminActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                editDateInput.setText(dayOfMonth + "/" + month + "/" + year);
            }
        }, mYear, mMonth, mDay);


//      Saving the date input on the editDateInput variable
        editDateInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });


//        location spinners
        final ArrayList<String> wardSpinnerArray = new ArrayList<>();
        wardSpinnerArray.addAll(mLocationDBHelper.getWards());

        final ArrayAdapter<String> wardAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, wardSpinnerArray);
        wardSpinner.setAdapter(wardAdapter);
        districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedWard= wardSpinnerArray.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final ArrayList<String> districtSpinnerArray =new ArrayList<>();
        districtSpinnerArray.addAll(mLocationDBHelper.getDistricts());

        final ArrayAdapter<String> districtAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,districtSpinnerArray);
        districtSpinner.setAdapter(districtAdapter);

        districtSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDistrict = districtSpinnerArray.get(position);
                mLocationDBHelper.setSelectedDistrict(selectedDistrict);

                wardSpinnerArray.clear();
                wardSpinnerArray.addAll(mLocationDBHelper.getWards());
                wardAdapter.notifyDataSetChanged();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final ArrayList<String> regionSpinnerArray = new ArrayList<>();
        regionSpinnerArray.addAll((mLocationDBHelper.getRegions()));

        final ArrayAdapter<String> regionAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,regionSpinnerArray);
        regionSpinner.setAdapter(regionAdapter);

        regionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedRegion = regionSpinnerArray.get(position);
                mLocationDBHelper.setSelectedRegion(selectedRegion);

                districtSpinnerArray.clear();
                districtSpinnerArray.addAll(mLocationDBHelper.getDistricts());
                districtAdapter.notifyDataSetChanged();
//
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });


//        Saving the registration information using the save button

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                databaseHelp.insertData(
                        editRegNumber.getText().toString(),
                        editFirstName.getText().toString(),
                        editLastName.getText().toString(),
                        editEmail.getText().toString(),
                        editPhone.getText().toString(),
                        selectedRegion,
                        selectedDistrict,
                        selectedWard,
                        editDateInput.getText().toString(),
                        selectedGender
                        );

                editFirstName.setText("");
                editLastName.setText("");
                editEmail.setText("");
                editPhone.setText("");

                editRegNumber.setText(databaseHelp.registrationNumberGenerate());
                Intent viewActivity = new Intent(AdminActivity.this, StudentView.class);
                startActivity(viewActivity);

                Toast.makeText(getApplicationContext(),"Information saved", Toast.LENGTH_SHORT).show();

            }
        });

    }
}
