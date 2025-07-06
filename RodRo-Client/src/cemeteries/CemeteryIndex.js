import React, { useEffect, useState } from "react";
import { apiDelete, apiGet } from "../utils/api";
import { Container, Alert } from "react-bootstrap";
import CemeteryTable from "./CemeteryTable";

const CemeteryIndex = () => {
    const [cemeteries, setCemeteries] = useState([]); // State to hold cemetery records
    const [error, setError] = useState(null); // State to handle errors

    // Function to delete a cemetery record
    const deleteCemetery = async (id) => {
        try {
            await apiDelete("/api/cemeteries/" + id); // Call API to delete the cemetery
            // Remove deleted cemetery from the state
            setCemeteries(cemeteries.filter((item) => item.id !== id)); // Use `id`
        } catch (error) {
            console.log(error.message);
            setError(error.message); // Set error state for displaying in alert
        }
    };

    // Fetch cemeteries from the API when the component is mounted
    useEffect(() => {
        apiGet("/api/cemeteries")
            .then((data) => setCemeteries(data))
            .catch((error) => setError("Failed to load cemeteries."));
    }, []);

    return (
        <Container>
            <h1>Cemetery Records</h1>
            {error && <Alert variant="danger">{error}</Alert>}
            <CemeteryTable
                label="Number of Cemeteries:"
                items={cemeteries}
                deleteCemetery={deleteCemetery}
            />
        </Container>
    );
};

export default CemeteryIndex;
