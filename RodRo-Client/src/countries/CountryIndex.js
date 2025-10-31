import React, { useEffect, useState } from "react";
import { apiDelete, apiGet } from "../utils/api";
import { 
    Container, Row, Col, Alert, Spinner, Card, Nav, Tab 
} from 'react-bootstrap'; 
import { Link } from 'react-router-dom';
import CountryTable from "./CountryTable";
import { useSession } from "../contexts/session";
import "@fortawesome/fontawesome-free/css/all.min.css"; 

const CountryIndex = () => {
    const { session } = useSession();
    const isAdmin = session.data?.isAdmin === true;

    const [countries, setCountries] = useState([]);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);
    const [activeTab, setActiveTab] = useState('POLITICAL_HISTORY');

    const deleteCountry = async (id) => {
        if (!window.confirm("Are you sure you want to delete this country?")) return;
        try {
            await apiDelete(`/api/countries/${id}`);
            setCountries(countries.filter(item => item.id !== id));
            setError(null);
        } catch (err) {
            console.error(err);
            setError(`Failed to delete country: ${err.message || "Unknown error"}`);
        }
    };

    useEffect(() => {
        setLoading(true);
        apiGet("/api/countries")
            .then((data) => {
                setCountries(data);
                setError(null);
            })
            .catch((err) => {
                console.error(err);
                setError(`Failed to load country data: ${err.message || "Unknown error"}`);
            })
            .finally(() => setLoading(false));
    }, []);

    // --- Context cards describing temporal and spatial framework ---
    const countryStructureCards = [
        {
            title: "State Continuity & Changes",
            icon: "fas fa-history",
            text: "Tracks establishment and dissolution years, allowing historians to trace political boundaries and state evolution across Europe."
        },
        {
            title: "Flag & Symbolic Identity",
            icon: "fas fa-flag",
            text: "Flags and emblems provide visual cues for national identity and continuity in historical documentation."
        },
        {
            title: "Administrative Integration",
            icon: "fas fa-landmark",
            text: "Linkage with provinces, military organizations, and governance structures illustrates the material and political organization of each state."
        }
    ];

    // --- Tabs for historical/cultural context ---
    const countryHistoryTabs = [
        {
            key: "POLITICAL_HISTORY",
            title: "Political History",
            icon: "fas fa-scroll",
            content: "Describes the formation, alliances, and conflicts of states, including partitions, unions, and treaties shaping Europe across centuries."
        },
        {
            key: "ECONOMIC_FRAMEWORK",
            title: "Economic Framework",
            icon: "fas fa-coins",
            content: "Examines trade networks, taxation, urban centers, and resource distribution, linking economic structures to national stability."
        },
        {
            key: "MILITARY_ORGANIZATION",
            title: "Military Organization",
            icon: "fas fa-shield-alt",
            content: "Analyzes army structures, command hierarchies, and fortifications, revealing state capacity for defense and territorial control."
        }
    ];

    if (loading) return (
        <Container className="text-center my-5 py-5">
            <Spinner animation="border" variant="primary" />
            <p className="mt-3 text-muted">Loading country records...</p>
        </Container>
    );

    return (
        <Container className="my-5 py-3">
            <header className="text-center mb-5">
                <h1 className="display-5 fw-bold text-dark mb-1">
                    <i className="fas fa-globe me-3 text-primary"></i>Historical State Records
                </h1>
                <p className="lead text-secondary fst-italic mb-4 mx-auto" style={{maxWidth: '800px'}}>
                    This index provides structured access to country-level data, including political, economic, and military frameworks across time and space.
                </p>
                {isAdmin && (
                    <div className="d-flex justify-content-center my-4">
                        <Link to="/countries/create" className="btn btn-success btn-lg rounded-pill px-5 py-2 fw-semibold shadow-lg">
                            <i className="fas fa-plus-circle me-2"></i>Create New Country
                        </Link>
                    </div>
                )}
            </header>

            {/* Structural Cards */}
            <Row className="mb-5 justify-content-center">
                {countryStructureCards.map((item, idx) => (
                    <Col md={4} key={idx}>
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

            {/* History Tabs */}
            <Tab.Container activeKey={activeTab} onSelect={(k) => setActiveTab(k)}>
                <Row>
                    <Col md={3} className="mb-3">
                        <Nav variant="pills" className="flex-column p-3 bg-light rounded-3 shadow-sm border">
                            {countryHistoryTabs.map(tab => (
                                <Nav.Item key={tab.key}>
                                    <Nav.Link eventKey={tab.key} className="text-start mb-1 fw-semibold">
                                        <i className={`${tab.icon} me-2`}></i> {tab.title}
                                    </Nav.Link>
                                </Nav.Item>
                            ))}
                        </Nav>
                    </Col>
                    <Col md={9}>
                        <Tab.Content className="p-4 bg-white rounded-3 shadow border">
                            {countryHistoryTabs.map(tab => (
                                <Tab.Pane key={tab.key} eventKey={tab.key}>
                                    <p className="text-muted mb-0">{tab.content}</p>
                                </Tab.Pane>
                            ))}
                        </Tab.Content>
                    </Col>
                </Row>
            </Tab.Container>

            <hr className="my-5" />

            {error && <Alert variant="danger" className="rounded-3 shadow-sm">{error}</Alert>}

            {/* Country Table */}
            <CountryTable
                deleteCountry={deleteCountry}
                items={countries}
                label="Total Countries Indexed:"
            />
        </Container>
    );
};

export default CountryIndex;
