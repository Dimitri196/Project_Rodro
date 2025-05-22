import React, { useState, useMemo } from "react";
import { Link } from "react-router-dom";
import { normalizeString } from "../utils/stringUtils";

const SourceTable = ({ label, items, deleteSource }) => {
    const [currentPage, setCurrentPage] = useState(1);
    const [itemsPerPage, setItemsPerPage] = useState(10);
    const [searchTerm, setSearchTerm] = useState("");
    const [sortAsc, setSortAsc] = useState(true);

    const filteredItems = useMemo(() => {
        const term = normalizeString(searchTerm);
        const filtered = items.filter(item =>
            normalizeString(item.sourceTitle).includes(term) ||
            normalizeString(item.sourceReference).includes(term) ||
            normalizeString(item.sourceType).includes(term) ||
            normalizeString(item.sourceDescription).includes(term)
        );

        return filtered.sort((a, b) => {
            const titleA = a.sourceTitle?.toLowerCase() || "";
            const titleB = b.sourceTitle?.toLowerCase() || "";
            return sortAsc ? titleA.localeCompare(titleB) : titleB.localeCompare(titleA);
        });
    }, [items, searchTerm, sortAsc]);

    const totalPages = Math.ceil(filteredItems.length / itemsPerPage);
    const startIndex = (currentPage - 1) * itemsPerPage;
    const currentItems = filteredItems.slice(startIndex, startIndex + itemsPerPage);

    const handlePageChange = (page) => setCurrentPage(page);
    const handleItemsPerPageChange = (e) => {
        setItemsPerPage(parseInt(e.target.value));
        setCurrentPage(1);
    };
    const handleSearchChange = (e) => {
        setSearchTerm(e.target.value);
        setCurrentPage(1);
    };
    const handleSortToggle = () => setSortAsc(prev => !prev);

    const getPageRange = () => {
        const max = 5;
        const half = Math.floor(max / 2);
        let start = Math.max(currentPage - half, 1);
        let end = Math.min(currentPage + half, totalPages);

        if (end - start < max - 1) {
            if (start === 1) {
                end = Math.min(start + max - 1, totalPages);
            } else if (end === totalPages) {
                start = Math.max(end - max + 1, 1);
            }
        }

        return Array.from({ length: end - start + 1 }, (_, i) => start + i);
    };

    return (
        <div className="container my-4">
            <div className="d-flex justify-content-between align-items-center mb-3">
                <h5>{label} {items.length}</h5>
                <Link to="/sources/create" className="btn btn-success">Create Source</Link>
            </div>

            <div className="mb-3 d-flex justify-content-center">
                <input
                    type="text"
                    className="form-control w-50"
                    placeholder="Search sources..."
                    value={searchTerm}
                    onChange={handleSearchChange}
                />
            </div>

            <div className="mb-3 d-flex justify-content-between align-items-center">
                <button className="btn btn-outline-primary" onClick={handleSortToggle}>
                    Sort by Title {sortAsc ? "↓ A–Z" : "↑ Z–A"}
                </button>
                <div className="d-flex align-items-center">
                    <label className="me-2 mb-0">Records per page:</label>
                    <select
                        className="form-select w-auto"
                        value={itemsPerPage}
                        onChange={handleItemsPerPageChange}
                    >
                        {[10, 20, 50, items.length].map(num => (
                            <option key={num} value={num}>
                                {num === items.length ? "All" : num}
                            </option>
                        ))}
                    </select>
                </div>
            </div>

            <div className="table-responsive">
                <table className="table table-striped table-bordered">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th>Title</th>
                            <th>Reference</th>
                            <th>Type</th>
                            <th>Description</th>
                            <th>URL</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {currentItems.map((item, idx) => (
                            <tr key={item._id}>
                                <td>{startIndex + idx + 1}</td>
                                <td>{item.sourceTitle}</td>
                                <td>{item.sourceReference}</td>
                                <td>{item.sourceType}</td>
                                <td>{item.sourceDescription}</td>
                                <td>
                                    {item.sourceUrl && (
                                        <a href={item.sourceUrl} target="_blank" rel="noopener noreferrer">
                                            Link
                                        </a>
                                    )}
                                </td>
                                <td>
                                    <div className="btn-group">
                                        <Link to={`/sources/show/${item._id}`} className="btn btn-sm btn-info">View</Link>
                                        <Link to={`/sources/edit/${item._id}`} className="btn btn-sm btn-warning">Edit</Link>
                                        <button
                                            onClick={() => deleteSource(item._id)}
                                            className="btn btn-sm btn-danger"
                                        >
                                            Delete
                                        </button>
                                    </div>
                                </td>
                            </tr>
                        ))}
                        {currentItems.length === 0 && (
                            <tr>
                                <td colSpan="7" className="text-center">No results found.</td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>

            <nav>
                <ul className="pagination justify-content-center">
                    <li className={`page-item ${currentPage === 1 ? "disabled" : ""}`}>
                        <button className="page-link" onClick={() => handlePageChange(currentPage - 1)}>Previous</button>
                    </li>
                    {getPageRange().map((page) => (
                        <li key={page} className={`page-item ${page === currentPage ? "active" : ""}`}>
                            <button className="page-link" onClick={() => handlePageChange(page)}>{page}</button>
                        </li>
                    ))}
                    <li className={`page-item ${currentPage === totalPages ? "disabled" : ""}`}>
                        <button className="page-link" onClick={() => handlePageChange(currentPage + 1)}>Next</button>
                    </li>
                </ul>
            </nav>
        </div>
    );
};

export default SourceTable;
