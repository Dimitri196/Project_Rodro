package cz.rodro.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(TaxationEntity.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class TaxationEntity_ {

	
	/**
	 * @see cz.rodro.entity.TaxationEntity#taxDescription
	 **/
	public static volatile SingularAttribute<TaxationEntity, String> taxDescription;
	
	/**
	 * @see cz.rodro.entity.TaxationEntity#id
	 **/
	public static volatile SingularAttribute<TaxationEntity, Long> id;
	
	/**
	 * @see cz.rodro.entity.TaxationEntity
	 **/
	public static volatile EntityType<TaxationEntity> class_;
	
	/**
	 * @see cz.rodro.entity.TaxationEntity#taxName
	 **/
	public static volatile SingularAttribute<TaxationEntity, String> taxName;

	public static final String TAX_DESCRIPTION = "taxDescription";
	public static final String ID = "id";
	public static final String TAX_NAME = "taxName";

}

