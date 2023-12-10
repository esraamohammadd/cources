package com.example.cources.ui.admin;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cources.R;
import com.example.cources.pojo.ExamModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ExamFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    EditText et_question,et_option1,et_option2,et_option3,et_option4,et_score;
    Spinner sp_answer;
    Button btn_save,btn_sendExam;
    String question;
    String option1;
    String option2;
    String option3;
    String option4;
    double score;
            int correctAnswer;
    DatabaseReference reference;

    private String mParam1;
    private String mParam2;

    public ExamFragment() {
        // Required empty public constructor
    }



    public static ExamFragment newInstance(String param1, String param2) {
        ExamFragment fragment = new ExamFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exam, container, false);
        et_question = view.findViewById(R.id.et_question);
        et_option1 = view.findViewById(R.id.et_option1);
        et_option2 = view.findViewById(R.id.et_option2);
        et_option3 = view.findViewById(R.id.et_option3);
        et_option4 = view.findViewById(R.id.et_option4);
        et_score = view.findViewById(R.id.et_score);
        sp_answer = view.findViewById(R.id.sp_correctAnswer);
        btn_save = view.findViewById(R.id.btn_exam_save);
        btn_sendExam = view.findViewById(R.id.send_exam);




        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question = et_question.getText().toString();
                option1 = et_option1.getText().toString();
                option2 = et_option2.getText().toString();
                option3 = et_option3.getText().toString();
                option4 = et_option4.getText().toString();
                score = Double.parseDouble(et_score.getText().toString());
                correctAnswer= (int) sp_answer.getSelectedItemId();

                if (question.isEmpty()||question.isEmpty()||question.isEmpty()||question.isEmpty()||question.isEmpty()||question.isEmpty()||sp_answer.getSelectedItemId()== 0) {
                    {
                        et_question.setError("required");
                        et_option1.setError("required");
                        et_option2.setError("required");
                        et_option3.setError("required");
                        et_option4.setError("required");
                        et_score.setError("required");


                    }
                }else {
                    ExamModel examModel = new ExamModel(question, option1, option2, option3, option4, correctAnswer, score);
                    Toast.makeText(getActivity(), correctAnswer+"", Toast.LENGTH_SHORT).show();
                    sendQuestion(reference, examModel);
                }

            }
        });

          btn_sendExam.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Toast.makeText(getActivity(), R.string.success, Toast.LENGTH_SHORT).show();
                  replaceFragment(HomeFragment.newInstance(mParam1,null));
              }
          });


        return view;
    }

    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_sub_content,fragment);
        transaction.commit();



    }
    private void sendQuestion(DatabaseReference reference,ExamModel examModel) {
        reference = FirebaseDatabase.getInstance().getReference().child("folders").child(mParam1).child("exam");
         examModel = new ExamModel(question,option1,option2,option3,option4,correctAnswer,score);

         reference.push().setValue(examModel).addOnSuccessListener(new OnSuccessListener<Void>() {
             @Override
             public void onSuccess(Void unused) {
                 et_question.setText("");
                 et_option1.setText("");
                 et_option2.setText("");
                 et_option3.setText("");
                 et_option4.setText("");
                 et_score.setText("");
                 sp_answer.setSelection(0);



             }
         }).addOnFailureListener(new OnFailureListener() {
             @Override
             public void onFailure(@NonNull Exception e) {

                 Toast.makeText(getActivity(), "try again", Toast.LENGTH_SHORT).show();
             }
         });



    }
}