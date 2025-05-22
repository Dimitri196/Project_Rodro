package cz.rodro.entity;

import cz.rodro.constant.Gender;
import cz.rodro.constant.SocialStatus;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDate;

@StaticMetamodel(PersonEntity.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class PersonEntity_ {

	
	/**
	 * @see cz.rodro.entity.PersonEntity#note
	 **/
	public static volatile SingularAttribute<PersonEntity, String> note;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#burialPlace
	 **/
	public static volatile SingularAttribute<PersonEntity, LocationEntity> burialPlace;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#gender
	 **/
	public static volatile SingularAttribute<PersonEntity, Gender> gender;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#givenName
	 **/
	public static volatile SingularAttribute<PersonEntity, String> givenName;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#father
	 **/
	public static volatile SingularAttribute<PersonEntity, PersonEntity> father;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#baptizationPlace
	 **/
	public static volatile SingularAttribute<PersonEntity, LocationEntity> baptizationPlace;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#socialStatus
	 **/
	public static volatile SingularAttribute<PersonEntity, SocialStatus> socialStatus;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#baptizationDate
	 **/
	public static volatile SingularAttribute<PersonEntity, LocalDate> baptizationDate;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#occupations
	 **/
	public static volatile ListAttribute<PersonEntity, PersonOccupationEntity> occupations;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#birthDate
	 **/
	public static volatile SingularAttribute<PersonEntity, LocalDate> birthDate;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#birthPlace
	 **/
	public static volatile SingularAttribute<PersonEntity, LocationEntity> birthPlace;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#mother
	 **/
	public static volatile SingularAttribute<PersonEntity, PersonEntity> mother;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#burialDate
	 **/
	public static volatile SingularAttribute<PersonEntity, LocalDate> burialDate;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#sourceEvidences
	 **/
	public static volatile ListAttribute<PersonEntity, PersonSourceEvidenceEntity> sourceEvidences;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#deathPlace
	 **/
	public static volatile SingularAttribute<PersonEntity, LocationEntity> deathPlace;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#givenSurname
	 **/
	public static volatile SingularAttribute<PersonEntity, String> givenSurname;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#deathDate
	 **/
	public static volatile SingularAttribute<PersonEntity, LocalDate> deathDate;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#identificationNumber
	 **/
	public static volatile SingularAttribute<PersonEntity, String> identificationNumber;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#id
	 **/
	public static volatile SingularAttribute<PersonEntity, Long> id;
	
	/**
	 * @see cz.rodro.entity.PersonEntity
	 **/
	public static volatile EntityType<PersonEntity> class_;

	public static final String NOTE = "note";
	public static final String BURIAL_PLACE = "burialPlace";
	public static final String GENDER = "gender";
	public static final String GIVEN_NAME = "givenName";
	public static final String FATHER = "father";
	public static final String BAPTIZATION_PLACE = "baptizationPlace";
	public static final String SOCIAL_STATUS = "socialStatus";
	public static final String BAPTIZATION_DATE = "baptizationDate";
	public static final String OCCUPATIONS = "occupations";
	public static final String BIRTH_DATE = "birthDate";
	public static final String BIRTH_PLACE = "birthPlace";
	public static final String MOTHER = "mother";
	public static final String BURIAL_DATE = "burialDate";
	public static final String SOURCE_EVIDENCES = "sourceEvidences";
	public static final String DEATH_PLACE = "deathPlace";
	public static final String GIVEN_SURNAME = "givenSurname";
	public static final String DEATH_DATE = "deathDate";
	public static final String IDENTIFICATION_NUMBER = "identificationNumber";
	public static final String ID = "id";

}

