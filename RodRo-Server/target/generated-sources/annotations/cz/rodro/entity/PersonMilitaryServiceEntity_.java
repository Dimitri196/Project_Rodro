package cz.rodro.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(PersonMilitaryServiceEntity.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class PersonMilitaryServiceEntity_ {

	
	/**
	 * @see cz.rodro.entity.PersonMilitaryServiceEntity#enlistmentYear
	 **/
	public static volatile SingularAttribute<PersonMilitaryServiceEntity, Integer> enlistmentYear;
	
	/**
	 * @see cz.rodro.entity.PersonMilitaryServiceEntity#militaryRank
	 **/
	public static volatile SingularAttribute<PersonMilitaryServiceEntity, MilitaryRankEntity> militaryRank;
	
	/**
	 * @see cz.rodro.entity.PersonMilitaryServiceEntity#notes
	 **/
	public static volatile SingularAttribute<PersonMilitaryServiceEntity, String> notes;
	
	/**
	 * @see cz.rodro.entity.PersonMilitaryServiceEntity#person
	 **/
	public static volatile SingularAttribute<PersonMilitaryServiceEntity, PersonEntity> person;
	
	/**
	 * @see cz.rodro.entity.PersonMilitaryServiceEntity#militaryStructure
	 **/
	public static volatile SingularAttribute<PersonMilitaryServiceEntity, MilitaryStructureEntity> militaryStructure;
	
	/**
	 * @see cz.rodro.entity.PersonMilitaryServiceEntity#id
	 **/
	public static volatile SingularAttribute<PersonMilitaryServiceEntity, Long> id;
	
	/**
	 * @see cz.rodro.entity.PersonMilitaryServiceEntity
	 **/
	public static volatile EntityType<PersonMilitaryServiceEntity> class_;
	
	/**
	 * @see cz.rodro.entity.PersonMilitaryServiceEntity#dischargeYear
	 **/
	public static volatile SingularAttribute<PersonMilitaryServiceEntity, Integer> dischargeYear;

	public static final String ENLISTMENT_YEAR = "enlistmentYear";
	public static final String MILITARY_RANK = "militaryRank";
	public static final String NOTES = "notes";
	public static final String PERSON = "person";
	public static final String MILITARY_STRUCTURE = "militaryStructure";
	public static final String ID = "id";
	public static final String DISCHARGE_YEAR = "dischargeYear";

}

