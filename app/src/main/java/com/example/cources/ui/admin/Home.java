package com.example.cources.ui.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.cources.student.ui.MainActivity;
import com.example.cources.R;
import com.google.android.material.navigation.NavigationView;

public class Home extends AppCompatActivity {

    DrawerLayout drawerLayout;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navigationView = findViewById(R.id.navgationView);
        drawerLayout = findViewById(R.id.myDrawerLayout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.nav_open,R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        replaceFragment(new HomeFragment());
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())

                {
                    case R.id.menu_home:
                        replaceFragment(new HomeFragment());
                        drawerLayout.close();

                          break;
                    case R.id.students:e:
                        replaceFragment(new StudentsFragment());
                        drawerLayout.close();
                        break;
                    case R.id.folders:
                        replaceFragment(new FoldersFragment());
                        drawerLayout.close();
                        break;
                    case R.id.chat:
                        replaceFragment(new ChatFragment());
                        drawerLayout.close();
                        break;

                    case R.id.logout:
                        startActivity(new Intent(Home.this, MainActivity.class));
                        drawerLayout.close();
                        break;


                }
                return true;
            }
        });




    }

    private  void replaceFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout,fragment);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}