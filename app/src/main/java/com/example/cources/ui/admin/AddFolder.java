package com.example.cources.ui.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cources.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;
import java.util.UUID;

public class AddFolder extends AppCompatActivity {

    EditText et_name;
    EditText et_payment_phone;
    Button btn_save;
    ImageView back;
    String name;
    String payment_phone;
    DatabaseReference reference ;
    @SuppressLint({"MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_folder);

        back = findViewById(R.id.btn_back);
        et_name = findViewById(R.id.et_folderName);
        et_payment_phone = findViewById(R.id.et_folder_payment_phone);
        btn_save = findViewById(R.id.btn_saveFolder);

        reference = FirebaseDatabase.getInstance().getReference();

        btn_save.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 name = et_name.getText().toString().trim();
                 payment_phone = et_payment_phone.getText().toString().trim();

                 // add name to firebase
                 reference.child("folders").addListenerForSingleValueEvent(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot snapshot) {
                         if (!(snapshot.hasChild(name)))

                         {
                           addFolder();

                         }
                         else{

                             AlertDialog.Builder builder=new AlertDialog.Builder(AddFolder.this);
                             builder.setTitle("this folder is found ,if replace all data will be deleted");
                             builder.setPositiveButton("replace",new DialogInterface.OnClickListener(){
                                 @Override
                                 public void onClick(DialogInterface dialogInterface,int i){


                                   addFolder();

                                 }
                             });
                             builder.setNegativeButton("cancel",new DialogInterface.OnClickListener(){
                                 @Override
                                 public void onClick(DialogInterface dialogInterface,int i){

                                     Toast.makeText(AddFolder.this," canceled",Toast.LENGTH_SHORT).show();

                                 }
                             });
                             builder.show();

                         }

                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError error) {

                     }
                 });






             }
         });

         back.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent intent = new Intent(AddFolder.this,Home.class);
              startActivity(intent);
             }
         });


    }



    private void addFolder() {
        reference.child("folders").child(name).setValue(name).addOnSuccessListener(new OnSuccessListener<Void>() {

            @Override
            public void onSuccess(Void unused) {
                reference.child("folders").child(name).child("phone").setValue(payment_phone).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(AddFolder.this, R.string.success, Toast.LENGTH_SHORT).show();
                        et_name.setText("");
                    }
                });


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(AddFolder.this, R.string.fail, Toast.LENGTH_SHORT).show();
            }
        });
    }
}