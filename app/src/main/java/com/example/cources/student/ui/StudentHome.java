package com.example.cources.student.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.cources.R;
import com.example.cources.ui.admin.DocumentsFragment;
import com.example.cources.ui.admin.VideoesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class StudentHome extends AppCompatActivity {

    private String username,name,subject,phone;
       SharedPreferences sharedPreferences ;
       SharedPreferences.Editor editor;

  BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        bottomNavigationView = findViewById(R.id.bottom_nav);

        sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


        if (sharedPreferences.contains("password"))
        {
            name = sharedPreferences.getString("name","");

            username =  sharedPreferences.getString("userName","");
            subject =  sharedPreferences.getString("subject","");
            phone =  sharedPreferences.getString("phone","");


        }



        replaceFragment(DocumentsFragment.newInstance(name,null));

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.chats  :
                        replaceFragment(ChatFragmentt.newInstance(name,null));
                        break;
                    case R.id.videoes  :
                        replaceFragment(VideoesFragment.newInstance(name,null));
                        break;
                    case R.id.documents:
                        replaceFragment(DocumentsFragment.newInstance(subject,null));
                        break;

                }

                return true;
            }
        });






    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_sub_content,fragment);
        transaction.commit();



    }


    }
