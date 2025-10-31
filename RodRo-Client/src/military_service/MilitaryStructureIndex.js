import React, { useEffect, useState, useCallback } from "react";
import { apiGet, apiDelete } from "../utils/api";
import { 
  Container, Row, Col, Alert, Spinner, Button, Card, 
  Nav, Tab 
} from "react-bootstrap"; 
import { Link } from "react-router-dom";
import { useSession } from "../contexts/session";
import MilitaryStructureTable from "./MilitaryStructureTable";
import "@fortawesome/fontawesome-free/css/all.min.css";

const MilitaryStructureIndex = () => {
  const { session } = useSession();
  const isAdmin = session.data?.isAdmin === true;

  const [structures, setStructures] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [successMessage, setSuccessMessage] = useState(null);
  // Default to a general modern unit size for the initial view
  const [activeUnit, setActiveUnit] = useState("BRIGADE");

  const fetchStructures = useCallback(async () => {
    setLoading(true);
    setError(null);
    try {
      // Assuming you fetch general unit size data from this endpoint
      const data = await apiGet("/api/militaryStructures");
      setStructures(data);
    } catch (err) {
      console.error("Error fetching military structures:", err);
      setError("Failed to load military structure records. Please try again later.");
    } finally {
      setLoading(false);
    }
  }, []);

  const deleteStructure = useCallback(async (id) => {
    if (!window.confirm("CONFIRMATION: Deleting this structure record will remove all associated data. Proceed?")) return;
    setError(null);
    setSuccessMessage(null);
    try {
      await apiDelete(`/api/militaryStructures/${id}`);
      setStructures(prev => prev.filter(item => item._id !== id));
      setSuccessMessage("Military structure deleted successfully.");
    } catch (err) {
      console.error("Error deleting military structure:", err);
      setError("Authorization Error: Failed to delete the record.");
    }
  }, []);

  useEffect(() => {
    fetchStructures();
  }, [fetchStructures]);


  // --- Contextual cards (Adapted) ---
  const structureFrameworkDescriptions = [
    {
      title: "Unit Standardization",
      icon: "fas fa-ruler-combined",
      text: "Defines the fixed or nominal size of a unit (e.g., 500-2,000 personnel) for planning."
    },
    {
      title: "Functional Role",
      icon: "fas fa-project-diagram",
      text: "Units are categorized by their mission: tactical maneuver, logistical support, or strategic command."
    },
    {
      title: "Historical Relativity",
      icon: "fas fa-map-marked-alt",
      text: "The exact size and role of a unit (e.g., 'Regiment') change drastically based on nation and century."
    }
  ];

  // --- Core Administrative/Tactical Units (Focus on Regiment, Brigade, Legion, etc.) ---
  const unitDescriptions = [
    {
      key: "SQUAD_PLATOON",
      name: "Squad / Platoon",
      icon: "fas fa-users",
      description: "The smallest tactical units. The Squad (8-12 men) is the basic fire team, while the Platoon (3-4 Squads) is led by a junior officer.",
      details: [
        { era: "Modern NATO", size: "10-40 personnel", context: "Used for direct fire and localized combat tasks." }
      ],
      historicalNote: "This size concept has been consistent across most modern armies since WWI."
    },
    {
      key: "COMPANY_BATTERY",
      name: "Company / Battery",
      icon: "fas fa-building",
      description: "An administrative and tactical unit (80-250 personnel). A Battery is the equivalent for Artillery.",
      details: [
        { era: "Modern US Army", size: "80-150 personnel (3-5 platoons)", context: "A Company is commanded by a Captain, often the lowest command level with integrated support." }
      ],
      historicalNote: "The Company has been a key administrative unit since the 17th century."
    },
    {
      key: "BATTALION",
      name: "Battalion / Squadron",
      icon: "fas fa-flag",
      description: "A major tactical and administrative unit (300-1,000 personnel). Capable of independent operations for short periods.",
      details: [
        { era: "Modern Infantry", size: "500-1,000 personnel", context: "Led by a Lieutenant Colonel. Often consists of 3-5 companies plus a headquarters element." }
      ],
      historicalNote: "First formalized in the late 16th century, representing a significant grouping of troops."
    },
    {
      key: "REGIMENT_PULK",
      name: "Regiment / Pułk",
      icon: "fas fa-shield-alt",
      description: "Historically a primary administrative unit. Its size varies wildly (500–5,000+ men), often representing a unit's lineage.",
      details: [
        { era: "17th Century Europe", size: "1,000-2,000 men", context: "Often commanded by a Colonel and was the unit of ownership/patronage." },
        { era: "Modern NATO", size: "2,000-5,000 men (Often ceremonial/administrative)", context: "Often exists as an administrative grouping of several battalions, or a large brigade." },
        { era: "Polish/Slavic (Pułk)", size: "Variable", context: "Equivalent to a Regiment, historically used in Polish, Cossack, and Russian forces." }
      ],
      historicalNote: "The Regiment's role has largely been superseded by the Brigade/Division in tactical command, but remains a cultural and administrative center."
    },
    {
      key: "BRIGADE",
      name: "Brigade",
      icon: "fas fa-fire-extinguisher",
      description: "A major, flexible tactical formation (3,000–5,000+ personnel). Designed to be self-sufficient in combat for extended periods.",
      details: [
        { era: "Modern Army", size: "3,000-5,000 personnel", context: "Led by a Colonel or Brigadier General. It is the primary combined-arms maneuver unit." },
        { era: "Historical Use", size: "2-4 Regiments", context: "First used in the 17th century to group multiple regiments under a single commander for battle." }
      ],
      historicalNote: "The Brigade is the key fighting unit in many modern armies, often tailored for specific missions (Stryker, Armor, Infantry)."
    },
    {
      key: "LEGION",
      name: "Legion",
      icon: "fas fa-gavel",
      description: "A historic unit, primarily associated with Ancient Rome, but also used by France and Spain for foreign troops.",
      details: [
        { era: "Roman Republic/Empire", size: "4,200–6,000 men (Infantry & Cavalry)", context: "The fundamental operational unit of the Roman Army, commanded by a Legate." },
        { era: "French Foreign Legion", size: "Regiment/Brigade equivalent", context: "Modern unit name used to designate elite foreign-recruiting forces." }
      ],
      historicalNote: "The Roman Legion was an exceptionally organized, self-sufficient, and effective formation whose structure influenced centuries of military thought."
    },
    {
      key: "DIVISION_CORPS",
      name: "Division / Corps",
      icon: "fas fa-globe-americas",
      description: "Large, strategic formations. The Division (10,000–20,000) is led by a Major General; the Corps (2-5 Divisions) by a Lieutenant General.",
      details: [
        { era: "20th Century", size: "10,000-40,000 personnel", context: "Divisions were the primary maneuver unit of WWII; the Corps commands a major operational sector." }
      ],
      historicalNote: "The concept of a Division as an independent force was pioneered by the French in the 18th century."
    },
  ];

  if (loading) return (
    <Container className="text-center my-5 py-5">
      <Spinner animation="border" variant="primary" />
      <p className="mt-3 text-muted">Loading military structure ontology...</p>
    </Container>
  );

  return (
    <Container className="my-5 py-3">
      <header className="text-center mb-5">
        <h1 className="display-5 fw-bold text-dark mb-1">
          <i className="fas fa-sitemap me-3 text-danger"></i>Military Structure Ontology
        </h1>
        <p className="lead text-secondary fst-italic mb-4 mx-auto" style={{ maxWidth: "800px" }}>
          A cross-temporal and cross-cultural index of key military administrative and tactical units,
          detailing their nominal size and functional role across history.
        </p>
        {isAdmin && (
          <div className="d-flex justify-content-center my-4">
            <Link to="/militaryStructures/create" className="btn btn-danger btn-lg rounded-pill px-5 py-2 fw-semibold shadow-lg">
              <i className="fas fa-plus-circle me-2"></i>Create New Structure Record
            </Link>
          </div>
        )}
      </header>

      {/* Framework Cards */}
      <Row className="mb-5 justify-content-center">
        <Col md={10}>
          <h2 className="fw-bold text-dark mb-3">I. The Unit Definition Framework</h2>
          <Row className="g-4 mb-4">
            {structureFrameworkDescriptions.map((item, index) => (
              <Col md={4} key={index}>
                <Card className="h-100 shadow-sm border-start border-danger border-4 rounded-3">
                  <Card.Body className="p-3">
                    <h6 className="fw-bold text-danger mb-1">
                      <i className={`${item.icon} me-2`}></i> {item.title}
                    </h6>
                    <p className="text-muted small mb-0">{item.text}</p>
                  </Card.Body>
                </Card>
              </Col>
            ))}
          </Row>

          {/* Unit Tabs */}
          <h2 className="fw-bold text-dark mt-4 mb-3">II. Key Units Across Time and Space</h2>
          <Tab.Container id="unit-tabs" activeKey={activeUnit} onSelect={(k) => setActiveUnit(k)}>
            <Row>
              {/* Side Nav */}
              <Col md={3} className="mb-3">
                <Nav variant="pills" className="flex-column p-3 bg-light rounded-3 shadow-sm border">
                  {unitDescriptions.map((item) => (
                    <Nav.Item key={item.key}>
                      <Nav.Link eventKey={item.key} className="text-start mb-1 fw-semibold">
                        <i className={`${item.icon} me-2`}></i> {item.name}
                      </Nav.Link>
                    </Nav.Item>
                  ))}
                </Nav>
              </Col>

              {/* Content */}
              <Col md={9}>
                <Tab.Content className="p-4 bg-white rounded-3 shadow border">
                  {unitDescriptions.map((item) => (
                    <Tab.Pane key={item.key} eventKey={item.key}>
                      <h4 className="fw-bold text-dark border-bottom pb-2 mb-3">{item.name} Definition</h4>
                      <p className="text-muted mb-4">{item.description}</p>
                      
                      <h5 className="fw-bold text-danger mb-3">Contextual Roles and Sizes:</h5>
                      <Row className="g-3">
                        {item.details.map((level, i) => (
                          <Col md={6} key={i}>
                            <Card className="h-100 border-0 border-start border-4 border-secondary-subtle">
                              <Card.Body className="p-3">
                                <h6 className="mb-1 text-dark">
                                  <i className="fas fa-calendar-alt me-2 text-secondary"></i>
                                  <strong>{level.era}</strong>
                                </h6>
                                <p className="small text-muted mb-1">Size: {level.size}</p>
                                <p className="small text-muted mb-0">Role: {level.context}</p>
                              </Card.Body>
                            </Card>
                          </Col>
                        ))}
                      </Row>
                      
                      <p className="small fst-italic text-muted mt-4 mb-0 border-top pt-3">
                        <i className="fas fa-info-circle me-1 text-danger"></i> Historical Note: {item.historicalNote}
                      </p>
                    </Tab.Pane>
                  ))}
                </Tab.Content>
              </Col>
            </Row>
          </Tab.Container>
        </Col>
      </Row>

      <hr className="mb-5"/>

      {error && <Alert variant="danger" className="rounded-3 shadow-sm">{error}</Alert>}
      {successMessage && <Alert variant="success" className="rounded-3 shadow-sm">{successMessage}</Alert>}

      {/* Table of Structures */}
      <MilitaryStructureTable
        deleteStructure={deleteStructure}
        items={structures}
        label="Total Structures Indexed:"
      />
    </Container>
  );
};

export default MilitaryStructureIndex;
