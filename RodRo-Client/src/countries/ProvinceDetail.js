import React, { useEffect, useState } from 'react';
import { useParams, Link, useNavigate } from 'react-router-dom';
import { apiGet } from '../utils/api';
import {
  Container,
  Card,
  ListGroup,
  Alert,
  Button,
  Spinner,
} from 'react-bootstrap';

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
        console.error('Error fetching data:', err);
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
    provinceName = 'N/A',
    districts = [],
  } = province || {};

  const countryName =
    country?.countryNameInPolish || province?.country?.countryNameInPolish || 'Unknown Country';

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

      {/* Province Card */}
      <Card>
        <Card.Body>
          <Card.Title as="h3">{provinceName}</Card.Title>
          <ListGroup variant="flush" className="mt-3">
            <ListGroup.Item>
              <strong>Province Name:</strong> {provinceName}
            </ListGroup.Item>
            <ListGroup.Item>
              <strong>Country:</strong> {countryName}
            </ListGroup.Item>
          </ListGroup>
        </Card.Body>
      </Card>

      {/* Districts List */}
      <Card className="mt-4">
        <Card.Body>
          <Card.Title as="h4">Districts</Card.Title>
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
            <p className="mt-3">No districts available for this province.</p>
          )}
        </Card.Body>
      </Card>
    </Container>
  );
};

export default ProvinceDetail;

