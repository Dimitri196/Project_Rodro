import React, { useEffect, useState } from "react";
import { apiGet, apiDelete } from "../utils/api";
import { Container, Alert } from "react-bootstrap";
import MilitaryOrganizationTable from "./MilitaryOrganizationTable";

const MilitaryOrganizationIndex = () => {
    const [organizations, setOrganizations] = useState([]);
    const [error, setError] = useState(null);

    const fetchOrganizations = async () => {
        try {
            const data = await apiGet("/api/militaryOrganizations");
            setOrganizations(data);
        } catch (err) {
            console.error("Error fetching military organizations:", err);
            setError("Failed to load military organizations. Please try again later.");
        }
    };

    const deleteOrganization = async (id) => {
        try {
            await apiDelete(`/api/militaryOrganizations/${id}`);
            setOrganizations(prev => prev.filter(item => item.id !== id));
        } catch (err) {
            console.error("Error deleting military organization:", err);
            setError("Failed to delete military organization.");
        }
    };

    useEffect(() => {
        fetchOrganizations();
    }, []);

    return (
        <Container className="mt-5">
            <h1>Military Organization Records</h1>

            {error && <Alert variant="danger">{error}</Alert>}

            <MilitaryOrganizationTable
                items={organizations}
                deleteOrganization={deleteOrganization}
            />
        </Container>
    );
};

export default MilitaryOrganizationIndex;
