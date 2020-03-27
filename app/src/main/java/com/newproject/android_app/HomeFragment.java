package com.newproject.android_app;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONArray;
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


public class HomeFragment extends Fragment {
 Spinner sec_spinner,dep_spinner;
 EditText year;
 Button search;
 ListView listView;
 String dep[]={"Computer Engineering","Computer Science","TVET","Accounting"};
String sec[]={"1","2","3","4"};

    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sec_spinner=view.findViewById(R.id.sec_spinner);
        dep_spinner=view.findViewById(R.id.dep_spinner);
        year=view.findViewById(R.id.year_select);
        search=view.findViewById(R.id.search);
        listView=view.findViewById(R.id.list_item);

        ArrayAdapter depart=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_dropdown_item,dep);
         dep_spinner.setAdapter(depart);
         ArrayAdapter sect=new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_dropdown_item,sec);
         sec_spinner.setAdapter(sect);
         search.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 String department=dep_spinner.getSelectedItem().toString();
                 String section=sec_spinner.getSelectedItem().toString();
                 String select_year=year.getText().toString();
                 new SelectFrom().execute(department,section,select_year);
             }
         });

    }

    private class SelectFrom extends AsyncTask<String,String,String> {
        ProgressDialog pd1=new ProgressDialog(getContext());
        HttpURLConnection httpURLConnection;
        URL url=null;

        @Override
        protected void onPreExecute() {
            pd1.setMessage("Loading");
            pd1.setCancelable(false);
            pd1.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                url=new URL("http://192.168.0.105/Micro/teacherselect.php");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setConnectTimeout(10000);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                OutputStream outputStream=httpURLConnection.getOutputStream();
                BufferedWriter writer=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                Uri.Builder builder=new Uri.Builder()
                        .appendQueryParameter("Departement",strings[0])
                        .appendQueryParameter("Section",strings[1])
                        .appendQueryParameter("Year",strings[2]);
                String query=builder.build().getEncodedQuery();
                writer.write(query);
                writer.flush();
                writer.close();
                outputStream.close();
                httpURLConnection.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                int response_code=httpURLConnection.getResponseCode();
                if( response_code==HttpURLConnection.HTTP_OK){
                    InputStream inputStream=httpURLConnection.getInputStream();
                    BufferedReader reader=new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder builder=new StringBuilder();
                    String line;
                    if (null!=(line=reader.readLine())){
                        builder.append(line);
                    }
                    return builder.toString().trim();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return String.valueOf(httpURLConnection);
        }

        @Override
        protected void onPostExecute(String s) {
            pd1.dismiss();
            try {
             values(s);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            }
        }
        public String values(String json) throws JSONException  {
        JSONArray array=new JSONArray(json);
            String jsonarray[]=new String[array.length()];
            for (int i=0;i<array.length();i++) {
            JSONObject object = array.getJSONObject(i);
         jsonarray[i]= object.getString("user");

        }
            ArrayAdapter adapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,jsonarray);
            listView.setAdapter(adapter);
            return json;
        }
    }

