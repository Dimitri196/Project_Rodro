package cz.rodro.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(DistrictEntity.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class DistrictEntity_ {

	
	/**
	 * @see cz.rodro.entity.DistrictEntity#districtName
	 **/
	public static volatile SingularAttribute<DistrictEntity, String> districtName;
	
	/**
	 * @see cz.rodro.entity.DistrictEntity#province
	 **/
	public static volatile SingularAttribute<DistrictEntity, ProvinceEntity> province;
	
	/**
	 * @see cz.rodro.entity.DistrictEntity#id
	 **/
	public static volatile SingularAttribute<DistrictEntity, Long> id;
	
	/**
	 * @see cz.rodro.entity.DistrictEntity
	 **/
	public static volatile EntityType<DistrictEntity> class_;
	
	/**
	 * @see cz.rodro.entity.DistrictEntity#subdivisions
	 **/
	public static volatile ListAttribute<DistrictEntity, SubdivisionEntity> subdivisions;

	public static final String DISTRICT_NAME = "districtName";
	public static final String PROVINCE = "province";
	public static final String ID = "id";
	public static final String SUBDIVISIONS = "subdivisions";

}

