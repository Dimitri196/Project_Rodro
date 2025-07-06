package cz.rodro.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDate;

@StaticMetamodel(PersonOccupationEntity.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class PersonOccupationEntity_ {

	
	/**
	 * @see cz.rodro.entity.PersonOccupationEntity#occupationEndDate
	 **/
	public static volatile SingularAttribute<PersonOccupationEntity, LocalDate> occupationEndDate;
	
	/**
	 * @see cz.rodro.entity.PersonOccupationEntity#occupation
	 **/
	public static volatile SingularAttribute<PersonOccupationEntity, OccupationEntity> occupation;
	
	/**
	 * @see cz.rodro.entity.PersonOccupationEntity#person
	 **/
	public static volatile SingularAttribute<PersonOccupationEntity, PersonEntity> person;
	
	/**
	 * @see cz.rodro.entity.PersonOccupationEntity#occupationStartDate
	 **/
	public static volatile SingularAttribute<PersonOccupationEntity, LocalDate> occupationStartDate;
	
	/**
	 * @see cz.rodro.entity.PersonOccupationEntity#id
	 **/
	public static volatile SingularAttribute<PersonOccupationEntity, Long> id;
	
	/**
	 * @see cz.rodro.entity.PersonOccupationEntity
	 **/
	public static volatile EntityType<PersonOccupationEntity> class_;

	public static final String OCCUPATION_END_DATE = "occupationEndDate";
	public static final String OCCUPATION = "occupation";
	public static final String PERSON = "person";
	public static final String OCCUPATION_START_DATE = "occupationStartDate";
	public static final String ID = "id";

}

