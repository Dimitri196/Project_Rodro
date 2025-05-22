import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { apiGet, apiPost, apiPut } from "../utils/api";
import { Form, Button, Container, Row, Col, Alert } from "react-bootstrap";

import InputField from "../components/InputField";
import FlashMessage from "../components/FlashMessage";
import settlementTypeLabels from "../constants/settlementTypeLabels";

const LocationForm = () => {
    const navigate = useNavigate();
    const { id } = useParams();
    const [location, setLocation] = useState({
        locationName: "",
        settlementType: "",
        establishmentDate: "",
        gpsLatitude: "",
        gpsLongitude: ""
    });
    const [sentState, setSent] = useState(false);
    const [successState, setSuccess] = useState(false);
    const [errorState, setError] = useState(null);

    useEffect(() => {
        if (id) {
            apiGet(`/api/locations/${id}`)
                .then((data) => setLocation({
                    locationName: data.locationName || "",
                    settlementType: data.settlementType || "",
                    establishmentDate: data.establishmentDate || "",
                    gpsLatitude: data.gpsLatitude || "",
                    gpsLongitude: data.gpsLongitude || ""
                }))
                .catch((error) => setError(error.message));
        }
    }, [id]);

    const handleSubmit = (e) => {
        e.preventDefault();
        const apiCall = id
            ? apiPut(`/api/locations/${id}`, location)
            : apiPost("/api/locations", location);

        apiCall
            .then(() => {
                setSent(true);
                setSuccess(true);
                setTimeout(() => navigate("/locations"), 1000);
            })
            .catch((error) => {
                setError(error.message);
                setSent(true);
                setSuccess(false);
            });
    };

    useEffect(() => {
        if (sentState) {
            const timer = setTimeout(() => setSent(false), 3000);
            return () => clearTimeout(timer);
        }
    }, [sentState]);

    return (
        <Container className="mt-5">
            <h1 className="mb-4">{id ? "Update" : "Create"} Location</h1>
            {errorState && <Alert variant="danger">{errorState}</Alert>}
            {sentState && (
                <FlashMessage
                    theme={successState ? "success" : "danger"}
                    text={successState ? "Location saved successfully." : "Error saving location."}
                />
            )}

            <Form onSubmit={handleSubmit}>
                <Row>
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
  <Form.Group controlId="settlementType">
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

                <Row>
                    <Col md={6}>
                        <InputField
                            type="text"
                            name="establishmentDate"
                            label="Establishment Date"
                            prompt="Pick establishment date"
                            value={location.establishmentDate}
                            handleChange={(e) =>
                                setLocation({ ...location, establishmentDate: e.target.value })
                            }
                        />
                    </Col>
                </Row>

                <Row>
                    <Col md={6}>
                        <InputField
                            required
                            type="text"
                            name="gpsLatitude"
                            label="GPS Latitude"
                            prompt="Enter GPS latitude"
                            value={location.gpsLatitude}
                            handleChange={(e) =>
                                setLocation({ ...location, gpsLatitude: e.target.value })
                            }
                        />
                    </Col>
                    <Col md={6}>
                        <InputField
                            required
                            type="text"
                            name="gpsLongitude"
                            label="GPS Longitude"
                            prompt="Enter GPS longitude"
                            value={location.gpsLongitude}
                            handleChange={(e) =>
                                setLocation({ ...location, gpsLongitude: e.target.value })
                            }
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

export default LocationForm;
