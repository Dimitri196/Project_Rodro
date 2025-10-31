import React, { useEffect, useState } from "react";
import { apiGet, apiDelete } from "../utils/api";
import { Container, Row, Col, Alert, Spinner, Card, Tab, Nav } from "react-bootstrap";
import { Link } from "react-router-dom";
import OccupationTable from "./OccupationTable";
import { useSession } from "../contexts/session";
import "@fortawesome/fontawesome-free/css/all.min.css";

const OccupationIndex = () => {
    const { session } = useSession();
    const isAdmin = session.data?.isAdmin === true;

    const [occupations, setOccupations] = useState([]);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);
    const [activeType, setActiveType] = useState("POLITICAL");

    const deleteOccupation = async (id) => {
        if (!window.confirm("CONFIRMATION: Deleting this occupation will remove its associated records. Proceed?")) return;
        try {
            await apiDelete("/api/occupations/" + id);
            setOccupations(occupations.filter((item) => item._id !== id));
            setError(null);
        } catch (err) {
            console.error("Delete Error:", err.message);
            setError("Authorization Error: Failed to delete the record. Access denied.");
        }
    };

    useEffect(() => {
        setLoading(true);
        apiGet("/api/occupations")
            .then((data) => {
                setOccupations(data);
                setError(null);
            })
            .catch((err) => {
                setError("Data Retrieval Error: Failed to load occupation records.");
                console.error(err);
            })
            .finally(() => setLoading(false));
    }, []);

    // --- Occupation Typologies ---
