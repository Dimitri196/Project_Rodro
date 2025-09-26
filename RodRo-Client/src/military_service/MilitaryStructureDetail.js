import React, { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import { Container, Row, Col, Card, ListGroup, Alert, Spinner } from "react-bootstrap";
import { apiGet } from "../utils/api";

const MilitaryStructureDetail = () => {
    // useParams() retrieves the dynamic 'id' parameter from the URL
    const { id } = useParams(); 
    const [structure, setStructure] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const [ranks, setRanks] = useState([]);
    const [ranksError, setRanksError] = useState(null);

// useEffect hook to fetch data when the component mounts or the `id` changes.
  useEffect(() => {
    const fetchData = async () => {
      // Set loading state to true and clear any previous errors.
      setLoading(true);
      setError(null);
      try {
        // Fetch the military structure details from the API.
        const structureData = await apiGet(`/api/militaryStructures/${id}`);
        setStructure(structureData);
        
        // Fetch the associated ranks for the military structure's branch.
        const ranksData = await apiGet(`/api/militaryStructures/${id}/ranks`);
        setRanks(ranksData);

      } catch (err) {
        // Handle any errors that occur during the API calls.
        console.error("Error fetching military structure or ranks:", err);
        setError(`Failed to load military structure details: ${err.message || err}`);
      } finally {
        // Set loading state to false once the data is fetched (or an error occurs).
        setLoading(false);
      }
    };

    fetchData();
  }, [id]); // The effect re-runs whenever the `id` parameter changes.


    // Display loading state
    if (loading) {
        return (
            <Container className="my-5 text-center">
                <Spinner animation="border" role="status">
                    <span className="visually-hidden">Loading...</span>
                </Spinner>
                <p className="mt-3 text-muted">Loading military structure details...</p>
            </Container>
        );
    }

    // Display error messages
    if (error) {
        return (
            <Container className="my-5">
                <Alert variant="danger" className="text-center shadow-sm rounded-3">
                    <i className="fas fa-exclamation-triangle me-2"></i>{error}
                </Alert>
            </Container>
        );
    }

    // Display message if structure is not found
    if (!structure) {
        return (
            <Container className="my-5">
                <Alert variant="info" className="text-center shadow-sm rounded-3">
                    <i className="fas fa-info-circle me-2"></i>Military structure not found.
                </Alert>
            </Container>
        );
    }

    // Main component content
    return (
        <Container className="my-5 py-4 bg-light rounded shadow-lg">
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" xintegrity="sha512-1ycn6IcaQQ40JuKmWswL3QbpgExX+Q6bLSYg5rXW4remp4o/Y6fFZj/gX2Q2K2V2F5R5/5Q5w5w5w5w5w==" crossOrigin="anonymous" referrerPolicy="no-referrer" />

            <Row className="mb-4">
                <Col md={12} className="text-center">
                    <h1 className="display-4 fw-bold text-primary mb-3">
                        <i className="fas fa-landmark me-3"></i>
                        {structure.unitName}
                    </h1>
                    <p className="lead text-muted fst-italic">
                        {structure.unitType || "Military Unit"}
                    </p>
                </Col>
            </Row>

            <Row className="justify-content-center">
                {/* Basic Information Card */}
                <Col md={6} className="mb-4">
                    <Card className="shadow-lg border-0 rounded-4 h-100">
                        <Card.Header as="h5" className="bg-primary text-white py-3 rounded-top-4">
                            <i className="fas fa-info-circle me-2"></i>Basic Information
                        </Card.Header>
                        <Card.Body className="p-4">
                            <ListGroup variant="flush">
                                <ListGroup.Item className="d-flex justify-content-between align-items-center px-0">
                                    <strong>Unit Type:</strong>
                                    <span>{structure.unitType || "-"}</span>
                                </ListGroup.Item>
                                <ListGroup.Item className="d-flex justify-content-between align-items-center px-0">
                                    <strong>Organization:</strong>
                                    <span>
                                        {structure.organization ? (
                                            <Link to={`/militaryOrganizations/show/${structure.organization._id}`} className="text-decoration-none text-primary">
                                                {structure.organization.armyName}
                                            </Link>
                                        ) : (
                                            "Unknown"
                                        )}
                                    </span>
                                </ListGroup.Item>
                                <ListGroup.Item className="d-flex justify-content-between align-items-center px-0">
                                    <strong>Branch:</strong>
                                    <span>{structure.armyBranchName || "-"}</span>
                                </ListGroup.Item>
                                <ListGroup.Item className="d-flex justify-content-between align-items-center px-0">
                                    <strong>Active From:</strong>
                                    <span>{structure.activeFromYear || "-"}</span>
                                </ListGroup.Item>
                                <ListGroup.Item className="d-flex justify-content-between align-items-center px-0">
                                    <strong>Active To:</strong>
                                    <span>{structure.activeToYear || "-"}</span>
                                </ListGroup.Item>
                                <ListGroup.Item className="d-flex justify-content-between align-items-center px-0">
                                    <strong>Notes:</strong>
                                    <span>{structure.notes || "-"}</span>
                                </ListGroup.Item>
                            </ListGroup>
                        </Card.Body>
                    </Card>
                </Col>

                {/* Unit Banner Card */}
                <Col md={6} className="mb-4">
                    <Card className="shadow-lg border-0 rounded-4 h-100">
                        <Card.Header as="h5" className="bg-primary text-white py-3 rounded-top-4">
                            <i className="fas fa-flag me-2"></i>Unit Banner
                        </Card.Header>
                        <Card.Body className="p-4 d-flex justify-content-center align-items-center">
                            {structure.bannerImageUrl ? (
                                <img
                                    src={structure.bannerImageUrl}
                                    alt={`${structure.unitName} banner`}
                                    style={{ maxWidth: "100%", height: "auto", maxHeight: "300px", borderRadius: "0.5rem" }}
                                    className="img-fluid"
                                />
                            ) : (
                                <div className="text-center text-muted">
                                    <i className="fas fa-image fa-3x mb-3"></i>
                                    <p>No banner image available.</p>
                                </div>
                            )}
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
            
            <Row className="justify-content-center">
                {/* Ranks Card */}
                <Col md={12} className="mb-4">
                    <Card className="shadow-lg border-0 rounded-4 h-100">
                        <Card.Header as="h5" className="bg-primary text-white py-3 rounded-top-4">
                            <i className="fas fa-medal me-2"></i>Ranks for this Branch
                        </Card.Header>
                        <Card.Body className="p-4">
                            {ranksError && <Alert variant="danger">{ranksError}</Alert>}
                            {ranks.length === 0 ? (
                                <Alert variant="info" className="text-center mt-3">
                                    <i className="fas fa-info-circle me-2"></i>No ranks found for this structure's branch.
                                </Alert>
                            ) : (
                                <ListGroup>
                                    {ranks.map(rank => (
                                        <ListGroup.Item key={rank._id} className="pb-3 px-0 border-bottom">
                                            <strong>
                                                <Link to={`/militaryRanks/show/${rank._id}`} className="text-decoration-none text-primary">
                                                    {rank.rankName}
                                                </Link>
                                            </strong>
                                            {rank.rankLevel?.description ? (
                                                <> ({rank.rankLevel.description})</>
                                            ) : (
                                                rank.rankLevel && <> ({rank.rankLevel})</>
                                            )}
                                            {rank.rankDescription && <> - {rank.rankDescription}</>}
                                        </ListGroup.Item>
                                    ))}
                                </ListGroup>
                            )}
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default MilitaryStructureDetail;
