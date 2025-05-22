import React, { useEffect, useState } from "react";
import { apiDelete, apiGet } from "../utils/api";
import { Container, Row, Col, Alert } from 'react-bootstrap';  // Import React-Bootstrap components
import PersonTable from "./PersonTable";



const PersonIndex = () => {
    const [persons, setPersons] = useState([]);
    const [error, setError] = useState(null);

    const [loading, setLoading] = useState(true);

    const deletePerson = async (id) => {
        try {
            await apiDelete("/api/persons/" + id);
            setPersons(persons.filter((item) => item._id !== id));
        } catch (error) {
            console.log(error.message);
            setError(error.message); // Set error state for displaying in alert
        }
    };

    useEffect(() => {
        setLoading(true);  // Set loading to true before starting the API call

        apiGet("/api/persons")
            .then((data) => {
                setPersons(data);  // Set persons when the data is fetched
            })
            .catch((error) => {
                setError(error.message);  // Handle error if the API call fails
            })
            .finally(() => {
                setLoading(false);  // Set loading to false after the API call completes (success or error)
            });
    }, []);
    
      if (loading) return <div>Loading person records...</div>;
      if (error) return <Alert variant="danger">{error}</Alert>;

    return (
        <Container className="mt-5">
            <h1>Person records</h1>

            {/* Displaying error alert if there was an error */}
            {error && <Alert variant="danger">{error}</Alert>}

            {/* Person Table Component */}
            
                    <PersonTable
                        deletePerson={deletePerson}
                        items={persons}
                        label="Number of persons:"
                    />
      
        </Container>
    );
};

export default PersonIndex;
