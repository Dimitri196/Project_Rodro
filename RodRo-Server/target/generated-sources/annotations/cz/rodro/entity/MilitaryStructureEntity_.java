package cz.rodro.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(MilitaryStructureEntity.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class MilitaryStructureEntity_ {

	
	/**
	 * @see cz.rodro.entity.MilitaryStructureEntity#unitType
	 **/
	public static volatile SingularAttribute<MilitaryStructureEntity, String> unitType;
	
	/**
	 * @see cz.rodro.entity.MilitaryStructureEntity#notes
	 **/
	public static volatile SingularAttribute<MilitaryStructureEntity, String> notes;
	
	/**
	 * @see cz.rodro.entity.MilitaryStructureEntity#subStructures
	 **/
	public static volatile ListAttribute<MilitaryStructureEntity, MilitaryStructureEntity> subStructures;
	
	/**
	 * @see cz.rodro.entity.MilitaryStructureEntity#unitName
	 **/
	public static volatile SingularAttribute<MilitaryStructureEntity, String> unitName;
	
	/**
	 * @see cz.rodro.entity.MilitaryStructureEntity#bannerImageUrl
	 **/
	public static volatile SingularAttribute<MilitaryStructureEntity, String> bannerImageUrl;
	
	/**
	 * @see cz.rodro.entity.MilitaryStructureEntity#organization
	 **/
	public static volatile SingularAttribute<MilitaryStructureEntity, MilitaryOrganizationEntity> organization;
	
	/**
	 * @see cz.rodro.entity.MilitaryStructureEntity#activeToYear
	 **/
	public static volatile SingularAttribute<MilitaryStructureEntity, String> activeToYear;
	
	/**
	 * @see cz.rodro.entity.MilitaryStructureEntity#activeFromYear
	 **/
	public static volatile SingularAttribute<MilitaryStructureEntity, String> activeFromYear;
	
	/**
	 * @see cz.rodro.entity.MilitaryStructureEntity#id
	 **/
	public static volatile SingularAttribute<MilitaryStructureEntity, Long> id;
	
	/**
	 * @see cz.rodro.entity.MilitaryStructureEntity#parentStructure
	 **/
	public static volatile SingularAttribute<MilitaryStructureEntity, MilitaryStructureEntity> parentStructure;
	
	/**
	 * @see cz.rodro.entity.MilitaryStructureEntity
	 **/
	public static volatile EntityType<MilitaryStructureEntity> class_;

	public static final String UNIT_TYPE = "unitType";
	public static final String NOTES = "notes";
	public static final String SUB_STRUCTURES = "subStructures";
	public static final String UNIT_NAME = "unitName";
	public static final String BANNER_IMAGE_URL = "bannerImageUrl";
	public static final String ORGANIZATION = "organization";
	public static final String ACTIVE_TO_YEAR = "activeToYear";
	public static final String ACTIVE_FROM_YEAR = "activeFromYear";
	public static final String ID = "id";
	public static final String PARENT_STRUCTURE = "parentStructure";

}

