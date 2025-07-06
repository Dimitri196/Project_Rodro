import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Form, Button, Alert, Spinner, Container } from "react-bootstrap";
import { apiGet, apiPost, apiPut } from "../utils/api";

const ArticleForm = () => {
  const navigate = useNavigate();
  const { id } = useParams(); // Used to determine if editing
  const isEditing = !!id;

  const [article, setArticle] = useState({
    title: "",
    description: "",
    content: ""
  });

  const [loading, setLoading] = useState(isEditing);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState(null);
  const [successMessage, setSuccessMessage] = useState(null);

  useEffect(() => {
    if (isEditing) {
      apiGet(`/api/articles/${id}`)
        .then(data => {
          setArticle({
            title: data.title || "",
            description: data.description || "",
            content: data.content || ""
          });
        })
        .catch(err => {
          console.error(err);
          setError("Failed to load the article.");
        })
        .finally(() => setLoading(false));
    }
  }, [id, isEditing]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setArticle(prev => ({
      ...prev,
      [name]: value
    }));
  };

const handleSubmit = async (e) => {
  e.preventDefault();
  setSaving(true);
  setError(null);
  setSuccessMessage(null);

  try {
    if (isEditing) {
      await apiPut(`/api/articles/${id}`, article);
      setSuccessMessage("Article updated successfully.");
      navigate("/articles");  // <-- add this here
    } else {
      await apiPost("/api/articles", article);
      setSuccessMessage("Article created successfully.");
      navigate("/articles");
    }
  } catch (err) {
    console.error(err);
    setError("Failed to save the article.");
  } finally {
    setSaving(false);
  }
};


  

  if (loading) {
    return (
      <Container className="mt-5 text-center">
        <Spinner animation="border" />
      </Container>
    );
  }

  return (
    <Container className="mt-4">
      <h2>{isEditing ? "Edit Article" : "Create Article"}</h2>

      {error && <Alert variant="danger">{error}</Alert>}
      {successMessage && <Alert variant="success">{successMessage}</Alert>}

      <Form onSubmit={handleSubmit}>
        <Form.Group className="mb-3" controlId="title">
          <Form.Label>Title</Form.Label>
          <Form.Control
            type="text"
            name="title"
            value={article.title}
            onChange={handleChange}
            maxLength={100}
            required
          />
        </Form.Group>

        <Form.Group className="mb-3" controlId="description">
          <Form.Label>Description</Form.Label>
          <Form.Control
            type="text"
            name="description"
            value={article.description}
            onChange={handleChange}
            maxLength={255}
            required
          />
        </Form.Group>

        <Form.Group className="mb-3" controlId="content">
          <Form.Label>Content</Form.Label>
          <Form.Control
            as="textarea"
            rows={6}
            name="content"
            value={article.content}
            onChange={handleChange}
            required
          />
        </Form.Group>

        <Button variant="primary" type="submit" disabled={saving}>
          {saving ? "Saving..." : isEditing ? "Update Article" : "Create Article"}
        </Button>
      </Form>
    </Container>
  );
};

export default ArticleForm;
