import React from "react";
import { Link } from "react-router-dom";
import { Container, Row, Col, Card } from "react-bootstrap"; // Import Card for consistent styling

const PrivacyPolicy = () => {
  return (
    <Container className="my-5 py-4 bg-light rounded shadow-lg"> {/* Changed container background to light, consistent with HomePage */}
      {/* Font Awesome for icons (if not already globally included) */}
      <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css" xintegrity="sha512-1ycn6IcaQQ40JuKmWswL3QbpgExX+Q6bLSYg5rXW4remp4o/Y6fFZj/gX2Q2K2V2F5R5/5Q5w5w5w5w5w==" crossOrigin="anonymous" referrerPolicy="no-referrer" />

      <Row className="mb-4">
        <Col md={10} className="offset-md-1 text-center">
          <h1 className="display-4 fw-bold text-primary mb-3">Privacy Policy</h1>
          <p className="lead text-muted fst-italic mb-4">Effective Date: [Insert Date]</p>
        </Col>
      </Row>

      <Row className="justify-content-center">
        <Col md={10}>
          <Card className="mb-4 shadow-lg border-0 rounded-4"> {/* Enhanced card styling: stronger shadow, no border, more rounded */}
            <Card.Body className="p-4">
              <section className="mb-4">
                <h2 className="h4 fw-semibold mb-3 text-secondary">1. Who We Are</h2> {/* Increased margin-bottom for headings */}
                <p>
                  This website is operated by a private individual based in the Czech Republic.
                  For any questions regarding this policy, please contact us at:{" "}
                  <span className="fw-medium text-primary">geneabase.polesie@gmail.com</span> {/* Highlighted email */}
                </p>
              </section>

              <section className="mb-4">
                <h2 className="h4 fw-semibold mb-3 text-secondary">2. What Data We Collect</h2>
                <ul className="list-unstyled ps-4"> {/* Increased padding-left for list items */}
                  <li><i className="fas fa-dot-circle text-primary me-2"></i>Email address (if submitted through the contact form)</li>
                  <li><i className="fas fa-dot-circle text-primary me-2"></i>Message content and name (if voluntarily provided)</li>
                  <li><i className="fas fa-dot-circle text-primary me-2"></i>IP address and browser information (for diagnostics and security)</li>
                </ul>
              </section>

              <section className="mb-4">
                <h2 className="h4 fw-semibold mb-3 text-secondary">3. How We Use Your Data</h2>
                <p>We use your personal data for the following purposes:</p>
                <ul className="list-unstyled ps-4 mt-2">
                  <li><i className="fas fa-dot-circle text-primary me-2"></i>To respond to messages sent via the contact form</li>
                  <li><i className="fas fa-dot-circle text-primary me-2"></i>To monitor website performance and maintain security</li>
                  <li><i className="fas fa-dot-circle text-primary me-2"></i>To comply with legal obligations if applicable</li>
                </ul>
                <p className="mt-2">
                  We do <strong>not</strong> share your data with third parties, nor do we use it for marketing.
                </p>
              </section>

              <section className="mb-4">
                <h2 className="h4 fw-semibold mb-3 text-secondary">4. Legal Basis for Processing</h2>
                <ul className="list-unstyled ps-4">
                  <li><i className="fas fa-dot-circle text-primary me-2"></i><strong>Consent</strong> – when you submit information voluntarily</li>
                  <li><i className="fas fa-dot-circle text-primary me-2"></i><strong>Legitimate interest</strong> – to operate and protect the website</li>
                </ul>
              </section>

              <section className="mb-4">
                <h2 className="h4 fw-semibold mb-3 text-secondary">5. Cookies and Tracking</h2>
                <p>
                  We use only essential cookies required for site functionality. We do not use third-party tracking or analytics cookies. You can disable cookies in your browser settings if desired.
                </p>
              </section>

              <section className="mb-4">
                <h2 className="h4 fw-semibold mb-3 text-secondary">6. Data Retention</h2>
                <p>
                  Contact form submissions may be stored for up to 12 months to ensure proper follow-up. Other data is retained only as long as necessary for its intended purpose.
                </p>
              </section>

              <section className="mb-4">
                <h2 className="h4 fw-semibold mb-3 text-secondary">7. Your Rights</h2>
                <p>As an EU resident, you have the right to:</p>
                <ul className="list-unstyled ps-4 mt-2">
                  <li><i className="fas fa-dot-circle text-primary me-2"></i>Access the personal data we hold about you</li>
                  <li><i className="fas fa-dot-circle text-primary me-2"></i>Request correction or deletion of your data</li>
                  <li><i className="fas fa-dot-circle text-primary me-2"></i>Withdraw your consent at any time</li>
                  <li><i className="fas fa-dot-circle text-primary me-2"></i>File a complaint with your data protection authority</li>
                </ul>
                <p className="mt-2">
                  To exercise your rights, contact us at:{" "}
                  <span className="fw-medium text-primary">geneabase.polesie@gmail.com</span> {/* Highlighted email */}
                </p>
              </section>

              <section className="mb-4">
                <h2 className="h4 fw-semibold mb-3 text-secondary">8. Data Security</h2>
                <p>
                  We implement reasonable security measures to protect your data from unauthorized access, disclosure, or loss.
                </p>
              </section>

              <section className="mb-4">
                <h2 className="h4 fw-semibold mb-3 text-secondary">9. Changes to This Policy</h2>
                <p>
                  This privacy policy may be updated occasionally. The most recent version will always be available at{" "}
                  <Link to="/privacy-policy" className="text-decoration-underline text-info fw-medium">http://www.familiarum.eu/privacy-policy</Link>. {/* Styled link */}
                </p>
              </section>

              <section className="mb-4">
                <h2 className="h4 fw-semibold mb-3 text-secondary">10. Additional Notice for Genealogy Research</h2>
                <p>
                  This website is intended for historical and genealogical research. Information about deceased individuals is
                  publicly accessible. Data about living individuals is either hidden or included only with explicit consent.
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

export default PrivacyPolicy;
