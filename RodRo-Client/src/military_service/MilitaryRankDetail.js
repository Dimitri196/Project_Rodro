import React, { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import { Container, Row, Col, Card, ListGroup, Alert, Spinner } from "react-bootstrap";
import { apiGet } from "../utils/api";

const MilitaryRankDetail = () => {
    const { id } = useParams();
    const [rank, setRank] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchRank = async () => {
            setLoading(true);
            setError(null);
            try {
                const data = await apiGet(`/api/militaryRanks/${id}`);
                setRank(data);
            } catch (err) {
                console.error("Error fetching military rank details:", err);
                setError(`Failed to load military rank details: ${err.message || err}`);
            } finally {
                setLoading(false);
            }
        };
        fetchRank();
    }, [id]);

    if (loading) {
        return (
            <Container className="my-5 text-center">
                <Spinner animation="border" role="status">
                    <span className="visually-hidden">Loading...</span>
                </Spinner>
                <p className="mt-3 text-muted">Loading rank details...</p>
            </Container>
        );
    }

    if (error) {
        return (
            <Container className="my-5">
                <Alert variant="danger" className="text-center shadow-sm rounded-3">
                    <i className="fas fa-exclamation-triangle me-2"></i>{error}
                </Alert>
            </Container>
        );
    }

    if (!rank) {
        return (
            <Container className="my-5">
                <Alert variant="info" className="text-center shadow-sm rounded-3">
                    <i className="fas fa-info-circle me-2"></i>Military rank not found.
                </Alert>
            </Container>
        );
    }

    const {
        rankName,
        rankLevel,
        rankDescription,
        activeFromYear,
        activeToYear,
        notes,
        rankImageUrl,
        militaryOrganization,
        militaryStructureDTO,
        persons
    } = rank;

    return (
        <Container className="my-5 py-4 bg-light rounded shadow-lg">
            <link
                rel="stylesheet"
                href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"
                crossOrigin="anonymous"
                referrerPolicy="no-referrer"
            />

            {/* Title */}
            <Row className="mb-4 text-center">
                <Col>
                    <h1 className="display-4 fw-bold text-primary mb-3">
                        <i className="fas fa-medal me-3"></i>{rankName}
                    </h1>
                    <p className="lead text-muted fst-italic">{rankLevel || "Military Rank"}</p>
                </Col>
            </Row>

            {/* Basic Info & Image (two-column layout) */}
            <Row className="justify-content-center">
                {/* Left: Basic Info */}
                <Col md={6} className="mb-4">
                    <Card className="shadow-lg border-0 rounded-4 h-100">
                        <Card.Header as="h5" className="bg-primary text-white py-3 rounded-top-4">
                            <i className="fas fa-info-circle me-2"></i>Basic Information
                        </Card.Header>
                        <Card.Body className="p-4">
                            <ListGroup variant="flush">
                                <ListGroup.Item><strong>Description:</strong> {rankDescription || "-"}</ListGroup.Item>
                                <ListGroup.Item><strong>Level:</strong> {rankLevel || "-"}</ListGroup.Item>
                                <ListGroup.Item><strong>Active From:</strong> {activeFromYear || "-"}</ListGroup.Item>
                                <ListGroup.Item><strong>Active To:</strong> {activeToYear || "-"}</ListGroup.Item>
                                <ListGroup.Item><strong>Notes:</strong> {notes || "-"}</ListGroup.Item>
                            </ListGroup>
                        </Card.Body>
                    </Card>
                </Col>

                {/* Right: Image */}
                <Col md={6} className="mb-4">
                    <Card className="shadow-lg border-0 rounded-4 h-100">
                        <Card.Header as="h5" className="bg-primary text-white py-3 rounded-top-4">
                            <i className="fas fa-flag me-2"></i>Rank Insignia
                        </Card.Header>
                        <Card.Body className="p-4 d-flex justify-content-center align-items-center">
                            {rank.rankImageUrl ? (
                                <img
                                    src={rank.rankImageUrl}
                                    alt={`${rank.rankName} banner`}
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

            {/* Associated Units */}
            <Row className="justify-content-center">
                <Col md={12} className="mb-4">
                    <Card className="shadow-lg border-0 rounded-4 h-100">
                        <Card.Header as="h5" className="bg-secondary text-white py-3 rounded-top-4">
                            <i className="fas fa-sitemap me-2"></i>Associated Units
                        </Card.Header>
                        <Card.Body className="p-4">
                            <ListGroup variant="flush">
                                <ListGroup.Item>
                                    <strong>Military Organization:</strong>{" "}
                                    {militaryOrganization?._id ? (
                                        <Link
                                            to={`/militaryOrganizations/show/${militaryOrganization._id}`}
                                            className="text-decoration-none text-primary"
                                        >
                                            {militaryOrganization.armyName}
                                        </Link>
                                    ) : "Unknown"}
                                </ListGroup.Item>
                                <ListGroup.Item>
                                    <strong>Military Structure:</strong>{" "}
                                    {militaryStructureDTO?._id ? (
                                        <Link
                                            to={`/militaryStructures/show/${militaryStructureDTO._id}`}
                                            className="text-decoration-none text-primary"
                                        >
                                            {militaryStructureDTO.unitName}
                                        </Link>
                                    ) : "Unknown"}
                                </ListGroup.Item>
                            </ListGroup>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>

            {/* Persons */}
            <Row className="justify-content-center">
                <Col md={12} className="mb-4">
                    <Card className="shadow-lg border-0 rounded-4 h-100">
                        <Card.Header as="h5" className="bg-secondary text-white py-3 rounded-top-4">
                            <i className="fas fa-users me-2"></i>Persons Holding This Rank ({persons?.length || 0})
                        </Card.Header>
                        <Card.Body className="p-4">
                            {!persons || persons.length === 0 ? (
                                <Alert variant="info" className="text-center mt-3">
                                    <i className="fas fa-info-circle me-2"></i>No persons found.
                                </Alert>
                            ) : (
                                <Row xs={1} md={2} lg={3} className="g-4">
                                    {persons.map((person) => (
                                        <Col key={person.personId}>
                                            <Card className="shadow-sm h-100">
                                                <Card.Body>
                                                    <Card.Title className="text-info">
                                                        <Link to={`/persons/show/${person.personId}`} className="text-decoration-none">
                                                            {person.givenName} {person.givenSurname}
                                                        </Link>
                                                    </Card.Title>
                                                    <ListGroup variant="flush">
                                                        <ListGroup.Item><strong>Enlistment Year:</strong> {person.enlistmentYear || "-"}</ListGroup.Item>
                                                        <ListGroup.Item><strong>Discharge Year:</strong> {person.dischargeYear || "-"}</ListGroup.Item>
                                                        <ListGroup.Item><strong>Notes:</strong> {person.notes || "-"}</ListGroup.Item>
                                                    </ListGroup>
                                                </Card.Body>
                                            </Card>
                                        </Col>
                                    ))}
                                </Row>
                            )}
                        </Card.Body>
                    </Card>
                </Col>
            </Row>

            {/* Back button */}
            <div className="mt-4">
                <Link to="/militaryRanks" className="btn btn-secondary me-2">
                    <i className="fas fa-arrow-left me-2"></i>Back to Ranks
                </Link>
            </div>
        </Container>
    );
};

export default MilitaryRankDetail;
