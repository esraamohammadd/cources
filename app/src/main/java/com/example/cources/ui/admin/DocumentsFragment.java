package com.example.cources.ui.admin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cources.R;
import com.example.cources.adapter.Document_Adapter;
import com.example.cources.pojo.DocumentModel;
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
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DocumentsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DocumentsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_SUBJECTNAME = "subName";
    private static final String ARG_PARAM2 = "param2";
    StorageReference storageReference;
    DatabaseReference databaseReference;
    EditText etName;
    RecyclerView recyclerView;
    FloatingActionButton addFile;
    String name;
     ArrayList<DocumentModel>documentModels= new ArrayList<>();
     Document_Adapter document_adapter;
    String type;
    //
    private ActivityResultLauncher<String>filePickerLauncher;

    // TODO: Rename and change types of parameters
    private String subjectName;
    private String mParam2;

    public DocumentsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param subject_name Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DocumentsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DocumentsFragment newInstance(String subject_name, String param2) {
        DocumentsFragment fragment = new DocumentsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_SUBJECTNAME, subject_name);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            subjectName = getArguments().getString(ARG_SUBJECTNAME);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_documents, container, false);
        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("folders")
                .child(subjectName).child("documents");

        // show files
        recyclerView = view.findViewById(R.id.rec_documents);
        getFiles();
         document_adapter = new Document_Adapter(getActivity(), documentModels, new Document_Adapter.Listener() {
            @Override
            public void onClick(int possition) {

            }

             @Override
             public void onIteemClick(int position) {

                 // show file at possition
                 String uri = documentModels.get(position).getLink();
                 Intent intent = new Intent();
                 intent.setAction(Intent.ACTION_VIEW);
                 intent.setDataAndType(Uri.parse(uri),"application/pdf");
                 intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                 startActivity(intent);
             }
         });
       recyclerView.setAdapter(document_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

      // add file
        addFile = view.findViewById(R.id.btn_addDocument);
        etName = view.findViewById(R.id.etName);
        addFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = etName.getText().toString();
               if (!(name.isEmpty())) {
                   pickFile();

               }else
               {etName.setError("enter Name of file");}
            }
        });


        return view;


    }

    private void pickFile() {

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!(snapshot.hasChild(name)))
                { filePickerLauncher.launch("application/pdf");
                }else{
                    Toast.makeText(getActivity(), R.string.existstudent, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

   private void onFiledPicked (Uri fileUri){
        upload_File(fileUri);
   }

    private void upload_File(Uri data)
    {

        final ProgressDialog progressFile = new ProgressDialog ( getActivity() );
        progressFile.setTitle ( "uploading...." );
        progressFile.show ();
        StorageReference referencest = storageReference.child ( "uploads/"+name);
        referencest.putFile ( data ).addOnSuccessListener ( new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {


                // convert to uri
                Task<Uri> uriTask = taskSnapshot.getStorage ().getDownloadUrl ();
                while(!uriTask.isComplete ());
                Uri uri = uriTask.getResult ();



                  //to add file in real time database
                 DocumentModel documentModel = new DocumentModel (name, uri.toString ());
                databaseReference.child(name).setValue(documentModel);
                Toast.makeText(getActivity(), "file uploaded", Toast.LENGTH_SHORT).show();
                progressFile.dismiss();
                etName.setText("");

            }

        }).addOnProgressListener ( new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                double progress = (100.0*snapshot.getBytesTransferred ())/snapshot.getTotalByteCount ();
                progressFile.setMessage ( "upload : "+ (int)progress+"%" );

            }
        } );
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        filePickerLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),this::onFiledPicked);

    }

 // get files
   private  void getFiles()
   {
       databaseReference.addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
               DocumentModel documentModel = snapshot.getValue(DocumentModel.class);
               documentModels.add(documentModel);
               document_adapter.notifyDataSetChanged();
           }

           @Override
           public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
               document_adapter.notifyDataSetChanged();
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
   }
}