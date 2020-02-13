package com.example.schoolapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class StudentView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view);

        TextView textView =findViewById(R.id.view_data);
        DatabaseHelp db = new DatabaseHelp(this);

        String data = db.getData();
        textView.setText(data);
        textView.setMovementMethod(new ScrollingMovementMethod());

    }
}
