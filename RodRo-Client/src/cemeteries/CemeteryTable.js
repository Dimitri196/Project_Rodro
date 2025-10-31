import React, { useState, useMemo, useEffect } from "react";
import { Link } from "react-router-dom";
// Use Row and Col for better layout control
import { Card, Button, Form, InputGroup, Pagination, Container, Row, Col } from "react-bootstrap"; 
import { useSession } from "../contexts/session";
import { normalizeString } from "../utils/stringUtils";
import "@fortawesome/fontawesome-free/css/all.min.css"; // Ensure icons are available

const CemeteryTable = ({ label, items, deleteCemetery }) => {
    const [currentPage, setCurrentPage] = useState(1);
    const [itemsPerPage, setItemsPerPage] = useState(10);
    const [searchTerm, setSearchTerm] = useState("");
    const [sortAsc, setSortAsc] = useState(true);
    const [sortKey, setSortKey] = useState("cemeteryName"); // Default sort key

    const { session } = useSession();
    // Correctly determining isAdmin based on the session mock structure
    const isAdmin = session.data?.isAdmin === true;

    // --- Custom Styles for Scientific Table ---
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
            background-color: #007bff; /* Primary Blue Header */
            color: #fff;
            font-weight: 600;
            border-bottom: 3px solid #0056b3;
            text-transform: uppercase;
            font-size: 0.85rem;
            padding: 1rem 0.75rem;
            vertical-align: middle;
            cursor: pointer;
        }

        .scientific-table tbody tr {
            transition: background-color 0.2s ease;
        }

        .scientific-table tbody tr:hover {
            background-color: #f1f7fe; /* Light blue highlight on hover */
        }

        .scientific-table td {
            font-size: 0.95rem;
            vertical-align: middle;
            padding: 0.75rem;
        }

        .action-button-group .btn-sm {
            padding: 0.25rem 0.75rem;
            font-size: 0.8rem;
            border-radius: 0.25rem;
        }
    `;

    // Filter and sort items
    const filteredItems = useMemo(() => {
        const normalizedSearchTerm = normalizeString(searchTerm);

        const filtered = items.filter(item => {
            const normalizedName = normalizeString(item.cemeteryName || "");
            const normalizedLocation = normalizeString(item.cemeteryLocation?.locationName || "");
            const normalizedParish = normalizeString(item.cemeteryParish?.parishName || "");
            const normalizedDescription = normalizeString(item.description || "");
            
            return normalizedName.includes(normalizedSearchTerm) ||
                   normalizedLocation.includes(normalizedSearchTerm) ||
                   normalizedParish.includes(normalizedSearchTerm) ||
                   normalizedDescription.includes(normalizedSearchTerm);
        });

        return filtered.sort((a, b) => {
            let valA, valB;

            // Extract the sorting value based on the current key
            switch (sortKey) {
                case 'locationName':
                    valA = a.cemeteryLocation?.locationName || "";
                    valB = b.cemeteryLocation?.locationName || "";
                    break;
                case 'parishName':
                    valA = a.cemeteryParish?.parishName || "";
                    valB = b.cemeteryParish?.parishName || "";
                    break;
                default: // 'cemeteryName' or 'description'
                    valA = a[sortKey] || "";
                    valB = b[sortKey] || "";
                    break;
            }

            // Perform case-insensitive string comparison
            const comparison = (valA.toString()).toLowerCase().localeCompare((valB.toString()).toLowerCase());
            
            return sortAsc ? comparison : -comparison;
        });
    }, [items, searchTerm, sortAsc, sortKey]);

    const totalPages = Math.ceil(filteredItems.length / itemsPerPage);
    const startIndex = (currentPage - 1) * itemsPerPage;
    const currentItems = filteredItems.slice(startIndex, startIndex + itemsPerPage);

    // Ensure current page is valid when filters change
    useEffect(() => {
        if (currentPage > totalPages) {
            setCurrentPage(totalPages || 1);
        }
    }, [totalPages, currentPage]);

    const handlePageChange = (pageNumber) => setCurrentPage(pageNumber);

    const handleItemsPerPageChange = (e) => {
        setItemsPerPage(parseInt(e.target.value, 10) || 10);
        setCurrentPage(1);
    };
    
    // Unified Sort Handler
    const handleSort = (key) => {
        if (sortKey === key) {
            setSortAsc(prev => !prev);
        } else {
            setSortKey(key);
            setSortAsc(true);
        }
    };
    
    const handleSearchChange = (e) => {
        setSearchTerm(e.target.value);
        setCurrentPage(1);
    };

    const getPageRange = () => {
        const maxPagesToShow = 5;
        const halfRange = Math.floor(maxPagesToShow / 2);
        let startPage = Math.max(currentPage - halfRange, 1);
        let endPage = Math.min(currentPage + halfRange, totalPages);

        if (endPage - startPage + 1 < maxPagesToShow) {
            if (currentPage <= halfRange) {
                endPage = Math.min(startPage + maxPagesToShow - 1, totalPages);
            } else {
                startPage = Math.max(endPage - maxPagesToShow + 1, 1);
            }
        }
        return Array.from({ length: endPage - startPage + 1 }, (_, i) => startPage + i);
    };

    // Helper for table header rendering
    const renderHeader = (key, label) => (
        <th onClick={() => handleSort(key)} className="clickable">
            {label}
            {/* Fallback Font Awesome Icons assumed available via CDN */}
            {sortKey === key && (
                <i className={`fas ms-2 fa-sm ${sortAsc ? 'fa-caret-down' : 'fa-caret-up'}`}></i>
            )}
        </th>
    );

    return (
        <div className="my-4 p-4 bg-light rounded shadow-lg">
            <style>{customStyles}</style>
            
            {/* Control Panel: Count, Create, Search, Records Per Page */}
            <Row className="mb-4 align-items-center g-3">
                <Col md={3}>
                    <h5 className="mb-0 fw-bold text-dark">
                        {label} <span className="text-primary">{filteredItems.length}</span>
                    </h5>
                </Col>
                
                {/* Search */}
                <Col md={6}>
                    <InputGroup>
                        <InputGroup.Text><i className="fas fa-search"></i></InputGroup.Text>
                        <Form.Control
                            type="text"
                            placeholder="Search by name, location, parish, or description..."
                            value={searchTerm}
                            onChange={handleSearchChange}
                            className="rounded-end"
                        />
                    </InputGroup>
                </Col>
                
                {/* Records Per Page / Create Button */}
                <Col md={3} className="d-flex justify-content-end align-items-center">
                    <div className="d-flex align-items-center me-3">
                        <label className="me-2 mb-0 fw-semibold text-muted small text-nowrap">Records:</label>
                        <Form.Select
                            size="sm"
                            value={itemsPerPage}
                            onChange={handleItemsPerPageChange}
                            style={{ width: '80px' }}
                            className="shadow-sm"
                        >
                            {[10, 20, 50, 100, items.length].map(val => (
                                <option key={val} value={val}>
                                    {val === items.length ? "All" : val}
                                </option>
                            ))}
                        </Form.Select>
                    </div>
                    {isAdmin && (
                        <Link to="/cemeteries/create" className="btn btn-success btn-sm shadow-sm rounded-pill fw-semibold text-nowrap">
                            <i className="fas fa-plus me-1"></i> Add Record
                        </Link>
                    )}
                </Col>
            </Row>

            {/* Table */}
            <div className="table-responsive">
                <table className="table scientific-table">
                    <thead>
                        <tr>
                            <th style={{ width: '40px' }}>#</th>
                            {renderHeader("cemeteryName", "Cemetery Name")}
                            {renderHeader("locationName", "Associated Location")}
                            {renderHeader("description", "Description")}
                            {/* Conditional Header Rendering */}
                            {isAdmin && <th className="text-center" style={{ width: '120px' }}>Actions</th>}
                        </tr>
                    </thead>
                    <tbody>
                        {currentItems.length > 0 ? currentItems.map((item, index) => (
                            <tr key={item._id || item.id || index}>
                                <td>{startIndex + index + 1}</td>
                                <td>
                                    <Link to={`/cemeteries/show/${item._id || item.id}`} className="fw-bold text-dark text-decoration-none hover-underline">
                                        {item.cemeteryName}
                                    </Link>
                                </td>
                                
                                <td className="text-center"> 
                                    {item.cemeteryLocation?.locationName ? (
                                        <Link to={`/locations/show/${item.cemeteryLocation.id || item.cemeteryLocation._id}`} className="text-primary fw-semibold">
                                            <i className="fas fa-map-marker-alt me-1"></i> {item.cemeteryLocation.locationName}
                                        </Link>
                                    ) : <i className="text-muted">None</i>}
                                </td>
                                
                                <td className="small text-truncate" style={{ maxWidth: '250px' }}>{item.description || <i className="text-muted">N/A</i>}</td>
                                
                                {/* Conditional Cell Rendering */}
                                {isAdmin && (
                                    <td className="text-center">
                                        <div className="btn-group action-button-group">
                                            <Link to={`/cemeteries/edit/${item._id || item.id}`} className="btn btn-sm btn-warning" title="Edit Record">
                                                <i className="fas fa-edit"></i>
                                            </Link>
                                            <Button 
                                                onClick={() => {
                                                    // This window.confirm is used as a temporary placeholder, per instructions
                                                    if (window.confirm("CONFIRMATION REQUIRED: Are you sure you want to permanently delete this cemetery record?")) {
                                                        deleteCemetery(item._id || item.id);
                                                    }
                                                }} 
                                                variant="danger" 
                                                size="sm"
                                                title="Delete Record"
                                            >
                                                <i className="fas fa-trash-alt"></i>
                                            </Button>
                                        </div>
                                    </td>
                                )}
                            </tr>
                        )) : (
                            <tr>
                                {/* Adjust colspan based on isAdmin state */}
                                <td colSpan={isAdmin ? "6" : "5"} className="text-center text-muted py-4">
                                    <i className="fas fa-exclamation-triangle me-2"></i> No burial sites match the current criteria.
                                </td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>

            {/* Pagination */}
            {totalPages > 1 && (
                <div className="d-flex justify-content-center mt-4">
                    <Pagination size="sm" className="shadow-sm">
                        <Pagination.First onClick={() => handlePageChange(1)} disabled={currentPage === 1} />
                        <Pagination.Prev onClick={() => handlePageChange(currentPage - 1)} disabled={currentPage === 1} />
                        
                        {getPageRange().map(page => (
                            <Pagination.Item 
                                key={page} 
                                active={page === currentPage} 
                                onClick={() => handlePageChange(page)}
                            >
                                {page}
                            </Pagination.Item>
                        ))}

                        <Pagination.Next onClick={() => handlePageChange(currentPage + 1)} disabled={currentPage === totalPages} />
                        <Pagination.Last onClick={() => handlePageChange(totalPages)} disabled={currentPage === totalPages} />
                    </Pagination>
                </div>
            )}
            
            <p className="text-center text-muted mt-3 mb-0 small">
                Displaying **{currentItems.length}** records (Records {startIndex + 1} to {startIndex + currentItems.length} of **{filteredItems.length}** total).
            </p>

        </div>
    );
};

export default CemeteryTable;
