package cz.rodro.entity;

import cz.rodro.constant.MaritalStatus;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDate;

@StaticMetamodel(FamilyEntity.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class FamilyEntity_ {

	
	/**
	 * @see cz.rodro.entity.FamilyEntity#witnessesFemaleSide1
	 **/
	public static volatile SingularAttribute<FamilyEntity, PersonEntity> witnessesFemaleSide1;
	
	/**
	 * @see cz.rodro.entity.FamilyEntity#note
	 **/
	public static volatile SingularAttribute<FamilyEntity, String> note;
	
	/**
	 * @see cz.rodro.entity.FamilyEntity#witnessesMaleSide2
	 **/
	public static volatile SingularAttribute<FamilyEntity, PersonEntity> witnessesMaleSide2;
	
	/**
	 * @see cz.rodro.entity.FamilyEntity#witnessesMaleSide1
	 **/
	public static volatile SingularAttribute<FamilyEntity, PersonEntity> witnessesMaleSide1;
	
	/**
	 * @see cz.rodro.entity.FamilyEntity#marriageLocation
	 **/
	public static volatile SingularAttribute<FamilyEntity, LocationEntity> marriageLocation;
	
	/**
	 * @see cz.rodro.entity.FamilyEntity#source
	 **/
	public static volatile SingularAttribute<FamilyEntity, String> source;
	
	/**
	 * @see cz.rodro.entity.FamilyEntity#maritalStatusForSpouseMale
	 **/
	public static volatile SingularAttribute<FamilyEntity, MaritalStatus> maritalStatusForSpouseMale;
	
	/**
	 * @see cz.rodro.entity.FamilyEntity#spouseFemale
	 **/
	public static volatile SingularAttribute<FamilyEntity, PersonEntity> spouseFemale;
	
	/**
	 * @see cz.rodro.entity.FamilyEntity#witnessesFemaleSide2
	 **/
	public static volatile SingularAttribute<FamilyEntity, PersonEntity> witnessesFemaleSide2;
	
	/**
	 * @see cz.rodro.entity.FamilyEntity#children
	 **/
	public static volatile ListAttribute<FamilyEntity, PersonEntity> children;
	
	/**
	 * @see cz.rodro.entity.FamilyEntity#maritalStatusForSpouseFemale
	 **/
	public static volatile SingularAttribute<FamilyEntity, MaritalStatus> maritalStatusForSpouseFemale;
	
	/**
	 * @see cz.rodro.entity.FamilyEntity#spouseMale
	 **/
	public static volatile SingularAttribute<FamilyEntity, PersonEntity> spouseMale;
	
	/**
	 * @see cz.rodro.entity.FamilyEntity#id
	 **/
	public static volatile SingularAttribute<FamilyEntity, Long> id;
	
	/**
	 * @see cz.rodro.entity.FamilyEntity#marriageDate
	 **/
	public static volatile SingularAttribute<FamilyEntity, LocalDate> marriageDate;
	
	/**
	 * @see cz.rodro.entity.FamilyEntity
	 **/
	public static volatile EntityType<FamilyEntity> class_;

	public static final String WITNESSES_FEMALE_SIDE1 = "witnessesFemaleSide1";
	public static final String NOTE = "note";
	public static final String WITNESSES_MALE_SIDE2 = "witnessesMaleSide2";
	public static final String WITNESSES_MALE_SIDE1 = "witnessesMaleSide1";
	public static final String MARRIAGE_LOCATION = "marriageLocation";
	public static final String SOURCE = "source";
	public static final String MARITAL_STATUS_FOR_SPOUSE_MALE = "maritalStatusForSpouseMale";
	public static final String SPOUSE_FEMALE = "spouseFemale";
	public static final String WITNESSES_FEMALE_SIDE2 = "witnessesFemaleSide2";
	public static final String CHILDREN = "children";
	public static final String MARITAL_STATUS_FOR_SPOUSE_FEMALE = "maritalStatusForSpouseFemale";
	public static final String SPOUSE_MALE = "spouseMale";
	public static final String ID = "id";
	public static final String MARRIAGE_DATE = "marriageDate";

}

