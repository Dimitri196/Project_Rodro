import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { apiGet, apiPost, apiPut } from "../utils/api";
import { Form, Button, Container, Row, Col, Alert, Spinner } from "react-bootstrap";

import InputField from "../components/InputField";
import InputSelect from "../components/InputSelect";
import InputCheck from "../components/InputCheck";
import FlashMessage from "../components/FlashMessage";

const maritalStatusEnum = [
    { value: "SINGLE", label: "Single" },
    { value: "MARRIED", label: "Married" },
    { value: "SEPARATED", label: "Separated" },
    { value: "DIVORCED", label: "Divorced" },
    { value: "WIDOWED", label: "Widowed" }
];

const FamilyForm = () => {
    const navigate = useNavigate();
    const { id } = useParams();
    const [family, setFamily] = useState({
        marriageDate: "",
        marriageLocation: { _id: "" },
        spouseMale: { _id: "" },
        spouseFemale: { _id: "" },
        maritalStatusForSpouseMale: "",
        maritalStatusForSpouseFemale: "",
        witnessesMaleSide1: { _id: "" },
        witnessesMaleSide2: { _id: "" },
        witnessesFemaleSide1: { _id: "" },
        witnessesFemaleSide2: { _id: "" },
        source: "",
        note: ""
    });

    const [sent, setSent] = useState(false);
    const [success, setSuccess] = useState(false);
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(true);

    const [persons, setPersons] = useState([]);
    const [locations, setLocations] = useState([]);
    const [malePersons, setMalePersons] = useState([]);
    const [femalePersons, setFemalePersons] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const personData = await apiGet("/api/persons");
                const locationData = await apiGet("/api/locations");

                setPersons(personData);
                setLocations(locationData);
                setMalePersons(personData.filter(p => p.gender === "MALE"));
                setFemalePersons(personData.filter(p => p.gender === "FEMALE"));

                if (id) {
                    const familyData = await apiGet("/api/families/" + id);
                    setFamily(familyData);
                }
            } catch (err) {
                setError("Failed to load initial data.");
            } finally {
                setLoading(false);
            }
        };
        fetchData();
    }, [id]);

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (new Date(family.marriageDate) > new Date()) {
            setError("Marriage date cannot be in the future.");
            return;
        }

        if (!family.spouseMale._id || !family.spouseFemale._id) {
            setError("Both husband and wife must be selected.");
            return;
        }

        try {
            setError(null);
            const request = id
                ? apiPut("/api/families/" + id, family)
                : apiPost("/api/families", family);
            await request;

            setSent(true);
            setSuccess(true);
            setTimeout(() => navigate("/families"), 1000);
        } catch (err) {
            setError(err.response?.data?.error || "Failed to save family: " + err.message);
            setSent(true);
            setSuccess(false);
        }
    };

    if (loading) {
        return (
            <Container className="text-center mt-5">
                <Spinner animation="border" />
                <p>Loading form...</p>
            </Container>
        );
    }

    return (
        <Container className="mt-5">
            <h1 className="mb-4">{id ? "Edit" : "Create"} Family</h1>

            {error && <Alert variant="danger">{error}</Alert>}

            {sent && (
                <FlashMessage
                    theme={success ? "success" : "danger"}
                    text={success ? "Family saved successfully." : "Error saving family."}
                />
            )}

            <Form onSubmit={handleSubmit}>
                <Row className="mb-3">
                    <Col md={6}>
                        <InputField
                            required
                            type="date"
                            name="marriageDate"
                            label="Marriage Date"
                            prompt="Enter marriage date"
                            value={family.marriageDate}
                            handleChange={(e) => setFamily({ ...family, marriageDate: e.target.value })}
                        />
                    </Col>
                    <Col md={6}>
                        <InputSelect
                            name="marriageLocationId"
                            label="Marriage Location"
                            prompt="Select marriage location"
                            value={family.marriageLocation?._id || ""}
                            handleChange={(e) => setFamily({
                                ...family,
                                marriageLocation: { _id: e.target.value }
                            })}
                            items={locations}
                            getLabel={(item) => item.locationName}
                            getValue={(item) => item._id}
                        />
                    </Col>
                </Row>

                <Row className="mb-3">
                    <Col md={6}>
                        <InputSelect
                            name="spouseMaleId"
                            label="Husband"
                            prompt="Select husband"
                            value={family.spouseMale?._id || ""}
                            handleChange={(e) => setFamily({
                                ...family,
                                spouseMale: { ...family.spouseMale, _id: e.target.value }
                            })}
                            items={malePersons}
                            disabledItems={[family.spouseFemale?._id].filter(Boolean)}
                            getLabel={(p) => p ? `${p.givenName || ""} ${p.givenSurname || ""}`.trim() : ""}
                            getValue={(p) => p?._id}
                        />
                    </Col>
                    <Col md={6}>
                        <InputSelect
                            name="spouseFemaleId"
                            label="Wife"
                            prompt="Select wife"
                            value={family.spouseFemale?._id || ""}
                            handleChange={(e) => setFamily({
                                ...family,
                                spouseFemale: { ...family.spouseFemale, _id: e.target.value }
                            })}
                            items={femalePersons}
                            disabledItems={[family.spouseMale?._id].filter(Boolean)}
                            getLabel={(p) => p ? `${p.givenName || ""} ${p.givenSurname || ""}`.trim() : ""}
                            getValue={(p) => p?._id}
                        />
                    </Col>
                </Row>

                <Row className="mb-3">
                    <Col md={6}>
                        <h5>Husband's Marital Status</h5>
                        {maritalStatusEnum.map(status => (
                            <InputCheck
                                key={status.value}
                                type="radio"
                                name="maritalStatusForSpouseMale"
                                label={status.label}
                                value={status.value}
                                checked={family.maritalStatusForSpouseMale === status.value}
                                handleChange={(e) => setFamily({
                                    ...family,
                                    maritalStatusForSpouseMale: e.target.value
                                })}
                            />
                        ))}
                    </Col>
                    <Col md={6}>
                        <h5>Wife's Marital Status</h5>
                        {maritalStatusEnum.map(status => (
                            <InputCheck
                                key={status.value}
                                type="radio"
                                name="maritalStatusForSpouseFemale"
                                label={status.label}
                                value={status.value}
                                checked={family.maritalStatusForSpouseFemale === status.value}
                                handleChange={(e) => setFamily({
                                    ...family,
                                    maritalStatusForSpouseFemale: e.target.value
                                })}
                            />
                        ))}
                    </Col>
                </Row>

                <Row className="mb-3">
                    <Col md={6}>
                        <InputSelect
                            name="witnessMale1Id"
                            label="Witness 1 (Groom's Side)"
                            prompt="Select witness"
                            value={family.witnessesMaleSide1?._id || ""}
                            handleChange={(e) => setFamily({
                                ...family,
                                witnessesMaleSide1: { _id: e.target.value }
                            })}
                            items={persons}
                            getLabel={(p) => p ? `${p.givenName || ""} ${p.givenSurname || ""}`.trim() : ""}
                            getValue={(p) => p?._id}
                        />
                    </Col>
                    <Col md={6}>
                        <InputSelect
                            name="witnessMale2Id"
                            label="Witness 2 (Groom's Side)"
                            prompt="Select witness"
                            value={family.witnessesMaleSide2?._id || ""}
                            handleChange={(e) => setFamily({
                                ...family,
                                witnessesMaleSide2: { _id: e.target.value }
                            })}
                            items={persons}
                            getLabel={(p) => p ? `${p.givenName || ""} ${p.givenSurname || ""}`.trim() : ""}
                            getValue={(p) => p?._id}
                        />
                    </Col>
                </Row>

                <Row className="mb-3">
                    <Col md={6}>
                        <InputSelect
                            name="witnessFemale1Id"
                            label="Witness 1 (Bride's Side)"
                            prompt="Select witness"
                            value={family.witnessesFemaleSide1?._id || ""}
                            handleChange={(e) => setFamily({
                                ...family,
                                witnessesFemaleSide1: { _id: e.target.value }
                            })}
                            items={persons}
                            getLabel={(p) => p ? `${p.givenName || ""} ${p.givenSurname || ""}`.trim() : ""}
                            getValue={(p) => p?._id}
                        />
                    </Col>
                    <Col md={6}>
                        <InputSelect
                            name="witnessFemale2Id"
                            label="Witness 2 (Bride's Side)"
                            prompt="Select witness"
                            value={family.witnessesFemaleSide2?._id || ""}
                            handleChange={(e) => setFamily({
                                ...family,
                                witnessesFemaleSide2: { _id: e.target.value }
                            })}
                            items={persons}
                            getLabel={(p) => p ? `${p.givenName || ""} ${p.givenSurname || ""}`.trim() : ""}
                            getValue={(p) => p?._id}
                        />
                    </Col>
                </Row>

                <Row className="mb-3">
                    <Col md={12}>
                        <InputField
                            type="text"
                            name="source"
                            label="Source"
                            prompt="Enter the source of information"
                            value={family.source}
                            handleChange={(e) => setFamily({ ...family, source: e.target.value })}
                        />
                    </Col>
                </Row>

                <Row className="mb-3">
                    <Col md={12}>
                        <InputField
                            type="text"
                            name="note"
                            label="Note"
                            prompt="Enter any additional notes"
                            value={family.note}
                            handleChange={(e) => setFamily({ ...family, note: e.target.value })}
                        />
                    </Col>
                </Row>

                <Button type="submit" variant="primary" className="mt-3">
                    Save
                </Button>
            </Form>
        </Container>
    );
};

export default FamilyForm;
