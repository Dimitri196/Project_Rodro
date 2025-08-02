import React, { useEffect, useState } from "react";
import { Container, Alert, Spinner } from "react-bootstrap"; // Added Spinner for consistency
import { apiGet, apiDelete } from "../utils/api";
import SourceTable from "./SourceTable";

const SourceIndex = () => {
    // We will no longer store the full list of sources here,
    // as SourceTable now handles its own pagination and fetching.
    // However, we still need a way to trigger a refresh after deletion.
    const [refreshTrigger, setRefreshTrigger] = useState(0); // State to force re-fetch in SourceTable
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(false); // SourceTable handles its own loading

    const deleteSource = async (id) => {
        setLoading(true); // Show loading during deletion
        setError(null);
        try {
            await apiDelete(`/api/sources/${id}`);
            // After successful deletion, increment refreshTrigger to make SourceTable re-fetch
            setRefreshTrigger(prev => prev + 1);
        } catch (err) {
            console.error("Error deleting source:", err);
            setError(err.message || "Failed to delete source.");
        } finally {
            setLoading(false); // Hide loading after deletion attempt
        }
    };

    // No direct apiGet("/api/sources") here anymore, as SourceTable will manage its own data fetching.
    // The useEffect below is removed.

    // If you need a loading indicator for the *initial* load of SourceIndex itself (before SourceTable renders)
    // or for actions like deletion, keep a loading state.
    // For now, the main loading is handled by SourceTable.

    return (
        <Container className="my-5">
            {/* Font Awesome for icons (ensure it's linked in index.html) */}
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" xintegrity="sha512-1ycn6IcaQQ40JuKmWswL3QbpgExX+Q6bLSYg5rXW4remp4o/Y6fFZj/gX2Q2K2V2F5R5/5Q5w5w5w5w5w==" crossOrigin="anonymous" referrerPolicy="no-referrer" />

            <h1 className="mb-4 text-center display-4 fw-bold text-primary">
                <i className="fas fa-book me-3"></i>Source Records
            </h1>

            {loading && ( // Show loading spinner if delete operation is in progress
                <div className="text-center my-3">
                    <Spinner animation="border" role="status">
                        <span className="visually-hidden">Processing...</span>
                    </Spinner>
                    <p className="mt-2 text-muted">Processing request...</p>
                </div>
            )}

            {error && (
                <Alert variant="danger" className="text-center shadow-sm rounded-3">
                    <i className="fas fa-exclamation-triangle me-2"></i>{error}
                </Alert>
            )}

            {/* SourceTable now handles its own internal data fetching based on its state.
                We pass refreshTrigger to force it to re-fetch when a source is deleted. */}
            <SourceTable
                label="Total Sources"
                deleteSource={deleteSource}
                refreshTrigger={refreshTrigger} // Pass this to SourceTable to trigger re-fetch
            />
        </Container>
    );
};

export default SourceIndex;
