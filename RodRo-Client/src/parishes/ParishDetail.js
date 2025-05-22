import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { apiGet } from "../utils/api";
import { Container, Row, Col, Card, ListGroup, Alert } from "react-bootstrap";
import { Link } from "react-router-dom";

const ParishDetail = () => {
    const { id } = useParams(); // Getting id from URL
    const [parish, setParish] = useState({});
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        console.log("Fetching data for parish with ID:", id); // Debugging log
        const fetchData = async () => {
            try {
                // Fetch parish data
                const parishData = await apiGet("/api/parishes/" + id);
                setParish(parishData);
            } catch (err) {
                console.error("Error fetching data:", err);
                setError(`Error loading data: ${err.message || err}`);
            } finally {
                setLoading(false);
            }
        };

        fetchData();
    }, [id]); // Re-run the effect when id changes

    if (loading) return <p>Loading...</p>;
    if (error) return <Alert variant="danger">{error}</Alert>;

    return (
        <Container className="mt-5">
            {/* Parish Details */}
            <Row className="mb-4">
                <Col md={6}>
                    <Card>
                        <Card.Body>
                            <Card.Title>
                                {parish.name} - {parish.churchName}
                            </Card.Title>
                            <ListGroup variant="flush">
                                <ListGroup.Item>
                                    <strong>Cemetery:</strong> {parish.cemeteryName || "N/A"}
                                </ListGroup.Item>
                                <ListGroup.Item>
                                    <strong>Establishment Date:</strong>{" "}
                                    {parish.establishmentDate || "N/A"}
                                </ListGroup.Item>
                                <ListGroup.Item>
                                    <strong>Cancellation Date:</strong>{" "}
                                    {parish.cancellationDate || "N/A"}
                                </ListGroup.Item>
                                <ListGroup.Item>
                                    <strong>Location Name:</strong>{" "}
                                    {parish.parishLocation ? (
                                        <Link to={`/locations/show/${parish.parishLocation._id}`}>
                                            {parish.parishLocation.locationName}
                                        </Link>
                                    ) : (
                                        "N/A"
                                    )}
                                </ListGroup.Item>
                            </ListGroup>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default ParishDetail;
