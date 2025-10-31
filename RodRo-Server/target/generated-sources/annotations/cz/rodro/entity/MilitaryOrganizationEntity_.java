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
	 * @see cz.rodro.entity.MilitaryOrganizationEntity#organizationImageUrl
	 **/
	public static volatile SingularAttribute<MilitaryOrganizationEntity, String> organizationImageUrl;
	
	/**
	 * @see cz.rodro.entity.MilitaryOrganizationEntity#country
	 **/
	public static volatile SingularAttribute<MilitaryOrganizationEntity, CountryEntity> country;
	
	/**
	 * @see cz.rodro.entity.MilitaryOrganizationEntity#organizationDescription
	 **/
	public static volatile SingularAttribute<MilitaryOrganizationEntity, String> organizationDescription;
	
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
	public static volatile SingularAttribute<MilitaryOrganizationEntity, MilitaryArmyBranchEntity> armyBranch;
	
	/**
	 * @see cz.rodro.entity.MilitaryOrganizationEntity
	 **/
	public static volatile EntityType<MilitaryOrganizationEntity> class_;
	
	/**
	 * @see cz.rodro.entity.MilitaryOrganizationEntity#militaryRanks
	 **/
	public static volatile ListAttribute<MilitaryOrganizationEntity, MilitaryRankEntity> militaryRanks;
	
	/**
	 * @see cz.rodro.entity.MilitaryOrganizationEntity#armyName
	 **/
	public static volatile SingularAttribute<MilitaryOrganizationEntity, String> armyName;

	public static final String ORGANIZATION_IMAGE_URL = "organizationImageUrl";
	public static final String COUNTRY = "country";
	public static final String ORGANIZATION_DESCRIPTION = "organizationDescription";
	public static final String ACTIVE_TO_YEAR = "activeToYear";
	public static final String STRUCTURES = "structures";
	public static final String ACTIVE_FROM_YEAR = "activeFromYear";
	public static final String ID = "id";
	public static final String ARMY_BRANCH = "armyBranch";
	public static final String MILITARY_RANKS = "militaryRanks";
	public static final String ARMY_NAME = "armyName";

}

