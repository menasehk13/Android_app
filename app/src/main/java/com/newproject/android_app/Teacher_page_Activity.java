package com.newproject.android_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.google.android.material.navigation.NavigationView;

import static com.newproject.android_app.R.string.closedrawer;

public class Teacher_page_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
Toolbar toolbar;
NavigationView navigationView;
DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_page_);
        toolbar=findViewById(R.id.toolbar);
        navigationView=findViewById(R.id.nav_view);
        drawerLayout=findViewById(R.id.drawer);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.opendrawer, closedrawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        displaySelectedItems(R.id.home1);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
     displaySelectedItems(item.getItemId());

        return true;
    }
public void displaySelectedItems(int items){
        Fragment fragment=null;
        switch (items){
            case R.id.home1:
                fragment=new HomeFragment();
                break;
        }
    FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
    transaction.replace(R.id.frame,fragment);
    transaction.commit();
    drawerLayout.closeDrawer(GravityCompat.START);
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if (id==R.id.logout){
            SharedPreference.setLoggedIn(getApplicationContext(),false);
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }
        return true;
    }
}
