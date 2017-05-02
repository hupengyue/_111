package com.xiyoutest.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Style.class)
public abstract class Style_ {

	public static volatile SingularAttribute<Style, String> name;
	public static volatile SingularAttribute<Style, Integer> id;
	public static volatile ListAttribute<Style, Article> articles;

}

