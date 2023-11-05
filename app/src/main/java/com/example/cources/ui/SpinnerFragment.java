package com.example.cources.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cources.R;
import com.example.cources.adapter.Spinner_Adapter;
import com.example.cources.pojo.VideoModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SpinnerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SpinnerFragment extends DialogFragment implements Spinner_Adapter.Listener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_USERNAME = "username";
    private static final String ARG_SUBJECT = "subject";

    // views
    public Button btn_ok, btn_cancel;
    RecyclerView recyclerView;
    ArrayList<String> videoModels = new ArrayList<>();
    String[]names = null ;
    ArrayList<String> videoNo = new ArrayList<>();
    Spinner_Adapter spinnerAdapter;
    DatabaseReference databaseReference;

    //++++++
    boolean[] selectVideo;
    ArrayList<Integer>videoList = new ArrayList<>();
    String[] videoArray = {"video","video","video","video","video"};
    //+++++

    // TODO: Rename and change types of parameters
    private String userName;
    private String subject;

    public SpinnerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param username Parameter 1.
     * @param subject  Parameter 2.
     * @return A new instance of fragment SpinnerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SpinnerFragment newInstance(String username, String subject) {
        SpinnerFragment fragment = new SpinnerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, username);
        args.putString(ARG_SUBJECT, subject);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            userName = getArguments().getString(ARG_USERNAME);
            subject = getArguments().getString(ARG_SUBJECT);

        }
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_spinner, container, false);
//        recyclerView = view.findViewById(R.id.fr_rec_videos);
//        btn_cancel = view.findViewById(R.id.btn_cancel);
//        btn_ok = view.findViewById(R.id.btn_ok);
//        databaseReference = FirebaseDatabase.getInstance().getReference();
//        getAllVideos();
//        spinnerAdapter = new Spinner_Adapter(getActivity(), videoModels, new Spinner_Adapter.Listener() {
//            @Override
//            public void onClick(int position) {
//
//            }
//        });
//        recyclerView.setAdapter(spinnerAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        btn_ok.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // add allowed videos to spinner videos in firebase
//
//            }
//        });
//        btn_cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SpinnerFragment spinnerFragment = new SpinnerFragment();
//
//            }
//        });
//
//
//        return view;
//
//
//    }

    private void getAllVideos() {
     databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("folders").child(subject).child("videos").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                VideoModel videoModel = snapshot.getValue(VideoModel.class);

               // videoNo.add(videoModel.getName());
                videoNo.add("gt");
                videoNo.add("yuuy");
                videoNo.add("yuuy");
                videoNo.add("yuuy");
                videoNo.add("yuuy");



            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                spinnerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.videoNo);
//        getAllVideos();

//        names = videoNo.toArray(new String[videoNo.size()]);
//
//
//        builder.setMultiChoiceItems(names, null, new DialogInterface.OnMultiChoiceClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
//               if (b)
//               {videoNo.add(names[i]);}else {videoNo.remove(names[i]);}
//            }
//        });
//
//        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//                Toast.makeText(getActivity(), names.length, Toast.LENGTH_SHORT).show();
//                       dialogInterface.dismiss();
//            }
//        }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.dismiss();
//            }
//        });
//        return builder.create();
         selectVideo = new boolean[videoArray.length];
        builder.setCancelable(false);
        builder.setMultiChoiceItems(videoArray, selectVideo, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
               if (b)
               {
                   // when checkbox selected add position in videoList
                   videoList.add(i);
                   // sort list
                   Collections.sort(videoList);

               }else
               {
                   // when checkbox unselected remove position from videoList
                   videoList.remove(i);
               }


            }
        });
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // initialise string builder
                StringBuilder stringBuilder =new StringBuilder();
                for (int j = 0; j < videoList.size(); j++) {

                    // concat array value
                    stringBuilder.append(videoArray[videoList.get(j)]);
                    //check condition
                   // databaseReference.child("spinnerVideos").child(userName).setValue(videoArray[videoList.get(j)]);
                   stringBuilder.append(",");

                }
                Toast.makeText(getActivity(), stringBuilder.toString(), Toast.LENGTH_SHORT).show();

            }
        });
       builder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
           @Override
           public void onClick(DialogInterface dialogInterface, int i) {
              dialogInterface.dismiss();
           }
       });

        return  builder.create();
    }

    @Override
    public void onClick(int position) {


    }
}