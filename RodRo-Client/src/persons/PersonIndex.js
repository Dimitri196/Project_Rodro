import React, { useEffect, useState } from "react";
import { apiDelete, apiGet } from "../utils/api";
import { Container, Row, Col, Card, Alert, Badge } from 'react-bootstrap';
import PersonTable from "./PersonTable";
import "@fortawesome/fontawesome-free/css/all.min.css";

// --- Summary badges style
const summaryBadgeVariant = {
    total: "primary",
    male: "info",
    female: "warning",
    living: "success",
    deceased: "danger",
};

// --- Scientific/Research Context Descriptions
const personStructureDescriptions = [
    {
        title: "Demographic Data",
        icon: "fas fa-users",
        text: "Each person record contains birth, death, gender, and social status, enabling demographic studies, population statistics, and genealogical analysis."
    },
    {
        title: "Spatial & Temporal Anchoring",
        icon: "fas fa-map-marker-alt",
        text: "Places of birth, death, baptism, and burial provide geospatial context, allowing historical mapping, migration studies, and lineage tracking across regions."
    },
    {
        title: "Occupations & Sources",
        icon: "fas fa-briefcase",
        text: "Occupations and linked source evidences give insights into economic history, professional distribution, and verification of archival records."
    }
];

const PersonIndex = () => {
    const [persons, setPersons] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    // --- Safe persons array for filtering
    const safePersons = Array.isArray(persons) ? persons : [];

    // --- Summary counts
    const total = safePersons.length;
    const male = safePersons.filter(p => p.gender === "MALE").length;
    const female = safePersons.filter(p => p.gender === "FEMALE").length;
    const living = safePersons.filter(p => !p.deathYear).length;
    const deceased = total - living;

    // --- Delete person handler
    const deletePerson = async (id) => {
        try {
            await apiDelete("/api/persons/" + id);
            setPersons(safePersons.filter((item) => item._id !== id));
        } catch (err) {
            console.error(err.message);
            setError(err.message);
        }
    };

    // --- Fetch persons on mount
    useEffect(() => {
        setLoading(true);
        apiGet("/api/persons")
            .then((data) => setPersons(data))
            .catch((err) => setError(err.message))
            .finally(() => setLoading(false));
    }, []);

    if (loading) return <div className="text-center my-5">Loading person records...</div>;
    if (error) return <Alert variant="danger" className="my-5">{error}</Alert>;

    return (
        <Container className="my-5">
            <header className="text-center mb-4">
                <h1 className="display-5 fw-bold text-dark mb-2">
                    <i className="fas fa-user me-3 text-success"></i>Person Records
                </h1>
                <p className="lead text-secondary fst-italic mb-4 mx-auto" style={{maxWidth: '700px'}}>
                    This index catalogs individuals across historical records. It includes demographic, social, and occupational data, enabling genealogical, demographic, and historical research.
                </p>
            </header>

            {/* Scientific/Structural context */}
            <Row className="mb-4">
                {personStructureDescriptions.map((item, idx) => (
                    <Col md={4} key={idx}>
                        <Card className="shadow-sm border-start border-success border-4 rounded-3 mb-3">
                            <Card.Body className="p-3">
                                <h6 className="fw-bold text-success mb-1">
                                    <i className={`${item.icon} me-2`}></i> {item.title}
                                </h6>
                                <p className="text-muted small mb-0">{item.text}</p>
                            </Card.Body>
                        </Card>
                    </Col>
                ))}
            </Row>

            {/* Summary badges */}
            <Row className="mb-4">
                <Col>
                    <Badge bg={summaryBadgeVariant.total} className="me-2 py-2 px-3">
                        Total: {total}
                    </Badge>
                    <Badge bg={summaryBadgeVariant.male} className="me-2 py-2 px-3">
                        Male: {male}
                    </Badge>
                    <Badge bg={summaryBadgeVariant.female} className="me-2 py-2 px-3">
                        Female: {female}
                    </Badge>
                    <Badge bg={summaryBadgeVariant.living} className="me-2 py-2 px-3">
                        Living: {living}
                    </Badge>
                    <Badge bg={summaryBadgeVariant.deceased} className="py-2 px-3">
                        Deceased: {deceased}
                    </Badge>
                </Col>
            </Row>

            {/* Person Table */}
            <PersonTable
                label="Number of persons:"
                deletePerson={deletePerson}
                items={safePersons}
            />
        </Container>
    );
};

export default PersonIndex;
