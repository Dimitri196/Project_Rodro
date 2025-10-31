import React, { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import { 
    Container, Row, Col, Card, Alert, Spinner,
    Nav, Tab, ListGroup
} from "react-bootstrap";
import { apiGet } from "../utils/api";

const MilitaryStructureDetail = () => {
    const { id } = useParams(); 
    const [structure, setStructure] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const [ranks, setRanks] = useState([]);
    const [ranksError, setRanksError] = useState(null);
    const [activeRank, setActiveRank] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            setLoading(true);
            setError(null);
            try {
                const structureData = await apiGet(`/api/militaryStructures/${id}`);
                setStructure(structureData);

                const ranksData = await apiGet(`/api/militaryStructures/${id}/ranks`);
                setRanks(ranksData);
                if (ranksData.length > 0) setActiveRank(ranksData[0]._id);
            } catch (err) {
                console.error("Error fetching military structure or ranks:", err);
                setError(`Failed to load military structure details: ${err.message || err}`);
            } finally {
                setLoading(false);
            }
        };

        fetchData();
    }, [id]);

    if (loading) return (
        <Container className="my-5 text-center">
            <Spinner animation="border" role="status" />
            <p className="mt-3 text-muted">Loading military structure details...</p>
        </Container>
    );

    if (error) return (
        <Container className="my-5">
            <Alert variant="danger" className="text-center shadow-sm rounded-3">
                <i className="fas fa-exclamation-triangle me-2"></i>{error}
            </Alert>
        </Container>
    );

    if (!structure) return (
        <Container className="my-5">
            <Alert variant="info" className="text-center shadow-sm rounded-3">
                <i className="fas fa-info-circle me-2"></i>Military structure not found.
            </Alert>
        </Container>
    );

    return (
        <Container className="my-5 py-4 bg-light rounded shadow-lg">
            <Row className="mb-4">
                <Col md={12} className="text-center">
                    <h1 className="display-4 fw-bold text-primary mb-3">
                        <i className="fas fa-landmark me-3"></i>{structure.unitName}
                    </h1>
                    <p className="lead text-muted fst-italic">{structure.unitType || "Military Unit"}</p>
                </Col>
            </Row>

            <Row className="justify-content-center mb-4">
                {/* Basic Info */}
                <Col md={6} className="mb-4">
                    <Card className="shadow-lg border-0 rounded-4 h-100">
                        <Card.Header className="bg-primary text-white py-3 rounded-top-4">
                            <i className="fas fa-info-circle me-2"></i>Basic Information
                        </Card.Header>
                        <Card.Body className="p-4">
                            <ListGroup variant="flush">
                                <ListGroup.Item className="d-flex justify-content-between align-items-center px-0">
                                    <strong>Unit Type:</strong> <span>{structure.unitType || "-"}</span>
                                </ListGroup.Item>
                                <ListGroup.Item className="d-flex justify-content-between align-items-center px-0">
                                    <strong>Organization:</strong>
                                    <span>
                                        {structure.organization ? (
                                            <Link to={`/militaryOrganizations/show/${structure.organization._id}`} className="text-decoration-none text-primary">
                                                {structure.organization.armyName}
                                            </Link>
                                        ) : "Unknown"}
                                    </span>
                                </ListGroup.Item>
                                <ListGroup.Item className="d-flex justify-content-between align-items-center px-0">
                                    <strong>Branch:</strong> <span>{structure.armyBranchName || "-"}</span>
                                </ListGroup.Item>
                                <ListGroup.Item className="d-flex justify-content-between align-items-center px-0">
                                    <strong>Active From:</strong> <span>{structure.activeFromYear || "-"}</span>
                                </ListGroup.Item>
                                <ListGroup.Item className="d-flex justify-content-between align-items-center px-0">
                                    <strong>Active To:</strong> <span>{structure.activeToYear || "-"}</span>
                                </ListGroup.Item>
                                <ListGroup.Item className="d-flex justify-content-between align-items-center px-0">
                                    <strong>Notes:</strong> <span>{structure.notes || "-"}</span>
                                </ListGroup.Item>
                            </ListGroup>
                        </Card.Body>
                    </Card>
                </Col>

                {/* Banner */}
                <Col md={6} className="mb-4">
                    <Card className="shadow-lg border-0 rounded-4 h-100">
                        <Card.Header className="bg-primary text-white py-3 rounded-top-4">
                            <i className="fas fa-flag me-2"></i>Unit Banner
                        </Card.Header>
                        <Card.Body className="p-4 d-flex justify-content-center align-items-center">
                            {structure.bannerImageUrl ? (
                                <img
                                    src={structure.bannerImageUrl}
                                    alt={`${structure.unitName} banner`}
                                    style={{ maxWidth: "100%", height: "auto", maxHeight: "300px", borderRadius: "0.5rem" }}
                                    className="img-fluid"
                                />
                            ) : (
                                <div className="text-center text-muted">
                                    <i className="fas fa-image fa-3x mb-3"></i>
                                    <p>No banner image available.</p>
                                </div>
                            )}
                        </Card.Body>
                    </Card>
                </Col>
            </Row>

            {/* --- RANKS TABS --- */}
            <Row className="justify-content-center">
                <Col md={12}>
                    <h2 className="fw-bold text-dark mt-4 mb-3">Ranks for this Branch</h2>

                    <Tab.Container 
                        id="rank-tabs" 
                        activeKey={activeRank} 
                        onSelect={(k) => setActiveRank(k)}
                    >
                        <Row>
                            {/* Rank Nav */}
                            <Col md={3} className="mb-3">
                                <Nav variant="pills" className="flex-column p-3 bg-light rounded-3 shadow-sm border">
                                    {ranks.map((rank) => (
                                        <Nav.Item key={rank._id}>
                                            <Nav.Link eventKey={rank._id} className="text-start mb-1 fw-semibold">
                                                <i className="fas fa-medal me-2"></i> {rank.rankName}
                                            </Nav.Link>
                                        </Nav.Item>
                                    ))}
                                </Nav>
                            </Col>

                            {/* Rank Details */}
                            <Col md={9}>
                                <Tab.Content className="p-4 bg-white rounded-3 shadow border">
                                    {ranks.map((rank) => (
                                        <Tab.Pane key={rank._id} eventKey={rank._id}>
                                            <h4 className="fw-bold text-dark border-bottom pb-2 mb-3">
                                                <Link to={`/militaryRanks/show/${rank._id}`} className="text-decoration-none text-primary">
                                                    {rank.rankName}
                                                </Link>
                                            </h4>

                                            <p className="text-muted mb-4">{rank.rankDescription || "No description available."}</p>

                                            {rank.rankLevel && (
                                                <h5 className="fw-bold text-primary mb-3">Rank Level</h5>
                                            )}

                                            <Row className="g-3">
                                                <Col md={12}>
                                                    <Card className="h-100 border-0 border-start border-4 border-secondary-subtle">
                                                        <Card.Body className="p-3">
                                                            <h6 className="mb-1 text-dark">
                                                                <i className="fas fa-layer-group me-2 text-secondary"></i>
                                                                {rank.rankLevel?.description || rank.rankLevel || "Level info not available"}
                                                            </h6>
                                                        </Card.Body>
                                                    </Card>
                                                </Col>
                                            </Row>

                                            <p className="small fst-italic text-muted mt-4 mb-0 border-top pt-3">
                                                <i className="fas fa-info-circle me-1 text-primary"></i> Contextual Note: Additional rank details or historical notes can go here.
                                            </p>
                                        </Tab.Pane>
                                    ))}
                                </Tab.Content>
                            </Col>
                        </Row>
                    </Tab.Container>
                </Col>
            </Row>
        </Container>
    );
};

export default MilitaryStructureDetail;
