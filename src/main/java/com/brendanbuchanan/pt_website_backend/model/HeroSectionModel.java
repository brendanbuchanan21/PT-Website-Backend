package com.brendanbuchanan.pt_website_backend.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class HeroSectionModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String heading;
    private String subText1;
    private String subText2;

    // constructors
    public HeroSectionModel() {}

    public HeroSectionModel(String heading, String subText1, String subText2) {
        this.heading = heading;
        this.subText1 = subText1;
        this.subText2 = subText2;

    }

    //getters and setters
    public Long getId() {
        return id;
    }
    public String getHeading() {
        return heading;
    }
    public void setHeading(String heading) {
        this.heading = heading;
    }
    public String getSubText1() {
        return subText1;
    }
    public void setSubText1(String subText1) {
        this.subText1 = subText1;
    }
    public String getSubText2() {
        return subText2;
    }
    public void setSubText2(String subText2) {
        this.subText2 = subText2;
    }
}