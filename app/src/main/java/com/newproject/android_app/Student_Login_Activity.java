package com.newproject.android_app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;

import org.json.JSONException;
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
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class Student_Login_Activity extends AppCompatActivity implements View.OnClickListener {
EditText studentid,studentpassword;
Button studlogin;
TextView register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__login_);
        studentid=findViewById(R.id.student_username);
        studentpassword=findViewById(R.id.student_password);
        studlogin=findViewById(R.id.studlogin);
        register=findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             startActivity(new Intent(getApplicationContext(),Student_Signup_Activity.class));
             finish();
            }
        });
        studlogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
 String Student_id=studentid.getText().toString();
 String Student_password=studentpassword.getText().toString();
      new Login().execute(Student_id,Student_password);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
        super.onBackPressed();
    }

    private class Login extends AsyncTask<String,String,String> {
        ProgressDialog pd=new ProgressDialog(Student_Login_Activity.this);
        URL url=null;
        HttpURLConnection connection;
        @Override
        protected void onPreExecute() {
           pd.setMessage("Loading");
           pd.show();
           pd.setCancelable(false);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {
            try {
                url=new URL("http://192.168.0.105/Micro/studentlogin.php");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                connection=(HttpURLConnection)url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                connection.setReadTimeout(10000);
                connection.setConnectTimeout(10000);
                Uri.Builder builder=new Uri.Builder().appendQueryParameter("StudentId",strings[0])
                        .appendQueryParameter("Password",strings[1]);
                OutputStream outputStream=connection.getOutputStream();
                BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
                String query=builder.build().getEncodedQuery();
                writer.write(query);
                writer.flush();
                writer.close();
                outputStream.close();
                connection.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                int reseponse_code=connection.getResponseCode();
                if (reseponse_code==HttpURLConnection.HTTP_OK){
                    InputStream inputStream=connection.getInputStream();
                    BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder builder=new StringBuilder();
                    String line;
                    if(null !=(line=reader.readLine())){
                        builder.append(line);
                    }
                    return builder.toString();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            }

            return String.valueOf(connection);
        }

        @Override
        protected void onPostExecute(String s) {
            pd.dismiss();
            if (s.equalsIgnoreCase("wrong StudentID or password")){
                Toast.makeText(Student_Login_Activity.this, "Wrong id or password", Toast.LENGTH_SHORT).show();
            }else{
                try {
                    JSONObject object=new JSONObject(s);
                    String firstname=object.getString("fname");
                    String lastname=object.getString("lastname");
                    String department=object.getString("departemnt");
                    String studentid=object.getString("studetnID");
                    String setion=object.getString("Section");
                    String year=object.getString("year");
                    PrefManager manager=new PrefManager(getApplicationContext());
                    manager.saveUserDetail(firstname,lastname,department,studentid,setion,year);
                    startActivity(new Intent(getApplicationContext(),Student_Page_Activity.class));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
