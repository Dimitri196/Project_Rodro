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

// Helper to determine the badge color for continent history based on the name
const getContinentBadgeVariant = (continentName) => {
    switch (continentName) {
        case 'Europe': return 'primary';
        case 'Asia': return 'warning';
        case 'Africa': return 'success';
        case 'North America': return 'info';
        case 'South America': return 'danger';
        case 'Oceania': return 'secondary';
        default: return 'light text-dark';
    }
};

const CountryDetail = () => {
  const { id } = useParams();
  const navigate = useNavigate();

  const [country, setCountry] = useState(null);
  const [provinceData, setProvinceData] = useState([]); 
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [openProvince, setOpenProvince] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        // Fetch ALL data (Country, Continent History, and Provinces) in ONE call
        const countryData = await apiGet(`/api/countries/${id}`);
        setCountry(countryData);

        // Extract and sort Provinces (now nested in countryData)
        const provincesFromDTO = countryData.provinces || []; 

        const sortedProvinces = [...provincesFromDTO].sort((a, b) =>
          // FIX 1: Sort by the corrected province field 'name'
          a.name.localeCompare(b.name, undefined, {
            sensitivity: "base",
          })
        );
        setProvinceData(sortedProvinces);

        // Set default tab to first province
        if (sortedProvinces.length > 0) {
          setOpenProvince(sortedProvinces[0]._id);
        }
      } catch (err) {
        console.error("Error fetching data:", err);
        setError(`Error loading data: ${err.message || String(err)}`);
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
    nameInPolish,
    nameInEnglish,
    establishmentYear,
    cancellationYear,
    flagImgUrl,
    continentHistory,
  } = country;
  
  const provinces = provinceData; 

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
              <h3 className="fw-bold mb-4">{nameInPolish}</h3>
              <ListGroup variant="flush">
                <ListGroup.Item>
                  <strong>English Name:</strong> {nameInEnglish || "N/A"}
                </ListGroup.Item>
                <ListGroup.Item>
                  <strong>Establishment Year:</strong>{" "}
                  {establishmentYear || "N/A"}
                </ListGroup.Item>

                {/* --- NEW: Detailed Continent History List --- */}
                {continentHistory && continentHistory.length > 0 && (
                    <ListGroup.Item className="p-0">
                        <div className="pt-2 pb-1 ps-3 fw-bold bg-light-subtle">
                            Geographic History:
                        </div>
                        <ListGroup variant="flush" className="border-top">
                            {continentHistory.map((history) => (
                                <ListGroup.Item 
                                    key={history._id} 
                                    className="d-flex justify-content-between align-items-center py-2"
                                >
                                    <div>
                                        <span 
                                            className={`badge bg-${getContinentBadgeVariant(history.continentName)} me-2`}
                                        >
                                            {history.continentName}
                                        </span>
                                    </div>
                                    <div className="fw-semibold">
                                        {history.startYear} – {history.endYear || 'Present'}
                                    </div>
                                </ListGroup.Item>
                            ))}
                        </ListGroup>
                    </ListGroup.Item>
                )}
                {/* --- END NEW --- */}
                
                <ListGroup.Item>
                  <strong>Status:</strong>{" "}
                  {cancellationYear ? (
                    <>
                      Dissolved in {cancellationYear}{" "}
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
              {flagImgUrl ? (
                <img
                  src={flagImgUrl}
                  alt={`${nameInPolish} flag`}
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
        I. Administrative Hierarchies & Provincial Divisions
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
                      {/* FIX 2: Display province 'name' */}
                      {province.name}
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
                        {/* FIX 3: Link text uses province 'name' */}
                        {province.name}
                      </Link>
                    </h4>

                    <p className="text-muted mb-4">
                      {/* FIX 4: Province DTO doesn't have a description or context field, 
                                 but if it did, it would be 'description' or 'context'. 
                                 Assuming 'description' is not intended here as per your DTO.
                                 I'll leave it as is, or use a general fallback.*/}
                      {"This province is a primary administrative division within the country."}
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
                                    {/* FIX 5: District name uses district 'name' */}
                                    {district.name}
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
                      {province.context || // If you added a 'context' field to ProvinceDTO, it would work here
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
