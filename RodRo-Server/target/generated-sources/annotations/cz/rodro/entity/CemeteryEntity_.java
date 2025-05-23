package cz.rodro.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(CemeteryEntity.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class CemeteryEntity_ {

	
	/**
	 * @see cz.rodro.entity.CemeteryEntity#webLink
	 **/
	public static volatile SingularAttribute<CemeteryEntity, String> webLink;
	
	/**
	 * @see cz.rodro.entity.CemeteryEntity#description
	 **/
	public static volatile SingularAttribute<CemeteryEntity, String> description;
	
	/**
	 * @see cz.rodro.entity.CemeteryEntity#id
	 **/
	public static volatile SingularAttribute<CemeteryEntity, Long> id;
	
	/**
	 * @see cz.rodro.entity.CemeteryEntity#cemeteryName
	 **/
	public static volatile SingularAttribute<CemeteryEntity, String> cemeteryName;
	
	/**
	 * @see cz.rodro.entity.CemeteryEntity
	 **/
	public static volatile EntityType<CemeteryEntity> class_;
	
	/**
	 * @see cz.rodro.entity.CemeteryEntity#cemeteryLocation
	 **/
	public static volatile SingularAttribute<CemeteryEntity, LocationEntity> cemeteryLocation;

	public static final String WEB_LINK = "webLink";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String CEMETERY_NAME = "cemeteryName";
	public static final String CEMETERY_LOCATION = "cemeteryLocation";

}

