package cz.rodro.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;

@StaticMetamodel(ContactMessageEntity.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class ContactMessageEntity_ {

	
	/**
	 * @see cz.rodro.entity.ContactMessageEntity#createdAt
	 **/
	public static volatile SingularAttribute<ContactMessageEntity, LocalDateTime> createdAt;
	
	/**
	 * @see cz.rodro.entity.ContactMessageEntity#name
	 **/
	public static volatile SingularAttribute<ContactMessageEntity, String> name;
	
	/**
	 * @see cz.rodro.entity.ContactMessageEntity#id
	 **/
	public static volatile SingularAttribute<ContactMessageEntity, Long> id;
	
	/**
	 * @see cz.rodro.entity.ContactMessageEntity#message
	 **/
	public static volatile SingularAttribute<ContactMessageEntity, String> message;
	
	/**
	 * @see cz.rodro.entity.ContactMessageEntity
	 **/
	public static volatile EntityType<ContactMessageEntity> class_;
	
	/**
	 * @see cz.rodro.entity.ContactMessageEntity#email
	 **/
	public static volatile SingularAttribute<ContactMessageEntity, String> email;

	public static final String CREATED_AT = "createdAt";
	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String MESSAGE = "message";
	public static final String EMAIL = "email";

}

