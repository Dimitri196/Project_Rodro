package cz.rodro.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDate;

@StaticMetamodel(LocationHistoryEntity.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class LocationHistoryEntity_ {

	
	/**
	 * @see cz.rodro.entity.LocationHistoryEntity#country
	 **/
	public static volatile SingularAttribute<LocationHistoryEntity, CountryEntity> country;
	
	/**
	 * @see cz.rodro.entity.LocationHistoryEntity#notes
	 **/
	public static volatile SingularAttribute<LocationHistoryEntity, String> notes;
	
	/**
	 * @see cz.rodro.entity.LocationHistoryEntity#districtName
	 **/
	public static volatile SingularAttribute<LocationHistoryEntity, String> districtName;
	
	/**
	 * @see cz.rodro.entity.LocationHistoryEntity#endDate
	 **/
	public static volatile SingularAttribute<LocationHistoryEntity, LocalDate> endDate;
	
	/**
	 * @see cz.rodro.entity.LocationHistoryEntity#subdivision
	 **/
	public static volatile SingularAttribute<LocationHistoryEntity, SubdivisionEntity> subdivision;
	
	/**
	 * @see cz.rodro.entity.LocationHistoryEntity#province
	 **/
	public static volatile SingularAttribute<LocationHistoryEntity, ProvinceEntity> province;
	
	/**
	 * @see cz.rodro.entity.LocationHistoryEntity#district
	 **/
	public static volatile SingularAttribute<LocationHistoryEntity, DistrictEntity> district;
	
	/**
	 * @see cz.rodro.entity.LocationHistoryEntity#location
	 **/
	public static volatile SingularAttribute<LocationHistoryEntity, LocationEntity> location;
	
	/**
	 * @see cz.rodro.entity.LocationHistoryEntity#id
	 **/
	public static volatile SingularAttribute<LocationHistoryEntity, Long> id;
	
	/**
	 * @see cz.rodro.entity.LocationHistoryEntity#countryName
	 **/
	public static volatile SingularAttribute<LocationHistoryEntity, String> countryName;
	
	/**
	 * @see cz.rodro.entity.LocationHistoryEntity#provinceName
	 **/
	public static volatile SingularAttribute<LocationHistoryEntity, String> provinceName;
	
	/**
	 * @see cz.rodro.entity.LocationHistoryEntity
	 **/
	public static volatile EntityType<LocationHistoryEntity> class_;
	
	/**
	 * @see cz.rodro.entity.LocationHistoryEntity#subdivisionName
	 **/
	public static volatile SingularAttribute<LocationHistoryEntity, String> subdivisionName;
	
	/**
	 * @see cz.rodro.entity.LocationHistoryEntity#startDate
	 **/
	public static volatile SingularAttribute<LocationHistoryEntity, LocalDate> startDate;

	public static final String COUNTRY = "country";
	public static final String NOTES = "notes";
	public static final String DISTRICT_NAME = "districtName";
	public static final String END_DATE = "endDate";
	public static final String SUBDIVISION = "subdivision";
	public static final String PROVINCE = "province";
	public static final String DISTRICT = "district";
	public static final String LOCATION = "location";
	public static final String ID = "id";
	public static final String COUNTRY_NAME = "countryName";
	public static final String PROVINCE_NAME = "provinceName";
	public static final String SUBDIVISION_NAME = "subdivisionName";
	public static final String START_DATE = "startDate";

}

