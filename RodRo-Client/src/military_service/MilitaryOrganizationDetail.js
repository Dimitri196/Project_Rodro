import React, { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import { Container, Row, Col, Card, ListGroup, Alert, Spinner } from "react-bootstrap";
import { apiGet } from "../utils/api";

const MilitaryOrganizationDetail = () => {
    const { id } = useParams();
    const [organization, setOrganization] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchOrganization = async () => {
            setLoading(true);
            setError(null);
            try {
                const data = await apiGet(`/api/militaryOrganizations/${id}`);
                setOrganization(data);
            } catch (err) {
                setError(`Failed to load military organization: ${err.message || err}`);
            } finally {
                setLoading(false);
            }
        };
        fetchOrganization();
    }, [id]);

    if (loading) {
        return (
            <Container className="my-5 text-center">
                <Spinner animation="border" role="status" />
                <p className="mt-3 text-muted">Loading organization details...</p>
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

    if (!organization) {
        return (
            <Container className="my-5">
                <Alert variant="info" className="text-center shadow-sm rounded-3">
                    <i className="fas fa-info-circle me-2"></i>No organization data found.
                </Alert>
            </Container>
        );
    }

    const { armyName, armyBranch, country, activeFromYear, activeToYear, organizationDescription, structures } = organization;

    return (
        <Container className="my-5 py-4 bg-light rounded shadow-lg">
            <link
                rel="stylesheet"
                href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"
                crossOrigin="anonymous"
                referrerPolicy="no-referrer"
            />

            {/* Header */}
            <Row className="mb-4 text-center">
                <Col>
                    <h1 className="display-4 fw-bold text-primary mb-2">
                        <i className="fas fa-landmark me-3"></i>{armyName}
                    </h1>
                    <p className="lead text-muted fst-italic">{armyBranch?.armyBranchName || "Military Branch"}</p>
                </Col>
            </Row>

            {/* Basic Info & Optional Image Placeholder */}
            <Row className="justify-content-center">
                <Col md={6} className="mb-4">
                    <Card className="shadow-lg border-0 rounded-4 h-100">
                        <Card.Header as="h5" className="bg-primary text-white py-3 rounded-top-4">
                            <i className="fas fa-info-circle me-2"></i>Basic Information
                        </Card.Header>
                        <Card.Body className="p-4">
                            <ListGroup variant="flush">
                                <ListGroup.Item><strong>Country:</strong>{" "}
                                    {country?._id ? (
                                        <Link to={`/countries/show/${country._id}`} className="text-decoration-none text-primary">
                                            {country.countryNameInPolish}
                                        </Link>
                                    ) : "Unknown"} </ListGroup.Item>
                                <ListGroup.Item><strong>Active From:</strong> {activeFromYear || "Unknown"}</ListGroup.Item>
                                <ListGroup.Item><strong>Active To:</strong> {activeToYear || "Still Active"}</ListGroup.Item>
                                <ListGroup.Item><strong>Description:</strong> {organizationDescription || "-"}</ListGroup.Item>
                            </ListGroup>
                        </Card.Body>
                    </Card>
                </Col>

                <Col md={6} className="mb-4">
                    <Card className="shadow-lg border-0 rounded-4 h-100">
                        <Card.Header as="h5" className="bg-primary text-white py-3 rounded-top-4">
                            <i className="fas fa-flag me-2"></i>Organization Image
                        </Card.Header>
                        <Card.Body className="p-4 d-flex justify-content-center align-items-center">
                            {organization.organizationImageUrl ? (
                                <img
                                    src={organization.organizationImageUrl}
                                    alt={`${organization.armyName} banner`}
                                    style={{ maxWidth: "100%", height: "auto", maxHeight: "300px", borderRadius: "0.5rem" }}
                                    className="img-fluid"
                                />
                            ) : (
                                <div className="text-center text-muted">
                                    <i className="fas fa-image fa-3x mb-3"></i>
                                    <p>No image available.</p>
                                </div>
                            )}
                        </Card.Body>
                    </Card>
                </Col>
            </Row>

            {/* Structures */}
            <Row className="justify-content-center">
                <Col md={12}>
                    <Card className="shadow-lg border-0 rounded-4 h-100">
                        <Card.Header as="h5" className="bg-secondary text-white py-3 rounded-top-4">
                            <i className="fas fa-sitemap me-2"></i>Military Structures
                        </Card.Header>
                        <Card.Body className="p-4">
                            {structures && structures.length > 0 ? (
                                <ListGroup variant="flush">
                                    {structures.map((structure) => (
                                        <ListGroup.Item key={structure._id || structure.id}>
                                            <Link to={`/militaryStructures/show/${structure._id || structure.id}`} className="text-decoration-none">
                                                <strong>{structure.unitName}</strong>
                                            </Link>{" "}
                                            {structure.unitType}
                                            {structure.activeFromYear && ` (${structure.activeFromYear}–${structure.activeToYear || "present"})`}
                                            {structure.notes && <div className="text-muted">{structure.notes}</div>}
                                        </ListGroup.Item>
                                    ))}
                                </ListGroup>
                            ) : (
                                <Alert variant="info" className="text-center mb-0">
                                    <i className="fas fa-info-circle me-2"></i>No structures found for this organization.
                                </Alert>
                            )}
                        </Card.Body>
                    </Card>
                </Col>
            </Row>

            {/* Back Button */}
            <div className="mt-4">
                <Link to="/militaryOrganizations" className="btn btn-secondary me-2">
                    <i className="fas fa-arrow-left me-2"></i>Back to Organizations
                </Link>
            </div>
        </Container>
    );
};

export default MilitaryOrganizationDetail;
