import React, { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import {
    Container, Row, Col, Card, ListGroup, Alert, Spinner,
    // Importing Badge for Confession type
    Badge
} from "react-bootstrap";
import { apiGet } from "../utils/api";
import { useSession } from "../contexts/session";
import "@fortawesome/fontawesome-free/css/all.min.css";

const ParishDetail = () => {
    const { id } = useParams();
    const { session } = useSession();
    const isAdmin = session.data?.isAdmin === true;

    const [parish, setParish] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    // Helper to render the Confession pill/badge
    const renderConfessionBadge = (confession) => {
        if (!confession) return <Badge bg="secondary">Unknown</Badge>;

        // Mapped colors must match the scheme used across ParishIndex and ParishTable
        const variant = {
            'CATHOLIC_LATIN': 'primary', // Blue
            'CATHOLIC_UNIATE': 'info',    // Light Blue/Cyan
            'ORTHODOX': 'warning',        // Yellow/Orange (ensures consistency with the table view)
            'JEWISH': 'dark',             // Dark Gray/Black
            'OTHER': 'secondary'          // Gray
        }[confession] || 'secondary';

        const label = confession.replace('_', ' ');

        return (
            <Badge bg={variant} className="fw-bold fs-6 p-2 shadow-sm">
                <i className="fas fa-certificate me-2"></i>{label}
            </Badge>
        );
    };

    useEffect(() => {
        const fetchData = async () => {
            setLoading(true);
            setError(null);
            try {
                // Assuming the API returns a structure where `location` is the primary linked location
                const parishData = await apiGet(`/api/parishes/${id}`);
                setParish(parishData);
            } catch (err) {
                console.error("Error fetching parish:", err);
                setError("Data Retrieval Error: Could not fetch ecclesiastical record.");
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
                <p className="mt-3 text-muted">Awaiting data retrieval for Jurisdiction {id}...</p>
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

    if (!parish) {
        return (
            <Container className="my-5">
                <Alert variant="info" className="text-center shadow-sm rounded-3">
                    <i className="fas fa-info-circle me-2"></i>Ecclesiastical Jurisdiction record not found.
                </Alert>
            </Container>
        );
    }

    return (
        <Container className="my-5 py-3">

            {/* --- HEADER BLOCK: JURISDICTION TITLE & ACTIONS --- */}
            <Row className="mb-5 border-bottom pb-4 align-items-center">
                <Col md={9}>
                    <h1 className="display-4 fw-bold text-dark mb-1">
                        {parish.name || "Unnamed Jurisdiction"}
                    </h1>
                    <h2 className="h4 text-secondary mb-0 fw-normal">
                        <i className="fas fa-map-marker-alt me-2 text-muted"></i>
                        Primary Church: **{parish.mainChurchName || "Unspecified"}**
                    </h2>
                </Col>
                <Col md={3} className="d-flex justify-content-end">
                    {isAdmin && (
                        <Link to={`/parishes/edit/${parish._id}`} className="btn btn-warning btn-lg rounded-pill px-4 py-2 shadow-sm fw-semibold">
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
                            <i className="fas fa-sitemap me-2"></i>Core Metadata & Classification
                        </Card.Header>
                        <Card.Body className="p-4">

                            {/* Confession Badge */}
                            <div className="mb-4">
                                <strong className="d-block mb-2 text-muted">Confessional Type:</strong>
                                {renderConfessionBadge(parish.confession)}
                            </div>

                            {/* Description Panel */}
                            <div className="mb-4">
                                <strong className="d-block mb-2 text-muted">
                                    <i className="fas fa-align-left me-2"></i>Scholarly Description:
                                </strong>
                                <div className="p-3 bg-light border-start border-3 border-primary rounded shadow-sm">
                                    <p className="mb-0 small text-dark" style={{ whiteSpace: "pre-line" }}>
                                        {parish.description || <em className="text-danger">[DATA GAP] Comprehensive description pending archival confirmation.</em>}
                                    </p>
                                </div>
                            </div>

                            {/* Associated Locations Table/List */}
                            <div>
                                <strong className="d-block mb-2 text-muted">
                                    <i className="fas fa-link me-2"></i>Associated Geospatial Nodes: ({parish.locations?.length || 0})
                                </strong>
                                <ListGroup variant="flush" className="border rounded">
                                    {parish.locations && parish.locations.length > 0 ? (
                                        parish.locations.map((pl, index) => (
                                            <ListGroup.Item key={index} className="px-3 small d-flex justify-content-between align-items-center">
                                                <i className="fas fa-dot-circle text-secondary me-2"></i>
                                                <Link
                                                    to={`/locations/show/${pl.locationId || pl.location?._id}`} // Flexible ID check
                                                    className="text-decoration-none text-primary fw-semibold"
                                                >
                                                    {pl.locationName || pl.location?.locationName || `Unnamed Node ${index + 1}`}
                                                </Link>
                                                <Badge bg="light" text="dark" className="border ms-auto">
                                                    Linkage ID: {pl.id ? pl.id.toString() : pl._id?.toString() || 'N/A'}
                                                </Badge>
                                            </ListGroup.Item>
                                        ))
                                    ) : (
                                        <ListGroup.Item className="text-center text-muted small py-3">
                                            <i className="fas fa-exclamation-triangle me-1"></i> No confirmed geospatial linkages.
                                        </ListGroup.Item>
                                    )}
                                </ListGroup>
                            </div>

                        </Card.Body>
                    </Card>
                </Col>

                {/* --- RIGHT COLUMN: VISUAL AND CHRONOLOGY --- */}
                <Col md={6}>

                    {/* Panel 1: Chronological Data */}
                    <Card className="shadow-lg border-0 rounded-4 mb-4">
                        <Card.Header as="h5" className="bg-primary text-white py-3 rounded-top-4">
                            <i className="fas fa-history me-2"></i>Temporal & Chronological Data
                        </Card.Header>
                        <Card.Body className="p-4">
                            <ListGroup variant="flush">
                                <ListGroup.Item className="d-flex justify-content-between align-items-center px-0 fw-semibold">
                                    <span className="text-dark"><i className="fas fa-calendar-alt me-2 text-success"></i>Date of Establishment:</span>
                                    <span className="fs-5 text-success">{parish.establishmentYear || <span className="text-danger">**[UNKNOWN]**</span>}</span>
                                </ListGroup.Item>
                                <ListGroup.Item className="d-flex justify-content-between align-items-center px-0 fw-semibold">
                                    <span className="text-dark"><i className="fas fa-times-circle me-2 text-danger"></i>Date of Cancellation:</span>
                                    <span className="fs-5 text-danger">
                                        {parish.cancellationYear ? parish.cancellationYear : <em className="text-primary fw-bold">Active Jurisdiction</em>}
                                    </span>
                                </ListGroup.Item>
                            </ListGroup>
                        </Card.Body>
                    </Card>


                    {/* Panel 2: Visual Evidence */}
                    <Card className="shadow-lg border-0 rounded-4">
                        {/* No h-50 class, as fixed in previous step */}
                        <Card.Header as="h5" className="bg-secondary text-white py-3 rounded-top-4">
                            <i className="fas fa-camera me-2"></i>Visual Evidence (Primary Church)
                        </Card.Header>
                        <Card.Body className="p-4 d-flex flex-column justify-content-center align-items-center">
                            {parish.churchImageUrl ? (
                                <img
                                    src={parish.churchImageUrl}
                                    alt={`${parish.name} - ${parish.mainChurchName}`}
                                    style={{ width: "100%", height: "auto", maxHeight: "350px", objectFit: 'contain', borderRadius: "0.5rem" }}
                                    // REMOVED redundant 'border shadow-sm' classes
                                    className="img-fluid"
                                />
                            ) : (
                                <div
                                    className="text-center text-muted p-5 bg-light w-100 rounded"
                                // CHANGED to a simple rounded background for the placeholder
                                >
                                    <i className="fas fa-image fa-3x mb-3"></i>
                                    <p className="mb-0 fw-semibold">Image Source: [DATA GAP]</p>
                                    <p className="small mb-0">No primary visual evidence URL available for this record.</p>
                                </div>
                            )}
                        </Card.Body>
                    </Card>



                </Col>
            </Row>

            <div className="mt-5 pt-3 border-top text-center">
                <Link to="/parishes" className="btn btn-outline-secondary rounded-pill px-4">
                    <i className="fas fa-arrow-left me-2"></i>Return to Jurisdictions Index
                </Link>
            </div>

        </Container>
    );
};

export default ParishDetail;
