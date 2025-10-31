package cz.rodro.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(PersonOccupationEntity.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class PersonOccupationEntity_ {

	
	/**
	 * @see cz.rodro.entity.PersonOccupationEntity#occupation
	 **/
	public static volatile SingularAttribute<PersonOccupationEntity, OccupationEntity> occupation;
	
	/**
	 * @see cz.rodro.entity.PersonOccupationEntity#person
	 **/
	public static volatile SingularAttribute<PersonOccupationEntity, PersonEntity> person;
	
	/**
	 * @see cz.rodro.entity.PersonOccupationEntity#startYear
	 **/
	public static volatile SingularAttribute<PersonOccupationEntity, Integer> startYear;
	
	/**
	 * @see cz.rodro.entity.PersonOccupationEntity#id
	 **/
	public static volatile SingularAttribute<PersonOccupationEntity, Long> id;
	
	/**
	 * @see cz.rodro.entity.PersonOccupationEntity
	 **/
	public static volatile EntityType<PersonOccupationEntity> class_;
	
	/**
	 * @see cz.rodro.entity.PersonOccupationEntity#endYear
	 **/
	public static volatile SingularAttribute<PersonOccupationEntity, Integer> endYear;

	public static final String OCCUPATION = "occupation";
	public static final String PERSON = "person";
	public static final String START_YEAR = "startYear";
	public static final String ID = "id";
	public static final String END_YEAR = "endYear";

}

