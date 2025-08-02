import React, { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { Container, Row, Col, Card, ListGroup, Alert, Spinner, Accordion } from "react-bootstrap";

import { apiGet } from "../utils/api";
import socialStatusLabels from "../constants/socialStatusLabels";
import causeOfDeathLabels from "../constants/causeOfDeathLabels";

// Helper function to format partial dates
const formatPartialDate = (year, month, day) => {
    if (year === null || year === undefined) {
        return "Unknown"; // Or an empty string, depending on desired display
    }

    let dateParts = [year];
    if (month !== null && month !== undefined) {
        dateParts.push(String(month).padStart(2, '0')); // Pad month with leading zero
        if (day !== null && day !== undefined) {
            dateParts.push(String(day).padStart(2, '0')); // Pad day with leading zero
        }
    }
    return dateParts.join('-');
};

const PersonDetail = () => {
    const { id } = useParams();

    const [person, setPerson] = useState({});
    const [parents, setParents] = useState([]);
    const [childs, setChilds] = useState([]);
    const [spouses, setSpouses] = useState([]);
    const [occupations, setOccupations] = useState([]);
    const [militaryServices, setMilitaryServices] = useState([]);
    const [sourceEvidences, setSourceEvidences] = useState([]);
    const [familyData, setFamilyData] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    // Helper to get full name, handling potential nulls
    const getFullName = (p) =>
        p ? `${p.givenName || ""} ${p.givenSurname || ""}`.trim() : "N/A";

    useEffect(() => {
        const fetchData = async () => {
            try {
                const [
                    personData,
                    occupationData,
                    families,
                    evidences,
                    childrenData,
                    militaryServiceData
                ] = await Promise.all([
                    apiGet(`/api/persons/${id}`),
                    apiGet(`/api/persons/${id}/occupations`),
                    apiGet(`/api/persons/${id}/families`),
                    apiGet(`/api/persons/${id}/sourceEvidences`),
                    apiGet(`/api/persons/${id}/children`),
                    apiGet(`/api/personMilitaryServices?personId=${id}`)
                ]);

                setPerson(personData);
                setOccupations(occupationData);
                setFamilyData(families);
                setSourceEvidences(evidences);
                setMilitaryServices(militaryServiceData);

                // Logic to determine spouses from family data
                const fetchedSpouses = families
                    .map(family => {
                        if (family.spouseMale?.id === parseInt(id)) return family.spouseFemale;
                        if (family.spouseFemale?.id === parseInt(id)) return family.spouseMale;
                        return null;
                    })
                    .filter(Boolean); // Filter out nulls
                setSpouses(fetchedSpouses);

                // Logic to determine parents from family data
                const parentFamilies = families.filter(f =>
                    f.childs?.some(child => child.id === parseInt(id))
                );
                const fetchedParents = parentFamilies.map(f => ({
                    father: f.father,
                    mother: f.mother
                }));
                setParents(fetchedParents); // Set parents state

                setChilds(childrenData);
            } catch (err) {
                setError(`Error loading data: ${err.message || err}`);
            } finally {
                setLoading(false);
            }
        };

        fetchData();
    }, [id]); // Re-run effect when ID changes

    if (loading) {
        return (
            <Container className="mt-5 text-center">
                <Spinner animation="border" role="status" />
                <p>Loading...</p>
            </Container>
        );
    }
    if (error) return <Alert variant="danger">{error}</Alert>;

    return (
        <Container className="mt-5">
            <h1 className="mb-4 text-center">Person Details: {getFullName(person)}</h1>
            <Row className="mb-4">
                {/* Left Column - Basic Information */}
                <Col md={4}>
                    <Card className="mb-4 shadow-sm">
                        <Card.Header as="h5" className="bg-primary text-white">Basic Information</Card.Header>
                        <Card.Body>
                            <Card.Title className="text-center mb-3">{getFullName(person)}</Card.Title>

                            {/* GenWeb links based on identification number */}
                            {person.identificationNumber ? (
                                <div className="mb-3 d-flex flex-column gap-2">
                                    <a
                                        href={`http://mali-wielcy.pl:2317/Polesie?lang=en;templ=templd;m=D;i=${person.identificationNumber};oc=1;siblings=on;notes=on;t=T;image=on;v=5`}
                                        className="btn btn-outline-primary btn-sm"
                                        target="_blank"
                                        rel="noopener noreferrer"
                                    >
                                        View Descendants Tree
                                    </a>
                                    <a
                                        href={`http://mali-wielcy.pl:2317/Polesie?lang=en;templ=templd;m=A;i=${person.identificationNumber};oc=1;siblings=on;notes=on;t=T;image=on;v=5`}
                                        className="btn btn-outline-secondary btn-sm"
                                        target="_blank"
                                        rel="noopener noreferrer"
                                    >
                                        View Ancestors Tree
                                    </a>
                                </div>
                            ) : (
                                <p className="text-muted text-center">No GenWeb link available</p>
                            )}

                            {/* Person details list */}
                            <ListGroup variant="flush">
                                <ListGroup.Item><strong>Note:</strong> {person.note || "N/A"}</ListGroup.Item>
                                <ListGroup.Item><strong>Gender:</strong> {person.gender || "N/A"}</ListGroup.Item>
                                <ListGroup.Item><strong>GenWeb ID:</strong> {person.identificationNumber || "N/A"}</ListGroup.Item>
                                <ListGroup.Item><strong>Social Status:</strong> {socialStatusLabels[person.socialStatus] || "Unknown"}</ListGroup.Item>
                                <ListGroup.Item>
                                    <strong>Cause of Death:</strong> {causeOfDeathLabels[person.causeOfDeath] || "N/A"}
                                </ListGroup.Item>
                            </ListGroup>
                        </Card.Body>
                    </Card>

                    {/* Dates and Places Card (consolidated) */}
                    <Card className="mb-4 shadow-sm">
                        <Card.Header as="h5" className="bg-warning text-white">Dates & Places</Card.Header>
                        <Card.Body>
                            <ListGroup variant="flush">
                                {["birth", "baptization", "death", "burial"].map(type => (
                                    <React.Fragment key={type}>
                                        <ListGroup.Item>
                                            <strong>{`${type[0].toUpperCase() + type.slice(1)} Place:`}</strong>{" "}
                                            {person[`${type}Place`] ? (
                                                <Link to={`/locations/show/${person[`${type}Place`]._id}`}>
                                                    {person[`${type}Place`].locationName}
                                                </Link>
                                            ) : "N/A"}
                                        </ListGroup.Item>
                                        <ListGroup.Item className="pb-3 border-bottom">
                                            <strong>{`${type[0].toUpperCase() + type.slice(1)} Date:`}</strong>{" "}
                                            {/* Use formatPartialDate for all date types */}
                                            {formatPartialDate(
                                                person[`${type}Year`],
                                                person[`${type}Month`],
                                                person[`${type}Day`]
                                            )}
                                        </ListGroup.Item>
                                    </React.Fragment>
                                ))}
                            </ListGroup>
                        </Card.Body>
                    </Card>
                </Col>

                {/* Right Column - Relations, Occupations, Military, Sources (now in Accordion) */}
                <Col md={8}>
                    <h4 className="mb-3 text-center">Relevant Information</h4> {/* Added header here */}
                    {/* defaultActiveKey can be set to the eventKey of the item you want open by default */}
                    <Accordion defaultActiveKey={["0", "1", "2"]}>
                        {/* Parents Card */}
                        <Accordion.Item eventKey="0">
                            <Accordion.Header>Parent(s)</Accordion.Header>
                            <Accordion.Body>
                                <ListGroup variant="flush">
                                    <ListGroup.Item>
                                        <strong>Father:</strong>{" "}
                                        {person.father
                                            ? <Link to={`/persons/show/${person.father._id || person.father.id}`}>{getFullName(person.father)}</Link>
                                            : "N/A"}
                                    </ListGroup.Item>
                                    <ListGroup.Item>
                                        <strong>Mother:</strong>{" "}
                                        {person.mother
                                            ? <Link to={`/persons/show/${person.mother._id || person.mother.id}`}>{getFullName(person.mother)}</Link>
                                            : "N/A"}
                                    </ListGroup.Item>
                                </ListGroup>
                            </Accordion.Body>
                        </Accordion.Item>

                        {/* Spouses Card */}
                        <Accordion.Item eventKey="1">
                            <Accordion.Header>Spouse(s)</Accordion.Header>
                            <Accordion.Body>
                                {familyData.length > 0 ? (
                                    <ListGroup variant="flush">
                                        {familyData.map((family, index) => {
                                            let spouse = null;
                                            if (person.gender === "MALE") {
                                                spouse = family.spouseFemale;
                                            } else if (person.gender === "FEMALE") {
                                                spouse = family.spouseMale;
                                            }
                                            return (
                                                <ListGroup.Item key={index} className="pb-3 border-bottom">
                                                    <strong>Name:</strong>{" "}
                                                    {spouse
                                                        ? <Link to={`/persons/show/${spouse._id || spouse.id}`}>{getFullName(spouse)}</Link>
                                                        : "N/A"}<br />
                                                    <strong>Gender:</strong> {spouse?.gender || "N/A"}<br />
                                                    <strong>Marriage Date:</strong>{" "}
                                                    {/* Assuming marriageDate is still a full LocalDate string from backend */}
                                                    {family.marriageDate ? family.marriageDate : "N/A"}<br />
                                                    <strong>Marriage Location:</strong>{" "}
                                                    {family.marriageLocation?.locationName || "N/A"}
                                                </ListGroup.Item>
                                            );
                                        })}
                                    </ListGroup>
                                ) : <p className="text-muted">No spouse data available.</p>}
                            </Accordion.Body>
                        </Accordion.Item>

                        {/* Children Card */}
                        <Accordion.Item eventKey="2">
                            <Accordion.Header>Children</Accordion.Header>
                            <Accordion.Body>
                                {childs.length > 0 ? (
                                    <ListGroup variant="flush">
                                        {childs.map((child, index) => (
                                            <ListGroup.Item key={index} className="pb-3 border-bottom">
                                                <strong>Name:</strong>{" "}
                                                <Link to={`/persons/show/${child._id}`}>{getFullName(child)}</Link><br />
                                                <strong>Gender:</strong> {child.gender || "N/A"}<br />
                                                {/* Use formatPartialDate for child's birth date */}
                                                <strong>Birth Date:</strong> {formatPartialDate(child.birthYear, child.birthMonth, child.birthDay)}
                                            </ListGroup.Item>
                                        ))}
                                    </ListGroup>
                                ) : <p className="text-muted">No children available.</p>}
                            </Accordion.Body>
                        </Accordion.Item>

                        {/* Occupations Card */}
                        <Accordion.Item eventKey="3">
                            <Accordion.Header>Occupation(s)</Accordion.Header>
                            <Accordion.Body>
                                {occupations.length > 0 ? (
                                    <ListGroup variant="flush">
                                        {occupations.map((occ, index) => (
                                            <ListGroup.Item key={index} className="pb-3 border-bottom">
                                                <strong>Occupation:</strong>{" "}
                                                {occ.occupationId ? (
                                                    <Link to={`/occupations/show/${occ.occupationId}`}>
                                                        {occ.occupationName || "Occupation"}
                                                    </Link>
                                                ) : "N/A"}<br />
                                                <strong>Institution:</strong>{" "}
                                                {occ.institutionId ? (
                                                    <Link to={`/institutions/show/${occ.institutionId}`}>
                                                        {occ.institutionName || "Institution"}
                                                    </Link>
                                                ) : "N/A"}<br />
                                                <strong>Institution Location:</strong>{" "}
                                                {occ.institutionLocationId ? (
                                                    <Link to={`/locations/show/${occ.institutionLocationId}`}>
                                                        {occ.institutionLocationName || "N/A"}
                                                    </Link>
                                                ) : "N/A"}<br />
                                                {/* Assuming startDate and endDate for occupations are still full dates from backend */}
                                                <strong>Start Date:</strong> {occ.startDate ? occ.startDate : "N/A"}<br />
                                                <strong>End Date:</strong> {occ.endDate ? occ.endDate : "N/A"}
                                            </ListGroup.Item>
                                        ))}
                                    </ListGroup>
                                ) : <p className="text-muted">No occupations available.</p>}
                            </Accordion.Body>
                        </Accordion.Item>

                        {/* Military Service Card (only for males) */}
                        {person.gender === "MALE" && (
                            <Accordion.Item eventKey="4">
                                <Accordion.Header>Military Service</Accordion.Header>
                                <Accordion.Body>
                                    {militaryServices.length > 0 ? (
                                        <ListGroup variant="flush">
                                            {militaryServices.map((service, index) => (
                                                <ListGroup.Item key={index} className="pb-3 border-bottom">
                                                    <strong>Unit:</strong>{" "}
                                                    {service.militaryStructure?._id ? (
                                                        <Link to={`/militaryStructures/show/${service.militaryStructure._id}`}>
                                                            {service.militaryStructure.unitName || "N/A"}
                                                        </Link>
                                                    ) : "N/A"}
                                                    <br />

                                                    <strong>Rank:</strong>{" "}
                                                    {service.militaryRank?._id ? (
                                                        <Link to={`/militaryRanks/show/${service.militaryRank._id}`}>
                                                            {service.militaryRank.rankName || "N/A"}
                                                        </Link>
                                                    ) : "N/A"}
                                                    <br />

                                                    <strong>Army:</strong>{" "}
                                                    {service.militaryStructure?.organization?._id ? (
                                                        <Link to={`/militaryOrganizations/show/${service.militaryStructure.organization._id}`}>
                                                            {service.militaryStructure.organization.armyName || "N/A"}
                                                        </Link>
                                                    ) : "N/A"}
                                                    <br />

                                                    <strong>Branch:</strong>{" "}
                                                    {service.militaryStructure?.organization?.armyBranch?.armyBranchName || "N/A"}
                                                    <br />

                                                    <strong>Country:</strong>{" "}
                                                    {service.militaryStructure?.organization?.country?._id ? (
                                                        <Link to={`/countries/show/${service.militaryStructure.organization.country._id}`}>
                                                            {service.militaryStructure.organization.country.countryNameInPolish || "N/A"}
                                                        </Link>
                                                    ) : "N/A"}
                                                    <br />

                                                    <strong>Enlistment Year:</strong> {service.enlistmentYear || "N/A"}<br />
                                                    <strong>Discharge Year:</strong> {service.dischargeYear || "N/A"}<br />
                                                    <strong>Notes:</strong> {service.notes || "N/A"}
                                                </ListGroup.Item>
                                            ))}
                                        </ListGroup>
                                    ) : <p className="text-muted">No military service records available.</p>}
                                </Accordion.Body>
                            </Accordion.Item>
                        )}

                        {/* Source Evidences Card */}
                        <Accordion.Item eventKey="5">
                            <Accordion.Header>Source Evidences</Accordion.Header>
                            <Accordion.Body>
                                {sourceEvidences.length > 0 ? (
                                    <ListGroup variant="flush">
                                        {sourceEvidences.map((evidence, index) => (
                                            <ListGroup.Item key={index} className="pb-3 border-bottom">
                                                <strong>Evidence Type:</strong> {evidence.evidenceType || "N/A"}<br />
                                                <strong>Source:</strong>{" "}
                                                {evidence.sourceName && evidence.sourceId ? (
                                                    <Link to={`/sources/show/${evidence.sourceId}`}>
                                                        {evidence.sourceName}
                                                    </Link>
                                                ) : "N/A"}
                                            </ListGroup.Item>
                                        ))}
                                    </ListGroup>
                                ) : <p className="text-muted">No source evidence available.</p>}
                            </Accordion.Body>
                        </Accordion.Item>
                    </Accordion>
                </Col>
            </Row>
        </Container>
    );
};

export default PersonDetail;
