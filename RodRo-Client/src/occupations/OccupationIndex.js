import React, { useEffect, useState } from "react";
import { apiGet, apiDelete } from "../utils/api";
import { Container, Alert } from "react-bootstrap";
import OccupationTable from "./OccupationTable";

const OccupationIndex = () => {
    const [occupations, setOccupations] = useState([]);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);

    const deleteOccupation = async (id) => {
        try {
            await apiDelete("/api/occupations/" + id);
            setOccupations(occupations.filter((item) => item._id !== id));
        } catch (error) {
            console.error(error.message);
            setError(error.message);
        }
    };

    useEffect(() => {
        setLoading(true);
        apiGet("/api/occupations")
            .then((data) => setOccupations(data))
            .catch((error) => setError(error.message))
            .finally(() => setLoading(false));
    }, []);

    if (loading) return <div>Loading occupation records...</div>;
    if (error) return <Alert variant="danger">{error}</Alert>;

    return (
        <Container className="mt-5">
            <h1>Occupation records</h1>
            {error && <Alert variant="danger">{error}</Alert>}

            <OccupationTable
                deleteOccupation={deleteOccupation}
                items={occupations}
                label="Number of occupations:"
            />
        </Container>
    );
};

export default OccupationIndex;
