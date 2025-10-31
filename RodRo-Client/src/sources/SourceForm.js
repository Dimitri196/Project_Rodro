import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
// Use apiGet, apiPost, apiPut to match LocationForm
import { apiGet, apiPost, apiPut } from "../utils/api"; 
import { useSession } from "../contexts/session";

// Import Bootstrap components and custom components
import {
    Form,
    Button,
    Container,
    Row,
    Col,
    Alert,
    Spinner,
    Card,
} from "react-bootstrap";
import InputField from "../components/InputField";
import FlashMessage from "../components/FlashMessage";
// Assuming you have a constant file for SourceType similar to settlementTypeLabels
import { SOURCE_TYPE_MAP } from '../constants/sourceType'; 

const SourceForm = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const { session } = useSession();
    const isAdmin = session.data?.isAdmin;
    const isEditMode = Boolean(id);

    // --- State Initialization ---
    const [source, setSource] = useState({
        title: "", // Renamed to title to match DTO/entity conventions
        description: "",
        reference: "",
        type: "BAPTISM", // Renamed to type
        url: "", // Renamed to url
        locationId: "", // Renamed to locationId (ID of the location)
        creationYear: "",
        startYear: "",
        endYear: "",
        citationString: "", // New field based on common entity structure
    });
    
    // API/UI State
    const [locations, setLocations] = useState([]);
    const [errors, setErrors] = useState({});
    const [sentState, setSent] = useState(false);
    const [successState, setSuccess] = useState(false);
    const [errorState, setError] = useState(null);
    const [loading, setLoading] = useState(true); // Initial loading for fetching source/locations

    // --- Helper for unifying handleChange ---
    const handleChange = e => {
        const { name, value } = e.target;
        setSource(prev => ({ ...prev, [name]: value }));
    };

