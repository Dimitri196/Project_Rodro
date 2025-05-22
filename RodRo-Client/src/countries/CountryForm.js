import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { apiGet, apiPost, apiPut } from "../utils/api";
import { Form, Button, Container, Row, Col, Alert } from "react-bootstrap";

import InputField from "../components/InputField";
import FlashMessage from "../components/FlashMessage";

const CountryForm = () => {
    const navigate = useNavigate();
    const [isLoading, setIsLoading] = useState(false);
    const { id } = useParams(); // Retrieve ID for edit mode
    const [country, setCountry] = useState({
        id: null, // Add id to state to store _id from backend
        countryNameInPolish: "",
        countryNameInEnglish: "",
        countryEstablishmentYear: "",
        countryCancellationYear: "",
    });
    const [sentState, setSent] = useState(false);
    const [successState, setSuccess] = useState(false);
    const [errorState, setError] = useState(null);

    // Fetch existing country data if editing
    useEffect(() => {
        if (id) {
            apiGet(`/api/countries/${id}`)
                .then((data) => {
                    setCountry({
                        id: data._id, // Store _id in state for reference
                        countryNameInPolish: data.countryNameInPolish || "",
                        countryNameInEnglish: data.countryNameInEnglish || "",
                        countryEstablishmentYear: data.countryEstablishmentYear || "",
                        countryCancellationYear: data.countryCancellationYear || "",
                    });
                })
                .catch((error) => setError(error.message));
        }
    }, [id]);

    // Auto-hide flash message
    useEffect(() => {
        if (sentState) {
            const timer = setTimeout(() => setSent(false), 3000);
            return () => clearTimeout(timer);
        }
    }, [sentState]);

     // Validation for Establishment Year
     const isValidYear = (year) => {
        return /^[0-9]{3,4}$/.test(year); // Check for 3 or 4 digit year
    };

    
// Handle form submission
const handleSubmit = (e) => {
    e.preventDefault();

    if (!isValidYear(country.countryEstablishmentYear)) {
        setError("Please provide a valid establishment year (3-4 digits, ex. year 999 or 1000).");
        return;
    }

    setIsLoading(true);  // Start loading indicator

    // Prepare data to send (without id)
    const countryToSend = {
        countryNameInPolish: country.countryNameInPolish,
        countryNameInEnglish: country.countryNameInEnglish,
        countryEstablishmentYear: country.countryEstablishmentYear,
        countryCancellationYear: country.countryCancellationYear,
    };

    const request = id
        ? apiPut(`/api/countries/${id}`, countryToSend)
        : apiPost("/api/countries", countryToSend);

    request
        .then(() => {
            setSent(true);
            setSuccess(true);
            setTimeout(() => navigate("/countries"), 1000); // Redirect to list
        })
        .catch((error) => {
            setError(error.message);
            setSuccess(false);
        })
        .finally(() => setIsLoading(false));  // Stop loading indicator
};
    const sent = sentState;

    return (
        <Container className="mt-5">
            <h1 className="mb-4">{id ? "Update" : "Create"} Country</h1>
            
            {errorState && <Alert variant="danger">{errorState}</Alert>}

            {sent && (
                <FlashMessage
                    theme={successState ? "success" : "danger"}
                    text={successState ? "Country saved successfully." : "Error saving country."}
                >
                    <Button variant="link" onClick={() => setSent(false)}>Close</Button>
                </FlashMessage>
            )}

            <Form onSubmit={handleSubmit}>
                <Row>
                    <Col md={6}>
                        <InputField
                            required={true}
                            type="text"
                            name="countryNameInPolish"
                            min="3"
                            label="Country Name (Polish)"
                            prompt="Input country name in Polish"
                            value={country.countryNameInPolish}
                            handleChange={(e) =>
                                setCountry({ ...country, countryNameInPolish: e.target.value })
                            }
                        />
                    </Col>
                    <Col md={6}>
                        <InputField
                            required={true}
                            type="text"
                            name="countryNameInEnglish"
                            min="3"
                            label="Country Name (English)"
                            prompt="Input country name in English"
                            value={country.countryNameInEnglish}
                            handleChange={(e) =>
                                setCountry({ ...country, countryNameInEnglish: e.target.value })
                            }
                        />
                    </Col>
                </Row>

                <Row>
                    <Col md={6}>
                        <InputField
                            required={false}
                            type="text"
                            name="countryEstablishmentYear"
                            label="Establishment Year"
                            prompt="Input establishment year (e.g., 1918)"
                            value={country.countryEstablishmentYear}
                            handleChange={(e) =>
                                setCountry({ ...country, countryEstablishmentYear: e.target.value })
                            }
                        />
                    </Col>
                    <Col md={6}>
                        <InputField
                            type="text"
                            name="countryCancellationDate"
                            label="Cancellation Year"
                            prompt="Input cancellation year (optional)"
                            value={country.countryCancellationYear}
                            handleChange={(e) =>
                                setCountry({ ...country, countryCancellationYear: e.target.value })
                            }
                        />
                    </Col>
                </Row>

                <Button type="submit" variant="primary" className="mt-3" disabled={isLoading}>
                    {isLoading ? 'Saving...' : 'Save'}
                </Button>
            </Form>
        </Container>
    );
};

export default CountryForm;