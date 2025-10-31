import React, { useEffect, useState, useMemo, useCallback } from "react";
import { Link } from "react-router-dom";
import { apiDelete, apiGet } from "../utils/api"; 
import { Spinner, Button, Alert, Card, Container, Row, Col, Pagination } from "react-bootstrap";
import { useSession } from "../contexts/session";
import "@fortawesome/fontawesome-free/css/all.min.css"; 

const ArticleIndex = () => {
    const { session } = useSession();
    const isAdmin = session.data?.isAdmin === true;

    // State for API data
    const [articles, setArticles] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    
    // --- State for Server-Side Pagination & Sorting ---
    const [page, setPage] = useState(0);
    const [totalPages, setTotalPages] = useState(1);
    const [queryParams, setQueryParams] = useState({
        sortBy: "createdAt", 
        sortOrder: "desc", 
        size: 9, // Display 9 cards per page for a 3x3 layout
    });
    // ----------------------------------------------------

    // Effect to fetch data whenever page or queryParams changes
    useEffect(() => {
        setLoading(true);
        setError("");

        // Construct the query string parameters for the backend
        const params = {
            page: page,
            size: queryParams.size,
            // Format: sort=field,direction (e.g., sort=createdAt,desc)
            sort: `${queryParams.sortBy},${queryParams.sortOrder}`,
        };

        // The backend returns a Page object: { content: [...], totalPages: N, number: P, ... }
        apiGet("/api/articles", { params }) 
            .then((pageResponse) => {
                setArticles(pageResponse.content);
                setTotalPages(pageResponse.totalPages);
                // Ensure page number is synced in case initial page was out of bounds
                setPage(pageResponse.number); 
                setError("");
            })
            .catch((err) => {
                setError("Failed to load research papers. Please check the API connection.");
                console.error("API Error fetching articles:", err);
            })
            .finally(() => setLoading(false));
            
    }, [page, queryParams.sortBy, queryParams.sortOrder, queryParams.size]);
    
    // --- HANDLERS ---

    // Handler to change sorting options (resets to first page)
    const handleSortChange = (newSortBy) => {
        setPage(0); // Reset to page 0 when sorting changes
        setQueryParams(prev => ({ ...prev, sortBy: newSortBy }));
    };

    // Handler to toggle sort order (resets to first page)
    const toggleSortOrder = () => {
        setPage(0); // Reset to page 0 when order changes
        setQueryParams(prev => ({ 
            ...prev, 
            sortOrder: prev.sortOrder === 'desc' ? 'asc' : 'desc' 
        }));
    };

const handleDelete = async (articleId) => {
    if (!window.confirm("CONFIRMATION: Delete action will permanently remove this paper. Proceed?")) return;

    try {
        setLoading(true); // show spinner during delete
        await apiDelete(`/api/articles/${articleId}`);

        // ✅ Re-fetch articles after successful delete
        const params = {
            page: 0,
            size: queryParams.size,
            sort: `${queryParams.sortBy},${queryParams.sortOrder}`,
        };

        const refreshed = await apiGet("/api/articles", { params });
        setArticles(refreshed.content);
        setTotalPages(refreshed.totalPages);
        setPage(refreshed.number);

    } catch (err) {
        console.error(err);
        setError("Authorization required: Failed to delete the paper. Access denied or Article Not Found.");
    } finally {
        setLoading(false); // ✅ stop spinner
    }
};

    const formatDate = (dateString) => {
        if (!dateString) return "Date Unknown";
        return new Date(dateString).toLocaleDateString('en-US', { year: 'numeric', month: 'short', day: 'numeric' });
    };

    // --- Pagination Renderer ---
    const renderPagination = useMemo(() => {
        const items = [];
        const startPage = Math.max(0, page - 2);
        const endPage = Math.min(totalPages - 1, page + 2);

        if (totalPages <= 1) return null;

        for (let i = startPage; i <= endPage; i++) {
            items.push(
                <Pagination.Item key={i} active={i === page} onClick={() => setPage(i)}>
                    {i + 1}
                </Pagination.Item>
            );
        }

        return (
            <Pagination className="justify-content-center mt-4">
                <Pagination.First onClick={() => setPage(0)} disabled={page === 0} />
                <Pagination.Prev onClick={() => setPage(page - 1)} disabled={page === 0} />
                {items}
                <Pagination.Next onClick={() => setPage(page + 1)} disabled={page === totalPages - 1} />
                <Pagination.Last onClick={() => setPage(totalPages - 1)} disabled={page === totalPages - 1} />
            </Pagination>
        );
    }, [page, totalPages]);

    return (
        <Container className="my-5 py-5">
            <style>{`
                /* --- SCIENTIFIC PROJECT AESTHETICS --- */
                :root {
                    --accent-color: #007bff; 
                    --mid-gray: #e9ecef;
                    --dark-text: #212529;
                }
                .research-card-module {
                    transition: all 0.3s cubic-bezier(0.25, 0.46, 0.45, 0.94); 
                    border: 1px solid var(--mid-gray);
                    border-left: 5px solid var(--mid-gray);
                    border-radius: 0.5rem;
                    background-color: #fff;
                    box-shadow: 0 0.15rem 0.5rem rgba(0, 0, 0, 0.05);
                }
                .research-card-module:hover {
                    transform: translateY(-4px);
                    box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.1);
                    border-left: 5px solid var(--accent-color); 
                }
                .abstract-body-fixed {
                    min-height: 250px; 
                    display: flex;
                    flex-direction: column;
                    justify-content: space-between; 
                    padding: 1.5rem !important;
                }
                .abstract-body-fixed .card-title {
                    font-size: 1.25rem;
                    font-weight: 700;
                    color: var(--dark-text);
                    margin-bottom: 0.75rem;
                    line-height: 1.3;
                }
                .abstract-body-fixed .card-text {
                    flex-grow: 1; 
                    color: #6c757d;
                    font-size: 0.95rem;
                    line-height: 1.4;
                    overflow: hidden; 
                    text-overflow: ellipsis; 
                    max-height: 75px; 
                    margin-bottom: 1.25rem;
                }
                .research-metadata {
                    font-size: 0.8rem;
                    color: #495057;
                    padding-bottom: 0.75rem;
                    border-bottom: 1px dashed var(--mid-gray);
                    margin-bottom: 1rem;
                }
                .research-metadata i {
                    margin-right: 0.4rem;
                    color: var(--accent-color);
                }
                .action-button-group {
                    border-top: 1px solid var(--mid-gray);
                    padding-top: 1rem;
                    margin-top: auto;
                }
            `}</style>
            
            <header className="text-center mb-5">
                <h1 className="display-4 fw-bold text-dark mb-1">
                    <i className="fas fa-microscope me-3 text-primary"></i>Research Publications Repository
                </h1>
                <p className="lead text-secondary fst-italic mb-4">
                    A repository of findings and methodological papers generated by the Familiarum community.
                </p>
                {isAdmin && (
                    <div className="d-flex justify-content-center my-4">
                        <Link to="/articles/create" className="btn btn-primary btn-lg rounded-pill px-5 py-2 fw-semibold shadow-lg">
                            <i className="fas fa-plus-circle me-2"></i>Submit New Paper
                        </Link>
                    </div>
                )}
            </header>

            {loading && (
                <div className="text-center my-5">
                    <Spinner animation="border" variant="primary" />
                    <p className="mt-3 text-muted">Awaiting data stream from repository...</p>
                </div>
            )}

            {error && (
                <Alert variant="danger" className="text-center rounded-3 shadow-sm">
                    {error}
                </Alert>
            )}

            {!loading && !error && (
                <>
                    {/* --- Filter and Sort Toolbar --- */}
                    <Row className="mb-4 d-flex align-items-center border-bottom pb-3">
                        <Col md={4}>
                            {/* Display current page status */}
                            <h5 className="fw-bold text-dark mb-0">Page {page + 1} of {totalPages}</h5>
                        </Col>
                        <Col md={8} className="d-flex justify-content-end align-items-center">
                            
                            {/* Sort Type Dropdown */}
                            <label htmlFor="sort-by-select" className="text-muted small me-2 d-none d-md-block">Sort By:</label>
                            <select 
                                id="sort-by-select"
                                className="form-select w-auto"
                                value={queryParams.sortBy}
                                onChange={(e) => handleSortChange(e.target.value)} 
                            >
                                <option value="createdAt">Publication Date</option>
                                <option value="title">Title (A-Z)</option>
                                <option value="views">Popularity (Views)</option>
                            </select>

                            {/* Sort Order Button */}
                            <Button 
                                variant="outline-secondary" 
                                className="ms-3"
                                onClick={toggleSortOrder}
                                title={`Change sort order to ${queryParams.sortOrder === 'desc' ? 'Ascending' : 'Descending'}`}
                            >
                                <i className={`fas fa-sort-amount-${queryParams.sortOrder === 'desc' ? 'down' : 'up'}`}></i>
                            </Button>
                        </Col>
                    </Row>
                    {/* ------------------------------- */}
                </>
            )}
            
            {articles.length === 0 && !loading && !error && (
                <Alert variant="info" className="text-center rounded-3 shadow-sm">
                    No published papers found on this page or matching criteria.
                </Alert>
            )}

            <Row xs={1} md={2} lg={3} className="g-4 justify-content-center"> 
                {/* Render the articles from the current page */}
                {articles.map((article) => (
                    <Col key={article._id} className="d-flex">
                        <Card className="h-100 w-100 research-card-module">
                            <Card.Body className="abstract-body-fixed">
                                
                                {/* Metadata - Enhanced */}
                                <div className="research-metadata">
                                    <span className="me-3">
                                        <i className="fas fa-calendar-alt"></i> **{formatDate(article.createdAt)}** </span>
                                    <span className="me-3">
                                        {/* CRITICAL: Ensure you use authorName, which your DTO exposes */}
                                        <i className="fas fa-user-tag"></i> {article.author || "Unknown Author"} 
                                    </span>
                                    <span>
                                        <i className="fas fa-chart-bar"></i> {article.views || 0} Views
                                    </span>
                                </div>
                                
                                {/* Title - Enhanced with icon */}
                                <Card.Title>
                                    <i className="fas fa-book-reader me-2 text-secondary"></i>
                                    {article.title}
                                </Card.Title>
                                
                                {/* Abstract Preview */}
                                <Card.Text>
                                    {article.description && article.description.length > 180
                                        ? `${article.description.substring(0, 180)}...`
                                        : article.description}
                                </Card.Text>

                                <div className="action-button-group d-flex justify-content-between align-items-center">
                                    {/* Primary Action: Read Full Paper */}
                                    <Link to={`/articles/show/${article._id}`} className="btn btn-primary btn-sm rounded-pill px-3 fw-semibold">
                                        <i className="fas fa-book-open me-1"></i> Read Full Paper
                                    </Link>

                                    {/* Admin Actions */}
                                    {isAdmin && (
                                        <div className="btn-group">
                                            <Button
                                                variant="secondary"
                                                size="sm"
                                                className="rounded-pill"
                                                data-bs-toggle="dropdown" 
                                                aria-expanded="false"
                                            >
                                                <i className="fas fa-tools"></i> Admin Tools
                                            </Button>
                                            <ul className="dropdown-menu dropdown-menu-end shadow">
                                                <li>
                                                    <Link to={`/articles/edit/${article._id}`} className="dropdown-item">
                                                        <i className="fas fa-edit me-2 text-warning"></i> Edit Paper
                                                    </Link>
                                                </li>
                                                <li>
                                                    <Button
                                                        variant="link"
                                                        className="dropdown-item text-danger"
                                                        onClick={() => handleDelete(article._id)}
                                                    >
                                                        <i className="fas fa-trash-alt me-2"></i> Delete Paper
                                                    </Button>
                                                </li>
                                            </ul>
                                        </div>
                                    )}
                                </div>
                            </Card.Body>
                        </Card>
                    </Col>
                ))}
            </Row>
            
            {/* --- Pagination Controls --- */}
            {renderPagination}
            
        </Container>
    );
};

export default ArticleIndex;
