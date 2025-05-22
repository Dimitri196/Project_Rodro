package cz.rodro.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(SubdivisionEntity.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class SubdivisionEntity_ {

	
	/**
	 * @see cz.rodro.entity.SubdivisionEntity#subdivisionCancellationYear
	 **/
	public static volatile SingularAttribute<SubdivisionEntity, String> subdivisionCancellationYear;
	
	/**
	 * @see cz.rodro.entity.SubdivisionEntity#subdivisionEstablishmentYear
	 **/
	public static volatile SingularAttribute<SubdivisionEntity, String> subdivisionEstablishmentYear;
	
	/**
	 * @see cz.rodro.entity.SubdivisionEntity#district
	 **/
	public static volatile SingularAttribute<SubdivisionEntity, DistrictEntity> district;
	
	/**
	 * @see cz.rodro.entity.SubdivisionEntity#administrativeCenter
	 **/
	public static volatile SingularAttribute<SubdivisionEntity, LocationEntity> administrativeCenter;
	
	/**
	 * @see cz.rodro.entity.SubdivisionEntity#id
	 **/
	public static volatile SingularAttribute<SubdivisionEntity, Long> id;
	
	/**
	 * @see cz.rodro.entity.SubdivisionEntity#historyEntries
	 **/
	public static volatile ListAttribute<SubdivisionEntity, LocationHistoryEntity> historyEntries;
	
	/**
	 * @see cz.rodro.entity.SubdivisionEntity
	 **/
	public static volatile EntityType<SubdivisionEntity> class_;
	
	/**
	 * @see cz.rodro.entity.SubdivisionEntity#subdivisionName
	 **/
	public static volatile SingularAttribute<SubdivisionEntity, String> subdivisionName;

	public static final String SUBDIVISION_CANCELLATION_YEAR = "subdivisionCancellationYear";
	public static final String SUBDIVISION_ESTABLISHMENT_YEAR = "subdivisionEstablishmentYear";
	public static final String DISTRICT = "district";
	public static final String ADMINISTRATIVE_CENTER = "administrativeCenter";
	public static final String ID = "id";
	public static final String HISTORY_ENTRIES = "historyEntries";
	public static final String SUBDIVISION_NAME = "subdivisionName";

}

