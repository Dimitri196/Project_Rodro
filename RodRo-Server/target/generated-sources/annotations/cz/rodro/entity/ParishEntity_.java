package cz.rodro.entity;

import cz.rodro.constant.ConfessionType;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(ParishEntity.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class ParishEntity_ {

	
	/**
	 * @see cz.rodro.entity.ParishEntity#mainChurchName
	 **/
	public static volatile SingularAttribute<ParishEntity, String> mainChurchName;
	
	/**
	 * @see cz.rodro.entity.ParishEntity#churchImageUrl
	 **/
	public static volatile SingularAttribute<ParishEntity, String> churchImageUrl;
	
	/**
	 * @see cz.rodro.entity.ParishEntity#confession
	 **/
	public static volatile SingularAttribute<ParishEntity, ConfessionType> confession;
	
	/**
	 * @see cz.rodro.entity.ParishEntity#name
	 **/
	public static volatile SingularAttribute<ParishEntity, String> name;
	
	/**
	 * @see cz.rodro.entity.ParishEntity#cancellationYear
	 **/
	public static volatile SingularAttribute<ParishEntity, Integer> cancellationYear;
	
	/**
	 * @see cz.rodro.entity.ParishEntity#description
	 **/
	public static volatile SingularAttribute<ParishEntity, String> description;
	
	/**
	 * @see cz.rodro.entity.ParishEntity#locations
	 **/
	public static volatile ListAttribute<ParishEntity, ParishLocationEntity> locations;
	
	/**
	 * @see cz.rodro.entity.ParishEntity#id
	 **/
	public static volatile SingularAttribute<ParishEntity, Long> id;
	
	/**
	 * @see cz.rodro.entity.ParishEntity
	 **/
	public static volatile EntityType<ParishEntity> class_;
	
	/**
	 * @see cz.rodro.entity.ParishEntity#establishmentYear
	 **/
	public static volatile SingularAttribute<ParishEntity, Integer> establishmentYear;

	public static final String MAIN_CHURCH_NAME = "mainChurchName";
	public static final String CHURCH_IMAGE_URL = "churchImageUrl";
	public static final String CONFESSION = "confession";
	public static final String NAME = "name";
	public static final String CANCELLATION_YEAR = "cancellationYear";
	public static final String DESCRIPTION = "description";
	public static final String LOCATIONS = "locations";
	public static final String ID = "id";
	public static final String ESTABLISHMENT_YEAR = "establishmentYear";

}

