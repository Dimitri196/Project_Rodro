package cz.rodro.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.LocalDate;

@StaticMetamodel(ParishEntity.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class ParishEntity_ {

	
	/**
	 * @see cz.rodro.entity.ParishEntity#parishMainChurchName
	 **/
	public static volatile SingularAttribute<ParishEntity, String> parishMainChurchName;
	
	/**
	 * @see cz.rodro.entity.ParishEntity#establishmentDate
	 **/
	public static volatile SingularAttribute<ParishEntity, LocalDate> establishmentDate;
	
	/**
	 * @see cz.rodro.entity.ParishEntity#parishName
	 **/
	public static volatile SingularAttribute<ParishEntity, String> parishName;
	
	/**
	 * @see cz.rodro.entity.ParishEntity#parishLocations
	 **/
	public static volatile ListAttribute<ParishEntity, ParishLocationEntity> parishLocations;
	
	/**
	 * @see cz.rodro.entity.ParishEntity#parishLocation
	 **/
	public static volatile SingularAttribute<ParishEntity, LocationEntity> parishLocation;
	
	/**
	 * @see cz.rodro.entity.ParishEntity#id
	 **/
	public static volatile SingularAttribute<ParishEntity, Long> id;
	
	/**
	 * @see cz.rodro.entity.ParishEntity
	 **/
	public static volatile EntityType<ParishEntity> class_;
	
	/**
	 * @see cz.rodro.entity.ParishEntity#cancellationDate
	 **/
	public static volatile SingularAttribute<ParishEntity, LocalDate> cancellationDate;

	public static final String PARISH_MAIN_CHURCH_NAME = "parishMainChurchName";
	public static final String ESTABLISHMENT_DATE = "establishmentDate";
	public static final String PARISH_NAME = "parishName";
	public static final String PARISH_LOCATIONS = "parishLocations";
	public static final String PARISH_LOCATION = "parishLocation";
	public static final String ID = "id";
	public static final String CANCELLATION_DATE = "cancellationDate";

}

