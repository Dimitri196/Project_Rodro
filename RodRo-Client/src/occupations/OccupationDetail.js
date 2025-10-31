import React, { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import { Container, Row, Col, Card, ListGroup, Alert, Spinner, Badge } from "react-bootstrap";
import { apiGet } from "../utils/api";
import { institutionTypes } from "../constants/institutionTypes"; // mapping type -> display

const OccupationDetail = () => {
  const { id } = useParams();
  const [occupation, setOccupation] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchOccupation = async () => {
      setLoading(true);
      setError(null);
      try {
        const data = await apiGet(`/api/occupations/${id}`);
        setOccupation(data);
      } catch (err) {
        console.error("Error fetching occupation:", err);
        setError(`Failed to load occupation details: ${err.message || err}`);
      } finally {
        setLoading(false);
      }
    };
    fetchOccupation();
  }, [id]);

  if (loading) {
    return (
      <Container className="my-5 text-center">
        <Spinner animation="border" role="status" />
        <p className="mt-3 text-muted">Loading occupation details...</p>
      </Container>
    );
  }

  if (error) {
    return (
      <Container className="my-5">
        <Alert variant="danger" className="text-center shadow-sm rounded-3">
          <i className="fas fa-exclamation-triangle me-2"></i>{error}
        </Alert>
      </Container>
    );
  }

  if (!occupation) {
    return (
      <Container className="my-5">
        <Alert variant="info" className="text-center shadow-sm rounded-3">
          <i className="fas fa-info-circle me-2"></i>Occupation not found.
        </Alert>
      </Container>
    );
  }

  const { occupationName, description, personImageUrl, institution, personOccupations } = occupation;

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
            <i className="fas fa-briefcase me-3"></i>{occupationName}
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
                  <strong>Name:</strong> <span>{occupationName}</span>
                </ListGroup.Item>

                {institution && (
  <>
    {/* Institution Name */}
    <ListGroup.Item className="d-flex justify-content-between align-items-center px-0">
      <strong>Institution:</strong>
      <span>
        {institution._id ? (
          <Link
            to={`/institutions/show/${institution._id}`}
            className="text-decoration-none text-primary fw-bold"
          >
            {institution.institutionName}
          </Link>
        ) : "N/A"}
      </span>
    </ListGroup.Item>

    {/* Institution Type Badge */}
    {institution.institutionType && (
      <ListGroup.Item className="d-flex justify-content-between align-items-center px-0">
        <strong>Type:</strong>
        <Badge
          bg="info"
          text="dark"
          className="px-2 py-1 rounded-pill shadow-sm"
        >
          {institutionTypes[institution.institutionType] || institution.institutionType}
        </Badge>
      </ListGroup.Item>
    )}
  </>
)}
                <ListGroup.Item className="px-0">
                  <strong>Description:</strong>
                  <p className="mb-0">{description || "-"}</p>
                </ListGroup.Item>
              </ListGroup>
            </Card.Body>
          </Card>
        </Col>

        {/* Occupation Image */}
        <Col md={6} className="mb-4">
          <Card className="shadow-lg border-0 rounded-4 h-100">
            <Card.Header as="h5" className="bg-primary text-white py-3 rounded-top-4">
              <i className="fas fa-user me-2"></i>Occupation Image
            </Card.Header>
            <Card.Body className="p-4 d-flex justify-content-center align-items-center">
              {personImageUrl ? (
                <img
                  src={personImageUrl}
                  alt={occupationName}
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
                  <p>No image available.</p>
                </div>
              )}
            </Card.Body>
          </Card>
        </Col>
      </Row>

      {/* Associated Persons */}
      <Row className="justify-content-center">
        <Col md={12} className="mb-4">
          <Card className="shadow-lg border-0 rounded-4 h-100">
            <Card.Header as="h5" className="bg-primary text-white py-3 rounded-top-4">
              <i className="fas fa-users me-2"></i>Associated Persons
            </Card.Header>
            <Card.Body className="p-4">
              {personOccupations?.length > 0 ? (
                <ListGroup>
                  {personOccupations.map(po => (
                    <ListGroup.Item key={po._id} className="pb-3 px-0 border-bottom d-flex align-items-center">
                      {po.personImageUrl ? (
                        <img
                          src={po.personImageUrl}
                          alt="Person"
                          className="rounded-circle me-3"
                          style={{ width: "40px", height: "40px", objectFit: "cover" }}
                        />
                      ) : (
                        <i className="fas fa-user-circle fa-2x text-secondary me-3"></i>
                      )}
                      {po.personId ? (
                        <Link to={`/persons/show/${po.personId}`} className="text-decoration-none">
                          {po.givenName && po.surname
                            ? `${po.givenName} ${po.surname}`
                            : `Person #${po.personId}`}
                        </Link>
                      ) : (
                        <span>Unknown person</span>
                      )}
                    </ListGroup.Item>
                  ))}
                </ListGroup>
              ) : (
                <Alert variant="info" className="text-center mt-3">
                  <i className="fas fa-info-circle me-2"></i>No persons associated with this occupation.
                </Alert>
              )}
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
};

export default OccupationDetail;
