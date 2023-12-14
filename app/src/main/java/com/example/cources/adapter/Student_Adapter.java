package com.example.cources.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cources.R;
import com.example.cources.pojo.StudentModel;
import com.example.cources.ui.admin.StudentsFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class Student_Adapter extends RecyclerView.Adapter<Student_Adapter.Student_ViewHolder> {

    Context context;
    ArrayList<StudentModel>students;
    DatabaseReference databaseReference ;
    Listener listener;
   StudentsFragment listeners = new StudentsFragment();


    public Student_Adapter(Context context, ArrayList<StudentModel> students,Listener listener) {
        this.context = context;
        this.students = students;
        this.listener = listener;

    }


    @NonNull
    @Override
    public Student_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.student_layout,parent,false);
        return new Student_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Student_ViewHolder holder, int position)
    {
        StudentModel studentModel = students.get(position);
        holder.name.setText(studentModel.getName());
        holder.phone.setText(studentModel.getPhone_no());
        holder.subject.setText(studentModel.getSubject());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                databaseReference = FirebaseDatabase.getInstance().getReference();
                databaseReference.child("students").child(studentModel.getUserName()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, studentModel.getName()+ R.string.delet, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                listener.onClick(holder.getAdapterPosition());
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 listener.onItemClick(holder.getLayoutPosition());
            }
        });


//        Product product = products.get(position);
//        // getImg
//
//        databaseReference = FirebaseDatabase.getInstance().getReference();
//          getImage(holder.img_product,products.get(position));
//
//        holder.tv_name.setText(product.getName());
//       holder.img_description.setOnClickListener(new View.OnClickListener() {
//           @Override
//           public void onClick(View view) {
//
//
//               //open Dialog Fragment to display description ;
//               DescriptionFragment dialogue = DescriptionFragment
//                       .newInstance(product.getDescription());
//               FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
//               dialogue.show(fm, null);
//
//           }
//       });
//        holder.tv_price.setText(product.getPrice()+" "+product.getCoin());
//
//
//        if (isAdmin.equals("yes|product")) {
//            holder.img_addTo_basket.setVisibility(View.GONE);
//            holder.img_remove.setVisibility(View.VISIBLE);
//            holder.img_remove.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    databaseReference.child("products").child(product.getCode()).removeValue();
//                }
//            });
//
//        }else if (isAdmin.equals("yes|orders"))
//        {
//            holder.img_addTo_basket.setVisibility(View.GONE);
//            holder.img_remove.setVisibility(View.GONE);
//        }else
//        {
//            holder.img_addTo_basket.setVisibility(View.VISIBLE);
//            holder.img_remove.setVisibility(View.GONE);
//            holder.img_addTo_basket.setOnClickListener(new View.OnClickListener() {
//
//
//                @Override
//                public void onClick(View view) {
//
//
//
//                    {
//
//
//
//                        databaseReference.child("orders").child(product.getCode()).setValue(product).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void unused) {
//                                Toast.makeText(context, "add to basket", Toast.LENGTH_SHORT).show();
//                                holder.img_remove.setVisibility(View.VISIBLE);
//                                holder.img_addTo_basket.setVisibility(View.GONE);
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(context, "can't add to basket", Toast.LENGTH_SHORT).show();
//
//                            }
//                        });
//                    }
//
//
//                }
//            });
//
//            holder.img_remove.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    databaseReference.child("orders").child(product.getCode()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void unused) {
//                            holder.img_addTo_basket.setVisibility(View.VISIBLE);
//                            holder.img_remove.setVisibility(View.GONE);
//                            Toast.makeText(context, "delete from basket", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//                }
//            });
//
//        }
        }



    @Override
    public int getItemCount() {
        return students.size();
    }

    public class Student_ViewHolder extends RecyclerView.ViewHolder {


            TextView name , phone, subject;
            ImageView delete,edit;

        public Student_ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            phone = itemView.findViewById(R.id.phoneLayout);
            subject = itemView.findViewById(R.id.subject_layout);
            delete = itemView.findViewById(R.id.delete);
            edit = itemView.findViewById(R.id.edit);







        }


    }

    public interface Listener {
       void onClick(int position);
       void onItemClick(int position);
    }




}
