package com.xiyoutest.model;

import com.xiyoutest.model.Article_Tag.Id;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Article_Tag.class)
public abstract class Article_Tag_ {

	public static volatile SingularAttribute<Article_Tag, Id> id;
	public static volatile SingularAttribute<Article_Tag, Tag> tag;
	public static volatile SingularAttribute<Article_Tag, Article> article;

}

