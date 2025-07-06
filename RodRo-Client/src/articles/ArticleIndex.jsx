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
    <Container className="my-5">
      <header className="text-center mb-5">
        <h1 className="fw-bold">Blog – Article List</h1>
        <p className="text-muted">Explore knowledge shared by the community.</p>
        {isAdmin && (
          <div className="d-flex justify-content-center my-3">
            <Link to="/articles/create" className="btn btn-success">
              Create New Article
            </Link>
          </div>
        )}
      </header>

      {loading && (
        <div className="text-center my-5">
          <Spinner animation="border" variant="primary" />
          <p className="mt-3">Loading articles...</p>
        </div>
      )}

      {error && (
        <Alert variant="danger" className="text-center">
          {error}
        </Alert>
      )}

      {!loading && !error && articles.length === 0 && (
        <div className="text-center text-muted">No articles found.</div>
      )}

      <Row xs={1} md={2} lg={3} className="g-4">
        {articles.map((article) => (
          <Col key={article._id}>
            <Card className="h-100 shadow-sm border-0">
              <Card.Body className="d-flex flex-column">
                <Card.Title>{article.title}</Card.Title>
                <Card.Text className="text-muted">
                  {article.description.length > 150
                    ? `${article.description.substring(0, 150)}...`
                    : article.description}
                </Card.Text>
                <div className="mt-auto">
                  <Link to={`/articles/show/${article._id}`} className="btn btn-outline-primary btn-sm me-2">
                    View
                  </Link>
                  {isAdmin && (
                    <>
                      <Link to={`/articles/edit/${article._id}`} className="btn btn-warning btn-sm me-2">
                        Edit
                      </Link>
                      <Button
                        variant="danger"
                        size="sm"
                        onClick={() => handleDelete(article._id)}
                      >
                        Delete
                      </Button>
                      {/* Replace with real delete handler later */}
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
