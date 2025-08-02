import React, { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import { Container, Row, Col, Card, ListGroup, Alert, Spinner } from "react-bootstrap";
import { apiGet } from "../utils/api";
import { useSession } from "../contexts/session"; // Import useSession

const ParishDetail = () => {
    const { id } = useParams(); // Getting id from URL
    const { session } = useSession();
    const isAdmin = session.data?.isAdmin === true;

    const [parish, setParish] = useState(null); // Initialize as null to clearly indicate no data yet
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            setLoading(true);
            setError(null);
            try {
                // Fetch parish data
                const parishData = await apiGet(`/api/parishes/${id}`);
                setParish(parishData);
            } catch (err) {
                console.error("Error fetching data:", err);
                setError(`Failed to load data: ${err.message || err}`);
            } finally {
                setLoading(false);
            }
        };

        fetchData();
    }, [id]); // Re-run the effect when id changes

    if (loading) {
        return (
            <Container className="my-5 text-center">
                <Spinner animation="border" role="status">
                    <span className="visually-hidden">Loading...</span>
                </Spinner>
                <p className="mt-3 text-muted">Loading parish details...</p>
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

    // If parish is null after loading (e.g., 404 from API), handle gracefully
    if (!parish) {
        return (
            <Container className="my-5">
                <Alert variant="info" className="text-center shadow-sm rounded-3">
                    <i className="fas fa-info-circle me-2"></i>Parish not found.
                </Alert>
            </Container>
        );
    }

    return (
        <Container className="my-5 py-4 bg-light rounded shadow-lg">
            {/* Font Awesome for icons (ensure it's linked in index.html) */}
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" xintegrity="sha512-1ycn6IcaQQ40JuKmWswL3QbpgExX+Q6bLSYg5rXW4remp4o/Y6fFZj/gX2Q2K2V2F5R5/5Q5w5w5w5w5w==" crossOrigin="anonymous" referrerPolicy="no-referrer" />

            <Row className="mb-4">
                <Col md={12} className="text-center">
                    <h1 className="display-4 fw-bold text-primary mb-3">
                        <i className="fas fa-church me-3"></i>
                        {parish.parishName || "N/A"} - {parish.churchName || "N/A"}
                    </h1>
                    {isAdmin && (
                        <Link to={`/parishes/edit/${parish.id}`} className="btn btn-warning btn-lg rounded-pill px-4 py-2 shadow-sm mt-3">
                            <i className="fas fa-edit me-2"></i>Edit Parish
                        </Link>
                    )}
                </Col>
            </Row>

            <Row className="justify-content-center">
                {/* Basic Information Card */}
                <Col md={6} className="mb-4">
                    <Card className="shadow-lg border-0 rounded-4 h-100">
                        <Card.Header as="h5" className="bg-primary text-white py-3 rounded-top-4">
                            <i className="fas fa-info-circle me-2"></i>Basic Information
                        </Card.Header>
                        <Card.Body className="p-4">
                            <ListGroup variant="flush">
                                <ListGroup.Item className="d-flex justify-content-between align-items-center px-0">
                                    <strong>Cemetery:</strong>
                                    <span>{parish.cemeteryName || "N/A"}</span>
                                </ListGroup.Item>
                                <ListGroup.Item className="d-flex justify-content-between align-items-center px-0">
                                    <strong>Establishment Date:</strong>
                                    <span>{parish.establishmentDate || "N/A"}</span>
                                </ListGroup.Item>
                                <ListGroup.Item className="d-flex justify-content-between align-items-center px-0">
                                    <strong>Cancellation Date:</strong>
                                    <span>{parish.cancellationDate || "N/A"}</span>
                                </ListGroup.Item>
                                <ListGroup.Item className="d-flex justify-content-between align-items-center px-0">
                                    <strong>Location Name:</strong>
                                    <span>
                                        {parish.parishLocation ? (
                                            <Link to={`/locations/show/${parish.parishLocation._id}`} className="text-decoration-none text-primary">
                                                {parish.parishLocation.locationName}
                                            </Link>
                                        ) : (
                                            "N/A"
                                        )}
                                    </span>
                                </ListGroup.Item>
                            </ListGroup>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default ParishDetail;
