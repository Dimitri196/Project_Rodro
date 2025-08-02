package cz.rodro.dto;

/**
 * Projection interface for a lightweight view of LocationEntity.
 * Useful for lists where only basic information is needed,
 * avoiding the N+1 query problem for associated collections.
 */
public interface LocationListProjection {
    Long getId();
    String getLocationName();
    Integer getEstablishmentYear();
    Double getGpsLatitude();
    Double getGpsLongitude();
    String getSettlementType(); // Assuming SettlementType can be directly mapped to String
}
