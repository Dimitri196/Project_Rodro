import React, { useState } from "react";
import { apiDelete } from "../utils/api";
import { 
    Container, Row, Col, Alert, Spinner, Button, Card, 
    Nav, Tab // Importing Tabs components
} from 'react-bootstrap'; 
import { Link } from 'react-router-dom';
import SourceTable from "./SourceTable"; // Assumed the improved SourceTable is used
import { useSession } from "../contexts/session";
import "@fortawesome/fontawesome-free/css/all.min.css"; 

const SourceIndex = () => {
    const { session } = useSession();
    const isAdmin = session?.data?.isAdmin === true;

    // State to force re-fetch in SourceTable after delete or creation
    const [refreshTrigger, setRefreshTrigger] = useState(0); 
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(false); // Only used for the delete operation itself
    const [activeSourceType, setActiveSourceType] = useState('ARCHIVE'); // Default active tab

    const deleteSource = async (id) => {
        if (!window.confirm("CONFIRMATION: Deleting this source record will remove its metadata and links to all associated entities (parishes, locations). Proceed?")) return;
        setLoading(true);
        setError(null);
        try {
            await apiDelete(`/api/sources/${id}`);
            // Success: increment refreshTrigger to make SourceTable re-fetch its data
            setRefreshTrigger(prev => prev + 1);
        } catch (err) {
            console.error("Error deleting source:", err);
            setError(err.message || "Authorization Error: Failed to delete the source record. Access denied.");
        } finally {
            setLoading(false);
        }
    };

    // --- Structural and Methodological Context Data ---
    const sourceStructureDescriptions = [
        {
            title: "Metadata Provenance",
            icon: "fas fa-shield-alt",
            text: "Tracks the ultimate origin and custodial chain of the data, ensuring the validity of information extracted from primary materials."
        },
        {
            title: "Temporal Binding",
            icon: "fas fa-calendar-alt",
            text: "Allows filtering by source date (e.g., publication or creation), crucial for diachronic studies and cross-referencing events."
        },
        {
            title: "Entity Linkage",
            icon: "fas fa-link",
            text: "Explicitly links source records to the specific historical entities (Parishes, Locations) they describe, facilitating data aggregation and citation."
        }
    ];

    // --- Source Type Taxonomy Data (Mimicking Confession Descriptions) ---
    const sourceTypeDescriptions = [
        { 
            key: "ARCHIVE", name: "Archival Document", 
            icon: "fas fa-archive", 
            description: "Unique, non-published primary records (e.g., censuses, financial ledgers, land surveys). These are the fundamental truth carriers.",
            hierarchy: [
                { role: "Fondo (Collection)", authority: "Highest administrative unit (e.g., District Archives)" },
                { role: "Series/Opis", authority: "Grouping of related documents within a Fondo (e.g., Records of Governor)" },
                { role: "Unit (Delo)", authority: "Individual physical container (e.g., Box 53, Volume 10)" },
                { role: "Page/Sheet", authority: "Exact micro-location of the record (Citation Point)" }
            ],
            context: "Citation must follow a strict archival chain: Archive > Fondo > Series > Unit > Page. Data integrity relies on this structure."
        },
        { 
            key: "BOOK", name: "Published Monograph/Text", 
            icon: "fas fa-book-open", 
            description: "Secondary literature (books, atlases, encyclopedias) used for context, aggregated statistics, or known historical facts.",
            hierarchy: [
                { role: "Author/Editor", authority: "Creator of the content" },
                { role: "Title", authority: "The primary source identifier (Reference)" },
                { role: "Chapter/Page", authority: "Specific location of the cited information" },
                { role: "Publisher/Year", authority: "Bibliographic details for reproducibility" }
            ],
            context: "Provides narrative context but must be cross-validated against primary archival sources, especially for controversial claims."
        },
        { 
            key: "ARTICLE", name: "Scholarly Article/Journal", 
            icon: "fas fa-newspaper", 
            description: "Peer-reviewed research providing specific interpretations or methodological insight. Often links directly to primary sources.",
            hierarchy: [
                { role: "Author", authority: "Creator of the content" },
                { role: "Journal/Volume/Issue", authority: "Container of the publication" },
                { role: "Article Title", authority: "The primary source identifier" },
                { role: "DOI/URL", authority: "Digital locator for immediate access" }
            ],
            context: "Essential for linking empirical data to current academic debate and methodological practice."
        },
        { 
            key: "MAP", name: "Cartographic Material", 
            icon: "fas fa-map", 
            description: "Geospatial data sources, including historical cadastral maps, military maps, or topological surveys. Crucial for location validation.",
            hierarchy: [
                { role: "Survey Agency/Cartographer", authority: "Creator of the map" },
                { role: "Map Sheet/Scale", authority: "Specific administrative division of the map" },
                { role: "Year of Creation", authority: "Temporal marker for the data depicted" },
                { role: "Repository", authority: "Physical/Digital location of the map" }
            ],
            context: "Used to determine historical place names, administrative boundaries, and the location of physical structures (churches, synagogues)."
        },
    ];

    // --- Main Render ---
    return (
        <Container className="my-5 py-3">
            {/* The Font Awesome link is generally best placed in the main HTML/index.html, but kept here for completeness */}
            {/* <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" integrity="..." crossOrigin="anonymous" referrerPolicy="no-referrer" /> */}

            <header className="text-center mb-5">
                <h1 className="display-5 fw-bold text-dark mb-1">
                    <i className="fas fa-book-reader me-3 text-success"></i>Scholarly Source Ontology
                </h1>
                <p className="lead text-secondary fst-italic mb-4 mx-auto" style={{maxWidth: '800px'}}>
                    This index provides a **citation backbone** for the entire historical database, structuring the **provenance, methodology, and reliability** of all included entities and facts.
                </p>
            </header>
            
            {/* ------------------------------------------------------------------ */}
            {/* I. Structural and Methodological Context */}
            {/* ------------------------------------------------------------------ */}
            <Row className="mb-5 justify-content-center">
                <Col md={10}>
                    <h2 className="fw-bold text-dark mb-3">I. The Source Data Modeling Principles</h2>
                    <Row className="g-4 mb-4">
                        {sourceStructureDescriptions.map((item, index) => (
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

                    {/* ------------------------------------------------------------------ */}
                    {/* II. Source Type Taxonomy & Citation Hierarchies */}
                    {/* ------------------------------------------------------------------ */}
                    <h2 className="fw-bold text-dark mt-4 mb-3">II. Source Type Taxonomy & Citation Hierarchies</h2>
                    
                    <Tab.Container 
                        id="source-taxonomy-tabs" 
                        activeKey={activeSourceType} 
                        onSelect={(k) => setActiveSourceType(k)}
                    >
                        <Row>
                            {/* Source Type Nav/Pills */}
                            <Col md={3} className="mb-3">
                                <Nav variant="pills" className="flex-column p-3 bg-light rounded-3 shadow-sm border">
                                    {sourceTypeDescriptions.map((item) => (
                                        <Nav.Item key={item.key}>
                                            <Nav.Link eventKey={item.key} className="text-start mb-1 fw-semibold">
                                                <i className={`${item.icon} me-2`}></i> {item.name}
                                            </Nav.Link>
                                        </Nav.Item>
                                    ))}
                                </Nav>
                            </Col>

                            {/* Hierarchy Content */}
                            <Col md={9}>
                                <Tab.Content className="p-4 bg-white rounded-3 shadow border">
                                    {sourceTypeDescriptions.map((item) => (
                                        <Tab.Pane key={item.key} eventKey={item.key}>
                                            <h4 className="fw-bold text-dark border-bottom pb-2 mb-3">{item.name} Citation Structure</h4>
                                            
                                            <p className="text-muted mb-4">{item.description}</p>
                                            
                                            <h5 className="fw-bold text-success mb-3">Required Citation Chain of Authority:</h5>
                                            
                                            {/* Hierarchy List */}
                                            <Row className="g-3">
                                                {item.hierarchy.map((level, i) => (
                                                    <Col md={6} key={i}>
                                                        <Card className="h-100 border-0 border-start border-4 border-secondary-subtle">
                                                            <Card.Body className="p-3">
                                                                <h6 className="mb-1 text-dark">
                                                                    <i className="fas fa-sitemap me-2 text-secondary"></i> 
                                                                    <strong>{level.role}</strong>
                                                                </h6>
                                                                <p className="small text-muted mb-0">{level.authority}</p>
                                                            </Card.Body>
                                                        </Card>
                                                    </Col>
                                                ))}
                                            </Row>
                                            
                                            <p className="small fst-italic text-muted mt-4 mb-0 border-top pt-3">
                                                <i className="fas fa-info-circle me-1 text-success"></i> Citation Standard Note: {item.context}
                                            </p>
                                        </Tab.Pane>
                                    ))}
                                </Tab.Content>
                            </Col>
                        </Row>
                    </Tab.Container>

                </Col>
            </Row>

            <hr className="mb-5"/>
            
            {/* Delete in Progress / Global Errors */}
            {loading && ( 
                <div className="text-center my-3">
                    <Spinner animation="border" role="status" variant="danger"/>
                    <p className="mt-2 text-muted">Executing deletion command...</p>
                </div>
            )}

            {error && (
                <Alert variant="danger" className="rounded-3 shadow-sm mb-4">
                    <i className="fas fa-exclamation-triangle me-2"></i>{error}
                </Alert>
            )}

            {/* SourceTable - The main data view */}
            <SourceTable
                label="Total Sources Indexed"
                deleteSource={deleteSource}
                refreshTrigger={refreshTrigger} // Triggers re-fetch after a delete operation
            />
        </Container>
    );
};

export default SourceIndex;
