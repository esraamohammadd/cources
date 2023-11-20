package com.example.cources.student.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
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


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               password =  et_password.getText().toString();
              userName =  et_userName.getText().toString();
              if (!(password.isEmpty()||userName.isEmpty()))
              {
                  if (userName.equals("teacher")){
                      startActivity(new Intent(MainActivity.this,Home.class));
                  }else {
                      login();
                  }
              }
              else {
                     et_password.setError("required");
                     et_userName.setError("required");

              }

            }
        });


    }

    private void login() {

        databaseReference.child("students").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(userName)) {
                    String pasword = snapshot.child(userName).child("password").getValue(String.class);
                    String subject = snapshot.child(userName).child("subject").getValue(String.class);
                    String phone = snapshot.child(userName).child("phone_no").getValue(String.class);
                    String name = snapshot.child(userName).child("name").getValue(String.class);

                    if (password.equals(pasword))
                    {
                        Intent intent = new Intent(MainActivity.this, StudentHome.class);
                        intent.putExtra(ARG_USERNAME,userName);
                        intent.putExtra(ARG_PHONE,phone);
                        intent.putExtra(ARG_NAME,name);
                        intent.putExtra(ARG_SUBJECT,subject);
                        startActivity(intent);

                    }else{
                        Toast.makeText(MainActivity.this, R.string.errorLogin, Toast.LENGTH_LONG).show();

                    }
                }else
                {
                    Toast.makeText(MainActivity.this, R.string.errorusename, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}