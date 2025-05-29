import React, { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import { Container, Row, Col, Card, ListGroup, Alert } from "react-bootstrap";
import { apiGet } from "../utils/api";

const MilitaryStructureDetail = () => {
    const { id } = useParams();
    const [structure, setStructure] = useState({});
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchStructure = async () => {
            try {
                const data = await apiGet(`/api/militaryStructures/${id}`);
                setStructure(data);
            } catch (err) {
                setError(`Error loading military structure: ${err.message || err}`);
            } finally {
                setLoading(false);
            }
        };

        fetchStructure();
    }, [id]);

    if (loading) return <p>Loading structure details...</p>;
    if (error) return <Alert variant="danger">{error}</Alert>;

    return (
        <Container className="mt-5">
            <Row className="mb-4">
                <Col md={8}>
                    <Card>
                        <Card.Body>
                            <Card.Title>{structure.unitName}</Card.Title>
                            <ListGroup variant="flush">
                                <ListGroup.Item>
                                    <strong>Unit Type:</strong> {structure.unitType || "-"}
                                </ListGroup.Item>
                                <ListGroup.Item>
                                    <strong>Organization:</strong>{" "}
                                    {structure.organization ? (
                                        <Link to={`/militaryOrganizations/show/${structure.organization._id}`}>
                                            {structure.organization.armyName}
                                        </Link>
                                    ) : (
                                        "Unknown"
                                    )}
                                </ListGroup.Item>
                                <ListGroup.Item>
                                    <strong>Branch:</strong>{" "}
                                    {structure.organization?.armyBranch || "-"}
                                </ListGroup.Item>
                                <ListGroup.Item>
                                    <strong>Active From:</strong> {structure.activeFromYear || "-"}
                                </ListGroup.Item>
                                <ListGroup.Item>
                                    <strong>Active To:</strong> {structure.activeToYear || "-"}
                                </ListGroup.Item>
                                <ListGroup.Item>
                                    <strong>Notes:</strong> {structure.notes || "-"}
                                </ListGroup.Item>
                            </ListGroup>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default MilitaryStructureDetail;
