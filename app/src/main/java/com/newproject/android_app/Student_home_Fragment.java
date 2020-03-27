package com.newproject.android_app;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class Student_home_Fragment extends Fragment {

    TextView name,lname,studentid,departemnt,year,section;

String jason;
    public Student_home_Fragment() {
        // Required empty public constructor
    }





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.student_nav_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        name=view.findViewById(R.id.studentNameHomeNav);
        lname=view.findViewById(R.id.studentLastNameHomeNav);
        studentid=view.findViewById(R.id.studentIdNavHome);
        departemnt=view.findViewById(R.id.studentDepNavHome);
        section=view.findViewById(R.id.studentSecNavHome);
        year=view.findViewById(R.id.studentYearNavHome);
        PrefManager prefManager=new PrefManager(getActivity());
        name.setText(prefManager.getname());
        lname.setText(prefManager.getlastname());
        studentid.setText(prefManager.getStudentId());
        departemnt.setText(prefManager.getdepartement());
        section.setText(prefManager.getsection());
        year.setText(prefManager.getyear());


    }

}
