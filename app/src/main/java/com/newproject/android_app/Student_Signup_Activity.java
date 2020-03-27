package com.newproject.android_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import org.json.JSONObject;

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

public class Student_Signup_Activity extends AppCompatActivity implements  View.OnClickListener {

    EditText firstname,lastname,gender,department,section,year,id,pass;
   RadioGroup radioGroup;
    Button register;
    Spinner dep,sec;
    String deprt[]={"Computer Engineering","Computer Science","Accounting","TVTE"};
    String secs[]={"1","2","3"};
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__signup_);
      firstname=findViewById(R.id.first);
      lastname=findViewById(R.id.last);
      dep=findViewById(R.id.departement);
      sec=findViewById(R.id.section);
      ArrayAdapter adapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,secs);
      sec.setAdapter(adapter);
      radioGroup=findViewById(R.id.gendergroup);
        ArrayAdapter arrayAdapter=new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,deprt);
        dep.setAdapter(arrayAdapter);
      year=findViewById(R.id.year_register);
      id=findViewById(R.id.studentid);
      pass=findViewById(R.id.pass);
      register=findViewById(R.id.finish);
      register.setOnClickListener(this);
      int selecteditem=radioGroup.getCheckedRadioButtonId();
      RadioButton radioButton=findViewById(radioGroup.getCheckedRadioButtonId());
      String gender=radioButton.getText().toString();
    }

    @Override
    public void onClick(View view) {
            /*HashMap hashMap = new HashMap();
            hashMap.put("mobile","android");
            hashMap.put("First_name", firstname.getText().toString());
            hashMap.put("Last_name", lastname.getText().toString());
            hashMap.put("Gender",gender.getText().toString());
            hashMap.put("Departement", department.getText().toString());
            hashMap.put("Year", year.getText().toString());
            hashMap.put("Section", section.getText().toString());
            hashMap.put("StudentId", id.getText().toString());
            hashMap.put("Password", pass.getText().toString());
            PostResponseAsyncTask responseAsyncTask = new PostResponseAsyncTask(this,hashMap);
            responseAsyncTask.execute("https://10.0.3.2/Micro/studentsignup.php");

*/       RadioButton radioButton=findViewById(radioGroup.getCheckedRadioButtonId());
        dep=findViewById(R.id.departement);

        String gender=radioButton.getText().toString();
            String first=firstname.getText().toString();
            String last=lastname.getText().toString();
            String deps=dep.getSelectedItem().toString();
            String secs=sec.getSelectedItem().toString();
            String yer=year.getText().toString();
            String user=id.getText().toString();
            String pas=pass.getText().toString();
          new Signup().execute(first,last,gender,deps,secs,yer,user,pas);

    }


    private class Signup extends AsyncTask<String,String,String> {
        URL url=null;
        HttpURLConnection httpURLConnection;

        @Override
        protected String doInBackground(String... strings) {
            try {
                url=new URL("http://192.168.0.105/Micro/studentsignup.php");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setConnectTimeout(10000);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                Uri.Builder builder=new Uri.Builder()
                        .appendQueryParameter("First_name",strings[0])
                        .appendQueryParameter("Last_name",strings[1])
                        .appendQueryParameter("Gender",strings[2])
                        .appendQueryParameter("Departement",strings[3])
                        .appendQueryParameter("Section",strings[4])
                        .appendQueryParameter("Year",strings[5])
                        .appendQueryParameter("StudentId",strings[6])
                        .appendQueryParameter("Password",strings[7]);
                String qurey= builder.build().getEncodedQuery();
                BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                writer.write(qurey);
                writer.flush();
                writer.close();
                outputStream.close();
                httpURLConnection.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                int response_code=httpURLConnection.getResponseCode();
                if (response_code==HttpURLConnection.HTTP_OK){
                    InputStream inputStream= httpURLConnection.getInputStream();
                    BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder builder=new StringBuilder();
                    String line;
                    if (null!=(line=reader.readLine())){
                        builder.append(line);
                    }
                    return builder.toString();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            }
            return String.valueOf(httpURLConnection);
        }

        @Override
        protected void onPostExecute(String s) {
            if (s.equalsIgnoreCase("Registered successfully")){
                Toast.makeText(Student_Signup_Activity.this, "Registered sucessfully", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),Student_Login_Activity.class));
                finish();
            }else{
                Toast.makeText(Student_Signup_Activity.this, "please fill the form first", Toast.LENGTH_SHORT).show();
            }
        }
    }

}