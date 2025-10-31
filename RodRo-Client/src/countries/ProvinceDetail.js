import React, { useEffect, useState } from "react";
import { useParams, Link, useNavigate } from "react-router-dom";
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
  Tab,
  Nav,
} from "react-bootstrap";

const ProvinceDetail = () => {
  const { countryId, provinceId } = useParams();
  const navigate = useNavigate();

  const [province, setProvince] = useState(null);
  const [country, setCountry] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [activeDistrict, setActiveDistrict] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const provinceData = await apiGet(
          `/api/countries/${countryId}/provinces/${provinceId}`
        );
        setProvince(provinceData);

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

  if (!province) {
    return (
      <Container className="mt-5">
        <Alert variant="warning">No province data found.</Alert>
        <Button variant="secondary" onClick={() => navigate(-1)}>
          Go Back
        </Button>
      </Container>
    );
  }

  const { provinceName, provinceFlagImgUrl, districts = [] } = province;
  const countryName =
    country?.countryNameInEnglish ||
    country?.countryNameInPolish ||
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

      {/* Province Info & Flag */}
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
                  <Link
                    to={`/countries/show/${country?._id || countryId}`}
                    className="text-decoration-none text-primary fw-semibold"
                  >
                    {countryName}
                  </Link>
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

      {/* Districts Section with Tabs */}
      <h4 className="fw-bold text-dark mt-4 mb-3">Districts</h4>
      <Tab.Container
        id="province-district-tabs"
        activeKey={activeDistrict || (districts[0]?._id || null)}
        onSelect={(k) => setActiveDistrict(k)}
      >
        <Row>
          {/* Nav Column */}
          <Col md={3} className="mb-3">
            <Nav
              variant="pills"
              className="flex-column p-3 bg-light rounded-3 shadow-sm border"
            >
              {districts.length > 0 ? (
                districts.map((district) => (
                  <Nav.Item key={district._id}>
                    <Nav.Link
                      eventKey={district._id}
                      className="text-start mb-1 fw-semibold"
                    >
                      <i className="fas fa-circle me-2 text-secondary"></i>
                      {district.districtName}
                    </Nav.Link>
                  </Nav.Item>
                ))
              ) : (
                <Nav.Item>
                  <Nav.Link disabled>No districts</Nav.Link>
                </Nav.Item>
              )}
            </Nav>
          </Col>

          {/* District Content Column */}
          <Col md={9}>
<Tab.Content className="p-4 bg-white rounded-3 shadow border">
  {districts.length > 0 ? (
    districts.map((district) => (
      <Tab.Pane key={district._id} eventKey={district._id}>
        <h5 className="fw-bold text-primary mb-3">
          <span className="text-muted me-2">{provinceName} —</span>{" "}
          <Link
            to={`/countries/${countryId}/provinces/${provinceId}/districts/${district._id}`}
            className="text-decoration-none text-primary"
          >
            {district.districtName}
          </Link>
        </h5>
        <p className="text-muted">
          {district.description ||
            "This district is an administrative unit within the province."}
        </p>
      </Tab.Pane>
    ))
  ) : (
    <Alert variant="info" className="mb-0">
      No districts available for this province.
    </Alert>
  )}
</Tab.Content>
          </Col>
        </Row>
      </Tab.Container>
    </Container>
  );
};

export default ProvinceDetail;
