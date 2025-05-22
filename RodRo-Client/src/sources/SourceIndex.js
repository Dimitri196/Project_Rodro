import React, { useEffect, useState } from "react";
import { Container, Alert } from "react-bootstrap";
import { apiGet, apiDelete } from "../utils/api";
import SourceTable from "./SourceTable";

const SourceIndex = () => {
    const [sources, setSources] = useState([]);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);

    const deleteSource = async (id) => {
        try {
            await apiDelete(`/api/sources/${id}`);
            setSources((prev) => prev.filter((src) => src.id !== id));
        } catch (err) {
            setError(err.message);
        }
    };

    useEffect(() => {
        apiGet("/api/sources")
            .then(setSources)
            .catch((err) => setError(err.message))
            .finally(() => setLoading(false));
    }, []);

    if (loading) return <div>Loading source records...</div>;
    if (error) return <Alert variant="danger">{error}</Alert>;

    return (
        <Container className="mt-5">
            <h1>Source Records</h1>

            {/* Displaying error alert if there was an error */}
            {error && <Alert variant="danger">{error}</Alert>}
            <SourceTable
                label="Number of sources:"
                items={sources}
                deleteSource={deleteSource}
            />
        </Container>
    );
};

export default SourceIndex;
