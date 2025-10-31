import React from "react";
import { Container, Row, Col, Card } from "react-bootstrap";

const About = () => {
  // Data for the Core Ontologies section (replacing the simple bulleted list)
  const coreOntologies = [
    { title: "Socio-Demographic Model", text: "Structured data models for individuals, kinship ties, life events (birth, death, migration), and complex family relationships.", icon: "fas fa-chart-line" },
    { title: "Temporal-Spatial Layering", text: "Integrating historical political, administrative, and ecclesiastical boundaries with precise geolocation data to enable time-series spatial analysis.", icon: "fas fa-layer-group" },
    { title: "Institutional Taxonomy", text: "Classification of non-kin social structures, including military units, educational institutions, parishes, and civil organizations.", icon: "fas fa-sitemap" },
    { title: "Source-to-Data Pipeline", text: "Archival record transcription (in local and Latin languages) and linkage to the structured dataset, establishing data provenance and verifiability.", icon: "fas fa-code-branch" },
  ];

  return (
    <Container className="my-5 py-5">
      <style>
        {`
          /* Custom Styles for Scientific About Page */
          :root {
              --mit-blue: #007bff;
              --light-gray: #f8f9fa;
              --mid-gray: #e9ecef;
              --dark-text: #212529;
          }

          .about-header {
              border-bottom: 3px solid var(--mit-blue);
              padding-bottom: 0.5rem;
              margin-bottom: 3rem;
          }

          .section-title {
              font-weight: 700;
              color: var(--mit-blue);
              margin-top: 2rem;
              margin-bottom: 1.5rem;
              padding-left: 0.5rem;
              border-left: 4px solid var(--mit-blue);
              font-size: 1.5rem;
          }
          
          .ontology-card {
              transition: all 0.3s ease;
              border: 1px solid var(--mid-gray);
              background-color: #fff;
              border-radius: 0.5rem;
              height: 100%;
              overflow: hidden;
              border-top: 5px solid var(--mid-gray);
          }

          .ontology-card:hover {
              transform: translateY(-4px);
              box-shadow: 0 0 15px rgba(0, 123, 255, 0.2);
              border-top: 5px solid var(--mit-blue);
          }

          .ontology-card-icon {
              color: var(--mit-blue);
              font-size: 2rem;
              margin-bottom: 1rem;
              display: block;
          }

          .ontology-card .card-title {
              font-weight: 600;
              font-size: 1.1rem;
              color: var(--dark-text);
              min-height: 40px; /* Ensure title height consistency */
          }

          .ontology-card .card-text {
              font-size: 0.95rem;
              color: #6c757d;
          }
        `}
      </style>

      {/* Main Page Title */}
      <Row className="mb-5">
        <Col md={10} className="offset-md-1">
          <h1 className="display-4 fw-bold about-header text-dark">
            Project Methodology & Data Architecture
          </h1>
          <p className="lead text-secondary fst-italic mb-0">
            A high-fidelity framework for computational history and socio-demographic modeling.
          </p>
        </Col>
      </Row>

      {/* --- */}

      {/* Section I: The Computational Framework */}
      <Row className="justify-content-center">
        <Col md={10}>
          <h2 className="section-title">I. The Computational Framework</h2>
          <p className="fs-5 text-dark">
            The **Familia Familiarum** project operates as a specialized digital humanities initiative, moving beyond traditional genealogical databases by implementing a robust **relational data model** designed for quantitative social research. Our core commitment is the systematic integration of **longitudinal micro-data** into a unified, query-optimised schema.
          </p>

          <p className="mb-4 text-secondary">
            <i className="fas fa-microchip me-2 text-primary"></i> **System Integration:**
            The platform is built on a modified **GeneWeb** engine, extended with custom APIs to facilitate advanced network analysis and geospatial visualization. This architecture enables researchers to query data not just by individual names, but by **complex criteria** involving time, geography, social roles, and institutional affiliation.
          </p>
          
          <p className="mb-5 text-secondary">
            <i className="fas fa-globe me-2 text-primary"></i> **Goal:**
            To provide a verifiable, open-access research infrastructure capable of supporting sophisticated, data-driven inquiries into **kinship evolution, institutional resilience, and population dynamics** across historical boundaries.
          </p>

          <p className="text-center mt-5 mb-5">
            <span className="badge bg-primary fs-6 py-2 px-3 me-2">Data Integrity</span>
            <span className="badge bg-secondary fs-6 py-2 px-3 me-2">Scalable Architecture</span>
            <span className="badge bg-info text-dark fs-6 py-2 px-3">Quantitative Rigor</span>
          </p>
        </Col>
      </Row>
      
      {/* --- */}

      {/* Section II: Core Ontologies (Structured Grid) */}
      <Row className="justify-content-center bg-light py-5 rounded-3 shadow-sm">
        <Col md={10}>
          <h2 className="section-title">II. Core Data Ontologies</h2>
          <p className="mb-4 fs-5 text-dark">
            Our data model is partitioned into four interconnected ontologies, ensuring maximal analytic depth and interdisciplinary utility.
          </p>
          
          <Row xs={1} md={2} className="g-4">
            {coreOntologies.map((item, index) => (
              <Col key={index}>
                <Card className="ontology-card h-100">
                  <Card.Body>
                    <div className="d-flex align-items-center mb-3">
                      <i className={`${item.icon} ontology-card-icon me-3`}></i>
                      <div>
                         <span className="badge bg-primary-subtle text-primary me-2">O-{index + 1}</span>
                         <Card.Title className="d-inline">{item.title}</Card.Title>
                      </div>
                    </div>
                    <Card.Text>{item.text}</Card.Text>
                  </Card.Body>
                </Card>
              </Col>
            ))}
          </Row>
        </Col>
      </Row>

      {/* --- */}

      {/* Section III: Research Access & Collaboration */}
      <Row className="justify-content-center mt-5">
        <Col md={10}>
          <h2 className="section-title">III. Research Access & Collaboration</h2>
          
          <p className="fs-5 text-dark mb-4">
            The project operates under a strict **open-access, non-profit mandate**. Data and source code are provided under permissive licenses to maximize research impact and reproducibility.
          </p>
          
          <ul className="list-unstyled ps-4 mt-2 mb-4">
            <li className="mb-3"><i className="fas fa-user-friends text-primary me-3 fs-5"></i>
              **Academic Partners:** We actively seek collaboration with research groups and individual scholars in Digital History, GIScience, and Network Science to expand data validation and analytical scope.
            </li>
            <li className="mb-3"><i className="fas fa-handshake text-primary me-3 fs-5"></i>
              **Data Contribution:** The platform supports standardized data import workflows (e.g., GEDCOM, custom CSV) for community and archival data ingestion, subject to rigorous schema validation.
            </li>
            <li className="mb-3"><i className="fas fa-book-open text-primary me-3 fs-5"></i>
              **Open Source Ethos:** Built on **open-source** technology, the project encourages community development and technical contributions to enhance the underlying GeneWeb platform and our custom analytical tools.
            </li>
          </ul>

          <p className="text-center fw-semibold text-muted mt-5 pt-3 border-top">
            For technical documentation, API access, and schema details, please refer to our GitHub repository or contact the lead investigators.
          </p>
        </Col>
      </Row>
    </Container>
  );
};

export default About;
