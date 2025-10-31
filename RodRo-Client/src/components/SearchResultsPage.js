import React, { useState, useEffect } from 'react';
import { useSearchParams, Link } from "react-router-dom";
import { Container, Row, Col, Spinner, Card, Alert } from "react-bootstrap";
import { apiGet } from "../utils/api";

// A reusable card component for search results, maintaining the same style
const ResultsCard = ({ title, text, path, icon }) => (
    <Card className="h-100 w-100 card-hover-effect shadow-sm">
        <Card.Body className="card-body-equal">
            <div className="icon-wrapper">
                <i className={`${icon} fa-2x text-primary`}></i>
            </div>
            <Card.Title className="card-title">{title}</Card.Title>
            <Card.Text className="card-text">{text}</Card.Text>
            <Link to={path} className="btn btn-outline-primary rounded-pill px-4 py-2 shadow-sm">
                View Details
            </Link>
        </Card.Body>
    </Card>
);

function SearchResultsPage() {
    const [searchParams] = useSearchParams();
    const query = searchParams.get('q');

    const [results, setResults] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        if (!query) {
            setLoading(false);
            return;
        }

        const fetchResults = async () => {
            setLoading(true);
            setError(null);
            try {
                const response = await apiGet(`/api/search?q=${query}`);
                // Ensure response data is handled correctly
                setResults(response); 
            } catch (err) {
                console.error("Failed to fetch search results:", err);
                setError("Failed to load search results. Please try again later.");
            } finally {
                setLoading(false);
            }
        };

        fetchResults();
    }, [query]);

    // Check if any results were found
    const hasResults = results && (
        results.persons?.length > 0 ||
        results.locations?.length > 0 ||
        results.parishes?.length > 0
    );

    if (loading) {
        return (
            <div className="d-flex justify-content-center align-items-center vh-100 bg-light">
                <div className="text-center">
                    <Spinner animation="border" variant="primary" className="mb-3" />
                    <p className="text-muted">Searching for "{query}"...</p>
                </div>
            </div>
        );
    }

    return (
        <Container className="my-5">
            <style>
                {/* Your existing CSS styles */}
                {`
                .card-hover-effect {
                    transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
                    border: 1px solid #dee2e6;
                    background-color: #fff;
                    border-radius: 1rem;
                }
                .card-hover-effect:hover {
                    transform: translateY(-6px);
                    box-shadow: 0 0.75rem 1.5rem rgba(0, 0, 0, 0.1);
                    border-color: #0d6efd;
                }
                .card-body-equal {
                    display: flex;
                    flex-direction: column;
                    justify-content: space-between;
                    padding: 1.5rem;
                    text-align: center;
                }
                .icon-wrapper {
                    width: 64px;
                    height: 64px;
                    margin: 0 auto 1rem;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    background-color: #f8f9fa;
                    border: 1px solid #dee2e6;
                    border-radius: 50%;
                    box-shadow: 0 0.25rem 0.5rem rgba(0, 0, 0, 0.05);
                }
                .card-title {
                    font-weight: 600;
                    font-size: 1.15rem;
                    color: #343a40;
                }
                .card-text {
                    color: #6c757d;
                    font-size: 0.95rem;
                    flex-grow: 1;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                }
                .card-body-equal .btn {
                    margin-top: 1rem;
                }
                `}
            </style>

            <h1 className="display-4 fw-bold mb-4">Search Results</h1>
            <p className="lead text-muted mb-5">Showing results for: <strong>"{query}"</strong></p>

            {error && <Alert variant="danger">{error}</Alert>}

            {!hasResults && !loading && (
                <Alert variant="info">
                    No results found for your search. Please try a different query.
                </Alert>
            )}

            {hasResults && (
                <>
                    {results.persons?.length > 0 && (
                        <div className="mb-5">
                            <h2 className="mb-4">Persons ({results.persons.length})</h2>
                            <Row xs={1} sm={2} md={3} xl={4} className="g-4">
                                {results.persons.map(person => (
                                    <Col key={person['_id']}> {/* Corrected here */}
                                        <ResultsCard
                                            title={`${person.givenName} ${person.surname}`}
                                            text={`Born: ${person.birthYear || 'N/A'}`}
                                            path={`/persons/show/${person['_id']}`} // Corrected here
                                            icon="fas fa-user-circle"
                                        />
                                    </Col>
                                ))}
                            </Row>
                        </div>
                    )}

                    {results.locations?.length > 0 && (
                        <div className="mb-5">
                            <h2 className="mb-4">Locations ({results.locations.length})</h2>
                            <Row xs={1} sm={2} md={3} xl={4} className="g-4">
                                {results.locations.map(location => (
                                    <Col key={location['_id']}> {/* Corrected here */}
                                        <ResultsCard
                                            title={location.locationName}
                                            text={`Established: ${location.establishmentYear || 'N/A'}`}
                                            path={`/locations/show/${location['_id']}`} // Corrected here
                                            icon="fas fa-map-marker-alt"
                                        />
                                    </Col>
                                ))}
                            </Row>
                        </div>
                    )}
                    
                    {results.parishes?.length > 0 && (
                        <div className="mb-5">
                            <h2 className="mb-4">Parishes ({results.parishes.length})</h2>
                            <Row xs={1} sm={2} md={3} xl={4} className="g-4">
                                {results.parishes.map(parish => (
                                    <Col key={parish['_id']}> {/* Corrected here */}
                                        <ResultsCard
                                            title={parish.parishName}
                                            text={`Established: ${parish.establishmentYear || 'N/A'}`}
                                            path={`/parishes/show/${parish['_id']}`} // Corrected here
                                            icon="fas fa-church"
                                        />
                                    </Col>
                                ))}
                            </Row>
                        </div>
                    )}
                </>
            )}
        </Container>
    );
}

export default SearchResultsPage;
