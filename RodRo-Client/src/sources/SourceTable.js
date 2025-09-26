import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { useSession } from "../contexts/session";
import { apiGet } from "../utils/api";
import { Card, Button, Form, Pagination, InputGroup, Spinner, Alert } from "react-bootstrap";

const SourceTable = ({ label, deleteSource, refreshTrigger }) => {
    const { session } = useSession();
    const isAdmin = session?.data?.isAdmin === true;

    // Pagination, search, and sorting state
    const [currentPage, setCurrentPage] = useState(0);
    const [itemsPerPage, setItemsPerPage] = useState(10);
    const [searchTerm, setSearchTerm] = useState("");
    const [localSearchTerm, setLocalSearchTerm] = useState("");
    const [sortBy, setSortBy] = useState("title");
    const [sortOrder, setSortOrder] = useState("asc");
    const [sourcesPage, setSourcesPage] = useState({ content: [], totalElements: 0, totalPages: 0 });
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    // Fetch sources from backend
    useEffect(() => {
        const fetchSources = async () => {
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

                const data = await apiGet(`/api/sources?${params.toString()}`);
                setSourcesPage(data);
            } catch (err) {
                console.error("Failed to fetch sources:", err);
                setError("Failed to load sources. Please try again later.");
            } finally {
                setLoading(false);
            }
        };
        fetchSources();
    }, [currentPage, itemsPerPage, sortBy, sortOrder, searchTerm, refreshTrigger]);

    // Pagination, sorting, and search handlers
    const handlePageChange = (pageNumber) => setCurrentPage(pageNumber);

    const handleItemsPerPageChange = (e) => {
        setItemsPerPage(parseInt(e.target.value, 10) || 1);
        setCurrentPage(0);
    };

    const handleHeaderClick = (field) => {
        if (sortBy === field) setSortOrder(prev => prev === "asc" ? "desc" : "asc");
        else {
            setSortBy(field);
            setSortOrder("asc");
        }
        setCurrentPage(0);
    };

    const handleApplySearch = () => {
        setSearchTerm(localSearchTerm);
        setCurrentPage(0);
    };

    const handleClearSearch = () => {
        setLocalSearchTerm("");
        setSearchTerm("");
        setCurrentPage(0);
    };

    const getPageRange = () => {
        const maxPagesToShow = 5;
        const totalPages = sourcesPage.totalPages;
        let startPage = Math.max(0, currentPage - Math.floor(maxPagesToShow / 2));
        let endPage = Math.min(totalPages - 1, startPage + maxPagesToShow - 1);

        if (endPage - startPage + 1 < maxPagesToShow) startPage = Math.max(0, endPage - maxPagesToShow + 1);

        const pages = [];
        for (let i = startPage; i <= endPage; i++) pages.push(i);
        return pages;
    };

    const renderSortIcon = (field) => {
        if (sortBy === field) return sortOrder === "asc" ? <i className="fas fa-sort-up ms-1"></i> : <i className="fas fa-sort-down ms-1"></i>;
        return <i className="fas fa-sort ms-1 text-muted"></i>;
    };

    if (loading) return (
        <div className="container my-5 text-center">
            <Spinner animation="border" role="status">
                <span className="visually-hidden">Loading...</span>
            </Spinner>
            <p className="mt-2">Loading sources...</p>
        </div>
    );

    if (error) return (
        <div className="container my-5">
            <Alert variant="danger">{error}</Alert>
        </div>
    );

    return (
        <div className="container my-5">
            <div className="d-flex justify-content-between align-items-center mb-4">
                <h2 className="mb-0 text-primary">{label} ({sourcesPage.totalElements})</h2>
                {isAdmin && (
                    <Link to="/sources/create" className="btn btn-success shadow-sm">
                        <i className="fas fa-plus me-2"></i>Create Source
                    </Link>
                )}
            </div>

            <Card className="mb-4 shadow-sm">
                <Card.Body>
                    <div className="d-flex flex-column flex-md-row justify-content-between align-items-center gap-3">
                        <InputGroup className="flex-grow-1">
                            <Form.Control
                                type="text"
                                placeholder="Search by title, reference, type, description, or location..."
                                value={localSearchTerm}
                                onChange={(e) => setLocalSearchTerm(e.target.value)}
                                className="rounded-start-lg"
                                onKeyPress={(e) => { if (e.key === 'Enter') handleApplySearch(); }}
                            />
                            <Button variant="primary" onClick={handleApplySearch}>
                                <i className="fas fa-search me-2"></i>Search
                            </Button>
                            <Button variant="outline-secondary" onClick={handleClearSearch} className="rounded-end-lg">
                                <i className="fas fa-times me-2"></i>Clear
                            </Button>
                        </InputGroup>

                        <Form.Group className="d-flex align-items-center mb-0">
                            <Form.Label className="me-2 mb-0 text-nowrap">Records per page:</Form.Label>
                            <Form.Select
                                className="w-auto"
                                value={itemsPerPage}
                                onChange={handleItemsPerPageChange}
                            >
                                {[10, 20, 50, sourcesPage.totalElements ?? 0].map(val => (
                                    <option key={val} value={val}>
                                        {val === (sourcesPage.totalElements ?? 0) ? "All" : val}
                                    </option>
                                ))}
                            </Form.Select>
                        </Form.Group>
                    </div>
                </Card.Body>
            </Card>

            <Card className="shadow-sm">
                <Card.Body className="p-0">
                    <div className="table-responsive">
                        <table className="table table-hover table-striped mb-0">
                            <thead className="bg-light">
                                <tr>
                                    <th className="py-3 px-4">#</th>
                                    <th className="py-3 px-4 clickable" onClick={() => handleHeaderClick("title")}>Title {renderSortIcon("title")}</th>
                                    <th className="py-3 px-4 clickable" onClick={() => handleHeaderClick("reference")}>Reference {renderSortIcon("reference")}</th>
                                    <th className="py-3 px-4 clickable" onClick={() => handleHeaderClick("type")}>Type {renderSortIcon("type")}</th>
                                    <th className="py-3 px-4">Description</th>
                                    <th className="py-3 px-4">URL</th>
                                    <th className="py-3 px-4 clickable" onClick={() => handleHeaderClick("locationName")}>Location {renderSortIcon("locationName")}</th>
                                    <th className="py-3 px-4 text-center">Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {sourcesPage.content.length > 0 ? sourcesPage.content.map((item, idx) => (
                                    <tr key={item.id}>
                                        <td className="py-2 px-4">{sourcesPage.number * sourcesPage.size + idx + 1}</td>
                                        <td className="py-2 px-4">{item.title}</td>
                                        <td className="py-2 px-4">{item.reference || "-"}</td>
                                        <td className="py-2 px-4">{item.type}</td>
                                        <td className="py-2 px-4">{item.description || "-"}</td>
                                        <td className="py-2 px-4">
                                            {item.url ? <a href={item.url} target="_blank" rel="noopener noreferrer">Link</a> : "-"}
                                        </td>
                                        <td className="py-2 px-4">{item.locationName || "N/A"}</td>
                                        <td className="py-2 px-4 text-center">
                                            <div className="d-flex justify-content-center gap-2">
                                                <Link to={`/sources/show/${item.id}`} className="btn btn-sm btn-info">
                                                    <i className="fas fa-eye"></i> View
                                                </Link>
                                                {isAdmin && (
                                                    <>
                                                        <Link to={`/sources/edit/${item.id}`} className="btn btn-sm btn-warning">
                                                            <i className="fas fa-edit"></i> Edit
                                                        </Link>
                                                        <Button onClick={() => deleteSource(item.id)} className="btn btn-sm btn-danger">
                                                            <i className="fas fa-trash-alt"></i> Delete
                                                        </Button>
                                                    </>
                                                )}
                                            </div>
                                        </td>
                                    </tr>
                                )) : (
                                    <tr>
                                        <td colSpan="8" className="text-center py-4 text-muted">No results found.</td>
                                    </tr>
                                )}
                            </tbody>
                        </table>
                    </div>
                </Card.Body>
            </Card>

            <nav className="mt-4">
                <Pagination className="justify-content-center shadow-sm">
                    <Pagination.First onClick={() => handlePageChange(0)} disabled={currentPage === 0} />
                    <Pagination.Prev onClick={() => handlePageChange(currentPage - 1)} disabled={currentPage === 0} />
                    {getPageRange().map((page) => (
                        <Pagination.Item key={page} active={page === currentPage} onClick={() => handlePageChange(page)}>
                            {page + 1}
                        </Pagination.Item>
                    ))}
                    <Pagination.Next onClick={() => handlePageChange(currentPage + 1)} disabled={currentPage === sourcesPage.totalPages - 1} />
                    <Pagination.Last onClick={() => handlePageChange(sourcesPage.totalPages - 1)} disabled={currentPage === sourcesPage.totalPages - 1} />
                </Pagination>
            </nav>
        </div>
    );
};

export default SourceTable;
