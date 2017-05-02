package com.xiyoutest.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Comment.class)
public abstract class Comment_ {

	public static volatile SingularAttribute<Comment, Date> createTime;
	public static volatile SingularAttribute<Comment, Integer> praised_times;
	public static volatile SingularAttribute<Comment, Integer> id;
	public static volatile SingularAttribute<Comment, String> text;
	public static volatile SingularAttribute<Comment, User> user;
	public static volatile SingularAttribute<Comment, Article> article;

}

