package com.example.my_app_project;

import java.util.Date;

public class WriteInfo {
    private String title;
    private String contents;
    private String publisher;
    private Date createdAt;
    private String id;

    public WriteInfo(String title, String contents, String publisher, Date createdAt, String id){
        this.title = title;
        this.contents = contents;
        this.publisher = publisher;
        this.createdAt = createdAt;
        this.id = id;
    }
    public WriteInfo(String title, String contents, String publisher, Date createdAt){
        this.title = title;
        this.contents = contents;
        this.publisher = publisher;
        this.createdAt = createdAt;
        this.id = id;
    }

    public String getTitle() {return this.title;}
    public void setTitle(String title) {this.title = title;}
    public String getContents() {return this.contents;}
    public void setContents() {this.contents = contents;}
    public String getPublisher() {return this.publisher;}
    public void setPublisher(String publisher) {this.publisher = publisher;}
    public Date getCreatedAt() {return this.createdAt;}
    public void setCreatedAt(Date createdAt) {this.createdAt = createdAt;}
    public String getId() {return this.id;}
    public void setId(String id) {this.id = id;}
}
