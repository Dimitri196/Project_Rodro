import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Form, Button, Alert, Spinner, Container, Card } from "react-bootstrap";
import { apiGet, apiPost, apiPut } from "../utils/api";

const ArticleForm = () => {
    const navigate = useNavigate();
    const { id } = useParams();
    const isEditing = !!id;

    const [article, setArticle] = useState({
        title: "",
        description: "",
        content: "",
        categories: [] // <-- NEW: Initialize categories as an array
    });

    // --- State for the categories input (text field) ---
    const [categoriesInput, setCategoriesInput] = useState(""); 
    // ---------------------------------------------------

    const [loading, setLoading] = useState(isEditing);
    const [saving, setSaving] = useState(false);
    const [error, setError] = useState(null);
    const [successMessage, setSuccessMessage] = useState(null);

    // --- Fetch Article Data for Editing ---
    useEffect(() => {
        if (isEditing) {
            apiGet(`/api/articles/${id}`)
                .then(data => {
                    // When editing, populate all fields including the categories array
                    setArticle({
                        title: data.title || "",
                        description: data.description || "",
                        content: data.content || "",
                        categories: data.categories || []
                    });
                    // Set the text input field with comma-separated categories for the user
                    setCategoriesInput((data.categories || []).join(', '));
                })
                .catch(err => {
                    console.error(err);
                    setError("Failed to load the article for editing. It may not exist or access is denied.");
                })
                .finally(() => setLoading(false));
        }
    }, [id, isEditing]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        // Handle all regular fields (title, description, content)
        setArticle(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const handleCategoriesChange = (e) => {
        // Handle the raw text input for categories
        setCategoriesInput(e.target.value);
    };


    const handleSubmit = async (e) => {
        e.preventDefault();
        setSaving(true);
        setError(null);
        setSuccessMessage(null);

        // 1. Convert the comma-separated string back into a clean array of strings
        const categoriesArray = categoriesInput
            .split(',') // Split by comma
            .map(tag => tag.trim()) // Trim whitespace from each tag
            .filter(tag => tag.length > 0); // Remove empty strings

        // 2. Prepare the final DTO object to send to the backend
        const articleWriteDTO = {
            title: article.title,
            description: article.description,
            content: article.content,
            categories: categoriesArray // <-- NEW: Include the processed array
        };
        
        try {
            const apiCall = isEditing 
                ? apiPut(`/api/articles/${id}`, articleWriteDTO)
                : apiPost("/api/articles", articleWriteDTO);

            await apiCall;
            
            setSuccessMessage(`Article successfully ${isEditing ? "updated" : "created"}.`);
            
            // Redirect to the article index after a brief delay
            setTimeout(() => navigate("/articles"), 1000); 

        } catch (err) {
            console.error(err);
            // Enhanced Error Handling for backend validation/security errors
            let errorMessage = "An unknown error occurred while saving the article.";

            if (err.response && err.response.data) {
                const data = err.response.data;
                if (data.status === 400 && data.errors) {
                    // Handle validation errors (Spring @Valid returning a list of field errors)
                    errorMessage = "Validation Error: Please correct the following fields:\n" + 
                        data.errors.map(e => `- ${e.field}: ${e.defaultMessage}`).join('\n');
                } else if (data.message) {
                    // Handle custom exceptions (like AccessDeniedException, 403 or 404)
                    errorMessage = `Error: ${data.message}`;
                }
            } else if (err.message) {
                 errorMessage = `Network Error: ${err.message}`;
            }

            setError(errorMessage);
        } finally {
            setSaving(false);
        }
    };

    // --- Render Logic ---
    if (loading) {
        return (
            <Container className="mt-5 text-center">
                <Spinner animation="border" variant="primary" />
                <p className="mt-3 text-muted">Loading article data...</p>
            </Container>
        );
    }

    return (
        <Container className="my-5">
            <h2 className="mb-4 fw-bold text-dark">
                {isEditing ? "Edit Research Paper" : "Submit New Research Paper"}
            </h2>
            <Card className="p-4 shadow-sm">
                <Card.Body>
                    {/* Error Display */}
                    {error && <Alert variant="danger" style={{ whiteSpace: "pre-wrap" }}>{error}</Alert>}
                    {/* Success Display */}
                    {successMessage && <Alert variant="success">{successMessage}</Alert>}

                    <Form onSubmit={handleSubmit}>
                        
                        {/* Title */}
                        <Form.Group className="mb-3" controlId="title">
                            <Form.Label className="fw-semibold">Title (Max 100 chars)</Form.Label>
                            <Form.Control
                                type="text"
                                name="title"
                                value={article.title}
                                onChange={handleChange}
                                maxLength={100}
                                required
                            />
                        </Form.Group>

                        {/* Description / Abstract */}
                        <Form.Group className="mb-3" controlId="description">
                            <Form.Label className="fw-semibold">Description / Abstract (Max 255 chars)</Form.Label>
                            <Form.Control
                                as="textarea"
                                rows={3}
                                name="description"
                                value={article.description}
                                onChange={handleChange}
                                maxLength={255}
                                required
                            />
                        </Form.Group>

                        {/* Categories (NEW FIELD) */}
                        <Form.Group className="mb-3" controlId="categories">
                            <Form.Label className="fw-semibold">Categories (Comma-separated)</Form.Label>
                            <Form.Control
                                type="text"
                                name="categories"
                                placeholder="e.g., Infantry, Interwar, Doctrine, Logistics"
                                value={categoriesInput}
                                onChange={handleCategoriesChange} // Use separate handler for string input
                            />
                            <Form.Text className="text-muted">
                                Enter tags to categorize the paper, separated by commas.
                            </Form.Text>
                        </Form.Group>


                        {/* Content */}
                        <Form.Group className="mb-4" controlId="content">
                            <Form.Label className="fw-semibold">Full Research Content</Form.Label>
                            <Form.Control
                                as="textarea"
                                rows={15} // Increased rows for content
                                name="content"
                                value={article.content}
                                onChange={handleChange}
                                required
                            />
                        </Form.Group>

                        <Button variant="primary" type="submit" disabled={saving} className="px-5 fw-semibold">
                            <i className={`fas ${saving ? 'fa-sync fa-spin' : 'fa-save'} me-2`}></i>
                            {saving ? (isEditing ? "Updating..." : "Creating...") : (isEditing ? "Update Paper" : "Submit Paper")}
                        </Button>
                    </Form>
                </Card.Body>
            </Card>
        </Container>
    );
};

export default ArticleForm;
