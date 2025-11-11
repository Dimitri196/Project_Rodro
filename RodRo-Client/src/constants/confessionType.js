/**
 * CONFESSION_TYPE_MAP
 * Maps backend ConfessionType enum keys (uppercase_with_underscores) to 
 * user-friendly display names for the frontend.
 */
export const CONFESSION_TYPE_MAP = { 
    // Key (from API) : Value (for display)
    CATHOLIC_LATIN: 'Roman Catholic (Latin)',       
    CATHOLIC_UNIATE: 'Greek Catholic / Uniate',     
    ORTHODOX: 'Eastern Orthodox', // <--- This key is required to match the API's enum name
    PROTESTANT_LUTHERAN: 'Lutheran / Evangelical',  
    PROTESTANT_REFORMED: 'Calvinist / Reformed',    
    ANGLICAN: 'Anglican',                           
    JEWISH: 'Jewish',                               
    ISLAMIC: 'Islamic (Muslim)',                    
    OTHER: 'Other / Unknown'                        
};