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
import InputSelect from "../components/InputSelect";
import InputField from "../components/InputField";
import FlashMessage from "../components/FlashMessage";

const ParishForm = () => {
  const navigate = useNavigate();
  const { id } = useParams();

  const [parish, setParish] = useState({
    name: "",
    mainChurchName: "",
    establishmentYear: "",
    cancellationYear: "",
    churchImageUrl: "",
    description: "",
    location: null,
  });

  const [locations, setLocations] = useState([]);
  const [sentState, setSent] = useState(false);
  const [successState, setSuccess] = useState(false);
  const [errorState, setError] = useState(null);
  const [loading, setLoading] = useState(true);

  // Fetch initial data
  useEffect(() => {
    const fetchData = async () => {
      try {
        const locsResponse = await apiGet("/api/locations?size=1000");
        setLocations(locsResponse.content || []);

        if (id) {
          const data = await apiGet(`/api/parishes/${id}`);
          const location = data.location
            ? { ...data.location, _id: data.location.id || data.location._id }
            : null;

          setParish({
            ...data,
            establishmentYear: data.establishmentYear || "",
            cancellationYear: data.cancellationYear || "",
            location,
          });
        }
      } catch (error) {
        setError("Failed to load initial data: " + error.message);
      } finally {
        setLoading(false);
      }
    };
    fetchData();
  }, [id]);

  // Flash message auto-hide
  useEffect(() => {
    if (sentState) {
      const timer = setTimeout(() => setSent(false), 3000);
      return () => clearTimeout(timer);
    }
  }, [sentState]);

  const handleLocationChange = (selectedLocationId) => {
    const selected = locations.find(
      (loc) => loc.id === Number(selectedLocationId)
    );
    setParish((prev) => ({
      ...prev,
      location: selected ? { ...selected, _id: selected.id } : null,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!parish.location || !parish.location._id) {
      setError("Parish location must be provided with a valid id.");
      return;
    }

    const parishToSend = {
      ...parish,
      establishmentYear: parish.establishmentYear
        ? Number(parish.establishmentYear)
        : null,
      cancellationYear: parish.cancellationYear
        ? Number(parish.cancellationYear)
        : null,
      location: { _id: parish.location._id },
    };

    try {
      const request = id
        ? apiPut(`/api/parishes/${id}`, parishToSend)
        : apiPost("/api/parishes", parishToSend);

      await request;

      setSent(true);
      setSuccess(true);
      setTimeout(() => navigate("/parishes"), 1000);
    } catch (error) {
      setError(error.message || "Failed to save parish");
      setSent(true);
      setSuccess(false);
    }
  };

  if (loading) {
    return (
      <Container className="my-5 text-center">
        <Spinner animation="border" role="status" />
        <p className="mt-3 text-muted">Loading parish data...</p>
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
                {id ? "Update Parish" : "Create Parish"}
              </h3>

              {errorState && <Alert variant="danger">{errorState}</Alert>}
              {sentState && (
                <FlashMessage
                  theme={successState ? "success" : "danger"}
                  text={
                    successState
                      ? "Parish saved successfully."
                      : "Error saving parish."
                  }
                />
              )}

              <Form onSubmit={handleSubmit}>
                <Row>
                  <Col md={6}>
                    <Form.Group className="mb-3" controlId="name">
                      <Form.Label className="fw-semibold">Parish Name</Form.Label>
                      <Form.Control
                        size="sm"
                        type="text"
                        required
                        value={parish.name}
                        onChange={(e) =>
                          setParish({ ...parish, name: e.target.value })
                        }
                        placeholder="Enter parish name"
                      />
                    </Form.Group>
                  </Col>
                  <Col md={6}>
                    <Form.Group className="mb-3" controlId="mainChurchName">
                      <Form.Label className="fw-semibold">
                        Church Name
                      </Form.Label>
                      <Form.Control
                        size="sm"
                        type="text"
                        required
                        value={parish.mainChurchName}
                        onChange={(e) =>
                          setParish({
                            ...parish,
                            mainChurchName: e.target.value,
                          })
                        }
                        placeholder="Enter church name"
                      />
                    </Form.Group>
                  </Col>
                </Row>

                <Row>
                  <Col md={6}>
                    <Form.Group className="mb-3" controlId="establishmentYear">
                      <Form.Label className="fw-semibold">
                        Establishment Year
                      </Form.Label>
                      <Form.Control
                        size="sm"
                        type="number"
                        value={parish.establishmentYear}
                        onChange={(e) =>
                          setParish({
                            ...parish,
                            establishmentYear: e.target.value,
                          })
                        }
                        placeholder="Year"
                      />
                    </Form.Group>
                  </Col>
                  <Col md={6}>
                    <Form.Group className="mb-3" controlId="cancellationYear">
                      <Form.Label className="fw-semibold">
                        Cancellation Year
                      </Form.Label>
                      <Form.Control
                        size="sm"
                        type="number"
                        value={parish.cancellationYear}
                        onChange={(e) =>
                          setParish({
                            ...parish,
                            cancellationYear: e.target.value,
                          })
                        }
                        placeholder="Year"
                      />
                    </Form.Group>
                  </Col>
                </Row>

                <Row>
                  <Col md={6}>
                    <Form.Group className="mb-3" controlId="location">
                      <Form.Label className="fw-semibold">
                        Parish Location
                      </Form.Label>
                      <Form.Select
                        size="sm"
                        value={parish.location?._id || ""}
                        onChange={(e) => handleLocationChange(e.target.value)}
                      >
                        <option value="">Select parish location</option>
                        {locations.map((loc) => (
                          <option key={loc.id} value={loc.id}>
                            {loc.locationName}
                          </option>
                        ))}
                      </Form.Select>
                    </Form.Group>
                  </Col>
                  <Col md={6}>
                    <Form.Group className="mb-3" controlId="description">
                      <Form.Label className="fw-semibold">Description</Form.Label>
                      <Form.Control
                        as="textarea"
                        rows={3}
                        size="sm"
                        value={parish.description || ""}
                        onChange={(e) =>
                          setParish({
                            ...parish,
                            description: e.target.value,
                          })
                        }
                        placeholder="Optional description"
                      />
                    </Form.Group>
                  </Col>
                </Row>

                <div className="d-flex justify-content-end">
                  <Button
                    type="submit"
                    variant="primary"
                    size="sm"
                    className="px-4"
                  >
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

export default ParishForm;
