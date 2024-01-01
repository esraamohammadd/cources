package com.example.cources.pojo;

public class StudentChatsModel {

   private String userName;
    private String name;
    private String phone;



    public StudentChatsModel() {
    }

    public StudentChatsModel(String userName, String name, String phone) {
        this.userName = userName;
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


}
