package com.example.cources.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.cources.pojo.StudentModel;

public class StudentViewModel extends ViewModel {

    public MutableLiveData <StudentModel> mutableLiveData = new MutableLiveData<>();


    public StudentModel getStudentModel ()
    {
        // return data from database (realtime firebase)
        return new StudentModel("Es","kh","jk","jf","jh");
    }
}
