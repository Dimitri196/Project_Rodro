import React, { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import {
    Container, Row, Col, Card, ListGroup, Alert, Spinner
} from "react-bootstrap";
import { apiGet } from "../utils/api";
import { useSession } from "../contexts/session";
import "@fortawesome/fontawesome-free/css/all.min.css";

const MilitaryOrganizationDetail = () => {
    const { id } = useParams();
    const { session } = useSession();
    const isAdmin = session.data?.isAdmin === true;

    const [organization, setOrganization] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            setLoading(true);
            setError(null);
            try {
                const data = await apiGet(`/api/militaryOrganizations/${id}`);
                setOrganization(data);
            } catch (err) {
                console.error("Error fetching military organization:", err);
                setError("Data Retrieval Error: Could not fetch military organization record.");
            } finally {
                setLoading(false);
            }
        };
        fetchData();
    }, [id]);

    if (loading) {
        return (
            <Container className="my-5 text-center py-5">
                <Spinner animation="border" variant="primary" />
                <p className="mt-3 text-muted">Awaiting data retrieval for Military Organization {id}...</p>
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
                    <i className="fas fa-info-circle me-2"></i>Military organization record not found.
                </Alert>
            </Container>
        );
    }

    const {
        armyName, armyBranch, country,
        activeFromYear, activeToYear,
        organizationDescription, structures, organizationImageUrl
    } = organization;

    return (
        <Container className="my-5 py-3">

            {/* --- HEADER --- */}
            <Row className="mb-5 border-bottom pb-4 align-items-center">
                <Col md={9}>
                    <h1 className="display-4 fw-bold text-dark mb-1">
                        {armyName || "Unnamed Organization"}
                    </h1>
                    <h2 className="h4 text-secondary mb-0 fw-normal">
                        <i className="fas fa-shield-alt me-2 text-muted"></i>
                        Branch: {armyBranch?.armyBranchName || "Unspecified"}
                    </h2>
                </Col>
                <Col md={3} className="d-flex justify-content-end">
                    {isAdmin && (
                        <Link to={`/militaryOrganizations/edit/${organization._id}`} className="btn btn-warning btn-lg rounded-pill px-4 py-2 shadow-sm fw-semibold">
                            <i className="fas fa-edit me-2"></i>Modify Record
                        </Link>
                    )}
                </Col>
            </Row>

            <Row className="justify-content-center g-4">

                {/* --- LEFT COLUMN: CORE METADATA & DESCRIPTION --- */}
                <Col md={6}>
                    <Card className="shadow-lg border-0 rounded-4 h-100">
                        <Card.Header as="h5" className="bg-dark text-white py-3 rounded-top-4">
                            <i className="fas fa-info-circle me-2"></i>Core Metadata & Description
                        </Card.Header>
                        <Card.Body>
                            <div className="mb-4">
                                <strong className="d-block mb-2 text-muted">Country:</strong>
                                {country?._id ? (
                                    <Link to={`/countries/show/${country._id}`} className="text-decoration-none text-primary fw-semibold">
                                        {country.countryNameInPolish}
                                    </Link>
                                ) : <span className="text-secondary">Unknown</span>}
                            </div>

                            <div className="mb-4">
                                <strong className="d-block mb-2 text-muted">Description:</strong>
                                <div className="p-3 bg-light border-start border-3 border-primary rounded shadow-sm">
                                    <p className="mb-0 small text-dark" style={{ whiteSpace: "pre-line" }}>
                                        {organizationDescription || <em className="text-danger">[DATA GAP] No description available.</em>}
                                    </p>
                                </div>
                            </div>
                        </Card.Body>
                    </Card>
                </Col>

                {/* --- RIGHT COLUMN: CHRONOLOGY & IMAGE --- */}
                <Col md={6}>

                    <Card className="shadow-lg border-0 rounded-4 mb-4">
                        <Card.Header as="h5" className="bg-primary text-white py-3 rounded-top-4">
                            <i className="fas fa-history me-2"></i>Temporal Information
                        </Card.Header>
                        <Card.Body>
                            <ListGroup variant="flush">
                                <ListGroup.Item className="d-flex justify-content-between align-items-center px-0 fw-semibold">
                                    <span className="text-dark"><i className="fas fa-calendar-alt me-2 text-success"></i>Active From:</span>
                                    <span className="fs-5 text-success">{activeFromYear || <span className="text-danger">Unknown</span>}</span>
                                </ListGroup.Item>
                                <ListGroup.Item className="d-flex justify-content-between align-items-center px-0 fw-semibold">
                                    <span className="text-dark"><i className="fas fa-times-circle me-2 text-danger"></i>Active To:</span>
                                    <span className="fs-5 text-danger">
                                        {activeToYear ? activeToYear : <em className="text-primary fw-bold">Still Active</em>}
                                    </span>
                                </ListGroup.Item>
                            </ListGroup>
                        </Card.Body>
                    </Card>

                    <Card className="shadow-lg border-0 rounded-4">
                        <Card.Header as="h5" className="bg-secondary text-white py-3 rounded-top-4">
                            <i className="fas fa-flag me-2"></i>Organization Image
                        </Card.Header>
                        <Card.Body className="p-4 d-flex flex-column justify-content-center align-items-center">
                            {organizationImageUrl ? (
                                <img
                                    src={organizationImageUrl}
                                    alt={`${armyName} banner`}
                                    style={{ width: "100%", height: "auto", maxHeight: "350px", objectFit: 'contain', borderRadius: "0.5rem" }}
                                    className="img-fluid"
                                />
                            ) : (
                                <div className="text-center text-muted p-5 bg-light w-100 rounded">
                                    <i className="fas fa-image fa-3x mb-3"></i>
                                    <p className="mb-0 fw-semibold">No image available</p>
                                </div>
                            )}
                        </Card.Body>
                    </Card>
                </Col>
            </Row>

            {/* --- Structures --- */}
            <Row className="justify-content-center mt-4">
                <Col md={12}>
                    <Card className="shadow-lg border-0 rounded-4">
                        <Card.Header as="h5" className="bg-secondary text-white py-3 rounded-top-4">
                            <i className="fas fa-sitemap me-2"></i>Sub-units / Structures
                        </Card.Header>
                        <Card.Body>
                            {structures && structures.length > 0 ? (
                                <ListGroup variant="flush">
                                    {structures.map((s, i) => (
                                        <ListGroup.Item key={s._id || s.id}>
                                            <Link to={`/militaryStructures/show/${s._id || s.id}`} className="text-decoration-none fw-semibold">
                                                {s.unitName}
                                            </Link>{" "}
                                            {s.unitType}
                                            {s.activeFromYear && ` (${s.activeFromYear}–${s.activeToYear || "present"})`}
                                            {s.notes && <div className="text-muted small">{s.notes}</div>}
                                        </ListGroup.Item>
                                    ))}
                                </ListGroup>
                            ) : (
                                <Alert variant="info" className="text-center mb-0">
                                    <i className="fas fa-info-circle me-2"></i>No sub-units recorded for this organization.
                                </Alert>
                            )}
                        </Card.Body>
                    </Card>
                </Col>
            </Row>

            <div className="mt-5 pt-3 border-top text-center">
                <Link to="/militaryOrganizations" className="btn btn-outline-secondary rounded-pill px-4">
                    <i className="fas fa-arrow-left me-2"></i>Return to Organizations Index
                </Link>
            </div>

        </Container>
    );
};

export default MilitaryOrganizationDetail;
