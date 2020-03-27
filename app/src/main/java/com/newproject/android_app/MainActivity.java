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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
EditText user,pass;
Button log;
TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user=findViewById(R.id.username);
        pass=findViewById(R.id.password);
        textView=findViewById(R.id.registertext);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               startActivity(new Intent(getApplicationContext(),Student_Login_Activity.class));
               finish();
            }
        });
        log=findViewById(R.id.login);
        log.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
     final String username=user.getText().toString();
     final String password=pass.getText().toString();
     new Login().execute(username,password);
    }

    private class Login extends AsyncTask<String,String,String> {
        HttpURLConnection connection;
        URL url=null;
        ProgressDialog progressDialog=new ProgressDialog(MainActivity.this);
        @Override
        protected void onPreExecute() {
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                url=new URL("http://192.168.1.10/HighTech/teacherLogin.php");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                connection=(HttpURLConnection)url.openConnection();
                connection.setRequestMethod("POST");
                connection.setReadTimeout(10000);
                connection.setConnectTimeout(10000);
                connection.setDoOutput(true);
                connection.setDoInput(true);
                Uri.Builder builder=new Uri.Builder().appendQueryParameter("username",strings[0])
                        .appendQueryParameter("password",strings[1]);
                OutputStream outputStream=connection.getOutputStream();
                String query=builder.build().getEncodedQuery();
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
                if (response_code==HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder builder = new StringBuilder();
                    String line ;
                    while (null != (line = reader.readLine())){
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
            progressDialog.dismiss();
            if (s.equalsIgnoreCase("login succesfully")){
                Toast.makeText(MainActivity.this, s, Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getApplicationContext(),Teacher_page_Activity.class));
                finish();
            }else if(s.equalsIgnoreCase("exception")){
                Toast.makeText(MainActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(MainActivity.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
