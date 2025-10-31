package cz.rodro.entity;

import cz.rodro.constant.AttributionType;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(SourceAttributionEntity.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class SourceAttributionEntity_ {

	
	/**
	 * @see cz.rodro.entity.SourceAttributionEntity#note
	 **/
	public static volatile SingularAttribute<SourceAttributionEntity, String> note;
	
	/**
	 * @see cz.rodro.entity.SourceAttributionEntity#occupation
	 **/
	public static volatile SingularAttribute<SourceAttributionEntity, PersonOccupationEntity> occupation;
	
	/**
	 * @see cz.rodro.entity.SourceAttributionEntity#person
	 **/
	public static volatile SingularAttribute<SourceAttributionEntity, PersonEntity> person;
	
	/**
	 * @see cz.rodro.entity.SourceAttributionEntity#militaryService
	 **/
	public static volatile SingularAttribute<SourceAttributionEntity, PersonMilitaryServiceEntity> militaryService;
	
	/**
	 * @see cz.rodro.entity.SourceAttributionEntity#id
	 **/
	public static volatile SingularAttribute<SourceAttributionEntity, Long> id;
	
	/**
	 * @see cz.rodro.entity.SourceAttributionEntity#source
	 **/
	public static volatile SingularAttribute<SourceAttributionEntity, SourceEntity> source;
	
	/**
	 * @see cz.rodro.entity.SourceAttributionEntity#type
	 **/
	public static volatile SingularAttribute<SourceAttributionEntity, AttributionType> type;
	
	/**
	 * @see cz.rodro.entity.SourceAttributionEntity#family
	 **/
	public static volatile SingularAttribute<SourceAttributionEntity, FamilyEntity> family;
	
	/**
	 * @see cz.rodro.entity.SourceAttributionEntity
	 **/
	public static volatile EntityType<SourceAttributionEntity> class_;

	public static final String NOTE = "note";
	public static final String OCCUPATION = "occupation";
	public static final String PERSON = "person";
	public static final String MILITARY_SERVICE = "militaryService";
	public static final String ID = "id";
	public static final String SOURCE = "source";
	public static final String TYPE = "type";
	public static final String FAMILY = "family";

}

