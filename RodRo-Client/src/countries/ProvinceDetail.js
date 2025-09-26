import React, { useEffect, useState } from "react";
import { useParams, Link, useNavigate } from "react-router-dom";
import { apiGet } from "../utils/api";
import {
  Container,
  Row,
  Col,
  Card,
  ListGroup,
  Alert,
  Button,
  Spinner,
} from "react-bootstrap";

const ProvinceDetail = () => {
  const { countryId, provinceId } = useParams();
  const navigate = useNavigate();

  const [province, setProvince] = useState(null);
  const [country, setCountry] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        // Fetch province with districts
        const provinceData = await apiGet(
          `/api/countries/${countryId}/provinces/${provinceId}`
        );
        setProvince(provinceData);

        // Fetch country
        const countryData = await apiGet(`/api/countries/${countryId}`);
        setCountry(countryData);
      } catch (err) {
        console.error("Error fetching data:", err);
        setError(`Error loading province details: ${err.message || err}`);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [countryId, provinceId]);

  if (loading) {
    return (
      <Container className="mt-5 text-center">
        <Spinner animation="border" variant="primary" />
        <p>Loading province details...</p>
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
    provinceName = "N/A",
    provinceFlagImgUrl = null,
    districts = [],
  } = province || {};

  const countryName =
    country?.countryNameInEnglish ||
    country?.countryNameInPolish ||
    province?.country?.countryNameInEnglish ||
    "Unknown Country";

  return (
    <Container className="mt-5">
      {/* Back Button */}
      <Button
        as={Link}
        to={`/countries/show/${country?._id || countryId}`}
        variant="secondary"
        className="mb-4"
      >
        ← Back to {countryName}
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
              <i className="fas fa-map me-2"></i>Province Details
            </Card.Header>
            <Card.Body className="p-4">
              <h3 className="fw-bold mb-4">{provinceName}</h3>
              <ListGroup variant="flush">
                <ListGroup.Item>
                  <strong>Province Name:</strong> {provinceName}
                </ListGroup.Item>
                <ListGroup.Item>
                  <strong>Country:</strong>{" "}
                  {country ? (
                    <Link
                      to={`/countries/show/${country._id || countryId}`}
                      className="text-decoration-none"
                    >
                      {countryName}
                    </Link>
                  ) : (
                    "Unknown"
                  )}
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
              <i className="fas fa-flag me-2"></i>Province Flag
            </Card.Header>
            <Card.Body className="p-4 d-flex justify-content-center align-items-center">
              {provinceFlagImgUrl ? (
                <img
                  src={provinceFlagImgUrl}
                  alt={`${provinceName} flag`}
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

      {/* Districts */}
      <Row>
        <Col md={12}>
          <Card className="shadow-sm border-0 rounded-4">
            <Card.Header
              as="h5"
              className="bg-success text-white py-3 rounded-top-4"
            >
              <i className="fas fa-map-marked-alt me-2"></i>Districts
            </Card.Header>
            <Card.Body className="p-4">
              {districts.length > 0 ? (
                <ListGroup variant="flush">
                  {districts.map((district) => (
                    <ListGroup.Item key={district._id}>
                      <Link
                        to={`/countries/${countryId}/provinces/${provinceId}/districts/${district._id}`}
                        className="text-decoration-none"
                      >
                        {district.districtName}
                      </Link>
                    </ListGroup.Item>
                  ))}
                </ListGroup>
              ) : (
                <p className="mt-3 text-muted">
                  No districts available for this province.
                </p>
              )}
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
};

export default ProvinceDetail;
