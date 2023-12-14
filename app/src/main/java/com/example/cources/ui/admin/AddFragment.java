package com.example.cources.ui.admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cources.R;
import com.example.cources.pojo.VideoModel;
import com.example.cources.ui.ShowVideo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddFragment extends DialogFragment {

    EditText et_name,et_link;
    Button btn_upload;
    String name,link;
    DatabaseReference databaseReference;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SUBJECT= "subjectName";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String msubjectName;
    private String mParam2;

    public AddFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param subjectName Parameter 1.
     * @return A new instance of fragment AddFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddFragment newInstance(String subjectName) {
        AddFragment fragment = new AddFragment();
        Bundle args = new Bundle();
       args.putString(ARG_SUBJECT, subjectName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            msubjectName = getArguments().getString(ARG_SUBJECT);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_add, container, false);

       databaseReference = FirebaseDatabase.getInstance().getReference().child("folders").child(msubjectName).child("videos");
        et_name = view.findViewById(R.id.et_name);
        et_link = view.findViewById(R.id.et_link);
         btn_upload = view.findViewById(R.id.btn_upload);
         btn_upload.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 name = et_name.getText().toString();
                 link = et_link.getText().toString();

                 if (!(name.isEmpty()||link.isEmpty()))
                 {
                     databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot snapshot) {
                             if (snapshot.hasChild(name))
                             {
                                 Toast.makeText(getActivity(), R.string.existstudent, Toast.LENGTH_SHORT).show();
                             }else
                             {addvideo();


                             }
                         }

                         @Override
                         public void onCancelled(@NonNull DatabaseError error) {

                         }
                     });


                 }

             }
         });





        return view;

    }

    private void addvideo() {

        VideoModel videoModel = new VideoModel(name,link);

        databaseReference.child(name).setValue(videoModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getActivity(), R.string.success, Toast.LENGTH_SHORT).show();
                et_name.setText("");
                et_link.setText("");


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), R.string.fail, Toast.LENGTH_SHORT).show();
            }
        });
    }
}