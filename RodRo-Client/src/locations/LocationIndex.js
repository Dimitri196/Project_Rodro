import React, { useState } from "react";
import { 
    Container, Row, Col, Card, Nav 
} from 'react-bootstrap'; 
import { Link } from 'react-router-dom';
import LocationTable from "./LocationTable"; 
import { useSession } from "../contexts/session";
import "@fortawesome/fontawesome-free/css/all.min.css"; 

// --- Settlement Label Map (Used to get names from keys) ---
const settlementLabelMap = {
    CITY: "City",
    TOWN: "Town",
    VILLAGE: "Village",
    FARM: "Farm",
    FORESTERS_LODGE: "Forester's Lodge",
    KHUTOR: "Khutor",
    SETTLEMENT: "Settlement",
    COLONY: "Colony",
    RAILWAY_STATION: "Railway Station",
    BRICKYARD: "Brickyard",
    MILL_SETTLEMENT: "Mill Settlement",
    ZASCIANEK: "Zaścianek",
    FOREST_SETTLEMENT: "Forest Settlement",
    FACTORY_SETTLEMENT: "Factory Settlement",
    SAWMILL: "Sawmill",
    SUBURB: "Suburb",
    TAR_FACTORY: "Tar Factory",
};

// --- Settlement Color Map (ENSURING VISUAL CONSISTENCY) ---
// This map is copied directly from your LocationTable for consistent badge colors.
const colorMap = {
    'CITY': 'primary',
    'TOWN': 'info',
    'VILLAGE': 'success',
    'FARM': 'secondary',
    'FORESTERS_LODGE': 'dark',
    'KHUTOR': 'secondary',
    'SETTLEMENT': 'info',
    'COLONY': 'warning',
    'RAILWAY_STATION': 'primary',
    'BRICKYARD': 'danger',
    'MILL_SETTLEMENT': 'success',
    'ZASCIANEK': 'secondary',
    'FOREST_SETTLEMENT': 'dark',
    'FACTORY_SETTLEMENT': 'danger',
    'SAWMILL': 'warning',
    'SUBURB': 'info',
    'TAR_FACTORY': 'danger'
};


// --- Settlement Taxonomy Definitions (Organized into Groups) ---
const settlementTaxonomyGroups = [
    { 
        key: "URBAN_MAJOR", 
        name: "Major Urban Centers", 
        icon: "fas fa-city", 
        categories: ["CITY", "TOWN"],
        description: "The primary administrative and economic cores. These settlements hold historical charters, centralized legal structures, and are vital for tracing high-level political and commercial records.",
        importance: "Highest administrative and commercial weight. Key centers for census, court records, and guild documents.",
    },
    { 
        key: "RURAL_DENSE", 
        name: "Dense Rural/Suburban", 
        icon: "fas fa-home", 
        categories: ["VILLAGE", "SETTLEMENT", "SUBURB"],
        description: "The most common form of inhabited area, representing the agricultural backbone of the region. They vary in size from small hamlets to large, well-established communities.",
        importance: "Fundamental demographic units. Primary locations for parish registers, land tenure documents, and agricultural labor records.",
    },
    { 
        key: "RURAL_SCATTERED", 
        name: "Scattered/Isolated Sites", 
        icon: "fas fa-tractor", 
        categories: ["FARM", "KHUTOR", "ZASCIANEK"],
        description: "Isolated or smaller, often newly established agricultural units, typically centered around a single dwelling or a small cluster of families. They chart the expansion of cultivated land.",
        importance: "Essential for tracking land reclamation, internal migration patterns, and the genealogy of tenant or colonizing families. Often required cross-referencing with adjacent larger parishes.",
    },
    { 
        key: "SPECIALIZED", 
        name: "Specialized/Industrial", 
        icon: "fas fa-industry", 
        categories: ["FORESTERS_LODGE", "COLONY", "RAILWAY_STATION", "BRICKYARD", "MILL_SETTLEMENT", "FOREST_SETTLEMENT", "FACTORY_SETTLEMENT", "SAWMILL", "TAR_FACTORY"],
        description: "Locations tied directly to resource extraction, processing, or infrastructure (e.g., transport, forestry, mining). These sites track occupational and industrial history.",
        importance: "Provides micro-level data on specific trades and occupations. Records are valuable for economic history and tracking specialized labor groups like millers, foresters, and factory workers.",
    },
];


