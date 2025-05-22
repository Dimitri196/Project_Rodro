import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { apiGet, apiPost, apiPut } from "../utils/api";
import { Form, Button, Container, Row, Col, Alert } from "react-bootstrap";

import InputSelect from "../components/InputSelect";
import InputField from "../components/InputField";
import FlashMessage from "../components/FlashMessage";
import dateStringFormatter from "../utils/dateStringFormatter";

const ParishForm = () => {
    const navigate = useNavigate();
    const { id } = useParams();
    const [parish, setParish] = useState({
        name: "",
        churchName: "",
        cemeteryName: "",
        establishmentDate: "",
        cancellationDate: "",
        parishLocation: { _id: "" },
    });
    const [locations, setLocations] = useState([]); // List of available locations
    const [sentState, setSent] = useState(false);
    const [successState, setSuccess] = useState(false);
    const [errorState, setError] = useState(null);

    // Fetch existing parish data if editing
    useEffect(() => {
        if (id) {
            apiGet(`/api/parishes/${id}`)
                .then((data) => {
                    setParish({
                        ...data,
                        establishmentDate: dateStringFormatter(data.establishmentDate), // Format date
                        cancellationDate: dateStringFormatter(data.cancellationDate),   // Format date
                    });
                })
                .catch((error) => setError(error.message));
        }
    }, [id]);

    // Fetch list of locations
    useEffect(() => {
        apiGet("/api/locations")
            .then((data) => {
                console.log("Fetched locations:", data); // <-- Add this
                setLocations(data);
            })
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

        const parishToSend = {
            ...parish,
            establishmentDate: parish.establishmentDate ? dateStringFormatter(parish.establishmentDate) : null,
            cancellationDate: parish.cancellationDate ? dateStringFormatter(parish.cancellationDate) : null,
            parishLocation: parish.parishLocation._id ? { _id: parish.parishLocation._id } : null,
        };

        (id ? apiPut(`/api/parishes/${id}`, parishToSend) : apiPost("/api/parishes", parishToSend))
            .then(() => {
                setSent(true);
                setSuccess(true);
                setTimeout(() => navigate("/parishes"), 1000);
            })
            .catch((error) => {
                console.error(error.message);
                setError(error.message);
                setSent(true);
                setSuccess(false);
            });
    };

    // Handle location selection change
    const handleLocationChange = (placeType, selectedLocationId) => {
        setParish((prevParish) => ({
            ...prevParish,
            parishLocation: placeType === "parishLocation" ? { _id: selectedLocationId } : prevParish.parishLocation,
        }));
    };

    const sent = sentState;
    const success = successState;

    return (
        <Container className="mt-5">
            <h1 className="mb-4">{id ? "Update" : "Create"} Parish</h1>
            {errorState && <Alert variant="danger">{errorState}</Alert>}

            {sent && (
                <FlashMessage
                    theme={success ? "success" : "danger"}
                    text={success ? "Parish saved successfully." : "Error saving parish."}
                />
            )}

            <Form onSubmit={handleSubmit}>
                <Row>
                    <Col md={6}>
                        <InputField
                            required={true}
                            type="text"
                            name="name"
                            min="3"
                            label="Parish Name"
                            prompt="Input name of parish"
                            value={parish.name}
                            handleChange={(e) => setParish({ ...parish, name: e.target.value })}
                        />
                    </Col>
                    <Col md={6}>
                        <InputField
                            required={true}
                            type="text"
                            name="churchName"
                            min="3"
                            label="Church Name"
                            prompt="Input name of church"
                            value={parish.churchName}
                            handleChange={(e) => setParish({ ...parish, churchName: e.target.value })}
                        />
                    </Col>
                </Row>

                <Row>
                    <Col md={6}>
                        <InputField
                            required={true}
                            type="text"
                            name="cemeteryName"
                            min="3"
                            label="Cemetery Name"
                            prompt="Input name of cemetery"
                            value={parish.cemeteryName}
                            handleChange={(e) => setParish({ ...parish, cemeteryName: e.target.value })}
                        />
                    </Col>
                    <Col md={6}>
                        <InputField
                            required={true}
                            type="date"
                            name="establishmentDate"
                            label="Establishment Date"
                            prompt="Input establishment date"
                            value={parish.establishmentDate}
                            handleChange={(e) => setParish({ ...parish, establishmentDate: e.target.value })}
                        />
                    </Col>
                </Row>

                <Row>
                    <Col md={6}>
                        <InputField
                            type="date"
                            name="cancellationDate"
                            label="Cancellation Date"
                            prompt="Input cancellation date (optional)"
                            value={parish.cancellationDate}
                            handleChange={(e) => setParish({ ...parish, cancellationDate: e.target.value })}
                        />
                    </Col>
                    <Col md={6}>
                        <InputSelect
                            name="parishLocation"
                            label="Parish Location"
                            prompt="Select parish location"
                            value={parish.parishLocation._id}
                            handleChange={(e) => handleLocationChange("parishLocation", e.target.value)}
                            items={locations}
                            getLabel={(item) => item.locationName}
                            getValue={(item) => item._id}
                        />
                    </Col>
                </Row>

                <Button type="submit" variant="primary" className="mt-3">
                    Save
                </Button>
            </Form>
        </Container>
    );
};

export default ParishForm;
