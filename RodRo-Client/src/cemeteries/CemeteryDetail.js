import React, { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import { Container, Row, Col, Card, ListGroup, Alert, Spinner } from "react-bootstrap";
import { apiGet } from "../utils/api";
import { useSession } from "../contexts/session";

const CemeteryDetail = () => {
  const { id } = useParams();
  const { session } = useSession();
  const isAdmin = session.data?.isAdmin === true;

  const [cemetery, setCemetery] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      setError(null);
      try {
        const cemeteryData = await apiGet(`/api/cemeteries/${id}`);
        setCemetery(cemeteryData);
      } catch (err) {
        console.error("Error fetching cemetery:", err);
        setError(err.message || "Failed to fetch cemetery data.");
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [id]);

  if (loading) {
    return (
      <Container className="my-5 text-center">
        <Spinner animation="border" role="status" />
        <p className="mt-3 text-muted">Loading cemetery details...</p>
      </Container>
    );
  }

  if (error) {
    return (
      <Container className="my-5">
        <Alert variant="danger" className="text-center shadow-sm rounded-3">
          <i className="fas fa-exclamation-triangle me-2"></i>
          {error}
        </Alert>
      </Container>
    );
  }

  if (!cemetery) {
    return (
      <Container className="my-5">
        <Alert variant="info" className="text-center shadow-sm rounded-3">
          <i className="fas fa-info-circle me-2"></i>
          Cemetery not found.
        </Alert>
      </Container>
    );
  }

  // Historical context cards
  const cemeteryContext = [
    {
      title: "Confessional Structure",
      icon: "fas fa-people-arrows",
      text: "Earlier cemeteries were strictly segregated by confession. Later, shared municipal cemeteries emerged with distinct zones for each faith."
    },
    {
      title: "Marker Evolution",
      icon: "fas fa-monument",
      text: "Wooden crosses once dominated but decayed quickly. By the 19th century, durable stone markers became symbols of permanence and affluence."
    },
    {
      title: "Modern Landscape",
      icon: "fas fa-city",
      text: "Today’s cemeteries reveal contrasts: grassy areas where wooden markers vanished, alongside enduring stone monuments and mausoleums."
    }
  ];

  return (
    <Container className="my-5 py-4 bg-light rounded shadow-lg">
      <Row className="mb-4 text-center">
        <Col md={12}>
          <h1 className="display-4 fw-bold text-primary mb-2">
            <i className="fas fa-cross me-3"></i>
            {cemetery.cemeteryName || "N/A"}
          </h1>

          {isAdmin && (
            <Link
              to={`/cemeteries/edit/${cemetery._id}`}
              className="btn btn-warning btn-lg rounded-pill px-4 py-2 shadow-sm mt-3"
            >
              <i className="fas fa-edit me-2"></i>Edit Cemetery
            </Link>
          )}
        </Col>
      </Row>

      <Row className="justify-content-center">
        {/* Left: Basic Info */}
        <Col md={6} className="mb-4">
          <Card className="shadow-lg border-0 rounded-4 h-100">
            <Card.Header
              as="h5"
              className="bg-primary text-white py-3 rounded-top-4"
            >
              <i className="fas fa-info-circle me-2"></i>Basic Information
            </Card.Header>
            <Card.Body className="p-4">
              <ListGroup variant="flush">
                <ListGroup.Item className="d-flex justify-content-between align-items-center px-0">
                  <strong>Location:</strong>
                  <span>
                    {cemetery.cemeteryLocation ? (
                      <Link
                        to={`/locations/show/${cemetery.cemeteryLocation._id}`}
                        className="text-decoration-none text-primary"
                      >
                        {cemetery.cemeteryLocation.locationName}
                      </Link>
                    ) : (
                      "N/A"
                    )}
                  </span>
                </ListGroup.Item>

                <ListGroup.Item className="d-flex justify-content-between align-items-center px-0">
                  <strong>Web Link:</strong>
                  <span>
                    {cemetery.webLink ? (
                      <a
                        href={cemetery.webLink}
                        target="_blank"
                        rel="noopener noreferrer"
                        className="text-decoration-none text-primary"
                      >
                        {cemetery.webLink}
                      </a>
                    ) : (
                      "N/A"
                    )}
                  </span>
                </ListGroup.Item>

                <ListGroup.Item className="px-0">
                  <strong className="d-block mb-2">
                    <i className="fas fa-align-left me-2 text-secondary"></i>
                    Description:
                  </strong>
                  <div className="p-3 bg-light border rounded shadow-sm">
                    <p className="mb-0" style={{ whiteSpace: "pre-line" }}>
                      {cemetery.description || "No description available."}
                    </p>
                  </div>
                </ListGroup.Item>
              </ListGroup>
            </Card.Body>
          </Card>
        </Col>

        {/* Right: Image */}
        <Col md={6} className="mb-4">
          <Card className="shadow-lg border-0 rounded-4 h-100">
            <Card.Header
              as="h5"
              className="bg-primary text-white py-3 rounded-top-4"
            >
              <i className="fas fa-image me-2"></i>Cemetery Image
            </Card.Header>
            <Card.Body className="p-4 d-flex justify-content-center align-items-center">
              {cemetery.imageUrl ? (
                <img
                  src={cemetery.imageUrl}
                  alt={cemetery.cemeteryName}
                  style={{
                    maxWidth: "100%",
                    height: "auto",
                    maxHeight: "300px",
                    borderRadius: "0.5rem",
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

      {/* Historical Context Section */}
      <Row className="mt-5">
        <Col>
          <h2 className="fw-bold text-dark mb-3">
            <i className="fas fa-history me-2 text-primary"></i>
            Historical Context & Evolution
          </h2>
        </Col>
      </Row>

      <Row className="g-4">
        {cemeteryContext.map((item, index) => (
          <Col md={4} key={index}>
            <Card className="h-100 shadow-sm border-start border-primary border-4 rounded-3">
              <Card.Body className="p-3">
                <h6 className="fw-bold text-primary mb-2">
                  <i className={`${item.icon} me-2`}></i> {item.title}
                </h6>
                <p className="text-muted small mb-0">{item.text}</p>
              </Card.Body>
            </Card>
          </Col>
        ))}
      </Row>
    </Container>
  );
};

export default CemeteryDetail;
