import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { apiDelete, apiGet } from "../utils/api";
import { Spinner, Button, Alert, Card, Container, Row, Col } from "react-bootstrap";
import { useSession } from "../contexts/session";

const ArticleIndex = () => {
  const { session } = useSession();
  const isAdmin = session.data?.isAdmin === true;

  const [articles, setArticles] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    apiGet("/api/articles")
      .then((data) => {
        setArticles(data);
        setError("");
      })
      .catch((err) => {
        setError("Failed to load articles. Please try again.");
        console.error(err);
      })
      .finally(() => setLoading(false));
  }, []);

  const handleDelete = async (articleId) => {
    if (!window.confirm("Are you sure you want to delete this article?")) return;

    try {
      await apiDelete(`/api/articles/${articleId}`);
      setArticles((prev) => prev.filter((article) => article._id !== articleId));
    } catch (err) {
      console.error(err);
      setError("Failed to delete the article. Please try again.");
    }
  };

  return (
    <Container className="my-5 py-4 bg-light rounded shadow-lg"> {/* Consistent container styling */}
      {/* Font Awesome for icons (if not already globally included in App.js) */}
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" xintegrity="sha512-1ycn6IcaQQ40JuKmWswL3QbpgExX+Q6bLSYg5rXW4remp4o/Y6fFZj/gX2Q2K2V2F5R5/5Q5w5w5w5w5w==" crossOrigin="anonymous" referrerPolicy="no-referrer" />

      {/* Custom CSS for card styling */}
      <style>
        {`
        .article-card-hover-effect {
          transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out, border-color 0.3s ease-in-out;
          border: 1px solid #e0e0e0; /* Subtle border */
          background-color: #ffffff; /* Ensure white background */
        }

        .article-card-hover-effect:hover {
          transform: translateY(-8px); /* Lifts the card slightly more */
          box-shadow: 0 1rem 2rem rgba(0,0,0,.15) !important; /* More pronounced shadow */
          border-color: #0d6efd; /* Highlight border on hover */
        }

        /* Force consistent height for card body and distribute content */
        .article-card-body-fixed-height {
          min-height: 220px; /* Adjust as needed based on your content's longest description */
          display: flex;
          flex-direction: column;
          justify-content: space-between; /* Distribute content and push buttons to bottom */
        }

        .article-card-body-fixed-height .card-text {
          flex-grow: 1; /* Allow text to grow and push buttons down */
          overflow: hidden; /* Hide overflow if text is too long */
          text-overflow: ellipsis; /* Add ellipsis for overflowed text */
        }
        `}
      </style>

      <header className="text-center mb-5">
        <h1 className="display-4 fw-bold text-primary mb-3">
          <i className="fas fa-blog me-3"></i>Blog – Article List
        </h1>
        <p className="lead text-muted fst-italic mb-4">Explore knowledge shared by the community.</p>
        {isAdmin && (
          <div className="d-flex justify-content-center my-3">
            <Link to="/articles/create" className="btn btn-primary btn-lg rounded-pill px-4 py-2 shadow-sm">
              <i className="fas fa-plus me-2"></i>Create New Article
            </Link>
          </div>
        )}
      </header>

      {loading && (
        <div className="text-center my-5">
          <Spinner animation="border" variant="primary" />
          <p className="mt-3 text-muted">Loading articles...</p>
        </div>
      )}

      {error && (
        <Alert variant="danger" className="text-center rounded-3 shadow-sm">
          {error}
        </Alert>
      )}

      {!loading && !error && articles.length === 0 && (
        <Alert variant="info" className="text-center rounded-3 shadow-sm">
          No articles found. Be the first to create one!
        </Alert>
      )}

      <Row xs={1} md={2} lg={3} className="g-4 justify-content-center"> {/* Responsive grid with gap */}
        {articles.map((article) => (
          <Col key={article._id} className="d-flex"> {/* Use d-flex to make cards equal height */}
            <Card className="h-100 w-100 article-card-hover-effect rounded-4"> {/* Apply custom class and ensure full width */}
              <Card.Body className="article-card-body-fixed-height p-4"> {/* Apply custom class for fixed height */}
                <Card.Title className="fw-bold text-secondary mb-2">{article.title}</Card.Title>
                <Card.Text className="text-muted mb-3">
                  {article.description.length > 150
                    ? `${article.description.substring(0, 150)}...`
                    : article.description}
                </Card.Text>
                <div className="mt-auto d-flex justify-content-center gap-2"> {/* Use flex for button group */}
                  <Link to={`/articles/show/${article._id}`} className="btn btn-outline-primary btn-sm rounded-pill px-3 shadow-sm">
                    <i className="fas fa-eye me-1"></i> View
                  </Link>
                  {isAdmin && (
                    <>
                      <Link to={`/articles/edit/${article._id}`} className="btn btn-warning btn-sm rounded-pill px-3 shadow-sm">
                        <i className="fas fa-edit me-1"></i> Edit
                      </Link>
                      <Button
                        variant="danger"
                        size="sm"
                        onClick={() => handleDelete(article._id)}
                        className="rounded-pill px-3 shadow-sm"
                      >
                        <i className="fas fa-trash-alt me-1"></i> Delete
                      </Button>
                    </>
                  )}
                </div>
              </Card.Body>
            </Card>
          </Col>
        ))}
      </Row>
    </Container>
  );
};

export default ArticleIndex;
