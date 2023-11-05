package com.example.cources.pojo;

public class DocumentModel {

    private String name;
    private String link;


    public DocumentModel() {
    }

    public DocumentModel(String name, String link) {
        this.name = name;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
