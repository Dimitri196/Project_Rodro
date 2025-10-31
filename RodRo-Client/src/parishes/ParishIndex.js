import React, { useEffect, useState } from "react";
import { apiDelete, apiGet } from "../utils/api";
import { 
    Container, Row, Col, Alert, Spinner, Button, Card, 
    // Importing Tabs components
    Nav, Tab 
} from 'react-bootstrap'; 
import { Link } from 'react-router-dom';
import ParishTable from "./ParishTable";
import { useSession } from "../contexts/session";
import "@fortawesome/fontawesome-free/css/all.min.css"; 

const ParishIndex = () => {
    const { session } = useSession();
    const isAdmin = session.data?.isAdmin === true;

    const [parishes, setParishes] = useState([]);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);
    const [activeConfession, setActiveConfession] = useState('CATHOLIC_LATIN'); // Set default active tab

    const deleteParish = async (id) => {
        if (!window.confirm("CONFIRMATION: Deleting this jurisdiction will remove all associated location links. Proceed?")) return;
        try {
            await apiDelete("/api/parishes/" + id);
            setParishes(parishes.filter((item) => item._id !== id));
            setError(null);
        } catch (error) {
            console.error("Delete Error:", error.message);
            setError("Authorization Error: Failed to delete the record. Access denied.");
        }
    };

    useEffect(() => {
        setLoading(true);
        apiGet("/api/parishes")
            .then((data) => {
                setParishes(data);
                setError(null);
            })
            .catch((error) => {
                setError("Data Retrieval Error: Failed to load ecclesiastical records.");
                console.error(error);
            })
            .finally(() => {
                setLoading(false);
            });
    }, []);
    
    // --- UPDATED CONFESSION DATA WITH HIERARCHY (same structure as before) ---
    const confessionDescriptions = [
        { 
            key: "CATHOLIC_LATIN", name: "Catholic Latin (Roman)", 
            icon: "fas fa-cross", 
            description: "The primary Western rite, foundational to Polish state administration. Records are crucial for civil and nobility research.",
            hierarchy: [
                { role: "Metropolitan Archbishop", authority: "Highest territorial authority (e.g., Gniezno)" },
                { role: "Diocesan Bishop", authority: "Administrator of a Diocese" },
                { role: "Dean/Vicar", authority: "Regional administrative oversight" },
                { role: "Parish Priest (Parochus)", authority: "Direct administrator of the Parish (Jursidiction)" }
            ],
            context: "Structure mirrors contemporary Western Europe; records were often maintained in Latin, linking the Parish to the central European administration."
        },
        { 
            key: "CATHOLIC_UNIATE", name: "Catholic Uniate (Greek)", 
            icon: "fas fa-people-arrows", 
            description: "An Eastern liturgy united with Rome. Highly sensitive administrative data reflecting ethnic and political allegiances in the Commonwealth.",
            hierarchy: [
                { role: "Metropolitan/Archeparch", authority: "Highest authority (e.g., Kyiv, Lviv)" },
                { role: "Eparchial Bishop", authority: "Administrator of an Eparchy (Diocese)" },
                { role: "Deanery (Protopop)", authority: "Regional administrative oversight" },
                { role: "Parish Priest (Paroch)", authority: "Direct administrator of the Parish" }
            ],
            context: "Used Ruthenian (later Cyrillic script) and Church Slavonic. The structure was influenced by Eastern tradition but mandated papal recognition."
        },
        { 
            key: "ORTHODOX", name: "Orthodox (Eastern)", 
            icon: "fas fa-church", 
            description: "The dominant Eastern Christian confession. Their jurisdictions track population shifts following partitions and political annexations.",
            hierarchy: [
                { role: "Patriarch/Metropolitan", authority: "The highest spiritual authority (e.g., Moscow, Constantinople)" },
                { role: "Eparchial Bishop", authority: "Administrator of an Eparchy (Diocese)" },
                { role: "Priest (Paroch)", authority: "Direct administrator of the Parish" },
                { role: "Clerical Assistants", authority: "Diakon, Psalmista (support roles for services and records)" }
            ],
            context: "Strongly tied to Eastern cultural identity. Administrative changes frequently mirrored the expansion of the Russian Empire into the region."
        },
        { 
            key: "JEWISH", name: "Jewish (Kahal)", 
            icon: "fas fa-star-of-david", 
            description: "Administrative units centered on the Kahal (Kehillah). These bodies were central to civil registration, taxation, and internal governance.",
            hierarchy: [
                { role: "Council of the Four Lands (Until 1764)", authority: "Highest administrative and taxation body for the region" },
                { role: "Kahal / Kehillah (Local)", authority: "Autonomous municipal council and taxing authority" },
                { role: "Rabbi / Dayyan", authority: "Spiritual and legal head" },
                { role: "Clerical/Scribal Officials", authority: "Secretaries, tax collectors, record keepers (key to civil data)" }
            ],
            context: "The Kahal served both religious and civil functions until its abolition by the authorities, making its structure key to understanding Jewish autonomy."
        },
        { 
            key: "OTHER", name: "Other / Protestant", 
            icon: "fas fa-bible", 
            description: "Covers denominations like Lutheran and Calvinist. Provides micro-level data on professional and merchant classes.", 
            hierarchy: [
                { role: "Superintendent/Senior", authority: "Regional administrative authority over multiple congregations" },
                { role: "Consistory", authority: "Council of elders/clergy governing a district" },
                { role: "Pastor (Minister)", authority: "Head of the individual Congregation/Parish" }
            ],
            context: "Often concentrated in large towns, their records reveal networks distinct from the major state-recognized confessions."
        },
    ];

    const parishStructureDescriptions = [
        {
            title: "Jurisdictional Integrity",
            icon: "fas fa-map-marked-alt",
            text: "Each entry defines a spatial and legal boundary, allowing researchers to accurately place individuals and events within specific religious and civil administrative spheres."
        },
        {
            title: "Temporal Dynamics",
            icon: "fas fa-history",
            text: "Records include establishment and cancellation years, enabling longitudinal studies of religious institutional survival and dissolution across historical regime changes."
        },
        {
            title: "Core Infrastructure Linkage",
            icon: "fas fa-building",
            text: "Explicitly links the primary church building and its geographical coordinates to the parish entity, supporting geospatial analysis and archival provenance."
        }
    ];


    if (loading) return (
        <Container className="text-center my-5 py-5">
            <Spinner animation="border" variant="primary" />
            <p className="mt-3 text-muted">Indexing ecclesiastical records...</p>
        </Container>
    );

    return (
        <Container className="my-5 py-3">
            <header className="text-center mb-5">
                <h1 className="display-5 fw-bold text-dark mb-1">
                    <i className="fas fa-church me-3 text-primary"></i>Ecclesiastical Jurisdictions Ontology
                </h1>
                <p className="lead text-secondary fst-italic mb-4 mx-auto" style={{maxWidth: '800px'}}>
                    This index provides structured access to religious and administrative units, crucial for **socio-demographic, cultural, and geospatial analysis** across the long-term historical domain of Eastern Europe.
                </p>
                {isAdmin && (
                    <div className="d-flex justify-content-center my-4">
                        <Link to="/parishes/create" className="btn btn-primary btn-lg rounded-pill px-5 py-2 fw-semibold shadow-lg">
                            <i className="fas fa-plus-circle me-2"></i>Create New Jurisdiction
                        </Link>
                    </div>
                )}
            </header>
            
            {/* Structural and Methodological Context */}
            <Row className="mb-5 justify-content-center">
                <Col md={10}>
                    <h2 className="fw-bold text-dark mb-3">I. The Organizational Structure Framework</h2>
                    <Row className="g-4 mb-4">
                        {parishStructureDescriptions.map((item, index) => (
                            <Col md={4} key={index}>
                                <Card className="h-100 shadow-sm border-start border-primary border-4 rounded-3">
                                    <Card.Body className="p-3">
                                        <h6 className="fw-bold text-primary mb-1">
                                            <i className={`${item.icon} me-2`}></i> {item.title}
                                        </h6>
                                        <p className="text-muted small mb-0">{item.text}</p>
                                    </Card.Body>
                                </Card>
                            </Col>
                        ))}
                    </Row>

                    <h2 className="fw-bold text-dark mt-4 mb-3">II. Confessional Taxonomies & Administrative Hierarchies</h2>
                    
                    {/* --- TABS IMPLEMENTATION START --- */}
                    <Tab.Container 
                        id="confession-tabs" 
                        activeKey={activeConfession} 
                        onSelect={(k) => setActiveConfession(k)}
                    >
                        <Row>
                            {/* Confession Nav/Pills */}
                            <Col md={3} className="mb-3">
                                <Nav variant="pills" className="flex-column p-3 bg-light rounded-3 shadow-sm border">
                                    {confessionDescriptions.map((item) => (
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
                                    {confessionDescriptions.map((item) => (
                                        <Tab.Pane key={item.key} eventKey={item.key}>
                                            <h4 className="fw-bold text-dark border-bottom pb-2 mb-3">{item.name} Hierarchy</h4>
                                            
                                            <p className="text-muted mb-4">{item.description}</p>
                                            
                                            <h5 className="fw-bold text-primary mb-3">Administrative Chain of Authority (PL-LT Context):</h5>
                                            
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
                                                <i className="fas fa-info-circle me-1 text-primary"></i> Contextual Note: {item.context}
                                            </p>
                                        </Tab.Pane>
                                    ))}
                                </Tab.Content>
                            </Col>
                        </Row>
                    </Tab.Container>
                    {/* --- TABS IMPLEMENTATION END --- */}


                </Col>
            </Row>

            <hr className="mb-5"/>

            {/* Displaying error alert if there was an error */}
            {error && <Alert variant="danger" className="rounded-3 shadow-sm">{error}</Alert>}
            
            <ParishTable
                deleteParish={deleteParish}
                items={parishes}
                label="Total Jurisdictions Indexed:"
            />
 
        </Container>
    );
};

export default ParishIndex;
