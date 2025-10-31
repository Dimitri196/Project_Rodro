import React, { useEffect, useState } from "react";
import { apiGet, apiDelete } from "../utils/api";
import { 
  Container, Row, Col, Alert, Spinner, Button, Card, 
  Nav, Tab 
} from "react-bootstrap"; 
import { Link } from "react-router-dom";
import { useSession } from "../contexts/session";
import MilitaryOrganizationTable from "./MilitaryOrganizationTable";
import "@fortawesome/fontawesome-free/css/all.min.css"; 

const MilitaryOrganizationIndex = () => {
  const { session } = useSession();
  const isAdmin = session.data?.isAdmin === true;

  const [organizations, setOrganizations] = useState([]);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);
  const [activeBranch, setActiveBranch] = useState("INFANTRY");

  const fetchOrganizations = async () => {
    try {
      const data = await apiGet("/api/militaryOrganizations");
      setOrganizations(data);
      setError(null);
    } catch (err) {
      console.error("Error fetching military organizations:", err);
      setError("Failed to load military organizations. Please try again later.");
    } finally {
      setLoading(false);
    }
  };

  const deleteOrganization = async (id) => {
    if (!window.confirm("CONFIRMATION: Deleting this record will remove all associated data. Proceed?")) return;
    try {
      await apiDelete(`/api/militaryOrganizations/${id}`);
      setOrganizations((prev) => prev.filter((item) => item._id !== id));
      setError(null);
    } catch (err) {
      console.error("Error deleting military organization:", err);
      setError("Authorization Error: Failed to delete the record.");
    }
  };

  useEffect(() => {
    fetchOrganizations();
  }, []);

  // --- Contextual cards ---
  const militaryStructureDescriptions = [
    {
      title: "Strategic Framework",
      icon: "fas fa-chess-rook",
      text: "Military organizations defined the backbone of state security and power projection."
    },
    {
      title: "Temporal Dynamics",
      icon: "fas fa-hourglass-half",
      text: "Active years reveal cycles of reform, mobilization, and dissolution — mirroring regime changes and wars."
    },
    {
      title: "Socio-Political Linkage",
      icon: "fas fa-users-cog",
      text: "Branches reflected tactical roles as well as ethnic, regional, and class identities."
    }
  ];

  // --- All Army Branches ---
  const branchDescriptions = [
    {
      key: "INFANTRY",
      name: "Infantry",
      icon: "fas fa-person-rifle",
      description: "Foot soldiers forming the backbone of most armies.",
      hierarchy: [
        { role: "Regiment", authority: "Several battalions" },
        { role: "Battalion", authority: "300–1000 men" },
        { role: "Company", authority: "Basic fighting unit" },
        { role: "Platoon/Squad", authority: "Smallest subdivision" }
      ],
      context: "Infantry dominated both defensive and offensive operations."
    },
    {
      key: "CAVALRY",
      name: "Cavalry",
      icon: "fas fa-horse",
      description: "Mounted troops providing mobility, raids, and shock tactics.",
      hierarchy: [
        { role: "Regiment", authority: "Major cavalry unit" },
        { role: "Squadron", authority: "Core maneuver subunit" },
        { role: "Troop", authority: "Smallest cavalry group" }
      ],
      context: "Often associated with prestige and nobility."
    },
    {
      key: "ARTILLERY",
      name: "Artillery",
      icon: "fas fa-bomb",
      description: "Operators of cannons, mortars, and heavy guns.",
      hierarchy: [
        { role: "Regiment", authority: "Commanding multiple batteries" },
        { role: "Battery", authority: "Fire unit of several guns" },
        { role: "Gun Crew", authority: "Operated each piece" }
      ],
      context: "Artillery required technical expertise and logistics."
    },
    {
      key: "ENGINEERS",
      name: "Engineers / Pioneers",
      icon: "fas fa-tools",
      description: "Troops building fortifications, bridges, and demolitions.",
      hierarchy: [
        { role: "Regiment", authority: "Responsible for major works" },
        { role: "Company", authority: "Construction & sappers" },
        { role: "Detachment", authority: "Specialized units (bridging, mining)" }
      ],
      context: "Linked civil engineering with military modernization."
    },
    {
      key: "GUARD",
      name: "Guard Units",
      icon: "fas fa-crown",
      description: "Elite formations, often protecting rulers or capitals.",
      hierarchy: [
        { role: "Guard Regiment", authority: "Elite status unit" },
        { role: "Battalion/Company", authority: "Prestige subunits" }
      ],
      context: "Guards symbolized loyalty and political legitimacy."
    },
    {
      key: "NAVAL",
      name: "Naval Forces",
      icon: "fas fa-anchor",
      description: "Fleets, squadrons, and ships securing waterways.",
      hierarchy: [
        { role: "Fleet", authority: "Strategic naval command" },
        { role: "Squadron", authority: "Operational grouping" },
        { role: "Ship Crew", authority: "Officers and sailors" }
      ],
      context: "Vital for maritime control and trade defense."
    },
    {
      key: "AIR",
      name: "Air Forces",
      icon: "fas fa-plane",
      description: "Aircraft and pilots conducting aerial operations.",
      hierarchy: [
        { role: "Air Fleet", authority: "Strategic aviation command" },
        { role: "Squadron", authority: "Core flying unit" },
        { role: "Flight", authority: "Small group of aircraft" }
      ],
      context: "Air power redefined mobility and strike capability."
    },
    {
      key: "ARMORED",
      name: "Armored / Mechanized",
      icon: "fas fa-truck-monster",
      description: "Tank and armored divisions providing shock power.",
      hierarchy: [
        { role: "Division", authority: "Large armored formation" },
        { role: "Brigade/Regiment", authority: "Combined mechanized units" },
        { role: "Company/Platoon", authority: "Tank crews" }
      ],
      context: "Mechanization transformed 20th-century battlefields."
    },
    {
      key: "SIGNALS",
      name: "Signals / Communications",
      icon: "fas fa-satellite-dish",
      description: "Communication troops handling signals, telegraphs, radios.",
      hierarchy: [
        { role: "Signal Regiment", authority: "Wide-area comms" },
        { role: "Company", authority: "Field support" }
      ],
      context: "Enabled centralized command and modern coordination."
    },
    {
      key: "MEDICAL",
      name: "Medical Corps",
      icon: "fas fa-notes-medical",
      description: "Doctors, nurses, and stretcher-bearers in the army.",
      hierarchy: [
        { role: "Field Hospital", authority: "Surgical & triage unit" },
        { role: "Medical Company", authority: "Forward medical support" }
      ],
      context: "Crucial for morale and survival in prolonged wars."
    },
    {
      key: "LOGISTICS",
      name: "Logistics / Supply",
      icon: "fas fa-boxes",
      description: "Troops ensuring food, munitions, and equipment delivery.",
      hierarchy: [
        { role: "Logistics Regiment", authority: "Wide supply chain" },
        { role: "Transport Company", authority: "Field supply unit" }
      ],
      context: "Armies marched on supply — logistics decided campaigns."
    },
    {
      key: "GENDARMERIE",
      name: "Gendarmerie / Military Police",
      icon: "fas fa-balance-scale",
      description: "Troops enforcing discipline and internal security.",
      hierarchy: [
        { role: "Gendarmerie Regiment", authority: "Law & order" },
        { role: "Detachment", authority: "Regional policing" }
      ],
      context: "Bridged military authority with civilian populations."
    },
    {
      key: "INTELLIGENCE",
      name: "Intelligence / Reconnaissance",
      icon: "fas fa-user-secret",
      description: "Scouts, spies, and information-gathering units.",
      hierarchy: [
        { role: "Recon Battalion", authority: "Tactical reconnaissance" },
        { role: "Patrol", authority: "Forward observation" }
      ],
      context: "Essential for planning and surprise operations."
    },
    {
      key: "AIRBORNE",
      name: "Airborne / Paratroopers",
      icon: "fas fa-parachute-box",
      description: "Troops deployed from aircraft behind enemy lines.",
      hierarchy: [
        { role: "Airborne Division", authority: "Large-scale parachute force" },
        { role: "Parachute Regiment", authority: "Operational airborne unit" }
      ],
      context: "Created rapid-strike shock capability."
    },
    {
      key: "SPECIAL_FORCES",
      name: "Special Forces / Commandos",
      icon: "fas fa-skull-crossbones",
      description: "Elite irregular units for raids, sabotage, high-risk missions.",
      hierarchy: [
        { role: "Commando Unit", authority: "Specialized raiding force" },
        { role: "Small Team", authority: "Covert missions" }
      ],
      context: "Trained for unconventional warfare."
    },
    {
      key: "GUERILLA",
      name: "Guerilla / Resistance",
      icon: "fas fa-flag",
      description: "Irregular local forces resisting occupation.",
      hierarchy: [
        { role: "Partisan Detachment", authority: "Local resistance group" },
        { role: "Cell", authority: "Small covert team" }
      ],
      context: "Carried political and national liberation struggles."
    },
    {
      key: "PEACEKEEPING",
      name: "Peacekeeping Forces",
      icon: "fas fa-dove",
      description: "Forces deployed for peace enforcement and monitoring.",
      hierarchy: [
        { role: "Peacekeeping Mission", authority: "International mandate" },
        { role: "Battalion/Company", authority: "On-ground enforcement" }
      ],
      context: "Reflected diplomacy and multinational cooperation."
    }
  ];

  if (loading) return (
    <Container className="text-center my-5 py-5">
      <Spinner animation="border" variant="primary" />
      <p className="mt-3 text-muted">Loading military organizations index...</p>
    </Container>
  );

  return (
    <Container className="my-5 py-3">
      <header className="text-center mb-5">
        <h1 className="display-5 fw-bold text-dark mb-1">
          <i className="fas fa-shield-alt me-3 text-primary"></i>Military Organizations Ontology
        </h1>
        <p className="lead text-secondary fst-italic mb-4 mx-auto" style={{ maxWidth: "800px" }}>
          This index provides structured access to military administrative and organizational units,
          essential for studying the role of armed forces in state power, social structures, and conflict history.
        </p>
        {isAdmin && (
          <div className="d-flex justify-content-center my-4">
            <Link to="/militaryOrganizations/create" className="btn btn-primary btn-lg rounded-pill px-5 py-2 fw-semibold shadow-lg">
              <i className="fas fa-plus-circle me-2"></i>Create New Organization
            </Link>
          </div>
        )}
      </header>

      {/* Framework Cards */}
      <Row className="mb-5 justify-content-center">
        <Col md={10}>
          <h2 className="fw-bold text-dark mb-3">I. The Organizational Structure Framework</h2>
          <Row className="g-4 mb-4">
            {militaryStructureDescriptions.map((item, index) => (
              <Col md={4} key={index}>
                <Card className="h-100 shadow-sm border-start border-primary border-4 rounded-3">
                  <Card.Body className="p-3">
                    <h6 className="fw-bold text-primary mb-1">
                      <i className={`${item.icon} me-2`}></i> {item.title}
                    </h6>
                    <p className="text-muted small mb-0">{item.text}</p>
                  </Card.Body>
                </Card>
              </Col>
            ))}
          </Row>

          {/* Branch Tabs */}
          <h2 className="fw-bold text-dark mt-4 mb-3">II. Army Branches & Administrative Hierarchies</h2>
          <Tab.Container id="branch-tabs" activeKey={activeBranch} onSelect={(k) => setActiveBranch(k)}>
            <Row>
              {/* Side Nav */}
              <Col md={3} className="mb-3">
                <Nav variant="pills" className="flex-column p-3 bg-light rounded-3 shadow-sm border">
                  {branchDescriptions.map((item) => (
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
                  {branchDescriptions.map((item) => (
                    <Tab.Pane key={item.key} eventKey={item.key}>
                      <h4 className="fw-bold text-dark border-bottom pb-2 mb-3">{item.name} Hierarchy</h4>
                      <p className="text-muted mb-4">{item.description}</p>
                      <h5 className="fw-bold text-primary mb-3">Administrative Chain of Command:</h5>
                      <Row className="g-3">
                        {item.hierarchy.map((level, i) => (
                          <Col md={6} key={i}>
                            <Card className="h-100 border-0 border-start border-4 border-secondary-subtle">
                              <Card.Body className="p-3">
                                <h6 className="mb-1 text-dark">
                                  <i className="fas fa-sitemap me-2 text-secondary"></i>
                                  <strong>{level.role}</strong>
                                </h6>
                                <p className="small text-muted mb-0">{level.authority}</p>
                              </Card.Body>
                            </Card>
                          </Col>
                        ))}
                      </Row>
                      <p className="small fst-italic text-muted mt-4 mb-0 border-top pt-3">
                        <i className="fas fa-info-circle me-1 text-primary"></i> Contextual Note: {item.context}
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

      {/* Table of Organizations */}
      <MilitaryOrganizationTable
        deleteOrganization={deleteOrganization}
        items={organizations}
        label="Total Organizations Indexed:"
      />
    </Container>
  );
};

export default MilitaryOrganizationIndex;
