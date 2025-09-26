import React, { useEffect, useState } from "react";
import { useNavigate, useParams, Link } from "react-router-dom";
import { Form, Button, Container, Row, Col, Alert } from "react-bootstrap";
import { apiGet, apiPost, apiPut } from "../utils/api";

import InputSelect from "../components/InputSelect";
import InputField from "../components/InputField";
import FlashMessage from "../components/FlashMessage";

// New: Import the MilitaryRankLevel enum
import MilitaryRankLevel from "../constants/militaryRankLevel";

const MilitaryRankForm = () => {
    const navigate = useNavigate();
    const { id } = useParams();
    const [rank, setRank] = useState({
        rankName: "",
        rankLevel: MilitaryRankLevel.OFFICER.name, // UPDATED: Initialize with a default enum value
        notes: "",
        activeFromYear: "", // Use empty string for better number input UX
        activeToYear: "",   // Use empty string for better number input UX
        militaryOrganization: { _id: "" },
    });
    const [militaryOrganizations, setMilitaryOrganizations] = useState([]);
    const [sentState, setSent] = useState(false);
    const [successState, setSuccess] = useState(false);
    const [errorState, setError] = useState(null);
    const [loading, setLoading] = useState(true);

    // Fetch military rank data if in edit mode
    useEffect(() => {
        if (id) {
            apiGet(`/api/militaryRanks/${id}`)
                .then((data) => {
                    // UPDATED: When fetching an existing rank, correctly set the rankLevel state
                    setRank({
                        ...data,
                        rankLevel: data.rankLevel ? data.rankLevel.name : "", // Use the enum's name
                    });
                    setLoading(false);
                })
                .catch((error) => {
                    setError(error.message);
                    setLoading(false);
                });
        } else {
            setLoading(false);
        }
    }, [id]);

    // Fetch list of military organizations for the dropdown
    useEffect(() => {
        apiGet("/api/militaryOrganizations")
            .then((data) => {
                setMilitaryOrganizations(data);
            })
            .catch((error) => setError(error.message));
    }, []);

    // Auto-hide flash message after 3 seconds
    useEffect(() => {
        if (sentState) {
            const timer = setTimeout(() => setSent(false), 3000);
            return () => clearTimeout(timer);
        }
    }, [sentState]);

    const handleSubmit = (e) => {
        e.preventDefault();

        // UPDATED: The rankLevel is already a string, so we don't need to do anything special here.
        const rankToSend = {
            ...rank,
            militaryOrganization: rank.militaryOrganization._id ? { _id: rank.militaryOrganization._id } : null,
        };

        (id ? apiPut(`/api/militaryRanks/${id}`, rankToSend) : apiPost("/api/militaryRanks", rankToSend))
            .then(() => {
                setSent(true);
                setSuccess(true);
                setTimeout(() => navigate("/militaryRanks"), 1000);
            })
            .catch((error) => {
                console.error("API error:", error.message);
                setError(error.message);
                setSent(true);
                setSuccess(false);
            });
    };

    const handleMilitaryOrganizationChange = (selectedId) => {
        setRank((prevRank) => ({
            ...prevRank,
            militaryOrganization: { _id: selectedId },
        }));
    };

    const handleRankLevelChange = (selectedLevel) => {
        setRank((prevRank) => ({
            ...prevRank,
            rankLevel: selectedLevel,
        }));
    };

    if (loading) return <p>Loading form...</p>;
    if (errorState) return <Alert variant="danger">{errorState}</Alert>;

    const sent = sentState;
    const success = successState;

    return (
        <Container className="mt-5">
            <h1 className="mb-4">{id ? "Update" : "Create"} Military Rank</h1>
            {errorState && <Alert variant="danger">{errorState}</Alert>}

            {sent && (
                <FlashMessage
                    theme={success ? "success" : "danger"}
                    text={success ? "Military Rank saved successfully." : "Error saving military rank."}
                />
            )}

            <Form onSubmit={handleSubmit}>
                <Row className="mb-3">
                    <Col md={6}>
                        <InputField
                            required={true}
                            type="text"
                            name="rankName"
                            min="3"
                            label="Rank Name"
                            prompt="Input name of military rank"
                            value={rank.rankName}
                            handleChange={(e) => setRank({ ...rank, rankName: e.target.value })}
                        />
                    </Col>
                    <Col md={6}>
                        {/* UPDATED: Replaced InputField with InputSelect for rankLevel */}
                        <InputSelect
                            required={true}
                            name="rankLevel"
                            label="Rank Level"
                            prompt="Select rank level"
                            value={rank.rankLevel}
                            handleChange={(e) => handleRankLevelChange(e.target.value)}
                            items={Object.values(MilitaryRankLevel)}
                            getLabel={(item) => item.description} // Use description for display
                            getValue={(item) => item.name}         // Use name for the value
                        />
                    </Col>
                </Row>

                <Row className="mb-3">
                    <Col md={6}>
                        <InputField
                            type="number"
                            name="activeFromYear"
                            label="Active From Year"
                            prompt="Input year the rank became active"
                            value={rank.activeFromYear}
                            handleChange={(e) => setRank({ ...rank, activeFromYear: e.target.value })}
                        />
                    </Col>
                    <Col md={6}>
                        <InputField
                            type="number"
                            name="activeToYear"
                            label="Active To Year"
                            prompt="Input year the rank became inactive"
                            value={rank.activeToYear}
                            handleChange={(e) => setRank({ ...rank, activeToYear: e.target.value })}
                        />
                    </Col>
                </Row>

                <Row className="mb-3">
                    <Col md={6}>
                        <InputSelect
                            name="militaryOrganization"
                            label="Military Organization"
                            prompt="Select military organization"
                            value={rank.militaryOrganization?._id || ""}
                            handleChange={(e) => handleMilitaryOrganizationChange(e.target.value)}
                            items={militaryOrganizations}
                            getLabel={(item) => item.armyName}
                            getValue={(item) => item._id}
                        />
                    </Col>
                    <Col md={6}>
                        <InputField
                            type="text"
                            name="notes"
                            label="Notes"
                            prompt="Add any additional notes"
                            value={rank.notes}
                            handleChange={(e) => setRank({ ...rank, notes: e.target.value })}
                        />
                    </Col>
                </Row>

                <Button type="submit" variant="primary" className="mt-3 me-2">
                    <i className="fas fa-save me-2"></i>Save
                </Button>
                <Link to="/militaryRanks" className="btn btn-secondary mt-3">
                    <i className="fas fa-arrow-left me-2"></i>Cancel
                </Link>
            </Form>
        </Container>
    );
};

export default MilitaryRankForm;
