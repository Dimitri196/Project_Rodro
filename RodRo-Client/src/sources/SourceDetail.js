import React, { useEffect, useState } from "react";
import { useParams, Link, useNavigate } from "react-router-dom";
import { 
    Container, Row, Col, Card, ListGroup, Alert, Spinner, Button,
    // Importing Badge for Source type
    Badge
} from "react-bootstrap";
import { apiGet } from "../utils/api";
import { useSession } from "../contexts/session";
import "@fortawesome/fontawesome-free/css/all.min.css"; 

// IMPORTANT: Import the full map from your constants file
import { SOURCE_TYPE_MAP } from "../constants/sourceType"; 

const SourceDetail = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const { session } = useSession();
    const isAdmin = session?.data?.isAdmin === true;

    const [source, setSource] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    // 🎯 EXPANDED Helper to render the Source Type pill/badge with color and icon scheme
    const renderSourceTypeBadge = (enumKey) => {
        if (!enumKey) return <Badge bg="secondary">Unknown</Badge>;

        // 1. Get the human-readable label from the imported map
        const label = SOURCE_TYPE_MAP[enumKey] || enumKey.replace(/_/g, ' ');

        // 2. Define comprehensive mapping for variants (colors) and icons
        const variantMap = {
            ARCHIVAL_DOCUMENT: 'primary',
            CHURCH_RECORD: 'success',
            CIVIL_REGISTRY: 'info',
            CENSUS: 'warning',
            MILITARY_RECORD: 'danger',
            TAX_RECORD: 'secondary',
            NEWSPAPER: 'dark',
            BOOK: 'primary',
            ORAL_HISTORY: 'secondary',
            PHOTOGRAPH: 'info',
            MAP: 'danger',
            LEGAL_DOCUMENT: 'success',
            PERSONAL_CORRESPONDENCE: 'warning',
            DATABASE: 'dark',
            WEBSITE: 'info',
            UNKNOWN: 'secondary',
            NOT_APPLICABLE: 'light',
        };

        const iconMap = {
            ARCHIVAL_DOCUMENT: 'fas fa-archive',
            CHURCH_RECORD: 'fas fa-church',
            CIVIL_REGISTRY: 'fas fa-gavel',
            CENSUS: 'fas fa-users',
            MILITARY_RECORD: 'fas fa-shield-alt',
            TAX_RECORD: 'fas fa-file-invoice-dollar',
            NEWSPAPER: 'fas fa-newspaper',
            BOOK: 'fas fa-book-open',
            ORAL_HISTORY: 'fas fa-microphone-alt',
            PHOTOGRAPH: 'fas fa-camera',
            MAP: 'fas fa-map',
            LEGAL_DOCUMENT: 'fas fa-balance-scale',
            PERSONAL_CORRESPONDENCE: 'fas fa-envelope-open-text',
            DATABASE: 'fas fa-database',
            WEBSITE: 'fas fa-globe',
            UNKNOWN: 'fas fa-question',
            NOT_APPLICABLE: 'fas fa-minus-circle',
        };

        const variant = variantMap[enumKey] || 'secondary';
        const icon = iconMap[enumKey] || 'fas fa-certificate';

        return (
            <Badge bg={variant} className="fw-bold fs-6 p-2 shadow-sm">
                <i className={`${icon} me-2`}></i>{label}
            </Badge>
        );
    };

    useEffect(() => {
        const fetchData = async () => {
            setLoading(true);
            setError(null);
            try {
                // Assuming Source Detail endpoint returns the full DTO, including the ENUM key (e.g., "ARCHIVAL_DOCUMENT")
                const sourceData = await apiGet(`/api/sources/${id}`);
                setSource(sourceData);
            } catch (err) {
                console.error("Error fetching source:", err);
                setError(`Data Retrieval Error: Could not fetch source record: ${err.message || err}`);
            } finally {
                setLoading(false);
            }
        };

        fetchData();
    }, [id]);

    if (loading) {
        return (
            <Container className="my-5 text-center py-5">
                <Spinner animation="border" variant="success" />
                <p className="mt-3 text-muted">Awaiting data retrieval for Source {id}...</p>
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

    if (!source) {
        return (
            <Container className="my-5">
                <Alert variant="info" className="text-center shadow-sm rounded-3">
                    <i className="fas fa-info-circle me-2"></i>Source record not found.
                </Alert>
            </Container>
        );
    }

    // Helper function to format date/year strings (No change)
    const formatYear = (year) => year || <span className="text-danger">**[UNKNOWN]**</span>;
    const formatYearRange = (start, end) => {
        if (!start && !end) return "N/A";
        return `${start || "?"} – ${end || "?"}`;
    };

    return (
        <Container className="my-5 py-3">

            {/* --- HEADER BLOCK: SOURCE TITLE & ACTIONS --- */}
            <Row className="mb-5 border-bottom pb-4 align-items-center">
                <Col md={9}>
                    <h1 className="display-4 fw-bold text-dark mb-1">
                        {source.title || "Unnamed Source Record"}
                    </h1>
                    <h2 className="h4 text-secondary mb-0 fw-normal">
                        <i className="fas fa-barcode me-2 text-muted"></i>
                        Reference ID: **{source.reference || "N/A"}**
                    </h2>
                </Col>
                <Col md={3} className="d-flex justify-content-end">
                    {isAdmin && (
                        <Link to={`/sources/edit/${source.id || id}`} className="btn btn-warning btn-lg rounded-pill px-4 py-2 shadow-sm fw-semibold">
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
                            <i className="fas fa-sitemap me-2"></i>Core Metadata & Provenance
                        </Card.Header>
                        <Card.Body className="p-4">

                            {/* Source Type Badge */}
                            <div className="mb-4">
                                <strong className="d-block mb-2 text-muted">Source Classification:</strong>
                                {renderSourceTypeBadge(source.type)}
                            </div>

                            {/* Description Panel */}
                            <div className="mb-4">
                                <strong className="d-block mb-2 text-muted">
                                    <i className="fas fa-align-left me-2"></i>Scholarly Description:
                                </strong>
                                <div className="p-3 bg-light border-start border-3 border-success rounded shadow-sm">
                                    <p className="mb-0 small text-dark" style={{ whiteSpace: "pre-line" }}>
                                        {source.description || <em className="text-danger">[DATA GAP] Comprehensive description pending.</em>}
                                    </p>
                                </div>
                            </div>

                            {/* External URL Link */}
                            <div className="mb-0">
                                <strong className="d-block mb-2 text-muted">
                                    <i className="fas fa-external-link-alt me-2"></i>External Resource URL:
                                </strong>
                                <div className="p-3 bg-light border-start border-3 border-secondary rounded shadow-sm">
                                    {source.url ? (
                                        <a href={source.url} target="_blank" rel="noopener noreferrer" className="text-decoration-none text-primary fw-semibold small">
                                            {source.url}
                                        </a>
                                    ) : (
                                        <em className="text-muted small">No direct online link provided.</em>
                                    )}
                                </div>
                            </div>
                            
                        </Card.Body>
                    </Card>
                </Col>

                {/* --- RIGHT COLUMN: CITATION CHAIN & TEMPORAL DATA --- */}
                <Col md={6}>

                    {/* Panel 1: Temporal & Citation Data */}
                    <Card className="shadow-lg border-0 rounded-4 mb-4">
                        <Card.Header as="h5" className="bg-success text-white py-3 rounded-top-4">
                            <i className="fas fa-hourglass-half me-2"></i>Temporal & Citation Data
                        </Card.Header>
                        <Card.Body className="p-4">
                            <ListGroup variant="flush">
                                <ListGroup.Item className="d-flex justify-content-between align-items-center px-0 fw-semibold">
                                    <span className="text-dark"><i className="fas fa-calendar-plus me-2 text-success"></i>Creation Year:</span>
                                    <span className="fs-5 text-success">{formatYear(source.creationYear)}</span>
                                </ListGroup.Item>
                                <ListGroup.Item className="d-flex justify-content-between align-items-center px-0 fw-semibold">
                                    <span className="text-dark"><i className="fas fa-calendar-check me-2 text-info"></i>Date Range Covered:</span>
                                    <span className="fs-6 text-info">{formatYearRange(source.startYear, source.endYear)}</span>
                                </ListGroup.Item>
                                <ListGroup.Item className="d-flex justify-content-between align-items-center px-0 fw-semibold">
                                    <span className="text-dark"><i className="fas fa-users me-2 text-primary"></i>Author/Editor:</span>
                                    <span className="fs-6 text-primary">{source.author || "N/A"}</span>
                                </ListGroup.Item>
                            </ListGroup>
                        </Card.Body>
                    </Card>


                    {/* Panel 2: Geospatial Linkage */}
                    <Card className="shadow-lg border-0 rounded-4">
                        <Card.Header as="h5" className="bg-secondary text-white py-3 rounded-top-4">
                            <i className="fas fa-map-marked-alt me-2"></i>Geospatial Linkage
                        </Card.Header>
                        <Card.Body className="p-4 d-flex flex-column justify-content-center">
                            <strong className="d-block mb-3 text-muted">
                                <i className="fas fa-map-pin me-2"></i>Primary Location Reference:
                            </strong>
                            {source.locationId ? (
                                <Card className="bg-light border-0 shadow-sm p-3">
                                    <h6 className="mb-1 fw-bold text-dark">
                                        <i className="fas fa-city me-2 text-primary"></i>
                                        {source.locationName || "Linked Location"}
                                    </h6>
                                    <Link 
                                        to={`/locations/show/${source.locationId}`} 
                                        className="btn btn-sm btn-outline-primary mt-2 fw-semibold"
                                    >
                                        <i className="fas fa-external-link-alt me-1"></i> View Location Details
                                    </Link>
                                </Card>
                            ) : (
                                <div className="text-center text-muted p-4 bg-light w-100 rounded">
                                    <i className="fas fa-exclamation-circle fa-2x mb-2"></i>
                                    <p className="mb-0 fw-semibold">Location: [UNLINKED]</p>
                                    <p className="small mb-0">This source is not explicitly tied to a single geospatial entity.</p>
                                </div>
                            )}
                        </Card.Body>
                    </Card>

                </Col>
            </Row>

            <div className="mt-5 pt-3 border-top text-center">
                <Button variant="outline-secondary" size="lg" onClick={() => navigate("/sources")} className="rounded-pill px-4">
                    <i className="fas fa-arrow-left me-2"></i>Return to Source Index
                </Button>
            </div>

        </Container>
    );
};

export default SourceDetail;
