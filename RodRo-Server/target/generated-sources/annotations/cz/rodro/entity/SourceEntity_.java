package cz.rodro.entity;

import cz.rodro.constant.SourceType;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(SourceEntity.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class SourceEntity_ {

	
	/**
	 * @see cz.rodro.entity.SourceEntity#sourceReference
	 **/
	public static volatile SingularAttribute<SourceEntity, String> sourceReference;
	
	/**
	 * @see cz.rodro.entity.SourceEntity#sourceUrl
	 **/
	public static volatile SingularAttribute<SourceEntity, String> sourceUrl;
	
	/**
	 * @see cz.rodro.entity.SourceEntity#sourceType
	 **/
	public static volatile SingularAttribute<SourceEntity, SourceType> sourceType;
	
	/**
	 * @see cz.rodro.entity.SourceEntity#personSourceEvidences
	 **/
	public static volatile ListAttribute<SourceEntity, PersonSourceEvidenceEntity> personSourceEvidences;
	
	/**
	 * @see cz.rodro.entity.SourceEntity#sourceTitle
	 **/
	public static volatile SingularAttribute<SourceEntity, String> sourceTitle;
	
	/**
	 * @see cz.rodro.entity.SourceEntity#sourceLocation
	 **/
	public static volatile SingularAttribute<SourceEntity, LocationEntity> sourceLocation;
	
	/**
	 * @see cz.rodro.entity.SourceEntity#id
	 **/
	public static volatile SingularAttribute<SourceEntity, Long> id;
	
	/**
	 * @see cz.rodro.entity.SourceEntity#sourceDescription
	 **/
	public static volatile SingularAttribute<SourceEntity, String> sourceDescription;
	
	/**
	 * @see cz.rodro.entity.SourceEntity
	 **/
	public static volatile EntityType<SourceEntity> class_;

	public static final String SOURCE_REFERENCE = "sourceReference";
	public static final String SOURCE_URL = "sourceUrl";
	public static final String SOURCE_TYPE = "sourceType";
	public static final String PERSON_SOURCE_EVIDENCES = "personSourceEvidences";
	public static final String SOURCE_TITLE = "sourceTitle";
	public static final String SOURCE_LOCATION = "sourceLocation";
	public static final String ID = "id";
	public static final String SOURCE_DESCRIPTION = "sourceDescription";

}

