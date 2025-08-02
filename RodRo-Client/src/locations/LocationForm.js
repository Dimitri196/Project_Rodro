import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { apiGet, apiPost, apiPut } from "../utils/api";
import { Form, Button, Container, Row, Col, Alert, Card, Spinner } from "react-bootstrap"; // Added Card, Spinner for consistent styling

import InputField from "../components/InputField"; // Assuming this is a custom component
import FlashMessage from "../components/FlashMessage"; // Assuming this is a custom component
import settlementTypeLabels from "../constants/settlementTypeLabels";

const LocationForm = () => {
    const navigate = useNavigate();
    const { id } = useParams();
    const [location, setLocation] = useState({
        locationName: "",
        settlementType: "",
        establishmentYear: "", // Changed from establishmentDate to establishmentYear
        gpsLatitude: "",
        gpsLongitude: ""
    });
    const [loading, setLoading] = useState(true); // Added loading state
    const [sentState, setSent] = useState(false);
    const [successState, setSuccess] = useState(false);
    const [errorState, setError] = useState(null);

    useEffect(() => {
        const fetchLocationData = async () => {
            if (id) {
                setLoading(true);
                try {
                    const data = await apiGet(`/api/locations/${id}`);
                    setLocation({
                        locationName: data.locationName || "",
                        settlementType: data.settlementType || "",
                        establishmentYear: data.establishmentYear || "", // Use establishmentYear
                        gpsLatitude: data.gpsLatitude || "",
                        gpsLongitude: data.gpsLongitude || ""
                    });
                } catch (error) {
                    setError(`Error loading location: ${error.message || error}`);
                } finally {
                    setLoading(false);
                }
            } else {
                setLoading(false); // No ID, so not loading existing data
            }
        };

        fetchLocationData();
    }, [id]);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setSent(false); // Reset sent state
        setError(null); // Clear previous errors

        // Prepare data for submission, converting numbers where necessary
        const dataToSubmit = {
            ...location,
            establishmentYear: location.establishmentYear ? parseInt(location.establishmentYear, 10) : null,
            gpsLatitude: location.gpsLatitude ? parseFloat(location.gpsLatitude) : null,
            gpsLongitude: location.gpsLongitude ? parseFloat(location.gpsLongitude) : null,
            // If updating, include the ID
            ...(id && { id: parseInt(id, 10) })
        };

        try {
            const apiCall = id
                ? apiPut(`/api/locations/${id}`, dataToSubmit)
                : apiPost("/api/locations", dataToSubmit);

            await apiCall;
            setSuccess(true);
            setSent(true);
            setTimeout(() => navigate("/locations"), 1500); // Navigate after a short delay
        } catch (error) {
            console.error("Error saving location:", error);
            setError(error.message || "An unexpected error occurred.");
            setSuccess(false);
            setSent(true);
        }
    };

    // Effect for flash message visibility
    useEffect(() => {
        if (sentState) {
            const timer = setTimeout(() => setSent(false), 3000);
            return () => clearTimeout(timer);
        }
    }, [sentState]);

    if (loading) {
        return (
            <Container className="my-5 text-center">
                <Spinner animation="border" role="status">
                    <span className="visually-hidden">Loading...</span>
                </Spinner>
                <p className="mt-3 text-muted">Loading location data...</p>
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
                        <i className="fas fa-map-marker-alt me-3"></i>
                        {id ? "Update" : "Create"} Location
                    </h1>
                </Col>
            </Row>

            {errorState && (
                <Alert variant="danger" className="text-center shadow-sm rounded-3">
                    <i className="fas fa-exclamation-triangle me-2"></i>{errorState}
                </Alert>
            )}
            {sentState && (
                <FlashMessage
                    theme={successState ? "success" : "danger"}
                    text={successState ? "Location saved successfully." : "Error saving location."}
                />
            )}

            <Card className="shadow-sm border-0 rounded-4 mt-4">
                <Card.Body className="p-4">
                    <Form onSubmit={handleSubmit}>
                        <Row className="mb-3">
                            <Col md={6}>
                                <InputField
                                    required
                                    type="text"
                                    name="locationName"
                                    label="Location Name"
                                    prompt="Enter location name"
                                    value={location.locationName}
                                    handleChange={(e) =>
                                        setLocation({ ...location, locationName: e.target.value })
                                    }
                                />
                            </Col>
                            <Col md={6}>
                                <Form.Group controlId="settlementType" className="mb-3">
                                    <Form.Label>Settlement Type</Form.Label>
                                    <Form.Select
                                        required
                                        value={location.settlementType}
                                        onChange={(e) =>
                                            setLocation({ ...location, settlementType: e.target.value })
                                        }
                                    >
                                        <option value="">-- Select settlement type --</option>
                                        {Object.entries(settlementTypeLabels).map(([key, label]) => (
                                            <option key={key} value={key}>
                                                {label}
                                            </option>
                                        ))}
                                    </Form.Select>
                                </Form.Group>
                            </Col>
                        </Row>

                        <Row className="mb-3">
                            <Col md={4}>
                                <InputField
                                    type="number" // Changed to number for year
                                    name="establishmentYear" // Changed name
                                    label="Establishment Year" // Changed label
                                    prompt="Enter establishment year (e.g., 1800)"
                                    value={location.establishmentYear}
                                    handleChange={(e) =>
                                        setLocation({ ...location, establishmentYear: e.target.value })
                                    }
                                />
                            </Col>
                            <Col md={4}>
                                <InputField
                                    type="number" // Changed to number for GPS
                                    name="gpsLatitude"
                                    label="GPS Latitude"
                                    prompt="Enter GPS latitude (e.g., 49.8175)"
                                    value={location.gpsLatitude}
                                    handleChange={(e) =>
                                        setLocation({ ...location, gpsLatitude: e.target.value })
                                    }
                                    step="any" // Allow decimal numbers
                                />
                            </Col>
                            <Col md={4}>
                                <InputField
                                    type="number" // Changed to number for GPS
                                    name="gpsLongitude"
                                    label="GPS Longitude"
                                    prompt="Enter GPS longitude (e.g., 15.4730)"
                                    value={location.gpsLongitude}
                                    handleChange={(e) =>
                                        setLocation({ ...location, gpsLongitude: e.target.value })
                                    }
                                    step="any" // Allow decimal numbers
                                />
                            </Col>
                        </Row>

                        <Button type="submit" variant="primary" className="mt-3 px-4 py-2 shadow-sm rounded-pill">
                            <i className="fas fa-save me-2"></i>Save Location
                        </Button>
                    </Form>
                </Card.Body>
            </Card>
        </Container>
    );
};

export default LocationForm;