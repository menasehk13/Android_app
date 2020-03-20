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

public class Student_Signup_Activity extends AppCompatActivity implements View.OnClickListener {
EditText firstname,lastname,password,confirm_password,registered_year,student_id;
Spinner gender,department,section;
Button submit;
String dep[]={"Computer Engineering","Computer Science","TVET","Accounting"};
String sec[]={"1","2","3","4"};
String gend[]={"MALE","FEMALE"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__signup_);
        firstname=findViewById(R.id.First_name);
        lastname=findViewById(R.id.last_name);
        password=findViewById(R.id.student_password);
        confirm_password=findViewById(R.id.confirm_password1);
        registered_year=findViewById(R.id.year_registered);
        student_id=findViewById(R.id.Micro_id);
        gender=findViewById(R.id.gender_spinner);
        department=findViewById(R.id.department_spinner);
        section=findViewById(R.id.section_spinner);
        submit=findViewById(R.id.submit);
        ArrayAdapter gender_Array=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,gend);
        gender.setAdapter(gender_Array);
        ArrayAdapter department_array=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,dep);
        department.setAdapter(department_array);
        ArrayAdapter Setion_array=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,sec);
        section.setAdapter(Setion_array);
        department.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                department.getSelectedItem();
            }
        });
        submit.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
       String Firstname=firstname.getText().toString();
       String Lastname=lastname.getText().toString();
       String Department=department.getSelectedItem().toString();
       String Gender=gender.getSelectedItem().toString();
       String Section=section.getSelectedItem().toString();
       String Pass=password.getText().toString();
       String year=registered_year.getText().toString();
       new Signin().execute(Firstname,Lastname,Department,year,Gender,Section,Pass);
    }

    private class Signin extends AsyncTask<String,String,String> {
        ProgressDialog progressDialog=new ProgressDialog(Student_Signup_Activity.this);
        HttpURLConnection connection;
        URL url=null;

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Loading");
            progressDialog.show();
            progressDialog.setCancelable(false);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                url=new URL("http://10.0.3.2/Micro/studentsignup.php");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                connection=(HttpURLConnection)url.openConnection();
                connection.setConnectTimeout(10000);
                connection.setReadTimeout(10000);
                connection.setRequestMethod("POST");
                connection.setDoInput(true);
                connection.setDoOutput(true);
                Uri.Builder builder=new Uri.Builder().appendQueryParameter("First_name",strings[0])
                        .appendQueryParameter("Last_name",strings[1])
                        .appendQueryParameter("Gender",strings[2])
                        .appendQueryParameter("Departement",strings[3])
                        .appendQueryParameter("Year",strings[4])
                        .appendQueryParameter("Section",strings[5])
                        .appendQueryParameter("StudentId",strings[6])
                        .appendQueryParameter("Password",strings[7]);
                String query=builder.build().getEncodedQuery();
                OutputStream outputStream=connection.getOutputStream();
                BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                outputStream.close();
                connection.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                int response_code=connection.getResponseCode();
                if (response_code==HttpURLConnection.HTTP_OK){
                    InputStream inputStream=connection.getInputStream();
                    BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder  builder=new StringBuilder();
                    String line=null;
                    if (null!=(line=reader.readLine())){
                        builder.append(line);
                    }
                    return builder.toString();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "Exception";
            }
            return String.valueOf(connection);
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            Toast.makeText(Student_Signup_Activity.this, s, Toast.LENGTH_SHORT).show();
        }
    }
}
