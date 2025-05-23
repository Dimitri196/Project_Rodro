package cz.rodro.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(MilitaryServiceSourceEvidenceEntity.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class MilitaryServiceSourceEvidenceEntity_ {

	
	/**
	 * @see cz.rodro.entity.MilitaryServiceSourceEvidenceEntity#note
	 **/
	public static volatile SingularAttribute<MilitaryServiceSourceEvidenceEntity, String> note;
	
	/**
	 * @see cz.rodro.entity.MilitaryServiceSourceEvidenceEntity#militaryService
	 **/
	public static volatile SingularAttribute<MilitaryServiceSourceEvidenceEntity, PersonMilitaryServiceEntity> militaryService;
	
	/**
	 * @see cz.rodro.entity.MilitaryServiceSourceEvidenceEntity#id
	 **/
	public static volatile SingularAttribute<MilitaryServiceSourceEvidenceEntity, Long> id;
	
	/**
	 * @see cz.rodro.entity.MilitaryServiceSourceEvidenceEntity#source
	 **/
	public static volatile SingularAttribute<MilitaryServiceSourceEvidenceEntity, SourceEntity> source;
	
	/**
	 * @see cz.rodro.entity.MilitaryServiceSourceEvidenceEntity
	 **/
	public static volatile EntityType<MilitaryServiceSourceEvidenceEntity> class_;

	public static final String NOTE = "note";
	public static final String MILITARY_SERVICE = "militaryService";
	public static final String ID = "id";
	public static final String SOURCE = "source";

}