const LocationIndex = () => {
    const { session } = useSession();
    const isAdmin = session.data?.isAdmin === true;
    
    // State now tracks the active GROUP, not a single type.
    const [activeGroupKey, setActiveGroupKey] = useState(settlementTaxonomyGroups[0].key); 
    const activeGroupData = settlementTaxonomyGroups.find(g => g.key === activeGroupKey);

    // --- Structural and Methodological Context Cards ---
    const locationStructureDescriptions = [
        {
            title: "Geospatial Referencing",
            icon: "fas fa-globe-europe",
            text: "Each location record provides precise coordinates, enabling high-resolution mapping for historical boundary analysis and migration tracking.",
        },
        {
            title: "Administrative Backbone",
            icon: "fas fa-gavel",
            text: "Settlements form the legal and civil basis for all records (taxation, military conscription, land registry), linking data to central authority.",
        },
        {
            title: "Temporal Permanence",
            icon: "fas fa-calendar-alt",
            text: "Tracking the establishment or cessation year allows researchers to filter records by the lifespan of the settlement.",
        }
    ];

    // --- Placeholder function for deleteLocation (required by LocationTable prop) ---
    const deleteLocation = (id) => {
        console.log(`Attempting to delete location with ID: ${id}`);
        // Implement API call for deletion here (e.g., apiDelete(`/api/locations/${id}`)).
        alert(`Location ID ${id} marked for deletion (functional placeholder).`);
    };

    return (
        <Container className="my-5 py-3">
            <header className="text-center mb-5">
                <h1 className="display-5 fw-bold text-dark mb-1">
                    <i className="fas fa-map-marker-alt me-3 text-success"></i>Geospatial Settlement Ontology
                </h1>
                <p className="lead text-secondary fst-italic mb-4 mx-auto" style={{maxWidth: '800px'}}>
                    This index catalogs historical geographical locations, providing the **spatial anchor** necessary to contextualize all demographic, ecclesiastical, and civil records.
                </p>
             </header>
            
            <hr className="mb-5"/>

            {/* Structural and Methodological Context */}
            <Row className="mb-5 justify-content-center">
                <Col md={12}>
                    <h2 className="fw-bold text-dark mb-3">I. Location Data Structural Framework</h2>
                    <Row className="g-4 mb-4">
                        {locationStructureDescriptions.map((item, index) => (
                            <Col md={4} key={index}>
                                <Card className="h-100 shadow-sm border-start border-success border-4 rounded-3">
                                    <Card.Body className="p-3">
                                        <h6 className="fw-bold text-success mb-1">
                                            <i className={`${item.icon} me-2`}></i> {item.title}
                                        </h6>
                                        <p className="text-muted small mb-0">{item.text}</p>
                                    </Card.Body>
                                </Card>
                            </Col>
                        ))}
                    </Row>
                    
                    <h2 className="fw-bold text-dark mt-4 mb-3">II. Settlement Taxonomy & Administrative Weight</h2>
                    
                    {/* --- TABS IMPLEMENTATION START --- */}
                    <div id="settlement-taxonomy-tabs">
                        <Row>
                            {/* Settlement Type Nav/Pills (Groups) */}
                            <Col md={3} className="mb-3">
                                <Nav variant="pills" className="flex-column p-3 bg-light rounded-3 shadow-sm border" onSelect={(k) => setActiveGroupKey(k)}>
                                    {settlementTaxonomyGroups.map((group) => (
                                        <Nav.Item key={group.key}>
                                            <Nav.Link 
                                                eventKey={group.key} 
                                                className={`text-start mb-1 fw-semibold ${activeGroupKey === group.key ? 'bg-success text-white' : 'text-dark'}`}
                                            >
                                                <i className={`${group.icon} me-2`}></i> {group.name}
                                            </Nav.Link>
                                        </Nav.Item>
                                    ))}
                                </Nav>
                            </Col>

                            {/* Importance Content for Active Group */}
                            <Col md={9}>
                                <Card className="h-100 p-4 bg-white rounded-3 shadow border">
                                    <h4 className="fw-bold text-dark border-bottom pb-2 mb-3">
                                        {activeGroupData?.name || 'Select a Group'} - Administrative Role
                                    </h4>
                                    
                                    <p className="text-muted mb-4">
                                        {activeGroupData?.description || 'Select a settlement group to view its historical and administrative context.'}
                                    </p>
                                    
                                    <h5 className="fw-bold text-primary mb-3">Key Settlement Types in this Group:</h5>
                                    <div className="d-flex flex-wrap gap-2 mb-4">
                                        {/* RENDER BADGES WITH CONSISTENT COLORS */}
                                        {activeGroupData?.categories.map(cat => (
                                            <span 
                                                key={cat} 
                                                // Use the explicit color map to set the background
                                                className={`badge bg-${colorMap[cat] || 'secondary'} py-2 px-3 fw-normal`}
                                            >
                                                {settlementLabelMap[cat]}
                                            </span>
                                        ))}
                                    </div>
                                    
                                    <h5 className="fw-bold text-primary mb-3">Key Archival & Administrative Importance:</h5>
                                    
                                    <Card className="h-100 border-0 border-start border-4 border-success-subtle bg-light">
                                        <Card.Body className="p-3">
                                            <h6 className="mb-1 text-dark">
                                                <i className="fas fa-file-alt me-2 text-secondary"></i> 
                                                <strong>{activeGroupData?.importance}</strong>
                                            </h6>
                                        </Card.Body>
                                    </Card>

                                    <p className="small fst-italic text-muted mt-4 mb-0 border-top pt-3">
                                        <i className="fas fa-info-circle me-1 text-primary"></i> Note: The detailed administrative significance varies based on the specific type and historical jurisdiction.
                                    </p>
                                </Card>
                            </Col>
                        </Row>
                    </div>
                    {/* --- TABS IMPLEMENTATION END --- */}
                </Col>
            </Row>

            <hr className="mb-5"/>

            {/* LocationTable Component (Self-contained for data fetching) */}
            <LocationTable
                label="Total Locations Indexed:"
                deleteLocation={deleteLocation} // Pass the necessary prop
            />
        </Container>
    );
};

export default LocationIndex;
