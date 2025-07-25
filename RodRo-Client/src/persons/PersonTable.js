import React, { useState, useEffect } from "react";
import { useSession } from "../contexts/session";
import { Link } from "react-router-dom";
import { Card, Button, Form, Pagination, InputGroup, Spinner, Alert } from "react-bootstrap";
import { apiGet } from "../utils/api";

// Helper function to format partial dates
const formatPartialDate = (year, month, day) => {
    if (year === null || year === undefined) {
        return "Unknown";
    }

    let dateParts = [year];
    if (month !== null && month !== undefined) {
        dateParts.push(String(month).padStart(2, '0'));
        if (day !== null && day !== undefined) {
            dateParts.push(String(day).padStart(2, '0'));
        }
    }
    return dateParts.join('-');
};

const PersonTable = ({ label, deletePerson }) => {
    // State variables for pagination, search, and sorting
    const [currentPage, setCurrentPage] = useState(0); // Backend is 0-indexed
    const [itemsPerPage, setItemsPerPage] = useState(10);
    const [searchTerm, setSearchTerm] = useState(""); // This state triggers API fetch for search
    const [localSearchTerm, setLocalSearchTerm] = useState(""); // This state is bound to the input field
    const [sortBy, setSortBy] = useState("givenName"); // Default sort field
    const [sortOrder, setSortOrder] = useState("asc"); // Default sort order
    const [personsPage, setPersonsPage] = useState({ content: [], totalElements: 0, totalPages: 0 }); // To store the Page object from backend
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    // Get session information to determine admin status
    const { session } = useSession();
    const isAdmin = session.data?.isAdmin === true;

    // Effect to fetch data from the backend
    useEffect(() => {
        const fetchPersons = async () => {
            setLoading(true);
            setError(null);
            try {
                // Construct query parameters for the API call
                const params = new URLSearchParams({
                    page: currentPage,
                    size: itemsPerPage,
                    sortBy: sortBy,
                    sortOrder: sortOrder,
                });
                if (searchTerm) {
                    params.append("searchTerm", searchTerm);
                }

                const data = await apiGet(`/api/persons?${params.toString()}`);
                setPersonsPage(data); // Assuming data directly contains content, totalElements, totalPages
            } catch (err) {
                console.error("Failed to fetch persons:", err);
                setError("Failed to load persons. Please try again later.");
            } finally {
                setLoading(false);
            }
        };

        fetchPersons();
    }, [currentPage, itemsPerPage, sortBy, sortOrder, searchTerm]); // Dependencies for re-fetching data

    // Handlers for pagination, items per page, sorting, and search
    const handlePageChange = (pageNumber) => setCurrentPage(pageNumber);
    const handleItemsPerPageChange = (e) => {
        setItemsPerPage(parseInt(e.target.value, 10) || 1);
        setCurrentPage(0); // Reset to first page when items per page changes
    };

    // New handler for column header clicks
    const handleHeaderClick = (field) => {
        if (sortBy === field) {
            // If clicking the same column, toggle sort order
            setSortOrder(prev => prev === "asc" ? "desc" : "asc");
        } else {
            // If clicking a new column, set it as sortBy and default to asc
            setSortBy(field);
            setSortOrder("asc");
        }
        setCurrentPage(0); // Always reset to the first page on sort change
    };

    // Handler to apply the search term from the input field
    const handleApplySearch = () => {
        setSearchTerm(localSearchTerm);
        setCurrentPage(0); // Reset to first page when search term changes
    };

    // Handler to clear the search term
    const handleClearSearch = () => {
        setLocalSearchTerm("");
        setSearchTerm(""); // Clear the actual search term to trigger API fetch
        setCurrentPage(0); // Reset to first page when search term changes
    };

    // Function to generate the range of page numbers to display in pagination
    const getPageRange = () => {
        const maxPagesToShow = 5;
        const totalPages = personsPage.totalPages;
        let startPage = Math.max(0, currentPage - Math.floor(maxPagesToShow / 2));
        let endPage = Math.min(totalPages - 1, startPage + maxPagesToShow - 1);

        if (endPage - startPage + 1 < maxPagesToShow) {
            startPage = Math.max(0, endPage - maxPagesToShow + 1);
        }

        const pages = [];
        for (let i = startPage; i <= endPage; i++) {
            pages.push(i);
        }
        return pages;
    };

    // Helper to render sort icon
    const renderSortIcon = (field) => {
        if (sortBy === field) {
            return sortOrder === 'asc' ? <i className="fas fa-sort-up ms-1"></i> : <i className="fas fa-sort-down ms-1"></i>;
        }
        return <i className="fas fa-sort ms-1 text-muted"></i>; // Default sort icon
    };

    if (loading) {
        return (
            <div className="container my-5 text-center">
                <Spinner animation="border" role="status">
                    <span className="visually-hidden">Loading...</span>
                </Spinner>
                <p className="mt-2">Loading persons...</p>
            </div>
        );
    }

    if (error) {
        return (
            <div className="container my-5">
                <Alert variant="danger">{error}</Alert>
            </div>
        );
    }

    return (
        <div className="container my-5">
            {/* Header with label and Create Person button */}
            <div className="d-flex justify-content-between align-items-center mb-4">
                <h2 className="mb-0 text-primary">{label} ({personsPage.totalElements})</h2>
                {isAdmin && (
                    <Link to="/persons/create" className="btn btn-success shadow-sm">
                        <i className="fas fa-plus me-2"></i>Create Person
                    </Link>
                )}
            </div>

            {/* Search and Filter Controls */}
            <Card className="mb-4 shadow-sm">
                <Card.Body>
                    <div className="d-flex flex-column flex-md-row justify-content-between align-items-center gap-3">
                        {/* Search bar and buttons */}
                        <InputGroup className="flex-grow-1">
                            <Form.Control
                                type="text"
                                placeholder="Search by full name or ID number..."
                                value={localSearchTerm} // Bind to localSearchTerm
                                onChange={(e) => setLocalSearchTerm(e.target.value)} // Update local state
                                className="rounded-start-lg"
                                onKeyPress={(e) => { // Allow pressing Enter to search
                                    if (e.key === 'Enter') {
                                        handleApplySearch();
                                    }
                                }}
                            />
                            <Button variant="primary" onClick={handleApplySearch}>
                                <i className="fas fa-search me-2"></i>Search
                            </Button>
                            <Button variant="outline-secondary" onClick={handleClearSearch} className="rounded-end-lg">
                                <i className="fas fa-times me-2"></i>Clear
                            </Button>
                        </InputGroup>

                        {/* Items per page select */}
                        <Form.Group className="d-flex align-items-center mb-0">
                            <Form.Label className="me-2 mb-0 text-nowrap">Records per page:</Form.Label>
                            <Form.Select
                                className="w-auto"
                                value={itemsPerPage}
                                onChange={handleItemsPerPageChange}
                            >
                                {[10, 20, 50, personsPage.totalElements ?? 0].map(val => (
                                    <option key={val} value={val}>
                                        {val === (personsPage.totalElements ?? 0) ? "All" : val}
                                    </option>
                                ))}
                            </Form.Select>
                        </Form.Group>
                    </div>
                </Card.Body>
            </Card>

            {/* Table to display person data */}
            <Card className="shadow-sm">
                <Card.Body className="p-0">
                    <div className="table-responsive">
                        <table className="table table-hover table-striped mb-0">
                            <thead className="bg-light">
                                <tr>
                                    <th className="py-3 px-4">#</th>
                                    <th className="py-3 px-4 clickable" onClick={() => handleHeaderClick("identificationNumber")}>
                                        GenWeb ID {renderSortIcon("identificationNumber")}
                                    </th>
                                    <th className="py-3 px-4 clickable" onClick={() => handleHeaderClick("givenName")}>
                                        Full Name {renderSortIcon("givenName")}
                                    </th>
                                    <th className="py-3 px-4 clickable" onClick={() => handleHeaderClick("birthYear")}>
                                        Years Of Life {renderSortIcon("birthYear")}
                                    </th>
                                    <th className="py-3 px-4 text-center" colSpan={3}>Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {personsPage.content.length > 0 ? (
                                    personsPage.content.map((item, index) => (
                                        <tr key={item.id}>
                                            <td className="py-2 px-4">{personsPage.number * personsPage.size + index + 1}</td>
                                            <td className="py-2 px-4">{item.identificationNumber || 'N/A'}</td>
                                            <td className="py-2 px-4">{item.givenName} {item.givenSurname}</td>
                                            <td className="py-2 px-4">
                                                {formatPartialDate(item.birthYear, item.birthMonth, item.birthDay)}
                                                <strong className="mx-1"> | </strong>
                                                {formatPartialDate(item.deathYear, item.deathMonth, item.deathDay)}
                                            </td>
                                            <td className="py-2 px-4 text-center">
                                                <div className="d-flex justify-content-center gap-2">
                                                    <Link to={`/persons/show/${item.id}`} className="btn btn-sm btn-info">
                                                        <i className="fas fa-eye"></i> View
                                                    </Link>
                                                    {isAdmin && (
                                                        <Link
                                                            to={"/persons/edit/" + item.id}
                                                            className="btn btn-sm btn-warning"
                                                        >
                                                            <i className="fas fa-edit"></i> Edit
                                                        </Link>
                                                    )}
                                                    {isAdmin && (
                                                        <Button
                                                            onClick={() => deletePerson(item.id)}
                                                            className="btn btn-sm btn-danger"
                                                        >
                                                            <i className="fas fa-trash-alt"></i> Delete
                                                        </Button>
                                                    )}
                                                </div>
                                            </td>
                                        </tr>
                                    ))
                                ) : (
                                    <tr>
                                        <td colSpan="6" className="text-center py-4 text-muted">No results found.</td>
                                    </tr>
                                )}
                            </tbody>
                        </table>
                    </div>
                </Card.Body>
            </Card>

            {/* Pagination controls */}
            <nav className="mt-4">
                <Pagination className="justify-content-center shadow-sm">
                    <Pagination.First onClick={() => handlePageChange(0)} disabled={currentPage === 0} />
                    <Pagination.Prev onClick={() => handlePageChange(currentPage - 1)} disabled={currentPage === 0} />
                    {getPageRange().map(page => (
                        <Pagination.Item
                            key={page}
                            active={page === currentPage}
                            onClick={() => handlePageChange(page)}
                        >
                            {page + 1}
                        </Pagination.Item>
                    ))}
                    <Pagination.Next onClick={() => handlePageChange(currentPage + 1)} disabled={currentPage === personsPage.totalPages - 1} />
                    <Pagination.Last onClick={() => handlePageChange(personsPage.totalPages - 1)} disabled={currentPage === personsPage.totalPages - 1} />
                </Pagination>
            </nav>
        </div>
    );
};

export default PersonTable;
