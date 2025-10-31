import React, { useEffect, useState } from "react";
import { Container, Row, Col, Alert, Spinner, Card, Nav, Tab } from "react-bootstrap";
import { Link } from "react-router-dom";
import { useSession } from "../contexts/session";
import { apiGet, apiDelete } from "../utils/api";
import MilitaryRankTable from "./MilitaryRankTable";
import "@fortawesome/fontawesome-free/css/all.min.css";

const MilitaryRankIndex = () => {
  const { session } = useSession();
  const isAdmin = session.data?.isAdmin === true;

  const [ranks, setRanks] = useState([]);
  const [error, setError] = useState(null);
  const [loading, setLoading] = useState(true);
  const [activeRank, setActiveRank] = useState("Regimental Commander (General)");

  const fetchRanks = async () => {
    try {
      const data = await apiGet("/api/militaryRanks");
      setRanks(data);
      setError(null);
    } catch (err) {
      console.error("Error fetching military ranks:", err);
      setError("Failed to load military ranks. Please try again later.");
    } finally {
      setLoading(false);
    }
  };

  const deleteRank = async (id) => {
    if (!window.confirm("CONFIRMATION: Deleting this rank will remove all associated data. Proceed?")) return;
    try {
      await apiDelete(`/api/militaryRanks/${id}`);
      setRanks((prev) => prev.filter((item) => item._id !== id));
      setError(null);
    } catch (err) {
      console.error("Error deleting military rank:", err);
      setError("Authorization Error: Failed to delete the record.");
    }
  };

  useEffect(() => {
    fetchRanks();
  }, []);

  // --- Full Military Rank Descriptions ---
  const rankDescriptions = [
    { name: "Regimental Commander (General)", icon: "fas fa-star", description: "Highest officer commanding an entire regiment.", hierarchy: [{ role: "Regiment", authority: "Several battalions" }], context: "Responsible for overall regimental operations and strategy." },
    { name: "Colonel", icon: "fas fa-star-half-alt", description: "Senior field officer, typically commands a regiment.", hierarchy: [{ role: "Regiment", authority: "Commanding officer" }], context: "Provides strategic leadership at the regiment level." },
    { name: "Lieutenant Colonel", icon: "fas fa-star-half-alt", description: "Deputy commander of a regiment or battalion commander.", hierarchy: [{ role: "Battalion", authority: "Commanding officer" }], context: "Assists regimental commander or leads battalion independently." },
    { name: "Major", icon: "fas fa-circle", description: "Mid-level officer, often commanding a battalion or acting as staff officer.", hierarchy: [{ role: "Battalion Staff", authority: "Operations and administration" }], context: "Coordinates tactical and administrative duties for battalion-level operations." },
    { name: "Captain (Company Commander)", icon: "fas fa-user-tie", description: "Leads a company of soldiers, responsible for tactical operations.", hierarchy: [{ role: "Company", authority: "100–200 men" }], context: "Directly commands troops in the field." },
    { name: "Auditor", icon: "fas fa-file-alt", description: "Military official overseeing audits and compliance in units.", context: "Ensures financial and administrative accountability within the regiment." },
    { name: "Regimental Commander (Quartermaster)", icon: "fas fa-boxes", description: "Manages logistics, supplies, and equipment for a regiment.", context: "Crucial for operational readiness and supply management." },
    { name: "Adjutant", icon: "fas fa-user-cog", description: "Administrative officer assisting the commander with staff duties.", context: "Coordinates orders, personnel, and reports." },
    { name: "First Lieutenant", icon: "fas fa-user", description: "Junior officer ranking above second lieutenant; often platoon leader.", hierarchy: [{ role: "Platoon", authority: "30–50 soldiers" }], context: "Leads troops and ensures company orders are executed." },
    { name: "Second Lieutenant", icon: "fas fa-user", description: "Entry-level officer responsible for small units or sections.", hierarchy: [{ role: "Platoon Section", authority: "10–20 soldiers" }], context: "Initial officer rank focusing on direct troop leadership." },
    { name: "Warrant Officer", icon: "fas fa-certificate", description: "Specialist officer, technical expert in their field.", context: "Provides advanced technical and operational expertise." },
    { name: "Cadet", icon: "fas fa-user-graduate", description: "Officer in training, usually enrolled in a military academy.", context: "Learns leadership, tactics, and military administration." },
    { name: "Feldfebel", icon: "fas fa-chess-knight", description: "Senior non-commissioned officer in charge of training and discipline.", context: "Ensures unit cohesion and soldier proficiency." },
    { name: "Sergeant", icon: "fas fa-chess-pawn", description: "NCO leading squads or sections; key in training and discipline.", context: "Supervises day-to-day operations and enforces orders." },
    { name: "Furyer", icon: "fas fa-cogs", description: "NCO responsible for administrative and training duties within a company.", context: "Supports officers in personnel and training tasks." },
    { name: "Corporal", icon: "fas fa-user-friends", description: "Junior NCO, often leading small teams or squads.", context: "Leads a squad and reports to sergeant or lieutenant." },
    { name: "Private", icon: "fas fa-user", description: "Entry-level enlisted soldier, performs basic duties.", context: "Foundation of the military workforce." },
    { name: "Brigadier (General) (Brigade Commander)", icon: "fas fa-star", description: "Commands a brigade; senior tactical commander.", hierarchy: [{ role: "Brigade", authority: "Several regiments" }], context: "Directs operations at brigade level." },
    { name: "Vice Brigadier", icon: "fas fa-star-half-alt", description: "Deputy brigade commander assisting the Brigadier.", context: "Supports strategic planning and operational oversight." },
    { name: "Cavalry Captain (Battle Chief)", icon: "fas fa-horse", description: "Commands cavalry units in battle operations.", hierarchy: [{ role: "Cavalry Squadron", authority: "Several troops" }], context: "Leads cavalry maneuvers and battlefield operations." },
    { name: "Quartermaster", icon: "fas fa-box", description: "Officer responsible for logistics and supply at unit level.", context: "Ensures troops are well-equipped and supplied." },
    { name: "Lieutenant", icon: "fas fa-user", description: "Officer commanding platoons or performing staff duties.", context: "Intermediate officer executing company and platoon tasks." },
    { name: "Comrade", icon: "fas fa-users", description: "Enlisted personnel, typically used in certain historical or political contexts.", context: "General enlisted rank or title of affiliation." },
    { name: "Second Sergeant", icon: "fas fa-chess-pawn", description: "NCO ranking below first sergeant; assists in unit management.", context: "Supports senior NCOs in discipline and training." },
    { name: "Corps Commander", icon: "fas fa-star", description: "Senior officer in charge of a corps, multiple divisions.", context: "Responsible for corps-level strategic operations." },
    { name: "Captain", icon: "fas fa-user-tie", description: "Company-level officer responsible for administration and operations.", context: "Leads and organizes company operations." },
    { name: "Conductor", icon: "fas fa-truck", description: "Logistics officer in charge of transport and supplies.", context: "Ensures movement of equipment and provisions." },
    { name: "Unterofficer", icon: "fas fa-user-shield", description: "NCO rank in charge of small units or squads.", context: "Leads small teams under officer guidance." },
    { name: "Miner", icon: "fas fa-mountain", description: "Engineer soldier specialized in mining and fortifications.", context: "Handles explosive and mining operations for fortifications." },
    { name: "Sapper", icon: "fas fa-tools", description: "Combat engineer responsible for demolitions, trenches, and fortifications.", context: "Performs field engineering tasks in combat." },
    { name: "Major General", icon: "fas fa-star", description: "Two-star general commanding divisions or equivalent formations.", hierarchy: [{ role: "Division", authority: "Several brigades" }], context: "Manages large-scale tactical operations." },
    { name: "Adjutant General", icon: "fas fa-user-cog", description: "Chief administrative officer for military headquarters.", context: "Oversees administration and personnel across units." },
    { name: "Oberceugwarter", icon: "fas fa-star", description: "Senior artillery NCO overseeing munitions and equipment.", context: "Manages artillery stores and technical logistics." },
    { name: "Ceugwarter", icon: "fas fa-star-half-alt", description: "Artillery NCO in charge of specific artillery sections.", context: "Supervises artillery operations and crews." },
    { name: "Sztuk Junker", icon: "fas fa-graduation-cap", description: "Officer cadet rank in training for commissioned service.", context: "Prepares for officer duties and leadership." },
    { name: "Sztabsfuryer", icon: "fas fa-chess-knight", description: "Senior NCO in artillery units with administrative duties.", context: "Coordinates artillery squads and training." },
    { name: "Oberfeuerwerker", icon: "fas fa-bomb", description: "Senior artillery technician supervising firing teams.", context: "Leads artillery firing operations." },
    { name: "Feuerwerker", icon: "fas fa-bomb", description: "Artillery technician responsible for gun crews.", context: "Operates artillery pieces under supervision." },
    { name: "Ober bombardier", icon: "fas fa-bomb", description: "Senior enlisted artilleryman, overseeing bombardment teams.", context: "Supervises bombardment and munitions handling." },
    { name: "Bombardier", icon: "fas fa-bomb", description: "Artillery crew member, operates guns and munitions.", context: "Manages firing and ammunition delivery." },
    { name: "Corps Commander (general)", icon: "fas fa-star", description: "Top officer commanding a corps-level formation.", context: "Oversees multiple divisions in strategic operations." }
  ];

  if (loading) return (
    <Container className="text-center my-5 py-5">
      <Spinner animation="border" variant="primary" />
      <p className="mt-3 text-muted">Loading military ranks index...</p>
    </Container>
  );

  return (
    <Container className="my-5 py-3">
      {/* Header */}
      <header className="text-center mb-5">
        <h1 className="display-5 fw-bold text-dark mb-1">
          <i className="fas fa-medal me-3 text-primary"></i>Military Ranks Ontology
        </h1>
        <p className="lead text-secondary fst-italic mb-4 mx-auto" style={{ maxWidth: "800px" }}>
          This index catalogs military ranks with their levels, branches, and associated organizations,
          providing insight into command structures, hierarchy, and historical significance.
        </p>
        {isAdmin && (
          <div className="d-flex justify-content-center my-4">
            <Link to="/militaryRanks/create" className="btn btn-primary btn-lg rounded-pill px-5 py-2 fw-semibold shadow-lg">
              <i className="fas fa-plus-circle me-2"></i>Create New Rank
            </Link>
          </div>
        )}
      </header>

      {/* Framework Cards */}
      <Row className="mb-5 justify-content-center">
        <Col md={10}>
          <h2 className="fw-bold text-dark mb-3">I. Rank Framework Overview</h2>
          <Row className="g-4 mb-4">
            {[
              { title: "Hierarchy & Classification", icon: "fas fa-layer-group", text: "Ranks indicate positions, authority, and seniority within military organizations." },
              { title: "Operational Roles", icon: "fas fa-crosshairs", text: "Rank corresponds to responsibilities, command capability, and functional specialization." },
              { title: "Historical Evolution", icon: "fas fa-history", text: "Changes in ranks reflect reforms, modernization, and military traditions over time." }
            ].map((item, idx) => (
              <Col md={4} key={idx}>
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
        </Col>
      </Row>

      <hr className="mb-5" />

      {error && <Alert variant="danger" className="rounded-3 shadow-sm">{error}</Alert>}

      {/* Tabs for Rank Descriptions */}
      <Tab.Container activeKey={activeRank} onSelect={(k) => setActiveRank(k)}>
        <Row className="mb-5">
          <Col md={3}>
            <Nav variant="pills" className="flex-column p-3 bg-light rounded-3 shadow-sm border">
              {rankDescriptions.map((rank) => (
                <Nav.Item key={rank.name}>
                  <Nav.Link eventKey={rank.name} className="text-start mb-1 fw-semibold">
                    <i className="fas fa-medal me-2"></i> {rank.name}
                  </Nav.Link>
                </Nav.Item>
              ))}
            </Nav>
          </Col>
          <Col md={9}>
            <Tab.Content className="p-4 bg-white rounded-3 shadow border">
              {rankDescriptions.map((rank) => (
                <Tab.Pane key={rank.name} eventKey={rank.name}>
                  <h4 className="fw-bold text-dark border-bottom pb-2 mb-3">{rank.name}</h4>
                  <p className="text-muted">{rank.description}</p>
                  {rank.hierarchy && (
                    <ul className="text-muted small">
                      {rank.hierarchy.map((h, idx) => (
                        <li key={idx}><strong>{h.role}:</strong> {h.authority}</li>
                      ))}
                    </ul>
                  )}
                  {rank.context && <p className="text-muted fst-italic">{rank.context}</p>}
                </Tab.Pane>
              ))}
            </Tab.Content>
          </Col>
        </Row>
      </Tab.Container>

      {/* Table of Ranks */}
      <MilitaryRankTable
        items={ranks}
        deleteRank={deleteRank}
        label="Total Ranks Indexed:"
      />
    </Container>
  );
};

export default MilitaryRankIndex;
