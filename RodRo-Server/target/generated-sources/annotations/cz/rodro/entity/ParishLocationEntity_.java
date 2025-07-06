package cz.rodro.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(ParishLocationEntity.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class ParishLocationEntity_ {

	
	/**
	 * @see cz.rodro.entity.ParishLocationEntity#locationName
	 **/
	public static volatile SingularAttribute<ParishLocationEntity, String> locationName;
	
	/**
	 * @see cz.rodro.entity.ParishLocationEntity#parish
	 **/
	public static volatile SingularAttribute<ParishLocationEntity, ParishEntity> parish;
	
	/**
	 * @see cz.rodro.entity.ParishLocationEntity#parishName
	 **/
	public static volatile SingularAttribute<ParishLocationEntity, String> parishName;
	
	/**
	 * @see cz.rodro.entity.ParishLocationEntity#location
	 **/
	public static volatile SingularAttribute<ParishLocationEntity, LocationEntity> location;
	
	/**
	 * @see cz.rodro.entity.ParishLocationEntity#id
	 **/
	public static volatile SingularAttribute<ParishLocationEntity, Long> id;
	
	/**
	 * @see cz.rodro.entity.ParishLocationEntity
	 **/
	public static volatile EntityType<ParishLocationEntity> class_;

	public static final String LOCATION_NAME = "locationName";
	public static final String PARISH = "parish";
	public static final String PARISH_NAME = "parishName";
	public static final String LOCATION = "location";
	public static final String ID = "id";

}

