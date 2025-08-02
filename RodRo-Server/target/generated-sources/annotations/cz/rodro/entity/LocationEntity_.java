package cz.rodro.entity;

import cz.rodro.constant.SettlementType;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(LocationEntity.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class LocationEntity_ {

	
	/**
	 * @see cz.rodro.entity.LocationEntity#settlementType
	 **/
	public static volatile SingularAttribute<LocationEntity, SettlementType> settlementType;
	
	/**
	 * @see cz.rodro.entity.LocationEntity#gpsLongitude
	 **/
	public static volatile SingularAttribute<LocationEntity, Double> gpsLongitude;
	
	/**
	 * @see cz.rodro.entity.LocationEntity#cemeteries
	 **/
	public static volatile ListAttribute<LocationEntity, CemeteryEntity> cemeteries;
	
	/**
	 * @see cz.rodro.entity.LocationEntity#locationName
	 **/
	public static volatile SingularAttribute<LocationEntity, String> locationName;
	
	/**
	 * @see cz.rodro.entity.LocationEntity#sources
	 **/
	public static volatile ListAttribute<LocationEntity, SourceEntity> sources;
	
	/**
	 * @see cz.rodro.entity.LocationEntity#parishLocations
	 **/
	public static volatile ListAttribute<LocationEntity, ParishLocationEntity> parishLocations;
	
	/**
	 * @see cz.rodro.entity.LocationEntity#births
	 **/
	public static volatile ListAttribute<LocationEntity, PersonEntity> births;
	
	/**
	 * @see cz.rodro.entity.LocationEntity#institutions
	 **/
	public static volatile ListAttribute<LocationEntity, InstitutionEntity> institutions;
	
	/**
	 * @see cz.rodro.entity.LocationEntity#locationHistories
	 **/
	public static volatile ListAttribute<LocationEntity, LocationHistoryEntity> locationHistories;
	
	/**
	 * @see cz.rodro.entity.LocationEntity#id
	 **/
	public static volatile SingularAttribute<LocationEntity, Long> id;
	
	/**
	 * @see cz.rodro.entity.LocationEntity
	 **/
	public static volatile EntityType<LocationEntity> class_;
	
	/**
	 * @see cz.rodro.entity.LocationEntity#establishmentYear
	 **/
	public static volatile SingularAttribute<LocationEntity, Integer> establishmentYear;
	
	/**
	 * @see cz.rodro.entity.LocationEntity#deaths
	 **/
	public static volatile ListAttribute<LocationEntity, PersonEntity> deaths;
	
	/**
	 * @see cz.rodro.entity.LocationEntity#gpsLatitude
	 **/
	public static volatile SingularAttribute<LocationEntity, Double> gpsLatitude;

	public static final String SETTLEMENT_TYPE = "settlementType";
	public static final String GPS_LONGITUDE = "gpsLongitude";
	public static final String CEMETERIES = "cemeteries";
	public static final String LOCATION_NAME = "locationName";
	public static final String SOURCES = "sources";
	public static final String PARISH_LOCATIONS = "parishLocations";
	public static final String BIRTHS = "births";
	public static final String INSTITUTIONS = "institutions";
	public static final String LOCATION_HISTORIES = "locationHistories";
	public static final String ID = "id";
	public static final String ESTABLISHMENT_YEAR = "establishmentYear";
	public static final String DEATHS = "deaths";
	public static final String GPS_LATITUDE = "gpsLatitude";

}

