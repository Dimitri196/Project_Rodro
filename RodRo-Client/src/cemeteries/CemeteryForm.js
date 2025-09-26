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
  Card,
  Spinner,
} from "react-bootstrap";
import { AsyncPaginate } from "react-select-async-paginate";
import InputField from "../components/InputField";
import FlashMessage from "../components/FlashMessage";

const CemeteryForm = () => {
  const navigate = useNavigate();
  const { id } = useParams();

  const [cemetery, setCemetery] = useState({
    cemeteryName: "",
    description: "",
    cemeteryLocation: null,
    webLink: "",
  });

  const [loading, setLoading] = useState(false);
  const [sentState, setSent] = useState(false);
  const [successState, setSuccess] = useState(false);
  const [errorState, setError] = useState(null);

  // Load cemetery data for editing
  useEffect(() => {
    const fetchCemetery = async () => {
      if (!id) {
        setLoading(false);
        return;
      }
      setLoading(true);
      try {
        const data = await apiGet(`/api/cemeteries/${id}`);
        setCemetery({
          cemeteryName: data.cemeteryName || "",
          description: data.description || "",
          webLink: data.webLink || "",
          cemeteryLocation: data.cemeteryLocation
            ? {
                value: data.cemeteryLocation._id ?? data.cemeteryLocation.id,
                label: data.cemeteryLocation.locationName,
              }
            : null,
        });
      } catch (err) {
        setError(`Failed to load cemetery data: ${err.message || err}`);
      } finally {
        setLoading(false);
      }
    };
    fetchCemetery();
  }, [id]);

  // Auto-hide flash message after showing
  useEffect(() => {
    if (sentState) {
      const timer = setTimeout(() => setSent(false), 3000);
      return () => clearTimeout(timer);
    }
  }, [sentState]);

  // Load location options async
  const loadLocationOptions = async (search, loadedOptions, { page }) => {
    try {
      const response = await apiGet(
        `/api/locations?searchTerm=${encodeURIComponent(search)}&page=${page}&size=10`
      );
      const options = response.content.map((loc) => ({
        value: loc._id ?? loc.id,
        label: loc.locationName,
      }));
      return {
        options,
        hasMore: response.totalPages > page + 1,
        additional: { page: page + 1 },
      };
    } catch {
      return { options: [], hasMore: false };
    }
  };

  // Submit handler
  const handleSubmit = async (e) => {
    e.preventDefault();
    setError(null);

    const cemeteryToSend = {
      cemeteryName: cemetery.cemeteryName,
      description: cemetery.description,
      webLink: cemetery.webLink,
      cemeteryLocation: cemetery.cemeteryLocation
        ? { _id: cemetery.cemeteryLocation.value }
        : null,
    };

    try {
      if (id) {
        await apiPut(`/api/cemeteries/${id}`, cemeteryToSend);
      } else {
        await apiPost("/api/cemeteries", cemeteryToSend);
      }
      setSuccess(true);
      setSent(true);
      setTimeout(() => navigate("/cemeteries"), 1500);
    } catch (err) {
      setError(err.message || "An unexpected error occurred.");
      setSuccess(false);
      setSent(true);
    }
  };

  if (loading) {
    return (
      <Container className="my-5 text-center">
        <Spinner animation="border" role="status" />
        <p className="mt-3 text-muted">Loading cemetery data...</p>
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
                {id ? "Update Cemetery" : "Create Cemetery"}
              </h3>

              {errorState && <Alert variant="danger">{errorState}</Alert>}
              {sentState && (
                <FlashMessage
                  theme={successState ? "success" : "danger"}
                  text={
                    successState
                      ? "Cemetery saved successfully."
                      : "Error saving cemetery."
                  }
                />
              )}

              <Form onSubmit={handleSubmit}>
                <Form.Group className="mb-3" controlId="cemeteryName">
                  <Form.Label className="fw-semibold">Cemetery Name</Form.Label>
                  <Form.Control
                    size="sm"
                    type="text"
                    required
                    value={cemetery.cemeteryName}
                    onChange={(e) =>
                      setCemetery({ ...cemetery, cemeteryName: e.target.value })
                    }
                    placeholder="Enter cemetery name"
                  />
                </Form.Group>

                <Form.Group className="mb-3" controlId="description">
                  <Form.Label className="fw-semibold">Description</Form.Label>
                  <Form.Control
                    size="sm"
                    type="text"
                    value={cemetery.description}
                    onChange={(e) =>
                      setCemetery({ ...cemetery, description: e.target.value })
                    }
                    placeholder="Optional description"
                  />
                </Form.Group>

                <Form.Group className="mb-3" controlId="cemeteryLocation">
                  <Form.Label className="fw-semibold">
                    Cemetery Location
                  </Form.Label>
                  <AsyncPaginate
                    value={cemetery.cemeteryLocation}
                    loadOptions={loadLocationOptions}
                    onChange={(option) =>
                      setCemetery({ ...cemetery, cemeteryLocation: option })
                    }
                    additional={{ page: 0 }}
                    isClearable
                    debounceTimeout={300}
                    placeholder="Search and select location..."
                    classNamePrefix="select-sm"
                  />
                </Form.Group>

                <Form.Group className="mb-4" controlId="webLink">
                  <Form.Label className="fw-semibold">Web Link</Form.Label>
                  <Form.Control
                    size="sm"
                    type="text"
                    value={cemetery.webLink}
                    onChange={(e) =>
                      setCemetery({ ...cemetery, webLink: e.target.value })
                    }
                    placeholder="https://example.com"
                  />
                </Form.Group>

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

export default CemeteryForm;
