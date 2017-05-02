package com.xiyoutest.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Article.class)
public abstract class Article_ {

	public static volatile SingularAttribute<Article, Boolean> isRecommend;
	public static volatile ListAttribute<Article, Comment> comments;
	public static volatile SingularAttribute<Article, String> author;
	public static volatile SingularAttribute<Article, String> title;
	public static volatile SingularAttribute<Article, CoverPhoto> cover_photo2;
	public static volatile SingularAttribute<Article, CoverPhoto> cover_photo3;
	public static volatile SingularAttribute<Article, Date> createTime;
	public static volatile SingularAttribute<Article, Boolean> isTop;
	public static volatile SingularAttribute<Article, Style> style;
	public static volatile SingularAttribute<Article, Integer> id;
	public static volatile SingularAttribute<Article, String> text;
	public static volatile SingularAttribute<Article, Category> category;
	public static volatile SetAttribute<Article, Article_Tag> article_tags;
	public static volatile SingularAttribute<Article, CoverPhoto> cover_photo1;

}

