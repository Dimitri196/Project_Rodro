import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { apiGet } from "../utils/api";
import { Container, Row, Col, Card } from "react-bootstrap";

const CemeteryDetail = () => {
    const { id } = useParams();
    const [cemetery, setCemetery] = useState(null);
    const [error, setError] = useState(null);

    // Fetch cemetery data on component mount
    useEffect(() => {
        apiGet(`/api/cemeteries/${id}`)
            .then((data) => setCemetery(data))
            .catch((error) => setError(error.message));
    }, [id]);

    if (error) {
        return <div>Error: {error}</div>;
    }

    if (!cemetery) {
        return <div>Loading...</div>;
    }

    return (
        <Container className="mt-5">
            <h1>{cemetery.cemeteryName}</h1>
            <Card>
                <Card.Body>
                    <Row>
                        <Col md={6}>
                            <h5>Description:</h5>
                            <p>{cemetery.description || "No description available"}</p>
                        </Col>
                    </Row>

                    <Row>
                        <Col md={6}>
                            <h5>Location:</h5>
                            {cemetery.cemeteryLocation?.locationName || "N/A"}
                        </Col>
                        <Col md={6}>
                            <h5>Web Link:</h5>
                            {cemetery.webLink ? (
                                <p>
                                    <a href={cemetery.webLink} target="_blank" rel="noopener noreferrer">
                                        {cemetery.webLink}
                                    </a>
                                </p>
                            ) : (
                                <p>N/A</p>
                            )}
                        </Col>
                    </Row>
                </Card.Body>
            </Card>
        </Container>
    );
};

export default CemeteryDetail;
