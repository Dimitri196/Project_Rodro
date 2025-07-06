import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { apiGet, apiPost, apiPut } from "../utils/api";
import { Form, Button, Container, Row, Col, Alert } from "react-bootstrap";
import InputSelect from "../components/InputSelect";
import InputField from "../components/InputField";
import FlashMessage from "../components/FlashMessage";

const CemeteryForm = () => {
    const navigate = useNavigate();
    const { id } = useParams();
    const [cemetery, setCemetery] = useState({
        cemeteryName: "", // Changed to match backend model
        description: "",
        address: "",
        numberOfGraves: "",
        latitude: "",
        longitude: "",
        cemeteryLocation: { _id: "" }, // Ensuring _id for location
        cemeteryParish: { _id: "" }, // Added parish field
        webLink: ""
    });

    const [locations, setLocations] = useState([]);
    const [parishes, setParishes] = useState([]); // Added parishes state
    const [sentState, setSent] = useState(false);
    const [successState, setSuccess] = useState(false);
    const [errorState, setError] = useState(null);

    // Fetch cemetery data for editing if ID is provided
    useEffect(() => {
        if (id) {
            apiGet(`/api/cemeteries/${id}`)
                .then((data) => setCemetery(data))
                .catch((error) => setError(error.message));
        }
    }, [id]);

    // Fetch list of locations and parishes
    useEffect(() => {
        apiGet("/api/locations")
            .then((data) => setLocations(data))
            .catch((error) => setError(error.message));

        apiGet("/api/parishes")
            .then((data) => setParishes(data))
            .catch((error) => setError(error.message));
    }, []);

    // Auto-hide flash message after 3 seconds
    useEffect(() => {
        if (sentState) {
            const timer = setTimeout(() => setSent(false), 3000);
            return () => clearTimeout(timer);
        }
    }, [sentState]);

    // Handle form submission
    const handleSubmit = (e) => {
        e.preventDefault();

        // Prepare cemetery data
        const cemeteryToSend = {
            ...cemetery,
            cemeteryLocation: cemetery.cemeteryLocation._id ? { _id: cemetery.cemeteryLocation._id } : null,
            cemeteryParish: cemetery.cemeteryParish._id ? { _id: cemetery.cemeteryParish._id } : null
        };

        (id ? apiPut(`/api/cemeteries/${id}`, cemeteryToSend) : apiPost("/api/cemeteries", cemeteryToSend))
            .then(() => {
                setSent(true);
                setSuccess(true);
                setTimeout(() => navigate("/cemeteries"), 1000);
            })
            .catch((error) => {
                setError(error.message);
                setSent(true);
                setSuccess(false);
            });
    };

    // Handle location and parish selection change
    const handleLocationChange = (placeType, selectedId) => {
        setCemetery((prevCemetery) => {
            const updatedCemetery = { ...prevCemetery };
            if (placeType === "cemeteryLocation") {
                updatedCemetery.cemeteryLocation = { _id: selectedId }; // Set cemeteryLocation as object with _id
            } else if (placeType === "cemeteryParish") {
                updatedCemetery.cemeteryParish = { _id: selectedId }; // Set cemeteryParish similarly
            }
            return updatedCemetery;
        });
    };

    const sent = sentState;
    const success = successState;

    return (
        <Container className="mt-5">
            <h1 className="mb-4">{id ? "Update" : "Create"} Cemetery</h1>
            {errorState && <Alert variant="danger">{errorState}</Alert>}

            {sent && (
                <FlashMessage
                    theme={success ? "success" : "danger"}
                    text={success ? "Cemetery saved successfully." : "Error saving cemetery."}
                />
            )}

            <Form onSubmit={handleSubmit}>
                {/* Cemetery Name */}
                <Row>
                    <Col md={6}>
                        <InputField
                            required={true}
                            type="text"
                            name="cemeteryName"
                            min="3"
                            label="Cemetery Name"
                            prompt="Input cemetery name"
                            value={cemetery.cemeteryName}
                            handleChange={(e) => setCemetery({ ...cemetery, cemeteryName: e.target.value })}
                        />
                    </Col>

                    {/* Description */}
                    <Col md={6}>
                        <InputField
                            required={false}
                            type="text"
                            name="description"
                            min="3"
                            label="Description"
                            prompt="Input cemetery description"
                            value={cemetery.description}
                            handleChange={(e) => setCemetery({ ...cemetery, description: e.target.value })}
                        />
                    </Col>
                </Row>

                {/* Location, Address, and Number of Graves */}
                <Row>
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
                            min="0"
                            label="Number of Graves"
                            prompt="Input number of graves"
                            value={cemetery.numberOfGraves}
                            handleChange={(e) => setCemetery({ ...cemetery, numberOfGraves: e.target.value })}
                        />
                    </Col>
                </Row>

                {/* Location Dropdown */}
                <Row>
                    <Col md={6}>
                        <InputSelect
                            name="cemeteryLocation"
                            label="Cemetery Location"
                            prompt="Select cemetery location"
                            value={cemetery.cemeteryLocation._id}
                            handleChange={(e) => handleLocationChange("cemeteryLocation", e.target.value)}
                            items={locations}
                            getLabel={(item) => item.locationName}
                            getValue={(item) => item._id}
                        />
                    </Col>

                    {/* Parish Dropdown */}
                    <Col md={6}>
                        <InputSelect
                            name="cemeteryParish"
                            label="Cemetery Parish"
                            prompt="Select cemetery parish"
                            value={cemetery.cemeteryParish._id}
                            handleChange={(e) => handleLocationChange("cemeteryParish", e.target.value)}
                            items={parishes}
                        />
                    </Col>
                </Row>

                {/* Latitude and Longitude */}
                <Row>
                    <Col md={6}>
                        <InputField
                            required={false}
                            type="number"
                            name="latitude"
                            min="0"
                            label="Latitude"
                            prompt="Input latitude coordinates"
                            value={cemetery.latitude}
                            handleChange={(e) => setCemetery({ ...cemetery, latitude: e.target.value })}
                        />
                    </Col>

                    <Col md={6}>
                        <InputField
                            required={false}
                            type="number"
                            name="longitude"
                            min="0"
                            label="Longitude"
                            prompt="Input longitude coordinates"
                            value={cemetery.longitude}
                            handleChange={(e) => setCemetery({ ...cemetery, longitude: e.target.value })}
                        />
                    </Col>
                </Row>

                {/* Web Link */}
                <Row>
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
                <Button type="submit" variant="primary" className="mt-3">
                    Save
                </Button>
            </Form>
        </Container>
    );
};

export default CemeteryForm;
