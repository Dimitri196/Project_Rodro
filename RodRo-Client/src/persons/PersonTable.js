import React, { useState, useMemo } from "react";
import { Link } from "react-router-dom";

const PersonTable = ({ label, items, deletePerson }) => {
    const [currentPage, setCurrentPage] = useState(1);
    const [itemsPerPage, setItemsPerPage] = useState(10);
    const [searchTerm, setSearchTerm] = useState("");
    const [sortAsc, setSortAsc] = useState(true);

    // Filtered and sorted list
    const filteredItems = useMemo(() => {
        const filtered = items.filter(item =>
            (`${item.givenName} ${item.givenSurname}`.toLowerCase().includes(searchTerm.toLowerCase()) ||
            (item.identificationNumber?.toString().toLowerCase().includes(searchTerm.toLowerCase())))
        );
        return filtered.sort((a, b) => {
            const nameA = `${a.givenName} ${a.givenSurname}`.toLowerCase();
            const nameB = `${b.givenName} ${b.givenSurname}`.toLowerCase();
            return sortAsc ? nameA.localeCompare(nameB) : nameB.localeCompare(nameA);
        });
    }, [items, searchTerm, sortAsc]);

    // Pagination logic
    const totalPages = Math.ceil(filteredItems.length / itemsPerPage);
    const startIndex = (currentPage - 1) * itemsPerPage;
    const currentItems = filteredItems.slice(startIndex, startIndex + itemsPerPage);

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
                <Link to="/persons/create" className="btn btn-success">Create Person</Link>
            </div>

            {/* Centered search bar */}
            <div className="mb-3 d-flex justify-content-center">
                <input
                    type="text"
                    className="form-control w-50"
                    placeholder="Search by fullname, name, surname ..."
                    value={searchTerm}
                    onChange={handleSearchChange}
                />
            </div>

            {/* Sort and pagination controls */}
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

            {/* Table */}
            <div className="table-responsive">
                <table className="table table-bordered table-striped">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>GenWeb ID</th>
                            <th>Full Name</th>
                            <th>Years Of Life</th>
                            <th colSpan={3}>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {currentItems.length > 0 ? (
                            currentItems.map((item, index) => (
                                <tr key={item._id}>
                                    <td>{startIndex + index + 1}</td>
                                    <td>{item.identificationNumber}</td>
                                    <td>{item.givenName} {item.givenSurname}</td>
                                    <td>{item.birthDate} <strong>|</strong> {item.deathDate}</td>
                                    <td>
                                        <div className="btn-group">
                                            <Link to={`/persons/show/${item._id}`} className="btn btn-sm btn-info mx-1">View</Link>
                                            <Link to={`/persons/edit/${item._id}`} className="btn btn-sm btn-warning mx-1">Update</Link>
                                            <button onClick={() => deletePerson(item._id)} className="btn btn-sm btn-danger mx-1">
                                                Delete
                                            </button>
                                        </div>
                                    </td>
                                </tr>
                            ))
                        ) : (
                            <tr>
                                <td colSpan="6" className="text-center">No results found.</td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>

            {/* Pagination */}
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

export default PersonTable;