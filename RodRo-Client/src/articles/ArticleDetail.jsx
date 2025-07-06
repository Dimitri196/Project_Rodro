import React, { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import { apiDelete, apiGet } from "../utils/api";
import { Container, Spinner, Alert, Button } from "react-bootstrap";
import { useSession } from "../contexts/session";

const ArticleDetail = () => {
  const { id } = useParams();
  const { session } = useSession();
  const isAdmin = session.data?.isAdmin === true;

  const [article, setArticle] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    apiGet(`/api/articles/${id}`)
      .then(setArticle)
      .catch(() => setError("Failed to load article."))
      .finally(() => setLoading(false));
  }, [id]);

  if (loading) {
    return (
      <Container className="my-5 text-center">
        <Spinner animation="border" />
        <p className="mt-3">Loading article...</p>
      </Container>
    );
  }

  if (error) {
    return (
      <Container className="my-5">
        <Alert variant="danger">{error}</Alert>
      </Container>
    );
  }

  if (!article) {
    return (
      <Container className="my-5">
        <Alert variant="warning">Article not found.</Alert>
      </Container>
    );
  }

  return (
    <Container className="my-5">
      <h1 className="fw-bold mb-3">{article.title}</h1>
      <p className="text-muted">{article.description}</p>
      <hr />
      <div style={{ whiteSpace: "pre-wrap" }}>{article.content}</div>

      {isAdmin && (
        <div className="mt-4">
          <Link to={`/articles/edit/${article._id}`} className="btn btn-warning me-2">
            Edit
          </Link>
          <Button variant="danger" disabled>Delete</Button>
          {/* Add delete logic later */}
        </div>
      )}
    </Container>
  );
};

export default ArticleDetail;
