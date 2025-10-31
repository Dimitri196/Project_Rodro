package cz.rodro.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(OccupationEntity.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class OccupationEntity_ {

	
	/**
	 * @see cz.rodro.entity.OccupationEntity#institution
	 **/
	public static volatile SingularAttribute<OccupationEntity, InstitutionEntity> institution;
	
	/**
	 * @see cz.rodro.entity.OccupationEntity#personOccupations
	 **/
	public static volatile ListAttribute<OccupationEntity, PersonOccupationEntity> personOccupations;
	
	/**
	 * @see cz.rodro.entity.OccupationEntity#personImageUrl
	 **/
	public static volatile SingularAttribute<OccupationEntity, String> personImageUrl;
	
	/**
	 * @see cz.rodro.entity.OccupationEntity#occupationName
	 **/
	public static volatile SingularAttribute<OccupationEntity, String> occupationName;
	
	/**
	 * @see cz.rodro.entity.OccupationEntity#description
	 **/
	public static volatile SingularAttribute<OccupationEntity, String> description;
	
	/**
	 * @see cz.rodro.entity.OccupationEntity#id
	 **/
	public static volatile SingularAttribute<OccupationEntity, Long> id;
	
	/**
	 * @see cz.rodro.entity.OccupationEntity
	 **/
	public static volatile EntityType<OccupationEntity> class_;

	public static final String INSTITUTION = "institution";
	public static final String PERSON_OCCUPATIONS = "personOccupations";
	public static final String PERSON_IMAGE_URL = "personImageUrl";
	public static final String OCCUPATION_NAME = "occupationName";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";

}

