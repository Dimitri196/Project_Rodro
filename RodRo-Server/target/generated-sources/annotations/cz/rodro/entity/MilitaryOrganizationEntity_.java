package cz.rodro.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(MilitaryOrganizationEntity.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class MilitaryOrganizationEntity_ {

	
	/**
	 * @see cz.rodro.entity.MilitaryOrganizationEntity#country
	 **/
	public static volatile SingularAttribute<MilitaryOrganizationEntity, CountryEntity> country;
	
	/**
	 * @see cz.rodro.entity.MilitaryOrganizationEntity#activeToYear
	 **/
	public static volatile SingularAttribute<MilitaryOrganizationEntity, String> activeToYear;
	
	/**
	 * @see cz.rodro.entity.MilitaryOrganizationEntity#structures
	 **/
	public static volatile ListAttribute<MilitaryOrganizationEntity, MilitaryStructureEntity> structures;
	
	/**
	 * @see cz.rodro.entity.MilitaryOrganizationEntity#activeFromYear
	 **/
	public static volatile SingularAttribute<MilitaryOrganizationEntity, String> activeFromYear;
	
	/**
	 * @see cz.rodro.entity.MilitaryOrganizationEntity#id
	 **/
	public static volatile SingularAttribute<MilitaryOrganizationEntity, Long> id;
	
	/**
	 * @see cz.rodro.entity.MilitaryOrganizationEntity#armyBranch
	 **/
	public static volatile SingularAttribute<MilitaryOrganizationEntity, String> armyBranch;
	
	/**
	 * @see cz.rodro.entity.MilitaryOrganizationEntity
	 **/
	public static volatile EntityType<MilitaryOrganizationEntity> class_;
	
	/**
	 * @see cz.rodro.entity.MilitaryOrganizationEntity#armyName
	 **/
	public static volatile SingularAttribute<MilitaryOrganizationEntity, String> armyName;

	public static final String COUNTRY = "country";
	public static final String ACTIVE_TO_YEAR = "activeToYear";
	public static final String STRUCTURES = "structures";
	public static final String ACTIVE_FROM_YEAR = "activeFromYear";
	public static final String ID = "id";
	public static final String ARMY_BRANCH = "armyBranch";
	public static final String ARMY_NAME = "armyName";

}

