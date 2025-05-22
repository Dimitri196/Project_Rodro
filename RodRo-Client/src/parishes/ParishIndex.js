import React, { useEffect, useState } from "react";
import { apiDelete, apiGet } from "../utils/api";
import { Container, Row, Col, Alert } from 'react-bootstrap';  // Import React-Bootstrap components
import ParishTable from "./ParishTable";

const ParishIndex = () => {
    const [parishes, setParishes] = useState([]);
    const [error, setError] = useState(null);

    const [loading, setLoading] = useState(true);

    const deleteParish= async (id) => {
        try {
            await apiDelete("/api/parishes/" + id);
            setParishes(parishes.filter((item) => item._id !== id));
        } catch (error) {
            console.log(error.message);
            setError(error.message); // Set error state for displaying in alert
        }
    };

    useEffect(() => {
        setLoading(true);  // Set loading to true before starting the API call

        apiGet("/api/parishes")
            .then((data) => {
                setParishes(data);  // Set persons when the data is fetched
            })
            .catch((error) => {
                setError(error.message);  // Handle error if the API call fails
            })
            .finally(() => {
                setLoading(false);  // Set loading to false after the API call completes (success or error)
            });
    }, []);
    
      if (loading) return <div>Loading parish records...</div>;
      if (error) return <Alert variant="danger">{error}</Alert>;

    return (
        <Container className="mt-5">
            <h1>Parish records</h1>

            {/* Displaying error alert if there was an error */}
            {error && <Alert variant="danger">{error}</Alert>}

            {/* Person Table Component */}
            
                    <ParishTable
                        deleteParish={deleteParish}
                        items={parishes}
                        label="Number of parishes:"
                    />
      
        </Container>
    );
};

export default ParishIndex;
