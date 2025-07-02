import React, { useState, useMemo, useEffect } from "react";
import { Link } from "react-router-dom";
import { useSession } from "../contexts/session";
import { normalizeString } from "../utils/stringUtils";

const OccupationTable = ({ label, items, deleteOccupation }) => {
    const [currentPage, setCurrentPage] = useState(1);
    const [itemsPerPage, setItemsPerPage] = useState(10);
    const [searchTerm, setSearchTerm] = useState("");
    const [sortAsc, setSortAsc] = useState(true);

    const { session } = useSession();
    const isAdmin = session.data?.isAdmin === true;

    const filteredItems = useMemo(() => {
        const filtered = items.filter(item =>
            normalizeString(item.occupationName).includes(normalizeString(searchTerm)) ||
            normalizeString(item.institution?.institutionName || "").includes(normalizeString(searchTerm))
        );

        return filtered.sort((a, b) => {
            const nameA = a.occupationName?.toLowerCase() || "";
            const nameB = b.occupationName?.toLowerCase() || "";
            return sortAsc ? nameA.localeCompare(nameB) : nameB.localeCompare(nameA);
        });
    }, [items, searchTerm, sortAsc]);

    const totalPages = Math.ceil(filteredItems.length / itemsPerPage);
    const startIndex = (currentPage - 1) * itemsPerPage;
    const currentItems = filteredItems.slice(startIndex, startIndex + itemsPerPage);

    useEffect(() => {
        if (currentPage > totalPages) {
            setCurrentPage(totalPages || 1);
        }
    }, [totalPages, currentPage]);

    const handlePageChange = (pageNumber) => setCurrentPage(pageNumber);
    const handleItemsPerPageChange = (e) => {
        setItemsPerPage(parseInt(e.target.value, 10) || 1);
        setCurrentPage(1);
    };
    const handleSortToggle = () => setSortAsc(prev => !prev);
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

    return (
        <div className="container my-4">
            <div className="mb-3 d-flex justify-content-between align-items-center">
                <h4>{label} {items.length}</h4>
                {isAdmin && (
                    <Link to="/occupations/create" className="btn btn-success">Create Occupation</Link>
                )}
            </div>

            <div className="mb-3 d-flex justify-content-center">
                <input
                    type="text"
                    className="form-control w-50"
                    placeholder="Search occupation or institution..."
                    value={searchTerm}
                    onChange={handleSearchChange}
                />
            </div>

            <div className="mb-3 d-flex justify-content-between align-items-center">
                <button className="btn btn-outline-primary" onClick={handleSortToggle}>
                    Sort by Name {sortAsc ? "↓ A–Z" : "↑ Z–A"}
                </button>
                <div className="d-flex align-items-center">
                    <label className="me-2 mb-0">Records per page:</label>
                    <select
                        className="form-select w-auto"
                        value={itemsPerPage}
                        onChange={handleItemsPerPageChange}
                    >
                        {[10, 20, 50, items.length].map(val => (
                            <option key={val} value={val}>
                                {val === items.length ? "All" : val}
                            </option>
                        ))}
                    </select>
                </div>
            </div>

            <div className="table-responsive">
                <table className="table table-bordered table-striped">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Occupation Name</th>
                            <th>Institution</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {currentItems.length > 0 ? currentItems.map((item, index) => (
                            <tr key={item._id}>
                                <td>{startIndex + index + 1}</td>
                                <td>{item.occupationName}</td>
                                <td>
                                    {item.institution?._id ? (
                                        <Link to={`/institutions/show/${item.institution._id}`}>
                                            {item.institution.institutionName}
                                        </Link>
                                    ) : "N/A"}
                                </td>
                                <td>
                                    <div className="btn-group">
                                        <Link to={`/occupations/show/${item._id}`} className="btn btn-sm btn-info mx-1">View</Link>
                                        {isAdmin && (
                                            <>
                                                <Link to={`/occupations/edit/${item._id}`} className="btn btn-sm btn-warning mx-1">Update</Link>
                                                <button
                                                    onClick={() => deleteOccupation(item._id)}
                                                    className="btn btn-sm btn-danger mx-1"
                                                >
                                                    Delete
                                                </button>
                                            </>
                                        )}
                                    </div>
                                </td>
                            </tr>
                        )) : (
                            <tr>
                                <td colSpan="4" className="text-center">No results found.</td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>

            <nav>
                <ul className="pagination justify-content-center">
                    <li className={`page-item ${currentPage === 1 ? "disabled" : ""}`}>
                        <button className="page-link" onClick={() => handlePageChange(1)}>First</button>
                    </li>
                    <li className={`page-item ${currentPage === 1 ? "disabled" : ""}`}>
                        <button className="page-link" onClick={() => handlePageChange(currentPage - 1)}>Previous</button>
                    </li>
                    {getPageRange().map(page => (
                        <li key={page} className={`page-item ${page === currentPage ? "active" : ""}`}>
                            <button className="page-link" onClick={() => handlePageChange(page)}>{page}</button>
                        </li>
                    ))}
                    <li className={`page-item ${currentPage === totalPages ? "disabled" : ""}`}>
                        <button className="page-link" onClick={() => handlePageChange(currentPage + 1)}>Next</button>
                    </li>
                    <li className={`page-item ${currentPage === totalPages ? "disabled" : ""}`}>
                        <button className="page-link" onClick={() => handlePageChange(totalPages)}>Last</button>
                    </li>
                </ul>
            </nav>
        </div>
    );
};

export default OccupationTable;
