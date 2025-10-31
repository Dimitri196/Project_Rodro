// PersonDetail.jsx
import React, { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import {
  Container,
  Row,
  Col,
  Card,
  ListGroup,
  Alert,
  Spinner,
  Accordion,
  Button,
  Modal,
  Form,
} from "react-bootstrap";
import { useSession } from "../contexts/session";
import { apiGet, apiPost } from "../utils/api";

import socialStatusLabels from "../constants/socialStatusLabels";
import causeOfDeathLabels from "../constants/causeOfDeathLabels";

const formatPartialDate = (year, month, day) => {
  if (!year) return "Unknown";
  const parts = [year];
  if (month) {
    parts.push(String(month).padStart(2, "0"));
    if (day) parts.push(String(day).padStart(2, "0"));
  }
  return parts.join("-");
};

const PersonDetail = () => {
  const { id } = useParams();
  const { session } = useSession();
  const isAdmin = session.data?.isAdmin === true;

  const [person, setPerson] = useState({});
  const [familyData, setFamilyData] = useState([]);
  const [childs, setChilds] = useState([]);
  const [occupations, setOccupations] = useState([]);
  const [militaryServices, setMilitaryServices] = useState([]);
  const [sourceAttributions, setSourceAttributions] = useState([]);
  const [sourcesList, setSourcesList] = useState([]); // for add modal dropdown
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // View sources modal state
  const [viewModalVisible, setViewModalVisible] = useState(false);
  const [viewModalSources, setViewModalSources] = useState([]); // array of { attribution, source }
  const [viewModalTitle, setViewModalTitle] = useState("");

  // Add source modal state
  const [addModalVisible, setAddModalVisible] = useState(false);
  const [addModalContext, setAddModalContext] = useState({
    type: null,
    entityId: null,
    label: "",
  });
  const [addModalSourceId, setAddModalSourceId] = useState("");
  const [addModalNote, setAddModalNote] = useState("");
  const [submitting, setSubmitting] = useState(false);

  const getFullName = (p) => (p ? `${p.givenName || ""} ${p.surname || ""}`.trim() : "N/A");

  // Helper: numeric person id (from param or person object)
  const personNumericId = () => Number(person.id ?? person._id ?? id);

  // initial load
  useEffect(() => {
    const fetchData = async () => {
      setLoading(true);
      try {
        const [
          personData,
          occupationData,
          families,
          childrenData,
          militaryServiceData,
          attributionsData,
          sourcesResp,
        ] = await Promise.all([
          apiGet(`/api/persons/${id}`),
          apiGet(`/api/persons/${id}/occupations`),
          apiGet(`/api/persons/${id}/families`),
          apiGet(`/api/persons/${id}/children`),
          apiGet(`/api/personMilitaryServices?personId=${id}`),
          apiGet(`/api/sourceAttributions/person/${id}`), // full list for this person
          apiGet(`/api/sources?size=1000`),
        ]);

        setPerson(personData || {});
        setOccupations(occupationData || []);
        setFamilyData(families || []);
        setChilds(childrenData || []);
        setMilitaryServices(militaryServiceData || []);
        setSourceAttributions(attributionsData || []);
        // ensure sourcesList is an array (sourcesResp may be paged)
        setSourcesList(sourcesResp?.content ?? sourcesResp ?? []);
      } catch (err) {
        console.error("Error fetching person detail data", err);
        setError(`Error loading data: ${err.message || err}`);
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [id]);

  // refresh attributions for this person (call after adding)
  const refreshAttributions = async () => {
    try {
      const a = await apiGet(`/api/sourceAttributions/person/${id}`);
      setSourceAttributions(a || []);
    } catch (err) {
      console.error("Failed to refresh attributions", err);
    }
  };

  // Fetch full Source by id (returns object or null)
  const fetchSourceById = async (sourceId) => {
    if (!sourceId) return null;
    try {
      return await apiGet(`/api/sources/${sourceId}`);
    } catch (err) {
      console.warn("Failed to load source", sourceId, err);
      return null;
    }
  };

  // Show sources for a given event/type and entityId
  // - For person-level events (BIRTH/BAPTISM/DEATH/BURIAL/FATHER/MOTHER/CHILD_BIRTH),
  //   use backend filter: GET /api/sourceAttributions/person/{personId}?eventType=TYPE
  // - For OCCUPATION/FAMILY/MILITARY, fetch all attributions for person and filter client-side by the entity id
  const showSources = async (type, entityId, label) => {
    setViewModalTitle(label || `${type} Sources`);
    setViewModalVisible(true);
    setViewModalSources([]); // clear while loading

    try {
      const upperType = String(type).toUpperCase();
      let attributions = [];

      // Person-level event types: backend can filter by eventType
      const personLevelTypes = ["BIRTH", "BAPTISM", "DEATH", "BURIAL", "FATHER", "MOTHER", "CHILD_BIRTH", "MARRIAGE", "OTHER"];
      if (personLevelTypes.includes(upperType)) {
        // call backend with ?eventType=... to get only relevant attributions tied to this person
        try {
          attributions = await apiGet(`/api/sourceAttributions/person/${personNumericId()}?eventType=${upperType}`);
        } catch (err) {
          // fallback to all and filter
          console.warn("Backend filter failed, fetching all attributions and client-filtering", err);
          attributions = await apiGet(`/api/sourceAttributions/person/${personNumericId()}`);
        }
        // If event maps to parent/child entities (FATHER/MOTHER/CHILD_BIRTH) we still want to filter by the linked id
        if (upperType === "FATHER" || upperType === "MOTHER") {
          const eid = Number(entityId);
          attributions = (attributions || []).filter(a => Number(a.personId) === personNumericId() && (Number(a.personId) === eid || Number(a.personId) === personNumericId() || Number(a.personId) === Number(a.personId)));
          // note: parent attributions may be stored with personId (the child) – keep backend result as-is
        }
      } else {
        // Non-person-level: fetch all for person then client-side filter by occupationId/familyId/militaryServiceId
        const all = await apiGet(`/api/sourceAttributions/person/${personNumericId()}`);
        const eid = Number(entityId);
        if (upperType === "OCCUPATION") {
          attributions = (all || []).filter(a => Number(a.occupationId) === eid);
        } else if (upperType === "MARRIAGE" || upperType === "FAMILY") {
          attributions = (all || []).filter(a => Number(a.familyId) === eid);
        } else if (upperType === "MILITARY") {
          attributions = (all || []).filter(a => Number(a.militaryServiceId) === eid);
        } else {
          // fallback: filter by type only
          attributions = (all || []).filter(a => (a.type || "").toString().toUpperCase() === upperType);
        }
      }

      // Fetch each attribution's full source (Variant A)
      const withSource = await Promise.all(
        (attributions || []).map(async (attr) => {
          const s = await fetchSourceById(attr.sourceId);
          return { attribution: attr, source: s };
        })
      );

      setViewModalSources(withSource || []);
    } catch (err) {
      console.error("Error while preparing sources modal", err);
      setViewModalSources([]);
    }
  };

  // Open Add Source modal
  const handleOpenAddModal = (type, entityId, label) => {
    setAddModalContext({ type, entityId, label });
    setAddModalSourceId("");
    setAddModalNote("");
    setAddModalVisible(true);
  };

  // Submit add source
  const handleAddSourceSubmit = async (ev) => {
    ev.preventDefault();
    if (!addModalSourceId) {
      alert("Please choose a source.");
      return;
    }

    const upperType = String(addModalContext.type).toUpperCase();
    const payload = {
      sourceId: Number(addModalSourceId),
      type: upperType,
      note: addModalNote || null,
      personId: null,
      occupationId: null,
      familyId: null,
      militaryServiceId: null,
    };

    // Determine correct target field
    switch (upperType) {
      case "BIRTH":
      case "BAPTISM":
      case "DEATH":
      case "BURIAL":
      case "FATHER":
      case "MOTHER":
      case "CHILD_BIRTH":
      case "OTHER":
        // these are person-level events -> link to the person on this page
        payload.personId = personNumericId();
        break;
      case "OCCUPATION":
        payload.occupationId = Number(addModalContext.entityId);
        break;
      case "MARRIAGE":
      case "FAMILY":
        payload.familyId = Number(addModalContext.entityId);
        break;
      case "MILITARY":
        payload.militaryServiceId = Number(addModalContext.entityId);
        break;
      default:
        // Fallback — treat as person-level
        payload.personId = personNumericId();
    }

    try {
      setSubmitting(true);
      await apiPost("/api/sourceAttributions", payload);
      setAddModalVisible(false);
      // Refresh local lists
      await refreshAttributions();
    } catch (err) {
      console.error("Error adding source attribution", err);
      alert("Failed to add source: " + (err.message || err));
    } finally {
      setSubmitting(false);
    }
  };

  if (loading)
    return (
      <Container className="mt-5 text-center">
        <Spinner animation="border" />
        <p>Loading...</p>
      </Container>
    );

  if (error) return <Alert variant="danger">{error}</Alert>;

  return (
    <Container className="mt-5">
      {/* Header */}
      <Row className="mb-4 text-center">
        <Col md={12}>
          <h1 className="display-4 fw-bold text-primary mb-2">{getFullName(person)}</h1>
          {isAdmin && (
            <Link
              to={`/persons/edit/${person._id || person.id}`}
              className="btn btn-warning btn-lg rounded-pill px-4 py-2 shadow-sm mt-3"
            >
              <i className="fas fa-edit me-2"></i>Edit Person
            </Link>
          )}
        </Col>
      </Row>

      <Row className="mb-4">
        {/* Left Column */}
        <Col md={4}>
          <Card className="mb-4 shadow-sm">
            <Card.Header as="h5" className="bg-primary text-white">Basic Information</Card.Header>
            <Card.Body>
              <Card.Title className="text-center mb-3">{getFullName(person)}</Card.Title>

              {person.externalId ? (
                <div className="mb-3 d-flex flex-column gap-2">
                  <a href={`http://mali-wielcy.pl:2317/Polesie?lang=en;templ=templd;m=D;i=${person.externalId};oc=1;siblings=on;notes=on;t=T;image=on;v=5`}
                     className="btn btn-outline-primary btn-sm" target="_blank" rel="noopener noreferrer">View Descendants Tree</a>
                  <a href={`http://mali-wielcy.pl:2317/Polesie?lang=en;templ=templd;m=A;i=${person.externalId};oc=1;siblings=on;notes=on;t=T;image=on;v=5`}
                     className="btn btn-outline-secondary btn-sm" target="_blank" rel="noopener noreferrer">View Ancestors Tree</a>
                </div>
              ) : <p className="text-muted text-center">No GenWeb link available</p>}

              <ListGroup variant="flush">
                <ListGroup.Item><strong>Bio Note:</strong> {person.bioNote || "N/A"}</ListGroup.Item>
                <ListGroup.Item><strong>Gender:</strong> {person.gender || "N/A"}</ListGroup.Item>
                <ListGroup.Item><strong>GenWeb ID:</strong> {person.externalId || "N/A"}</ListGroup.Item>
                <ListGroup.Item><strong>Social Status:</strong> {socialStatusLabels[person.socialStatus] || "Unknown"}</ListGroup.Item>
                <ListGroup.Item><strong>Cause of Death:</strong> {causeOfDeathLabels[person.causeOfDeath] || "N/A"}</ListGroup.Item>
              </ListGroup>
            </Card.Body>
          </Card>

          <Card className="mb-4 shadow-sm">
            <Card.Header as="h5" className="bg-warning text-white">Dates & Places</Card.Header>
            <Card.Body>
              <ListGroup variant="flush">
                {["birth", "baptism", "death", "burial"].map((type) => (
                  <React.Fragment key={type}>
                    <ListGroup.Item>
                      <strong>{type[0].toUpperCase() + type.slice(1)} Place:</strong>{" "}
                      {person[`${type}Place`] ? (
                        <Link to={`/locations/show/${person[`${type}Place`]._id || person[`${type}Place`].id}`}>
                          {person[`${type}Place`].locationName}
                        </Link>
                      ) : "N/A"}
                    </ListGroup.Item>

                    <ListGroup.Item className="pb-3 border-bottom d-flex align-items-center justify-content-between">
                      <div>
                        <strong>{type[0].toUpperCase() + type.slice(1)} Date:</strong>{" "}
                        {formatPartialDate(person[`${type}Year`], person[`${type}Month`], person[`${type}Day`])}
                      </div>
                      <div>
                        {/* view sources (fetch filtered from backend when possible) */}
                        <Button variant="link" size="sm" onClick={() => showSources(type.toUpperCase(), personNumericId(), `${type} sources`)}>
                          📄 View
                        </Button>
                        {isAdmin && (
                          <Button variant="link" size="sm" onClick={() => handleOpenAddModal(type.toUpperCase(), personNumericId(), `Add ${type} source`)}>
                            ➕ Add
                          </Button>
                        )}
                      </div>
                    </ListGroup.Item>
                  </React.Fragment>
                ))}
              </ListGroup>
            </Card.Body>
          </Card>
        </Col>

        {/* Right Column */}
        <Col md={8}>
          <h4 className="mb-3 text-center">Relevant Information</h4>
          <Accordion defaultActiveKey={["0", "1", "2", "3"]}>
            {/* Parents */}
            <Accordion.Item eventKey="0">
              <Accordion.Header>Parent(s)</Accordion.Header>
              <Accordion.Body>
                <ListGroup variant="flush">
                  {["father", "mother"].map((p) => (
                    <ListGroup.Item key={p} className="d-flex align-items-center justify-content-between">
                      <div>
                        <strong>{p[0].toUpperCase() + p.slice(1)}:</strong>{" "}
                        {person[p] ? (
                          <Link to={`/persons/show/${person[p]._id || person[p].id}`}>{getFullName(person[p])}</Link>
                        ) : "N/A"}
                      </div>
                      <div>
                        {person[p] && <Button variant="link" size="sm" onClick={() => showSources(p.toUpperCase(), person[p].id || person[p]._id || personNumericId(), `${p} sources`)}>📄 View</Button>}
                        {isAdmin && person[p] && <Button variant="link" size="sm" onClick={() => handleOpenAddModal(p.toUpperCase(), person[p].id || person[p]._id || personNumericId(), `Add ${p} source`)}>➕ Add</Button>}
                      </div>
                    </ListGroup.Item>
                  ))}
                </ListGroup>
              </Accordion.Body>
            </Accordion.Item>

            {/* Spouses */}
            <Accordion.Item eventKey="1">
              <Accordion.Header>Spouse(s)</Accordion.Header>
              <Accordion.Body>
                {familyData.length > 0 ? (
                  <ListGroup variant="flush">
                    {familyData.map((family, idx) => {
                      const spouse = person.gender === "MALE" ? family.spouseFemale : family.spouseMale;
                      return (
                        <ListGroup.Item key={idx} className="pb-3 border-bottom d-flex justify-content-between">
                          <div>
                            <strong>Name:</strong>{" "}
                            {spouse ? <Link to={`/persons/show/${spouse._id || spouse.id}`}>{getFullName(spouse)}</Link> : "N/A"}<br />
                            <strong>Gender:</strong> {spouse?.gender || "N/A"}<br />
                            <strong>Marriage Date:</strong> {family.marriageDate || "N/A"}<br />
                            <strong>Marriage Location:</strong> {family.marriageLocation?.locationName || "N/A"}
                          </div>
                          <div className="text-end">
                            <Button variant="link" size="sm" onClick={() => showSources("MARRIAGE", family.id, "Marriage sources")}>📄 View</Button>
                            {isAdmin && <Button variant="link" size="sm" onClick={() => handleOpenAddModal("MARRIAGE", family.id, "Add marriage source")}>➕ Add</Button>}
                          </div>
                        </ListGroup.Item>
                      );
                    })}
                  </ListGroup>
                ) : <p className="text-muted">No spouse data available.</p>}
              </Accordion.Body>
            </Accordion.Item>

            {/* Children */}
            <Accordion.Item eventKey="2">
              <Accordion.Header>Children</Accordion.Header>
              <Accordion.Body>
                {childs.length > 0 ? (
                  <ListGroup variant="flush">
                    {childs.map((child, index) => (
                      <ListGroup.Item key={index} className="pb-3 border-bottom d-flex justify-content-between">
                        <div>
                          <strong>Name:</strong>{" "}
                          <Link to={`/persons/show/${child._id || child.id}`}>{getFullName(child)}</Link><br />
                          <strong>Gender:</strong> {child.gender || "N/A"}<br />
                          <strong>Birth Date:</strong> {formatPartialDate(child.birthYear, child.birthMonth, child.birthDay)}
                        </div>
                        <div className="text-end">
                          <Button variant="link" size="sm" onClick={() => showSources("CHILD_BIRTH", child.id || child._id, "Child birth sources")}>📄 View</Button>
                          {isAdmin && <Button variant="link" size="sm" onClick={() => handleOpenAddModal("CHILD_BIRTH", child.id || child._id, "Add child birth source")}>➕ Add</Button>}
                        </div>
                      </ListGroup.Item>
                    ))}
                  </ListGroup>
                ) : <p className="text-muted">No children available.</p>}
              </Accordion.Body>
            </Accordion.Item>

            {/* Occupations */}
            <Accordion.Item eventKey="3">
              <Accordion.Header>Occupation(s)</Accordion.Header>
              <Accordion.Body>
                {occupations.length > 0 ? (
                  <ListGroup variant="flush">
                    {occupations.map((occ, idx) => (
                      <ListGroup.Item key={idx} className="pb-3 border-bottom d-flex justify-content-between">
                        <div>
                          <strong>Occupation:</strong>{" "}
                          {occ.occupationId ? <Link to={`/occupations/show/${occ.occupationId}`}>{occ.occupationName || "Occupation"}</Link> : occ.occupationName || "N/A"}<br />
                          <strong>Institution:</strong>{" "}
                          {occ.institutionId ? <Link to={`/institutions/show/${occ.institutionId}`}>{occ.institutionName || "Institution"}</Link> : occ.institutionName || "N/A"}<br />
                          <strong>Institution Location:</strong>{" "}
                          {occ.institutionLocationId ? <Link to={`/locations/show/${occ.institutionLocationId}`}>{occ.institutionLocationName || "N/A"}</Link> : occ.institutionLocationName || "N/A"}<br />
                          <strong>Start Date:</strong> {occ.startYear || "N/A"}<br />
                          <strong>End Date:</strong> {occ.endYear || "N/A"}
                        </div>
                        <div className="text-end">
                          <Button variant="link" size="sm" onClick={() => showSources("OCCUPATION", occ.id, "Occupation sources")}>📄 View</Button>
                          {isAdmin && <Button variant="link" size="sm" onClick={() => handleOpenAddModal("OCCUPATION", occ.id, "Add occupation source")}>➕ Add</Button>}
                        </div>
                      </ListGroup.Item>
                    ))}
                  </ListGroup>
                ) : <p className="text-muted">No occupations available.</p>}
              </Accordion.Body>
            </Accordion.Item>

            {/* Military */}
            {String(person.gender || "").toUpperCase() === "MALE" && (
              <Accordion.Item eventKey="4">
                <Accordion.Header>Military Service</Accordion.Header>
                <Accordion.Body>
                  {militaryServices.length > 0 ? (
                    <ListGroup variant="flush">
                      {militaryServices.map((service, index) => (
                        <ListGroup.Item key={index} className="pb-3 border-bottom d-flex justify-content-between">
                          <div>
                            <strong>Unit:</strong>{" "}
                            {service.militaryStructure?._id ? (
                              <Link to={`/militaryStructures/show/${service.militaryStructure._id}`}>{service.militaryStructure.unitName || "N/A"}</Link>
                            ) : "N/A"}<br />
                            <strong>Rank:</strong>{" "}
                            {service.militaryRank?._id ? <Link to={`/militaryRanks/show/${service.militaryRank._id}`}>{service.militaryRank.rankName || "N/A"}</Link> : "N/A"}<br />
                            <strong>Army:</strong>{" "}
                            {service.militaryStructure?.organization?._id ? <Link to={`/militaryOrganizations/show/${service.militaryStructure.organization._id}`}>{service.militaryStructure.organization.armyName || "N/A"}</Link> : "N/A"}<br />
                            <strong>Branch:</strong> {service.militaryStructure?.organization?.armyBranch?.armyBranchName || "N/A"}<br />
                            <strong>Country:</strong>{" "}
                            {service.militaryStructure?.organization?.country?._id ? <Link to={`/countries/show/${service.militaryStructure.organization.country._id}`}>{service.militaryStructure.organization.country.countryNameInPolish || "N/A"}</Link> : "N/A"}<br />
                            <strong>Enlistment Year:</strong> {service.enlistmentYear || "N/A"}<br />
                            <strong>Discharge Year:</strong> {service.dischargeYear || "N/A"}<br />
                            <strong>Notes:</strong> {service.notes || "N/A"}
                          </div>
                          <div className="text-end">
                            <Button variant="link" size="sm" onClick={() => showSources("MILITARY", service.id, "Military service sources")}>📄 View</Button>
                            {isAdmin && <Button variant="link" size="sm" onClick={() => handleOpenAddModal("MILITARY", service.id, "Add military source")}>➕ Add</Button>}
                          </div>
                        </ListGroup.Item>
                      ))}
                    </ListGroup>
                  ) : <p className="text-muted">No military service records available.</p>}
                </Accordion.Body>
              </Accordion.Item>
            )}
          </Accordion>
        </Col>
      </Row>

      {/* View Sources Modal */}
      <Modal show={viewModalVisible} onHide={() => setViewModalVisible(false)} size="lg">
        <Modal.Header closeButton>
          <Modal.Title>{viewModalTitle}</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          {viewModalSources.length ? (
            <ListGroup variant="flush">
              {viewModalSources.map((entry, idx) => {
                const attr = entry.attribution || entry;
                const src = entry.source || entry.source || null;
                return (
                  <ListGroup.Item key={idx}>
                    <strong>{src?.title ?? `Source #${attr.sourceId}`}</strong>
                    {attr.note && <div><em>{attr.note}</em></div>}
                    {/* optionally render more source metadata if available */}
                    {src?.reference && <div><small>Reference: {src.reference}</small></div>}
                    {src?.repository && <div><small>Repository: {src.repository}</small></div>}
                  </ListGroup.Item>
                );
              })}
            </ListGroup>
          ) : <p>No sources available.</p>}
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={() => setViewModalVisible(false)}>Close</Button>
        </Modal.Footer>
      </Modal>

      {/* Add Source Modal */}
      <Modal show={addModalVisible} onHide={() => setAddModalVisible(false)}>
        <Form onSubmit={handleAddSourceSubmit}>
          <Modal.Header closeButton>
            <Modal.Title>Add Source — {addModalContext.label || addModalContext.type}</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <Form.Group className="mb-3">
              <Form.Label>Select Source</Form.Label>
              <Form.Select
                value={addModalSourceId}
                onChange={(e) => setAddModalSourceId(e.target.value)}
                required
              >
                <option value="">-- choose source --</option>
                {(sourcesList || []).map((src) => (
                  <option key={src._id || src.id} value={src._id || src.id}>
                    {src.title || src.sourceTitle || `#${src._id || src.id}`}
                  </option>
                ))}
              </Form.Select>
            </Form.Group>

            <Form.Group>
              <Form.Label>Note (optional)</Form.Label>
              <Form.Control as="textarea" rows={3} value={addModalNote} onChange={(e) => setAddModalNote(e.target.value)} />
            </Form.Group>
          </Modal.Body>

          <Modal.Footer>
            <Button variant="secondary" onClick={() => setAddModalVisible(false)}>Cancel</Button>
            <Button type="submit" variant="primary" disabled={submitting}>
              {submitting ? "Adding..." : "Add Source"}
            </Button>
          </Modal.Footer>
        </Form>
      </Modal>
    </Container>
  );
};

export default PersonDetail;
