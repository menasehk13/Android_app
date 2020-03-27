package com.newproject.android_app;

import android.app.ProgressDialog;
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
import android.widget.Spinner;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class HomeFragment extends Fragment {
 Spinner sec_spinner,dep_spinner;
 EditText year;
 Button search;
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
                url=new URL("http://192.168.0.104/Micro/teacherselect.php");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setConnectTimeout(10000);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
