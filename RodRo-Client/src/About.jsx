import React from "react";
import { Container, Row, Col, Card } from "react-bootstrap";

const About = () => {
  return (
    <Container className="my-5 py-4 bg-light rounded shadow-lg"> {/* Consistent container styling */}
      {/* Font Awesome for icons (if not already globally included) */}
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" xintegrity="sha512-1ycn6IcaQQ40JuKmWswL3QbpgExX+Q6bLSYg5rXW4remp4o/Y6fFZj/gX2Q2K2V2F5R5/5Q5w5w5w5w5w==" crossOrigin="anonymous" referrerPolicy="no-referrer" />

      <Row className="mb-4">
        <Col md={10} className="offset-md-1 text-center">
          <h1 className="display-4 fw-bold text-primary mb-3">Welcome to the Familia Familiarum Project</h1>
          <p className="lead text-muted fst-italic mb-4">A Living Archive of People, Places, and Relationships Across Time</p>
        </Col>
      </Row>

      <Row className="justify-content-center">
        <Col md={10}>
          <Card className="mb-4 shadow-lg border-0 rounded-4"> {/* Enhanced card styling */}
            <Card.Body className="p-4">
              <p className="mb-4">
                This platform is the result of a passionate and collaborative effort to document the rich human landscape of a specific region and beyond. At its heart, the <strong>Familia Familiarum</strong> project — Latin for “Family of Families” — is a genealogical and historical database, designed not only for family researchers but also for scholars and enthusiasts in fields such as <em>sociology, anthropology, history, cultural studies, and demography</em>.
              </p>

              <p className="mb-4">
                What began as a personal genealogical journey has grown into a collective mission: to preserve memory, map relationships, and connect data points across generations. This project recognizes that every person’s story is part of a larger human story — shaped by place, social roles, institutions, and historical events.
              </p>

              <p className="mb-4">
                Our database includes more than just people and family ties. It integrates a wide range of historical and sociological contexts:
              </p>

              <ul className="list-unstyled ps-4 mt-2"> {/* Using list-unstyled and ps-4 for custom bullet effect */}
                <li><i className="fas fa-dot-circle text-primary me-2"></i><strong>People & Families:</strong> Genealogical connections across generations.</li>
                <li><i className="fas fa-dot-circle text-primary me-2"></i><strong>Institutions & Occupations:</strong> Social structure, professions, and public roles.</li>
                <li><i className="fas fa-dot-circle text-primary me-2"></i><strong>Places:</strong> Countries, districts, parishes, subdivisions, and cemeteries — geography as heritage.</li>
                <li><i className="fas fa-dot-circle text-primary me-2"></i><strong>Military Service:</strong> Military roles, structures, and historical ranks.</li>
                <li><i className="fas fa-dot-circle text-primary me-2"></i><strong>Taxation & Sources:</strong> Economic and archival records as lenses on society.</li>
              </ul>

              <p className="mb-4">
                By combining these elements, <strong>Familia Familiarum</strong> offers a multidimensional view of communities — not just who people were, but how they lived, worked, worshipped, migrated, and related to each other.
              </p>

              <p className="mb-4">
                This is a non-profit, open-access initiative built on the open-source GeneWeb platform, enhanced to reflect our vision. We invite collaboration, data sharing, and community participation. Whether you’re tracing your ancestry, conducting academic research, or simply curious about the past, we welcome you.
              </p>

              <p className="mb-4">
                Together, let’s preserve history and explore the intricate fabric of human life — one name, one place, one story at a time.
              </p>

              <p className="text-center fw-semibold text-muted mt-4">
                (Explore the database using the navigation bar above or start from the homepage.)
              </p>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
};

export default About;
