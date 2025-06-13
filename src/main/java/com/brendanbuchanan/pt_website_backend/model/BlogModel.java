package com.brendanbuchanan.pt_website_backend.model;


import jakarta.persistence.*;

@Entity
@Table( name = "blog_model")
public class BlogModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String date;
    private String description;
    private String imageUrl;

    //constructors
    public BlogModel() {}

    public BlogModel(String title, String author, String date, String description, String imageUrl) {
        this.title = title;
        this.author = author;
        this.date = date;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    //create getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
}
