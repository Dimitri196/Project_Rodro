import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { apiGet, apiPost, apiPut } from "../utils/api";
import { Form, Button, Container, Row, Col, Alert, Spinner } from "react-bootstrap";
import InputSelect from "../components/InputSelect";
import InputField from "../components/InputField";
import InputCheck from "../components/InputCheck";
import FlashMessage from "../components/FlashMessage";
import Gender from "./Gender";
import socialStatusLabels from "../constants/socialStatusLabels";
import causeOfDeathLabels from "../constants/causeOfDeathLabels";

const PersonForm = () => {
    const navigate = useNavigate();
    const { id } = useParams();
    const [person, setPerson] = useState({
        birthPlace: { _id: "" },
        deathPlace: { _id: "" },
        burialPlace: { _id: "" },
        baptizationPlace: { _id: "" },
        birthDate: "",
        baptizationDate: "",
        deathDate: "",
        burialDate: "",
        givenName: "",
        givenSurname: "",
        gender: Gender.UNKNOWN,
        identificationNumber: "",
        note: "",
        mother: null,
        father: null,
        socialStatus: "",
        causeOfDeath: "",
        occupations: [],
        sourceEvidences: []
    });

    const [locations, setLocations] = useState([]);
    const [occupations, setOccupations] = useState([]);
    const [sources, setSources] = useState([]);
    const [sentState, setSent] = useState(false);
    const [successState, setSuccess] = useState(false);
    const [persons, setPersons] = useState([]);
    const [errorState, setError] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const [locs, pers, occs, srcs] = await Promise.all([
                    apiGet("/api/locations"),
                    apiGet("/api/persons"),
                    apiGet("/api/occupations"),
                    apiGet("/api/sources")
                ]);
                setLocations(locs);
                setPersons(pers);
                setOccupations(occs);
                setSources(srcs);

                if (id) {
                    const data = await apiGet("/api/persons/" + id);
                    setPerson({
                        ...data,
                        occupations: (data.occupations || []).map(occ => ({
                            ...occ,
                            _id: occ.occupationId || occ._id || ""
                        })),
                        sourceEvidences: (data.sourceEvidences || []).map(ev => ({
                            ...ev,
                            _id: ev.sourceId || ev._id || ""
                        }))
                    });
                }
            } catch (error) {
                console.error("Failed to fetch data:", error);
                setError("Failed to load initial data.");
            } finally {
                setLoading(false);
            }
        };

        fetchData();
    }, [id]);

    const handleArrayObjectChange = (arrayName, index, field, value) => {
    setPerson((prevPerson) => {
        const updatedArray = [...prevPerson[arrayName]];
        updatedArray[index] = {
            ...updatedArray[index],
            [field]: field === "_id" ? Number(value) : value
        };
        return {
            ...prevPerson,
            [arrayName]: updatedArray
        };
    });
};

    const handleSubmit = async (e) => {
        e.preventDefault();

        const transformedPerson = {
            ...person,
            birthDate: person.birthDate || null,
            baptizationDate: person.baptizationDate || null,
            deathDate: person.deathDate || null,
            burialDate: person.burialDate || null,
            occupations: person.occupations.map((occ) => ({
                occupationId: occ._id || null,
                startDate: occ.startDate || null,
                endDate: occ.endDate || null
            })),
            sourceEvidences: person.sourceEvidences.map(ev => ({
                sourceId: ev._id || null
            }))
        };

        if (transformedPerson.birthDate && new Date(transformedPerson.birthDate) > new Date()) {
            setError("Birth date cannot be in the future.");
            return;
        }

        try {
            const request = id
                ? apiPut("/api/persons/" + id, transformedPerson)
                : apiPost("/api/persons", transformedPerson);
            await request;

            setSent(true);
            setSuccess(true);
            setTimeout(() => { navigate("/persons"); }, 1000);
        } catch (error) {
            console.error(error);
            setError("Failed to save person: " + error.message);
            setSent(true);
            setSuccess(false);
        }
    };

    const handleLocationChange = (placeType, selectedLocationId) => {
        setPerson((prevPerson) => ({
            ...prevPerson,
            [placeType]: { _id: Number(selectedLocationId) }
        }));
    };

    const handleParentChange = (parentType, selectedPersonId) => {
        setPerson((prevPerson) => ({
            ...prevPerson,
            [parentType]: selectedPersonId ? { _id: Number(selectedPersonId) } : null
        }));
    };

    const handleOccupationChange = (idx, field, value) => {
        setPerson((prev) => {
            const occs = [...prev.occupations];
            occs[idx] = { ...occs[idx], [field]: value };
            return { ...prev, occupations: occs };
        });
    };

    const addOccupation = () => {
        setPerson((prev) => ({
            ...prev,
            occupations: [...prev.occupations, { _id: "", startDate: "", endDate: "" }]
        }));
    };

    const removeOccupation = (idx) => {
        setPerson((prev) => ({
            ...prev,
            occupations: prev.occupations.filter((_, i) => i !== idx)
        }));
    };

    const addSourceEvidence = () => {
        setPerson((prev) => ({
            ...prev,
            sourceEvidences: [...prev.sourceEvidences, { _id: "" }]
        }));
    };

    const removeSourceEvidence = (idx) => {
        setPerson((prev) => ({
            ...prev,
            sourceEvidences: prev.sourceEvidences.filter((_, i) => i !== idx)
        }));
    };  

    if (loading) {
        return (
            <Container className="mt-5 text-center">
                <Spinner animation="border" role="status" />
                <p>Loading...</p>
            </Container>
        );
    }

    if (errorState) {
        return (
            <Container className="mt-5">
                <Alert variant="danger">{errorState}</Alert>
            </Container>
        );
    }

    return (
        <Container className="mt-5">
            <h1 className="mb-4">{id ? "Update" : "Create"} person</h1>

            {errorState && <Alert variant="danger">{errorState}</Alert>}

            {sentState && (
                <FlashMessage
                    theme={successState ? "success" : "danger"}
                    text={successState ? "Person saved successfully." : "Error saving person."}
                />
            )}

            <Form onSubmit={handleSubmit}>
                {/* Parents */}
<Row className="mb-3">
    <Col md={6}>
        <InputSelect
            name="motherId"
            label="Mother"
            prompt="Choose mother"
            value={person.mother ? person.mother._id : ""}
            handleChange={(e) => handleParentChange("mother", e.target.value)}
            items={persons.filter(p => p.gender === "FEMALE")}
            getLabel={(p) => p ? `${p.givenName || ""} ${p.givenSurname || ""}`.trim() : ""}
            getValue={(p) => p?._id}
        />
    </Col>
    <Col md={6}>
        <InputSelect
            name="fatherId"
            label="Father"
            prompt="Choose father"
            value={person.father ? person.father._id : ""}
            handleChange={(e) => handleParentChange("father", e.target.value)}
            items={persons.filter(p => p.gender === "MALE")}
            getLabel={(p) => p ? `${p.givenName || ""} ${p.givenSurname || ""}`.trim() : ""}
            getValue={(p) => p?._id}
        />
    </Col>
</Row>

                {/* Names */}
                <Row className="mb-3">
                    <Col md={6}>
                        <InputField
                            required={true}
                            type="text"
                            name="givenName"
                            label="Given Name"
                            prompt="Provide Given Name"
                            value={person.givenName}
                            handleChange={(e) => setPerson({ ...person, givenName: e.target.value })}
                        />
                    </Col>
                    <Col md={6}>
                        <InputField
                            required={true}
                            type="text"
                            name="givenSurname"
                            label="Given Surname"
                            prompt="Provide Given Surname"
                            value={person.givenSurname}
                            handleChange={(e) => setPerson({ ...person, givenSurname: e.target.value })}
                        />
                    </Col>
                </Row>

                {/* Locations */}
                <Row className="mb-3">
                    <Col md={6}>
                        <InputSelect
                            name="birthPlaceId"
                            label="Birth Place"
                            prompt="Select birth place"
                            value={person.birthPlace?._id || ""}
                            handleChange={(e) => handleLocationChange("birthPlace", e.target.value)}
                            items={locations}
                            getLabel={(item) => item.locationName}
                            getValue={(item) => item._id}
                        />
                    </Col>
                    <Col md={6}>
                        <InputSelect
                            name="deathPlaceId"
                            label="Death Place"
                            prompt="Select death place"
                            value={person.deathPlace?._id || ""}
                            handleChange={(e) => handleLocationChange("deathPlace", e.target.value)}
                            items={locations}
                            getLabel={(item) => item.locationName}
                            getValue={(item) => item._id}
                        />
                    </Col>
                </Row>

                {/* Dates */}
                <Row className="mb-3">
                    <Col md={6}>
                        <InputField
                            type="date"
                            name="birthDate"
                            label="Birth Date"
                            prompt="Enter birth date"
                            value={person.birthDate || ""}
                            handleChange={(e) => setPerson({ ...person, birthDate: e.target.value })}
                        />
                    </Col>
                    <Col md={6}>
                        <InputField
                            type="date"
                            name="deathDate"
                            label="Death Date"
                            prompt="Enter death date"
                            value={person.deathDate || ""}
                            handleChange={(e) => setPerson({ ...person, deathDate: e.target.value })}
                        />
                    </Col>
                </Row>
                <Row>
    <Col md={6}>
        <InputSelect
            name="causeOfDeath"
            label="Cause of Death"
            prompt="Select cause of death"
            value={person.causeOfDeath || ""}
            handleChange={(e) => setPerson({ ...person, causeOfDeath: e.target.value })}
            items={Object.keys(causeOfDeathLabels)}
            getLabel={(key) => causeOfDeathLabels[key]}
            getValue={(key) => key}
        />
    </Col>
</Row>

                <Row className="mb-3">
                    <Col md={6}>
                        <InputSelect
                            name="burialPlaceId"
                            label="Burial Place"
                            prompt="Choose burial place"
                            value={person.burialPlace?._id || ""}
                            handleChange={(e) => handleLocationChange("burialPlace", e.target.value)}
                            items={locations}
                            getLabel={(item) => item.locationName}
                            getValue={(item) => item._id}
                        />
                    </Col>
                    <Col md={6}>
                        <InputSelect
                            name="baptizationPlaceId"
                            label="Baptization Place"
                            prompt="Choose baptization place"
                            value={person.baptizationPlace?._id || ""}
                            handleChange={(e) => handleLocationChange("baptizationPlace", e.target.value)}
                            items={locations}
                            getLabel={(item) => item.locationName}
                            getValue={(item) => item._id}
                        />
                    </Col>
                </Row>

                <Row className="mb-3">
                    <Col md={6}>
                        <InputField
                            type="date"
                            name="burialDate"
                            label="Burial Date"
                            prompt="Enter burial date"
                            value={person.burialDate || ""}
                            handleChange={(e) => setPerson({ ...person, burialDate: e.target.value })}
                        />
                    </Col>
                    <Col md={6}>
                        <InputField
                            type="date"
                            name="baptizationDate"
                            label="Baptization Date"
                            prompt="Enter baptization date"
                            value={person.baptizationDate || ""}
                            handleChange={(e) => setPerson({ ...person, baptizationDate: e.target.value })}
                        />
                    </Col>
                </Row>

                {/* Gender */}
                <Row>
                    <Col md={3}>
                        <InputCheck
                            type="radio"
                            name="gender"
                            label="Unknown"
                            value={Gender.UNKNOWN}
                            handleChange={(e) => setPerson({ ...person, gender: e.target.value })}
                            checked={Gender.UNKNOWN === person.gender}
                        />
                    </Col>
                    <Col md={3}>
                        <InputCheck
                            type="radio"
                            name="gender"
                            label="Female"
                            value={Gender.FEMALE}
                            handleChange={(e) => setPerson({ ...person, gender: e.target.value })}
                            checked={Gender.FEMALE === person.gender}
                        />
                    </Col>
                    <Col md={3}>
                        <InputCheck
                            type="radio"
                            name="gender"
                            label="Male"
                            value={Gender.MALE}
                            handleChange={(e) => setPerson({ ...person, gender: e.target.value })}
                            checked={Gender.MALE === person.gender}
                        />
                    </Col>
                </Row>

                

                {/* Social Status */}
                <Row>
                    <Col md={6}>
                        <InputSelect
                            name="socialStatus"
                            label="Social Status"
                            prompt="Select social status"
                            value={person.socialStatus || ""}
                            handleChange={(e) => setPerson({ ...person, socialStatus: e.target.value })}
                            items={Object.keys(socialStatusLabels)}
                            getLabel={(key) => socialStatusLabels[key]}
                            getValue={(key) => key}
                        />
                    </Col>
                </Row>

                {/* Note */}
                <Row>
                    <Col md={6}>
                        <InputField
                            required={true}
                            type="text"
                            name="note"
                            label="Note"
                            value={person.note}
                            handleChange={(e) => setPerson({ ...person, note: e.target.value })}
                        />
                    </Col>
                </Row>

                {/* Occupations */}
                <Row className="mt-4">
                    <Col>
                        <h5>Occupations</h5>
                        {person.occupations.map((occ, idx) => (
                            <Row key={idx} className="mb-2 align-items-end">
                                <Col md={6}>
                                    <InputSelect
                                        name={`occupation-${idx}`}
                                        label={`Occupation #${idx + 1}`}
                                        prompt="Select occupation"
                                        value={person.occupations[idx]?._id || ""}
                                        handleChange={(e) => handleArrayObjectChange("occupations", idx, "_id", e.target.value)}
                                        items={occupations}
                                        getLabel={(item) => item.occupationName}
                                        getValue={(item) => item._id}
                                    />
                                </Col>
                                <Col md={2}>
                                    <InputField
                                        type="date"
                                        name={`startDate_${idx}`}
                                        label="Start"
                                        value={occ.startDate || ""}
                                        handleChange={e => handleOccupationChange(idx, "startDate", e.target.value)}
                                    />
                                </Col>
                                <Col md={2}>
                                    <InputField
                                        type="date"
                                        name={`endDate_${idx}`}
                                        label="End"
                                        value={occ.endDate || ""}
                                        handleChange={e => handleOccupationChange(idx, "endDate", e.target.value)}
                                    />
                                </Col>
                                <Col md={2}>
                                    <Button variant="danger" onClick={() => removeOccupation(idx)}>-</Button>
                                </Col>
                            </Row>
                        ))}
                        <Button variant="secondary" onClick={addOccupation}>Add Occupation</Button>
                    </Col>
                </Row>

                {/* Sources */}
                <Row className="mt-4">
                    <Col>
                        <h5>Sources</h5>
                        {person.sourceEvidences.map((ev, idx) => (
                            <Row key={idx} className="mb-2 align-items-end">
                                <Col md={6}>
                                    <InputSelect
                                        name={`sourceEvidence-${idx}`}
                                        label={`Source Evidence #${idx + 1}`}
                                        prompt="Select source"
                                        value={person.sourceEvidences[idx]?._id|| ""}
                                        handleChange={(e) => handleArrayObjectChange("sourceEvidences", idx, "_id", e.target.value)}
                                        items={sources}
                                        getLabel={(item) => item.sourceTitle}
                                        getValue={(item) => item._id}
                                    />
                                </Col>

                                <Col md={2}>
                                    <Button variant="danger" onClick={() => removeSourceEvidence(idx)}>-</Button>
                                </Col>
                            </Row>
                        ))}
                        <Button variant="secondary" onClick={addSourceEvidence}>Add Source</Button>
                    </Col>
                </Row>

                <Button type="submit" variant="primary" className="mt-3">
                    Save
                </Button>
            </Form>
        </Container>
    );
};

export default PersonForm;