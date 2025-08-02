package cz.rodro.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.ListAttribute;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(MilitaryArmyBranchEntity.class)
@Generated("org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
public abstract class MilitaryArmyBranchEntity_ {

	
	/**
	 * @see cz.rodro.entity.MilitaryArmyBranchEntity#ranks
	 **/
	public static volatile ListAttribute<MilitaryArmyBranchEntity, MilitaryRankEntity> ranks;
	
	/**
	 * @see cz.rodro.entity.MilitaryArmyBranchEntity#id
	 **/
	public static volatile SingularAttribute<MilitaryArmyBranchEntity, Long> id;
	
	/**
	 * @see cz.rodro.entity.MilitaryArmyBranchEntity
	 **/
	public static volatile EntityType<MilitaryArmyBranchEntity> class_;
	
	/**
	 * @see cz.rodro.entity.MilitaryArmyBranchEntity#armyBranchName
	 **/
	public static volatile SingularAttribute<MilitaryArmyBranchEntity, String> armyBranchName;

	public static final String RANKS = "ranks";
	public static final String ID = "id";
	public static final String ARMY_BRANCH_NAME = "armyBranchName";

}

