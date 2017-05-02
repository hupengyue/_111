package com.xiyoutest.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Created by hpyba on 2017/4/26.
 */
@Entity
@Table(name = "article_tag")
public class Article_Tag {
//    @Id
//    @GeneratedValue(generator = "native")
//    @GenericGenerator(name = "native", strategy = "native")
    @Embeddable
    public static class Id implements Serializable{
        @Column(name = "article_id")
        private Integer articleId;

        @Column(name = "tag_id")
        private Integer tagId;
        public Id(){}
        public Id(Integer articleId,Integer tagId){
            this.articleId = articleId;
            this.tagId = tagId;
        }
        public boolean equals(Object o){
            if(o != null && o instanceof Id){
                Id that  = (Id)o;
                return this.articleId.equals(that.articleId) &&
                        this.tagId.equals(that.tagId);
            }
            else{
                return false;
            }
        }
        public int hashCode(){
            return articleId.hashCode() + tagId.hashCode();
        }
}
    @EmbeddedId
    private Id id = new Id();

    @ManyToOne
    @JoinColumn(name = "article_id",insertable = false,updatable = false)
    private Article article;

    @ManyToOne
    @JoinColumn(name = "tag_id",insertable = false,updatable = false)
    private Tag tag;

    public Article_Tag(){}

    public Article_Tag(Article article, Tag tag){
        this.article =article;
        this.tag = tag;

        this.id.articleId = article.getId();
        this.id.tagId = tag.getId();

        article.getArticle_tags().add(this);
        tag.getArticle_tags().add(this);
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }
}
