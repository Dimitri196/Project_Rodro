import React, { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import { Container, Row, Col, Card, ListGroup, Alert, Spinner, Badge } from "react-bootstrap";
import { apiGet } from "../utils/api";
import { institutionTypes } from "../constants/institutionTypes";

const InstitutionDetail = () => {
  const { id } = useParams();
  const [institution, setInstitution] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchInstitution = async () => {
      setLoading(true);
      setError(null);
      try {
        const data = await apiGet(`/api/institutions/${id}`);
        setInstitution(data);
      } catch (err) {
        console.error("Error fetching institution:", err);
        setError(`Failed to load institution details: ${err.message || err}`);
      } finally {
        setLoading(false);
      }
    };

    fetchInstitution();
  }, [id]);

  // Loading state
  if (loading) {
    return (
      <Container className="my-5 text-center">
        <Spinner animation="border" role="status">
          <span className="visually-hidden">Loading...</span>
        </Spinner>
        <p className="mt-3 text-muted">Loading institution details...</p>
      </Container>
    );
  }

  // Error state
  if (error) {
    return (
      <Container className="my-5">
        <Alert variant="danger" className="text-center shadow-sm rounded-3">
          <i className="fas fa-exclamation-triangle me-2"></i>{error}
        </Alert>
      </Container>
    );
  }

  // Not found
  if (!institution) {
    return (
      <Container className="my-5">
        <Alert variant="info" className="text-center shadow-sm rounded-3">
          <i className="fas fa-info-circle me-2"></i>Institution not found.
        </Alert>
      </Container>
    );
  }

  // CRITICAL CHANGE: Use the new flattened DTO field names
  const {
    name, // The new DTO field for the institution name
    description, // The new DTO field for the description
    countryId, // Flat field for Country ID
    countryName, // Flat field for Country Name
    locationId, // Flat field for Location ID
    locationName, // Flat field for Location Name
    establishmentYear, // Flat field
    cancellationYear, // Flat field
    sealImageUrl,
    occupations,
    type, // The new DTO field for institution type
  } = institution;

  // Helper for rendering year range
  const formatYearRange = (start, end) => {
    if (!start && !end) return "N/A";
    if (start && !end) return `Established ${start}`;
    if (!start && end) return `Ceased ${end}`;
    return `${start} - ${end}`;
  }

  return (
    <Container className="my-5 py-4 bg-light rounded shadow-lg">
      <link
        rel="stylesheet"
        href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css"
        crossOrigin="anonymous"
        referrerPolicy="no-referrer"
      />

      {/* Title */}
      <Row className="mb-4">
        <Col md={12} className="text-center">
          <h1 className="display-4 fw-bold text-primary mb-3">
            <i className="fas fa-university me-3"></i>
            {name} {/* Use the new 'name' field */}
          </h1>
        </Col>
      </Row>

      <Row className="justify-content-center">
        {/* Basic Info */}
        <Col md={6} className="mb-4">
          <Card className="shadow-lg border-0 rounded-4 h-100">
            <Card.Header as="h5" className="bg-primary text-white py-3 rounded-top-4">
              <i className="fas fa-info-circle me-2"></i>Basic Information
            </Card.Header>
            <Card.Body className="p-4">
              <ListGroup variant="flush">
                <ListGroup.Item className="d-flex justify-content-between align-items-center px-0">
                  <strong>Name:</strong>
                  <span>{name}</span>
                </ListGroup.Item>

                {/* NEW: Country Link */}
                <ListGroup.Item className="d-flex justify-content-between align-items-center px-0">
                  <strong>Country:</strong>
                  <span>
                    {countryId ? (
                      <Link
                        to={`/countries/show/${countryId}`}
                        className="text-decoration-none text-info fw-semibold"
                      >
                        {countryName}
                      </Link>
                    ) : (
                      "N/A"
                    )}
                  </span>
                </ListGroup.Item>

                {/* Institution Type */}
                <ListGroup.Item className="d-flex justify-content-between align-items-center px-0">
                  <strong>Type:</strong>
                  <Badge bg="info" text="dark" className="px-3 py-2 rounded-pill shadow-sm">
                    {institutionTypes[type] || type || "N/A"}
                  </Badge>
                </ListGroup.Item>

                {/* Location Link (Updated to use flat fields) */}
                <ListGroup.Item className="d-flex justify-content-between align-items-center px-0">
                  <strong>Location:</strong>
                  <span>
                    {locationId ? (
                      <Link
                        to={`/locations/show/${locationId}`}
                        className="text-decoration-none text-primary"
                      >
                        {locationName}
                      </Link>
                    ) : (
                      "N/A"
                    )}
                  </span>
                </ListGroup.Item>
                
                {/* Years */}
                <ListGroup.Item className="d-flex justify-content-between align-items-center px-0">
                  <strong>Active Period:</strong>
                  <span className="text-muted">{formatYearRange(establishmentYear, cancellationYear)}</span>
                </ListGroup.Item>

                <ListGroup.Item className="px-0">
                  <strong>Description:</strong>
                  <p className="mb-0">{description || "-"}</p> {/* Use the new 'description' field */}
                </ListGroup.Item>
              </ListGroup>
            </Card.Body>
          </Card>
        </Col>

        {/* Seal Image */}
        <Col md={6} className="mb-4">
          <Card className="shadow-lg border-0 rounded-4 h-100">
            <Card.Header as="h5" className="bg-primary text-white py-3 rounded-top-4">
              <i className="fas fa-stamp me-2"></i>Institution Seal
            </Card.Header>
            <Card.Body className="p-4 d-flex justify-content-center align-items-center">
              {sealImageUrl ? (
                <img
                  src={sealImageUrl}
                  alt={`${name} seal`}
                  style={{
                    maxWidth: "100%",
                    height: "auto",
                    maxHeight: "300px",
                    borderRadius: "0.5rem",
                    objectFit: "contain",
                    backgroundColor: "#f8f9fa",
                    padding: "1rem",
                  }}
                  className="img-fluid"
                />
              ) : (
                <div className="text-center text-muted">
                  <i className="fas fa-image fa-3x mb-3"></i>
                  <p>No seal image available.</p>
                </div>
              )}
            </Card.Body>
          </Card>
        </Col>
      </Row>

      {/* Occupations */}
      <Row className="justify-content-center">
        <Col md={12} className="mb-4">
          <Card className="shadow-lg border-0 rounded-4 h-100">
            <Card.Header as="h5" className="bg-primary text-white py-3 rounded-top-4">
              <i className="fas fa-briefcase me-2"></i>Associated Occupations
            </Card.Header>
            <Card.Body className="p-4">
              {occupations?.length > 0 ? (
                <ListGroup>
                  {occupations.map((occ) => (
                    <ListGroup.Item key={occ._id} className="pb-3 px-0 border-bottom">
                      <strong>
                        <Link
                          to={`/occupations/show/${occ._id}`}
                          className="text-decoration-none text-primary"
                        >
                          {occ.occupationName}
                        </Link>
                      </strong>
                      {occ.description && (
                        <div className="text-muted small">{occ.description}</div>
                      )}
                    </ListGroup.Item>
                  ))}
                </ListGroup>
              ) : (
                <Alert variant="info" className="text-center mt-3">
                  <i className="fas fa-info-circle me-2"></i>No occupations found for this institution.
                </Alert>
              )}
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
};

export default InstitutionDetail;
