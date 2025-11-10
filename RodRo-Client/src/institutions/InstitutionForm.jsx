import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Form, Button, Container, Row, Col, Alert, Card, Spinner } from "react-bootstrap";
import AsyncSelect from "react-select/async";
import InputField from "../components/InputField";
import FlashMessage from "../components/FlashMessage";
import { apiGet, apiPost, apiPut } from "../utils/api";
import { institutionTypes } from "../constants/institutionTypes"; // Import institution types
import "bootstrap/dist/css/bootstrap.min.css";

const InstitutionForm = () => {
    const navigate = useNavigate();
    const { id } = useParams();

    // CRITICAL CHANGE 1: Use flattened DTO fields for initial state
    const [institution, setInstitution] = useState({
        name: "",
        description: "",
        sealImageUrl: "",
        type: "",
        establishmentYear: "",
        cancellationYear: "",
        countryId: null,
        locationId: null,
    });

    const [locations, setLocations] = useState([]); // Used for caching location results
    const [countries, setCountries] = useState([]); // Used for caching country results

    const [errorState, setError] = useState(null);
    const [sentState, setSent] = useState(false);
    const [successState, setSuccess] = useState(false);

    // Async Select states
    const [selectedLocationOption, setSelectedLocationOption] = useState(null);
    const [selectedCountryOption, setSelectedCountryOption] = useState(null);
    
    const [loadingInstitution, setLoadingInstitution] = useState(false);

    // Load institution if editing
    useEffect(() => {
        if (id) {
            setLoadingInstitution(true);
            apiGet(`/api/institutions/${id}`)
                .then((data) => {
                    // CRITICAL CHANGE 2: Map flattened DTO fields for edit form initialization
                    setInstitution({
                        name: data.name || "",
                        description: data.description || "",
                        sealImageUrl: data.sealImageUrl || "",
                        type: data.type || "",
                        establishmentYear: data.establishmentYear || "",
                        cancellationYear: data.cancellationYear || "",
                        countryId: data.countryId || null,
                        locationId: data.locationId || null,
                    });

                    // Set initial selected location option
                    if (data.locationId && data.locationName) {
                        setSelectedLocationOption({
                            value: data.locationId,
                            label: data.locationName,
                        });
                    }
                    
                    // Set initial selected country option
                    if (data.countryId && data.countryName) {
                        setSelectedCountryOption({
                            value: data.countryId,
                            label: data.countryName,
                        });
                    }

                    setLoadingInstitution(false);
                })
                .catch((error) => {
                    setError(error.message);
                    setLoadingInstitution(false);
                });
        }
    }, [id]);

    // --- Country AsyncSelect Logic ---

    const loadCountryOptions = (inputValue, callback) => {
        const searchTerm = inputValue || "";
        apiGet(`/api/countries?searchTerm=${encodeURIComponent(searchTerm)}&page=0&size=10000`)
            .then((data) => {
                const options = data.content.map((country) => ({
                    value: country._id ?? country.id,
                    label: country.nameInPolish,
                    fullCountry: country,
                }));
                setCountries(options.map((opt) => opt.fullCountry));
                callback(options);
            })
            .catch(() => {
                callback([]);
            });
    };

    const handleCountryChange = (selectedOption) => {
        setSelectedCountryOption(selectedOption);

        const countryId = selectedOption?.value || null;
        setInstitution((prev) => ({ ...prev, countryId }));
    };

    // --- Location AsyncSelect Logic (Simplified) ---

    // The loadLocationOptions remains the same, but we will simplify handleLocationChange.
    const loadLocationOptions = (inputValue, callback) => {
        const searchTerm = inputValue || "";
        // Note: The /api/locations endpoint should now return flat DTOs with _id, locationName, etc.
        apiGet(`/api/locations?searchTerm=${encodeURIComponent(searchTerm)}&page=0&size=10000`)
            .then((data) => {
                const options = data.content.map((loc) => ({
                    value: loc._id ?? loc.id,
                    label: loc.locationName,
                    fullLocation: loc,
                }));
                setLocations(options.map((opt) => opt.fullLocation));
                callback(options);
            })
            .catch(() => {
                callback([]);
            });
    };

    // CRITICAL CHANGE 3: Simplified Location Change Handler
    const handleLocationChange = (selectedOption) => {
        setSelectedLocationOption(selectedOption);
        
        const locationId = selectedOption?.value || null;
        setInstitution((prev) => ({ ...prev, locationId }));
    };

    // --- Form Submission ---

    const handleSubmit = (e) => {
        e.preventDefault();

        // The DTO sent back to the server only needs the name, description, type, years, and the IDs.
        const institutionToSend = {
            name: institution.name,
            description: institution.description,
            sealImageUrl: institution.sealImageUrl,
            type: institution.type,
            establishmentYear: institution.establishmentYear === "" ? null : parseInt(institution.establishmentYear, 10),
            cancellationYear: institution.cancellationYear === "" ? null : parseInt(institution.cancellationYear, 10),
            
            // Send IDs back for the backend service to perform lookup
            countryId: institution.countryId,
            locationId: institution.locationId,
        };

        console.log("Submitting institution data:", institutionToSend);

        (id
            ? apiPut(`/api/institutions/${id}`, institutionToSend)
            : apiPost("/api/institutions", institutionToSend)
        )
            .then(() => {
                setSent(true);
                setSuccess(true);
                setTimeout(() => navigate("/institutions"), 1000);
            })
            .catch((error) => {
                setError(error.message);
                setSent(true);
                setSuccess(false);
            });
    };

    const sent = sentState;
    const success = successState;
    const isEditing = !!id;

    const handleFieldChange = (e) => {
        const { name, value } = e.target;
        setInstitution({ ...institution, [name]: value });
    };

    return (
        <Container className="my-5">
            <Row className="justify-content-center">
                <Col md={8}>
                    <Card className="shadow-lg border-0 rounded-4">
                        <Card.Header as="h3" className="bg-primary text-white py-3 rounded-top-4 text-center">
                            {isEditing ? "Update Institution" : "Create Institution"}
                        </Card.Header>
                        <Card.Body className="p-4">
                            {loadingInstitution && (
                                <div className="d-flex justify-content-center my-3">
                                    <Spinner animation="border" role="status" />
                                </div>
                            )}

                            {!loadingInstitution && (
                                <>
                                    {errorState && <Alert variant="danger">{errorState}</Alert>}
                                    {sent && (
                                        <FlashMessage
                                            theme={success ? "success" : "danger"}
                                            text={success ? "Institution saved successfully." : "Error saving institution."}
                                        />
                                    )}

                                    <Form onSubmit={handleSubmit}>
                                        <Row className="mb-3">
                                            <Col md={12}>
                                                <InputField
                                                    required
                                                    type="text"
                                                    name="name" // Changed name to DTO field 'name'
                                                    min="3"
                                                    label="Institution Name"
                                                    prompt="Input name of institution"
                                                    value={institution.name}
                                                    handleChange={handleFieldChange} // Using generic handler
                                                />
                                            </Col>
                                        </Row>

                                        <Row className="mb-3">
                                            <Col md={12}>
                                                <InputField
                                                    type="textarea"
                                                    name="description" // Changed name to DTO field 'description'
                                                    label="Institution Description"
                                                    prompt="Input description of institution"
                                                    value={institution.description}
                                                    handleChange={handleFieldChange}
                                                />
                                            </Col>
                                        </Row>
                                        
                                        {/* NEW: Institution Type Select */}
                                        <Row className="mb-3">
                                            <Col md={12}>
                                                <Form.Group>
                                                    <Form.Label>Institution Type</Form.Label>
                                                    <Form.Select
                                                        name="type"
                                                        value={institution.type}
                                                        onChange={handleFieldChange}
                                                        required
                                                    >
                                                        <option value="">Select Type</option>
                                                        {Object.keys(institutionTypes).map((key) => (
                                                            <option key={key} value={key}>
                                                                {institutionTypes[key]}
                                                            </option>
                                                        ))}
                                                    </Form.Select>
                                                </Form.Group>
                                            </Col>
                                        </Row>
                                        
                                        {/* NEW: Establishment and Cancellation Years */}
                                        <Row className="mb-3">
                                            <Col md={6}>
                                                <InputField
                                                    type="number"
                                                    name="establishmentYear"
                                                    label="Establishment Year (Optional)"
                                                    prompt="e.g., 1850"
                                                    value={institution.establishmentYear}
                                                    handleChange={handleFieldChange}
                                                />
                                            </Col>
                                            <Col md={6}>
                                                <InputField
                                                    type="number"
                                                    name="cancellationYear"
                                                    label="Cancellation Year (Optional)"
                                                    prompt="e.g., 1945"
                                                    value={institution.cancellationYear}
                                                    handleChange={handleFieldChange}
                                                />
                                            </Col>
                                        </Row>

                                        {/* NEW: Country Selection */}
                                        <Row className="mb-4">
                                            <Col md={12}>
                                                <label>Country (Required):</label>
                                                <AsyncSelect
                                                    cacheOptions
                                                    defaultOptions
                                                    loadOptions={loadCountryOptions}
                                                    onChange={handleCountryChange}
                                                    value={selectedCountryOption}
                                                    placeholder="Search and select country..."
                                                    isClearable
                                                />
                                            </Col>
                                        </Row>

                                        {/* Location Selection (Updated to use simplified handler) */}
                                        <Row className="mb-4">
                                            <Col md={12}>
                                                <label>Location (Required):</label>
                                                <AsyncSelect
                                                    cacheOptions
                                                    defaultOptions
                                                    loadOptions={loadLocationOptions}
                                                    onChange={handleLocationChange}
                                                    value={selectedLocationOption}
                                                    placeholder="Search and select location..."
                                                    isClearable
                                                />
                                            </Col>
                                        </Row>

                                        <Button 
                                            type="submit" 
                                            variant="primary" 
                                            className="w-100 mt-3"
                                            disabled={!institution.countryId || !institution.locationId} // Disable if FKs aren't selected
                                        >
                                            <i className="fas fa-save me-2"></i>
                                            {isEditing ? "Update Institution" : "Create Institution"}
                                        </Button>
                                    </Form>
                                </>
                            )}
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default InstitutionForm;
