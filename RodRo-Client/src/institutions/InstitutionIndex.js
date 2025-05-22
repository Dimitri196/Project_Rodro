import React, { useEffect, useState } from "react";
import { apiGet, apiDelete } from "../utils/api";
import { Container, Alert } from "react-bootstrap";
import InstitutionTable from "./InstitutionTable";

const InstitutionIndex = () => {
    const [institutions, setInstitutions] = useState([]);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);

    const deleteInstitution = async (id) => {
        try {
            await apiDelete("/api/institutions/" + id);
            setInstitutions(institutions.filter((item) => item._id !== id));
        } catch (error) {
            console.error(error.message);
            setError(error.message);
        }
    };

    useEffect(() => {
        setLoading(true);
        apiGet("/api/institutions")
            .then((data) => setInstitutions(data))
            .catch((error) => setError(error.message))
            .finally(() => setLoading(false));
    }, []);

    if (loading) return <div>Loading institution records...</div>;
    if (error) return <Alert variant="danger">{error}</Alert>;

    return (
        <Container className="mt-5">
            <h1>Institution records</h1>
            {error && <Alert variant="danger">{error}</Alert>}

            <InstitutionTable
                deleteInstitution={deleteInstitution}
                items={institutions}
                label="Number of institutions:"
            />
        </Container>
    );
};

export default InstitutionIndex;
