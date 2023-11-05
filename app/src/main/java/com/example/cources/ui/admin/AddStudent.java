package com.example.cources.ui.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cources.R;
import com.example.cources.pojo.StudentModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddStudent extends AppCompatActivity {

    EditText etName,et_phone,et_subject,et_userName,et_password;
    String name,phone,subject,userName,password;
     DatabaseReference databaseReference;
    Button btn_save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);
        etName = findViewById(R.id.addstudent_et_fullName);
        et_phone = findViewById(R.id.addstudent_et_phone);
        et_subject = findViewById(R.id.addstudent_et_subject);
        et_userName = findViewById(R.id.addstudent_et_userName);
        et_password = findViewById(R.id.addstudent_et_password);
        btn_save = findViewById(R.id.btn_save);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = etName.getText().toString();
                phone = et_phone.getText().toString();
                subject = et_subject.getText().toString();
                userName = et_userName.getText().toString();
                password = et_password.getText().toString();

                if (!(name.isEmpty()||phone.isEmpty()||subject.isEmpty()||userName.isEmpty()||phone.isEmpty()))
                {
                   databaseReference.child("students").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (!(snapshot.hasChild(userName)))

                            {
                               String passwordFromFireBase =  snapshot.child(userName).child("phone_no").getValue(String.class);

                               addstudent();
                            }
                            else{

                                Toast.makeText(AddStudent.this, R.string.existstudent, Toast.LENGTH_SHORT).show();

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                }else{
                    etName.setError("required");
                    et_userName.setError("required");
                    et_phone.setError("required");
                    et_password.setError("required");
                    et_subject.setError("required");

                }

            }
        });


    }

    private void addstudent() {
        StudentModel studentModel = new StudentModel(name,phone,subject,userName,password);

        databaseReference.child("students").child(userName).setValue(studentModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AddStudent.this, R.string.success, Toast.LENGTH_SHORT).show();
                etName.setText("");
                et_userName.setText("");
                et_password.setText("");
                et_phone.setText("");
                et_subject.setText("");



            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(AddStudent.this, R.string.fail, Toast.LENGTH_SHORT).show();
            }
        });
    }
}