import React, { useState, useEffect } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { Form, Button, Alert, Container, Row, Col, Card } from "react-bootstrap";
import { apiPost } from "../utils/api";
import { useNavigate } from "react-router-dom";

const ContactMessage = () => {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    name: "",
    email: "",
    message: "",
  });

  const [sent, setSent] = useState(false);
  const [error, setError] = useState(null);
  const [countdown, setCountdown] = useState(5);

  useEffect(() => {
    if (sent) {
      const interval = setInterval(() => {
        setCountdown((prev) => {
          if (prev <= 1) {
            clearInterval(interval);
            return 0;
          }
          return prev - 1;
        });
      }, 1000);

      const timeout = setTimeout(() => navigate("/"), 5000);

      return () => {
        clearInterval(interval);
        clearTimeout(timeout);
      };
    }
  }, [sent, navigate]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setError(null);

    apiPost("/api/contact", formData)
      .then(() => setSent(true))
      .catch((err) => {
        console.error(err);
        setError("Failed to send message. Please try again.");
      });
  };

  return (
    <Container className="mt-5 py-4">
      <Row className="mb-4">
        <Col md={8} className="offset-md-2 text-center">
          <h2 className="fw-bold text-primary mb-2">
            <i className="fas fa-envelope-open-text me-2"></i> Contact Us
          </h2>
          <p className="text-muted">We’d love to hear from you. Drop us a quick message below.</p>
        </Col>
      </Row>

      {sent ? (
        <Alert variant="success" className="text-center py-4 rounded-3 shadow-sm">
          <h4 className="alert-heading">
            <i className="fas fa-check-circle me-2"></i> Message Sent!
          </h4>
          <p>Your message has been sent successfully. We’ll get back to you soon.</p>
          <hr />
          <p className="mb-0">
            Redirecting to homepage in <strong>{countdown}</strong> seconds...
          </p>
        </Alert>
      ) : (
        <Row className="justify-content-center">
          <Col md={8} lg={6}>
            <Card className="shadow-sm border-0 rounded-4">
              <Card.Body className="p-4">
                {error && <Alert variant="danger" className="text-center">{error}</Alert>}

                <Form onSubmit={handleSubmit}>
                  <Form.Group className="mb-3" controlId="name">
                    <Form.Label>Name</Form.Label>
                    <Form.Control
                      type="text"
                      name="name"
                      value={formData.name}
                      onChange={handleChange}
                      required
                      maxLength={100}
                      placeholder="Enter your name"
                      className="rounded-pill"
                    />
                  </Form.Group>

                  <Form.Group className="mb-3" controlId="email">
                    <Form.Label>Email</Form.Label>
                    <Form.Control
                      type="email"
                      name="email"
                      value={formData.email}
                      onChange={handleChange}
                      required
                      maxLength={100}
                      placeholder="Enter your email"
                      className="rounded-pill"
                    />
                  </Form.Group>

                  <Form.Group className="mb-4" controlId="message">
                    <Form.Label>Message</Form.Label>
                    <Form.Control
                      as="textarea"
                      rows={4}
                      name="message"
                      value={formData.message}
                      onChange={handleChange}
                      required
                      maxLength={1000}
                      placeholder="Write your message..."
                      className="rounded-3"
                    />
                  </Form.Group>

                  <div className="text-center">
                    <Button
                      variant="primary"
                      type="submit"
                      className="w-100 rounded-pill py-2 shadow-sm"
                    >
                      <i className="fas fa-paper-plane me-2"></i> Send Message
                    </Button>
                  </div>
                </Form>
              </Card.Body>
            </Card>
          </Col>
        </Row>
      )}

      {/* Google Map */}
      <Row className="mt-5">
        <Col md={12}>
          <Card className="shadow-lg rounded-4 border-0">
            <Card.Header className="text-center bg-primary text-white py-3 rounded-top-4">
              <h5 className="mb-0">
                <i className="fas fa-map-marker-alt me-2"></i> Our Location
              </h5>
            </Card.Header>
            <Card.Body className="p-0">
              <div style={{ height: "350px" }}>
                <iframe
                  title="Google Map - Pinsk, Belarus"
                  className="w-100 h-100 rounded-bottom-4"
                  src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d313243.41275915655!2d25.8334876897705!3d52.16679956174738!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x4727a1374eea7ead%3A0x14180b1bbb440aa1!2sPinsk%2C%20Brest%20Region%2C%20Belarus!5e0!3m2!1sen!2scz!4v1753681810644!5m2!1sen!2scz"
                  allowFullScreen=""
                  loading="lazy"
                ></iframe>
              </div>
            </Card.Body>
          </Card>
        </Col>
      </Row>
    </Container>
  );
};

export default ContactMessage;