const occupationTypes = [

    {
        key: "PROFESSIONAL",
        name: "Professional & Technical Occupations",
        icon: "fas fa-briefcase",
        description: "Roles in skilled trades, management, technical professions, and modern labor sectors.",
        structure: [
            { role: "Engineers / Architects", function: "Designed and implemented technical projects and infrastructure" },
            { role: "Managers / Administrators", function: "Oversaw operations, personnel, and organizational workflows" },
            { role: "Skilled Laborers / Craftsmen", function: "Performed specialized manual and technical tasks" },
            { role: "Technicians / Surveyors", function: "Maintained equipment, conducted measurements, and supported production" },
        ],
        context: "Professional and technical occupations drove industrial, commercial, and infrastructural development, bridging intellectual planning with practical execution across sectors and regions."
    },
    {
        key: "POLITICAL",
        name: "Political Occupations",
        icon: "fas fa-landmark",
        description: "Roles involved in governance, administration, and policy-making.",
        structure: [
            { role: "Monarch / Head of State", function: "Held supreme authority and directed government policies" },
            { role: "Legislators / Senators", function: "Drafted and approved laws" },
            { role: "Civil Servants / Bureaucrats", function: "Administered public offices and state functions" },
            { role: "Local Officials / Councilors", function: "Managed local governance and community affairs" }
        ],
        context: "Political occupations ensured the functioning of state structures, representation, and law enforcement over time and across regions."
    },
    {
        key: "ECONOMIC",
        name: "Economic Occupations",
        icon: "fas fa-coins",
        description: "Roles in trade, finance, agriculture, and industry.",
        structure: [
            { role: "Farmers / Agrarian Managers", function: "Managed crops, livestock, and rural production" },
            { role: "Merchants / Traders", function: "Engaged in commercial exchange locally and internationally" },
            { role: "Bankers / Accountants", function: "Oversaw financial transactions, credit, and bookkeeping" },
            { role: "Artisans / Craftsmen", function: "Produced goods and maintained trade skills" },
            { role: "Factory Workers / Industrial Laborers", function: "Operated machinery and contributed to manufacturing processes" }
        ],
        context: "Economic occupations structured production, commerce, and labor, connecting local economies to global markets."
    },
    {
        key: "SOCIAL",
        name: "Social Occupations",
        icon: "fas fa-hands-helping",
        description: "Roles in charitable, medical, and communal institutions.",
        structure: [
            { role: "Doctors / Surgeons", function: "Provided medical care and public health services" },
            { role: "Nurses / Caretakers", function: "Assisted in hospitals, almshouses, and social welfare institutions" },
            { role: "Charity Organizers / Social Workers", function: "Coordinated relief, education, and communal support" },
            { role: "Orphanage & Shelter Staff", function: "Managed care for vulnerable populations" }
        ],
        context: "Social occupations reflected community support, welfare, and organized philanthropy across societies."
    },
    {
        key: "CULTURAL",
        name: "Cultural Occupations",
        icon: "fas fa-theater-masks",
        description: "Roles in arts, literature, education, and heritage preservation.",
        structure: [
            { role: "Artists / Painters / Sculptors", function: "Produced visual artworks" },
            { role: "Writers / Poets / Journalists", function: "Created and disseminated literary works and news" },
            { role: "Teachers / Educators", function: "Delivered cultural and artistic education" },
            { role: "Archivists / Museum Curators", function: "Preserved and presented cultural heritage" }
        ],
        context: "Cultural occupations maintained societal memory, artistic expression, and intellectual transmission."
    },
    {
        key: "MILITARY",
        name: "Military Occupations",
        icon: "fas fa-shield-alt",
        description: "Roles related to defense, armed forces, and strategy.",
        structure: [
            { role: "Commander / General", function: "Directed military strategy and forces" },
            { role: "Officers", function: "Led units and executed orders" },
            { role: "Soldiers / Infantry", function: "Carried out combat and defensive operations" },
            { role: "Engineers / Artillery Specialists", function: "Maintained equipment and constructed fortifications" }
        ],
        context: "Military occupations ensured security, strategic planning, and operational command over territories and conflicts."
    },
    {
        key: "SCIENTIFIC",
        name: "Scientific Occupations",
        icon: "fas fa-flask",
        description: "Roles in research, experimentation, and knowledge production.",
        structure: [
            { role: "Researchers / Scientists", function: "Conducted studies, experiments, and observations" },
            { role: "Academics / Professors", function: "Taught and guided intellectual inquiry" },
            { role: "Lab Technicians / Assistants", function: "Supported experiments, data collection, and analysis" }
        ],
        context: "Scientific occupations advanced knowledge, innovation, and technological development across disciplines and geographies."
    },
    {
        key: "RELIGIOUS",
        name: "Religious Occupations",
        icon: "fas fa-church",
        description: "Roles within spiritual, clerical, and religious institutions.",
        structure: [
            { role: "Clergy / Priests / Ministers", function: "Led worship, rituals, and spiritual guidance" },
            { role: "Monks / Nuns", function: "Maintained religious practices and community support" },
            { role: "Religious Administrators", function: "Managed estates, schools, and charitable activities" }
        ],
        context: "Religious occupations mediated faith, moral authority, and social organization across time and regions."
    },
    {
        key: "ARCHIVAL",
        name: "Archival & Documentation Occupations",
        icon: "fas fa-archive",
        description: "Roles involved in record keeping, libraries, and historical documentation.",
        structure: [
            { role: "Archivists / Librarians", function: "Managed, preserved, and catalogued documents and books" },
            { role: "Cataloguers / Record Keepers", function: "Created finding aids, indexes, and inventories" },
            { role: "Historians / Researchers", function: "Consulted and interpreted archival materials" }
        ],
        context: "Archival occupations safeguarded institutional memory and ensured continuity of legal and historical records."
    }
];

    const researchAspects = [
        { title: "Social & Political Impact", icon: "fas fa-users", text: "Occupations reflect societal hierarchies and governance structures." },
        { title: "Economic Function", icon: "fas fa-coins", text: "Trade and profession shaped local and regional economies." },
        { title: "Cultural & Educational Role", icon: "fas fa-graduation-cap", text: "Professions preserved knowledge and contributed to civic culture." },
    ];

    if (loading) return (
        <Container className="text-center my-5 py-5">
            <Spinner animation="border" variant="primary" />
            <p className="mt-3 text-muted">Loading occupation records...</p>
        </Container>
    );

    return (
        <Container className="my-5 py-3">
            {/* Header */}
            <header className="text-center mb-5">
                <h1 className="display-5 fw-bold text-dark mb-2">
                    <i className="fas fa-briefcase me-3 text-primary"></i>Occupations Across Society
                </h1>
                <p className="lead text-secondary fst-italic mb-4 mx-auto" style={{ maxWidth: '800px' }}>
                    This index highlights political, economic, religious, and cultural occupations, offering insights into roles, responsibilities, and historical significance.
                </p>
                {isAdmin && (
                    <div className="d-flex justify-content-center my-4">
                        <Link to="/occupations/create" className="btn btn-primary btn-lg rounded-pill px-5 py-2 fw-semibold shadow-lg">
                            <i className="fas fa-plus-circle me-2"></i>Create New Occupation
                        </Link>
                    </div>
                )}
            </header>

            {/* Research Significance */}
            <Row className="mb-5 justify-content-center">
                {researchAspects.map((aspect, idx) => (
                    <Col md={4} key={idx}>
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
            <h2 className="fw-bold text-dark mt-4 mb-3">Occupation Typologies & Historical Context</h2>
            <Tab.Container id="occupation-tabs" activeKey={activeType} onSelect={(k) => setActiveType(k)}>
                <Row>
                    <Col md={3} className="mb-3">
                        <Nav variant="pills" className="flex-column p-3 bg-light rounded-3 shadow-sm border">
                            {occupationTypes.map((type) => (
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
                            {occupationTypes.map((type) => (
                                <Tab.Pane key={type.key} eventKey={type.key}>
                                    <h4 className="fw-bold text-dark border-bottom pb-2 mb-3">{type.name}</h4>
                                    <p className="text-muted mb-4">{type.description}</p>
                                    <h5 className="fw-bold text-primary mb-3">Roles & Functions:</h5>
                                    <Row className="g-3">
                                        {type.structure.map((level, i) => (
                                            <Col md={6} key={i}>
                                                <Card className="h-100 border-0 border-start border-4 border-secondary-subtle">
                                                    <Card.Body className="p-3">
                                                        <h6 className="mb-1 text-dark">
                                                            <i className="fas fa-sitemap me-2 text-secondary"></i>
                                                            <strong>{level.role}</strong>
                                                        </h6>
                                                        <p className="small text-muted mb-0">{level.function}</p>
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

            <hr className="my-5" />

            {error && <Alert variant="danger" className="rounded-3 shadow-sm">{error}</Alert>}

            {/* Occupation Table */}
            <OccupationTable
                deleteOccupation={deleteOccupation}
                items={occupations}
                label="Total Occupations Indexed:"
            />
        </Container>
    );
};

export default OccupationIndex;
