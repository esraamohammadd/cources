package com.example.cources.pojo;

public class StudentModel {



    private String name ;
    private String phone_no ;
    private String subject ;
    private String userName ;
    private String password ;

    public StudentModel() {
    }

    public StudentModel(String name, String phone_no, String subject, String userName, String password) {
        this.name = name;
        this.phone_no = phone_no;
        this.subject = subject;
        this.userName = userName;
        this.password = password;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
