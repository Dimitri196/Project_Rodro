package cz.rodro.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(UserEntity.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class UserEntity_ {

	
	/**
	 * @see cz.rodro.entity.UserEntity#password
	 **/
	public static volatile SingularAttribute<UserEntity, String> password;
	
	/**
	 * @see cz.rodro.entity.UserEntity#admin
	 **/
	public static volatile SingularAttribute<UserEntity, Boolean> admin;
	
	/**
	 * @see cz.rodro.entity.UserEntity
	 **/
	public static volatile EntityType<UserEntity> class_;
	
	/**
	 * @see cz.rodro.entity.UserEntity#userId
	 **/
	public static volatile SingularAttribute<UserEntity, Long> userId;
	
	/**
	 * @see cz.rodro.entity.UserEntity#email
	 **/
	public static volatile SingularAttribute<UserEntity, String> email;

	public static final String PASSWORD = "password";
	public static final String ADMIN = "admin";
	public static final String USER_ID = "userId";
	public static final String EMAIL = "email";

}

