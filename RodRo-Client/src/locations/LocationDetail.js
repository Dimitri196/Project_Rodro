import React, { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import { Container, Row, Col, Card, ListGroup, Alert, Spinner, Accordion } from "react-bootstrap";
import { apiGet } from "../utils/api";
import settlementTypeLabels from "../constants/settlementTypeLabels";
import { useSession } from "../contexts/session";

// 1. Import react-leaflet components
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';
// 2. Import Leaflet CSS (make sure this is globally accessible, e.g., in App.js or index.js)
import 'leaflet/dist/leaflet.css';

// 3. IMPORTANT: Fix for default marker icons not showing up in Webpack/Create React App environments
// This is a common issue with react-leaflet and needs to be included once per application
import L from 'leaflet';
delete L.Icon.Default.prototype._getIconUrl; // Prevents webpack from trying to find default icons
L.Icon.Default.mergeOptions({
    iconRetinaUrl: 'https://unpkg.com/leaflet@1.7.1/dist/images/marker-icon-2x.png',
    iconUrl: 'https://unpkg.com/leaflet@1.7.1/dist/images/marker-icon.png',
    shadowUrl: 'https://unpkg.com/leaflet@1.7.1/dist/images/marker-shadow.png',
});


const LocationDetail = () => {
    const { id } = useParams();
    const { session } = useSession();
    const isAdmin = session.data?.isAdmin === true;

    const [location, setLocation] = useState(null);
    const [cemeteries, setCemeteries] = useState([]);
    const [parishLocations, setParishLocations] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchData = async () => {
            setLoading(true);
            setError(null);
            try {
                const locationData = await apiGet(`/api/locations/${id}`);
                setLocation(locationData);

                const [cemeteryData, parishLocationData] = await Promise.all([
                    apiGet(`/api/locations/${id}/cemeteries`),
                    apiGet(`/api/locations/${id}/parishes`)
                ]);

                setCemeteries(cemeteryData);
                setParishLocations(parishLocationData);

            } catch (err) {
                console.error("Error fetching location details:", err);
                setError(`Failed to load location details: ${err.message || err}`);
            } finally {
                setLoading(false);
            }
        };

        fetchData();
    }, [id]);

    if (loading) {
        return (
            <Container className="my-5 text-center">
                <Spinner animation="border" role="status">
                    <span className="visually-hidden">Loading...</span>
                </Spinner>
                <p className="mt-3 text-muted">Loading location details...</p>
            </Container>
        );
    }

    if (error) {
        return (
            <Container className="my-5">
                <Alert variant="danger" className="text-center shadow-sm rounded-3">
                    <i className="fas fa-exclamation-triangle me-2"></i>{error}
                </Alert>
            </Container>
        );
    }

    if (!location) {
        return (
            <Container className="my-5">
                <Alert variant="info" className="text-center shadow-sm rounded-3">
                    <i className="fas fa-info-circle me-2"></i>Location not found.
                </Alert>
            </Container>
        );
    }

    // Check if GPS coordinates are available to render the map
    const hasGps = location.gpsLatitude && location.gpsLongitude;

    // Define the initial map center and zoom for Leaflet
    // Use the location's coordinates if available, otherwise a default for safety (though map won't render without GPS)
    const position = hasGps ? [location.gpsLatitude, location.gpsLongitude] : [0, 0];
    const initialZoom = 14; // A good starting zoom for a close-up view (e.g., 10-16 for city/street level)

    
    return (
        <Container className="my-5 py-4 bg-light rounded shadow-lg">
            {/* Font Awesome for icons (ensure it's linked in index.html) */}
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" integrity="sha512-1ycn6IcaQQ40JuKmWswL3QbpgExX+Q6bLSYg5rXW4remp4o/Y6fFZj/gX2Q2K2V2F5R5/5Q5w5w5w5w5w==" crossOrigin="anonymous" referrerPolicy="no-referrer" />

            <Row className="mb-4">
                <Col md={12} className="text-center">
                    <h1 className="display-4 fw-bold text-primary mb-3">
                        <i className="fas fa-map-marker-alt me-3"></i>
                        {location.locationName}
                    </h1>
                    <p className="lead text-muted fst-italic">
                        {settlementTypeLabels[location.settlementType] || location.settlementType}
                    </p>
                    {isAdmin && (
                        <Link to={`/locations/edit/${location._id}`} className="btn btn-warning btn-lg rounded-pill px-4 py-2 shadow-sm mt-3">
                            <i className="fas fa-edit me-2"></i>Edit Location
                        </Link>
                    )}
                </Col>
            </Row>

            <Row className="justify-content-center">
                {/* Basic Information Card */}
                <Col md={4} className="mb-4">
                    <Card className="shadow-lg border-0 rounded-4 h-100">
                        <Card.Header as="h5" className="bg-primary text-white py-3 rounded-top-4">
                            <i className="fas fa-info-circle me-2"></i>Basic Information
                        </Card.Header>
                        <Card.Body className="p-4">
                            <ListGroup variant="flush">
                                <ListGroup.Item className="d-flex justify-content-between align-items-center px-0">
                                    <strong>Establishment Year:</strong>
                                    <span>{location.establishmentYear || "Unknown"}</span>
                                </ListGroup.Item>
                                <ListGroup.Item className="d-flex justify-content-between align-items-center px-0">
                                    <strong>GPS Latitude:</strong>
                                    <span>{location.gpsLatitude || "-"}</span>
                                </ListGroup.Item>
                                <ListGroup.Item className="d-flex justify-content-between align-items-center px-0">
                                    <strong>GPS Longitude:</strong>
                                    <span>{location.gpsLongitude || "-"}</span>
                                </ListGroup.Item>
                            </ListGroup>
                        </Card.Body>
                    </Card>
                </Col>

                {/* Interactive OpenStreetMap Card */}
                <Col md={4} className="mb-4">
                    <Card className="shadow-lg border-0 rounded-4 h-100">
                        <Card.Header as="h5" className="bg-primary text-white py-3 rounded-top-4">
                            <i className="fas fa-map-marked-alt me-2"></i>
                            Interactive Map (OpenStreetMap)
                        </Card.Header>
                        <Card.Body
                            className="p-2 d-flex justify-content-center align-items-center"
                            style={{ minHeight: "300px" }}
                        >
                            {hasGps ? (
                                <MapContainer
                                    center={position}
                                    zoom={initialZoom}
                                    scrollWheelZoom={true}
                                    style={{ height: "100%", width: "100%", borderRadius: "0.25rem" }}
                                >
                                    <TileLayer
                                        attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                                        url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                                    />
                                    <Marker position={position}>
                                        <Popup>{location.locationName}</Popup>
                                    </Marker>
                                </MapContainer>
                            ) : (
                                <div className="text-center text-muted">
                                    <i className="fas fa-map-pin fa-2x mb-3"></i>
                                    <p className="mb-0">No GPS data available for this location.</p>
                                </div>
                            )}
                        </Card.Body>
                    </Card>
                </Col>

                {/* Flag Card */}
                <Col md={4} className="mb-4">
                    <Card className="shadow-lg border-0 rounded-4 h-100">
                        <Card.Header as="h5" className="bg-primary text-white py-3 rounded-top-4">
                            <i className="fas fa-flag me-2"></i>Location flag or emblem
                        </Card.Header>
                        <Card.Body className="p-4 d-flex justify-content-center align-items-center">
                            {location.locationImageUrl ? (
                                <img
                                    src={location.locationImageUrl}
                                    alt={`${location.locationName} flag`}
                                    style={{
                                        maxWidth: "100%",
                                        height: "auto",
                                        maxHeight: "250px",
                                        borderRadius: "0.5rem",
                                    }}
                                    className="img-fluid"
                                />
                            ) : (
                                <div className="text-center text-muted">
                                    <i className="fas fa-image fa-3x mb-3"></i>
                                    <p>No flag image available.</p>
                                </div>
                            )}
                        </Card.Body>
                    </Card>
                </Col>
            </Row>

            <Row>
                {/* Historical Details Card */}
                <Col md={12} className="mb-4">
                    <Card className="shadow-lg border-0 rounded-4 h-100">
                        <Card.Header as="h5" className="bg-primary text-white py-3 rounded-top-4">
                            <i className="fas fa-history me-2"></i>Historical Details
                        </Card.Header>
                        <Card.Body className="p-4">
                            {location.locationHistories && location.locationHistories.length > 0 ? (
                                <Accordion alwaysOpen>
                                    {location.locationHistories.map((record, idx) => (
                                        <Accordion.Item eventKey={idx.toString()} key={record.id}>
                                            <Accordion.Header>
                                                <span className="fw-bold me-2">
                                                    {record.startDate || "-"} – {record.endDate || "Present"}
                                                </span>
                                                <span className="text-muted">
                                                    ({record.countryName || "Unknown"})
                                                </span>
                                            </Accordion.Header>
                                            <Accordion.Body>
                                                <Row>
                                                    <Col md={6}>
                                                        <p className="mb-1">
                                                            <strong>Country:</strong>{" "}
                                                            {record.countryId ? (
                                                                <Link
                                                                    to={`/countries/show/${record.countryId}`}
                                                                    className="text-decoration-none text-primary"
                                                                >
                                                                    {record.countryName}
                                                                </Link>
                                                            ) : (
                                                                record.countryName || "-"
                                                            )}
                                                        </p>
                                                        <p className="mb-1">
                                                            <strong>Province:</strong>{" "}
                                                            {record.provinceId ? (
                                                                <Link
                                                                    to={`/countries/${record.countryId}/provinces/${record.provinceId}`}
                                                                    className="text-decoration-none text-primary"
                                                                >
                                                                    {record.provinceName}
                                                                </Link>
                                                            ) : (
                                                                record.provinceName || "-"
                                                            )}
                                                        </p>
                                                    </Col>

                                                    <Col md={6}>
                                                        <p className="mb-1">
                                                            <strong>District:</strong>{" "}
                                                            {record.districtId ? (
                                                                <Link
                                                                    to={`/countries/${record.countryId}/provinces/${record.provinceId}/districts/${record.districtId}`}
                                                                    className="text-decoration-none text-primary"
                                                                >
                                                                    {record.districtName}
                                                                </Link>
                                                            ) : (
                                                                record.districtName || "-"
                                                            )}
                                                        </p>
                                                        <p className="mb-1">
                                                            <strong>Subdivision:</strong>{" "}
                                                            {record.subdivisionId ? (
                                                                <Link
                                                                    to={`/subdivisions/show/${record.subdivisionId}`}
                                                                    className="text-decoration-none text-primary"
                                                                >
                                                                    {record.subdivisionName}
                                                                </Link>
                                                            ) : (
                                                                record.subdivisionName || "-"
                                                            )}
                                                        </p>
                                                    </Col>
                                                </Row>

                                                {/* Notes Full Width */}
                                                <Row>
                                                    <Col>
                                                        <p className="mt-2 mb-0">
                                                            <strong>Notes:</strong> {record.notes || "N/A"}
                                                        </p>
                                                    </Col>
                                                </Row>
                                            </Accordion.Body>
                                        </Accordion.Item>
                                    ))}
                                </Accordion>
                            ) : (
                                <Alert variant="info" className="text-center mt-3">
                                    No historical records available.
                                </Alert>
                            )}
                        </Card.Body>
                    </Card>
                </Col>
            </Row>

            <Row className="justify-content-center">
                {/* Parishes Card */}
                <Col md={6} className="mb-4">
                    <Card className="shadow-lg border-0 rounded-4 h-100">
                        <Card.Header as="h5" className="bg-primary text-white py-3 rounded-top-4">
                            <i className="fas fa-church me-2"></i>Parishes
                        </Card.Header>
                        <Card.Body className="p-4">
                            {parishLocations.length > 0 ? (
                                <div style={{ maxHeight: "300px", overflowY: "auto" }}>
                                    <ListGroup variant="flush">
                                        {parishLocations.map((parishLocation) => (
                                            <ListGroup.Item key={parishLocation.id} className="pb-3 px-0 border-bottom">
                                                <strong>Name:</strong>{" "}
                                                <Link
                                                    to={`/parishes/show/${parishLocation.parishId}`}
                                                    className="text-decoration-none text-primary"
                                                >
                                                    {parishLocation.parishName} ({parishLocation.mainChurchName})

                                                </Link>
                                                {/* Additional parish details can go here */}
                                            </ListGroup.Item>
                                        ))}
                                    </ListGroup>
                                </div>
                            ) : (
                                <Alert variant="info" className="text-center mb-0">
                                    No parishes found for this location.
                                </Alert>
                            )}
                        </Card.Body>
                    </Card>
                </Col>

                {/* Cemeteries Card */}
                <Col md={6} className="mb-4">
                    <Card className="shadow-lg border-0 rounded-4 h-100">
                        <Card.Header as="h5" className="bg-primary text-white py-3 rounded-top-4">
                            <i className="fas fa-cross me-2"></i>Cemeteries
                        </Card.Header>
                        <Card.Body className="p-4">
                            {cemeteries.length > 0 ? (
                                <div style={{ maxHeight: "300px", overflowY: "auto" }}>
                                    <ListGroup variant="flush">
                                        {cemeteries.map((cemetery) => (
                                            <ListGroup.Item key={cemetery.id} className="pb-3 px-0 border-bottom">
                                                <strong>Name:</strong>{" "}
                                                <Link
                                                    to={`/cemeteries/show/${cemetery._id}`}
                                                    className="text-decoration-none text-primary"
                                                >
                                                    {cemetery.cemeteryName}
                                                </Link>
                                                <br />
                                                {cemetery.webLink && (
                                                    <>
                                                        <strong>Website:</strong>{" "}
                                                        <a
                                                            href={cemetery.webLink}
                                                            target="_blank"
                                                            rel="noreferrer"
                                                            className="text-decoration-none text-info"
                                                        >
                                                            {cemetery.webLink}
                                                        </a>
                                                    </>
                                                )}
                                            </ListGroup.Item>
                                        ))}
                                    </ListGroup>
                                </div>
                            ) : (
                                <Alert variant="info" className="text-center mb-0">
                                    <i className="fas fa-cross me-2"></i>No cemeteries found for this location.
                                </Alert>
                            )}
                        </Card.Body>
                    </Card>
                </Col>
            </Row>


            <Row className="justify-content-center">
                <Col md={12} className="mb-4">
                    <Card className="shadow-lg border-0 rounded-4 h-100">
                        <Card.Header as="h5" className="bg-primary text-white py-3 rounded-top-4">
                            <i className="fas fa-book me-2"></i>Sources
                        </Card.Header>
                        <Card.Body className="p-4">
                            {location.sources && location.sources.length > 0 ? (
                                <div style={{ maxHeight: "300px", overflowY: "auto" }}>
                                    <ListGroup variant="flush">
                                        {location.sources.map((source) => (
                                            <ListGroup.Item key={source._id} className="pb-3 px-0 border-bottom">
                                                <strong>Source Title:</strong>{" "}
                                                <Link
                                                    to={`/sources/show/${source._id}`}
                                                    className="text-decoration-none text-primary"
                                                >
                                                    {source.title || "N/A"}
                                                </Link>
                                                <br />
                                                <strong>Source Reference:</strong> {source.reference || "N/A"}
                                                <br />
                                                <strong>Description:</strong> {source.description || "N/A"}
                                                <br />
                                                {source.webLink && (
                                                    <>
                                                        <strong>Web Link:</strong>{" "}
                                                        <a
                                                            href={source.webLink}
                                                            target="_blank"
                                                            rel="noreferrer"
                                                            className="text-decoration-none text-info"
                                                        >
                                                            {source.webLink}
                                                        </a>
                                                    </>
                                                )}
                                            </ListGroup.Item>
                                        ))}
                                    </ListGroup>
                                </div>
                            ) : (
                                <Alert variant="info" className="text-center mb-0">
                                    No sources found for this location.
                                </Alert>
                            )}
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default LocationDetail;
