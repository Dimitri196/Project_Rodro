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
  Tab,
  Nav,
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
        const sortedProvinces = [...provincesData].sort((a, b) =>
          a.provinceName.localeCompare(b.provinceName, undefined, {
            sensitivity: "base",
          })
        );
        setProvinces(sortedProvinces);

        // ✅ default tab to first province
        if (sortedProvinces.length > 0) {
          setOpenProvince(sortedProvinces[0]._id);
        }
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

      {/* --- Top Info Row --- */}
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
                  <strong>Status:</strong>{" "}
                  {countryCancellationYear ? (
                    <>
                      Dissolved in {countryCancellationYear}{" "}
                      <span className="badge bg-danger ms-2">Dissolved</span>
                    </>
                  ) : (
                    <>
                      Still Exists{" "}
                      <span className="badge bg-success ms-2">Active</span>
                    </>
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

      {/* --- Provinces Section --- */}
      <h2 className="fw-bold text-dark mt-5 mb-4">
        II. Administrative Hierarchies & Provincial Divisions
      </h2>

      {provinces.length > 0 ? (
        <Tab.Container
          id="province-tabs"
          activeKey={openProvince}
          onSelect={(k) => setOpenProvince(k)}
        >
          <Row>
            {/* Province Sidebar */}
            <Col md={3} className="mb-3">
              <Nav
                variant="pills"
                className="flex-column p-3 bg-light rounded-3 shadow-sm border"
              >
                {provinces.map((province) => (
                  <Nav.Item key={province._id}>
                    <Nav.Link
                      eventKey={province._id}
                      className="text-start mb-1 fw-semibold"
                    >
                      <i className="fas fa-landmark me-2 text-secondary"></i>
                      {province.provinceName}
                      {province.districts?.length > 0 && (
                        <span className="badge bg-primary ms-2">
                          {province.districts.length}
                        </span>
                      )}
                    </Nav.Link>
                  </Nav.Item>
                ))}
              </Nav>
            </Col>

{/* Province Content */}
<Col md={9}>
  <Tab.Content className="p-4 bg-white rounded-3 shadow border">
    {provinces.map((province) => (
      <Tab.Pane key={province._id} eventKey={province._id}>
        <h4 className="fw-bold text-dark border-bottom pb-2 mb-3 d-flex align-items-center">
          <Link
            to={`/countries/${id}/provinces/${province._id}`}
            className="text-decoration-none text-primary me-2"
          >
            <i className="fas fa-landmark me-2"></i>
            {province.provinceName}
          </Link>
        </h4>

        <p className="text-muted mb-4">
          {province.description ||
            "This province is a primary administrative division within the country."}
        </p>

                    <h5 className="fw-bold text-primary mb-3">
                      District Structure:
                    </h5>

                    <Row className="g-3">
                      {province.districts && province.districts.length > 0 ? (
                        province.districts.map((district) => (
                          <Col md={6} key={district._id}>
                            <Card className="h-100 border-0 border-start border-4 border-secondary-subtle">
                              <Card.Body className="p-3">
                                <h6 className="mb-1 text-dark">
                                  <i className="fas fa-map-pin me-2 text-secondary"></i>
                                  <Link
                                    to={`/countries/${id}/provinces/${province._id}/districts/${district._id}`}
                                    className="text-decoration-none text-dark fw-semibold"
                                  >
                                    {district.districtName}
                                  </Link>
                                </h6>
                                <p className="small text-muted mb-0">
                                  {district.description ||
                                    "District-level administration"}
                                </p>
                              </Card.Body>
                            </Card>
                          </Col>
                        ))
                      ) : (
                        <Col>
                          <Alert variant="info" className="mb-0">
                            <i className="fas fa-info-circle me-2"></i>
                            No districts found for this province.
                          </Alert>
                        </Col>
                      )}
                    </Row>

                    <p className="small fst-italic text-muted mt-4 mb-0 border-top pt-3">
                      <i className="fas fa-info-circle me-1 text-primary"></i>
                      Contextual Note:{" "}
                      {province.context ||
                        "This province has historical, cultural, and administrative significance within the country."}
                    </p>
                  </Tab.Pane>
                ))}
              </Tab.Content>
            </Col>
          </Row>
        </Tab.Container>
      ) : (
        <Alert variant="info" className="text-center mb-0">
          <i className="fas fa-info-circle me-2"></i>
          No provinces available for this country.
        </Alert>
      )}
    </Container>
  );
};

export default CountryDetail;
