import React from "react";
import { Link } from "react-router-dom";
import { Container, Row, Col, Card } from "react-bootstrap"; // Import Card for consistent styling

const TermsOfService = () => {
  return (
    <Container className="my-5 py-4 bg-light rounded shadow-lg"> {/* Consistent container styling */}
      {/* Font Awesome for icons (if not already globally included) */}
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" xintegrity="sha512-1ycn6IcaQQ40JuKmWswL3QbpgExX+Q6bLSYg5rXW4remp4o/Y6fFZj/gX2Q2K2V2F5R5/5Q5w5w5w5w5w==" crossOrigin="anonymous" referrerPolicy="no-referrer" />

      <Row className="mb-4">
        <Col md={10} className="offset-md-1 text-center">
          <h1 className="display-4 fw-bold text-primary mb-3">Terms of Service</h1>
          <p className="lead text-muted fst-italic mb-4">Last updated: August 1, 2025</p> {/* Moved date to top for consistency */}
        </Col>
      </Row>

      <Row className="justify-content-center">
        <Col md={10}>
          <Card className="mb-4 shadow-lg border-0 rounded-4"> {/* Enhanced card styling */}
            <Card.Body className="p-4">
              <p className="mb-4">
                Welcome to <strong>familiarum.eu</strong>. By using this website and its associated services,
                you agree to the following terms and conditions. If you do not agree
                with these terms, please refrain from using the site.
              </p>

              <section className="mb-4">
                <h2 className="h4 fw-semibold mb-3 text-secondary">1. About the Project</h2>
                <p>
                  This website is operated as a personal non-commercial initiative based in the Czech Republic.
                  Its purpose is to document and share genealogical, historical, and sociological data about people,
                  families, institutions, and regions across time and space. The site is operated by a private
                  individual for research and educational purposes.
                </p>
              </section>

              <section className="mb-4">
                <h2 className="h4 fw-semibold mb-3 text-secondary">2. Data Accuracy</h2>
                <p>
                  While we strive to ensure the accuracy of the information published, no guarantee is made regarding
                  the completeness, reliability, or correctness of data. You use the data at your own risk.
                </p>
              </section>

              <section className="mb-4">
                <h2 className="h4 fw-semibold mb-3 text-secondary">3. User Contributions</h2>
                <p>If you submit information, such as corrections, feedback, or contact forms, you affirm that:</p>
                <ul className="list-unstyled ps-4 mt-2">
                  <li><i className="fas fa-dot-circle text-primary me-2"></i>You are the rightful owner of the content or have permission to share it;</li>
                  <li><i className="fas fa-dot-circle text-primary me-2"></i>You grant the operator the right to use, adapt, and include the submitted data within the project database;</li>
                  <li><i className="fas fa-dot-circle text-primary me-2"></i>Your submission does not infringe upon the rights of others or violate any applicable laws.</li>
                </ul>
              </section>

              <section className="mb-4">
                <h2 className="h4 fw-semibold mb-3 text-secondary">4. Intellectual Property</h2>
                <p>
                  All website content, including text, design, and data structure, unless otherwise stated, is the intellectual
                  property of the operator and may not be reproduced or redistributed without permission. Open data sources,
                  if used, are cited where applicable.
                </p>
              </section>

              <section className="mb-4">
                <h2 className="h4 fw-semibold mb-3 text-secondary">5. Privacy and Data Handling</h2>
                <p>
                  Please refer to our <Link to="/privacy-policy" className="text-decoration-underline text-info fw-medium">Privacy Policy</Link> for information on how personal data is collected,
                  used, and protected.
                </p>
              </section>

              <section className="mb-4">
                <h2 className="h4 fw-semibold mb-3 text-secondary">6. Disclaimers</h2>
                <p>
                  This website and its services are provided “as is” with no warranties, express or implied. The operator does not
                  assume responsibility for any damages arising from the use or inability to use the website.
                </p>
              </section>

              <section className="mb-4">
                <h2 className="h4 fw-semibold mb-3 text-secondary">7. Governing Law</h2>
                <p>
                  These terms shall be governed by and construed in accordance with the laws of the Czech Republic and applicable
                  European Union regulations.
                </p>
              </section>

              <section className="mb-4">
                <h2 className="h4 fw-semibold mb-3 text-secondary">8. Changes to These Terms</h2>
                <p>
                  These Terms of Service may be updated from time to time. Continued use of the website after changes implies acceptance
                  of the new terms.
                </p>
              </section>

              <section className="mb-4">
                <h2 className="h4 fw-semibold mb-3 text-secondary">9. Contact</h2>
                <p>
                  For questions or concerns regarding these terms, please use the <Link to="/contact" className="text-decoration-underline text-info fw-medium">contact form</Link> available on the website.
                </p>
              </section>

              <p className="text-muted text-center mt-5">
                © {new Date().getFullYear()} familiarum.eu — All rights reserved.
              </p>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
};

export default TermsOfService;
