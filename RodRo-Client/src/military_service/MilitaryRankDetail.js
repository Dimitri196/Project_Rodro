import React, { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import { Container, Row, Col, Card, ListGroup, Alert } from "react-bootstrap";
import { apiGet } from "../utils/api";

const MilitaryRankDetail = () => {
    const { id } = useParams();
    const [rank, setRank] = useState({});
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchRank = async () => {
            try {
                const data = await apiGet(`/api/militaryRanks/${id}`);
                setRank(data);
            } catch (err) {
                setError(`Error loading military rank: ${err.message || err}`);
            } finally {
                setLoading(false);
            }
        };

        fetchRank();
    }, [id]);

    if (loading) return <p>Loading rank details...</p>;
    if (error) return <Alert variant="danger">{error}</Alert>;

    return (
        <Container className="mt-5">
            <Row className="mb-4">
                <Col md={6}>
                    <Card>
                        <Card.Body>
                            <Card.Title>{rank.rankName}</Card.Title>
                            <ListGroup variant="flush">
                                <ListGroup.Item>
                                    <strong>Description:</strong> {rank.rankDescription || "-"}
                                </ListGroup.Item>
                                <ListGroup.Item>
                                    <strong>Level:</strong> {rank.rankLevel || "-"}
                                </ListGroup.Item>
                                <ListGroup.Item>
                                    <strong>Organization:</strong>{" "}
                                    {rank.organization ? (
                                        <Link to={`/militaryOrganizations/show/${rank.organization._id}`}>
                                            {rank.organization.armyName}
                                        </Link>
                                    ) : (
                                        "Unknown"
                                    )}
                                </ListGroup.Item>
                                <ListGroup.Item>
                                    <strong>Active From:</strong> {rank.activeFromYear || "Unknown"}
                                </ListGroup.Item>
                                <ListGroup.Item>
                                    <strong>Active To:</strong> {rank.activeToYear || "Still Active"}
                                </ListGroup.Item>
                                <ListGroup.Item>
                                    <strong>Notes:</strong> {rank.notes || "-"}
                                </ListGroup.Item>
                            </ListGroup>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default MilitaryRankDetail;
