import React, { useEffect, useState } from 'react';
import { useParams, Link, useNavigate } from 'react-router-dom';
import { apiGet } from '../utils/api';
import { Container, Card, ListGroup, Alert, Button, Spinner } from 'react-bootstrap';

const formatDate = (dateArray) => {
    if (!dateArray || dateArray.length !== 3) return 'N/A';
    const [year, month, day] = dateArray;
    return `${year}-${String(month).padStart(2, '0')}-${String(day).padStart(2, '0')}`;
};

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

    // Deduplicate by locationId or _id
    const deduplicateLocations = (locations) => {
        const seen = new Set();
        return locations.filter((loc) => {
            const key = loc.locationId || loc._id;
            if (seen.has(key)) return false;
            seen.add(key);
            return true;
        });
    };

    // Sort locations alphabetically
    const sortLocationList = (locations, asc) => {
        const sorted = [...locations].sort((a, b) => {
            const nameA = a.locationName?.toLowerCase() || '';
            const nameB = b.locationName?.toLowerCase() || '';
            return asc ? nameA.localeCompare(nameB) : nameB.localeCompare(nameA);
        });
        setSortedLocations(sorted);
    };

    const toggleSort = () => {
        const newSortAsc = !sortAsc;
        setSortAsc(newSortAsc);
        sortLocationList(locations, newSortAsc);
    };

    useEffect(() => {
        const fetchData = async () => {
            try {
                const districtData = await apiGet(`/api/countries/${countryId}/provinces/${provinceId}/districts/${districtId}`);
                setDistrict(districtData);

                const provinceData = await apiGet(`/api/countries/${countryId}/provinces/${provinceId}`);
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
                <Button variant="secondary" onClick={() => navigate(-1)}>Go Back</Button>
            </Container>
        );
    }

    const { districtName = 'N/A' } = district || {};

    return (
        <Container className="mt-5">
            {province && province._id && country && country._id ? (
                <Button as={Link} to={`/countries/${country._id}/provinces/${province._id}`} variant="secondary" className="mb-4">
                    ← Back to {province.provinceName} in {country.countryNameInPolish}
                </Button>
            ) : (
                <p>Loading province or country data...</p>
            )}

            {/* District Info */}
            <Card>
                <Card.Body>
                    <Card.Title as="h3">{districtName}</Card.Title>
                    <ListGroup variant="flush" className="mt-3">
                        <ListGroup.Item>
                            <strong>District Name:</strong> {districtName}
                        </ListGroup.Item>
                        <ListGroup.Item>
                            <strong>Province:</strong> {province.provinceName}
                        </ListGroup.Item>
                        <ListGroup.Item>
                            <strong>Country:</strong> {country ? country.countryNameInPolish : 'Unknown Country'}
                        </ListGroup.Item>
                    </ListGroup>
                </Card.Body>
            </Card>

            {/* Locations List */}
            <Card className="mt-4">
                <Card.Body>
                    <div className="d-flex justify-content-between align-items-center mb-2">
                        <Card.Title as="h5">List of locations</Card.Title>
                        <Button variant="outline-primary" size="sm" onClick={toggleSort}>
                            Sort {sortAsc ? '↓ A–Z' : '↑ Z–A'}
                        </Button>
                    </div>

                    {sortedLocations.length > 0 ? (
                        <ListGroup variant="flush">
                            {sortedLocations.map((location) => (
                                <ListGroup.Item key={location._id}>
                                    <strong>
                                        <Link to={`/locations/show/${location.locationId}`}>
                                            {location.locationName || 'Unnamed Location'}
                                        </Link>
                                    </strong>
                                </ListGroup.Item>
                            ))}
                        </ListGroup>
                    ) : (
                        <Alert variant="info">No locations found for this district.</Alert>
                    )}
                </Card.Body>
            </Card>
        </Container>
    );
};

export default DistrictDetail;

