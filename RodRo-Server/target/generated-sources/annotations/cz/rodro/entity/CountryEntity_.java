package cz.rodro.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(CountryEntity.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class CountryEntity_ {

	
	/**
	 * @see cz.rodro.entity.CountryEntity#countryNameInPolish
	 **/
	public static volatile SingularAttribute<CountryEntity, String> countryNameInPolish;
	
	/**
	 * @see cz.rodro.entity.CountryEntity#provinces
	 **/
	public static volatile ListAttribute<CountryEntity, ProvinceEntity> provinces;
	
	/**
	 * @see cz.rodro.entity.CountryEntity#countryEstablishmentYear
	 **/
	public static volatile SingularAttribute<CountryEntity, String> countryEstablishmentYear;
	
	/**
	 * @see cz.rodro.entity.CountryEntity#militaryOrganizations
	 **/
	public static volatile ListAttribute<CountryEntity, MilitaryOrganizationEntity> militaryOrganizations;
	
	/**
	 * @see cz.rodro.entity.CountryEntity#countryNameInEnglish
	 **/
	public static volatile SingularAttribute<CountryEntity, String> countryNameInEnglish;
	
	/**
	 * @see cz.rodro.entity.CountryEntity#id
	 **/
	public static volatile SingularAttribute<CountryEntity, Long> id;
	
	/**
	 * @see cz.rodro.entity.CountryEntity#countryCancellationYear
	 **/
	public static volatile SingularAttribute<CountryEntity, String> countryCancellationYear;
	
	/**
	 * @see cz.rodro.entity.CountryEntity
	 **/
	public static volatile EntityType<CountryEntity> class_;
	
	/**
	 * @see cz.rodro.entity.CountryEntity#countryFlagImgUrl
	 **/
	public static volatile SingularAttribute<CountryEntity, String> countryFlagImgUrl;

	public static final String COUNTRY_NAME_IN_POLISH = "countryNameInPolish";
	public static final String PROVINCES = "provinces";
	public static final String COUNTRY_ESTABLISHMENT_YEAR = "countryEstablishmentYear";
	public static final String MILITARY_ORGANIZATIONS = "militaryOrganizations";
	public static final String COUNTRY_NAME_IN_ENGLISH = "countryNameInEnglish";
	public static final String ID = "id";
	public static final String COUNTRY_CANCELLATION_YEAR = "countryCancellationYear";
	public static final String COUNTRY_FLAG_IMG_URL = "countryFlagImgUrl";

}

