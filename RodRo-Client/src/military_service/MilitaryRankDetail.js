import React, { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import { 
    Container, Row, Col, Card, Alert, Spinner,
    ListGroup, Badge
} from "react-bootstrap";
import { apiGet } from "../utils/api";
import "@fortawesome/fontawesome-free/css/all.min.css";

const MilitaryRankDetail = () => {
    const { id } = useParams(); 
    const [rank, setRank] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            setLoading(true);
            setError(null);
            try {
                // Fetch the MilitaryRankDTO
                const rankData = await apiGet(`/api/militaryRanks/${id}`);
                setRank(rankData);

            } catch (err) {
                console.error("Error fetching military rank:", err);
                setError(`Failed to load military rank details for ID ${id}.`);
            } finally {
                setLoading(false);
            }
        };

        fetchData();
    }, [id]);

    // --- Render Helpers ---

    const renderLevelBadge = (level) => {
        const variant = {
            OFFICER: "primary",
            NCO: "success", // Non-Commissioned Officer
            ENLISTED: "info",
            GENERAL: "danger",
            WARRANT: "warning"
        }[level] || "secondary";

        return <Badge bg={variant} className="fw-semibold">{level || "N/A"}</Badge>;
    };

    if (loading) return (
        <Container className="my-5 text-center">
            <Spinner animation="border" role="status" variant="primary" />
            <p className="mt-3 text-muted">Loading military rank details...</p>
        </Container>
    );

    if (error) return (
        <Container className="my-5">
            <Alert variant="danger" className="text-center shadow-sm rounded-3">
                <i className="fas fa-exclamation-triangle me-2"></i>{error}
            </Alert>
        </Container>
    );

    if (!rank) return (
        <Container className="my-5">
            <Alert variant="info" className="text-center shadow-sm rounded-3">
                <i className="fas fa-info-circle me-2"></i>Military rank not found.
            </Alert>
        </Container>
    );

    // ✅ Accessing properties from the flat DTO
    const rankName = rank.name || "Unnamed Rank";
    const rankDescription = rank.description || "No detailed description available.";
    const activePeriod = `${rank.activeFromYear || "?"} – ${rank.activeToYear || "Present"}`;
    const organizationLink = rank.organizationId ? `/militaryOrganizations/show/${rank.organizationId}` : null;
    const structureLink = rank.structureId ? `/militaryStructures/show/${rank.structureId}` : null;


    return (
        <Container className="my-5 py-4 bg-white rounded shadow-lg">
            <Row className="mb-4 border-bottom pb-3">
                <Col md={12} className="text-center">
                    <h1 className="display-4 fw-bold text-dark mb-2">
                        <i className="fas fa-medal me-3 text-primary"></i>{rankName}
                    </h1>
                    <div className="d-flex justify-content-center align-items-center mb-3">
                        <p className="lead text-muted fst-italic mb-0 me-3">Rank Level:</p>
                        {renderLevelBadge(rank.rankLevel)}
                    </div>
                    <p className="text-secondary fst-italic">{rankDescription}</p>
                </Col>
            </Row>

            <Row className="justify-content-center mb-4">
                {/* Basic Details & Context */}
                <Col md={6} className="mb-4">
                    <Card className="shadow-sm border-0 h-100">
                        <Card.Header className="bg-light fw-bold">
                            <i className="fas fa-info-circle me-2 text-primary"></i>Contextual Details
                        </Card.Header>
                        <Card.Body className="p-4">
                            <ListGroup variant="flush">
                                <ListGroup.Item className="d-flex justify-content-between align-items-center px-0">
                                    <strong>Active Period:</strong> <span>{activePeriod}</span>
                                </ListGroup.Item>
                                
                                {/* ✅ FIX: Using flat DTO properties organizationName and organizationId */}
                                <ListGroup.Item className="d-flex justify-content-between align-items-center px-0">
                                    <strong>Organization:</strong>
                                    <span>
                                        {organizationLink && rank.organizationName ? (
                                            <Link to={organizationLink} className="text-decoration-none text-primary fw-semibold">
                                                {rank.organizationName}
                                            </Link>
                                        ) : <i className="text-muted">N/A</i>}
                                    </span>
                                </ListGroup.Item>
                                
                                {/* ✅ FIX: Using flat DTO properties structureName and structureId */}
                                <ListGroup.Item className="d-flex justify-content-between align-items-center px-0">
                                    <strong>Specific Structure:</strong>
                                    <span>
                                        {structureLink && rank.structureName ? (
                                            <Link to={structureLink} className="text-decoration-none text-info fw-semibold">
                                                {rank.structureName}
                                            </Link>
                                        ) : <i className="text-muted">N/A</i>}
                                    </span>
                                </ListGroup.Item>
                                
                                <ListGroup.Item className="px-0">
                                    <strong>Notes:</strong> 
                                    <p className="text-muted mb-0 mt-1 fst-italic small">{rank.notes || "No additional remarks."}</p>
                                </ListGroup.Item>
                            </ListGroup>
                        </Card.Body>
                    </Card>
                </Col>

                {/* Insignia Image */}
                <Col md={6} className="mb-4">
                    <Card className="shadow-sm border-0 h-100">
                        <Card.Header className="bg-light fw-bold">
                            <i className="fas fa-image me-2 text-primary"></i>Insignia
                        </Card.Header>
                        <Card.Body className="p-4 d-flex justify-content-center align-items-center">
                            {rank.insigniaImageUrl ? (
                                <img
                                    src={rank.insigniaImageUrl}
                                    alt={`${rankName} insignia`}
                                    style={{ maxWidth: "100%", height: "auto", maxHeight: "250px", objectFit: 'contain', borderRadius: "0.5rem" }}
                                    className="img-fluid border p-2"
                                />
                            ) : (
                                <div className="text-center text-muted py-5">
                                    <i className="fas fa-splotch fa-3x mb-3"></i>
                                    <p>No insignia image available.</p>
                                </div>
                            )}
                        </Card.Body>
                    </Card>
                </Col>
            </Row>

            {/* Associated Persons (Military Service Records) */}
            <Row className="mt-4">
                <Col md={12}>
                    <Card className="shadow-lg border-0 rounded-4">
                        <Card.Header className="bg-primary text-white py-3 rounded-top-4">
                            <i className="fas fa-users me-2"></i>
                            Associated Personnel ({rank.persons?.length || 0})
                        </Card.Header>
                        <Card.Body className="p-0">
                            {rank.persons && rank.persons.length > 0 ? (
                                <ListGroup variant="flush">
                                    {rank.persons.map((personService) => ( 
                                        <ListGroup.Item key={personService.id || personService._id} className="d-flex justify-content-between align-items-center">
                                            <span>
                                                <i className="fas fa-user-circle me-2 text-secondary"></i>
                                                {/* ✅ FIX: Use combined personGivenName and personSurname for display */}
                                                <Link to={`/persons/show/${personService.personId}`} className="fw-semibold text-dark text-decoration-none">
                                                    {personService.personGivenName} {personService.personSurname}
                                                </Link>
                                                {/* Display the Military Structure Name from the service record for context */}
                                                <span className="ms-3 text-muted small">
                                                    (Unit: {personService.militaryStructureName || personService.militaryStructure?.unitName || 'N/A'})
                                                </span>
                                            </span>
                                            {/* Display service years and notes clearly */}
                                            <div className="d-flex flex-column align-items-end small">
                                                <span className="text-success fw-semibold">
                                                    {personService.enlistmentYear} - {personService.dischargeYear || 'Present'}
                                                </span>
                                                <span className="text-muted fst-italic">Notes: {personService.notes || '-'}</span>
                                            </div>
                                        </ListGroup.Item>
                                    ))}
                                </ListGroup>
                            ) : (
                                <div className="p-4 text-center text-muted">
                                    <i className="fas fa-clipboard-list fa-2x mb-2"></i>
                                    <p>No military service records currently linked to this rank.</p>
                                </div>
                            )}
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default MilitaryRankDetail;