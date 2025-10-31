import React, { useEffect, useState, useCallback } from "react";
import { useParams, Link, useNavigate } from "react-router-dom";
import { apiDelete, apiGet } from "../utils/api";
import { 
    Container, Spinner, Alert, Button, Row, Col, Card, Badge 
} from "react-bootstrap";
import { useSession } from "../contexts/session";
import "@fortawesome/fontawesome-free/css/all.min.css"; 

const ArticleDetail = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const { session } = useSession();
    const isAdmin = session.data?.isAdmin === true;

    const [article, setArticle] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");

    // --- Data Fetching and Formatting ---
    useEffect(() => {
        // Fetch article data (which also increments the view count on the backend)
        apiGet(`/api/articles/${id}`)
            .then(data => {
                setArticle({
                    ...data,
                    // CRITICAL: Ensure we rely on the authorName field from the DTO
                    authorName: data.author || "Community Contributor", 
                    categories: data.categories || ["Uncategorized"],
                    views: data.views || 0, // Ensure views is present
                    // Simulating a download link
                    pdfLink: `/api/articles/${id}/download` 
                });
            })
            .catch((err) => {
                console.error("Fetch error:", err);
                setError("Failed to load article. The record may have been deleted or the ID is invalid.");
            })
            .finally(() => setLoading(false));
    }, [id]);

    const handleDelete = useCallback(async () => {
        if (!window.confirm("CRITICAL WARNING: This will permanently delete the research paper and all associated data. Proceed?")) {
            return;
        }
        
        try {
            await apiDelete(`/api/articles/${id}`);
            alert("Research paper successfully deleted.");
            navigate("/articles"); // Redirect back to the index page
        } catch (err) {
            console.error("Delete error:", err);
            setError("Authorization Error: Failed to delete the paper. Access denied.");
        }
    }, [id, navigate]);
    
    // Helper function to format the date
    const formatDate = (dateString) => {
        if (!dateString) return "Date Unknown";
        return new Date(dateString).toLocaleDateString('en-US', { 
            year: 'numeric', month: 'long', day: 'numeric' 
        });
    };
    
    // --- Loading and Error States ---
    if (loading) {
        return (
            <Container className="my-5 text-center py-5">
                <Spinner animation="border" variant="primary" />
                <p className="mt-3 text-muted lead">Retrieving paper from the repository...</p>
            </Container>
        );
    }

    if (error) {
        return (
            <Container className="my-5 py-5">
                <Alert variant="danger">
                    <i className="fas fa-exclamation-triangle me-2"></i>{error}
                </Alert>
                <div className="text-center">
                    <Link to="/articles" className="btn btn-secondary mt-3">
                        <i className="fas fa-arrow-left me-2"></i>Back to Index
                    </Link>
                </div>
            </Container>
        );
    }

    if (!article) {
        return (
            <Container className="my-5 py-5">
                <Alert variant="warning">
                    <i className="fas fa-info-circle me-2"></i>Article not found.
                </Alert>
            </Container>
        );
    }

    // --- Main Component Render ---
    return (
        <Container className="my-5 py-3">
            <Row className="justify-content-center">
                <Col md={10} lg={9}>
                    
                    {/* Header and Title */}
                    <header className="mb-4">
                        <h1 className="display-6 fw-bold text-dark mb-3">
                            {article.title}
                        </h1>
                        <p className="lead text-secondary fst-italic">
                            <i className="fas fa-quote-left me-2"></i> {article.description}
                        </p>
                        <hr className="mt-4 mb-2"/>
                    </header>

                    {/* Metadata Card (Professional Look) */}
                    <Card className="bg-light shadow-sm mb-5 border-0 rounded-3">
                        <Card.Body className="p-4">
                            <Row className="small text-muted fw-semibold">
                                {/* Author */}
                                <Col md={3} className="mb-2 mb-md-0">
                                    <i className="fas fa-user-tag me-2 text-primary"></i>
                                    **Author:** {article.authorName}
                                </Col>
                                
                                {/* Publication Date */}
                                <Col md={3} className="mb-2 mb-md-0">
                                    <i className="fas fa-calendar-alt me-2 text-primary"></i>
                                    **Published:** {formatDate(article.createdAt)}
                                </Col>
                                
                                {/* Views Counter (INCLUDED) */}
                                <Col md={3} className="mb-2 mb-md-0">
                                    <i className="fas fa-eye me-2 text-primary"></i>
                                    **Views:** {article.views}
                                </Col>
                                
                                {/* PDF/Download Link */}
                                <Col md={3}>
                                    <a href={article.pdfLink} download className="text-decoration-none text-dark">
                                        <i className="fas fa-file-pdf me-2 text-danger"></i>
                                        **Download PDF**
                                    </a>
                                </Col>
                            </Row>
                        </Card.Body>
                    </Card>

                    {/* Main Content (Simulating long text output) */}
                    <section className="article-content mb-5">
                        <h3 className="fw-bold text-dark mb-4 border-bottom pb-2">Full Research Content</h3>
                        <div className="p-4 bg-white shadow-sm rounded-3 border">
                            {/* Use style to preserve line breaks and spacing from API data */}
                            <div style={{ whiteSpace: "pre-wrap", lineHeight: "1.7" }}>
                                {article.content}
                            </div>
                        </div>
                    </section>

                    {/* Categories and Tags */}
                    <div className="mb-5 border-top pt-4">
                        <span className="fw-bold me-2 text-dark">Tags:</span>
                        {article.categories.map((cat, index) => (
                            <Badge key={index} pill bg="secondary" className="me-2 fw-normal">
                                <i className="fas fa-tag me-1"></i> {cat}
                            </Badge>
                        ))}
                    </div>

                    {/* Action Bar (View, Edit, Delete) */}
                    <div className="d-flex justify-content-between border-top pt-4">
                        <Link to="/articles" className="btn btn-outline-secondary rounded-pill px-4">
                            <i className="fas fa-arrow-left me-2"></i> Back to Index
                        </Link>
                        
                        {isAdmin && (
                            <div className="btn-group">
                                <Link to={`/articles/edit/${article._id}`} className="btn btn-warning me-2 rounded-pill px-4 fw-semibold">
                                    <i className="fas fa-edit me-2"></i> Edit Paper
                                </Link>
                                <Button 
                                    variant="danger" 
                                    onClick={handleDelete}
                                    className="rounded-pill px-4 fw-semibold"
                                >
                                    <i className="fas fa-trash-alt me-2"></i> Delete Permanently
                                </Button>
                            </div>
                        )}
                    </div>

                </Col>
            </Row>
        </Container>
    );
};

export default ArticleDetail;
