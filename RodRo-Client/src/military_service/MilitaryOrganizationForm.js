import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { Form, Button, Container, Row, Col, Card, Alert, Spinner } from "react-bootstrap";
import { apiGet, apiPost, apiPut } from "../utils/api";

const MilitaryOrganizationForm = () => {
  const navigate = useNavigate();
  const { id } = useParams();

  const [organization, setOrganization] = useState({
    armyName: "",
    armyBranch: null,
    country: null,
    activeFromYear: "",
    activeToYear: "",
    organizationDescription: "",
    organizationImageUrl: "",
  });

  const [branches, setBranches] = useState([]);
  const [countries, setCountries] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [sent, setSent] = useState(false);
  const [success, setSuccess] = useState(false);

  // Fetch branches, countries, and organization if editing
  useEffect(() => {
    const fetchData = async () => {
      try {
        const [branchData, countryData] = await Promise.all([
          apiGet("/api/militaryArmyBranches?size=1000"),
          apiGet("/api/countries?size=1000")
        ]);
        setBranches(branchData || []);
        setCountries(countryData || []);

        if (id) {
          const orgData = await apiGet(`/api/militaryOrganizations/${id}`);
          setOrganization({
            ...orgData,
            armyBranch: orgData.armyBranch || null,
            country: orgData.country || null,
          });
        }
      } catch (err) {
        setError("Failed to load initial data: " + err.message);
      } finally {
        setLoading(false);
      }
    };
    fetchData();
  }, [id]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setSent(false);
    setError(null);

    if (!organization.armyBranch || !organization.armyBranch.id) {
      setError("Army branch must be selected");
      return;
    }

    if (!organization.country || !organization.country.id) {
      setError("Country must be selected");
      return;
    }

    const payload = {
      ...organization,
      armyBranch: { id: organization.armyBranch.id },
      country: { id: organization.country.id },
      activeFromYear: organization.activeFromYear ? Number(organization.activeFromYear) : null,
      activeToYear: organization.activeToYear ? Number(organization.activeToYear) : null,
    };

    try {
      if (id) {
        await apiPut(`/api/militaryOrganizations/${id}`, payload);
      } else {
        await apiPost("/api/militaryOrganizations", payload);
      }
      setSent(true);
      setSuccess(true);
      setTimeout(() => navigate("/militaryOrganizations"), 1000);
    } catch (err) {
      setError(err.message || "Failed to save organization");
      setSent(true);
      setSuccess(false);
    }
  };

  if (loading) {
    return (
      <Container className="my-5 text-center">
        <Spinner animation="border" role="status" />
        <p className="mt-3 text-muted">Loading data...</p>
      </Container>
    );
  }

  return (
    <Container className="my-5">
      <Card className="shadow-sm border-0 rounded-3">
        <Card.Body>
          <h3 className="text-center text-primary mb-4">
            {id ? "Update Military Organization" : "Create Military Organization"}
          </h3>

          {error && <Alert variant="danger">{error}</Alert>}
          {sent && success && <Alert variant="success">Organization saved successfully.</Alert>}
          {sent && !success && <Alert variant="danger">Error saving organization.</Alert>}

          <Form onSubmit={handleSubmit}>
            <Row>
              <Col md={6}>
                <Form.Group className="mb-3" controlId="armyName">
                  <Form.Label>Army Name</Form.Label>
                  <Form.Control
                    type="text"
                    required
                    value={organization.armyName}
                    onChange={(e) =>
                      setOrganization({ ...organization, armyName: e.target.value })
                    }
                    placeholder="Enter army name"
                  />
                </Form.Group>
              </Col>

              <Col md={6}>
                <Form.Group className="mb-3" controlId="armyBranch">
                  <Form.Label>Army Branch</Form.Label>
                  <Form.Select
                    required
                    value={organization.armyBranch?.id || ""}
                    onChange={(e) =>
                      setOrganization({
                        ...organization,
                        armyBranch: branches.find(b => b.id === Number(e.target.value)) || null
                      })
                    }
                  >
                    <option value="">Select branch</option>
                    {branches.map(branch => (
                      <option key={branch.id} value={branch.id}>{branch.armyBranchName}</option>
                    ))}
                  </Form.Select>
                </Form.Group>
              </Col>
            </Row>

            <Row>
              <Col md={6}>
                <Form.Group className="mb-3" controlId="country">
                  <Form.Label>Country</Form.Label>
                  <Form.Select
                    required
                    value={organization.country?.id || ""}
                    onChange={(e) =>
                      setOrganization({
                        ...organization,
                        country: countries.find(c => c.id === Number(e.target.value)) || null
                      })
                    }
                  >
                    <option value="">Select country</option>
                    {countries.map(country => (
                      <option key={country.id} value={country.id}>{country.name}</option>
                    ))}
                  </Form.Select>
                </Form.Group>
              </Col>

              <Col md={3}>
                <Form.Group className="mb-3" controlId="activeFromYear">
                  <Form.Label>Active From Year</Form.Label>
                  <Form.Control
                    type="number"
                    value={organization.activeFromYear || ""}
                    onChange={(e) =>
                      setOrganization({ ...organization, activeFromYear: e.target.value })
                    }
                  />
                </Form.Group>
              </Col>

              <Col md={3}>
                <Form.Group className="mb-3" controlId="activeToYear">
                  <Form.Label>Active To Year</Form.Label>
                  <Form.Control
                    type="number"
                    value={organization.activeToYear || ""}
                    onChange={(e) =>
                      setOrganization({ ...organization, activeToYear: e.target.value })
                    }
                  />
                </Form.Group>
              </Col>
            </Row>

            <Form.Group className="mb-3" controlId="organizationDescription">
              <Form.Label>Description</Form.Label>
              <Form.Control
                as="textarea"
                rows={3}
                value={organization.organizationDescription || ""}
                onChange={(e) =>
                  setOrganization({ ...organization, organizationDescription: e.target.value })
                }
                placeholder="Optional description"
              />
            </Form.Group>

            <Form.Group className="mb-3" controlId="organizationImageUrl">
              <Form.Label>Image URL</Form.Label>
              <Form.Control
                type="text"
                value={organization.organizationImageUrl || ""}
                onChange={(e) =>
                  setOrganization({ ...organization, organizationImageUrl: e.target.value })
                }
                placeholder="Optional image URL"
              />
            </Form.Group>

            <div className="d-flex justify-content-end">
              <Button type="submit" variant="primary" className="px-4">
                {id ? "Update" : "Create"}
              </Button>
            </div>
          </Form>
        </Card.Body>
      </Card>
    </Container>
  );
};

export default MilitaryOrganizationForm;
