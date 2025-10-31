package cz.rodro.entity.helper;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.MappedSuperclassType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDateTime;

@StaticMetamodel(AuditableEntity.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class AuditableEntity_ {

	
	/**
	 * @see cz.rodro.entity.helper.AuditableEntity#createdAt
	 **/
	public static volatile SingularAttribute<AuditableEntity, LocalDateTime> createdAt;
	
	/**
	 * @see cz.rodro.entity.helper.AuditableEntity
	 **/
	public static volatile MappedSuperclassType<AuditableEntity> class_;
	
	/**
	 * @see cz.rodro.entity.helper.AuditableEntity#updatedAt
	 **/
	public static volatile SingularAttribute<AuditableEntity, LocalDateTime> updatedAt;

	public static final String CREATED_AT = "createdAt";
	public static final String UPDATED_AT = "updatedAt";

}

