package cz.rodro.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;

@StaticMetamodel(ArticleEntity.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class ArticleEntity_ {

	
	/**
	 * @see cz.rodro.entity.ArticleEntity#createdAt
	 **/
	public static volatile SingularAttribute<ArticleEntity, LocalDateTime> createdAt;
	
	/**
	 * @see cz.rodro.entity.ArticleEntity#author
	 **/
	public static volatile SingularAttribute<ArticleEntity, UserEntity> author;
	
	/**
	 * @see cz.rodro.entity.ArticleEntity#description
	 **/
	public static volatile SingularAttribute<ArticleEntity, String> description;
	
	/**
	 * @see cz.rodro.entity.ArticleEntity#id
	 **/
	public static volatile SingularAttribute<ArticleEntity, Long> id;
	
	/**
	 * @see cz.rodro.entity.ArticleEntity#categories
	 **/
	public static volatile ListAttribute<ArticleEntity, String> categories;
	
	/**
	 * @see cz.rodro.entity.ArticleEntity#title
	 **/
	public static volatile SingularAttribute<ArticleEntity, String> title;
	
	/**
	 * @see cz.rodro.entity.ArticleEntity
	 **/
	public static volatile EntityType<ArticleEntity> class_;
	
	/**
	 * @see cz.rodro.entity.ArticleEntity#views
	 **/
	public static volatile SingularAttribute<ArticleEntity, Long> views;
	
	/**
	 * @see cz.rodro.entity.ArticleEntity#content
	 **/
	public static volatile SingularAttribute<ArticleEntity, String> content;
	
	/**
	 * @see cz.rodro.entity.ArticleEntity#updatedAt
	 **/
	public static volatile SingularAttribute<ArticleEntity, LocalDateTime> updatedAt;

	public static final String CREATED_AT = "createdAt";
	public static final String AUTHOR = "author";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String CATEGORIES = "categories";
	public static final String TITLE = "title";
	public static final String VIEWS = "views";
	public static final String CONTENT = "content";
	public static final String UPDATED_AT = "updatedAt";

}

