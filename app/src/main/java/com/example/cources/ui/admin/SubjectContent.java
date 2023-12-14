package com.example.cources.ui.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.cources.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class SubjectContent extends AppCompatActivity {
    TextView tv_name;
    BottomNavigationView bottomNavigationView;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subject_content);
        getSupportActionBar().hide();
        tv_name  = findViewById(R.id.nameSub);
        bottomNavigationView = findViewById(R.id.bottom_nav);



        Intent intent = getIntent();
        if (intent != null) {
            name = intent.getStringExtra(FoldersFragment.NAME);
        }

       tv_name.setText(name);
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
                   case R.id.exam:
                       replaceFragment(ExamsFragment.newInstance(name,null));
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