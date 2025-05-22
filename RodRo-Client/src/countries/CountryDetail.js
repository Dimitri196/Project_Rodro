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
  const { id } = useParams(); // Country ID
  const navigate = useNavigate();

  const [country, setCountry] = useState(null);
  const [provinces, setProvinces] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [openProvince, setOpenProvince] = useState(null); // track expanded province

  useEffect(() => {
    const fetchData = async () => {
      try {
        const countryData = await apiGet(`/api/countries/${id}`);
        setCountry(countryData);

    const provincesData = await apiGet(`/api/countries/${id}/provinces`);

    // ✅ Sort provinces by name (case-insensitive ascending)
    const sortedProvinces = [...provincesData].sort((a, b) =>
      a.provinceName.localeCompare(b.provinceName, undefined, { sensitivity: "base" })
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

  const {
    countryNameInPolish = "N/A",
    countryNameInEnglish = "N/A",
    countryEstablishmentYear = "N/A",
    countryCancellationYear = "N/A",
  } = country || {};

  return (
    <Container className="mt-5">
      <Button
        variant="secondary"
        className="mb-4"
        onClick={() => navigate("/countries")}
      >
        ← Back to Countries List
      </Button>

      {/* Country Info */}
      <Row className="mb-4">
        <Col md={8} lg={6}>
          <Card>
            <Card.Body>
              <Card.Title as="h3">{countryNameInPolish}</Card.Title>
              <ListGroup variant="flush" className="mt-3">
                <ListGroup.Item>
                  <strong>Country Name (English):</strong> {countryNameInEnglish}
                </ListGroup.Item>
                <ListGroup.Item>
                  <strong>Establishment Year:</strong> {countryEstablishmentYear}
                </ListGroup.Item>
                <ListGroup.Item>
                  <strong>Cancellation Year:</strong> {countryCancellationYear}
                </ListGroup.Item>
              </ListGroup>
            </Card.Body>
          </Card>
        </Col>
      </Row>

      {/* Provinces & Districts */}
      <Row>
        <Col md={12}>
          <Card>
            <Card.Body>
              <Card.Title as="h4">Provinces</Card.Title>
              {provinces.length > 0 ? (
                <ListGroup variant="flush">
                  {provinces.map((province) => (
                    <ListGroup.Item key={province._id}>
                      {/* Province clickable */}
                      <div
                        role="button"
                        className="fw-bold text-primary d-flex justify-content-between align-items-center"
                        onClick={() =>
                          setOpenProvince(
                            openProvince === province._id ? null : province._id
                          )
                        }
                      >
                        <span>{province.provinceName}</span>
                        <span style={{ fontSize: "1.2rem" }}>
                          {openProvince === province._id ? "▼" : "▶"}
                        </span>
                      </div>

                      {/* Show districts if province is open */}
                      {openProvince === province._id &&
                        province.districts &&
                        province.districts.length > 0 && (
                          <ul className="mt-2 ms-3">
                            {province.districts.map((district) => (
                              <li key={district._id}>
                                <Link
                                  to={`/countries/${id}/provinces/${province._id}/districts/${district._id}`}
                                  className="text-decoration-none"
                                >
                                  {district.districtName}
                                </Link>
                              </li>
                            ))}
                          </ul>
                        )}
                    </ListGroup.Item>
                  ))}
                </ListGroup>
              ) : (
                <p className="mt-3">No provinces available for this country.</p>
              )}
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
};

export default CountryDetail;
