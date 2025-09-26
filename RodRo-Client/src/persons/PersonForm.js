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

  const [person, setPerson] = useState({
    givenName: "",
    givenSurname: "",
    gender: Gender.UNKNOWN,
    identificationNumber: "",
    note: "",
    socialStatus: "",
    causeOfDeath: "",

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

    birthPlace: null,
    baptizationPlace: null,
    deathPlace: null,
    burialPlace: null,
    mother: null,
    father: null,

    occupations: [],
    sourceEvidences: []
  });

  const [locations, setLocations] = useState([]);
  const [persons, setPersons] = useState([]);
  const [occupations, setOccupations] = useState([]);
  const [sources, setSources] = useState([]);
  const [sentState, setSent] = useState(false);
  const [successState, setSuccess] = useState(false);
  const [errorState, setError] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [locsResp, persResp, occsResp, srcsResp] = await Promise.all([
          apiGet("/api/locations?size=1000"),
          apiGet("/api/persons?size=1000"),
          apiGet("/api/occupations?size=1000"),
          apiGet("/api/sources?size=1000"),
        ]);

        setLocations(locsResp.content || []);
        setPersons(persResp.content || []);
        setOccupations(occsResp.content || []);
        setSources(srcsResp.content || []);

        if (id) {
          const data = await apiGet(`/api/persons/${id}`);
          setPerson({
            ...data,
            birthPlace: data.birthPlace ? { ...data.birthPlace, _id: data.birthPlace._id ?? data.birthPlace.id } : null,
            baptizationPlace: data.baptizationPlace ? { ...data.baptizationPlace, _id: data.baptizationPlace._id ?? data.baptizationPlace.id } : null,
            deathPlace: data.deathPlace ? { ...data.deathPlace, _id: data.deathPlace._id ?? data.deathPlace.id } : null,
            burialPlace: data.burialPlace ? { ...data.burialPlace, _id: data.burialPlace._id ?? data.burialPlace.id } : null,
            mother: data.mother ? { ...data.mother, _id: data.mother._id ?? data.mother.id } : null,
            father: data.father ? { ...data.father, _id: data.father._id ?? data.father.id } : null,
            occupations: (data.occupations || []).map(occ => ({
              ...occ,
              _id: occ.occupation ? occ.occupation._id ?? occ.occupation.id : ""
            })),
            sourceEvidences: (data.sourceEvidences || []).map(ev => ({
              ...ev,
              _id: ev.source ? ev.source._id ?? ev.source.id : ""
            })),
          });
        }
      } catch (error) {
        console.error(error);
        setError("Failed to load initial data.");
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [id]);

  const handleLocationChange = (placeType, selectedLocationId) => {
    const selected = locations.find((loc) => String(loc._id ?? loc.id) === String(selectedLocationId));
    setPerson((prev) => ({
      ...prev,
      [placeType]: selected ? { ...selected, _id: selected._id ?? selected.id } : null,
    }));
  };

  const handleParentChange = (parentType, selectedPersonId) => {
    const selected = persons.find((p) => String(p._id ?? p.id) === String(selectedPersonId));
    setPerson((prev) => ({
      ...prev,
      [parentType]: selected ? { ...selected, _id: selected._id ?? selected.id } : null,
    }));
  };

  const handleDatePartChange = (dateType, part, value) => {
    setPerson((prev) => ({
      ...prev,
      [`${dateType}${part}`]: value === "" ? null : parseInt(value, 10)
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const transformedPerson = {
      ...person,
      birthPlaceId: person.birthPlace?._id ?? null,
      baptizationPlaceId: person.baptizationPlace?._id ?? null,
      deathPlaceId: person.deathPlace?._id ?? null,
      burialPlaceId: person.burialPlace?._id ?? null,
      motherId: person.mother?._id ?? null,
      fatherId: person.father?._id ?? null,
      occupations: person.occupations.map((occ) => ({
        occupationId: occ._id ?? null,
        startDate: occ.startDate || null,
        endDate: occ.endDate || null
      })),
      sourceEvidences: person.sourceEvidences.map((ev) => ({
        sourceId: ev._id ?? null
      })),
    };

    if (transformedPerson.birthYear && transformedPerson.birthYear > new Date().getFullYear()) {
      setError("Birth year cannot be in the future.");
      return;
    }

    try {
      const request = id
        ? apiPut(`/api/persons/${id}`, transformedPerson)
        : apiPost("/api/persons", transformedPerson);

      await request;
      setSent(true);
      setSuccess(true);
      setTimeout(() => navigate("/persons"), 1000);
    } catch (error) {
      console.error(error);
      setError("Failed to save person: " + (error.message || "Unknown error"));
      setSent(true);
      setSuccess(false);
    }
  };

  const addOccupation = () => setPerson((prev) => ({
    ...prev,
    occupations: [...prev.occupations, { _id: "", startDate: "", endDate: "" }]
  }));

  const removeOccupation = (idx) => setPerson((prev) => ({
    ...prev,
    occupations: prev.occupations.filter((_, i) => i !== idx)
  }));

  const addSourceEvidence = () => setPerson((prev) => ({
    ...prev,
    sourceEvidences: [...prev.sourceEvidences, { _id: "" }]
  }));

  const removeSourceEvidence = (idx) => setPerson((prev) => ({
    ...prev,
    sourceEvidences: prev.sourceEvidences.filter((_, i) => i !== idx)
  }));

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
