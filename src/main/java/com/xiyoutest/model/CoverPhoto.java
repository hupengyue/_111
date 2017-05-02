package com.xiyoutest.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;

/**
 * Created by hpyba on 2017/4/19.
 */
@Entity
@Table(name = "cover_photo")
public class CoverPhoto {
    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    private Integer id;

    @OneToOne(mappedBy = "cover_photo1")
    @Access(AccessType.PROPERTY)
    private Article article1;

    @OneToOne(mappedBy = "cover_photo2")
    @Access(AccessType.PROPERTY)
    private Article article2;

    @OneToOne(mappedBy = "cover_photo3")
    @Access(AccessType.PROPERTY)
    private Article article3;

    private String location;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Article getArticle1() {
        return article1;
    }

    public void setArticle1(Article article1) {
        this.article1 = article1;
    }

    public Article getArticle2() {
        return article2;
    }

    public void setArticle2(Article article2) {
        this.article2 = article2;
    }

    public Article getArticle3() {
        return article3;
    }

    public void setArticle3(Article article3) {
        this.article3 = article3;
    }

}
