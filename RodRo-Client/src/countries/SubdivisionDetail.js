import React, { useEffect, useState } from "react";
import { useParams, Link, useNavigate } from "react-router-dom";
import { apiGet } from "../utils/api";
import {
  Container,
  Card,
  ListGroup,
  Spinner,
  Alert,
  Button,
  Row,
  Col,
} from "react-bootstrap";

const SubdivisionDetail = () => {
  const { id } = useParams();
  const navigate = useNavigate();

  const [subdivision, setSubdivision] = useState(null);
  const [sortedLocations, setSortedLocations] = useState([]);
  const [sortAsc, setSortAsc] = useState(true);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // Sort helper
  const sortLocationList = (list, asc) => {
    const sorted = [...list].sort((a, b) => {
      const nameA = a.locationName?.toLowerCase() || "";
      const nameB = b.locationName?.toLowerCase() || "";
      return asc ? nameA.localeCompare(nameB) : nameB.localeCompare(nameA);
    });
    setSortedLocations(sorted);
  };

  const toggleSort = () => {
    const newSort = !sortAsc;
    setSortAsc(newSort);
    sortLocationList(subdivision?.locations || [], newSort);
  };

  useEffect(() => {
    const fetchSubdivision = async () => {
      try {
        const data = await apiGet(`/api/subdivisions/${id}`);
        setSubdivision(data);
        if (data.locations) {
          sortLocationList(data.locations, true);
        }
      } catch (err) {
        setError(`Error loading subdivision: ${err.message || err}`);
      } finally {
        setLoading(false);
      }
    };

    fetchSubdivision();
  }, [id]);

  // Loading state
  if (loading) {
    return (
      <Container className="mt-5 text-center">
        <Spinner animation="border" variant="primary" />
        <p>Loading subdivision details...</p>
      </Container>
    );
  }

  // Error state
  if (error) {
    return (
      <Container className="mt-5">
        <Alert variant="danger">{error}</Alert>
        <Button variant="secondary" onClick={() => navigate(-1)}>
          Go Back
        </Button>
      </Container>
    );
  }

  if (!subdivision) return null;

  const {
    subdivisionName = "N/A",
    subdivisionFlagImgUrl,
    district,
    administrativeCenter,
    subdivisionEstablishmentYear,
    subdivisionCancellationYear,
  } = subdivision;

  return (
    <Container className="mt-5">
      <Row className="mb-4">
        {/* Left Column: Subdivision Details */}
        <Col md={8}>
          <Card className="shadow-lg border-0 rounded-4 h-100">
            <Card.Header
              as="h5"
              className="bg-primary text-white py-3 rounded-top-4"
            >
              <i className="fas fa-info-circle me-2"></i> Subdivision Details
            </Card.Header>
            <Card.Body className="p-4">
              <Card.Title as="h3" className="mb-3">
                {subdivision.name || "-"}
              </Card.Title>


              <ListGroup variant="flush">
                <ListGroup.Item className="d-flex justify-content-between align-items-center px-0">
                  <strong>District:</strong>
                  <span>
                    {district?._id ? (
                      <Link
                        to={`/countries/${district.countryId}/provinces/${district.provinceId}/districts/${district._id}`}
                      >
                        {district.name}
                      </Link>
                    ) : (
                      district?.name || "-"
                    )}
                  </span>
                </ListGroup.Item>

                <ListGroup.Item className="d-flex justify-content-between align-items-center px-0">
                  <strong>Administrative Center:</strong>
                  <span>
                    {administrativeCenter?._id ? (
                      <Link
                        to={`/locations/show/${administrativeCenter._id}`}
                        className="text-decoration-none text-primary"
                      >
                        {administrativeCenter.locationName}
                      </Link>
                    ) : (
                      administrativeCenter?.locationName || "-"
                    )}
                  </span>
                </ListGroup.Item>


                <ListGroup.Item className="d-flex justify-content-between align-items-center px-0">
                  <strong>Establishment Year:</strong>
                  <span>{subdivisionEstablishmentYear || "-"}</span>
                </ListGroup.Item>

                <ListGroup.Item className="d-flex justify-content-between align-items-center px-0">
                  <strong>Cancellation Year:</strong>
                  <span>{subdivisionCancellationYear || "-"}</span>
                </ListGroup.Item>
              </ListGroup>
            </Card.Body>
          </Card>
        </Col>


        {/* Right Column: Subdivision Flag / Image */}
        <Col md={4}>
          <Card className="shadow-lg border-0 rounded-4 h-100">
            <Card.Header
              as="h5"
              className="bg-secondary text-white py-3 rounded-top-4"
            >
              <i className="fas fa-flag me-2"></i> Subdivision Flag / Image
            </Card.Header>
            <Card.Body className="d-flex justify-content-center align-items-center">
              {subdivisionFlagImgUrl ? (
                <img
                  src={subdivisionFlagImgUrl}
                  alt={`${subdivisionName} flag`}
                  className="img-fluid rounded shadow-sm"
                  style={{ maxHeight: "250px" }}
                />
              ) : (
                <div className="text-center text-muted">
                  <i className="fas fa-image fa-3x mb-3"></i>
                  <p>No flag image available.</p>
                </div>
              )}
            </Card.Body>
          </Card>
        </Col>
      </Row>

      {/* Locations List */}
      <Card className="mt-4 shadow-sm border-0 rounded-4">
        <Card.Header
          as="h5"
          className="bg-primary text-white py-3 rounded-top-4"
        >
          <i className="fas fa-globe me-2"></i> Locations
        </Card.Header>
        <Card.Body>
          <div className="d-flex justify-content-between align-items-center mb-3">
            <Button
              variant="outline-light"
              size="sm"
              className="text-white border-white"
              onClick={toggleSort}
            >
              Sort {sortAsc ? "↓ A–Z" : "↑ Z–A"}
            </Button>
          </div>

          {sortedLocations.length > 0 ? (
            <Row xs={1} sm={2} md={3} lg={4} className="g-3">
              {sortedLocations.map((loc) => (
                <Col key={loc._id}>
                  <Card className="h-100 shadow-sm border-0 rounded-3">
                    <Card.Body className="d-flex align-items-center">
                      <i
                        className="fas fa-map-marker-alt text-danger me-2"
                        style={{ fontSize: "0.9rem" }}
                      ></i>
                      <Link
                        to={`/locations/show/${loc._id}`}
                        className="text-decoration-none fw-bold text-dark"
                      >
                        {loc.locationName || "Unnamed Location"}
                      </Link>
                    </Card.Body>
                  </Card>
                </Col>
              ))}
            </Row>
          ) : (
            <Alert variant="info" className="mb-0">
              <i className="fas fa-info-circle me-2"></i>
              No locations available for this subdivision.
            </Alert>
          )}
        </Card.Body>
      </Card>
    </Container>
  );
};

export default SubdivisionDetail;
