import React, { useState, useMemo } from "react";
import { Link } from "react-router-dom";
import { normalizeString } from "../utils/stringUtils";
import { useSession } from "../contexts/session";
import { Button, InputGroup, Form, Pagination, Row, Col } from 'react-bootstrap';
import "@fortawesome/fontawesome-free/css/all.min.css";

const CountryTable = ({ label, items = [], deleteCountry }) => {
    const [currentPage, setCurrentPage] = useState(1);
    const [itemsPerPage, setItemsPerPage] = useState(10);
    const [searchTerm, setSearchTerm] = useState("");
    const [sortAsc, setSortAsc] = useState(true);
    const [sortKey, setSortKey] = useState("countryNameInPolish");

    const { session } = useSession();
    const isAdmin = session.data?.isAdmin === true;

    const customStyles = `
        .country-table {
            --bs-table-bg: #fff;
            --bs-table-color: #343a40;
            border-radius: 0.5rem;
            overflow: hidden;
            border-collapse: separate;
            border-spacing: 0;
            box-shadow: 0 0.5rem 1rem rgba(0,0,0,0.05);
        }
        .country-table thead th {
            background-color: #0d6efd;
            color: #fff;
            font-weight: 600;
            border-bottom: 3px solid #0a58ca;
            text-transform: uppercase;
            font-size: 0.85rem;
            padding: 1rem 0.75rem;
            cursor: pointer;
        }
        .country-table tbody tr:hover {
            background-color: #e7f1ff;
        }
        .country-table td {
            font-size: 0.95rem;
            vertical-align: middle;
            padding: 0.75rem;
        }
        .action-button-group .btn-sm {
            padding: 0.25rem 0.75rem;
            font-size: 0.8rem;
            border-radius: 0.25rem;
        }
        .flag-img {
            width: 40px;
            height: 25px;
            object-fit: cover;
            border: 1px solid #ddd;
            border-radius: 3px;
        }
    `;

    const filteredItems = useMemo(() => {
        const filtered = items.filter(item =>
            normalizeString(item.countryNameInPolish || "").includes(normalizeString(searchTerm)) ||
            normalizeString(item.countryNameInEnglish || "").includes(normalizeString(searchTerm))
        );

        return filtered.sort((a, b) => {
            let valA = a[sortKey] || "";
            let valB = b[sortKey] || "";
            if (typeof valA === 'number' && typeof valB === 'number') return sortAsc ? valA - valB : valB - valA;
            valA = valA.toString().toLowerCase();
            valB = valB.toString().toLowerCase();
            return sortAsc ? valA.localeCompare(valB) : valB.localeCompare(valA);
        });
    }, [items, searchTerm, sortAsc, sortKey]);

    const totalPages = Math.ceil(filteredItems.length / itemsPerPage);
    const startIndex = (currentPage - 1) * itemsPerPage;
    const currentItems = filteredItems.slice(startIndex, startIndex + itemsPerPage);

    const handlePageChange = (pageNumber) => setCurrentPage(pageNumber);
    const handleItemsPerPageChange = (e) => {
        setItemsPerPage(parseInt(e.target.value, 10) || 1);
        setCurrentPage(1);
    };
    const handleSort = (key) => {
        if (sortKey === key) setSortAsc(prev => !prev);
        else {
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
            if (currentPage <= halfRange) endPage = Math.min(startPage + maxPagesToShow - 1, totalPages);
            else startPage = Math.max(endPage - maxPagesToShow + 1, 1);
        }
        return Array.from({ length: endPage - startPage + 1 }, (_, i) => startPage + i);
    };

    const renderHeader = (key, label) => (
        <th onClick={() => handleSort(key)}>
            {label}
            {sortKey === key && <i className={`fas ms-2 fa-sm ${sortAsc ? 'fa-caret-down' : 'fa-caret-up'}`}></i>}
        </th>
    );

   return (
        <div className="my-4 p-4 bg-light rounded shadow-lg">
            <style>{customStyles}</style>

            <Row className="mb-4 align-items-center">
                <Col md={4}>
                    <h5 className="mb-0 fw-bold text-dark">
                        {label} <span className="text-primary">{items.length}</span>
                    </h5>
                </Col>
                <Col md={5}>
                    <InputGroup>
                        <InputGroup.Text><i className="fas fa-search"></i></InputGroup.Text>
                        <Form.Control
                            type="text"
                            placeholder="Search by Polish or English name..."
                            value={searchTerm}
                            onChange={handleSearchChange}
                        />
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
                        >
                            {[10, 20, 50, items.length].map(val => (
                                <option key={val} value={val}>{val === items.length ? "All" : val}</option>
                            ))}
                        </Form.Select>
                    </div>
                    {isAdmin && (
                        <Link to="/countries/create" className="btn btn-success btn-sm">Create Country</Link>
                    )}
                </Col>
            </Row>

            <div className="table-responsive">
                <table className="table country-table">
                    <thead>
                        <tr>
                            <th>#</th>
                            {renderHeader("countryNameInPolish", "Polish Name")}
                            {renderHeader("countryNameInEnglish", "English Name")}
                            {renderHeader("countryEstablishmentYear", "Established")}
                            {renderHeader("countryCancellationYear", "Cancelled")}
                            <th>Flag</th>
                            {isAdmin && <th>Actions</th>}
                        </tr>
                    </thead>
                    <tbody>
                        {currentItems.length > 0 ? currentItems.map((item, index) => (
                            <tr key={item.id || index}>
                                <td>{startIndex + index + 1}</td>
                                <td>
                                    <Link to={`/countries/show/${item._id}`} className="fw-bold text-dark text-decoration-none">
                                        {item.countryNameInPolish}
                                    </Link>
                                </td>
                                <td>{item.countryNameInEnglish}</td>
                                <td>{item.countryEstablishmentYear || "-"}</td>
                                <td>{item.countryCancellationYear || "-"}</td>
                                <td>{item.countryFlagImgUrl ? <img src={item.countryFlagImgUrl} alt="Flag" className="flag-img"/> : "-"}</td>
                                {isAdmin && (
                                    <td>
                                        <div className="btn-group">
                                            <Link to={`/countries/edit/${item.id}`} className="btn btn-sm btn-warning">
                                                <i className="fas fa-edit"></i>
                                            </Link>
                                            <Button onClick={() => deleteCountry(item.id)} variant="danger" size="sm">
                                                <i className="fas fa-trash-alt"></i>
                                            </Button>
                                        </div>
                                    </td>
                                )}
                            </tr>
                        )) : (
                            <tr>
                                <td colSpan={isAdmin ? "9" : "8"} className="text-center text-muted py-4">
                                    No countries match the search criteria.
                                </td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>

            <div className="d-flex justify-content-center mt-4">
                <Pagination size="sm">
                    <Pagination.First onClick={() => handlePageChange(1)} disabled={currentPage === 1}/>
                    <Pagination.Prev onClick={() => handlePageChange(currentPage - 1)} disabled={currentPage === 1}/>
                    {getPageRange().map(page => (
                        <Pagination.Item key={page} active={page === currentPage} onClick={() => handlePageChange(page)}>
                            {page}
                        </Pagination.Item>
                    ))}
                    <Pagination.Next onClick={() => handlePageChange(currentPage + 1)} disabled={currentPage === totalPages}/>
                    <Pagination.Last onClick={() => handlePageChange(totalPages)} disabled={currentPage === totalPages}/>
                </Pagination>
            </div>
        </div>
    );
};

export default CountryTable;
