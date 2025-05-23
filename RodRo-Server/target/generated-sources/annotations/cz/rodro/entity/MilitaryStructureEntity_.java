package cz.rodro.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
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
	 * @see cz.rodro.entity.MilitaryStructureEntity#country
	 **/
	public static volatile SingularAttribute<MilitaryStructureEntity, CountryEntity> country;
	
	/**
	 * @see cz.rodro.entity.MilitaryStructureEntity#notes
	 **/
	public static volatile SingularAttribute<MilitaryStructureEntity, String> notes;
	
	/**
	 * @see cz.rodro.entity.MilitaryStructureEntity#unitName
	 **/
	public static volatile SingularAttribute<MilitaryStructureEntity, String> unitName;
	
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
	 * @see cz.rodro.entity.MilitaryStructureEntity#countryName
	 **/
	public static volatile SingularAttribute<MilitaryStructureEntity, String> countryName;
	
	/**
	 * @see cz.rodro.entity.MilitaryStructureEntity#armyBranch
	 **/
	public static volatile SingularAttribute<MilitaryStructureEntity, String> armyBranch;
	
	/**
	 * @see cz.rodro.entity.MilitaryStructureEntity
	 **/
	public static volatile EntityType<MilitaryStructureEntity> class_;
	
	/**
	 * @see cz.rodro.entity.MilitaryStructureEntity#armyName
	 **/
	public static volatile SingularAttribute<MilitaryStructureEntity, String> armyName;

	public static final String UNIT_TYPE = "unitType";
	public static final String COUNTRY = "country";
	public static final String NOTES = "notes";
	public static final String UNIT_NAME = "unitName";
	public static final String ACTIVE_TO_YEAR = "activeToYear";
	public static final String ACTIVE_FROM_YEAR = "activeFromYear";
	public static final String ID = "id";
	public static final String COUNTRY_NAME = "countryName";
	public static final String ARMY_BRANCH = "armyBranch";
	public static final String ARMY_NAME = "armyName";

}

