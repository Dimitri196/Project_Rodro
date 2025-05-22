import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { apiGet } from "../utils/api";
import { Button, Container, Alert } from "react-bootstrap";

const SourceDetail = () => {
    const { id } = useParams();
    const navigate = useNavigate();
    const [source, setSource] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        apiGet(`/api/sources/${id}`)
            .then(setSource)
            .catch((err) => setError(err.message))
            .finally(() => setLoading(false));
    }, [id]);

    if (loading) return <p>Loading...</p>;
    if (error) return <Alert variant="danger">{error}</Alert>;

    return (
        <Container className="my-4">
            <h2>Source Details</h2>
            <p><strong>Title:</strong> {source.sourceTitle}</p>
            <p><strong>Reference:</strong> {source.sourceReference}</p>
            <p><strong>Type:</strong> {source.sourceType}</p>
            <p><strong>Description:</strong> {source.sourceDescription}</p>
            <p>
                <strong>URL:</strong>{" "}
                <a href={source.sourceUrl} target="_blank" rel="noopener noreferrer">
                    {source.sourceUrl}
                </a>
            </p>
            <Button variant="secondary" onClick={() => navigate("/sources")}>
                Back to List
            </Button>
        </Container>
    );
};

export default SourceDetail;
