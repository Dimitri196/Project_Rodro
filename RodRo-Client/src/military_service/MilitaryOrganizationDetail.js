import React, { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import { Container, Row, Col, Card, ListGroup, Alert } from "react-bootstrap";
import { apiGet } from "../utils/api";

const MilitaryOrganizationDetail = () => {
    const { id } = useParams();
    const [organization, setOrganization] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchOrganization = async () => {
            try {
                const data = await apiGet(`/api/militaryOrganizations/${id}`);
                setOrganization(data);
            } catch (err) {
                setError(`Error loading military organization: ${err.message || err}`);
            } finally {
                setLoading(false);
            }
        };

        fetchOrganization();
    }, [id]);

    if (loading) return <p>Loading organization details...</p>;
    if (error) return <Alert variant="danger">{error}</Alert>;
    if (!organization) return <p>No organization data found.</p>;

    return (
        <Container className="mt-5">
            <Row className="mb-4">
                <Col md={6}>
                    <Card>
                        <Card.Body>
                            <Card.Title>{organization.armyName}</Card.Title>
                            <ListGroup variant="flush">
                                <ListGroup.Item>
                                    <strong>Branch:</strong> {organization.armyBranch?.armyBranchName}
                                </ListGroup.Item>
                                <ListGroup.Item>
                                    <strong>Country:</strong>{" "}
                                    {organization.country ? (
                                        <Link to={`/countries/show/${organization.country._id}`}>
                                            {organization.country.countryNameInPolish}
                                        </Link>
                                    ) : (
                                        "Unknown"
                                    )}
                                </ListGroup.Item>
                                <ListGroup.Item>
                                    <strong>Active From:</strong> {organization.activeFromYear || "Unknown"}
                                </ListGroup.Item>
                                <ListGroup.Item>
                                    <strong>Active To:</strong> {organization.activeToYear || "Still Active"}
                                </ListGroup.Item>
                            </ListGroup>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>

            <Row>
                <Col>
                    <h4>Military Structures</h4>
                    {organization.structures && organization.structures.length > 0 ? (
                        <ListGroup>
                            {organization.structures.map((structure) => (
                                <ListGroup.Item key={structure._id || structure.id}>
                                    <Link to={`/militaryStructures/show/${structure._id || structure.id}`}>
                                        <strong>{structure.unitName}</strong>
                                    </Link>{" "}
                                    – {structure.unitType}
                                    {structure.activeFromYear && ` (${structure.activeFromYear}–${structure.activeToYear || "present"})`}
                                    {structure.notes && <div className="text-muted">{structure.notes}</div>}
                                </ListGroup.Item>
                            ))}
                        </ListGroup>
                    ) : (
                        <p>No structures found for this organization.</p>
                    )}
                </Col>
            </Row>
        </Container>
    );
};

export default MilitaryOrganizationDetail;
