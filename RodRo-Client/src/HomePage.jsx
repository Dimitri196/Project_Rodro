import React from "react";
import { Link, useNavigate } from "react-router-dom";
import {
  Container,
  Card,
  Row,
  Col,
  Spinner,
  Form,
  Button,
  InputGroup,
  ListGroup, // Although ListGroup is not used for main blocks, it's kept for completeness
} from "react-bootstrap";
import { useSession } from "./contexts/session";
import "bootstrap/dist/css/bootstrap.min.css";
import "@fortawesome/fontawesome-free/css/all.min.css";

function HomePage() {
  const { session, setSession } = useSession();
  const isLoading = session.status === "loading";
  const navigate = useNavigate();

  // Refactored Main entity cards - Academic Terminology Applied
  const mainCardsData = [
    {
      title: "Individual Trajectories",
      summary: "Access high-resolution biographical data tracing personal records, life events, and complex interpersonal networks across the entire temporal domain.",
      path: "/persons",
      icon: "fas fa-user-circle",
      points: [
        "Millions of individual life course records.",
        "Structured event data: Vital statistics, professional roles, and affiliations.",
        "Advanced relational network and social structure mapping.",
      ],
    },
    {
      title: "Geospatial Frameworks",
      summary: "Explore dynamic, multi-layered spatial data with precision geographic coordinates and historical administrative boundaries across one thousand years.",
      path: "/locations",
      icon: "fas fa-map-marker-alt",
      points: [
        "Historical administrative boundaries and territories (Time-sliced data).",
        "Precision geolocation and settlement patterns.",
        "Dynamic, layered location histories for spatial analysis.",
      ],
    },
    {
      title: "Kinship and Social Systems",
      summary: "Reconstruct and analyze family structures and kinship ties, modeling the complex fabric of human life and long-term social stratification.",
      path: "/families",
      icon: "fas fa-users",
      points: [
        "Computational genealogy and lineage tracking algorithms.",
        "Tools for complex relationship visualization (Graph database models).",
        "Data-driven analysis of family heritage and societal change.",
      ],
    },
    {
      title: "Documentary Evidence",
      summary: "Systematically explore the underlying archival and source material, transcribed and digitized to support systematic, high-throughput research.",
      path: "/sources",
      icon: "fas fa-book",
      points: [
        "Direct access to digitized primary source manuscripts.",
        "Full-text transcription in local and classical languages.",
        "Integration of qualitative and quantitative research pipelines.",
      ],
    },
  ];

  // Secondary cards - Academic Terminology Applied
  const secondaryCardsData = [
    { title: "Ecclesiastical Units", text: "Religious and community administrative divisions.", path: "/parishes", icon: "fas fa-church" },
    { title: "Mortality Data", text: "Burial sites, demographic data, and memorial records.", path: "/cemeteries", icon: "fas fa-cross" },
    { title: "Institutional Hierarchies", text: "Historical organizations, governance, and political actors.", path: "/institutions", icon: "fas fa-building" },
    { title: "Professional Vocabularies", text: "Structured dataset of professions and official titles.", path: "/occupations", icon: "fas fa-briefcase" },
    { title: "National Jurisdictions", text: "Macro-level national histories and governance structures.", path: "/countries", icon: "fas fa-globe-americas" },
    { title: "Military Organizations", text: "Structured data on military service and units.", path: "/militaryOrganizations", icon: "fas fa-medal" },
    { title: "Military Command", text: "Organizational structures and command lineage.", path: "/militaryStructures", icon: "fas fa-sitemap" },
    { title: "Military Rank Taxonomy", text: "Standardized list of ranks and hierarchies.", path: "/militaryRanks", icon: "fas fa-star" },
  ];

  const handleSearch = (event) => {
    event.preventDefault();
    const query = event.target.elements.search.value;
    if (query) {
      navigate(`/search?q=${encodeURIComponent(query)}`);
    }
  };

  if (isLoading) {
    return (
      <div className="d-flex justify-content-center align-items-center vh-100 bg-light">
        <div className="text-center">
          <Spinner animation="border" variant="primary" className="mb-3" />
          <p className="text-muted">Loading session...</p>
        </div>
      </div>
    );
  }

  return (
    <div className="d-flex flex-column min-vh-100">
      <style>
        {`
        /* --- SCIENTIFIC PROJECT AESTHETICS --- */
        :root {
            --mit-blue: #007bff; /* Sharp accent color */
            --light-gray: #f8f9fa;
            --mid-gray: #e9ecef;
        }

        /* Main Feature Blocks */
        .feature-block {
          transition: all 0.3s cubic-bezier(0.25, 0.46, 0.45, 0.94); /* Smooth, sharp hover */
          border: 1px solid var(--mid-gray);
          border-left: 5px solid var(--mid-gray); /* Subtle scientific border */
          border-radius: 0.5rem;
          padding: 2.5rem; /* Increased padding */
          background-color: #fff;
          box-shadow: 0 0.25rem 0.75rem rgba(0, 0, 0, 0.05);
          height: 100%;
        }

        .feature-block:hover {
          transform: translateY(-4px);
          box-shadow: 0 0.75rem 1.5rem rgba(0, 0, 0, 0.1);
          border-left: 5px solid var(--mit-blue); /* Active state highlight */
        }

        .feature-icon-wrapper {
          width: 60px;
          height: 60px;
          display: flex;
          align-items: center;
          justify-content: center;
          flex-shrink: 0;
          margin-right: 1.5rem;
          background-color: var(--light-gray);
          border-radius: 50%;
        }

        .feature-title {
          font-weight: 700;
          font-size: 1.7rem;
          color: #343a40;
        }

        .feature-summary {
          color: #6c757d;
          font-size: 1rem;
          margin-bottom: 1.5rem;
          padding-bottom: 1.5rem;
          border-bottom: 1px dashed var(--mid-gray); /* Dashed separator for technical feel */
        }

        .feature-points-list {
            list-style: none;
            padding: 0;
            margin: 0;
        }

        .feature-point-item {
            display: flex;
            align-items: flex-start;
            margin-bottom: 0.75rem;
            color: #495057;
            font-size: 1rem;
        }

        .feature-point-icon {
          color: var(--mit-blue); /* Checkmark now uses the primary MIT-style blue */
          margin-right: 1rem;
          font-size: 1.15rem;
          flex-shrink: 0;
          transform: translateY(2px);
        }

        .feature-button-container {
             margin-top: 2rem;
             text-align: right;
        }

        .action-link-btn {
          font-size: 1.05rem;
          font-weight: 600;
          color: var(--mit-blue);
          text-decoration: none;
          padding: 0.5rem 1rem;
          border-bottom: 2px solid transparent;
          transition: all 0.2s;
        }

        .action-link-btn:hover {
            color: #0056b3;
            border-bottom: 2px solid var(--mit-blue);
            background-color: var(--light-gray);
            border-radius: 0.25rem;
        }

        /* Secondary Module Cards */
        .scientific-card {
          transition: all 0.3s ease;
          border: 1px solid var(--mid-gray);
          background-color: #fff;
          border-radius: 0.5rem;
          height: 100%;
          text-align: center;
        }

        .scientific-card:hover {
          transform: translateY(-4px);
          box-shadow: 0 0 10px rgba(0, 123, 255, 0.2), 0 0.5rem 1rem rgba(0, 0, 0, 0.05);
          border-color: var(--mit-blue);
        }

        .scientific-card-body {
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            align-items: center;
            padding: 1.25rem 1rem;
        }

        .scientific-icon-wrapper {
          width: 48px;
          height: 48px;
          display: flex;
          align-items: center;
          justify-content: center;
          margin: 0 auto 0.5rem;
          background-color: var(--mid-gray);
          border-radius: 0.25rem; /* Square with subtle rounded corners */
        }

        .scientific-card .card-title {
          font-weight: 600;
          font-size: 1rem;
          color: #212529;
          margin-top: 0.5rem;
          margin-bottom: 0.5rem;
        }

        .scientific-card .card-text {
          color: #6c757d;
          font-size: 0.8rem;
          flex-grow: 1;
          line-height: 1.4;
          margin-bottom: 1rem;
        }
        `}
      </style>

      <main
        className="position-relative overflow-hidden flex-grow-1"
        style={{
          backgroundImage: 'url("/images/Polesiae.jpg")',
          backgroundSize: "cover",
          backgroundPosition: "center",
          backgroundAttachment: "fixed",
        }}
      >
        {/* Overlay */}
        <div
          style={{
            position: "absolute",
            top: 0,
            left: 0,
            width: "100%",
            height: "100%",
            backgroundColor: "rgba(0,0,0,0.6)",
            zIndex: 0,
          }}
        ></div>

        {/* Hero Section - Academic Pitch */}
        <header className="text-white pt-5 pb-5 text-center position-relative" style={{ zIndex: 1 }}>
          <Container>
            {/* Project name refinement */}
            <h1 className="display-3 fw-bold mb-1">FAMILIARUM.EU</h1>
            <p className="lead fs-5 text-uppercase fw-light" style={{ letterSpacing: '2px' }}>
              Computational Historical Demography & Social Network Modeling
            </p>
            <p className="mt-4 mx-auto" style={{ maxWidth: "900px", fontSize: "1.1rem" }}>
              The **Familia Familiarum** initiative is a leading **open-access digital humanities project** dedicated to reconstructing the complex socio-demographic fabric of the last millennium. We integrate advanced **Computational History** and **Quantitative Social Research** to map family heritage and institutional dynamics across space and time.
            </p>
            {/* Simplified bullet list for cleaner Hero section */}
            <p className="mt-3 mx-auto fw-bold" style={{ maxWidth: "900px", fontSize: "1.1rem", color: '#fff' }}>
              Core Data Dimensions: <span className="text-primary">Trajectories</span> | <span className="text-primary">Geospatial</span> | <span className="text-primary">Kinship</span> | <span className="text-primary">Archival</span>
            </p>
          </Container>
        </header>

        {/* Search - Modernized */}
        <section className="text-white pb-5 position-relative" style={{ zIndex: 1 }}>
          <Container>
            <div className="mx-auto" style={{ maxWidth: "700px" }}>
              <Form onSubmit={handleSearch}>
                <InputGroup className="shadow-lg rounded-pill overflow-hidden">
                  <Form.Control
                    type="text"
                    name="search"
                    id="searchInput"
                    placeholder="Search entities, events, or structured data via computational index..."
                    className="border-0 py-3 px-4 bg-white text-dark"
                    style={{ fontSize: "1.05rem" }}
                  />
                  <Button type="submit" variant="primary" className="px-4 fw-semibold text-white">
                    <i className="fas fa-search me-2"></i> Query Index
                  </Button>
                </InputGroup>
              </Form>
            </div>
          </Container>
        </section>

        {/* --- */}

        {/* Main Entities Section - Computational History Modules */}
        <section className="bg-light py-5 position-relative" style={{ zIndex: 1 }}>
          <Container>
            <h2 className="text-center fw-light text-dark display-5 mb-1">
                Data Infrastructure Modules
            </h2>
            <p className="text-center text-muted mb-5 mx-auto" style={{ maxWidth: "800px" }}>
                Dive into our core data models. Each module is engineered for high-throughput querying and complex relational analysis, supporting academic research and computational modeling.
            </p>
            <Row xs={1} md={2} className="g-4">
              {mainCardsData.map(({ title, summary, path, icon, points }, i) => (
                <Col key={i}>
                  <div className="feature-block">
                    <div className="feature-header d-flex align-items-center mb-4">
                      {/* Icon */}
                      <div className="feature-icon-wrapper">
                        <i className={`${icon} fa-2x text-primary`}></i>
                      </div>
                      {/* Title */}
                      <div>
                        <h3 className="feature-title mb-0">{title}</h3>
                      </div>
                    </div>
                    
                    {/* Summary/Short Description */}
                    <p className="feature-summary">{summary}</p>

                    {/* Points List */}
                    <ul className="feature-points-list">
                      {points.map((point, index) => (
                        <li key={index} className="feature-point-item">
                          {/* Using check-circle for visual confirmation, but color is blue */}
                          <i className="fas fa-check-circle feature-point-icon"></i>
                          <span>{point}</span>
                        </li>
                      ))}
                    </ul>

                    {/* Button - Clean Link Style */}
                    <div className="feature-button-container">
                      <Link
                        to={path}
                        className="action-link-btn"
                      >
                        Launch {title.replace(' Data', '')} Module <i className="fas fa-arrow-right ms-2"></i>
                      </Link>
                    </div>
                  </div>
                </Col>
              ))}
            </Row>
          </Container>
        </section>

        {/* --- */}

        {/* Secondary Entities Section - Auxiliary Ontologies (Scientific) */}
        <section className="py-5 position-relative flex-grow-1" style={{ zIndex: 1, backgroundColor: "#ffffff" }}>
          <Container className="pt-3 pb-5">
            {/* Scientific Header */}
            <h2 className="text-center fw-light text-dark display-6 mb-2">
                Auxiliary Ontologies & Structured Taxonomies
            </h2>
            <p className="text-center text-muted mb-5 mx-auto" style={{ maxWidth: "700px" }}>
                Access secondary classification systems and structured data streams critical for granular analysis of institutional and geographical frameworks.
            </p>
            
            <Row xs={2} sm={3} md={4} lg={6} className="g-3"> 
              {secondaryCardsData.map(({ title, text, path, icon }, i) => (
                <Col key={i} className="d-flex">
                  <Card className="scientific-card w-100">
                    <Card.Body className="scientific-card-body">
                      <div className="scientific-icon-wrapper">
                        <i className={`${icon} fa-lg text-primary`}></i> 
                      </div>
                      <Card.Title className="scientific-card-title">{title}</Card.Title>
                      <Card.Text className="scientific-card-text">
                        {text}
                      </Card.Text>
                      <Link
                        to={path}
                        className="btn btn-outline-primary btn-sm rounded-pill mt-auto" // Added mt-auto to align buttons
                      >
                        View
                      </Link>
                    </Card.Body>
                  </Card>
                </Col>
              ))}
            </Row>
          </Container>
        </section>
      </main>
    </div>
  );
}

export default HomePage;
