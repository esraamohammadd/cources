package com.example.cources.student.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cources.R;
import com.example.cources.pojo.StudentModel;
import com.example.cources.student.VideosActivity;
import com.example.cources.ui.admin.Home;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    public static final String  ARG_USERNAME = "userName";
    public static final String  ARG_SUBJECT = "subject";
    public static final String  ARG_PHONE = "phone";
    public static final String  ARG_NAME = "name";


    EditText et_userName,et_password;
    Button btn_login;
    DatabaseReference databaseReference;
    String password,userName;
    SharedPreferences sharedPreferences ;
    SharedPreferences.Editor editor ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // to prevent screenShot or video record
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_main);
        // hiding actionbar
        getSupportActionBar().hide();

        et_userName = findViewById(R.id.et_userName);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_login);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        sharedPreferences = getSharedPreferences("preferences", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

             if (sharedPreferences != null) {
                 userName = sharedPreferences.getString("userName", "default");
                 password = sharedPreferences.getString("password", "de");
                 if (userName.equals("teacher".trim()) && password.equals("123".trim())) {
                     startActivity(new Intent(MainActivity.this, Home.class));

                 }
             }

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               password =  et_password.getText().toString();
              userName =  et_userName.getText().toString();

              if (!(password.isEmpty()||userName.isEmpty()))
              {
                  if (userName.equals("teacher")){
                      editor.putString("userName","teacher");
                      editor.putString("password","123");
                      editor.apply();
                      startActivity(new Intent(MainActivity.this,Home.class));
                  }else
                  {
                      Toast.makeText(MainActivity.this, "error login ", Toast.LENGTH_SHORT).show();
                  }
              }
              else {
                     et_password.setError("required");
                     et_userName.setError("required");

              }

            }
        });


    }


}