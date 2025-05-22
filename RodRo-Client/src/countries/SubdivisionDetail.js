import React, { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import { apiGet } from "../utils/api";
import { Container, Card, ListGroup, Spinner, Alert, Row, Col } from "react-bootstrap";

const SubdivisionDetail = () => {
    const { id } = useParams();
    const [subdivision, setSubdivision] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchSubdivision = async () => {
            try {
                const data = await apiGet(`/api/subdivisions/${id}`);
                setSubdivision(data);
            } catch (err) {
                setError(`Error loading subdivision: ${err.message || err}`);
            } finally {
                setLoading(false);
            }
        };

        fetchSubdivision();
    }, [id]);

    if (loading) return <Spinner animation="border" />;
    if (error) return <Alert variant="danger">{error}</Alert>;
    if (!subdivision) return null;

    return (
        <Container className="mt-5">
            <Row>
                <Col md={8}>
                    <Card>
                        <Card.Body>
                            <Card.Title>{subdivision.subdivisionName}</Card.Title>
                            <ListGroup variant="flush">
                                <ListGroup.Item>
                                    <strong>District:</strong>{" "}
                                    {subdivision.district?.id ? (
                                        <Link to={`/countries/${subdivision.district.countryId}/provinces/${subdivision.district.provinceId}/districts/${subdivision.district.id}`}>
                                            {subdivision.districtName}
                                        </Link>
                                    ) : (
                                        subdivision.districtName || "-"
                                    )}
                                </ListGroup.Item>

                                <ListGroup.Item>
                                    <strong>Administrative Center:</strong>{" "}
                                    {subdivision.administrativeCenter?.id ? (
                                        <Link to={`/locations/show/${subdivision.administrativeCenter.id}`}>
                                            {subdivision.administrativeCenterName}
                                        </Link>
                                    ) : (
                                        subdivision.administrativeCenterName || "-"
                                    )}
                                </ListGroup.Item>

                                <ListGroup.Item>
                                    <strong>Establishment Year:</strong>{" "}
                                    {subdivision.subdivisionEstablishmentYear || "-"}
                                </ListGroup.Item>

                                <ListGroup.Item>
                                    <strong>Cancellation Year:</strong>{" "}
                                    {subdivision.subdivisionCancellationYear || "-"}
                                </ListGroup.Item>
                            </ListGroup>
                        </Card.Body>
                    </Card>

                    {subdivision.locations?.length > 0 && (
                        <Card className="mt-4">
                            <Card.Body>
                                <Card.Title>Locations in this Subdivision</Card.Title>
                                <ListGroup variant="flush">
                                    {subdivision.locations.map((loc) => (
                                        <ListGroup.Item key={loc._id}>
                                            <Link to={`/locations/show/${loc._id}`}>
                                                {loc.locationName}
                                            </Link>
                                        </ListGroup.Item>
                                    ))}
                                </ListGroup>
                            </Card.Body>
                        </Card>
                    )}
                </Col>
            </Row>
        </Container>
    );
};

export default SubdivisionDetail;
