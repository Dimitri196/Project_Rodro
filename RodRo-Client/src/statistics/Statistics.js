import React, { useEffect, useState } from "react";
import { apiGet } from "../utils/api";
import { Container, Row, Col, Card, Table, Alert } from 'react-bootstrap';  // Import React-Bootstrap components

const Statistics = () => {
    const [invoiceStats, setInvoiceStats] = useState({
        currentYearSum: 0,
        allTimeSum: 0,
        invoicesCount: 0
    });
    const [personStats, setPersonStats] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchStatistics = async () => {
            try {
                const invoiceData = await apiGet("/api/invoices/statistics");
                const personData = await apiGet("/api/persons/statistics");
                setInvoiceStats(invoiceData);
                setPersonStats(personData);
            } catch (err) {
                setError("Failed to load statistics. Please try again later.");
            } finally {
                setLoading(false);
            }
        };

        fetchStatistics();
    }, []);

    if (loading) return <div>Loading statistics...</div>;
    if (error) return <Alert variant="danger">{error}</Alert>;

    return (
        <Container className="mt-5">
            <h1>Statistics</h1>
            
            <h2>Invoice Statistics</h2>
            <Row>
                <Col md={4}>
                    <Card className="mb-4 shadow-sm">
                        <Card.Body>
                            <Card.Title>Current Year Revenue</Card.Title>
                            <Card.Text>{invoiceStats.currentYearSum} Kč</Card.Text>
                        </Card.Body>
                    </Card>
                </Col>
                <Col md={4}>
                    <Card className="mb-4 shadow-sm">
                        <Card.Body>
                            <Card.Title>All-Time Revenue</Card.Title>
                            <Card.Text>{invoiceStats.allTimeSum} Kč</Card.Text>
                        </Card.Body>
                    </Card>
                </Col>
                <Col md={4}>
                    <Card className="mb-4 shadow-sm">
                        <Card.Body>
                            <Card.Title>Invoice Count</Card.Title>
                            <Card.Text>{invoiceStats.invoicesCount}</Card.Text>
                        </Card.Body>
                    </Card>
                </Col>
            </Row>

            <h2>Person Statistics</h2>
            <Table striped bordered hover responsive className="mt-3">
                <thead>
                    <tr>
                        <th>Person ID</th>
                        <th>Name</th>
                        <th>Revenue (Kč)</th>
                    </tr>
                </thead>
                <tbody>
                    {personStats.map(({ id, name, revenue }) => (
                        <tr key={id}>
                            <td>{id}</td>
                            <td>{name}</td>
                            <td>{revenue} Kč</td>
                        </tr>
                    ))}
                </tbody>
            </Table>
        </Container>
    );
};

export default Statistics;
