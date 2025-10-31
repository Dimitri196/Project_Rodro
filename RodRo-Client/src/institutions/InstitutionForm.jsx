import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Form, Button, Container, Row, Col, Alert, Card, Spinner } from "react-bootstrap";
import AsyncSelect from "react-select/async";
import InputField from "../components/InputField";
import FlashMessage from "../components/FlashMessage";
import { apiGet, apiPost, apiPut } from "../utils/api";
import "bootstrap/dist/css/bootstrap.min.css";

const InstitutionForm = () => {
  const navigate = useNavigate();
  const { id } = useParams();

  const [institution, setInstitution] = useState({
    institutionName: "",
    institutionDescription: "",
    institutionLocation: null,
  });
  const [locations, setLocations] = useState([]); 
  const [errorState, setError] = useState(null);
  const [sentState, setSent] = useState(false);
  const [successState, setSuccess] = useState(false);
  const [selectedLocationOption, setSelectedLocationOption] = useState(null);
  const [loadingInstitution, setLoadingInstitution] = useState(false);

  // Load institution if editing
  useEffect(() => {
    if (id) {
      setLoadingInstitution(true);
      apiGet(`/api/institutions/${id}`)
        .then((data) => {
          let updatedInstitution = data;

          const locationId = data.institutionLocation?._id ?? data.institutionLocation?.id;

          if (locationId) {
            setSelectedLocationOption({
              value: locationId,
              label: data.institutionLocation.locationName || "Loading...",
            });

            updatedInstitution = {
              ...data,
              institutionLocation: data.institutionLocation,
            };
          }

          setInstitution(updatedInstitution);
          setLoadingInstitution(false);
        })
        .catch((error) => {
          setError(error.message);
          setLoadingInstitution(false);
        });
    }
  }, [id]);

  // Load location options async
  const loadLocationOptions = (inputValue, callback) => {
    const searchTerm = inputValue || "";
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

  // Handle location change
  const handleLocationChange = (selectedOption) => {
    setSelectedLocationOption(selectedOption);

    if (!selectedOption) {
      setInstitution((prev) => ({ ...prev, institutionLocation: null }));
      return;
    }

    const cachedLocation = locations.find(
      (loc) => (loc._id ?? loc.id) === selectedOption.value
    );

    if (cachedLocation) {
      setInstitution((prev) => ({ ...prev, institutionLocation: cachedLocation }));
    } else {
      apiGet(`/api/locations/${selectedOption.value}`)
        .then((fullLoc) => {
          setInstitution((prev) => ({ ...prev, institutionLocation: fullLoc }));
        })
        .catch(() => {
          setInstitution((prev) => ({
            ...prev,
            institutionLocation: { _id: selectedOption.value },
          }));
        });
    }
  };

  // Submit handler
  const handleSubmit = (e) => {
    e.preventDefault();

    const locationId = institution.institutionLocation?._id ?? institution.institutionLocation?.id;

    const institutionToSend = {
      ...institution,
      institutionLocation: locationId ? { _id: locationId } : null,
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
                          name="institutionName"
                          min="3"
                          label="Institution Name"
                          prompt="Input name of institution"
                          value={institution.institutionName}
                          handleChange={(e) => setInstitution({ ...institution, institutionName: e.target.value })}
                        />
                      </Col>
                    </Row>

                    <Row className="mb-3">
                      <Col md={12}>
                        <InputField
                          type="textarea"
                          name="institutionDescription"
                          label="Institution Description"
                          prompt="Input description of institution"
                          value={institution.institutionDescription}
                          handleChange={(e) => setInstitution({ ...institution, institutionDescription: e.target.value })}
                        />
                      </Col>
                    </Row>

                    <Row className="mb-4">
                      <Col md={12}>
                        <label>Institution Location:</label>
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

                    <Button type="submit" variant="primary" className="w-100 mt-3">
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
