import React, { useState, useMemo } from "react";
import { Link } from "react-router-dom";
import { normalizeString } from "../utils/stringUtils";
import { useSession } from "../contexts/session";
import { Button, InputGroup, Form, Pagination, Row, Col, Badge } from 'react-bootstrap';
import "@fortawesome/fontawesome-free/css/all.min.css";

const MilitaryStructureTable = ({ label = "Military Structures", items, deleteStructure }) => {
    const [currentPage, setCurrentPage] = useState(1);
    const [itemsPerPage, setItemsPerPage] = useState(10);
    const [searchTerm, setSearchTerm] = useState("");
    const [sortAsc, setSortAsc] = useState(true);
    const [sortKey, setSortKey] = useState("unitName");

    const { session } = useSession();
    const isAdmin = session.data?.isAdmin === true;

    const customStyles = `
        .scientific-table {
            --bs-table-bg: #fff;
            --bs-table-color: #343a40;
            --bs-table-border-color: #dee2e6;
            border-radius: 0.5rem;
            overflow: hidden;
            border-collapse: separate;
            border-spacing: 0;
            box-shadow: 0 0.5rem 1rem rgba(0,0,0,0.05);
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
            cursor: pointer;
        }
        .scientific-table tbody tr:hover { background-color: #f1f7fe; }
        .scientific-table td { font-size: 0.95rem; vertical-align: middle; padding: 0.75rem; }
        .action-button-group .btn-sm { padding: 0.25rem 0.75rem; font-size: 0.8rem; border-radius: 0.25rem; }
        .badge-unit { font-size: 0.85rem; cursor: default; }
    `;

    // Filter and sort items
    const filteredItems = useMemo(() => {
        const filtered = items.filter(item =>
            normalizeString(item.unitName || "").includes(normalizeString(searchTerm)) ||
            normalizeString(item.unitType || "").includes(normalizeString(searchTerm)) ||
            // 🌟 REFACTOR: Use item.organization?.name instead of item.organization?.armyName
            normalizeString(item.organization?.name || "").includes(normalizeString(searchTerm))
        );

        return filtered.sort((a, b) => {
            // 🌟 REFACTOR: Use item.organization?.name for organization sort key
            let valA = sortKey === "organization" ? a.organization?.name || "" : a[sortKey] || "";
            let valB = sortKey === "organization" ? b.organization?.name || "" : b[sortKey] || "";

            if (typeof valA === 'number' && typeof valB === 'number') return sortAsc ? valA - valB : valB - valA;
            return sortAsc
                ? valA.toString().toLowerCase().localeCompare(valB.toString().toLowerCase())
                : valB.toString().toLowerCase().localeCompare(valA.toString().toLowerCase());
        });
    }, [items, searchTerm, sortAsc, sortKey]);

    const totalPages = Math.ceil(filteredItems.length / itemsPerPage);
    const startIndex = (currentPage - 1) * itemsPerPage;
    const currentItems = filteredItems.slice(startIndex, startIndex + itemsPerPage);

    const handleSort = key => { if (sortKey === key) setSortAsc(prev => !prev); else { setSortKey(key); setSortAsc(true); } };
    const handleSearchChange = e => { setSearchTerm(e.target.value); setCurrentPage(1); };
    const handleItemsPerPageChange = e => { setItemsPerPage(parseInt(e.target.value, 10)); setCurrentPage(1); };
    const handlePageChange = page => setCurrentPage(page);

    const getPageRange = () => {
        const maxPagesToShow = 5;
        const half = Math.floor(maxPagesToShow / 2);
        let start = Math.max(currentPage - half, 1);
        let end = Math.min(currentPage + half, totalPages);
        if (end - start + 1 < maxPagesToShow) start = Math.max(end - maxPagesToShow + 1, 1);
        return Array.from({ length: end - start + 1 }, (_, i) => start + i);
    };

    const renderHeader = (key, label) => (
        <th onClick={() => handleSort(key)}>
            {label} {sortKey === key && <i className={`fas ms-2 fa-sm ${sortAsc ? 'fa-caret-down' : 'fa-caret-up'}`}></i>}
        </th>
    );

    const renderUnitTypeBadge = type => {
        const variant = {
            "Infantry": "primary",
            "Cavalry": "success",
            "Artillery": "danger",
            "Engineering": "info",
            "Logistics": "warning",
            "Other": "secondary"
        }[type?.trim()] || "secondary";

        return <Badge bg={variant} className="fw-semibold badge-unit">{type || "Other"}</Badge>;
    };

    const renderActiveYears = (from, to) => (
        <>
            <Badge bg="success" className="me-1">{from || "?"}</Badge> –
            {to ? <Badge bg="danger" className="ms-1">{to}</Badge> : <Badge bg="info" className="ms-1">Present</Badge>}
        </>
    );

    return (
        <div className="my-4 p-4 bg-light rounded shadow-lg">
            <style>{customStyles}</style>

            {/* Controls */}
            <Row className="mb-4 align-items-center">
                <Col md={4}><h5 className="mb-0 fw-bold text-dark">{label} <span className="text-primary">{items.length}</span></h5></Col>
                <Col md={5}>
                    <InputGroup>
                        <InputGroup.Text><i className="fas fa-search"></i></InputGroup.Text>
                        <Form.Control
                            placeholder="Search by unit name, type, or organization..."
                            value={searchTerm}
                            onChange={handleSearchChange}
                            className="rounded-end"
                        />
                    </InputGroup>
                </Col>
                <Col md={3} className="d-flex justify-content-end">
                    <div className="d-flex align-items-center me-3">
                        <label className="me-2 mb-0 fw-semibold text-muted">Records:</label>
                        <Form.Select size="sm" value={itemsPerPage} onChange={handleItemsPerPageChange} style={{ width: '80px' }}>
                            {[10, 20, 50, 100, items.length].map(val => <option key={val} value={val}>{val === items.length ? "All" : val}</option>)}
                        </Form.Select>
                    </div>
                </Col>
            </Row>

            {/* Table */}
            <div className="table-responsive">
                <table className="table scientific-table">
                    <thead>
                        <tr>
                            <th style={{ width: '40px' }}>#</th>
                            {renderHeader("unitName", "Unit Name")}
                            {renderHeader("unitType", "Unit Type")}
                            {renderHeader("organization", "Organization")}
                            <th>Active Years</th>
                            {isAdmin && <th className="text-center" style={{ width: '150px' }}>Actions</th>}
                        </tr>
                    </thead>
                    <tbody>
                        {currentItems.length > 0 ? currentItems.map((item, index) => (
                            <tr key={item._id || item.id || index}> {/* Added item.id fallback for robustness */}
                                <td>{startIndex + index + 1}</td>
                                <td><Link to={`/militaryStructures/show/${item._id || item.id}`} className="fw-bold text-dark text-decoration-none">{item.unitName}</Link></td>
                                <td>{renderUnitTypeBadge(item.unitType)}</td>
                                <td>
                                    {item.organizationName && item.organizationId ? ( // Check for the flat properties
                                        <Link to={`/militaryOrganizations/show/${item.organizationId}`} className="text-primary fw-semibold">
                                            {item.organizationName}
                                        </Link>
                                    ) : <i className="text-muted">N/A</i>}
                                </td>
                                <td>{renderActiveYears(item.activeFromYear, item.activeToYear)}</td>
                                {isAdmin && (
                                    <td className="text-center">
                                        <div className="btn-group action-button-group">
                                            <Link to={`/militaryStructures/edit/${item._id || item.id}`} className="btn btn-sm btn-warning"><i className="fas fa-edit"></i></Link>
                                            <Button onClick={() => deleteStructure(item._id || item.id)} variant="danger" size="sm"><i className="fas fa-trash-alt"></i></Button>
                                        </div>
                                    </td>
                                )}
                            </tr>
                        )) : (
                            <tr>
                                <td colSpan={isAdmin ? 7 : 6} className="text-center text-muted py-4">
                                    <i className="fas fa-exclamation-triangle me-2"></i>No military structures match the search criteria.
                                </td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>

            {/* Pagination */}
            {totalPages > 1 && (
                <div className="d-flex justify-content-center mt-4">
                    <Pagination size="sm">
                        <Pagination.First onClick={() => handlePageChange(1)} disabled={currentPage === 1} />
                        <Pagination.Prev onClick={() => handlePageChange(currentPage - 1)} disabled={currentPage === 1} />
                        {getPageRange().map(page => (
                            <Pagination.Item key={page} active={page === currentPage} onClick={() => handlePageChange(page)}>{page}</Pagination.Item>
                        ))}
                        <Pagination.Next onClick={() => handlePageChange(currentPage + 1)} disabled={currentPage === totalPages} />
                        <Pagination.Last onClick={() => handlePageChange(totalPages)} disabled={currentPage === totalPages} />
                    </Pagination>
                </div>
            )}

            <p className="text-center text-muted mt-3 mb-0">
                Displaying {currentItems.length} records (Records {startIndex + 1} to {startIndex + currentItems.length} of {filteredItems.length}).
            </p>
        </div>
    );
};

export default MilitaryStructureTable;