
import React, { useState, useEffect, useCallback } from "react";
import { Link } from "react-router-dom";
import { normalizeString as normalizeStringForSearch } from "../utils/stringUtils";
import { useSession } from "../contexts/session";
import { apiGet } from "../utils/api";
import { Button, Form, Pagination, Spinner, Alert, Row, Col, Dropdown } from "react-bootstrap";
import "@fortawesome/fontawesome-free/css/all.min.css"; 
import { SOURCE_TYPE_MAP } from '../constants/sourceType'; 
import { CONFESSION_TYPE_MAP } from '../constants/confessionType'; 

const SourceTable = ({ label, deleteSource, refreshTrigger }) => {
    const { session } = useSession();
    const isAdmin = session?.data?.isAdmin === true;

    // --- State Initialization ---
    const [currentPage, setCurrentPage] = useState(0);
    const [itemsPerPage, setItemsPerPage] = useState(10);
    
    // Sort state
    const [sortBy, setSortBy] = useState("creationYear"); 
    const [sortOrder, setSortOrder] = useState("desc"); 
    
    // Filter state: Holds the RAW value from the input fields
    const [columnFilters, setColumnFilters] = useState({
        title: "",
        reference: "",
        type: "",
        confession: "", 
        locationName: "",
        creationYearMin: "", 
    });

    const [sourcesPage, setSourcesPage] = useState({ 
        content: [], 
        totalElements: 0, 
        totalPages: 0, 
        number: 0, // Current page number (0-indexed)
        size: 10 
    });
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    // --- Custom Styles (Placeholder - Replace with your actual CSS) ---
    const customStyles = `
        .scientific-table th { background-color: #3f51b5; color: white; cursor: pointer; }
        .scientific-table .filter-row td { padding-top: 5px; padding-bottom: 5px; }
        .filter-input { border-radius: 4px; border: 1px solid #ccc; font-size: 14px; }
        .sortable { cursor: pointer; }
        .source-title-text { font-weight: 600; }
        .action-cell .dropdown-toggle::after { content: none; }
    `;


    // --- Filter Handlers (Store raw value in state) ---
    const handleFilterChange = (column, value) => {
        // Store the raw input value in state. 
        setColumnFilters(prev => ({ ...prev, [column]: value }));
        setCurrentPage(0); // Reset pagination whenever a filter changes
    };

    // --- Clear All Filters Handler ---
    const clearAllFilters = () => {
        setColumnFilters({ 
            title: "", 
            reference: "", 
            type: "", 
            confession: "", 
            locationName: "",
            creationYearMin: "",
        });
        setCurrentPage(0);
    };

    // --- Data Fetching (Apply normalization/trimming here) ---
    const fetchSources = useCallback(async () => {
        setLoading(true);
        setError(null);
        try {
            const params = new URLSearchParams({
                page: currentPage,
                size: itemsPerPage,
                sort: `${sortBy},${sortOrder}`, 
            });
            
            // Append individual column filters as query parameters
            Object.entries(columnFilters).forEach(([key, value]) => {
                if (value || value === 0) {
                    let apiValue = value;
                    
                    if (["title", "reference", "locationName"].includes(key) && typeof value === 'string') {
                        // Apply normalization and trimming immediately before sending the request
                        apiValue = normalizeStringForSearch(value).trim();
                    }

                    if (apiValue || apiValue === 0) { 
                        params.append(`filter_${key}`, apiValue); 
                    }
                }
            });

            const data = await apiGet(`/api/sources?${params.toString()}`);
            setSourcesPage(data);
        } catch (err) {
            const errorMessage = err.message || "Failed to fetch sources. Check network connection or API status.";
            setError(errorMessage);
            setSourcesPage({ content: [], totalElements: 0, totalPages: 0, number: 0, size: 10 });
        } finally {
            setLoading(false);
        }
    }, [currentPage, itemsPerPage, sortBy, sortOrder, columnFilters, refreshTrigger]);

    useEffect(() => {
        fetchSources();
    }, [fetchSources]);


    const renderPaginationItems = () => {
        const totalPages = sourcesPage.totalPages;
        const currentPageIndex = sourcesPage.number;
        const maxPagesToShow = 5;
        const items = [];

        let startPage = Math.max(0, currentPageIndex - Math.floor(maxPagesToShow / 2));
        let endPage = Math.min(totalPages - 1, startPage + maxPagesToShow - 1);


        if (endPage - startPage + 1 < maxPagesToShow) {
            startPage = Math.max(0, endPage - maxPagesToShow + 1);
        }

          if (startPage > 0) {
            items.push(<Pagination.Item key={0} onClick={() => setCurrentPage(0)}>{1}</Pagination.Item>);
            if (startPage > 1) {
                items.push(<Pagination.Ellipsis key="start-ellipsis" />);
            }
        }

        for (let page = startPage; page <= endPage; page++) {
            items.push(
                <Pagination.Item 
                    key={page} 
                    active={page === currentPageIndex} 
                    onClick={() => setCurrentPage(page)}
                >
                    {page + 1}
                </Pagination.Item>
            );
        }

        // Always show the last page if it's not in the window
        if (endPage < totalPages - 1) {
            if (endPage < totalPages - 2) {
                items.push(<Pagination.Ellipsis key="end-ellipsis" />);
            }
            items.push(<Pagination.Item key={totalPages - 1} onClick={() => setCurrentPage(totalPages - 1)}>{totalPages}</Pagination.Item>);
        }

        return items;
    };
    
    // --- Other Handlers & Helpers (Rest remains unchanged) ---

    const handleHeaderClick = (field) => {
        const sortableFields = [
            "startYear", "creationYear", "locationName", 
            "title", "reference", "type", "confession" 
        ]; 
        if (!sortableFields.includes(field)) return;

        if (sortBy === field) setSortOrder(prev => prev === "asc" ? "desc" : "asc");
        else {
            setSortBy(field);
            setSortOrder("asc");
        }
        setCurrentPage(0); 
    };

    const renderSortIcon = (field) => {
        const sortableFields = [
            "title", "reference", "startYear", "creationYear", 
            "locationName", "type", "confession"
        ];
        
        if (sortBy === field) return sortOrder === "asc" 
            ? <i className="fas fa-caret-up ms-2 fa-sm"></i> 
            : <i className="fas fa-caret-down ms-2 fa-sm"></i>;
        
        if (sortableFields.includes(field)) {
            return <i className="fas fa-sort ms-2 text-white-50 fa-sm"></i>;
        }
        
        return null;
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

    const renderConfessionBadge = (enumKey) => { 
        if (!enumKey) return <span className="badge bg-secondary fw-semibold">Unknown</span>;
        
        const confessionDisplayName = CONFESSION_TYPE_MAP[enumKey] || enumKey.replace(/_/g, ' '); 
            const variantMap = {
            'Roman Catholic (Latin)': 'info',
            'Greek Catholic / Uniate': 'primary',
            'Eastern Orthodox': 'warning',
            'Lutheran / Evangelical': 'success',
            'Calvinist / Reformed': 'success', 
            'Anglican': 'success', 
            'Jewish': 'danger', 
            'Islamic (Muslim)': 'dark',
            'Other / Unknown': 'secondary',
        };

        const variant = variantMap[confessionDisplayName] || 'secondary';

        return (
            <span className={`badge bg-${variant} fw-semibold text-nowrap`}>
                {confessionDisplayName}
            </span>
        );
    };

    // The function that renders sortable headers
    const renderHeader = (field, label, isSortable = true) => (
        <th 
            key={field} 
            className={isSortable ? "sortable text-nowrap" : "text-nowrap"}
            onClick={() => isSortable && handleHeaderClick(field)}
        >
            {label} 
            {isSortable && renderSortIcon(field)}
        </th>
    );

    // --- Loading/Error Views ---
    if (loading && sourcesPage.content.length === 0 && Object.values(columnFilters).every(v => !v)) return (
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
    
    // Check if totalPages is 0 before rendering
    const totalPages = sourcesPage.totalPages;

    // --- Main Render ---
    return (
        <div className="container my-5">
            <style>{customStyles}</style>

            {/* Control Panel */}
            <Row className="mb-4 align-items-center">
                <Col md={6} className="d-flex align-items-center">
                    <h5 className="mb-0 fw-bold text-dark">
                        {label} <span className="text-primary">{sourcesPage.totalElements}</span>
                    </h5>
                    {isAdmin && (
                        <Link to="/sources/create" className="btn btn-success btn-sm ms-3 shadow-sm">
                            <i className="fas fa-plus me-1"></i>Add source
                        </Link>
                    )}
                </Col>
                
                <Col md={6} className="d-flex justify-content-end">
                    <Button 
                        variant="outline-danger" 
                        size="sm"
                        onClick={clearAllFilters}
                        disabled={Object.values(columnFilters).every(val => !val)}
                        className="shadow-sm me-3"
                    >
                        <i className="fas fa-times me-1"></i> **Clear All Filters**
                    </Button>
                </Col>
            </Row>

            {/* Table Card */}
            <div className="table-responsive shadow-lg rounded">
                <table className="table scientific-table">
                    <thead>
                        {/* --- Filter Row --- */}
                        <tr className="filter-row">
                            <td style={{ width: '40px' }}></td> 
                            
                            {/* Title Filter */}
                            <td>
                                <Form.Control 
                                    type="text" 
                                    value={columnFilters.title} 
                                    onChange={(e) => handleFilterChange("title", e.target.value)} 
                                    placeholder="Filter Title"
                                    className="filter-input"
                                />
                            </td>
                            
                            {/* Reference Filter */}
                            <td>
                                <Form.Control 
                                    type="text" 
                                    value={columnFilters.reference} 
                                    onChange={(e) => handleFilterChange("reference", e.target.value)} 
                                    placeholder="Filter Reference"
                                    className="filter-input"
                                />
                            </td>
                            
                            {/* Data Range Filter: Placeholder */}
                            <td style={{ width: '100px' }}></td> 
                            
                            {/* Creation Year Filter (MIN) */}
                            <td style={{ width: '90px' }}>
                                <Form.Control 
                                    type="number" 
                                    min="1000" 
                                    max="2099" 
                                    value={columnFilters.creationYearMin} 
                                    onChange={(e) => handleFilterChange("creationYearMin", e.target.value)} 
                                    placeholder="Min Year"
                                    className="filter-input"
                                />
                            </td>
                            
                            {/* Type Filter */}
                            <td>
                                <Form.Select 
                                    value={columnFilters.type} 
                                    onChange={(e) => handleFilterChange("type", e.target.value)} 
                                    className="filter-input"
                                    style={{ height: '38px', paddingTop: '0.25rem' }} 
                                >
                                    <option value="">Filter Type</option>
                                    {Object.entries(SOURCE_TYPE_MAP).map(([key, value]) => (
                                        <option key={key} value={key}>{value}</option>
                                    ))}
                                </Form.Select>
                            </td>

                            {/* Confession Filter */}
                            <td>
                                <Form.Select 
                                    value={columnFilters.confession} 
                                    onChange={(e) => handleFilterChange("confession", e.target.value)} 
                                    className="filter-input"
                                    style={{ height: '38px', paddingTop: '0.25rem' }} 
                                >
                                    <option value="">Filter Confession</option>
                                    {Object.entries(CONFESSION_TYPE_MAP).map(([key, value]) => (
                                        <option key={key} value={key}>{value}</option>
                                    ))}
                                </Form.Select>
                            </td>
                            
                            <td style={{ width: '80px' }}></td> {/* Link Column Spacer */}
                            
                            {/* Location Name Filter */}
                            <td>
                                <Form.Control 
                                    type="text" 
                                    value={columnFilters.locationName} 
                                    onChange={(e) => handleFilterChange("locationName", e.target.value)} 
                                    placeholder="Filter Location"
                                    className="filter-input"
                                />
                            </td>
                            
                            <td style={{ width: isAdmin ? '180px' : '150px' }}></td> {/* Actions Column Spacer */}
                        </tr>

                        {/* --- Header Row (Sorting Controls) --- */}
                        <tr>
                            <th style={{ width: '40px' }}>#</th>
                            
                            {renderHeader("title", "Title", true)}
                            {renderHeader("reference", "Reference", true)}
                            
                            <th onClick={() => handleHeaderClick("startYear")} className="sortable text-nowrap" style={{ width: '100px' }}>
                                Data Range {renderSortIcon("startYear")}
                            </th>
                            
                            <th onClick={() => handleHeaderClick("creationYear")} className="sortable text-nowrap" style={{ width: '90px' }}>
                                Year {renderSortIcon("creationYear")}
                            </th>
                            
                            {renderHeader("type", "Type", true)} 
                            
                            {renderHeader("confession", "Confession", true)} 
                            
                            <th style={{ width: '80px' }}>Link</th>
                            
                            {renderHeader("locationName", "Location", true)}
                            
                            <th className="text-center" style={{ width: isAdmin ? '180px' : '150px' }}>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {sourcesPage.content.length > 0 ? sourcesPage.content.map((item, idx) => (
                            <tr key={item.id}>
                                <td className="text-muted">{sourcesPage.number * sourcesPage.size + idx + 1}</td>
                                
                                <td><span className="source-title-text">{item.title}</span></td>
                                <td>{item.reference || <i className="text-muted">N/A</i>}</td>
                                
                                <td className="text-nowrap fw-semibold">
                                    {renderDataRange(item.startYear, item.endYear)}
                                </td>
                                
                                <td className="text-nowrap">
                                    {renderCreationYear(item.creationYear)}
                                </td>
                                
                                <td>{renderTypeBadge(item.type)}</td>
                                
                                <td>{renderConfessionBadge(item.confession)}</td>
                                
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

                                {/* Action Cell */}
                                <td className="action-cell text-center text-nowrap">
                                    <Link to={`/sources/show/${item.id}`} className="btn btn-primary btn-sm rounded-pill px-3 fw-semibold">
                                        <i className="fas fa-eye me-1"></i> View Source
                                    </Link>
                                    
                                    {isAdmin && (
                                        <Dropdown align="end" className="admin-action-dropdown">
                                            <Dropdown.Toggle variant="outline-secondary" size="sm" id={`dropdown-actions-${item.id}`} className="ms-2">
                                                <i className="fas fa-ellipsis-v"></i>
                                            </Dropdown.Toggle>

                                            <Dropdown.Menu>
                                                <Dropdown.Item as={Link} to={`/sources/edit/${item.id}`}>
                                                    <i className="fas fa-edit me-2"></i> Edit
                                                </Dropdown.Item>
                                                <Dropdown.Divider />
                                                <Dropdown.Item 
                                                    onClick={() => deleteSource(item.id)} 
                                                    className="text-danger"
                                                >
                                                    <i className="fas fa-trash-alt me-2"></i> Delete
                                                </Dropdown.Item>
                                            </Dropdown.Menu>
                                        </Dropdown>
                                    )}
                                </td>
                            </tr>
                        )) : (
                            <tr>
                                <td colSpan={isAdmin ? 11 : 10} className="text-center text-muted py-4">
                                    <i className="fas fa-exclamation-triangle me-2"></i> No sources match the current filters.
                                </td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>

            {/* Pagination and Summary */}
            <div className="d-flex justify-content-between align-items-center mt-3">
                <small className="text-muted">
                    Showing **{sourcesPage.number * sourcesPage.size + 1}** – **{Math.min((sourcesPage.number + 1) * sourcesPage.size, sourcesPage.totalElements)}** of 
                    **{sourcesPage.totalElements}** total sources.
                </small>
                
                {totalPages > 1 && (
                    <Pagination size="sm">
                        <Pagination.First 
                            onClick={() => setCurrentPage(0)} 
                            disabled={sourcesPage.number === 0} 
                        />
                        <Pagination.Prev 
                            onClick={() => setCurrentPage(sourcesPage.number - 1)} 
                            disabled={sourcesPage.number === 0} 
                        />
                        
                        {/* **REFACTORED: Render dynamic page numbers here** */}
                        {renderPaginationItems()} 

                        <Pagination.Next 
                            onClick={() => setCurrentPage(sourcesPage.number + 1)} 
                            disabled={sourcesPage.number >= sourcesPage.totalPages - 1} 
                        />
                        <Pagination.Last 
                            onClick={() => setCurrentPage(sourcesPage.totalPages - 1)} 
                            disabled={sourcesPage.number >= sourcesPage.totalPages - 1} 
                        />
                    </Pagination>
                )}
            </div>

        </div>
    );
};

export default SourceTable;