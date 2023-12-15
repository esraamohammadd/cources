package com.example.cources.ui.admin;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.cources.R;
import com.example.cources.adapter.Video_Adapter;

import com.example.cources.pojo.VideoModel;
import com.example.cources.ui.ShowYuoTubeVideo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideoesYouTubeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoesYouTubeFragment extends Fragment {


    RecyclerView recyclerView;
    FloatingActionButton btn_add;
    DatabaseReference databaseReference;
    ArrayList<VideoModel>videoModels = new ArrayList<>();
    Video_Adapter video_adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SUBJECT_NAME = "sub_name";
    private static final String ARG_PARAM2 = "param2";
    public static final String ARG_VIDE_LINK = "videoName";

    // TODO: Rename and change types of parameters
    private String msubjectName;
    private String mParam2;

    public VideoesYouTubeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param subject_name Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VideoesYouTubeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VideoesYouTubeFragment newInstance(String subject_name, String param2) {
        VideoesYouTubeFragment fragment = new VideoesYouTubeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SUBJECT_NAME, subject_name);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            msubjectName = getArguments().getString(ARG_SUBJECT_NAME);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_videoes, container, false);
         btn_add = view.findViewById(R.id.btn_addvideo);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddVideoFragment addVideoFragment = AddVideoFragment.newInstance(msubjectName);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                addVideoFragment.show(fragmentManager,null);

            }
        });

        recyclerView = view.findViewById(R.id.rec_video);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("folders").child(msubjectName).child("videos").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                VideoModel videoModel = snapshot.getValue(VideoModel.class);
                videoModels.add(videoModel);
                video_adapter.notifyDataSetChanged();



            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

           video_adapter = new Video_Adapter(getActivity(), videoModels, new Video_Adapter.Listener() {
               @Override
               public void onClick(int possition) {
                   Intent intent = new Intent(getActivity(), ShowYuoTubeVideo.class);
                   intent.putExtra(ARG_VIDE_LINK,videoModels.get(possition).getLink());
                   startActivity(intent);
               }
           });
           recyclerView.setAdapter(video_adapter);
           recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;

    }
}