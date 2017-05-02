package com.xiyoutest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

/**
 * Created by hpyba on 2017/4/15.
 */
@Entity
@Table(name = "article", schema = "xiyou2")
public class Article {
    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Integer id;
    private String title;
    private String author;
    @Column(length = 1000)
    private String text;

    @ManyToOne
    @JsonIgnore
    private Category category;

    @OneToMany(mappedBy = "article")
    private Set<Article_Tag> article_tags = new HashSet<Article_Tag>();

//    @JoinTable(
//            name = "ARTICLE_TAG",
//            joinColumns = {@JoinColumn(name = "article_id")},
//            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
//    )
//    private Set<Tag> tagList = new HashSet<Tag>();

    private Boolean isTop = false;//置顶

    private Boolean isRecommend = true;//推荐


    @ManyToOne
    @JsonIgnore
    private Style style;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    private Date createTime ;

//    @ManyToMany(fetch = FetchType.LAZY)
//    @JoinTable(
//            name = "ARTICLE_USER",
//            joinColumns = {@JoinColumn(name = "article_id")},
//            inverseJoinColumns = {@JoinColumn(name = "user_id")}
//    )
//    private Set<User> userList = new HashSet<User>();

    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE},
        mappedBy = "article",fetch = FetchType.EAGER)
    private List<Comment> comments = new ArrayList<>();

    @OneToOne
    @JoinColumn(name= "cover_photo1")
    private CoverPhoto cover_photo1;
    @OneToOne
    @JoinColumn(name= "cover_photo2")
    private CoverPhoto cover_photo2;
    @OneToOne
    @JoinColumn(name= "cover_photo3")
    private CoverPhoto cover_photo3;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isTop() {
        return isTop;
    }

    public void setTop(boolean top) {
        isTop = top;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

//    public Set<User> getUserList() {
//        return userList;
//    }
//
//    public void setUserList(Set<User> userList) {
//        this.userList = userList;
//    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public CoverPhoto getCover_photo1() {
        return cover_photo1;
    }

    public void setCover_photo1(CoverPhoto cover_photo1) {
        this.cover_photo1 = cover_photo1;
    }

    public CoverPhoto getCover_photo2() {
        return cover_photo2;
    }

    public void setCover_photo2(CoverPhoto cover_photo2) {
        this.cover_photo2 = cover_photo2;
    }

    public CoverPhoto getCover_photo3() {
        return cover_photo3;
    }

    public void setCover_photo3(CoverPhoto cover_photo3) {
        this.cover_photo3 = cover_photo3;
    }

    public Boolean getTop() {
        return isTop;
    }

    public void setTop(Boolean top) {
        isTop = top;
    }

    public Boolean getRecommend() {
        return isRecommend;
    }

    public void setRecommend(Boolean recommend) {
        isRecommend = recommend;
    }

    public Set<Article_Tag> getArticle_tags() {
        return article_tags;
    }

    public void setArticle_tags(Set<Article_Tag> article_tags) {
        this.article_tags = article_tags;
    }
}
