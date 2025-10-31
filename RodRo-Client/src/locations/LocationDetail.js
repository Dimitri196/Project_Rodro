import React, { useEffect, useState, useMemo } from "react";
import { useParams, Link } from "react-router-dom";
import { Container, Row, Col, Card, ListGroup, Alert, Spinner, Accordion, Badge, Form} from "react-bootstrap";
import { apiGet } from "../utils/api";
import settlementTypeLabels from "../constants/settlementTypeLabels";
import { useSession } from "../contexts/session";
import { MapContainer, TileLayer, Marker, Popup } from 'react-leaflet';
import 'leaflet/dist/leaflet.css';
import L from 'leaflet';

// Fix for default marker icon issue with Leaflet in React
delete L.Icon.Default.prototype._getIconUrl; 
L.Icon.Default.mergeOptions({
    iconRetinaUrl: 'https://unpkg.com/leaflet@1.7.1/dist/images/marker-icon2x.png',
    iconUrl: 'https://unpkg.com/leaflet@1.7.1/dist/images/marker-icon.png',
    shadowUrl: 'https://unpkg.com/leaflet@1.7.1/dist/images/marker-shadow.png',
});

// --- Settlement Color Map ---
const colorMap = {
    'CITY': 'primary',
    'TOWN': 'info',
    'VILLAGE': 'success',
    'FARM': 'secondary',
    'FORESTERS_LODGE': 'dark',
    'KHUTOR': 'secondary',
    'SETTLEMENT': 'info',
    'COLONY': 'warning',
    'RAILWAY_STATION': 'primary',
    'BRICKYARD': 'danger',
    'MILL_SETTLEMENT': 'success',
    'ZASCIANEK': 'secondary',
    'FOREST_SETTLEMENT': 'dark',
    'FACTORY_SETTLEMENT': 'danger',
    'SAWMILL': 'warning',
    'SUBURB': 'info',
    'TAR_FACTORY': 'danger'
};

// =========================================================================
// Person List Helper Component (Optimized to display full name from parts)
// =========================================================================
/**
 * Renders a list of people for a specific event (e.g., births, deaths).
 * @param {{ people: Array<{id: number, givenName: string, surname: string, birthYear: number, deathYear: number, socialStatus: string}>, label: string }} props
 */
const PersonList = ({ people, label }) => {
    if (!people || people.length === 0) {
        return (
            <Alert variant="secondary" className="text-center mb-0 py-2 small">
                No recorded {label} match the current filter.
            </Alert>
        );
    }

    return (
        <ListGroup variant="flush" style={{ maxHeight: "300px", overflowY: "auto" }}>
            {people.map((person) => (
                <ListGroup.Item 
                    key={person.id} 
                    className="d-flex justify-content-between align-items-center px-2 py-1 small"
                >
                    <Link 
                        to={`/persons/show/${person.id}`} 
                        className="text-decoration-none text-primary fw-medium"
                    >
                        {person.givenName} {person.surname}
                    </Link>
                    <span className="text-muted small">
                        {person.birthYear || "?"} - {person.deathYear || "?"}
                    </span>
                    <Badge bg="secondary" className="ms-2">
                        {person.socialStatus || 'Unknown'}
                    </Badge>
                </ListGroup.Item>
            ))}
        </ListGroup>
    );
};
// =========================================================================

