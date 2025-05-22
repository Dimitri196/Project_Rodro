import React, { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import { Container, Row, Col, Card, ListGroup, Alert, Spinner } from "react-bootstrap";
import { apiGet } from "../utils/api";
import dateStringFormatter from "../utils/dateStringFormatter";
import settlementTypeLabels from "../constants/settlementTypeLabels";

const LocationDetail = () => {
    const { id } = useParams();
    const [location, setLocation] = useState({});
    const [history, setHistory] = useState([]);
    const [cemeteries, setCemeteries] = useState([]);
    const [loading, setLoading] = useState(true);
    const [historyLoading, setHistoryLoading] = useState(true);
    const [cemeteryLoading, setCemeteryLoading] = useState(true);
    const [parishLocationLoading, setParishLocationLoading] = useState(true);
    const [error, setError] = useState(null);
    const [historyError, setHistoryError] = useState(null);
    const [cemeteryError, setCemeteryError] = useState(null);
    const [parishLocationError, setParishLocationError] = useState(null);
    const [parishLocations, setParishLocations] = useState([]);

    useEffect(() => {
        const fetchLocation = async () => {
            try {
                const locationData = await apiGet(`/api/locations/${id}`);
                setLocation(locationData);
                if (locationData.history && locationData.history.length > 0) {
                    setHistory(locationData.history);
                    setHistoryLoading(false);
                }
            } catch (err) {
                setError(`Error loading location: ${err.message || err}`);
            } finally {
                setLoading(false);
            }
        };

        const fetchHistory = async () => {
            try {
                const historyData = await apiGet(`/api/locations/${id}/history`);
                setHistory(historyData);
            } catch (err) {
                setHistoryError(`Error loading history: ${err.message || err}`);
            } finally {
                setHistoryLoading(false);
            }
        };

        const fetchCemeteries = async () => {
            try {
                const cemeteryData = await apiGet(`/api/locations/${id}/cemeteries`);
                setCemeteries(cemeteryData);
            } catch (err) {
                setCemeteryError(`Error loading cemeteries: ${err.message || err}`);
            } finally {
                setCemeteryLoading(false);
            }
        };

         const fetchParishLocations = async () => {
            try {
                const parishLocationData = await apiGet(`/api/locations/${id}/parishes`);
                setParishLocations(parishLocationData);
            } catch (err) {
                setParishLocationError(`Error loading parish locations: ${err.message || err}`);
            } finally {
                setParishLocationLoading(false);
            }
        };

        fetchLocation();
        fetchHistory();
        fetchCemeteries();
        fetchParishLocations();
    }, [id]);

    if (loading) return <p>Loading location details...</p>;
    if (error) return <Alert variant="danger">{error}</Alert>;

    return (
        <Container className="mt-5">
            {/* Location Details */}
            <Row className="mb-4">
                <Col md={6}>
                    <Card>
                        <Card.Body>
                            <Card.Title>
                                {location.locationName} ({settlementTypeLabels[location.settlementType] || location.settlementType})
                            </Card.Title>
                            <ListGroup variant="flush">
                                <ListGroup.Item>
                                    <strong>Establishment Date:</strong>{" "}
                                    {location.establishmentDate
                                        ? dateStringFormatter(location.establishmentDate)
                                        : "Unknown"}
                                </ListGroup.Item>
                                <ListGroup.Item>
                                    <strong>GPS Latitude:</strong> {location.gpsLatitude}
                                </ListGroup.Item>
                                <ListGroup.Item>
                                    <strong>GPS Longitude:</strong> {location.gpsLongitude}
                                </ListGroup.Item>
                            </ListGroup>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>

            {/* Historical Details */}
            <Row>
                <Col md={8}>
                    <h3>Historical Details</h3>
                    {historyLoading ? (
                        <Spinner animation="border" />
                    ) : historyError ? (
                        <Alert variant="danger">{historyError}</Alert>
                    ) : history.length > 0 ? (
                        <ListGroup>
                            {history.map((record) => (
                                <ListGroup.Item key={record.id}>
                                    <strong>Period:</strong>{" "}
                                    {dateStringFormatter(record.startDate)} -{" "}
                                    {record.endDate
                                        ? dateStringFormatter(record.endDate)
                                        : "Present"}
                                    <br />
                                    <strong>Country:</strong>{" "}
                                    {record.countryId ? (
                                        <Link to={`/countries/show/${record.countryId}`}>{record.countryName}</Link>
                                    ) : (
                                        record.countryName
                                    )}
                                    <br />
                                    <strong>Province:</strong>{" "}
                                    {record.provinceId ? (
                                        <Link to={`/countries/${record.countryId}/provinces/${record.provinceId}`}>{record.provinceName}</Link>
                                    ) : (
                                        record.provinceName
                                    )}
                                    <br />
                                    <strong>District:</strong>{" "}
                                    {record.districtId ? (
                                        <Link to={`/countries/${record.countryId}/provinces/${record.provinceId}/districts/${record.districtId}`}>{record.districtName}</Link>
                                    ) : (
                                        record.districtName
                                    )}
                                    <br />
                                    <strong>Subdivision:</strong>{" "}
                                    {record.subdivisionId ? (
                                        <Link to={`/subdivisions/show/${record.subdivisionId}`}>
                                            {record.subdivisionName}
                                        </Link>
                                    ) : (
                                        record.subdivisionName || "-"
                                    )}
                                    <br />
                                    <strong>Notes:</strong> {record.notes || "N/A"}
                                </ListGroup.Item>
                            ))}
                        </ListGroup>
                    ) : (
                        <Alert variant="info">No historical records available.</Alert>
                    )}
                </Col>
            </Row>

            {/* Cemetery Section */}
            <Row className="mt-4">
                <Col md={8}>
                    <h3>Cemeteries</h3>
                    {cemeteryLoading ? (
                        <Spinner animation="border" />
                    ) : cemeteryError ? (
                        <Alert variant="danger">{cemeteryError}</Alert>
                    ) : cemeteries.length > 0 ? (
                        <ListGroup>
                            {cemeteries.map((cemetery) => (
                                <ListGroup.Item key={cemetery.id}>
                                    <strong>Name:</strong> {cemetery.cemeteryName}
                                    <br />
                                    {cemetery.webLink && (
                                        <>
                                            <strong>Website:</strong>{" "}
                                            <a href={cemetery.webLink} target="_blank" rel="noreferrer">
                                                {cemetery.webLink}
                                            </a>
                                        </>
                                    )}
                                </ListGroup.Item>
                            ))}
                        </ListGroup>
                    ) : (
                        <Alert variant="info">No cemeteries found for this location.</Alert>
                    )}
                </Col>
            </Row>

            {/* Parish Section */}
            <Row className="mt-4">
                <Col md={8}>
                    <h3>Parishes</h3>
                    {parishLocationLoading ? (
                        <Spinner animation="border" />
                    ) : parishLocationError ? (
                        <Alert variant="danger">{parishLocationError}</Alert>
                    ) : parishLocations.length > 0 ? (
                        <ListGroup>
                            {parishLocations.map((parishLocation) => (
                                <ListGroup.Item key={parishLocation.id}>
                                    <strong>Name:</strong> {parishLocation.parishName}

                                </ListGroup.Item>
                            ))}
                        </ListGroup>
                    ) : (
                        <Alert variant="info">No parishes found for this location.</Alert>
                    )}
                </Col>
            </Row>

        </Container>
    );
};

export default LocationDetail;
