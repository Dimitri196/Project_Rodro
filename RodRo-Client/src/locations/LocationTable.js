import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import {
    Button,
    Form,
    Pagination,
    InputGroup,
    Spinner,
    Alert,
    Row,
    Col,
} from "react-bootstrap";
import settlementTypeLabels from "../constants/settlementTypeLabels";
import { normalizeString } from "../utils/stringUtils";
import { useSession } from "../contexts/session";
import { apiGet } from "../utils/api";
import "@fortawesome/fontawesome-free/css/all.min.css";

const LocationTable = ({ label, deleteLocation }) => {
    // NOTE: currentPage is 0-indexed for the API call, as is common with Spring Boot Pageable
    const [currentPage, setCurrentPage] = useState(0);
    const [itemsPerPage, setItemsPerPage] = useState(10);
    const [searchTerm, setSearchTerm] = useState("");
    const [localSearchTerm, setLocalSearchTerm] = useState("");
    const [sortBy, setSortBy] = useState("locationName");
    const [sortOrder, setSortOrder] = useState("asc");
    const [locationsPage, setLocationsPage] = useState({
        content: [],
        totalElements: 0,
        totalPages: 0,
    });
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const { session } = useSession();
    const isAdmin = session.data?.isAdmin === true;

    // --- Custom Styles for Scientific Table (Copied from ParishTable) ---
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

        /* 💡 NOTE: This style definition is crucial for the icon-only buttons */
        .action-button-group .btn-sm {
            padding: 0.25rem 0.5rem; /* Reduced padding for compact look */
            font-size: 0.8rem;
            border-radius: 0.25rem;
        }
    `;

    useEffect(() => {
        const fetchLocations = async () => {
            setLoading(true);
            setError(null);
            try {
                const params = new URLSearchParams({
                    page: currentPage,
                    size: itemsPerPage,
                    sortBy,
                    sortOrder,
                });
                if (searchTerm) {
                    params.append("searchTerm", searchTerm);
                }

                const data = await apiGet(`/api/locations?${params.toString()}`);
                setLocationsPage(data);
            } catch (err) {
                console.error("Failed to fetch locations:", err);
                setError("Failed to load locations. Please try again later.");
            } finally {
                setLoading(false);
            }
        };

        fetchLocations();
    }, [currentPage, itemsPerPage, sortBy, sortOrder, searchTerm]);

    const handlePageChange = (pageNumber) => setCurrentPage(pageNumber);

    const handleItemsPerPageChange = (e) => {
        setItemsPerPage(parseInt(e.target.value, 10) || 1);
        setCurrentPage(0); // Reset to first page (index 0)
    };

    const handleSort = (field) => {
        if (sortBy === field) {
            setSortOrder((prev) => (prev === "asc" ? "desc" : "asc"));
        } else {
            setSortBy(field);
            setSortOrder("asc");
        }
        setCurrentPage(0);
    };

    const handleApplySearch = () => {
        setSearchTerm(normalizeString(localSearchTerm));
        setCurrentPage(0);
    };

    const handleClearSearch = () => {
        setLocalSearchTerm("");
        setSearchTerm("");
        setCurrentPage(0);
    };
    
    const handleLocalSearchChange = (e) => {
        setLocalSearchTerm(e.target.value);
    };


    const getPageRange = () => {
        const maxPagesToShow = 5;
        const totalPages = locationsPage.totalPages;
        const current1Idx = currentPage + 1;
        
        const halfRange = Math.floor(maxPagesToShow / 2);
        let startPage = Math.max(current1Idx - halfRange, 1);
        let endPage = Math.min(current1Idx + halfRange, totalPages);

        if (endPage - startPage + 1 < maxPagesToShow) {
            if (current1Idx <= halfRange) {
                endPage = Math.min(startPage + maxPagesToShow - 1, totalPages);
            } else {
                startPage = Math.max(endPage - maxPagesToShow + 1, 1);
            }
        }
        
        // Return 0-indexed pages
        return Array.from({ length: endPage - startPage + 1 }, (_, i) => startPage + i - 1);
    };
    
    // Helper for table header rendering
    const renderHeader = (key, label) => (
        <th onClick={() => handleSort(key)} className="clickable">
            {label}
            {sortBy === key && (
                <i className={`fas ms-2 fa-sm ${sortOrder === 'asc' ? 'fa-caret-down' : 'fa-caret-up'}`}></i>
            )}
        </th>
    );
    
    // Helper for rendering Settlement Type Pill/Badge
    const renderSettlementTypeBadge = (settlementType) => {
        const colorMap = {
            'CITY': 'primary',
            'TOWN': 'info',
            'VILLAGE': 'success',
            'FARM': 'secondary',
            'FORESTERS_LODGE': 'dark',
            'KHUTOR': 'secondary',
            'SETTLEMENT': 'info',
            'COLONY': 'warning',
            'RAILWAY_STATION': 'primary',
            'BRICKYARD': 'danger',
            'MILL_SETTLEMENT': 'success',
            'ZASCIANEK': 'secondary',
            'FOREST_SETTLEMENT': 'dark',
            'FACTORY_SETTLEMENT': 'danger',
            'SAWMILL': 'warning',
            'SUBURB': 'info',
            'TAR_FACTORY': 'danger'
        };
        
        const variant = colorMap[settlementType] || 'secondary';
        
        let label = settlementTypeLabels[settlementType];
        if (!label && settlementType) {
            label = settlementType.replace(/_/g, ' ').replace(/\b\w/g, l => l.toUpperCase());
        } else if (!label) {
            label = "N/A";
        }
        
        return (
            <span className={`badge bg-${variant} fw-semibold`}>
                {label}
            </span>
        );
    };


    if (loading) {
        return (
            <div className="container my-5 text-center">
                <Spinner animation="border" role="status">
                    <span className="visually-hidden">Loading...</span>
                </Spinner>
                <p className="mt-2">Loading locations...</p>
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

    const { content: currentItems, totalElements, totalPages, number: currentApiPage } = locationsPage;
    const startIndex = currentApiPage * itemsPerPage;
    const currentRecordsCount = currentItems.length;


    return (
        <div className="my-4 p-4 bg-light rounded shadow-lg">
            <style>{customStyles}</style>

            {/* Control Panel: Count, Create, Search, Items Per Page */}
            <Row className="mb-4 align-items-center">
                <Col md={3}>
                    <h5 className="mb-0 fw-bold text-dark">
                        {label} <span className="text-primary">{totalElements}</span>
                    </h5>
                </Col>
                <Col md={6}>
                    <InputGroup>
                        <InputGroup.Text><i className="fas fa-search"></i></InputGroup.Text>
                        <Form.Control
                            type="text"
                            placeholder="Search by name..."
                            value={localSearchTerm}
                            onChange={handleLocalSearchChange}
                            onKeyDown={(e) => {
                                if (e.key === "Enter") handleApplySearch();
                            }}
                            className="rounded-end-0"
                        />
                        <Button variant="primary" onClick={handleApplySearch}>
                            <i className="fas fa-search"></i>
                        </Button>
                        {localSearchTerm && (
                            <Button
                                variant="outline-secondary"
                                onClick={handleClearSearch}
                                className="rounded-end"
                            >
                                <i className="fas fa-times"></i>
                            </Button>
                        )}
                    </InputGroup>
                </Col>
                <Col md={3} className="d-flex justify-content-end">
                    <div className="d-flex align-items-center me-3">
                        <label className="me-2 mb-0 fw-semibold text-muted">Records:</label>
                        <Form.Select
                            size="sm"
                            value={itemsPerPage}
                            onChange={handleItemsPerPageChange}
                            style={{ width: '80px' }}
                            className="shadow-sm"
                        >
                            {[10, 20, 50, 100, totalElements || 0].map(val => (
                                <option key={val} value={val}>
                                    {val === totalElements ? "All" : val}
                                </option>
                            ))}
                        </Form.Select>
                    </div>
                </Col>
            </Row>

            {/* Admin Create Button - Moved to the end of the Control Panel visually */}
            {isAdmin && (
                <Row className="mb-4">
                    <Col className="d-flex justify-content-end">
                        <Link to="/locations/create" className="btn btn-sm btn-success shadow-sm">
                            <i className="fas fa-plus me-2"></i>Create New Location
                        </Link>
                    </Col>
                </Row>
            )}

            {/* Table */}
            <div className="table-responsive">
                <table className="table scientific-table">
                    <thead>
                        <tr>
                            <th style={{ width: '40px' }}>#</th>
                            {renderHeader("locationName", "Location Name")}
                            {renderHeader("settlementType", "Type")}
                            {renderHeader("establishmentYear", "Est. Year")}
                            {renderHeader("gpsLatitude", "Latitude")}
                            {renderHeader("gpsLongitude", "Longitude")}
                            {/* Actions column: Only render the header if the user is an admin */}
                            {isAdmin && <th className="text-center" style={{ width: '150px' }}>Actions</th>}
                        </tr>
                    </thead>
                    <tbody>
                        {currentItems.length > 0 ? currentItems.map((item, index) => (
                            <tr key={item.id}>
                                <td>{startIndex + index + 1}</td>
                                <td>
                                    <Link to={`/locations/show/${item.id}`} className="fw-bold text-dark text-decoration-none">
                                        {item.locationName}
                                    </Link>
                                </td>
                                <td>{renderSettlementTypeBadge(item.settlementType)}</td>
                                
                                <td>{item.establishmentYear || <i className="text-muted">Unknown</i>}</td>
                                <td>{item.gpsLatitude || <i className="text-muted">N/A</i>}</td>
                                <td>{item.gpsLongitude || <i className="text-muted">N/A</i>}</td>
                                
                                {/* *** FIXES APPLIED HERE ***
                                    1. Corrected all IDs to 'item.id'.
                                    2. Corrected Link URLs to '/locations/edit/'.
                                    3. Corrected delete function to 'deleteLocation(item.id)'.
                                    4. Added the 'View' button to the action group.
                                */}
                                {isAdmin && (
                                    <td className="text-center">
                                        <div className="btn-group action-button-group">
                                            {/* View Button (Optional, but useful if the link in <td> isn't clear) */}
                                            <Link to={`/locations/show/${item.id}`} className="btn btn-sm btn-info" title="View Details">
                                                <i className="fas fa-eye"></i>
                                            </Link>
                                            
                                            {/* Edit Button */}
                                            <Link to={`/locations/edit/${item.id}`} className="btn btn-sm btn-warning" title="Edit Location">
                                                <i className="fas fa-edit"></i>
                                            </Link>
                                            
                                            {/* Delete Button */}
                                            <Button 
                                                onClick={() => deleteLocation(item.id)} 
                                                variant="danger" 
                                                size="sm"
                                                title="Delete Location"
                                            >
                                                <i className="fas fa-trash-alt"></i>
                                            </Button>
                                        </div>
                                    </td>
                                )}
                            </tr>
                        )) : (
                            <tr>
                                <td colSpan={isAdmin ? "7" : "6"} className="text-center text-muted py-4">
                                    <i className="fas fa-exclamation-triangle me-2"></i> No locations match the current criteria.
                                </td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>

            {/* Pagination */}
            {totalPages > 0 && (
                <div className="d-flex justify-content-center mt-4">
                    <Pagination size="sm">
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

                        <Pagination.Next onClick={() => handlePageChange(currentPage + 1)} disabled={currentPage === totalPages - 1} />
                        <Pagination.Last onClick={() => handlePageChange(totalPages - 1)} disabled={currentPage === totalPages - 1} />
                    </Pagination>
                </div>
            )}
            
            <p className="text-center text-muted mt-3 mb-0">
                Displaying **{currentRecordsCount}** records (Records {startIndex + 1} to {startIndex + currentRecordsCount} of {totalElements}).
            </p>

        </div>
    );
};

export default LocationTable;
