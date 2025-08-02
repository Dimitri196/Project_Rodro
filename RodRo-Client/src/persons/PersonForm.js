import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { apiGet, apiPost, apiPut } from "../utils/api";
import { Form, Button, Container, Row, Col, Alert, Spinner, Card } from "react-bootstrap";
import InputSelect from "../components/InputSelect";
import InputField from "../components/InputField";
import InputCheck from "../components/InputCheck";
import FlashMessage from "../components/FlashMessage";
import Gender from "./Gender"; // Assuming Gender is an enum or object with values
import socialStatusLabels from "../constants/socialStatusLabels";
import causeOfDeathLabels from "../constants/causeOfDeathLabels";

const PersonForm = () => {
    const navigate = useNavigate();
    const { id } = useParams();

    // Initial state for a new person, now including year, month, day for dates
    const [person, setPerson] = useState({
        givenName: "",
        givenSurname: "",
        gender: Gender.UNKNOWN,
        identificationNumber: "",
        note: "",
        socialStatus: "",
        causeOfDeath: "",

        // Date fields - now split into year, month, day
        birthYear: null,
        birthMonth: null,
        birthDay: null,
        baptizationYear: null,
        baptizationMonth: null,
        baptizationDay: null,
        deathYear: null,
        deathMonth: null,
        deathDay: null,
        burialYear: null,
        burialMonth: null,
        burialDay: null,

        // Place and relation IDs (initially empty objects with _id for InputSelect)
        birthPlace: { _id: "" },
        baptizationPlace: { _id: "" },
        deathPlace: { _id: "" },
        burialPlace: { _id: "" },
        mother: null, // Should be null or { _id: "" }
        father: null, // Should be null or { _id: "" }

        occupations: [],
        sourceEvidences: []
    });

    // State for dropdown options and form status
    const [locations, setLocations] = useState([]);
    const [occupations, setOccupations] = useState([]);
    const [sources, setSources] = useState([]);
    const [persons, setPersons] = useState([]); // For mother/father selection
    const [sentState, setSent] = useState(false);
    const [successState, setSuccess] = useState(false);
    const [errorState, setError] = useState(null);
    const [loading, setLoading] = useState(true);

    // Fetch initial data (locations, persons, occupations, sources) and existing person data if editing
    useEffect(() => {
        const fetchData = async () => {
            try {
                const [locsResponse, persResponse, occsResponse, srcsResponse] = await Promise.all([
                    apiGet("/api/locations?size=1000"), // Request all locations (or a sufficiently large page size)
                    apiGet("/api/persons?size=1000"), // Request all persons for parents
                    apiGet("/api/occupations?size=1000"), // Request all occupations
                    apiGet("/api/sources?size=1000") // Request all sources
                ]);

                // --- KEY CHANGE HERE ---
                // Extract the 'content' array from the paginated responses
                setLocations(locsResponse.content || []);
                setPersons(persResponse.content || []);
                setOccupations(occsResponse.content || []);
                setSources(srcsResponse.content || []);

                if (id) {
                    const data = await apiGet("/api/persons/" + id);

                    // The API response already contains the full nested objects, which is what your state is expecting.
                    // So, we can set the data directly.
                    setPerson({
                        ...data,
                        // The backend's GET response now provides `birthPlace`, `mother`, etc., as full DTOs.
                        // No mapping is needed for them. Just ensure that if the DTO is null, the state is set correctly.
                        birthPlace: data.birthPlace || { _id: "" },
                        baptizationPlace: data.baptizationPlace || { _id: "" },
                        deathPlace: data.deathPlace || { _id: "" },
                        burialPlace: data.burialPlace || { _id: "" },
                        mother: data.mother || { _id: "" },
                        father: data.father || { _id: "" },
                        // Occupations and sourceEvidences from the backend GET response are now also nested DTOs.
                        // We'll map them to a format that matches the component's state and dropdowns.
                        occupations: (data.occupations || []).map(occ => ({
                            ...occ,
                            // The backend GET response for `PersonOccupationDTO` likely contains a nested `OccupationDTO`.
                            // We need to extract the `_id` from that nested object to match the dropdown value.
                            _id: occ.occupation ? occ.occupation._id : ""
                        })),
                        sourceEvidences: (data.sourceEvidences || []).map(ev => ({
                            ...ev,
                            // The backend GET response for `PersonSourceEvidenceDTO` likely contains a nested `SourceDTO`.
                            // We need to extract the `_id` from that nested object to match the dropdown value.
                            _id: ev.source ? ev.source._id : ""
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
    }, [id]); // Effect runs on component mount and when ID changes (for edit mode)

    // Handler for changes in nested array objects (e.g., occupations, sourceEvidences)
    const handleArrayObjectChange = (arrayName, index, field, value) => {
        setPerson((prevPerson) => {
            const updatedArray = [...prevPerson[arrayName]];
            // Update the specific field for the item at the given index
            updatedArray[index] = {
                ...updatedArray[index],
                [field]: field === "_id" ? (value === "" ? null : Number(value)) : value // Convert _id to number or null
            };
            return {
                ...prevPerson,
                [arrayName]: updatedArray
            };
        });
    };

    // Handler for changes in date parts (year, month, day)
    const handleDatePartChange = (dateType, part, value) => {
        setPerson(prevPerson => ({
            ...prevPerson,
            [`${dateType}${part}`]: value === "" ? null : parseInt(value, 10) // Convert to number or null
        }));
    };

    // Form submission handler
    const handleSubmit = async (e) => {
        e.preventDefault();

        // Transform person data for API submission
        const transformedPerson = {
            ...person,
            // Ensure location and parent IDs are sent as numbers or null, using _id as per backend GET response
            // When sending to backend, use 'id' as the property name for nested objects
            birthPlace: person.birthPlace?._id ? { id: Number(person.birthPlace._id) } : null,
            baptizationPlace: person.baptizationPlace?._id ? { id: Number(person.baptizationPlace._id) } : null,
            deathPlace: person.deathPlace?._id ? { id: Number(person.deathPlace._id) } : null,
            burialPlace: person.burialPlace?._id ? { id: Number(person.burialPlace._id) } : null,
            mother: person.mother?._id ? { id: Number(person.mother._id) } : null,
            father: person.father?._id ? { id: Number(person.father._id) } : null,

            // Map occupations to match backend DTO structure (using occupationId)
            occupations: person.occupations.map((occ) => ({
                occupationId: occ._id ? Number(occ._id) : null, // Send frontend _id as occupationId
                startDate: occ.startDate || null,
                endDate: occ.endDate || null
            })),
            // Map source evidences to match backend DTO structure (using sourceId)
            sourceEvidences: person.sourceEvidences.map(ev => ({
                sourceId: ev._id ? Number(ev._id) : null // Send frontend _id as sourceId
            }))
        };

        // Basic validation for birth year not in the future
        if (transformedPerson.birthYear && transformedPerson.birthYear > new Date().getFullYear()) {
            setError("Birth year cannot be in the future.");
            return;
        }
        // More comprehensive date validation can be added here (e.g., month/day validity)

        try {
            const request = id
                ? apiPut("/api/persons/" + id, transformedPerson)
                : apiPost("/api/persons", transformedPerson);
            await request;

            setSent(true);
            setSuccess(true);
            setTimeout(() => { navigate("/persons"); }, 1000); // Redirect after successful save
        } catch (error) {
            console.error("Failed to save person:", error);
            setError("Failed to save person: " + (error.message || "An unknown error occurred."));
            setSent(true);
            setSuccess(false);
        }
    };

    // Handler for location dropdown changes
    const handleLocationChange = (placeType, selectedLocationId) => {
        setPerson((prevPerson) => ({
            ...prevPerson,
            [placeType]: selectedLocationId ? { _id: Number(selectedLocationId) } : { _id: "" } // Store ID as number or empty object
        }));
    };

    // Handler for parent dropdown changes
    const handleParentChange = (parentType, selectedPersonId) => {
        setPerson((prevPerson) => ({
            ...prevPerson,
            [parentType]: selectedPersonId ? { _id: Number(selectedPersonId) } : null // Store ID as number or null
        }));
    };

    // Add new occupation entry
    const addOccupation = () => {
        setPerson((prev) => ({
            ...prev,
            occupations: [...prev.occupations, { _id: "", startDate: "", endDate: "" }]
        }));
    };

    // Remove occupation entry by index
    const removeOccupation = (idx) => {
        setPerson((prev) => ({
            ...prev,
            occupations: prev.occupations.filter((_, i) => i !== idx)
        }));
    };

    // Add new source evidence entry
    const addSourceEvidence = () => {
        setPerson((prev) => ({
            ...prev,
            sourceEvidences: [...prev.sourceEvidences, { _id: "" }]
        }));
    };

    // Remove source evidence entry by index
    const removeSourceEvidence = (idx) => {
        setPerson((prev) => ({
            ...prev,
            sourceEvidences: prev.sourceEvidences.filter((_, i) => i !== idx)
        }));
    };

    // Display loading spinner while data is being fetched
    if (loading) {
        return (
            <Container className="mt-5 text-center">
                <Spinner animation="border" role="status" />
                <p>Loading...</p>
            </Container>
        );
    }

    // Display error message if data fetching fails
    if (errorState && !sentState) { // Only show initial fetch error, not submission error here
        return (
            <Container className="mt-5">
                <Alert variant="danger">{errorState}</Alert>
            </Container>
        );
    }

    return (
        <Container className="my-5">
            <h1 className="mb-4 text-center">{id ? "Update" : "Create New"} Person Record</h1>

            {/* Flash messages for success/error after submission */}
            {sentState && (
                <FlashMessage
                    theme={successState ? "success" : "danger"}
                    text={successState ? "Person record saved successfully!" : `Error saving person record: ${errorState}`}
                />
            )}

            <Form onSubmit={handleSubmit}>
                {/* Basic Information Card */}
                <Card className="mb-4 shadow-sm">
                    <Card.Header as="h5" className="bg-primary text-white">Basic Information</Card.Header>
                    <Card.Body>
                        <Row className="mb-3">
                            <Col md={6}>
                                <InputField
                                    required={true}
                                    type="text"
                                    name="givenName"
                                    label="Given Name"
                                    prompt="Enter given name"
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
                                    prompt="Enter given surname"
                                    value={person.givenSurname}
                                    handleChange={(e) => setPerson({ ...person, givenSurname: e.target.value })}
                                />
                            </Col>
                        </Row>

                        <Row className="mb-3">
                            <Col md={6}>
                                <InputField
                                    type="text"
                                    name="identificationNumber"
                                    label="GenWeb ID"
                                    prompt="Enter GenWeb ID (optional)"
                                    value={person.identificationNumber || ""}
                                    handleChange={(e) => setPerson({ ...person, identificationNumber: e.target.value })}
                                />
                            </Col>
                            <Col md={6}>
                                <Form.Group className="mb-3">
                                    <Form.Label>Gender</Form.Label>
                                    <div className="d-flex gap-3">
                                        <InputCheck
                                            type="radio"
                                            name="gender"
                                            label="Unknown"
                                            value={Gender.UNKNOWN}
                                            handleChange={(e) => setPerson({ ...person, gender: e.target.value })}
                                            checked={Gender.UNKNOWN === person.gender}
                                        />
                                        <InputCheck
                                            type="radio"
                                            name="gender"
                                            label="Female"
                                            value={Gender.FEMALE}
                                            handleChange={(e) => setPerson({ ...person, gender: e.target.value })}
                                            checked={Gender.FEMALE === person.gender}
                                        />
                                        <InputCheck
                                            type="radio"
                                            name="gender"
                                            label="Male"
                                            value={Gender.MALE}
                                            handleChange={(e) => setPerson({ ...person, gender: e.target.value })}
                                            checked={Gender.MALE === person.gender}
                                        />
                                    </div>
                                </Form.Group>
                            </Col>
                        </Row>

                        <Row className="mb-3">
                            <Col md={12}>
                                <InputField
                                    type="textarea" // Changed to textarea for multi-line notes
                                    name="note"
                                    label="Note"
                                    prompt="Add any relevant notes about the person"
                                    value={person.note || ""}
                                    handleChange={(e) => setPerson({ ...person, note: e.target.value })}
                                    rows={3} // Specify number of rows for textarea
                                />
                            </Col>
                        </Row>
                    </Card.Body>
                </Card>

                {/* Dates and Places Card */}
                <Card className="mb-4 shadow-sm">
                    <Card.Header as="h5" className="bg-primary text-white">Dates and Places</Card.Header>
                    <Card.Body>
                        {/* Birth */}
                        <Row className="mb-3 align-items-end">
                            <Col md={4}>
                                <InputSelect
                                    // ... other props ...
                                    name="birthPlace"
                                    label="Birth Place"
                                    prompt="Select birth place"
                                    value={person.birthPlace?._id || ""}
                                    handleChange={(e) => handleLocationChange("birthPlace", e.target.value)}
                                    items={locations}
                                    getLabel={(item) => item.locationName}
                                    getValue={(item) => item.id}
                                />
                            </Col>
                            <Col md={2}>
                                <InputField
                                    type="number"
                                    name="birthYear"
                                    label="Birth Year"
                                    prompt="YYYY"
                                    value={person.birthYear || ""}
                                    handleChange={(e) => handleDatePartChange("birth", "Year", e.target.value)}
                                    min="1000" // Example min year
                                    max={new Date().getFullYear()} // Max current year
                                />
                            </Col>
                            <Col md={3}>
                                <InputField
                                    type="number"
                                    name="birthMonth"
                                    label="Month"
                                    prompt="MM"
                                    value={person.birthMonth || ""}
                                    handleChange={(e) => handleDatePartChange("birth", "Month", e.target.value)}
                                    min="1"
                                    max="12"
                                />
                            </Col>
                            <Col md={3}>
                                <InputField
                                    type="number"
                                    name="birthDay"
                                    label="Day"
                                    prompt="DD"
                                    value={person.birthDay || ""}
                                    handleChange={(e) => handleDatePartChange("birth", "Day", e.target.value)}
                                    min="1"
                                    max="31"
                                />
                            </Col>
                        </Row>

                        {/* Baptization */}
                        <Row className="mb-3 align-items-end">
                            <Col md={4}>
                                <InputSelect
                                    name="baptizationPlace"
                                    label="Baptization Place"
                                    prompt="Select baptization place"
                                    value={person.baptizationPlace?._id || ""}
                                    handleChange={(e) => handleLocationChange("baptizationPlace", e.target.value)}
                                    items={locations}
                                    getLabel={(item) => item.locationName}
                                    getValue={(item) => item.id}
                                />
                            </Col>
                            <Col md={2}>
                                <InputField
                                    type="number"
                                    name="baptizationYear"
                                    label="Bapt. Year"
                                    prompt="YYYY"
                                    value={person.baptizationYear || ""}
                                    handleChange={(e) => handleDatePartChange("baptization", "Year", e.target.value)}
                                    min="1000"
                                    max={new Date().getFullYear()}
                                />
                            </Col>
                            <Col md={3}>
                                <InputField
                                    type="number"
                                    name="baptizationMonth"
                                    label="Month"
                                    prompt="MM"
                                    value={person.baptizationMonth || ""}
                                    handleChange={(e) => handleDatePartChange("baptization", "Month", e.target.value)}
                                    min="1"
                                    max="12"
                                />
                            </Col>
                            <Col md={3}>
                                <InputField
                                    type="number"
                                    name="baptizationDay"
                                    label="Day"
                                    prompt="DD"
                                    value={person.baptizationDay || ""}
                                    handleChange={(e) => handleDatePartChange("baptization", "Day", e.target.value)}
                                    min="1"
                                    max="31"
                                />
                            </Col>
                        </Row>

                        {/* Death */}
                        <Row className="mb-3 align-items-end">
                            <Col md={4}>
                                <InputSelect
                                    name="deathPlace"
                                    label="Death Place"
                                    prompt="Select death place"
                                    value={person.deathPlace?._id || ""}
                                    handleChange={(e) => handleLocationChange("deathPlace", e.target.value)}
                                    items={locations}
                                    getLabel={(item) => item.locationName}
                                    getValue={(item) => item.id}
                                />
                            </Col>
                            <Col md={2}>
                                <InputField
                                    type="number"
                                    name="deathYear"
                                    label="Death Year"
                                    prompt="YYYY"
                                    value={person.deathYear || ""}
                                    handleChange={(e) => handleDatePartChange("death", "Year", e.target.value)}
                                    min="1000"
                                    max={new Date().getFullYear()}
                                />
                            </Col>
                            <Col md={3}>
                                <InputField
                                    type="number"
                                    name="deathMonth"
                                    label="Month"
                                    prompt="MM"
                                    value={person.deathMonth || ""}
                                    handleChange={(e) => handleDatePartChange("death", "Month", e.target.value)}
                                    min="1"
                                    max="12"
                                />
                            </Col>
                            <Col md={3}>
                                <InputField
                                    type="number"
                                    name="deathDay"
                                    label="Day"
                                    prompt="DD"
                                    value={person.deathDay || ""}
                                    handleChange={(e) => handleDatePartChange("death", "Day", e.target.value)}
                                    min="1"
                                    max="31"
                                />
                            </Col>
                        </Row>

                        {/* Burial */}
                        <Row className="mb-3 align-items-end">
                            <Col md={4}>
                                <InputSelect
                                    name="burialPlace"
                                    label="Burial Place"
                                    prompt="Select burial place"
                                    value={person.burialPlace?._id || ""}
                                    handleChange={(e) => handleLocationChange("burialPlace", e.target.value)}
                                    items={locations}
                                    getLabel={(item) => item.locationName}
                                    getValue={(item) => item.id}
                                />
                            </Col>
                            <Col md={2}>
                                <InputField
                                    type="number"
                                    name="burialYear"
                                    label="Burial Year"
                                    prompt="YYYY"
                                    value={person.burialYear || ""}
                                    handleChange={(e) => handleDatePartChange("burial", "Year", e.target.value)}
                                    min="1000"
                                    max={new Date().getFullYear()}
                                />
                            </Col>
                            <Col md={3}>
                                <InputField
                                    type="number"
                                    name="burialMonth"
                                    label="Month"
                                    prompt="MM"
                                    value={person.burialMonth || ""}
                                    handleChange={(e) => handleDatePartChange("burial", "Month", e.target.value)}
                                    min="1"
                                    max="12"
                                />
                            </Col>
                            <Col md={3}>
                                <InputField
                                    type="number"
                                    name="burialDay"
                                    label="Day"
                                    prompt="DD"
                                    value={person.burialDay || ""}
                                    handleChange={(e) => handleDatePartChange("burial", "Day", e.target.value)}
                                    min="1"
                                    max="31"
                                />
                            </Col>
                        </Row>

                        <Row className="mb-3">
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
                    </Card.Body>
                </Card>

                {/* Family Relations Card */}
                <Card className="mb-4 shadow-sm">
                    <Card.Header as="h5" className="bg-primary text-white">Family Relations</Card.Header>
                    <Card.Body>
                        <Row className="mb-3">
                            <Col md={6}>
                                <InputSelect
                                    name="motherId"
                                    label="Mother"
                                    prompt="Choose mother"
                                    value={person.mother ? person.mother._id : ""}
                                    handleChange={(e) => handleParentChange("mother", e.target.value)}
                                    items={persons.filter(p => p.gender === Gender.FEMALE)}
                                    getLabel={(p) => p ? `${p.givenName || ""} ${p.givenSurname || ""}`.trim() : ""}
                                    getValue={(p) => p._id}
                                />
                            </Col>
                            <Col md={6}>
                                <InputSelect
                                    name="fatherId"
                                    label="Father"
                                    prompt="Choose father"
                                    value={person.father ? person.father._id : ""}
                                    handleChange={(e) => handleParentChange("father", e.target.value)}
                                    items={persons.filter(p => p.gender === Gender.MALE)}
                                    getLabel={(p) => p ? `${p.givenName || ""} ${p.givenSurname || ""}`.trim() : ""}
                                    getValue={(p) => p._id}
                                />
                            </Col>
                        </Row>
                    </Card.Body>
                </Card>

                {/* Occupations Card */}
                <Card className="mb-4 shadow-sm">
                    <Card.Header as="h5" className="bg-primary text-white">Occupations</Card.Header>
                    <Card.Body>
                        {person.occupations.length === 0 && <p className="text-muted">No occupations added yet.</p>}
                        {person.occupations.map((occ, idx) => (
                            <Row key={idx} className="mb-3 align-items-end border-bottom pb-3">
                                <Col md={5}>
                                    <InputSelect
                                        name={`occupation-${idx}`}
                                        label="Occupation"
                                        prompt="Select occupation"
                                        value={occ._id || ""}
                                        handleChange={(e) => handleArrayObjectChange("occupations", idx, "_id", e.target.value)}
                                        items={occupations}
                                        getLabel={(item) => item.occupationName}
                                        getValue={(item) => item._id}
                                    />
                                </Col>
                                <Col md={3}>
                                    <InputField
                                        type="text"
                                        name={`occupationStartDate-${idx}`}
                                        label="Start Date"
                                        prompt="YYYY-MM-DD"
                                        value={occ.startDate || ""}
                                        handleChange={(e) => handleArrayObjectChange("occupations", idx, "startDate", e.target.value)}
                                    />
                                </Col>
                                <Col md={3}>
                                    <InputField
                                        type="text"
                                        name={`occupationEndDate-${idx}`}
                                        label="End Date"
                                        prompt="YYYY-MM-DD"
                                        value={occ.endDate || ""}
                                        handleChange={(e) => handleArrayObjectChange("occupations", idx, "endDate", e.target.value)}
                                    />
                                </Col>
                                <Col md={1} className="d-flex align-items-end">
                                    <Button variant="danger" onClick={() => removeOccupation(idx)} className="w-100">
                                        <i className="fas fa-trash-alt"></i>
                                    </Button>
                                </Col>
                            </Row>
                        ))}
                        <Button variant="secondary" onClick={addOccupation} className="mt-3">
                            <i className="fas fa-plus me-2"></i>Add Occupation
                        </Button>
                    </Card.Body>
                </Card>

                {/* Source Evidences Card */}
                <Card className="mb-4 shadow-sm">
                    <Card.Header as="h5" className="bg-primary text-white">Source Evidences</Card.Header>
                    <Card.Body>
                        {person.sourceEvidences.length === 0 && <p className="text-muted">No source evidences added yet.</p>}
                        {person.sourceEvidences.map((ev, idx) => (
                            <Row key={idx} className="mb-3 align-items-end border-bottom pb-3">
                                <Col md={11}>
                                    <InputSelect
                                        name={`sourceEvidence-${idx}`}
                                        label="Source"
                                        prompt="Select source"
                                        value={ev._id || ""}
                                        handleChange={(e) => handleArrayObjectChange("sourceEvidences", idx, "_id", e.target.value)}
                                        items={sources}
                                        getLabel={(item) => item.sourceTitle}
                                        getValue={(item) => item._id}
                                    />
                                </Col>
                                <Col md={1} className="d-flex align-items-end">
                                    <Button variant="danger" onClick={() => removeSourceEvidence(idx)} className="w-100">
                                        <i className="fas fa-trash-alt"></i>
                                    </Button>
                                </Col>
                            </Row>
                        ))}
                        <Button variant="secondary" onClick={addSourceEvidence} className="mt-3">
                            <i className="fas fa-plus me-2"></i>Add Source Evidence
                        </Button>
                    </Card.Body>
                </Card>

                {/* Submit Button */}
                <Button type="submit" variant="primary" className="mt-3 px-4 py-2 shadow-sm rounded-pill">
                    <i className="fas fa-save me-2"></i>Save Record
                </Button>
            </Form>
        </Container>
    );
};

export default PersonForm;