// --- Data Fetching Effect ---
useEffect(() => {
    const fetchData = async () => {
        if (!isAdmin) {
            setLoading(false);
            return;
        }

        try {
            // 1. Fetch location options
            // The API returns a Page object, extract the 'content' array
            const locationResponse = await apiGet("/api/locations?size=1000"); 
            
            // FIX IS HERE: Extract the array from the 'content' property
            setLocations(locationResponse.content || []); // Use '|| []' as a safety net

            // 2. Fetch source data if editing
            if (isEditMode) {
                const src = await apiGet(`/api/sources/${id}`);
                // ... rest of setSource logic (unchanged) ...
                setSource({
                    title: src.title || "",
                    description: src.description || "",
                    reference: src.reference || "",
                    type: src.type || "BAPTISM",
                    url: src.url || "",
                    locationId: src.location?.id || "",
                    creationYear: src.creationYear || "",
                    startYear: src.startYear || "",
                    endYear: src.endYear || "",
                    citationString: src.citationString || "",
                });
            }
        } catch (err) {
            // ... (unchanged) ...
            setError("Failed to load necessary data (locations or source).");
            console.error(err);
        } finally {
            setLoading(false);
        }
    };

    fetchData();
}, [id, isEditMode, isAdmin]);

    // Auto-hide flash messages
    useEffect(() => {
        if (sentState) {
            const timer = setTimeout(() => setSent(false), 3000);
            return () => clearTimeout(timer);
        }
    }, [sentState]);

    // --- Validation Logic ---
    const validate = () => {
        const errs = {};
        if (!source.title.trim()) errs.title = "Title is required";
        if (!source.type) errs.type = "Type is required";
        if (!source.locationId) errs.locationId = "Location is required";
        return errs;
    };

    // --- Submission Handler ---
    const handleSubmit = async (e) => {
        e.preventDefault();
        setError(null);
        setSent(false);
        setSuccess(false);

        const validationErrors = validate();
        if (Object.keys(validationErrors).length > 0) {
            setErrors(validationErrors);
            return;
        }
        setErrors({});

        // Prepare payload, converting number strings to actual numbers or null
        const payload = {
            title: source.title,
            description: source.description,
            reference: source.reference,
            type: source.type,
            url: source.url,
            citationString: source.citationString,
            
            // Convert year fields to Number or null
            creationYear: source.creationYear ? Number(source.creationYear) : null,
            startYear: source.startYear ? Number(source.startYear) : null,
            endYear: source.endYear ? Number(source.endYear) : null,

            // Link Location by ID (assuming backend expects 'location' object with ID)
            location: source.locationId ? { id: parseInt(source.locationId, 10) } : null
        };

        try {
            const request = isEditMode
                ? apiPut(`/api/sources/${id}`, payload)
                : apiPost("/api/sources", payload);

            await request;
            setSent(true);
            setSuccess(true);
            setTimeout(() => navigate("/sources"), 1000);
        } catch (error) {
            setError(error.message || "Failed to save source");
            setSent(true);
            setSuccess(false);
        }
    };

    // --- Render Logic ---
    if (!isAdmin) return <Alert variant="danger" className="m-4">You are not authorized to view this page.</Alert>;

    if (loading) {
        return (
            <Container className="my-5 text-center">
                <Spinner animation="border" role="status" />
                <p className="mt-3 text-muted">Loading source data and locations...</p>
            </Container>
        );
    }

    return (
        <Container className="my-4">
            <Row className="justify-content-center">
                <Col md={10} lg={8}>
                    <Card className="shadow-sm border-0 rounded-3">
                        <Card.Body className="p-4">
                            <h3 className="text-center text-primary mb-4">
                                {isEditMode ? "Update Source" : "Create New Source"}
                            </h3>

                            {errorState && <Alert variant="danger">{errorState}</Alert>}
                            {sentState && (
                                <FlashMessage
                                    theme={successState ? "success" : "danger"}
                                    text={
                                        successState
                                            ? "Source saved successfully."
                                            : "Error saving source."
                                    }
                                />
                            )}

                            <Form onSubmit={handleSubmit}>
                                {/* Row 1: Title and Reference */}
                                <Row>
                                    <Col md={8}>
                                        <InputField
                                            required
                                            type="text"
                                            name="title"
                                            label="Source Title"
                                            prompt="Enter the main title of the source"
                                            value={source.title}
                                            handleChange={handleChange}
                                            error={errors.title}
                                        />
                                    </Col>
                                    <Col md={4}>
                                        <InputField
                                            type="text"
                                            name="reference"
                                            label="Reference / Citation Short-Form"
                                            prompt="e.g., Book Author, Year, Page"
                                            value={source.reference}
                                            handleChange={handleChange}
                                        />
                                    </Col>
                                </Row>
                                
                                {/* Row 2: Type and Location */}
                                <Row>
                                    <Col md={6}>
                                        <Form.Group className="mb-3" controlId="type">
                                            <Form.Label className="fw-semibold">Source Type</Form.Label>
                                            <Form.Select
                                                required
                                                name="type"
                                                value={source.type}
                                                onChange={handleChange}
                                                size="sm"
                                                isInvalid={!!errors.type}
                                            >
                                                <option value="">-- Select Type --</option>
                                                {/* Use SOURCE_TYPE_MAP if available, otherwise hardcode options */}
                                                {Object.entries(SOURCE_TYPE_MAP || {BAPTISM: "Baptism", MARRIAGE: "Marriage", BURIAL: "Burial", OTHER: "Other"}).map(([key, label]) => (
                                                    <option key={key} value={key}>{label}</option>
                                                ))}
                                            </Form.Select>
                                            <Form.Control.Feedback type="invalid">{errors.type}</Form.Control.Feedback>
                                        </Form.Group>
                                    </Col>
                                    <Col md={6}>
                                        <Form.Group className="mb-3" controlId="locationId">
                                            <Form.Label className="fw-semibold">Location</Form.Label>
                                            <Form.Select
                                                required
                                                name="locationId"
                                                value={source.locationId}
                                                onChange={handleChange}
                                                size="sm"
                                                isInvalid={!!errors.locationId}
                                            >
                                                <option value="">-- Select Location --</option>
                                                {locations.map(loc => (
                                                    <option key={loc.id} value={loc.id}>
                                                        {loc.locationName}
                                                    </option>
                                                ))}
                                            </Form.Select>
                                            <Form.Control.Feedback type="invalid">{errors.locationId}</Form.Control.Feedback>
                                        </Form.Group>
                                    </Col>
                                </Row>

                                {/* Row 3: Temporal Data (Years) */}
                                <Row>
                                    <Col md={4}>
                                        <InputField
                                            type="number"
                                            name="creationYear"
                                            label="Creation / Publication Year"
                                            prompt="Year source was created (e.g., 1905)"
                                            value={source.creationYear}
                                            handleChange={handleChange}
                                        />
                                    </Col>
                                    <Col md={4}>
                                        <InputField
                                            type="number"
                                            name="startYear"
                                            label="Data Start Year"
                                            prompt="Temporal start year (e.g., 1850)"
                                            value={source.startYear}
                                            handleChange={handleChange}
                                        />
                                    </Col>
                                    <Col md={4}>
                                        <InputField
                                            type="number"
                                            name="endYear"
                                            label="Data End Year"
                                            prompt="Temporal end year (e.g., 1899)"
                                            value={source.endYear}
                                            handleChange={handleChange}
                                        />
                                    </Col>
                                </Row>
                                
                                {/* Row 4: URL */}
                                <InputField
                                    type="url"
                                    name="url"
                                    label="Source URL"
                                    prompt="Link to the source material online"
                                    value={source.url}
                                    handleChange={handleChange}
                                />

                                {/* Row 5: Description */}
                                <Form.Group className="mb-3" controlId="description">
                                    <Form.Label className="fw-semibold">Description</Form.Label>
                                    <Form.Control
                                        as="textarea"
                                        name="description"
                                        rows={3}
                                        value={source.description}
                                        onChange={handleChange}
                                        placeholder="Detailed description of the source and its contents."
                                    />
                                </Form.Group>
                                
                                {/* Row 6: Full Citation String */}
                                <Form.Group className="mb-3" controlId="citationString">
                                    <Form.Label className="fw-semibold">Full Citation String</Form.Label>
                                    <Form.Control
                                        as="textarea"
                                        name="citationString"
                                        rows={2}
                                        value={source.citationString}
                                        onChange={handleChange}
                                        placeholder="Full bibliographic citation (optional)."
                                    />
                                </Form.Group>

                                <div className="d-flex justify-content-end mt-4">
                                    <Button type="submit" variant="primary" size="sm" className="px-4">
                                        <i className="fas fa-save me-2"></i>
                                        {isEditMode ? "Update Source" : "Create Source"}
                                    </Button>
                                </div>
                            </Form>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default SourceForm;
