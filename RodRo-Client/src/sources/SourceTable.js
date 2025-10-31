import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import { useSession } from "../contexts/session";
import { apiGet } from "../utils/api";
import { Button, Form, Pagination, InputGroup, Spinner, Alert, Row, Col, Dropdown } from "react-bootstrap";
import "@fortawesome/fontawesome-free/css/all.min.css"; 

import { SOURCE_TYPE_MAP } from '../constants/sourceType'; 

const SourceTable = ({ label, deleteSource, refreshTrigger }) => {
    const { session } = useSession();
    const isAdmin = session?.data?.isAdmin === true;

    // --- State Initialization ---
    const [currentPage, setCurrentPage] = useState(0);
    const [itemsPerPage, setItemsPerPage] = useState(10);
    const [searchTerm, setSearchTerm] = useState("");
    const [localSearchTerm, setLocalSearchTerm] = useState("");
    
    // Sort state: Default sort on 'creationYear'
    const [sortBy, setSortBy] = useState("creationYear"); 
    const [sortOrder, setSortOrder] = useState("desc"); 
    
    const [sourcesPage, setSourcesPage] = useState({ content: [], totalElements: 0, totalPages: 0, number: 0, size: 10 });
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    // --- Custom Styles (Revised) ---
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
            white-space: nowrap; /* Prevent wrapping on headers */
        }
        
        /* Set pointer cursor only for sortable columns */
        .scientific-table thead th.sortable {
            cursor: pointer;
        }
        
        .scientific-table tbody tr {
            transition: background-color 0.2s ease;
        }

        .scientific-table tbody tr:hover {
            background-color: #f1f7fe;
        }

        .scientific-table td {
            font-size: 0.95rem;
            vertical-align: middle;
            padding: 0.75rem;
        }
        
        /* Admin dropdown custom styling */
        .admin-action-dropdown .dropdown-toggle {
            padding: 0.25rem 0.5rem; 
        }

        .admin-action-dropdown .dropdown-menu {
            min-width: auto;
        }

        .action-cell {
            text-align: center;
            /* Ensure primary action button and dropdown are spaced */
            display: flex;
            align-items: center;
            justify-content: center; 
            gap: 8px; /* Spacing between view button and admin tools */
        }
        
        /* Ensure title text is not mistaken for a link */
        .source-title-text {
            color: #343a40; /* Standard dark text color */
            font-weight: 600; 
        }
    `;

    // --- useEffect: Data Fetching ---
    useEffect(() => {
        const fetchSources = async () => {
            setLoading(true);
            setError(null);
            try {
                const params = new URLSearchParams({
                    page: currentPage,
                    size: itemsPerPage,
                    sortBy: sortBy,
                    sortOrder: sortOrder,
                });
                if (searchTerm) params.append("searchTerm", searchTerm);

                const data = await apiGet(`/api/sources?${params.toString()}`);
                setSourcesPage(data);
            } catch (err) {
                const errorMessage = err.message || "Failed to fetch sources. Check network connection or API status.";
                setError(errorMessage);
                setSourcesPage({ content: [], totalElements: 0, totalPages: 0, number: 0, size: 10 });
            } finally {
                setLoading(false);
            }
        };
        fetchSources();
    }, [currentPage, itemsPerPage, sortBy, sortOrder, searchTerm, refreshTrigger]);

    // --- Handlers ---
    const handlePageChange = (pageNumber) => setCurrentPage(pageNumber);

    const handleItemsPerPageChange = (e) => {
        setItemsPerPage(parseInt(e.target.value, 10) || 1);
        setCurrentPage(0);
    };

    const handleHeaderClick = (field) => {
        // Define which fields are sortable for icon rendering and click logic
        const sortableFields = ["startYear", "creationYear", "locationName"];
        if (!sortableFields.includes(field)) return;

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

    // --- Helpers ---
    const getPageRange = () => {
        const maxPagesToShow = 5;
        const totalPages = sourcesPage.totalPages;
        const oneBasedCurrentPage = currentPage + 1;
        
        let startPage = Math.max(oneBasedCurrentPage - Math.floor(maxPagesToShow / 2), 1);
        let endPage = Math.min(totalPages, startPage + maxPagesToShow - 1);

        if (endPage - startPage + 1 < maxPagesToShow) {
             startPage = Math.max(endPage - maxPagesToShow + 1, 1);
        }

        const pages = [];
        for (let i = startPage; i <= endPage; i++) pages.push(i - 1);
        return pages;
    };

    const renderSortIcon = (field) => {
        const sortableFields = ["startYear", "creationYear", "locationName"];
        
        if (sortBy === field) return sortOrder === "asc" 
            ? <i className="fas fa-caret-up ms-2 fa-sm"></i> 
            : <i className="fas fa-caret-down ms-2 fa-sm"></i>;
        
        // Render a subtle sort icon for other sortable columns
        if (sortableFields.includes(field)) {
             return <i className="fas fa-sort ms-2 text-white-50 fa-sm"></i>;
        }
        
        return null;
    };
    
    const renderTypeBadge = (enumKey) => {
        if (!enumKey) return <span className="badge bg-secondary fw-semibold">Unknown</span>;
        
        const typeDisplayName = SOURCE_TYPE_MAP[enumKey] || enumKey.replace(/_/g, ' '); 

        const variantMap = {
            'Archival Document': 'primary', 'Church Record': 'success', 'Civil Registry': 'info',
            'Census': 'warning', 'Military Record': 'danger', 'Tax Record': 'secondary',
            'Newspaper': 'dark', 'Book / Publication': 'primary', 'Oral History': 'secondary',
            'Photograph': 'info', 'Map': 'danger', 'Legal Document': 'success',
            'Personal Correspondence': 'warning', 'Database / Digital Source': 'dark', 'Website': 'info',
            'Unknown': 'secondary', 'Not Applicable': 'light',
        };

        const variant = variantMap[typeDisplayName] || 'secondary';

        return (
            <span className={`badge bg-${variant} fw-semibold text-nowrap`}>
                {typeDisplayName}
            </span>
        );
    };

    const renderDataRange = (startYear, endYear) => {
        if (startYear && endYear) return `${startYear}–${endYear}`;
        if (startYear) return `${startYear}–?`;
        if (endYear) return `?–${endYear}`;
        return <i className="text-muted">N/A</i>;
    };
    
    const renderCreationYear = (creationYear) => {
        return creationYear || <i className="text-muted">N/A</i>;
    };


    // --- Loading/Error Views ---
    if (loading) return (
        <div className="container my-5 p-5 text-center bg-light rounded shadow-sm">
            <Spinner animation="border" role="status" variant="primary">
                <span className="visually-hidden">Loading...</span>
            </Spinner>
            <p className="mt-3 text-muted">Fetching source data...</p>
        </div>
    );

    if (error) return (
        <div className="container my-5">
            <Alert variant="danger" className="shadow-sm">{error}</Alert>
        </div>
    );

    // --- Main Render ---
    return (
        <div className="container my-5">
            <style>{customStyles}</style>

            {/* Control Panel (Same as before) */}
            <Row className="mb-4 align-items-center">
                <Col md={4} className="d-flex align-items-center">
                    <h5 className="mb-0 fw-bold text-dark">
                        {label} <span className="text-primary">{sourcesPage.totalElements}</span>
                    </h5>
                    {isAdmin && (
                        <Link to="/sources/create" className="btn btn-success btn-sm ms-3 shadow-sm">
                            <i className="fas fa-plus me-1"></i>Add source
                        </Link>
                    )}
                </Col>
                <Col md={5}>
                    <InputGroup>
                        <InputGroup.Text className="bg-white"><i className="fas fa-search"></i></InputGroup.Text>
                        <Form.Control
                            type="text"
                            placeholder="Search sources (title, reference, location name)..."
                            value={localSearchTerm}
                            onChange={(e) => setLocalSearchTerm(e.target.value)}
                            onKeyPress={(e) => { if (e.key === 'Enter') handleApplySearch(); }}
                        />
                        <Button variant="primary" onClick={handleApplySearch} className="shadow-sm">
                            Search
                        </Button>
                        <Button variant="outline-secondary" onClick={handleClearSearch}>
                            <i className="fas fa-times"></i>
                        </Button>
                    </InputGroup>
                </Col>
                <Col md={3} className="d-flex justify-content-end">
                    <div className="d-flex align-items-center">
                        <label className="me-2 mb-0 fw-semibold text-muted text-nowrap">Records:</label>
                        <Form.Select
                            size="sm"
                            value={itemsPerPage}
                            onChange={handleItemsPerPageChange}
                            style={{ width: '80px' }}
                            className="shadow-sm"
                        >
                            {[10, 20, 50, sourcesPage.totalElements ?? 0].map(val => (
                                <option key={val} value={val}>
                                    {val === (sourcesPage.totalElements ?? 0) ? "All" : val}
                                </option>
                            ))}
                        </Form.Select>
                    </div>
                </Col>
            </Row>

            {/* Table Card */}
            <div className="table-responsive shadow-lg rounded">
                <table className="table scientific-table">
                    <thead>
                        <tr>
                            <th style={{ width: '40px' }}>#</th>
                            
                            {/* Title - Link Removed */}
                            <th>Title {renderSortIcon("title")}</th>
                            
                            {/* Reference - Not Sortable */}
                            <th>Reference {renderSortIcon("reference")}</th>
                            
                            {/* Data Range (Sortable) */}
                            <th onClick={() => handleHeaderClick("startYear")} className="sortable text-nowrap" style={{ width: '100px' }}>
                                Data Range {renderSortIcon("startYear")}
                            </th>
                            
                            {/* Creation Year (Sortable) */}
                            <th onClick={() => handleHeaderClick("creationYear")} className="sortable text-nowrap" style={{ width: '90px' }}>
                                Creation Year {renderSortIcon("creationYear")}
                            </th>
                            
                            {/* Type - Not Sortable */}
                            <th>Type {renderSortIcon("type")}</th>
                            
                            {/* Link - Not Sortable */}
                            <th style={{ width: '80px' }}>Link</th>
                            
                            {/* Location Name (Sortable) */}
                            <th onClick={() => handleHeaderClick("locationName")} className="sortable">
                                Location {renderSortIcon("locationName")}
                            </th>
                            
                            <th className="text-center" style={{ width: isAdmin ? '180px' : '150px' }}>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {sourcesPage.content.length > 0 ? sourcesPage.content.map((item, idx) => (
                            <tr key={item.id}>
                                <td className="text-muted">{sourcesPage.number * sourcesPage.size + idx + 1}</td>
                                
                                {/* Title: Now simple bold text */}
                                <td>
                                    <span className="source-title-text">
                                        {item.title}
                                    </span>
                                </td>
                                
                                <td>{item.reference || <i className="text-muted">N/A</i>}</td>
                                <td className="text-nowrap fw-semibold">
                                    {renderDataRange(item.startYear, item.endYear)}
                                </td>
                                <td className="text-nowrap">
                                    {renderCreationYear(item.creationYear)}
                                </td>
                                <td>{renderTypeBadge(item.type)}</td>
                                <td>
                                    {item.url ? (
                                        <a href={item.url} target="_blank" rel="noopener noreferrer" className="text-primary fw-semibold">
                                            <i className="fas fa-external-link-alt me-1"></i> Link
                                        </a>
                                    ) : <i className="text-muted">None</i>}
                                </td>
                                <td className="py-2 px-4">
                                    {item.locationId && item.locationName ? (
                                        <Link to={`/locations/show/${item.locationId}`} className="text-primary fw-semibold text-nowrap">
                                            <i className="fas fa-map-marker-alt me-1"></i> {item.locationName}
                                        </Link>
                                    ) : <i className="text-muted">N/A</i>}
                                </td>

                                {/* Action Cell: Centralized View & Admin Tools */}
                                <td className="action-cell text-center text-nowrap">
                                    <Link to={`/sources/show/${item.id}`} className="btn btn-primary btn-sm rounded-pill px-3 fw-semibold">
                                        <i className="fas fa-eye me-1"></i> View Source
                                    </Link>
                                    
                                    {isAdmin && (
                                        <Dropdown align="end" className="admin-action-dropdown">
                                            <Dropdown.Toggle variant="secondary" size="sm" id={`dropdown-admin-${item.id}`} className="rounded-circle">
                                                <i className="fas fa-tools"></i>
                                            </Dropdown.Toggle>

                                            <Dropdown.Menu className="shadow">
                                                <Dropdown.Item as={Link} to={`/sources/edit/${item.id}`}>
                                                    <i className="fas fa-edit me-2 text-warning"></i> Edit Source
                                                </Dropdown.Item>
                                                <Dropdown.Divider />
                                                <Dropdown.Item 
                                                    onClick={() => deleteSource(item.id)}
                                                    className="text-danger"
                                                >
                                                    <i className="fas fa-trash-alt me-2"></i> Delete Source
                                                </Dropdown.Item>
                                            </Dropdown.Menu>
                                        </Dropdown>
                                    )}
                                </td>
                            </tr>
                        )) : (
                            <tr>
                                <td colSpan={isAdmin ? 10 : 9} className="text-center text-muted py-4">
                                    <i className="fas fa-exclamation-triangle me-2"></i> No sources match the search criteria.
                                </td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>

            {/* Pagination and Summary (Same as before) */}
            <div className="d-flex justify-content-between align-items-center mt-4 flex-column flex-sm-row">
                <p className="text-muted mb-2 mb-sm-0 small">
                    Showing **{sourcesPage.number * sourcesPage.size + 1}** - **{sourcesPage.number * sourcesPage.size + sourcesPage.content.length}** of **{sourcesPage.totalElements}** total records.
                </p>

                <Pagination size="sm" className="shadow-sm">
                    <Pagination.First onClick={() => handlePageChange(0)} disabled={currentPage === 0} />
                    <Pagination.Prev onClick={() => handlePageChange(currentPage - 1)} disabled={currentPage === 0} />
                    
                    {getPageRange().map(pageIndex => (
                        <Pagination.Item 
                            key={pageIndex} 
                            active={pageIndex === currentPage} 
                            onClick={() => handlePageChange(pageIndex)}
                        >
                            {pageIndex + 1}
                        </Pagination.Item>
                    ))}

                    <Pagination.Next onClick={() => handlePageChange(currentPage + 1)} disabled={currentPage === sourcesPage.totalPages - 1} />
                    <Pagination.Last onClick={() => handlePageChange(sourcesPage.totalPages - 1)} disabled={currentPage === sourcesPage.totalPages - 1} />
                </Pagination>
            </div>
        </div>
    );
};

export default SourceTable;