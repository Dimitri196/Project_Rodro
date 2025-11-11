import React, { useEffect, useState, useMemo } from "react";
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
  // REMOVED: const [country, setCountry] = useState(null); 
  // We'll extract country info directly from the province DTO

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [activeDistrict, setActiveDistrict] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        // Fetch province details
        // Note: The backend endpoint /api/provinces/{provinceId} should work based on the previous context
        // We'll use the combined path as you have it, assuming your backend expects it for validation:
        const provinceData = await apiGet(
          `/api/countries/${countryId}/provinces/${provinceId}`
        );
        setProvince(provinceData);
        
        // Set initial active district tab
        if (provinceData.districts?.length > 0) {
          setActiveDistrict(provinceData.districts[0]._id);
        }
      } catch (err) {
        console.error("Error fetching data:", err);
        setError(`Error loading province details: ${err.message || err}`);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  // We only need to re-run when IDs change
  }, [countryId, provinceId]); 
  
  // Use useMemo to calculate derived values based on the province state
  const { 
    name, 
    imgUrl, 
    districts = [], 
    // ✅ Extract country name directly from ProvinceDTO (assuming fields are countryName and countryId)
    countryName: provinceCountryName, 
    countryId: provinceCountryId 
  } = province || {};


  // Fallback/Display Country Name using province DTO fields
  const displayCountryName = useMemo(() => {
    return provinceCountryName || "Unknown Country";
  }, [provinceCountryName]);


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

  return (
    <Container className="mt-5">
      {/* Back Button */}
      <Button
        as={Link}
        // Use the ID from the province DTO for consistency, defaulting to param
        to={`/countries/show/${provinceCountryId || countryId}`} 
        variant="secondary"
        className="mb-4"
      >
        ← Back to {displayCountryName}
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
              <h3 className="fw-bold mb-4">{name}</h3>
              <ListGroup variant="flush">
                <ListGroup.Item>
                  <strong>Province Name:</strong> {name}
                </ListGroup.Item>
                <ListGroup.Item>
                  <strong>Country:</strong>{" "}
                  <Link
                    to={`/countries/${provinceCountryId || countryId}`}
                    className="text-decoration-none text-primary fw-semibold"
                  >
                    {displayCountryName}
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
              {imgUrl ? (
                <img
                  src={imgUrl}
                  alt={`${name} flag`}
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
        // Use the activeDistrict state which is set on initial load
        activeKey={activeDistrict} 
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
                      {/* ✅ Correctly using 'district.name' */}
                      {district.name}
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
                      <span className="text-muted me-2">{name} —</span>{" "}
                      <Link
                        to={`/countries/${countryId}/provinces/${provinceId}/districts/${district._id}`}
                        className="text-decoration-none text-primary"
                      >
                        {/* ✅ Correctly using 'district.name' */}
                        {district.name}
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