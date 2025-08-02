package cz.rodro.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(ProvinceEntity.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class ProvinceEntity_ {

	
	/**
	 * @see cz.rodro.entity.ProvinceEntity#country
	 **/
	public static volatile SingularAttribute<ProvinceEntity, CountryEntity> country;
	
	/**
	 * @see cz.rodro.entity.ProvinceEntity#districts
	 **/
	public static volatile ListAttribute<ProvinceEntity, DistrictEntity> districts;
	
	/**
	 * @see cz.rodro.entity.ProvinceEntity#id
	 **/
	public static volatile SingularAttribute<ProvinceEntity, Long> id;
	
	/**
	 * @see cz.rodro.entity.ProvinceEntity#provinceName
	 **/
	public static volatile SingularAttribute<ProvinceEntity, String> provinceName;
	
	/**
	 * @see cz.rodro.entity.ProvinceEntity
	 **/
	public static volatile EntityType<ProvinceEntity> class_;

	public static final String COUNTRY = "country";
	public static final String DISTRICTS = "districts";
	public static final String ID = "id";
	public static final String PROVINCE_NAME = "provinceName";

}

