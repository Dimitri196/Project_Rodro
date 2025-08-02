import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { apiGet, apiPost, apiPut } from "../utils/api";
import { Form, Button, Container, Row, Col, Alert, Card, Spinner } from "react-bootstrap";
import InputSelect from "../components/InputSelect"; // Assuming this is a custom component
import InputField from "../components/InputField"; // Assuming this is a custom component
import FlashMessage from "../components/FlashMessage"; // Assuming this is a custom component

const CemeteryForm = () => {
    const navigate = useNavigate();
    const { id } = useParams();
    const [cemetery, setCemetery] = useState({
        cemeteryName: "",
        description: "",
        address: "",
        numberOfGraves: "",
        latitude: "",
        longitude: "",
        cemeteryLocation: { _id: "" }, // Initialize with _id to avoid undefined issues
        cemeteryParish: { _id: "" }, // Initialize with _id to avoid undefined issues
        webLink: ""
    });

    const [locations, setLocations] = useState([]);
    const [parishes, setParishes] = useState([]);
    const [loading, setLoading] = useState(true); // Overall loading state for initial data fetch
    const [sentState, setSent] = useState(false); // For form submission status
    const [successState, setSuccess] = useState(false); // For form submission success
    const [errorState, setError] = useState(null); // For general errors

    // Fetch cemetery data for editing if ID is provided
    useEffect(() => {
        const fetchCemeteryData = async () => {
            setLoading(true);
            setError(null);
            try {
                if (id) {
                    const data = await apiGet(`/api/cemeteries/${id}`);
                    // Safely set nested objects, ensuring they are always objects with _id
                    setCemetery({
                        ...data,
                        cemeteryLocation: data.cemeteryLocation ? { _id: data.cemeteryLocation._id } : { _id: "" },
                        cemeteryParish: data.cemeteryParish ? { _id: data.cemeteryParish._id } : { _id: "" },
                        // Ensure numerical fields are parsed if they come as strings
                        numberOfGraves: data.numberOfGraves || "",
                        latitude: data.latitude || "",
                        longitude: data.longitude || ""
                    });
                }
            } catch (err) {
                console.error("Error fetching cemetery data:", err);
                setError(`Failed to load cemetery data: ${err.message || err}`);
            } finally {
                setLoading(false);
            }
        };

        // Fetch lists for dropdowns
        const fetchDropdownData = async () => {
            try {
                const [locationsData, parishesData] = await Promise.all([
                    apiGet("/api/locations"),
                    apiGet("/api/parishes")
                ]);
                setLocations(locationsData);
                setParishes(parishesData);
            } catch (err) {
                console.error("Error fetching dropdown data:", err);
                setError(`Failed to load dropdown options: ${err.message || err}`);
            }
        };

        fetchCemeteryData();
        fetchDropdownData();
    }, [id]);

    // Auto-hide flash message after 3 seconds
    useEffect(() => {
        if (sentState) {
            const timer = setTimeout(() => setSent(false), 3000);
            return () => clearTimeout(timer);
        }
    }, [sentState]);

    // Handle form submission
    const handleSubmit = async (e) => {
        e.preventDefault();
        setSent(false); // Reset sent state
        setError(null); // Clear previous errors

        // Prepare cemetery data for submission
        const cemeteryToSend = {
            ...cemetery,
            // Convert numerical fields to actual numbers or null
            numberOfGraves: cemetery.numberOfGraves ? parseInt(cemetery.numberOfGraves, 10) : null,
            latitude: cemetery.latitude ? parseFloat(cemetery.latitude) : null,
            longitude: cemetery.longitude ? parseFloat(cemetery.longitude) : null,
            // Ensure nested objects are sent as { id: "..." } or null
            cemeteryLocation: cemetery.cemeteryLocation._id ? { id: cemetery.cemeteryLocation._id } : null,
            cemeteryParish: cemetery.cemeteryParish._id ? { id: cemetery.cemeteryParish._id } : null,
            // If updating, include the ID
            ...(id && { id: parseInt(id, 10) })
        };

        try {
            const apiCall = id
                ? apiPut(`/api/cemeteries/${id}`, cemeteryToSend)
                : apiPost("/api/cemeteries", cemeteryToSend);

            await apiCall;
            setSuccess(true);
            setSent(true);
            setTimeout(() => navigate("/cemeteries"), 1500); // Navigate after a short delay
        } catch (err) {
            console.error("Error saving cemetery:", err);
            setError(err.message || "An unexpected error occurred.");
            setSuccess(false);
            setSent(true);
        }
    };

    // Handle location and parish selection change
    const handleSelectChange = (field, selectedId) => {
        setCemetery((prevCemetery) => ({
            ...prevCemetery,
            [field]: selectedId ? { _id: selectedId } : { _id: "" } // Store as { _id: "selectedId" } or { _id: "" }
        }));
    };

    if (loading) {
        return (
            <Container className="my-5 text-center">
                <Spinner animation="border" role="status">
                    <span className="visually-hidden">Loading...</span>
                </Spinner>
                <p className="mt-3 text-muted">Loading cemetery data...</p>
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
                        <i className="fas fa-cross me-3"></i>
                        {id ? "Update" : "Create"} Cemetery
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
                    text={successState ? "Cemetery saved successfully." : "Error saving cemetery."}
                />
            )}

            <Card className="shadow-sm border-0 rounded-4 mt-4">
                <Card.Body className="p-4">
                    <Form onSubmit={handleSubmit}>
                        {/* Cemetery Name and Description */}
                        <Row className="mb-3">
                            <Col md={6}>
                                <InputField
                                    required={true}
                                    type="text"
                                    name="cemeteryName"
                                    label="Cemetery Name"
                                    prompt="Input cemetery name"
                                    value={cemetery.cemeteryName}
                                    handleChange={(e) => setCemetery({ ...cemetery, cemeteryName: e.target.value })}
                                />
                            </Col>
                            <Col md={6}>
                                <InputField
                                    required={false}
                                    type="text"
                                    name="description"
                                    label="Description"
                                    prompt="Input cemetery description"
                                    value={cemetery.description}
                                    handleChange={(e) => setCemetery({ ...cemetery, description: e.target.value })}
                                />
                            </Col>
                        </Row>

                        {/* Address and Number of Graves */}
                        <Row className="mb-3">
                            <Col md={6}>
                                <InputField
                                    required={false}
                                    type="text"
                                    name="address"
                                    label="Address"
                                    prompt="Input cemetery address"
                                    value={cemetery.address}
                                    handleChange={(e) => setCemetery({ ...cemetery, address: e.target.value })}
                                />
                            </Col>
                            <Col md={6}>
                                <InputField
                                    required={false}
                                    type="number"
                                    name="numberOfGraves"
                                    label="Number of Graves"
                                    prompt="Input number of graves"
                                    value={cemetery.numberOfGraves}
                                    handleChange={(e) => setCemetery({ ...cemetery, numberOfGraves: e.target.value })}
                                    min="0"
                                />
                            </Col>
                        </Row>

                        {/* Location Dropdown */}
                        <Row className="mb-3">
                            <Col md={6}>
                                <InputSelect
                                    name="cemeteryLocation"
                                    label="Cemetery Location"
                                    prompt="Select cemetery location"
                                    value={cemetery.cemeteryLocation._id} // Use ._id for frontend
                                    handleChange={(e) => handleSelectChange("cemeteryLocation", e.target.value)}
                                    items={locations}
                                    getLabel={(item) => item.locationName}
                                    getValue={(item) => item._id} // Use ._id for frontend
                                />
                            </Col>

                            {/* Parish Dropdown */}
                            <Col md={6}>
                                <InputSelect
                                    name="cemeteryParish"
                                    label="Cemetery Parish"
                                    prompt="Select cemetery parish"
                                    value={cemetery.cemeteryParish._id} // Use ._id for frontend
                                    handleChange={(e) => handleSelectChange("cemeteryParish", e.target.value)}
                                    items={parishes}
                                    getLabel={(item) => item.parishName} // Assuming parishName for parish
                                    getValue={(item) => item._id} // Assuming _id for parish
                                />
                            </Col>
                        </Row>

                        {/* Latitude and Longitude */}
                        <Row className="mb-3">
                            <Col md={6}>
                                <InputField
                                    required={false}
                                    type="number"
                                    name="latitude"
                                    label="Latitude"
                                    prompt="Input latitude coordinates"
                                    value={cemetery.latitude}
                                    handleChange={(e) => setCemetery({ ...cemetery, latitude: e.target.value })}
                                    step="any" // Allow decimal numbers
                                />
                            </Col>
                            <Col md={6}>
                                <InputField
                                    required={false}
                                    type="number"
                                    name="longitude"
                                    label="Longitude"
                                    prompt="Input longitude coordinates"
                                    value={cemetery.longitude}
                                    handleChange={(e) => setCemetery({ ...cemetery, longitude: e.target.value })}
                                    step="any" // Allow decimal numbers
                                />
                            </Col>
                        </Row>

                        {/* Web Link */}
                        <Row className="mb-3">
                            <Col md={12}>
                                <InputField
                                    required={false}
                                    type="text"
                                    name="webLink"
                                    label="Web Link"
                                    prompt="Input cemetery's website link"
                                    value={cemetery.webLink}
                                    handleChange={(e) => setCemetery({ ...cemetery, webLink: e.target.value })}
                                />
                            </Col>
                        </Row>

                        {/* Submit Button */}
                        <Button type="submit" variant="primary" className="mt-3 px-4 py-2 shadow-sm rounded-pill">
                            <i className="fas fa-save me-2"></i>Save Cemetery
                        </Button>
                    </Form>
                </Card.Body>
            </Card>
        </Container>
    );
};

export default CemeteryForm;
