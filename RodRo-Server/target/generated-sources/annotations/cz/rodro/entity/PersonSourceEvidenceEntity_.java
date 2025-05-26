package cz.rodro.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(PersonSourceEvidenceEntity.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class PersonSourceEvidenceEntity_ {

	
	/**
	 * @see cz.rodro.entity.PersonSourceEvidenceEntity#personName
	 **/
	public static volatile SingularAttribute<PersonSourceEvidenceEntity, String> personName;
	
	/**
	 * @see cz.rodro.entity.PersonSourceEvidenceEntity#person
	 **/
	public static volatile SingularAttribute<PersonSourceEvidenceEntity, PersonEntity> person;
	
	/**
	 * @see cz.rodro.entity.PersonSourceEvidenceEntity#personMilitaryService
	 **/
	public static volatile SingularAttribute<PersonSourceEvidenceEntity, PersonMilitaryServiceEntity> personMilitaryService;
	
	/**
	 * @see cz.rodro.entity.PersonSourceEvidenceEntity#id
	 **/
	public static volatile SingularAttribute<PersonSourceEvidenceEntity, Long> id;
	
	/**
	 * @see cz.rodro.entity.PersonSourceEvidenceEntity#sourceName
	 **/
	public static volatile SingularAttribute<PersonSourceEvidenceEntity, String> sourceName;
	
	/**
	 * @see cz.rodro.entity.PersonSourceEvidenceEntity#source
	 **/
	public static volatile SingularAttribute<PersonSourceEvidenceEntity, SourceEntity> source;
	
	/**
	 * @see cz.rodro.entity.PersonSourceEvidenceEntity
	 **/
	public static volatile EntityType<PersonSourceEvidenceEntity> class_;

	public static final String PERSON_NAME = "personName";
	public static final String PERSON = "person";
	public static final String PERSON_MILITARY_SERVICE = "personMilitaryService";
	public static final String ID = "id";
	public static final String SOURCE_NAME = "sourceName";
	public static final String SOURCE = "source";

}

