import React, { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import { Container, Row, Col, Card, ListGroup, Alert, Spinner } from "react-bootstrap";

import { apiGet } from "../utils/api";
import dateStringFormatter from "../utils/dateStringFormatter";

const InstitutionDetail = () => {
  const { id } = useParams();

  const [institution, setInstitution] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchInstitution = async () => {
      try {
        const data = await apiGet(`/api/institutions/${id}`);
        setInstitution(data);
      } catch (err) {
        setError("Institution not found.");
      } finally {
        setLoading(false);
      }
    };

    fetchInstitution();
  }, [id]);

  if (loading) {
    return (
      <Container className="mt-5 text-center">
        <Spinner animation="border" role="status" />
        <p className="mt-2">Loading institution...</p>
      </Container>
    );
  }

  if (error) {
    return (
      <Container className="mt-5">
        <Alert variant="danger">{error}</Alert>
      </Container>
    );
  }

  const { institutionName, institutionDescription, institutionLocation } = institution;

  return (
    <Container className="mt-5">
      <Row>
        <Col md={{ span: 8, offset: 2 }}>
          <Card>
<Card.Body>
  <Card.Title>{institutionName}</Card.Title>
  <ListGroup variant="flush" className="mb-3">
    <ListGroup.Item>
      <strong>Location:</strong>{" "}
      {institutionLocation?._id ? (
        <Link to={`/locations/show/${institutionLocation._id}`}>
          {institutionLocation.locationName}
        </Link>
      ) : (
        "N/A"
      )}
    </ListGroup.Item>
  </ListGroup>
  <p>
    <strong>Description:</strong>{" "}
    {institutionDescription || "N/A"}
  </p>
{institution.occupations && institution.occupations.length > 0 && (
  <>
    <h5>Occupations</h5>
    <ListGroup className="mb-3">
      {institution.occupations.map((occupation) => (
        <ListGroup.Item key={occupation._id}>
          <strong>
            <Link to={`/occupations/show/${occupation._id}`}>
              {occupation.occupationName}
            </Link>
          </strong>
          {occupation.description && (
            <div className="text-muted">{occupation.description}</div>
          )}
        </ListGroup.Item>
      ))}
    </ListGroup>
  </>
)}
</Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
};

export default InstitutionDetail;
