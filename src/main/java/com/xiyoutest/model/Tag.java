package com.xiyoutest.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

/**
 * Created by hpyba on 2017/4/24.
 */
@Entity
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Integer id;

    @OneToMany(mappedBy = "tag")
    //(cascade = {CascadeType.MERGE,CascadeType.PERSIST},    mappedBy = "tagList",fetch = FetchType.EAGER)
    private Set<Article_Tag> article_tags = new HashSet<Article_Tag>();

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Article_Tag> getArticle_tags() {
        return article_tags;
    }

    public void setArticle_tags(Set<Article_Tag> article_tags) {
        this.article_tags = article_tags;
    }
}