const LocationDetail = () => {
    const { id } = useParams();
    const { session } = useSession();
    const isAdmin = session.data?.isAdmin === true;

    const [location, setLocation] = useState(null);
    const [cemeteries, setCemeteries] = useState([]);
    const [parishLocations, setParishLocations] = useState([]);
    
    // START: NEW STATE VARIABLES
    const [births, setBirths] = useState([]);
    const [deaths, setDeaths] = useState([]);
    const [burials, setBurials] = useState([]);
    const [baptisms, setBaptisms] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');
    // END: NEW STATE VARIABLES

    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const getSettlementColor = (type) => colorMap[type] || 'secondary';

    useEffect(() => {
        const fetchData = async () => {
            setLoading(true);
            setError(null);
            try {
                // Fetch all data concurrently
                const [
                    locationData, 
                    cemeteryData, 
                    parishLocationData, 
                    birthData, 
                    deathData, 
                    burialData, 
                    baptismData
                ] = await Promise.all([
                    apiGet(`/api/locations/${id}`),
                    apiGet(`/api/locations/${id}/cemeteries`),
                    apiGet(`/api/locations/${id}/parishes`),
                    // START: NEW API CALLS
                    apiGet(`/api/locations/${id}/births`),
                    apiGet(`/api/locations/${id}/deaths`),
                    apiGet(`/api/locations/${id}/burials`),
                    apiGet(`/api/locations/${id}/baptisms`),
                    // END: NEW API CALLS
                ]);

                setLocation(locationData);
                setCemeteries(cemeteryData);
                setParishLocations(parishLocationData);
                
                // START: NEW STATE UPDATES
                setBirths(birthData);
                setDeaths(deathData);
                setBurials(burialData);
                setBaptisms(baptismData);
                // END: NEW STATE UPDATES

            } catch (err) {
                console.error("Error fetching location details:", err);
                setError(`Failed to load location details: ${err.message || err}`);
            } finally {
                setLoading(false);
            }
        };

        fetchData();
    }, [id]);

    const handleSearchChange = (event) => {
        setSearchTerm(event.target.value);
    };

    // =========================================================================
    // NEW: Search Filtering Logic using useMemo for performance
    // =========================================================================
    const normalizeSearchTerm = searchTerm.toLowerCase().trim();

    const filterPeople = (people) => {
        if (!normalizeSearchTerm) {
            return people;
        }
        return people.filter(person => {
            const fullName = `${person.givenName} ${person.surname}`.toLowerCase();
            return fullName.includes(normalizeSearchTerm);
        });
    };

    const filteredBirths = useMemo(() => filterPeople(births), [births, normalizeSearchTerm]);
    const filteredDeaths = useMemo(() => filterPeople(deaths), [deaths, normalizeSearchTerm]);
    const filteredBurials = useMemo(() => filterPeople(burials), [burials, normalizeSearchTerm]);
    const filteredBaptisms = useMemo(() => filterPeople(baptisms), [baptisms, normalizeSearchTerm]);
    // =========================================================================


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

    const hasGps = location.gpsLatitude && location.gpsLongitude;
    const position = hasGps ? [location.gpsLatitude, location.gpsLongitude] : [0, 0];
    const initialZoom = hasGps ? 14 : 2; 
    const badgeVariant = getSettlementColor(location.settlementType);

    return (
        <Container className="my-5 py-4 bg-light rounded shadow-lg">
            {/* Font Awesome for icons (ensure it's linked in index.html) */}
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" integrity="sha512-1ycn6IcaQQ40JuKmWswL3QbpgExX+Q6bLSYg5rXW4remp4o/Y6fFZj/gX2Q2K2V2F5R5/5Q5w5w5w5w5w==" crossOrigin="anonymous" referrerPolicy="no-referrer" />

            <Row className="mb-4">
                <Col md={12} className="text-center">
                    <h1 className="display-4 fw-bold text-dark mb-3">
                        <i className="fas fa-map-marker-alt me-3 text-primary"></i>
                        {location.locationName}
                    </h1>
                    
                    <div className="mb-4">
                        <Badge 
                            bg={badgeVariant} 
                            className="fs-6 p-2 fw-semibold shadow-sm text-uppercase"
                        >
                            {settlementTypeLabels[location.settlementType] || location.settlementType}
                        </Badge>
                    </div>

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

            {/* START: NEW ROW FOR GENEALOGICAL EVENTS (With Search Panel) */}
            <Row className="justify-content-center">
                <Col md={12} className="mb-4">
                    <Card className="shadow-lg border-0 rounded-4">
                        <Card.Header as="h5" className="bg-success text-white py-3 rounded-top-4">
                            <i className="fas fa-users me-2"></i>Associated Persons by Event
                        </Card.Header>
                        <Card.Body className="p-4">
                            
                            {/* START: SEARCH FIELD */}
                            <div className="mb-4">
                                <Form.Control
                                    type="search"
                                    placeholder="Search persons by name (First or Last)..."
                                    className="rounded-pill"
                                    value={searchTerm}
                                    onChange={handleSearchChange}
                                />
                            </div>
                            {/* END: SEARCH FIELD */}

                            <Accordion defaultActiveKey="0">
                                <Accordion.Item eventKey="0">
                                    <Accordion.Header>
                                        <i className="fas fa-baby me-2 text-info"></i>
                                        **Births** ({filteredBirths.length} of {births.length})
                                    </Accordion.Header>
                                    <Accordion.Body className="p-2">
                                        <PersonList people={filteredBirths} label="births" />
                                    </Accordion.Body>
                                </Accordion.Item>

                                <Accordion.Item eventKey="1">
                                    <Accordion.Header>
                                        <i className="fas fa-cross me-2 text-danger"></i>
                                        **Deaths** ({filteredDeaths.length} of {deaths.length})
                                    </Accordion.Header>
                                    <Accordion.Body className="p-2">
                                        <PersonList people={filteredDeaths} label="deaths" />
                                    </Accordion.Body>
                                </Accordion.Item>

                                <Accordion.Item eventKey="2">
                                    <Accordion.Header>
                                        <i className="fas fa-tombstone-alt me-2 text-secondary"></i>
                                        **Burials** ({filteredBurials.length} of {burials.length})
                                    </Accordion.Header>
                                    <Accordion.Body className="p-2">
                                        <PersonList people={filteredBurials} label="burials" />
                                    </Accordion.Body>
                                </Accordion.Item>
                                
                                <Accordion.Item eventKey="3">
                                    <Accordion.Header>
                                        <i className="fas fa-hands-praying me-2 text-warning"></i>
                                        **Baptisms** ({filteredBaptisms.length} of {baptisms.length})
                                    </Accordion.Header>
                                    <Accordion.Body className="p-2">
                                        <PersonList people={filteredBaptisms} label="baptisms" />
                                    </Accordion.Body>
                                </Accordion.Item>
                            </Accordion>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
            {/* END: NEW ROW FOR GENEALOGICAL EVENTS */}

            {/* Historical Details Card */}
            <Row>
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
