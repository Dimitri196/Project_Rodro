import React, { useEffect, useState } from "react";
import { apiDelete, apiGet } from "../utils/api";
import { Container, Row, Col, Alert, Spinner } from 'react-bootstrap';  // Import React-Bootstrap components
import CountryTable from "./CountryTable";

const CountryIndex = () => {
    const [countries, setCountries] = useState([]);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);

    const deleteCountry = async (id) => {
        const confirmDelete = window.confirm("Are you sure you want to delete this country?");
        if (!confirmDelete) return;
    
        try {
            await apiDelete(`/api/countries/${id}`);
            setCountries(countries.filter((item) => item.id !== id));  // Adjusting filtering logic to use 'id'
        } catch (error) {
            console.log(error.message);
            setError(`Failed to delete country: ${error.message || "Unknown error"}`);
        }
    };

    useEffect(() => {
        setLoading(true);  // Set loading to true before starting the API call

        apiGet("/api/countries")
            .then((data) => {
                setCountries(data);  // Set countries when the data is fetched
            })
            .catch((error) => {
                console.log(error);
                setError(`Failed to load countries: ${error.message || "Unknown error"}`);
            })
            .finally(() => {
                setLoading(false);  // Set loading to false after the API call completes (success or error)
            });
    }, []);

    if (loading) return (
        <div className="text-center">
            <Spinner animation="border" variant="primary" />
            <p>Loading country records...</p>
        </div>
    );
    if (error) return <Alert variant="danger">{error}</Alert>;

    return (
        <Container className="mt-5">
            <h1>Country records</h1>

            {/* Displaying error alert if there was an error */}
            {error && <Alert variant="danger">{error}</Alert>}

            {/* Country Table Component */}
            <CountryTable
                deleteCountry={deleteCountry}
                items={countries}
                label="Number of countries:"
            />
        </Container>
    );
};

export default CountryIndex;
