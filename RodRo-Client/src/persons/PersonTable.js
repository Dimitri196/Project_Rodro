import React, { useState, useEffect } from "react";
import { useSession } from "../contexts/session";
import { Link } from "react-router-dom";
import { Card, Button, Form, Pagination, InputGroup, Spinner, Alert, Row, Col, Dropdown } from "react-bootstrap";
import { apiGet } from "../utils/api";
import { remove as removeDiacritics } from "diacritics";

// Helper for partial date formatting
const formatPartialDate = (year, month, day) => {
    if (!year) return "Unknown";
    let parts = [year];
    if (month != null) parts.push(String(month).padStart(2, "0"));
    if (day != null) parts.push(String(day).padStart(2, "0"));
    return parts.join("-");
};

const PersonTable = ({ label, deletePerson, refreshTrigger }) => {
    const { session } = useSession();
    const isAdmin = session?.data?.isAdmin === true;

    // Pagination, search, sort
    const [currentPage, setCurrentPage] = useState(0);
    const [itemsPerPage, setItemsPerPage] = useState(10);
    const [searchTerm, setSearchTerm] = useState("");
    const [localSearchTerm, setLocalSearchTerm] = useState("");
    const [sortBy, setSortBy] = useState("givenName");
    const [sortOrder, setSortOrder] = useState("asc");

    const [personsPage, setPersonsPage] = useState({ content: [], totalElements: 0, totalPages: 0, number: 0, size: 10 });
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    // Custom styles for table
    const customStyles = `
        .scientific-table {
            --bs-table-bg: #fff;
            --bs-table-color: #343a40;
            --bs-table-border-color: #dee2e6;
            border-radius: 0.5rem;
            overflow: hidden;
            border-collapse: separate;
            border-spacing: 0;
            box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.05);
        }
        .scientific-table thead th {
            background-color: #007bff;
            color: #fff;
            font-weight: 600;
            border-bottom: 3px solid #0056b3;
            text-transform: uppercase;
            font-size: 0.85rem;
            padding: 1rem 0.75rem;
            vertical-align: middle;
            cursor: default;
            white-space: nowrap;
        }
        .scientific-table thead th.sortable { cursor: pointer; }
        .scientific-table tbody tr:hover { background-color: #f1f7fe; }
        .scientific-table td { font-size: 0.95rem; vertical-align: middle; padding: 0.75rem; }
        .admin-action-dropdown .dropdown-toggle { padding: 0.25rem 0.5rem; }
        .admin-action-dropdown .dropdown-menu { min-width: auto; }
        .action-cell { text-align: center; display: flex; align-items: center; justify-content: center; gap: 8px; }
        .person-name-text { font-weight: 600; color: #343a40; }
    `;

    // --- Fetch Data ---
    useEffect(() => {
        const fetchPersons = async () => {
            setLoading(true);
            setError(null);
            try {
                const params = new URLSearchParams({
                    page: currentPage,
                    size: itemsPerPage,
                    sortBy,
                    sortOrder
                });
                if (searchTerm) params.append("searchTerm", searchTerm);

                const data = await apiGet(`/api/persons?${params.toString()}`);
                setPersonsPage(data);
            } catch (err) {
                setError(err.message || "Failed to fetch persons. Check network or API.");
                setPersonsPage({ content: [], totalElements: 0, totalPages: 0, number: 0, size: 10 });
            } finally {
                setLoading(false);
            }
        };
        fetchPersons();
    }, [currentPage, itemsPerPage, sortBy, sortOrder, searchTerm, refreshTrigger]);

    // --- Handlers ---
    const handlePageChange = (page) => setCurrentPage(page);
    const handleItemsPerPageChange = (e) => { setItemsPerPage(parseInt(e.target.value, 10) || 1); setCurrentPage(0); };
    const handleHeaderClick = (field) => {
        const sortableFields = ["identificationNumber", "givenName", "birthYear"];
        if (!sortableFields.includes(field)) return;
        if (sortBy === field) setSortOrder(prev => prev === "asc" ? "desc" : "asc");
        else { setSortBy(field); setSortOrder("asc"); }
        setCurrentPage(0);
    };
    const handleApplySearch = () => { setSearchTerm(removeDiacritics(localSearchTerm).toLowerCase().trim()); setCurrentPage(0); };
    const handleClearSearch = () => { setLocalSearchTerm(""); setSearchTerm(""); setCurrentPage(0); };
    const getPageRange = () => {
        const maxPagesToShow = 5;
        const totalPages = personsPage.totalPages;
        let start = Math.max(currentPage - Math.floor(maxPagesToShow / 2), 0);
        let end = Math.min(totalPages - 1, start + maxPagesToShow - 1);
        if (end - start + 1 < maxPagesToShow) start = Math.max(0, end - maxPagesToShow + 1);
        return Array.from({ length: end - start + 1 }, (_, i) => start + i);
    };
    const renderSortIcon = (field) => sortBy === field ? (sortOrder === 'asc' ? <i className="fas fa-caret-up ms-2 fa-sm"></i> : <i className="fas fa-caret-down ms-2 fa-sm"></i>) : <i className="fas fa-sort ms-2 text-white-50 fa-sm"></i>;

    if (loading) return (
        <div className="container my-5 p-5 text-center bg-light rounded shadow-sm">
            <Spinner animation="border" role="status" />
            <p className="mt-3 text-muted">Fetching persons...</p>
        </div>
    );

    if (error) return (
        <div className="container my-5">
            <Alert variant="danger" className="shadow-sm">{error}</Alert>
        </div>
    );

    return (
        <div className="container my-5">
            <style>{customStyles}</style>

            {/* Header & Actions */}
            <Row className="mb-4 align-items-center">
                <Col md={4} className="d-flex align-items-center">
                    <h5 className="mb-0 fw-bold text-dark">
                        {label} <span className="text-primary">{personsPage.totalElements}</span>
                    </h5>
                    {isAdmin && (
                        <Link to="/persons/create" className="btn btn-success btn-sm ms-3 shadow-sm">
                            <i className="fas fa-plus me-1"></i>Add Person
                        </Link>
                    )}
                </Col>
                <Col md={5}>
                    <InputGroup>
                        <InputGroup.Text className="bg-white"><i className="fas fa-search"></i></InputGroup.Text>
                        <Form.Control
                            type="text"
                            placeholder="Search by full name or ID..."
                            value={localSearchTerm}
                            onChange={(e) => setLocalSearchTerm(e.target.value)}
                            onKeyPress={(e) => { if (e.key === 'Enter') handleApplySearch(); }}
                        />
                        <Button variant="primary" onClick={handleApplySearch}>Search</Button>
                        <Button variant="outline-secondary" onClick={handleClearSearch}><i className="fas fa-times"></i></Button>
                    </InputGroup>
                </Col>
                <Col md={3} className="d-flex justify-content-end">
                    <div className="d-flex align-items-center">
                        <label className="me-2 mb-0 fw-semibold text-muted text-nowrap">Records:</label>
                        <Form.Select size="sm" value={itemsPerPage} onChange={handleItemsPerPageChange} style={{ width: '80px' }}>
                            {[10, 20, 50, personsPage.totalElements ?? 0].map(val => (
                                <option key={val} value={val}>{val === (personsPage.totalElements ?? 0) ? "All" : val}</option>
                            ))}
                        </Form.Select>
                    </div>
                </Col>
            </Row>

            {/* Table */}
            <div className="table-responsive shadow-lg rounded">
                <table className="table scientific-table">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th onClick={() => handleHeaderClick("externalId")} className="sortable">ID {renderSortIcon("externalId")}</th>
                            <th onClick={() => handleHeaderClick("givenName")} className="sortable">Full Name {renderSortIcon("givenName")}</th>
                            <th onClick={() => handleHeaderClick("birthYear")} className="sortable">Years {renderSortIcon("birthYear")}</th>
                            <th className="text-center" style={{ width: isAdmin ? '200px' : '150px' }}>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {personsPage.content.length > 0 ? personsPage.content.map((item, idx) => (
                            <tr key={item.id}>
                                <td className="text-muted">{personsPage.number * personsPage.size + idx + 1}</td>
                                <td>{item.externalId || <i className="text-muted">N/A</i>}</td>
                                <td className="person-name-text">{item.givenName} {item.surname}</td>
                                <td>{formatPartialDate(item.birthYear, item.birthMonth, item.birthDay)} – {formatPartialDate(item.deathYear, item.deathMonth, item.deathDay)}</td>
                                <td className="action-cell">
                                    <Link to={`/persons/show/${item.id}`} className="btn btn-primary btn-sm rounded-pill px-3 fw-semibold"><i className="fas fa-eye me-1"></i>View</Link>
                                    {isAdmin && (
                                        <Dropdown align="end" className="admin-action-dropdown">
                                            <Dropdown.Toggle variant="secondary" size="sm" className="rounded-circle"><i className="fas fa-tools"></i></Dropdown.Toggle>
                                            <Dropdown.Menu className="shadow">
                                                <Dropdown.Item as={Link} to={`/persons/edit/${item.id}`}><i className="fas fa-edit me-2 text-warning"></i>Edit</Dropdown.Item>
                                                <Dropdown.Divider />
                                                <Dropdown.Item onClick={() => deletePerson(item.id)} className="text-danger"><i className="fas fa-trash-alt me-2"></i>Delete</Dropdown.Item>
                                            </Dropdown.Menu>
                                        </Dropdown>
                                    )}
                                </td>
                            </tr>
                        )) : (
                            <tr>
                                <td colSpan={isAdmin ? 5 : 4} className="text-center text-muted py-4"><i className="fas fa-exclamation-triangle me-2"></i>No persons found.</td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>

            {/* Pagination */}
            <div className="d-flex justify-content-between align-items-center mt-4 flex-column flex-sm-row">
                <p className="text-muted mb-2 mb-sm-0 small">
                    Showing <strong>{personsPage.number * personsPage.size + 1}</strong> – <strong>{personsPage.number * personsPage.size + personsPage.content.length}</strong> of <strong>{personsPage.totalElements}</strong> records
                </p>
                <Pagination size="sm" className="shadow-sm">
                    <Pagination.First onClick={() => handlePageChange(0)} disabled={currentPage === 0} />
                    <Pagination.Prev onClick={() => handlePageChange(currentPage - 1)} disabled={currentPage === 0} />
                    {getPageRange().map(p => <Pagination.Item key={p} active={p === currentPage} onClick={() => handlePageChange(p)}>{p + 1}</Pagination.Item>)}
                    <Pagination.Next onClick={() => handlePageChange(currentPage + 1)} disabled={currentPage === personsPage.totalPages - 1} />
                    <Pagination.Last onClick={() => handlePageChange(personsPage.totalPages - 1)} disabled={currentPage === personsPage.totalPages - 1} />
                </Pagination>
            </div>
        </div>
    );
};

export default PersonTable;
