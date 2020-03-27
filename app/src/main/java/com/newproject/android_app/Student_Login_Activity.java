package com.newproject.android_app;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.HashMap;

public class Student_Login_Activity extends AppCompatActivity implements View.OnClickListener {
EditText studentid,studentpassword;
Button studlogin;
TextView register;
    JSONObject object;
    String  java="name";
    HashMap<String,String>hashMap=new HashMap<>();
    TextView view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__login_);
        studentid=findViewById(R.id.student_username);
        studentpassword=findViewById(R.id.student_password);
        studlogin=findViewById(R.id.studlogin);
        register=findViewById(R.id.register);
        view.getText();
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




    public class Login extends AsyncTask<String, String, String> {


        ProgressDialog pd=new ProgressDialog(Student_Login_Activity.this);
        URL url=null;
        HttpURLConnection connection;
        @Override
        protected void onPreExecute() {
           pd.setMessage("Loading");
           pd.show();
           pd.setCancelable(false);
        }

        @Override
        public String doInBackground(String... strings) {
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
                BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
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
                    return builder.toString().trim();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            }


            return String.valueOf(connection);
        }


        @Override
        public void onPostExecute(String s) {
            pd.dismiss();
            try {
                JSONObject object=new JSONObject(s);
                String first=object.getString("fname");
                String last=object.getString("lastname");
                String departement=object.getString("departemnt");
                String section=object.getString("Section");
                String Year=object.getString("year");
                String studentid=object.getString("studetnID");
              PrefManager manager=new PrefManager(getApplicationContext());
              manager.saveUserDetail(first,last,departement,studentid,section,Year);
               startActivity(new Intent(getApplicationContext(),Student_Page_Activity.class));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }







}
