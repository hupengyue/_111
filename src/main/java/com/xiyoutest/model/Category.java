package com.xiyoutest.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hpyba on 2017/4/15.
 */
@Entity
@Table(name = "category", schema = "xiyou2")
public class Category {
    @Id
    @GeneratedValue(generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Integer id;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            mappedBy = "category",fetch =FetchType.EAGER)
    private List<Article> article = new ArrayList<>();

    private String name;

    public Integer getCategoryId() {
        return id;
    }

    public void setCategoryId(Integer categoryId) {
        this.id = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Article> getArticle() {
        return article;
    }

    public void setArticle(List<Article> article) {
        this.article = article;
    }
}
