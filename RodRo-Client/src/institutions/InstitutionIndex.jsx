// InstitutionIndex.jsx
import React, { useEffect, useState } from "react";
import { apiDelete, apiGet } from "../utils/api";
import {
    Container, Row, Col, Alert, Spinner, Card,
    Nav, Tab
} from 'react-bootstrap';
import { Link } from 'react-router-dom';
import InstitutionTable from "./InstitutionTable";
import { useSession } from "../contexts/session";
import "@fortawesome/fontawesome-free/css/all.min.css";

const InstitutionIndex = () => {
    const { session } = useSession();
    const isAdmin = session.data?.isAdmin === true;

    const [institutions, setInstitutions] = useState([]);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);
    const [activeType, setActiveType] = useState("POLITICAL");

    const deleteInstitution = async (id) => {
        if (!window.confirm("CONFIRMATION: Deleting this institution will remove its associated records. Proceed?")) return;
        try {
            await apiDelete("/api/institutions/" + id);
            setInstitutions(institutions.filter((item) => item._id !== id));
            setError(null);
        } catch (error) {
            console.error("Delete Error:", error.message);
            setError("Authorization Error: Failed to delete the record. Access denied.");
        }
    };

    useEffect(() => {
        setLoading(true);
        apiGet("/api/institutions")
            .then((data) => {
                setInstitutions(data);
                setError(null);
            })
            .catch((error) => {
                setError("Data Retrieval Error: Failed to load institutional records.");
                console.error(error);
            })
            .finally(() => {
                setLoading(false);
            });
    }, []);

    // --- Expanded Institution Typologies ---
    const institutionTypes = [
        {
            key: "POLITICAL",
            name: "Political Institutions",
            icon: "fas fa-landmark",
            description: "Organs of governance that shaped law, sovereignty, and administration. Their records reveal power structures and the interaction between ruler and subjects.",
            structure: [
                { role: "Monarch / Head of State", authority: "Supreme authority of the realm" },
                { role: "Parliament / Diet / Senate", authority: "Representative assemblies deliberating on laws and taxation" },
                { role: "Chancery / Kanzlei", authority: "Administrative office issuing royal decrees and charters" },
                { role: "Local Councils / Voivodeships", authority: "Regional power centers" }
            ],
            context: "Political institutions balanced central authority with estates and local representation; their records form the backbone of state history."
        },
{
    key: "ECONOMIC",
    name: "Economic Institutions",
    icon: "fas fa-coins",
    description: "Organizers of production, trade, finance, agriculture, and labor, crucial to the material and commercial basis of society.",
    structure: [
        { role: "Agrarian Institution / Manor", authority: "Managed land, crops, and peasant labor; organized rural production" },
        { role: "Merchant Guild", authority: "Controlled urban trade, set standards, and regulated membership" },
        { role: "Craft Guild", authority: "Organized artisans, oversaw apprenticeships and quality of production" },
        { role: "Bank / Treasury", authority: "Managed credit, taxation, and financial flows" },
        { role: "Trading Company", authority: "Joint-stock or commercial enterprises for domestic and international trade" },
        { role: "Manufacturing Enterprise", authority: "Factories and workshops producing goods at scale" },
        { role: "Small Private Business", authority: "Independent shops, workshops, and family-run commercial ventures" },
        { role: "Industrial Sector Organization", authority: "Institutions coordinating industrial production, labor, and technology" }
    ],
    context: "Economic institutions structured rural and urban production, linked local and global markets, organized labor, and shaped wealth distribution and state revenues across time and space."
},
        {
            key: "SOCIAL",
            name: "Social Institutions",
            icon: "fas fa-hands-helping",
            description: "Charitable, medical, and communal organizations supporting society’s cohesion.",
            structure: [
                { role: "Hospitals / Almshouses", authority: "Care for the sick and poor" },
                { role: "Brotherhoods / Confraternities", authority: "Lay religious associations with civic roles" },
                { role: "Foundations / Endowments", authority: "Funded schools, chapels, or welfare" },
                { role: "Orphanages", authority: "Custodians of vulnerable children" }
            ],
            context: "Social institutions reflect communal solidarity, philanthropy, and religiously motivated welfare."
        },
        {
            key: "CULTURAL",
            name: "Cultural Institutions",
            icon: "fas fa-theater-masks",
            description: "Spaces for performance, art, and intellectual communication.",
            structure: [
                { role: "Theatres", authority: "Produced drama and public performance" },
                { role: "Museums", authority: "Preserved collections and artifacts of heritage" },
                { role: "Printing Houses / Publishers", authority: "Spread books, pamphlets, and newspapers" },
                { role: "Art Academies", authority: "Institutionalized education of artists" }
            ],
            context: "Cultural institutions acted as laboratories of identity and memory."
        },
        {
            key: "EDUCATIONAL",
            name: "Educational Institutions",
            icon: "fas fa-graduation-cap",
            description: "Institutions of learning that train elites, clerics, and professionals across various levels of education.",
            structure: [
                { role: "University", authority: "Higher education institutions granting degrees and fostering research" },
                { role: "College", authority: "Post-secondary institutions providing specialized courses and training" },
                { role: "School", authority: "Primary and secondary institutions offering foundational education" },
                { role: "Academy", authority: "Specialized institutions focused on arts, sciences, or vocational skills" }
            ],
            context: "Educational institutions shaped intellectual elites, facilitated knowledge transfer, and supported social mobility across regions."
        },
        {
            key: "RELIGIOUS",
            name: "Religious Institutions",
            icon: "fas fa-church",
            description: "Centers of spiritual life, manuscript culture, and estate management across societies.",
            structure: [
                { role: "Monastery", authority: "Religious community preserving knowledge, traditions, and managing estates" },
                { role: "Convent / Abbey", authority: "Female religious community focused on prayer, education, and charitable work" },
                { role: "Order", authority: "Organized religious society with specific rules and spiritual focus" },
                { role: "Temple / Shrine", authority: "Centers of worship, pilgrimage, and ritual activities" }
            ],
            context: "Religious institutions preserved knowledge, administered land, mediated social and spiritual life, and maintained continuity across time and space."
        },
        {
            key: "ARCHIVAL",
            name: "Archives & Libraries",
            icon: "fas fa-archive",
            description: "Custodians of written and material heritage across societies.",
            structure: [
                { role: "Archive", authority: "Repository for government, legal, and institutional records" },
                { role: "Library", authority: "Collection of books and manuscripts for research, education, and preservation" },
                { role: "Record Office", authority: "Facility maintaining official documentation for administrative continuity" },
                { role: "Manuscript Collection", authority: "Specialized holdings of rare or historical texts" }
            ],
            context: "Archives and libraries safeguarded collective memory, legal continuity, and historical research across generations."
        },
        {
            key: "SCIENTIFIC",
            name: "Scientific Institutions",
            icon: "fas fa-flask",
            description: "Associations and institutions promoting research, experimentation, and the exchange of ideas.",
            structure: [
                { role: "Scientific Society", authority: "Formal association for research and knowledge dissemination" },
                { role: "Laboratory / Research Institute", authority: "Facilities for conducting experiments and scientific studies" },
                { role: "Academy of Sciences", authority: "Institution coordinating scholarly work and standardizing knowledge" },
                { role: "Observatory / Field Station", authority: "Specialized site for empirical observation and experimentation" }
            ],
            context: "Scientific institutions institutionalized inquiry, knowledge production, and transnational collaboration across time and space."
        },
        {
            key: "MILITARY",
            name: "Military Institutions",
            icon: "fas fa-shield-alt",
            description: "Organizations structuring armies, strategic planning, and defense across states and regions.",
            structure: [
                { role: "Army", authority: "Primary military organization responsible for defense and campaigns" },
                { role: "Naval Force", authority: "Maritime military institution controlling fleets and naval operations" },
                { role: "Fort / Garrison", authority: "Stationed troops for territorial control and defense" },
                { role: "Military Academy", authority: "Training institution for officers and specialized military personnel" }
            ],
            context: "Military institutions structured armed forces, ensured strategic command, and maintained defense and order across territories and historical periods."
        }

    ];

    const researchAspects = [
        {
            title: "Continuity & Transformation",
            icon: "fas fa-history",
            text: "Institutions adapt across regimes, offering insights into resilience and reform."
        },
        {
            title: "Networks of Power",
            icon: "fas fa-sitemap",
            text: "Institutional linkages reveal who governed, produced, and preserved knowledge."
        },
        {
            title: "Social Memory",
            icon: "fas fa-book",
            text: "Institutions transmit culture and identity across generations."
        }
    ];

    if (loading) return (
        <Container className="text-center my-5 py-5">
            <Spinner animation="border" variant="primary" />
            <p className="mt-3 text-muted">Indexing institutional records...</p>
        </Container>
    );

    return (
        <Container className="my-5 py-3">
            <header className="text-center mb-5">
                <h1 className="display-5 fw-bold text-dark mb-1">
                    <i className="fas fa-university me-3 text-primary"></i>Institutions Across Society and State
                </h1>
                <p className="lead text-secondary fst-italic mb-4 mx-auto" style={{ maxWidth: '800px' }}>
                    This index integrates political, economic, social, and cultural institutions —
                    offering a comparative framework for studying governance, community, and cultural life across Europe.
                </p>
                {isAdmin && (
                    <div className="d-flex justify-content-center my-4">
                        <Link to="/institutions/create" className="btn btn-primary btn-lg rounded-pill px-5 py-2 fw-semibold shadow-lg">
                            <i className="fas fa-plus-circle me-2"></i>Create New Institution
                        </Link>
                    </div>
                )}
            </header>

            {/* Research Significance Cards */}
            <Row className="mb-5 justify-content-center">
                {researchAspects.map((aspect, index) => (
                    <Col md={4} key={index}>
                        <Card className="h-100 shadow-sm border-start border-primary border-4 rounded-3">
                            <Card.Body className="p-3">
                                <h6 className="fw-bold text-primary mb-1">
                                    <i className={`${aspect.icon} me-2`}></i>{aspect.title}
                                </h6>
                                <p className="text-muted small mb-0">{aspect.text}</p>
                            </Card.Body>
                        </Card>
                    </Col>
                ))}
            </Row>

            {/* Typology Tabs */}
            <h2 className="fw-bold text-dark mt-4 mb-3">Institutional Typologies & Historical Contexts</h2>
            <Tab.Container id="institution-tabs" activeKey={activeType} onSelect={(k) => setActiveType(k)}>
                <Row>
                    <Col md={3} className="mb-3">
                        <Nav variant="pills" className="flex-column p-3 bg-light rounded-3 shadow-sm border">
                            {institutionTypes.map((type) => (
                                <Nav.Item key={type.key}>
                                    <Nav.Link eventKey={type.key} className="text-start mb-1 fw-semibold">
                                        <i className={`${type.icon} me-2`}></i> {type.name}
                                    </Nav.Link>
                                </Nav.Item>
                            ))}
                        </Nav>
                    </Col>
                    <Col md={9}>
                        <Tab.Content className="p-4 bg-white rounded-3 shadow border">
                            {institutionTypes.map((type) => (
                                <Tab.Pane key={type.key} eventKey={type.key}>
                                    <h4 className="fw-bold text-dark border-bottom pb-2 mb-3">{type.name}</h4>
                                    <p className="text-muted mb-4">{type.description}</p>
                                    <h5 className="fw-bold text-primary mb-3">Institutional Structure:</h5>
                                    <Row className="g-3">
                                        {type.structure.map((level, i) => (
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
                                        <i className="fas fa-info-circle me-1 text-primary"></i> Contextual Note: {type.context}
                                    </p>
                                </Tab.Pane>
                            ))}
                        </Tab.Content>
                    </Col>
                </Row>
            </Tab.Container>

            <hr className="mb-5" />

            {error && <Alert variant="danger" className="rounded-3 shadow-sm">{error}</Alert>}

            <InstitutionTable
                deleteInstitution={deleteInstitution}
                items={institutions}
                label="Total Institutions Indexed:"
            />
        </Container>
    );
};

export default InstitutionIndex;
