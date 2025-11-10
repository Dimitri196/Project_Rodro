import React, { useState, useMemo } from "react";
import { Link } from "react-router-dom";
import { normalizeString } from "../utils/stringUtils";
import { useSession } from "../contexts/session";
import { Button, InputGroup, Form, Pagination, Row, Col } from "react-bootstrap";
import "@fortawesome/fontawesome-free/css/all.min.css";

const MilitaryRankTable = ({ label = "Military Ranks", items, deleteRank }) => {
    const [currentPage, setCurrentPage] = useState(1);
    const [itemsPerPage, setItemsPerPage] = useState(10);
    const [searchTerm, setSearchTerm] = useState("");
    const [sortAsc, setSortAsc] = useState(true);
    const [sortKey, setSortKey] = useState("name"); 

    const { session } = useSession();
    const isAdmin = session?.data?.isAdmin === true;

    // --- Custom Styles (Omitted for brevity) ---
    const customStyles = `
        .scientific-table { ... }
        .scientific-table thead th { ... }
        .scientific-table tbody tr { ... }
        .scientific-table tbody tr:hover { ... }
        .scientific-table td { ... }
        .action-button-group .btn-sm { ... }
    `;

    // --- Filter & Sort ---
    const filteredItems = useMemo(() => {
        const filtered = items.filter(item => {
            const rankName = item.name || ""; 
            const rankLevel = item.rankLevel || ""; 
            
            // 🚀 DTO REFACTOR: Use flat properties for search
            const orgName = item.organizationName || ""; 
            const structureName = item.structureName || "";
            // NOTE: armyBranchName is likely missing from the flat DTO. We keep it as is, expecting it might be passed separately, or remove it if not available.
            const branchName = item.armyBranchName || ""; 

            return (
                normalizeString(rankName).includes(normalizeString(searchTerm)) ||
                normalizeString(rankLevel).includes(normalizeString(searchTerm)) ||
                normalizeString(orgName).includes(normalizeString(searchTerm)) ||
                normalizeString(structureName).includes(normalizeString(searchTerm)) ||
                normalizeString(branchName).includes(normalizeString(searchTerm))
            );
        });

        return filtered.sort((a, b) => {
            let valA;
            let valB;

            // Handle sorting for nested/special fields using flat DTO names
            if (sortKey === "rankLevel") {
                valA = a.rankLevel || "";
                valB = b.rankLevel || "";
            } else if (sortKey === "organizationName") { // 🚀 DTO REFACTOR
                valA = a.organizationName || "";
                valB = b.organizationName || "";
            } else if (sortKey === "structureName") { // 🚀 DTO REFACTOR
                valA = a.structureName || "";
                valB = b.structureName || "";
            } else if (sortKey === "armyBranchName") {
                valA = a.armyBranchName || ""; // Assuming this is present in the DTO
                valB = b.armyBranchName || "";
            } else {
                valA = a[sortKey] || "";
                valB = b[sortKey] || "";
            }

            return sortAsc 
                ? valA.toString().toLowerCase().localeCompare(valB.toString().toLowerCase()) 
                : valB.toString().toLowerCase().localeCompare(valA.toString().toLowerCase());
        });
    }, [items, searchTerm, sortKey, sortAsc]);

    // --- Pagination Logic (Unchanged) ---
    const totalPages = Math.ceil(filteredItems.length / itemsPerPage);
    const startIndex = (currentPage - 1) * itemsPerPage;
    const currentItems = filteredItems.slice(startIndex, startIndex + itemsPerPage);

    const getPageRange = () => {
        const maxPagesToShow = 5;
        const half = Math.floor(maxPagesToShow / 2);
        let start = Math.max(currentPage - half, 1);
        let end = Math.min(currentPage + half, totalPages);
        if (end - start + 1 < maxPagesToShow) start = Math.max(end - maxPagesToShow + 1, 1);
        return Array.from({ length: end - start + 1 }, (_, i) => start + i);
    };

    const handleSort = (key) => {
        // Renaming keys for flat DTO sorting
        const newSortKey = {
            "militaryOrganization": "organizationName",
            "militaryStructure": "structureName",
            "armyBranch": "armyBranchName", // Keep using 'armyBranchName' if available
        }[key] || key;

        if (sortKey === newSortKey) setSortAsc(prev => !prev);
        else {
            setSortKey(newSortKey);
            setSortAsc(true);
        }
    };

    const handleSearchChange = (e) => {
        setSearchTerm(e.target.value);
        setCurrentPage(1);
    };

    const handleItemsPerPageChange = (e) => {
        setItemsPerPage(parseInt(e.target.value, 10) || 1);
        setCurrentPage(1);
    };

    const renderHeader = (key, label) => (
        <th onClick={() => handleSort(key)} className="text-nowrap">
            {label} {
                sortKey === key || 
                (key === "militaryOrganization" && sortKey === "organizationName") || 
                (key === "militaryStructure" && sortKey === "structureName") ||
                (key === "armyBranch" && sortKey === "armyBranchName")
                ? <i className={`fas ms-2 fa-sm ${sortAsc ? 'fa-caret-down' : 'fa-caret-up'}`}></i> 
                : null
            }
        </th>
    );

    return (
        <div className="my-4 p-4 bg-light rounded shadow-lg">
            {/* ... Custom Styles Block ... */}

            {/* Control Panel (Unchanged) */}
            <Row className="mb-4 align-items-center">
                <Col md={4}>
                    <h5 className="mb-0 fw-bold text-dark">
                        {label} <span className="text-primary">{filteredItems.length}</span>
                    </h5>
                </Col>
                <Col md={5}>
                    <InputGroup>
                        <InputGroup.Text><i className="fas fa-search"></i></InputGroup.Text>
                        <Form.Control
                            type="text"
                            placeholder="Search by rank, level, organization, or branch..."
                            value={searchTerm}
                            onChange={handleSearchChange}
                            className="rounded-end"
                        />
                    </InputGroup>
                </Col>
                <Col md={3} className="d-flex justify-content-end">
                    <div className="d-flex align-items-center">
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
                            {renderHeader("name", "Rank Name")} 
                            {renderHeader("rankLevel", "Level")}
                            {/* 🚀 DTO REFACTOR: Use key that maps to 'organizationName' */}
                            {renderHeader("organizationName", "Organization")} 
                            {/* 🚀 DTO REFACTOR: Use key that maps to 'structureName' */}
                            {renderHeader("structureName", "Structure")} 
                            {/* 🚀 DTO REFACTOR: Use key that maps to 'armyBranchName' */}
                            {renderHeader("armyBranchName", "Army Branch")}
                            {renderHeader("activeFromYear", "Active From")}
                            {renderHeader("activeToYear", "Active To")}
                            {isAdmin && <th className="text-center" style={{ width: '150px' }}>Actions</th>}
                        </tr>
                    </thead>
                    <tbody>
                        {currentItems.length > 0 ? currentItems.map((item, index) => (
                            <tr key={item.id}>
                                <td>{startIndex + index + 1}</td>
                                <td>
                                    <Link to={`/militaryRanks/show/${item._id}`} className="fw-bold text-decoration-none text-primary">
                                        {item.name} 
                                    </Link>
                                </td>
                                <td>{item.rankLevel || "-"}</td> 
                                <td>
                                    {/* 🚀 DTO REFACTOR: Use flat organizationName and ID */}
                                    {item.organizationId ? (
                                        <Link to={`/militaryOrganizations/show/${item.organizationId}`} className="text-decoration-none text-dark">
                                            {item.organizationName}
                                        </Link>
                                    ) : "-"}
                                </td>
                                <td>
                                    {/* 🚀 DTO REFACTOR: Use flat structureName and ID */}
                                    {item.structureId ? (
                                        <Link to={`/militaryStructures/show/${item.structureId}`} className="text-decoration-none text-dark">
                                            {item.structureName}
                                        </Link>
                                    ) : "-"}
                                </td>
                                <td>
                                    {/* Assuming armyBranchName is now directly on the DTO or available somewhere else, otherwise it will be "-" */}
                                    {item.armyBranchName || "-"} 
                                </td>
                                <td>{item.activeFromYear || "-"}</td>
                                <td>{item.activeToYear || "-"}</td>
                                {isAdmin && (
                                    <td className="text-center">
                                        <div className="btn-group action-button-group">
                                            <Link to={`/militaryRanks/show/${item._id}`} className="btn btn-sm btn-info"><i className="fas fa-eye"></i></Link>
                                            <Link to={`/militaryRanks/edit/${item.id}`} className="btn btn-sm btn-warning"><i className="fas fa-edit"></i></Link>
                                            <Button onClick={() => deleteRank(item.id)} variant="danger" size="sm"><i className="fas fa-trash-alt"></i></Button>
                                        </div>
                                    </td>
                                )}
                            </tr>
                        )) : (
                            <tr>
                                <td colSpan={isAdmin ? "9" : "8"} className="text-center text-muted py-4"> 
                                    <i className="fas fa-exclamation-triangle me-2"></i> No military ranks match the search criteria.
                                </td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>

            {/* Pagination (Unchanged) */}
            <div className="d-flex justify-content-center mt-4">
                <Pagination size="sm">
                    <Pagination.First onClick={() => setCurrentPage(1)} disabled={currentPage === 1} />
                    <Pagination.Prev onClick={() => setCurrentPage(currentPage - 1)} disabled={currentPage === 1} />
                    {getPageRange().map(page => <Pagination.Item key={page} active={page === currentPage} onClick={() => setCurrentPage(page)}>{page}</Pagination.Item>)}
                    <Pagination.Next onClick={() => setCurrentPage(currentPage + 1)} disabled={currentPage === totalPages} />
                    <Pagination.Last onClick={() => setCurrentPage(totalPages)} disabled={currentPage === totalPages} />
                </Pagination>
            </div>

            <p className="text-center text-muted mt-3 mb-0">
                Displaying **{currentItems.length}** records (Records {startIndex + 1} to {startIndex + currentItems.length} of {filteredItems.length}).
            </p>
        </div>
    );
};

export default MilitaryRankTable;
