import React, { useEffect, useState } from "react";
import { useParams, Link, useNavigate } from "react-router-dom";
import { apiGet } from "../utils/api";
import {
    Container,
    Card,
    ListGroup,
    Alert,
    Button,
    Spinner,
    Row,
    Col,
} from "react-bootstrap";

const DistrictDetail = () => {
    const { countryId, provinceId, districtId } = useParams();
    const navigate = useNavigate();

    const [district, setDistrict] = useState(null);
    const [province, setProvince] = useState(null);
    const [country, setCountry] = useState(null);
    const [locations, setLocations] = useState([]);
    const [sortedLocations, setSortedLocations] = useState([]);
    const [sortAsc, setSortAsc] = useState(true);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    // Deduplicate by id
    const deduplicateLocations = (list) => {
        const seen = new Set();
        return list.filter((loc) => {
            const key = loc.locationId || loc._id;
            if (seen.has(key)) return false;
            seen.add(key);
            return true;
        });
    };

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
        sortLocationList(locations, newSort);
    };

    useEffect(() => {
        const fetchData = async () => {
            try {
                const districtData = await apiGet(
                    `/api/countries/${countryId}/provinces/${provinceId}/districts/${districtId}`
                );
                setDistrict(districtData);

                const provinceData = await apiGet(
                    `/api/countries/${countryId}/provinces/${provinceId}`
                );
                setProvince(provinceData);

                const countryData = await apiGet(`/api/countries/${countryId}`);
                setCountry(countryData);

                const locationsData = await apiGet(`/api/districts/${districtId}/locations`);
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

    // Loading state
    if (loading) {
        return (
            <Container className="mt-5 text-center">
                <Spinner animation="border" variant="primary" />
                <p>Loading district details...</p>
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

    const { districtName = "N/A" } = district || {};
    const provinceName = province?.provinceName || "Unknown Province";
    const countryName = country?.countryNameInPolish || "Unknown Country";

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

            <Row className="mb-4">
  {/* Left Column: District Details */}
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
              className="text-decoration-none"
            >
              {provinceName}
            </Link>
          </ListGroup.Item>
          <ListGroup.Item>
            <strong>Country:</strong>{" "}
            <Link
              to={`/countries/show/${countryId}`}
              className="text-decoration-none"
            >
              {countryName}
            </Link>
          </ListGroup.Item>
        </ListGroup>
      </Card.Body>
    </Card>
  </Col>

  {/* Right Column: District Image */}
  <Col md={4}>
    <Card className="shadow-lg border-0 rounded-4 h-100">
      <Card.Header
        as="h5"
        className="bg-secondary text-white py-3 rounded-top-4"
      >
        <i className="fas fa-flag me-2"></i> District Flag / Image
      </Card.Header>
      <Card.Body className="d-flex justify-content-center align-items-center">
        {district?.districtFlagImgUrl ? (
          <img
            src={district.districtFlagImgUrl}
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


            {/* Locations List */}
            <Card className="mt-4 shadow-sm border-0 rounded-4">
                <Card.Header
                    as="h5"
                    className="bg-primary text-white py-3 rounded-top-4"
                >
                    <i className="fas fa-globe me-2"></i>Locations
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
                            {sortedLocations.map((location) => (
                                <Col key={location._id}>
                                    <Card className="h-100 shadow-sm border-0 rounded-3">
                                        <Card.Body className="d-flex align-items-center">
                                            <i
                                                className="fas fa-map-marker-alt text-danger me-2"
                                                style={{ fontSize: "0.9rem" }}
                                            ></i>
                                            <Link
                                                to={`/locations/show/${location.locationId}`}
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
                            No locations available for this district.
                        </Alert>
                    )}
                </Card.Body>
            </Card>

        </Container>
    );
};

export default DistrictDetail;
