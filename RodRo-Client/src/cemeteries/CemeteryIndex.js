import React, { useEffect, useState } from "react";
import { apiDelete, apiGet } from "../utils/api";
import { Container, Alert, Spinner, Row, Col, Card } from "react-bootstrap";
import CemeteryTable from "./CemeteryTable";
import "@fortawesome/fontawesome-free/css/all.min.css"; 

const CemeteryIndex = () => {
    const [cemeteries, setCemeteries] = useState([]);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true); 

    const deleteCemetery = async (id) => {
        try {
            await apiDelete("/api/cemeteries/" + id);
            setCemeteries(cemeteries.filter((item) => (item.id || item._id) !== id));
            setError(null);
        } catch (error) {
            console.error("Delete Error:", error.message);
            setError("Authorization Error: Failed to delete the record.");
        }
    };

    useEffect(() => {
        setLoading(true);
        apiGet("/api/cemeteries")
            .then((data) => {
                setCemeteries(data);
                setError(null);
            })
            .catch((error) => {
                setError("Data Retrieval Error: Failed to load cemetery records.");
                console.error(error);
            })
            .finally(() => {
                setLoading(false);
            });
    }, []);

    if (loading) return (
        <Container className="text-center my-5 py-5">
            <Spinner animation="border" variant="primary" />
            <p className="mt-3 text-muted">Indexing cemetery records...</p>
        </Container>
    );

    const cemeteryContext = [
        {
            title: "Historical Context",
            icon: "fas fa-landmark",
            text: "Cemeteries reflect the social, religious, and economic fabric of communities. Their layout and design tell stories of tolerance, planning, and memory."
        },
        {
            title: "Confessional Structure",
            icon: "fas fa-people-arrows",
            text: "Burial sites often began as segregated by confession. Over time, many evolved into shared municipal cemeteries, with distinct zones for each faith."
        },
        {
            title: "Grave Marker Evolution",
            icon: "fas fa-monument",
            text: "Wooden crosses once dominated but decayed quickly. By the 19th century, durable stone markers (sandstone, granite, marble) became symbols of permanence and affluence."
        },
        {
            title: "Modern Landscape",
            icon: "fas fa-city",
            text: "Today’s cemeteries reveal contrasts: empty grassy areas where wooden crosses vanished, alongside enduring stone monuments and elaborate mausoleums."
        }
    ];

    return (
        <Container className="my-5 py-3">
            <header className="text-center mb-5">
                <h1 className="display-5 fw-bold text-dark mb-1">
                    <i className="fas fa-cross me-3 text-primary"></i>Historical Burial Sites Ontology
                </h1>
                <p className="lead text-secondary fst-italic mb-4 mx-auto" style={{maxWidth: '800px'}}>
                    This database indexes <strong>cemetery and burial grounds</strong> as geospatial points of historical and socio-demographic importance, crucial for tracking population migration and religious history.
                </p>
            </header>

            {/* Cemetery Context Section */}
            <Row className="mb-5 justify-content-center">
                {cemeteryContext.map((item, index) => (
                    <Col md={6} lg={3} key={index} className="mb-4">
                        <Card className="h-100 shadow-sm border-start border-primary border-4 rounded-3">
                            <Card.Body className="p-3">
                                <h6 className="fw-bold text-primary mb-2">
                                    <i className={`${item.icon} me-2`}></i> {item.title}
                                </h6>
                                <p className="text-muted small mb-0">{item.text}</p>
                            </Card.Body>
                        </Card>
                    </Col>
                ))}
            </Row>

            <hr className="mb-5"/>

            {error && <Alert variant="danger" className="rounded-3 shadow-sm">{error}</Alert>}
            
            <CemeteryTable
                label="Total Burial Sites Indexed:"
                items={cemeteries}
                deleteCemetery={deleteCemetery}
            />
        </Container>
    );
};

export default CemeteryIndex;
