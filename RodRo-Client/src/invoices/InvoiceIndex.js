import React, { useEffect, useState } from "react";
import { apiGet, apiDelete } from "../utils/api";
import InvoiceTable from "./InvoiceTable";
import InvoiceFilter from "./InvoiceFilter";
import { Container, Row, Col, Button, Card, Form, Alert } from 'react-bootstrap';  // Import React-Bootstrap components

const InvoiceIndex = () => {
  const [personListState, setPersonList] = useState([]);
  const [invoicesState, setInvoices] = useState([]);
  const [filterState, setFilter] = useState({
    buyerID: undefined,
    sellerID: undefined,
    minPrice: undefined,
    maxPrice: undefined,
    product: undefined,
    limit: undefined, // Default limit
  });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    // Fetch the list of persons (buyers and sellers) and invoices when the component mounts
    const fetchData = async () => {
      try {
        const personData = await apiGet("/api/persons");
        const invoiceData = await apiGet("/api/invoices");
        setPersonList(personData);
        setInvoices(invoiceData);
      } catch (err) {
        setError("Failed to load data. Please try again later.");
      } finally {
        setLoading(false);
      }
    };
    
    fetchData();
  }, []);

  const deleteInvoice = async (id) => {
    await apiDelete("/api/invoices/" + id);
    setInvoices(invoicesState.filter((invoice) => invoice._id !== id));
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    if (value === "" || value === "false" || value === "true") {
      setFilter((prevState) => ({ ...prevState, [name]: undefined }));
    } else {
      setFilter((prevState) => ({ ...prevState, [name]: value }));
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Clean up the filter parameters - remove undefined values
    const params = { ...filterState };
    Object.keys(params).forEach((key) => {
      if (params[key] === undefined || params[key] === null || params[key] === '') {
        delete params[key];
      }
    });

    console.log("Submitting with filters:", params);

    try {
      const filteredInvoices = await apiGet("/api/invoices", params);
      setInvoices(filteredInvoices);
    } catch (error) {
      console.error("Error fetching filtered invoices:", error);
      setError("Failed to fetch filtered invoices.");
    }
  };

  if (loading) return <div>Loading invoices...</div>;
  if (error) return <Alert variant="danger">{error}</Alert>;

  return (
    <Container className="mt-5">
      <h1>Seznam faktur</h1>
      <hr />

      {/* Invoice Filter Section */}
      <Card className="mb-4 shadow-sm">
        <Card.Body>
          <h3>Filtrovat faktury</h3>
          <InvoiceFilter
            handleChange={handleChange}
            handleSubmit={handleSubmit}
            personList={personListState}
            filter={filterState}
            confirm="Filtrovat faktury"
          />
        </Card.Body>
      </Card>

      <hr />

      {/* Invoice Table Section */}
      <InvoiceTable
        deleteInvoice={deleteInvoice}
        items={invoicesState}
        label="Počet faktur:"
      />
    </Container>
  );
};

export default InvoiceIndex;
