import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { apiGet, apiPost, apiPut } from "../utils/api";
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
import settlementTypeLabels from "../constants/settlementTypeLabels";

const LocationForm = () => {
  const navigate = useNavigate();
  const { id } = useParams();

  const [location, setLocation] = useState({
    locationName: "",
    settlementType: "",
    establishmentYear: "",
    gpsLatitude: "",
    gpsLongitude: "",
  });

  const [sentState, setSent] = useState(false);
  const [successState, setSuccess] = useState(false);
  const [errorState, setError] = useState(null);
  const [loading, setLoading] = useState(true);

  // Fetch location data if editing
  useEffect(() => {
    const fetchLocation = async () => {
      if (id) {
        setLoading(true);
        try {
          const data = await apiGet(`/api/locations/${id}`);
          setLocation({
            locationName: data.locationName || "",
            settlementType: data.settlementType || "",
            establishmentYear: data.establishmentYear || "",
            gpsLatitude: data.gpsLatitude || "",
            gpsLongitude: data.gpsLongitude || "",
          });
        } catch (error) {
          setError("Failed to load location: " + (error.message || error));
        } finally {
          setLoading(false);
        }
      } else {
        setLoading(false);
      }
    };
    fetchLocation();
  }, [id]);

  // Auto-hide flash messages
  useEffect(() => {
    if (sentState) {
      const timer = setTimeout(() => setSent(false), 3000);
      return () => clearTimeout(timer);
    }
  }, [sentState]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);

    const locationToSend = {
      ...location,
      establishmentYear: location.establishmentYear
        ? Number(location.establishmentYear)
        : null,
      gpsLatitude: location.gpsLatitude ? parseFloat(location.gpsLatitude) : null,
      gpsLongitude: location.gpsLongitude ? parseFloat(location.gpsLongitude) : null,
    };

    try {
      const request = id
        ? apiPut(`/api/locations/${id}`, locationToSend)
        : apiPost("/api/locations", locationToSend);

      await request;
      setSent(true);
      setSuccess(true);
      setTimeout(() => navigate("/locations"), 1000);
    } catch (error) {
      setError(error.message || "Failed to save location");
      setSent(true);
      setSuccess(false);
    }
  };

  if (loading) {
    return (
      <Container className="my-5 text-center">
        <Spinner animation="border" role="status" />
        <p className="mt-3 text-muted">Loading location data...</p>
      </Container>
    );
  }

  return (
    <Container className="my-4">
      <Row className="justify-content-center">
        <Col md={8} lg={6}>
          <Card className="shadow-sm border-0 rounded-3">
            <Card.Body className="p-4">
              <h3 className="text-center text-primary mb-4">
                {id ? "Update Location" : "Create Location"}
              </h3>

              {errorState && <Alert variant="danger">{errorState}</Alert>}
              {sentState && (
                <FlashMessage
                  theme={successState ? "success" : "danger"}
                  text={
                    successState
                      ? "Location saved successfully."
                      : "Error saving location."
                  }
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
                    <Form.Group className="mb-3" controlId="settlementType">
                      <Form.Label className="fw-semibold">Settlement Type</Form.Label>
                      <Form.Select
                        required
                        value={location.settlementType}
                        onChange={(e) =>
                          setLocation({ ...location, settlementType: e.target.value })
                        }
                        size="sm"
                      >
                        <option value="">Select settlement type</option>
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
                  <Col md={4}>
                    <InputField
                      type="number"
                      name="establishmentYear"
                      label="Establishment Year"
                      prompt="Enter establishment year (e.g., 1800)"
                      value={location.establishmentYear}
                      handleChange={(e) =>
                        setLocation({ ...location, establishmentYear: e.target.value })
                      }
                    />
                  </Col>
                  <Col md={4}>
                    <InputField
                      type="number"
                      name="gpsLatitude"
                      label="GPS Latitude"
                      prompt="Enter GPS latitude (e.g., 49.8175)"
                      value={location.gpsLatitude}
                      handleChange={(e) =>
                        setLocation({ ...location, gpsLatitude: e.target.value })
                      }
                      step="any"
                    />
                  </Col>
                  <Col md={4}>
                    <InputField
                      type="number"
                      name="gpsLongitude"
                      label="GPS Longitude"
                      prompt="Enter GPS longitude (e.g., 15.4730)"
                      value={location.gpsLongitude}
                      handleChange={(e) =>
                        setLocation({ ...location, gpsLongitude: e.target.value })
                      }
                      step="any"
                    />
                  </Col>
                </Row>

                <div className="d-flex justify-content-end">
                  <Button type="submit" variant="primary" size="sm" className="px-4">
                    <i className="fas fa-save me-2"></i>
                    {id ? "Update" : "Create"}
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

export default LocationForm;
