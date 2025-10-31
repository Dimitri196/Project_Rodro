package cz.rodro.entity;

import cz.rodro.constant.CauseOfDeath;
import cz.rodro.constant.Gender;
import cz.rodro.constant.SocialStatus;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(PersonEntity.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class PersonEntity_ extends cz.rodro.entity.helper.AuditableEntity_ {

	
	/**
	 * @see cz.rodro.entity.PersonEntity#burialPlace
	 **/
	public static volatile SingularAttribute<PersonEntity, LocationEntity> burialPlace;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#gender
	 **/
	public static volatile SingularAttribute<PersonEntity, Gender> gender;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#baptismMonth
	 **/
	public static volatile SingularAttribute<PersonEntity, Integer> baptismMonth;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#father
	 **/
	public static volatile SingularAttribute<PersonEntity, PersonEntity> father;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#baptismDay
	 **/
	public static volatile SingularAttribute<PersonEntity, Integer> baptismDay;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#bioNote
	 **/
	public static volatile SingularAttribute<PersonEntity, String> bioNote;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#occupations
	 **/
	public static volatile ListAttribute<PersonEntity, PersonOccupationEntity> occupations;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#birthPlace
	 **/
	public static volatile SingularAttribute<PersonEntity, LocationEntity> birthPlace;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#mother
	 **/
	public static volatile SingularAttribute<PersonEntity, PersonEntity> mother;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#deathMonth
	 **/
	public static volatile SingularAttribute<PersonEntity, Integer> deathMonth;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#baptismYear
	 **/
	public static volatile SingularAttribute<PersonEntity, Integer> baptismYear;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#sourceEvidences
	 **/
	public static volatile ListAttribute<PersonEntity, PersonSourceEvidenceEntity> sourceEvidences;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#deathPlace
	 **/
	public static volatile SingularAttribute<PersonEntity, LocationEntity> deathPlace;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#surname
	 **/
	public static volatile SingularAttribute<PersonEntity, String> surname;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#id
	 **/
	public static volatile SingularAttribute<PersonEntity, Long> id;
	
	/**
	 * @see cz.rodro.entity.PersonEntity
	 **/
	public static volatile EntityType<PersonEntity> class_;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#burialMonth
	 **/
	public static volatile SingularAttribute<PersonEntity, Integer> burialMonth;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#birthDay
	 **/
	public static volatile SingularAttribute<PersonEntity, Integer> birthDay;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#givenName
	 **/
	public static volatile SingularAttribute<PersonEntity, String> givenName;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#burialYear
	 **/
	public static volatile SingularAttribute<PersonEntity, Integer> burialYear;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#externalId
	 **/
	public static volatile SingularAttribute<PersonEntity, String> externalId;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#baptismPlace
	 **/
	public static volatile SingularAttribute<PersonEntity, LocationEntity> baptismPlace;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#socialStatus
	 **/
	public static volatile SingularAttribute<PersonEntity, SocialStatus> socialStatus;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#deathDay
	 **/
	public static volatile SingularAttribute<PersonEntity, Integer> deathDay;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#birthMonth
	 **/
	public static volatile SingularAttribute<PersonEntity, Integer> birthMonth;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#birthYear
	 **/
	public static volatile SingularAttribute<PersonEntity, Integer> birthYear;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#deathYear
	 **/
	public static volatile SingularAttribute<PersonEntity, Integer> deathYear;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#causeOfDeath
	 **/
	public static volatile SingularAttribute<PersonEntity, CauseOfDeath> causeOfDeath;
	
	/**
	 * @see cz.rodro.entity.PersonEntity#burialDay
	 **/
	public static volatile SingularAttribute<PersonEntity, Integer> burialDay;

	public static final String BURIAL_PLACE = "burialPlace";
	public static final String GENDER = "gender";
	public static final String BAPTISM_MONTH = "baptismMonth";
	public static final String FATHER = "father";
	public static final String BAPTISM_DAY = "baptismDay";
	public static final String BIO_NOTE = "bioNote";
	public static final String OCCUPATIONS = "occupations";
	public static final String BIRTH_PLACE = "birthPlace";
	public static final String MOTHER = "mother";
	public static final String DEATH_MONTH = "deathMonth";
	public static final String BAPTISM_YEAR = "baptismYear";
	public static final String SOURCE_EVIDENCES = "sourceEvidences";
	public static final String DEATH_PLACE = "deathPlace";
	public static final String SURNAME = "surname";
	public static final String ID = "id";
	public static final String BURIAL_MONTH = "burialMonth";
	public static final String BIRTH_DAY = "birthDay";
	public static final String GIVEN_NAME = "givenName";
	public static final String BURIAL_YEAR = "burialYear";
	public static final String EXTERNAL_ID = "externalId";
	public static final String BAPTISM_PLACE = "baptismPlace";
	public static final String SOCIAL_STATUS = "socialStatus";
	public static final String DEATH_DAY = "deathDay";
	public static final String BIRTH_MONTH = "birthMonth";
	public static final String BIRTH_YEAR = "birthYear";
	public static final String DEATH_YEAR = "deathYear";
	public static final String CAUSE_OF_DEATH = "causeOfDeath";
	public static final String BURIAL_DAY = "burialDay";

}

