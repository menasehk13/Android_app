package com.newproject.android_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class Student_Signup_Activity extends AppCompatActivity implements AsyncResponse, View.OnClickListener {
    EditText firstname, lastname, password, confirm_password, registered_year, student_id;
    Spinner gender, department, section;
    Button submit;
    String dep[] = {"Computer Engineering", "Computer Science", "TVET", "Accounting"};
    String sec[] = {"1", "2", "3", "4"};
    String gend[] = {"MALE", "FEMALE"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__signup_);
        firstname = findViewById(R.id.First_name);
        lastname = findViewById(R.id.last_name);
        password = findViewById(R.id.student_password);
        confirm_password = findViewById(R.id.confirm_password1);
        registered_year = findViewById(R.id.year_registered);
        student_id = findViewById(R.id.Micro_id);
        gender = findViewById(R.id.gender_spinner);
        department = findViewById(R.id.department_spinner);
        section = findViewById(R.id.section_spinner);
        submit = findViewById(R.id.submit);
        ArrayAdapter gender_Array = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, gend);
        gender.setAdapter(gender_Array);
        ArrayAdapter department_array = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, dep);
        department.setAdapter(department_array);
        ArrayAdapter Setion_array = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sec);
        section.setAdapter(Setion_array);
        submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
      /*  String Firstname = firstname.getText().toString();
        String Lastname = lastname.getText().toString();
        String Department = department.getSelectedItem().toString();
        String Gender = gender.getSelectedItem().toString();
        String Section = section.getSelectedItem().toString();
        String Pass = password.getText().toString();
        String year = registered_year.getText().toString();
        */
        HashMap hashMap=new HashMap();
        hashMap.put("First_name",firstname.getText().toString());
        hashMap.put("Last_name",lastname.getText().toString());
        hashMap.put("Gender",gender.getSelectedItem().toString());
        hashMap.put("Departement",department.getSelectedItem().toString());
        hashMap.put("Section",section.getSelectedItem().toString());
        hashMap.put("StudentId",student_id.getText().toString());
        hashMap.put("Password",password.getText().toString());
        PostResponseAsyncTask responseAsyncTask=new PostResponseAsyncTask(this,hashMap);
        responseAsyncTask.execute("http://192.168.0.104/Micro/studentsignup.php");
    }

    @Override
    public void processFinish(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}