import React, { useEffect, useState } from "react";
import { apiDelete, apiGet } from "../utils/api";
import { Container, Alert } from 'react-bootstrap';
import LocationTable from "./LocationTable";

const LocationIndex = () => {
    const [locations, setLocations] = useState([]);
    const [error, setError] = useState(null);

    const fetchLocations = async () => {
        try {
            const data = await apiGet("/api/locations");
            setLocations(data);
        } catch (err) {
            console.error("Error fetching locations:", err);
            setError("Failed to load locations. Please try again later.");
        }
    };

    const deleteLocation = async (id) => {
        try {
            await apiDelete(`/api/locations/${id}`);
            setLocations((prev) => prev.filter((item) => item._id !== id));
        } catch (err) {
            console.error("Error deleting location:", err);
            setError("Failed to delete location.");
        }
    };

    useEffect(() => {
        fetchLocations();
    }, []);

    return (
        <Container className="mt-5">
            <h1>Location Records</h1>

            {error && <Alert variant="danger">{error}</Alert>}

            <LocationTable
                deleteLocation={deleteLocation}
                items={locations}
                label="Number of Localities:"
            />
        </Container>
    );
};

export default LocationIndex;
