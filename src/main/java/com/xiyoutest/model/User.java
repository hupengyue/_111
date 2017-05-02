package com.xiyoutest.model;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hibernate.annotations.CascadeType.*;

/**
 * Created by hpyba on 2017/4/14.
 */
@Entity
@Table(name = "user", schema = "xiyou2")
public class User {
    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private int Id;
    private String name;

//    @Access(AccessType.PROPERTY)
//    @ManyToMany(targetEntity=Article.class,mappedBy = "userList",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
//    Set<Article> articleList;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
    mappedBy = "user",fetch = FetchType.EAGER)
    private List<Comment> comments = new ArrayList<>();

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public Set<Article> getArticleList() {
//        return articleList;
//    }
//
//    public void setArticleList(Set<Article> articleList) {
//        this.articleList = articleList;
//    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
