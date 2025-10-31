import React from "react";
import { Form, Button, Row, Col } from "react-bootstrap";

const PersonFilter = ({ handleChange, handleSubmit, handleClear, locationList, filter, confirm }) => {
  return (
    <Form onSubmit={handleSubmit}>
      {/* Name Filters */}
      <Row className="mb-3">
        <Col md={4}>
          <Form.Group>
            <Form.Label>First Name</Form.Label>
            <Form.Control type="text" name="firstName" value={filter.firstName || ""} onChange={handleChange} />
          </Form.Group>
        </Col>

        <Col md={4}>
          <Form.Group>
            <Form.Label>Last Name</Form.Label>
            <Form.Control type="text" name="lastName" value={filter.lastName || ""} onChange={handleChange} />
          </Form.Group>
        </Col>

              </Row>

      {/* Location Filters */}
      <Row className="mb-3">
        {[
          { label: "Birth Location", name: "personBirthLocationId" },
          { label: "Baptism Location", name: "personBaptismLocationId" },
          { label: "Death Location", name: "personDeathLocationId" },
          { label: "Burial Location", name: "personBurialLocationId" }
        ].map((field, index) => (
          <Col md={3} key={index}>
            <Form.Group>
              <Form.Label>{field.label}</Form.Label>
              <Form.Select name={field.name} value={filter[field.name] || ""} onChange={handleChange}>
                <option value="">Select a location</option>
                {locationList.map((location) => (
                  <option key={location._id} value={location._id}>
                    {location.name}
                  </option>
                ))}
              </Form.Select>
            </Form.Group>
          </Col>
        ))}
      </Row>

      {/* Date Filters */}
      <Row className="mb-3">
        {[
          { label: "Birth Date", name: "personBirthDate" },
          { label: "Baptism Date", name: "personBaptisDate" },
          { label: "Death Date", name: "personDeathDate" },
          { label: "Burial Date", name: "personBurialDate" }
        ].map((field, index) => (
          <Col md={3} key={index}>
            <Form.Group>
              <Form.Label>{field.label}</Form.Label>
              <Form.Control type="date" name={field.name} value={filter[field.name] || ""} onChange={handleChange} />
            </Form.Group>
          </Col>
        ))}
      </Row>

      {/* Buttons */}
      <div className="text-center">
        <Button variant="primary" type="submit" title="Filter persons">
          {confirm || "Filter Persons"}
        </Button>
        <Button variant="secondary" type="button" className="ms-2" onClick={handleClear}>
          Clear Filter
        </Button>
      </div>
    </Form>
  );
};

export default PersonFilter;
