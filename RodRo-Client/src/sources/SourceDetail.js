import React, { useEffect, useState } from "react";
import { useParams, Link, useNavigate } from "react-router-dom";
import { Container, Row, Col, Card, ListGroup, Alert, Spinner, Button } from "react-bootstrap";
import { apiGet } from "../utils/api";

import { SOURCE_TYPE_MAP } from "../constants/sourceType";

const SourceDetail = () => {
    const { id } = useParams();
    const navigate = useNavigate();

    const [source, setSource] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            setLoading(true);
            setError(null);
            try {
                const sourceData = await apiGet(`/api/sources/${id}`);
                setSource(sourceData);
            } catch (err) {
                console.error("Error fetching source:", err);
                setError(`Failed to load source: ${err.message || err}`);
            } finally {
                setLoading(false);
            }
        };

        fetchData();
    }, [id]);

    if (loading) {
        return (
            <Container className="my-5 text-center">
                <Spinner animation="border" role="status">
                    <span className="visually-hidden">Loading...</span>
                </Spinner>
                <p className="mt-3 text-muted">Loading source details...</p>
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
                    <i className="fas fa-info-circle me-2"></i>Source not found.
                </Alert>
            </Container>
        );
    }

    return (
        <Container className="my-5 py-4 bg-light rounded shadow-lg">
            <Row className="mb-4">
                <Col md={12} className="text-center">
                    <h1 className="display-5 fw-bold text-primary mb-3">
                        <i className="fas fa-book me-3"></i>
                        {source.title || "Untitled Source"}
                    </h1>
                </Col>
            </Row>

            <Row className="justify-content-center">
                <Col md={8} className="mb-4">
                    <Card className="shadow-lg border-0 rounded-4 h-100">
                        <Card.Header as="h5" className="bg-primary text-white py-3 rounded-top-4">
                            <i className="fas fa-info-circle me-2"></i>Source Information
                        </Card.Header>
                        <Card.Body className="p-4">
                            <ListGroup variant="flush">
                                <ListGroup.Item className="d-flex justify-content-between align-items-center px-0">
                                    <strong>Reference:</strong>
                                    <span>{source.reference || "N/A"}</span>
                                </ListGroup.Item>
                                <ListGroup.Item className="d-flex justify-content-between align-items-center px-0">
                                    <strong>Type:</strong>
                                    <span>{SOURCE_TYPE_MAP[source.type] || source.type || "N/A"}</span>
                                </ListGroup.Item>
                                <ListGroup.Item className="px-0">
                                    <strong>Description:</strong>
                                    <p className="mb-0 text-muted">{source.description || "N/A"}</p>
                                </ListGroup.Item>
                                <ListGroup.Item className="d-flex justify-content-between align-items-center px-0">
                                    <strong>URL:</strong>
                                    {source.url ? (
                                        <a href={source.url} target="_blank" rel="noopener noreferrer" className="text-decoration-none text-primary">
                                            {source.url}
                                        </a>
                                    ) : (
                                        "N/A"
                                    )}
                                </ListGroup.Item>
                                <ListGroup.Item className="d-flex justify-content-between align-items-center px-0">
                                    <strong>Location:</strong>
                                    <span>
                                        {source.locationId ? (
                                            <Link to={`/locations/show/${source.locationId}`} className="text-decoration-none text-primary">
                                                {source.locationName || "View Location"}
                                            </Link>
                                        ) : (
                                            "N/A"
                                        )}
                                    </span>
                                </ListGroup.Item>
                            </ListGroup>
                        </Card.Body>
                    </Card>

                    <div className="text-center mt-4">
                        <Button variant="secondary" size="lg" onClick={() => navigate("/sources")} className="rounded-pill px-4 shadow-sm">
                            <i className="fas fa-arrow-left me-2"></i> Back to Sources
                        </Button>
                    </div>
                </Col>
            </Row>
        </Container>
    );
};

export default SourceDetail;
