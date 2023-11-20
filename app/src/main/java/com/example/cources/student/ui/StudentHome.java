package com.example.cources.student.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.example.cources.R;
import com.example.cources.ui.admin.ChatsFragment;
import com.example.cources.ui.admin.DocumentsFragment;
import com.example.cources.ui.admin.FoldersFragment;
import com.example.cources.ui.admin.VideoesFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class StudentHome extends AppCompatActivity {

    private String username,name,subject,phone;

  BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        bottomNavigationView = findViewById(R.id.bottom_nav);

        Intent intent = getIntent();
        if (intent != null)
        {
            name = intent.getStringExtra(MainActivity.ARG_NAME);
            username = intent.getStringExtra(MainActivity.ARG_USERNAME);
            subject = intent.getStringExtra(MainActivity.ARG_SUBJECT);
            phone = intent.getStringExtra(MainActivity.ARG_PHONE);


        }



        replaceFragment(DocumentsFragment.newInstance(name,null));

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId())
                {
                    case R.id.chats  :
                        replaceFragment(ChatsFragment.newInstance(name,null));
                        break;
                    case R.id.videoes  :
                        replaceFragment(VideoesFragment.newInstance(name,null));
                        break;
                    case R.id.documents:
                        replaceFragment(DocumentsFragment.newInstance(name,null));
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
