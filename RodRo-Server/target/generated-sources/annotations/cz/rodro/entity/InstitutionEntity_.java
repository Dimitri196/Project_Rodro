package cz.rodro.entity;

import cz.rodro.constant.InstitutionType;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(InstitutionEntity.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class InstitutionEntity_ {

	
	/**
	 * @see cz.rodro.entity.InstitutionEntity#institutionLocation
	 **/
	public static volatile SingularAttribute<InstitutionEntity, LocationEntity> institutionLocation;
	
	/**
	 * @see cz.rodro.entity.InstitutionEntity#institutionName
	 **/
	public static volatile SingularAttribute<InstitutionEntity, String> institutionName;
	
	/**
	 * @see cz.rodro.entity.InstitutionEntity#sealImageUrl
	 **/
	public static volatile SingularAttribute<InstitutionEntity, String> sealImageUrl;
	
	/**
	 * @see cz.rodro.entity.InstitutionEntity#institutionType
	 **/
	public static volatile SingularAttribute<InstitutionEntity, InstitutionType> institutionType;
	
	/**
	 * @see cz.rodro.entity.InstitutionEntity#id
	 **/
	public static volatile SingularAttribute<InstitutionEntity, Long> id;
	
	/**
	 * @see cz.rodro.entity.InstitutionEntity#occupations
	 **/
	public static volatile ListAttribute<InstitutionEntity, OccupationEntity> occupations;
	
	/**
	 * @see cz.rodro.entity.InstitutionEntity
	 **/
	public static volatile EntityType<InstitutionEntity> class_;
	
	/**
	 * @see cz.rodro.entity.InstitutionEntity#institutionDescription
	 **/
	public static volatile SingularAttribute<InstitutionEntity, String> institutionDescription;

	public static final String INSTITUTION_LOCATION = "institutionLocation";
	public static final String INSTITUTION_NAME = "institutionName";
	public static final String SEAL_IMAGE_URL = "sealImageUrl";
	public static final String INSTITUTION_TYPE = "institutionType";
	public static final String ID = "id";
	public static final String OCCUPATIONS = "occupations";
	public static final String INSTITUTION_DESCRIPTION = "institutionDescription";

}

