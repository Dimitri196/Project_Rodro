import React, { useState, useEffect } from "react";
import { Form, Button, Alert, Container } from "react-bootstrap";
import { apiPost } from "../utils/api";
import { useNavigate } from "react-router-dom";

const ContactMessage = () => {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    name: "",
    email: "",
    message: ""
  });

  const [sent, setSent] = useState(false);
  const [error, setError] = useState(null);
  const [countdown, setCountdown] = useState(5); // Countdown starts at 5

useEffect(() => {
  if (sent) {
    const interval = setInterval(() => {
      setCountdown(prev => {
        if (prev <= 1) {
          clearInterval(interval);
          return 0;
        }
        return prev - 1;
      });
    }, 1000);

    // Delayed redirect after 5 seconds
    const timeout = setTimeout(() => navigate("/"), 5000);

    return () => {
      clearInterval(interval);
      clearTimeout(timeout);
    };
  }
}, [sent, navigate]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    setError(null);

    apiPost("/api/contact", formData)
      .then(() => {
        setSent(true);
      })
      .catch((err) => {
        console.error(err);
        setError("Failed to send message. Please try again.");
      });
  };

  return (
    <Container className="mt-5">
      <h2 className="mb-4 text-center">Contact Us</h2>

      {sent ? (
        <Alert variant="success" className="text-center">
          <p>Your message has been sent successfully!</p>
          <small>Redirecting to homepage in <strong>{countdown}</strong> seconds...</small>
        </Alert>
      ) : (
        <Form onSubmit={handleSubmit}>
          {error && <Alert variant="danger">{error}</Alert>}

          <Form.Group className="mb-3" controlId="name">
            <Form.Label>Name</Form.Label>
            <Form.Control
              type="text"
              name="name"
              value={formData.name}
              onChange={handleChange}
              required
              maxLength={100}
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
            />
          </Form.Group>

          <Form.Group className="mb-3" controlId="message">
            <Form.Label>Message</Form.Label>
            <Form.Control
              as="textarea"
              rows={5}
              name="message"
              value={formData.message}
              onChange={handleChange}
              required
              maxLength={2000}
            />
          </Form.Group>

          <div className="text-center">
            <Button variant="primary" type="submit">
              Send Message
            </Button>
          </div>
        </Form>
      )}
    </Container>
  );
};

export default ContactMessage;
