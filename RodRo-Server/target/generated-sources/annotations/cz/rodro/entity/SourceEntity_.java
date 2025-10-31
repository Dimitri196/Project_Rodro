package cz.rodro.entity;

import cz.rodro.constant.SourceType;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.util.Map;

@StaticMetamodel(SourceEntity.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class SourceEntity_ {

	
	/**
	 * @see cz.rodro.entity.SourceEntity#metadata
	 **/
	public static volatile SingularAttribute<SourceEntity, Map<String,Object>> metadata;
	
	/**
	 * @see cz.rodro.entity.SourceEntity#startYear
	 **/
	public static volatile SingularAttribute<SourceEntity, Integer> startYear;
	
	/**
	 * @see cz.rodro.entity.SourceEntity#description
	 **/
	public static volatile SingularAttribute<SourceEntity, String> description;
	
	/**
	 * @see cz.rodro.entity.SourceEntity#title
	 **/
	public static volatile SingularAttribute<SourceEntity, String> title;
	
	/**
	 * @see cz.rodro.entity.SourceEntity#type
	 **/
	public static volatile SingularAttribute<SourceEntity, SourceType> type;
	
	/**
	 * @see cz.rodro.entity.SourceEntity#endYear
	 **/
	public static volatile SingularAttribute<SourceEntity, Integer> endYear;
	
	/**
	 * @see cz.rodro.entity.SourceEntity#url
	 **/
	public static volatile SingularAttribute<SourceEntity, String> url;
	
	/**
	 * @see cz.rodro.entity.SourceEntity#reference
	 **/
	public static volatile SingularAttribute<SourceEntity, String> reference;
	
	/**
	 * @see cz.rodro.entity.SourceEntity#citationString
	 **/
	public static volatile SingularAttribute<SourceEntity, String> citationString;
	
	/**
	 * @see cz.rodro.entity.SourceEntity#creationYear
	 **/
	public static volatile SingularAttribute<SourceEntity, Integer> creationYear;
	
	/**
	 * @see cz.rodro.entity.SourceEntity#location
	 **/
	public static volatile SingularAttribute<SourceEntity, LocationEntity> location;
	
	/**
	 * @see cz.rodro.entity.SourceEntity#id
	 **/
	public static volatile SingularAttribute<SourceEntity, Long> id;
	
	/**
	 * @see cz.rodro.entity.SourceEntity#evidences
	 **/
	public static volatile ListAttribute<SourceEntity, PersonSourceEvidenceEntity> evidences;
	
	/**
	 * @see cz.rodro.entity.SourceEntity
	 **/
	public static volatile EntityType<SourceEntity> class_;

	public static final String METADATA = "metadata";
	public static final String START_YEAR = "startYear";
	public static final String DESCRIPTION = "description";
	public static final String TITLE = "title";
	public static final String TYPE = "type";
	public static final String END_YEAR = "endYear";
	public static final String URL = "url";
	public static final String REFERENCE = "reference";
	public static final String CITATION_STRING = "citationString";
	public static final String CREATION_YEAR = "creationYear";
	public static final String LOCATION = "location";
	public static final String ID = "id";
	public static final String EVIDENCES = "evidences";

}

