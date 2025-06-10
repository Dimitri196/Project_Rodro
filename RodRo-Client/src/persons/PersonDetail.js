import React, { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { Container, Row, Col, Card, ListGroup, Alert } from "react-bootstrap";

import { apiGet } from "../utils/api";
import dateStringFormatter from "../utils/dateStringFormatter";
import socialStatusLabels from "../constants/socialStatusLabels";
import causeOfDeathLabels from "../constants/causeOfDeathLabels";

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

                const fetchedSpouses = families
                    .map(family => {
                        if (family.spouseMale?.id === parseInt(id)) return family.spouseFemale;
                        if (family.spouseFemale?.id === parseInt(id)) return family.spouseMale;
                        return null;
                    })
                    .filter(Boolean);
                setSpouses(fetchedSpouses);

                const parentFamilies = families.filter(f =>
                    f.childs?.some(child => child.id === parseInt(id))
                );
                const fetchedParents = parentFamilies.map(f => ({
                    father: f.father,
                    mother: f.mother
                }));
                setParents(fetchedParents);

                setChilds(childrenData);
            } catch (err) {
                setError(`Error loading data: ${err.message || err}`);
            } finally {
                setLoading(false);
            }
        };

        fetchData();
    }, [id]);

    if (loading) return <p>Loading...</p>;
    if (error) return <Alert variant="danger">{error}</Alert>;

    return (
        <Container className="mt-5">
            <Row className="mb-4">
                <Col md={4}>
                    <Card>
                        <Card.Body>


                            <Card.Title>{getFullName(person)}</Card.Title>

                            <div className="mb-3">
                                <Link to={`/family-tree/${person._id}`} className="btn btn-outline-primary btn-sm">
                                    View Family Tree
                                </Link>
                            </div>



                            <ListGroup variant="flush">
                                <ListGroup.Item><strong>Note:</strong> {person.note || "N/A"}</ListGroup.Item>
                                <ListGroup.Item><strong>Gender:</strong> {person.gender || "N/A"}</ListGroup.Item>
                                <ListGroup.Item><strong>GenWeb ID:</strong> {person.identificationNumber || "N/A"}</ListGroup.Item>
                                <ListGroup.Item><strong>Social Status:</strong> {socialStatusLabels[person.socialStatus] || "Unknown"}</ListGroup.Item>
                                <ListGroup.Item>
                                    <strong>Cause of Death:</strong> {causeOfDeathLabels[person.causeOfDeath] || "N/A"}
                                </ListGroup.Item>
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
                                        <ListGroup.Item>
                                            <strong>{`${type[0].toUpperCase() + type.slice(1)} Date:`}</strong>{" "}
                                            {person[`${type}Date`] ? dateStringFormatter(person[`${type}Date`], true) : "N/A"}
                                        </ListGroup.Item>
                                    </React.Fragment>
                                ))}
                            </ListGroup>
                        </Card.Body>
                    </Card>
                </Col>

                <Col md={8}>
                    {/* Parents */}
                    <Card className="mb-4">
                        <Card.Body>
                            <Card.Title>Parent(s)</Card.Title>
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
                        </Card.Body>
                    </Card>

                    {/* Spouses */}
                    <Card className="mb-4">
                        <Card.Body>
                            <Card.Title>Spouse(s)</Card.Title>
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
                                            <ListGroup.Item key={index}>
                                                <strong>Name:</strong>{" "}
                                                {spouse
                                                    ? <Link to={`/persons/show/${spouse._id || spouse.id}`}>{getFullName(spouse)}</Link>
                                                    : "N/A"}<br />
                                                <strong>Gender:</strong> {spouse?.gender || "N/A"}<br />
                                                <strong>Marriage Date:</strong>{" "}
                                                {family.marriageDate ? dateStringFormatter(family.marriageDate, true) : "N/A"}<br />
                                                <strong>Marriage Location:</strong>{" "}
                                                {family.marriageLocation?.locationName || "N/A"}
                                            </ListGroup.Item>
                                        );
                                    })}
                                </ListGroup>
                            ) : <p>No spouse data available.</p>}
                        </Card.Body>
                    </Card>

                    {/* Children */}
                    <Card className="mb-4">
                        <Card.Body>
                            <Card.Title>Children</Card.Title>
                            {childs.length > 0 ? (
                                <ListGroup variant="flush">
                                    {childs.map((child, index) => (
                                        <ListGroup.Item key={index}>
                                            <strong>Name:</strong>{" "}
                                            <Link to={`/persons/show/${child._id}`}>{getFullName(child)}</Link><br />
                                            <strong>Gender:</strong> {child.gender}<br />
                                            <strong>Birth Date:</strong> {child.birthDate && dateStringFormatter(child.birthDate, true)}
                                        </ListGroup.Item>
                                    ))}
                                </ListGroup>
                            ) : <p>No children available.</p>}
                        </Card.Body>
                    </Card>

                    {/* Occupations */}
                    <Card className="mb-4">
                        <Card.Body>
                            <Card.Title>Occupation(s)</Card.Title>
                            {occupations.length > 0 ? (
                                <ListGroup variant="flush">
                                    {occupations.map((occ, index) => (
                                        <ListGroup.Item key={index}>
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
                                            <strong>Start Date:</strong> {occ.startDate && dateStringFormatter(occ.startDate, true)}<br />
                                            <strong>End Date:</strong> {occ.endDate && dateStringFormatter(occ.endDate, true)}
                                        </ListGroup.Item>
                                    ))}
                                </ListGroup>
                            ) : <p>No occupations available.</p>}
                        </Card.Body>
                    </Card>

                    {/* Military Service */}
                    {person.gender === "MALE" && (
                        <Card className="mb-4">
                            <Card.Body>
                                <Card.Title>Military Service</Card.Title>
                                {militaryServices.length > 0 ? (
                                    <ListGroup variant="flush">
                                        {militaryServices.map((service, index) => (
                                            <ListGroup.Item key={index}>
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
                                ) : <p>No military service records available.</p>}
                            </Card.Body>
                        </Card>
                    )}


                    {/* Source Evidences */}
                    <Card className="mb-4">
                        <Card.Body>
                            <Card.Title>Source(s)</Card.Title>
                            {sourceEvidences.length > 0 ? (
                                <ListGroup variant="flush">
                                    {sourceEvidences.map((evidence, index) => (
                                        <ListGroup.Item key={index}>
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
                            ) : <p>No source evidence available.</p>}
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default PersonDetail;
