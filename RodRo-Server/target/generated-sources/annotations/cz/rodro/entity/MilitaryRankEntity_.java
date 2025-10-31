package cz.rodro.entity;

import cz.rodro.constant.MilitaryRankLevel;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(MilitaryRankEntity.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class MilitaryRankEntity_ {

	
	/**
	 * @see cz.rodro.entity.MilitaryRankEntity#notes
	 **/
	public static volatile SingularAttribute<MilitaryRankEntity, String> notes;
	
	/**
	 * @see cz.rodro.entity.MilitaryRankEntity#personMilitaryServices
	 **/
	public static volatile ListAttribute<MilitaryRankEntity, PersonMilitaryServiceEntity> personMilitaryServices;
	
	/**
	 * @see cz.rodro.entity.MilitaryRankEntity#rankLevel
	 **/
	public static volatile SingularAttribute<MilitaryRankEntity, MilitaryRankLevel> rankLevel;
	
	/**
	 * @see cz.rodro.entity.MilitaryRankEntity#rankName
	 **/
	public static volatile SingularAttribute<MilitaryRankEntity, String> rankName;
	
	/**
	 * @see cz.rodro.entity.MilitaryRankEntity#activeToYear
	 **/
	public static volatile SingularAttribute<MilitaryRankEntity, String> activeToYear;
	
	/**
	 * @see cz.rodro.entity.MilitaryRankEntity#rankDescription
	 **/
	public static volatile SingularAttribute<MilitaryRankEntity, String> rankDescription;
	
	/**
	 * @see cz.rodro.entity.MilitaryRankEntity#activeFromYear
	 **/
	public static volatile SingularAttribute<MilitaryRankEntity, String> activeFromYear;
	
	/**
	 * @see cz.rodro.entity.MilitaryRankEntity#rankImageUrl
	 **/
	public static volatile SingularAttribute<MilitaryRankEntity, String> rankImageUrl;
	
	/**
	 * @see cz.rodro.entity.MilitaryRankEntity#militaryStructure
	 **/
	public static volatile SingularAttribute<MilitaryRankEntity, MilitaryStructureEntity> militaryStructure;
	
	/**
	 * @see cz.rodro.entity.MilitaryRankEntity#id
	 **/
	public static volatile SingularAttribute<MilitaryRankEntity, Long> id;
	
	/**
	 * @see cz.rodro.entity.MilitaryRankEntity
	 **/
	public static volatile EntityType<MilitaryRankEntity> class_;
	
	/**
	 * @see cz.rodro.entity.MilitaryRankEntity#militaryOrganization
	 **/
	public static volatile SingularAttribute<MilitaryRankEntity, MilitaryOrganizationEntity> militaryOrganization;

	public static final String NOTES = "notes";
	public static final String PERSON_MILITARY_SERVICES = "personMilitaryServices";
	public static final String RANK_LEVEL = "rankLevel";
	public static final String RANK_NAME = "rankName";
	public static final String ACTIVE_TO_YEAR = "activeToYear";
	public static final String RANK_DESCRIPTION = "rankDescription";
	public static final String ACTIVE_FROM_YEAR = "activeFromYear";
	public static final String RANK_IMAGE_URL = "rankImageUrl";
	public static final String MILITARY_STRUCTURE = "militaryStructure";
	public static final String ID = "id";
	public static final String MILITARY_ORGANIZATION = "militaryOrganization";

}

