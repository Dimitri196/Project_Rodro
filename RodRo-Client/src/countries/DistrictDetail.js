import React, { useEffect, useState } from "react";
import { useParams, Link, useNavigate } from "react-router-dom";
import { apiGet } from "../utils/api";
import { normalizeString } from "../utils/stringUtils";
import {
  Container,
  Card,
  ListGroup,
  Alert,
  Button,
  Spinner,
  Row,
  Col,
  Form,
} from "react-bootstrap";

const DistrictDetail = () => {
  const { countryId, provinceId, districtId } = useParams();
  const navigate = useNavigate();

  const [district, setDistrict] = useState(null);
  const [province, setProvince] = useState(null);
  const [country, setCountry] = useState(null);
  const [locations, setLocations] = useState([]);
  const [sortedLocations, setSortedLocations] = useState([]);
  const [filteredLocations, setFilteredLocations] = useState([]);
  const [sortAsc, setSortAsc] = useState(true);
  const [searchTerm, setSearchTerm] = useState("");
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const deduplicateLocations = (list) => {
    const seen = new Set();
    return list.filter((loc) => {
      const key = loc.locationId || loc._id;
      if (seen.has(key)) return false;
      seen.add(key);
      return true;
    });
  };

  const sortLocationList = (list, asc) => {
    const sorted = [...list].sort((a, b) => {
      const nameA = normalizeString(a.locationName);
      const nameB = normalizeString(b.locationName);
      return asc ? nameA.localeCompare(nameB) : nameB.localeCompare(nameA);
    });
    setSortedLocations(sorted);
    filterLocations(sorted, searchTerm);
  };

  const filterLocations = (list, term) => {
    const normalizedTerm = normalizeString(term);
    if (!normalizedTerm) {
      setFilteredLocations(list);
    } else {
      const filtered = list.filter((loc) =>
        normalizeString(loc.locationName).includes(normalizedTerm)
      );
      setFilteredLocations(filtered);
    }
  };


  const toggleSort = () => {
    const newSort = !sortAsc;
    setSortAsc(newSort);
    sortLocationList(locations, newSort);
  };

  const handleSearch = (e) => {
    const term = e.target.value;
    setSearchTerm(term);
    filterLocations(sortedLocations, term);
  };

useEffect(() => {
    const fetchData = async () => {
      try {
        // 1. Fetch District: Use the dedicated DistrictController endpoint
        const districtData = await apiGet(
          `/api/countries/${countryId}/provinces/${provinceId}/districts/${districtId}`
        );
        setDistrict(districtData);

        // 2. Fetch Province: Use the dedicated ProvinceController endpoint
        const provinceData = await apiGet(
          `/api/countries/${countryId}/provinces/${provinceId}` // <-- FIX: Changed from /countries/{id}/provinces/{id}
        );
        setProvince(provinceData);

        // 3. Fetch Country: This path remains correct
        const countryData = await apiGet(`/api/countries/${countryId}`);
        setCountry(countryData);

        // 4. Fetch Locations: This path should still be valid in DistrictController
        const locationsData = await apiGet(
          `/api/districts/${districtId}/locations`
        );
        const uniqueLocations = deduplicateLocations(locationsData);
        setLocations(uniqueLocations);
        sortLocationList(uniqueLocations, true);
        
      } catch (err) {
        setError(`Error loading district details: ${err.message || err}`);
      } finally {
        setLoading(false);
      }
    };
    fetchData();
  }, [countryId, provinceId, districtId]);

  if (loading) {
    return (
      <Container className="mt-5 text-center">
        <Spinner animation="border" variant="primary" />
        <p>Loading district details...</p>
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
  
  // FIX 1: Update destructuring for DistrictDTO fields
  // Changed districtName to name and districtFlagImgUrl to imgUrl
  const { name: districtName = "N/A", imgUrl: districtImgUrl } = district || {}; 
  
  // FIX 2: Update field access for ProvinceDTO field
  // Changed provinceName to name
  const provinceName = province?.name || "Unknown Province";
  
  // FIX 3: Update field access for CountryDTO field
  // Changed countryNameInPolish to nameInPolish
  const countryName = country?.nameInPolish || "Unknown Country";

  return (
    <Container className="mt-5">
      {/* Back Button */}
      <Button
        as={Link}
        to={`/countries/${countryId}/provinces/${provinceId}`}
        variant="secondary"
        className="mb-4"
      >
        ← Back to {provinceName}, {countryName}
      </Button>

      {/* Top Info Row */}
      <Row className="mb-4">
        <Col md={8}>
          <Card className="shadow-lg border-0 rounded-4 h-100">
            <Card.Header
              as="h5"
              className="bg-primary text-white py-3 rounded-top-4"
            >
              <i className="fas fa-info-circle me-2"></i> District Details
            </Card.Header>
            <Card.Body>
              <Card.Title as="h3" className="mb-3">
                {districtName}
              </Card.Title>
              <ListGroup variant="flush">
                <ListGroup.Item>
                  <strong>District Name:</strong> {districtName}
                </ListGroup.Item>
                <ListGroup.Item>
                  <strong>Province:</strong>{" "}
                  <Link
                    to={`/countries/${countryId}/provinces/${provinceId}`}
                    className="text-decoration-none text-primary"
                  >
                    {provinceName}
                  </Link>
                </ListGroup.Item>
                <ListGroup.Item>
                  <strong>Country:</strong>{" "}
                  <Link
                    to={`/countries/show/${countryId}`}
                    className="text-decoration-none text-primary"
                  >
                    {countryName}
                  </Link>
                </ListGroup.Item>
              </ListGroup>
            </Card.Body>
          </Card>
        </Col>

        <Col md={4}>
          <Card className="shadow-lg border-0 rounded-4 h-100">
            <Card.Header
              as="h5"
              className="bg-secondary text-white py-3 rounded-top-4"
            >
              <i className="fas fa-flag me-2"></i> District Flag / Image
            </Card.Header>
            <Card.Body className="d-flex justify-content-center align-items-center">
              {districtImgUrl ? ( // FIX 4: Use districtImgUrl
                <img
                  src={districtImgUrl}
                  alt={`${districtName} flag`}
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

      {/* Locations Section */}
      <Card className="mt-4 shadow-sm border-0 rounded-4">
        <Card.Header
          as="h5"
          className="bg-primary text-white py-3 rounded-top-4"
        >
          <i className="fas fa-globe me-2"></i> Locations
        </Card.Header>
        <Card.Body>
          {/* Search & Sort Panel */}
          <Row className="mb-3">
            <Col md={6}>
              <Form.Control
                type="text"
                placeholder="Search locations..."
                value={searchTerm}
                onChange={handleSearch}
              />
            </Col>
            <Col md={6} className="text-md-end mt-2 mt-md-0">
              <Button
                variant="outline-light"
                size="sm"
                className="text-white border-white"
                onClick={toggleSort}
              >
                Sort {sortAsc ? "↓ A–Z" : "↑ Z–A"}
              </Button>
            </Col>
          </Row>

          {filteredLocations.length > 0 ? (
            <Row xs={1} sm={2} md={3} lg={4} className="g-3">
              {filteredLocations.map((location) => (
                <Col key={location._id}>
                  <Card className="h-100 shadow-sm border-0 rounded-3">
                    <Card.Body className="d-flex align-items-center">
                      <i
                        className="fas fa-map-marker-alt text-danger me-2"
                        style={{ fontSize: "0.9rem" }}
                      />
                      <Link
                        to={`/locations/show/${location._id}`}
                        className="text-decoration-none fw-bold text-dark"
                      >
                        {location.locationName || "Unnamed Location"}
                      </Link>
                    </Card.Body>
                  </Card>
                </Col>
              ))}
            </Row>
          ) : (
            <Alert variant="info" className="mb-0">
              <i className="fas fa-info-circle me-2"></i>
              No locations found for this search.
            </Alert>
          )}
        </Card.Body>
      </Card>
    </Container>
  );
};

export default DistrictDetail;
