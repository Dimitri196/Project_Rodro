package cz.rodro.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(MilitaryOccupationEntity.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class MilitaryOccupationEntity_ {

	
	/**
	 * @see cz.rodro.entity.MilitaryOccupationEntity#rankCode
	 **/
	public static volatile SingularAttribute<MilitaryOccupationEntity, String> rankCode;
	
	/**
	 * @see cz.rodro.entity.MilitaryOccupationEntity#notes
	 **/
	public static volatile SingularAttribute<MilitaryOccupationEntity, String> notes;
	
	/**
	 * @see cz.rodro.entity.MilitaryOccupationEntity#rankName
	 **/
	public static volatile SingularAttribute<MilitaryOccupationEntity, String> rankName;
	
	/**
	 * @see cz.rodro.entity.MilitaryOccupationEntity#description
	 **/
	public static volatile SingularAttribute<MilitaryOccupationEntity, String> description;
	
	/**
	 * @see cz.rodro.entity.MilitaryOccupationEntity#id
	 **/
	public static volatile SingularAttribute<MilitaryOccupationEntity, Long> id;
	
	/**
	 * @see cz.rodro.entity.MilitaryOccupationEntity
	 **/
	public static volatile EntityType<MilitaryOccupationEntity> class_;
	
	/**
	 * @see cz.rodro.entity.MilitaryOccupationEntity#structure
	 **/
	public static volatile SingularAttribute<MilitaryOccupationEntity, MilitaryStructureEntity> structure;

	public static final String RANK_CODE = "rankCode";
	public static final String NOTES = "notes";
	public static final String RANK_NAME = "rankName";
	public static final String DESCRIPTION = "description";
	public static final String ID = "id";
	public static final String STRUCTURE = "structure";

}

