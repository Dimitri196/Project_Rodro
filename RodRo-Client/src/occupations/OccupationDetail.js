import React, { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import { apiGet } from "../utils/api";
import { Container, Row, Col, Card, Alert, ListGroup } from "react-bootstrap";

const OccupationDetail = () => {
  const { id } = useParams();
  const [occupation, setOccupation] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchOccupation = async () => {
      try {
        const data = await apiGet(`/api/occupations/${id}`);
        setOccupation(data);
      } catch (err) {
        setError("Occupation not found.");
      } finally {
        setLoading(false);
      }
    };

    fetchOccupation();
  }, [id]);

  if (loading) return <p>Loading occupation...</p>;
  if (error) return <Alert variant="danger">{error}</Alert>;

  const institution = occupation.institution;
  const location = institution?.institutionLocation;
  const personOccupations = occupation.personOccupations || [];

  return (
    <Container className="mt-5">
      <Row>
        <Col md={{ span: 8, offset: 2 }}>
          <Card>
            <Card.Body>
              <Card.Title>{occupation.occupationName}</Card.Title>
              <p>
                <strong>Institution:</strong>{" "}
                {institution ? (
                  <Link to={`/institutions/show/${institution._id}`}>
                    {institution.institutionName}
                  </Link>
                ) : (
                  "N/A"
                )}
              </p>
              <p><strong>Location:</strong> {location?.locationName || "N/A"}</p>
              <p><strong>Description:</strong> {occupation.description || "N/A"}</p>
              <hr />
              <h5>Persons with this occupation:</h5>
              {personOccupations.length === 0 ? (
                <p>No persons linked to this occupation.</p>
              ) : (
                <ListGroup>
                  {personOccupations.map((po) => (
                    <ListGroup.Item key={po._id}>
                      {po.personId ? (
                        <Link to={`/persons/show/${po.personId}`}>
                          {po.givenName && po.givenSurname
                            ? `${po.givenName} ${po.givenSurname}`
                            : `Person #${po.personId}`}
                        </Link>
                      ) : (
                        <span>Unknown person</span>
                      )}
                    </ListGroup.Item>
                  ))}
                </ListGroup>
              )}
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
};

export default OccupationDetail;