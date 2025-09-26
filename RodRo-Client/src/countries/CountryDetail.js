import React, { useEffect, useState } from "react";
import { useParams, useNavigate, Link } from "react-router-dom";
import { apiGet } from "../utils/api";
import {
  Container,
  Row,
  Col,
  Card,
  ListGroup,
  Button,
  Spinner,
  Alert,
} from "react-bootstrap";

const CountryDetail = () => {
  const { id } = useParams();
  const navigate = useNavigate();

  const [country, setCountry] = useState(null);
  const [provinces, setProvinces] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [openProvince, setOpenProvince] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const countryData = await apiGet(`/api/countries/${id}`);
        setCountry(countryData);

        const provincesData = await apiGet(`/api/countries/${id}/provinces`);

        // ✅ Sort provinces by name
        const sortedProvinces = [...provincesData].sort((a, b) =>
          a.provinceName.localeCompare(b.provinceName, undefined, {
            sensitivity: "base",
          })
        );

        setProvinces(sortedProvinces);
      } catch (err) {
        console.error("Error fetching data:", err);
        setError(`Error loading data: ${err.message || err}`);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [id]);

  if (loading) {
    return (
      <Container className="mt-5 text-center">
        <Spinner animation="border" />
        <p>Loading country details...</p>
      </Container>
    );
  }

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

  if (!country) {
    return (
      <Container className="mt-5">
        <Alert variant="warning">No country data found.</Alert>
        <Button variant="secondary" onClick={() => navigate(-1)}>
          Go Back
        </Button>
      </Container>
    );
  }

  const {
    countryNameInPolish,
    countryNameInEnglish,
    countryEstablishmentYear,
    countryCancellationYear,
    countryFlagImgUrl,
  } = country;

  return (
    <Container className="mt-5">
      <Button
        variant="secondary"
        className="mb-4"
        onClick={() => navigate("/countries")}
      >
        ← Back to Countries List
      </Button>

      {/* Top Info Row */}
      <Row className="mb-5">
        {/* Info Card */}
        <Col md={7} lg={8}>
          <Card className="shadow-lg border-0 rounded-4 h-100">
            <Card.Header
              as="h5"
              className="bg-primary text-white py-3 rounded-top-4"
            >
              <i className="fas fa-globe me-2"></i>Country Details
            </Card.Header>
            <Card.Body className="p-4">
              <h3 className="fw-bold mb-4">{countryNameInPolish}</h3>
              <ListGroup variant="flush">
                <ListGroup.Item>
                  <strong>English Name:</strong> {countryNameInEnglish || "N/A"}
                </ListGroup.Item>
                <ListGroup.Item>
                  <strong>Establishment Year:</strong>{" "}
                  {countryEstablishmentYear || "N/A"}
                </ListGroup.Item>
                <ListGroup.Item>
                  <strong>Cancellation Year:</strong>{" "}
                  {countryCancellationYear || "Still Exists"}
                </ListGroup.Item>
              </ListGroup>
            </Card.Body>
          </Card>
        </Col>

        {/* Flag Card */}
        <Col md={5} lg={4} className="mt-4 mt-md-0">
          <Card className="shadow-lg border-0 rounded-4 h-100">
            <Card.Header
              as="h5"
              className="bg-primary text-white py-3 rounded-top-4"
            >
              <i className="fas fa-flag me-2"></i>National Flag
            </Card.Header>
            <Card.Body className="p-4 d-flex justify-content-center align-items-center">
              {countryFlagImgUrl ? (
                <img
                  src={countryFlagImgUrl}
                  alt={`${countryNameInPolish} flag`}
                  style={{
                    maxWidth: "100%",
                    height: "auto",
                    maxHeight: "250px",
                    borderRadius: "0.5rem",
                  }}
                  className="img-fluid"
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

      {/* Provinces Section */}
{/* Provinces Section */}
<Row>
  <Col>
    <Card className="shadow-lg border-0 rounded-4">
      <Card.Header
        as="h5"
        className="bg-success text-white py-3 rounded-top-4"
      >
        <i className="fas fa-map-marked-alt me-2"></i>Provinces
      </Card.Header>
      <Card.Body className="p-4">
        {provinces.length > 0 ? (
          <ListGroup variant="flush">
            {provinces.map((province) => {
              const isOpen = openProvince === province._id;
              return (
                <ListGroup.Item
                  key={province._id}
                  className="px-3 py-2"
                  style={{ border: "none" }}
                >
                  <div className="d-flex justify-content-between align-items-center">
                    {/* Province Name (clickable) */}
                    <Link
                      to={`/countries/${id}/provinces/${province._id}`}
                      className="fw-bold text-primary text-decoration-none"
                    >
                      {province.provinceName}
                    </Link>

                    {/* Toggle arrow */}
                    {province.districts?.length > 0 && (
                      <span
                        role="button"
                        aria-expanded={isOpen}
                        onClick={() =>
                          setOpenProvince(isOpen ? null : province._id)
                        }
                        className="ms-2 text-muted"
                        style={{ cursor: "pointer", fontSize: "1.1rem" }}
                      >
                        {isOpen ? "▼" : "▶"}
                      </span>
                    )}
                  </div>

                  {/* Districts list (expandable) */}
                  {isOpen && province.districts?.length > 0 && (
                    <ul className="mt-2 ps-3 list-unstyled">
                      {province.districts.map((district) => (
                        <li key={district._id} className="mb-1">
                          <Link
                            to={`/countries/${id}/provinces/${province._id}/districts/${district._id}`}
                            className="text-decoration-none text-secondary"
                          >
                            <i className="fas fa-circle me-2" style={{ fontSize: "0.6rem" }}></i>
                            {district.districtName}
                          </Link>
                        </li>
                      ))}
                    </ul>
                  )}
                </ListGroup.Item>
              );
            })}
          </ListGroup>
        ) : (
          <Alert variant="info" className="text-center mb-0">
            <i className="fas fa-info-circle me-2"></i>
            No provinces available for this country.
          </Alert>
        )}
      </Card.Body>
    </Card>
  </Col>
</Row>

    </Container>
  );
};

export default CountryDetail;
