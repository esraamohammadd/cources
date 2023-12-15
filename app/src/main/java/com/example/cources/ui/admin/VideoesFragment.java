package com.example.cources.ui.admin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cources.R;
import com.example.cources.adapter.Video_Adapter;
import com.example.cources.pojo.DocumentModel;
import com.example.cources.pojo.VideoModel;
import com.example.cources.ui.ShowFireBaseVideo;
import com.example.cources.ui.ShowYuoTubeVideo;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;


public class VideoesFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView recyclerView;
    FloatingActionButton btn_add,btn_upload;
    EditText et_Video_name;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    StorageReference storageReference = FirebaseStorage.getInstance().getReference(); ;
    private ActivityResultLauncher<String> videoPickerLauncher;
    ;
    ArrayList<VideoModel> videoModels = new ArrayList<>();
    Video_Adapter video_adapter;
    private String mParam1;
    private String mParam2;
    String videoName;

    public VideoesFragment() {
        // Required empty public constructor
    }

    public static VideoesFragment newInstance(String param1, String param2) {
        VideoesFragment fragment = new VideoesFragment();
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

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_videoes, container, false);

        recyclerView = view.findViewById(R.id.rec_videos);
        btn_add = view.findViewById(R.id.btn_addvideo);
        et_Video_name = view.findViewById(R.id.et_videoName);
        btn_upload = view.findViewById(R.id.btn_pick_video);

          getVideos();
        recyclerView.setAdapter(video_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_Video_name.setVisibility(View.VISIBLE);
                btn_upload.setVisibility(View.VISIBLE);

            }
        });
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               videoName = et_Video_name.getText().toString().trim();
                if (!(videoName.isEmpty()))
                { databaseReference.child("folders").child(mParam1).child("videos").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (!(snapshot.hasChild(videoName)))

                        {
                            pickVideo();
                        }else {et_Video_name.setError("required");}
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

    private void getVideos() {

        databaseReference.child("folders").child(mParam1).child("videos").addChildEventListener(new ChildEventListener() {
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
                Intent intent = new Intent(getActivity(), ShowFireBaseVideo.class);
                intent.putExtra("link",videoModels.get(possition).getLink());
                startActivity(intent);
            }
        });


    }

    private void pickVideo() {

       videoPickerLauncher.launch("video/*");

    }

    private void onFiledPicked(Uri data) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.confirmSend);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                uploadVideo(data);
            }
        }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
         builder.create();
        builder.show();

    }

    private void uploadVideo(Uri data) {

       storageReference.child("uploads/videos/"+videoName).putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
           @Override
           public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
               Uri resultUri = null;
               Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
               while (!(uriTask.isComplete()));

                    resultUri = uriTask.getResult();

               //to add file in real time database
               VideoModel videoModel = new VideoModel (videoName, resultUri.toString ());
               databaseReference.child("folders").child(mParam1).child("videos").child(videoName).setValue(videoModel);
               Toast.makeText(getActivity(), "file uploaded", Toast.LENGTH_SHORT).show();
               et_Video_name.setVisibility(View.GONE);
               btn_upload.setVisibility(View.GONE);
           }
       });




    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        videoPickerLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),this::onFiledPicked);
    }
}