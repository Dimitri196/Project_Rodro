import React, { useState, useMemo } from "react";
import { Link } from "react-router-dom";
import { useSession } from "../contexts/session"; // Adjust import to your project
import { normalizeString } from "../utils/stringUtils"; // e.g., remove accents, lowercase

const SourceTable = ({ label, items, deleteSource }) => {
    const { session } = useSession();
    const isAdmin = session?.data?.isAdmin === true;

    const [currentPage, setCurrentPage] = useState(1);
    const [itemsPerPage, setItemsPerPage] = useState(10);
    const [searchTerm, setSearchTerm] = useState("");
    const [sortAsc, setSortAsc] = useState(true);

    const filteredItems = useMemo(() => {
        const term = normalizeString(searchTerm);
        return items
            .filter(item =>
                normalizeString(item.sourceTitle).includes(term) ||
                normalizeString(item.sourceReference || "").includes(term) ||
                normalizeString(item.sourceType || "").includes(term) ||
                normalizeString(item.sourceDescription || "").includes(term) ||
                normalizeString(item.sourceLocation?.locationName || "").includes(term)
            )
            .sort((a, b) => {
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
        setItemsPerPage(Number(e.target.value));
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
            if (start === 1) end = Math.min(start + max - 1, totalPages);
            else if (end === totalPages) start = Math.max(end - max + 1, 1);
        }

        return Array.from({ length: end - start + 1 }, (_, i) => start + i);
    };

    return (
        <div className="container my-4">
            <div className="d-flex justify-content-between align-items-center mb-3">
                <h5>{label} ({items.length})</h5>
                {isAdmin && (
                    <Link to="/sources/create" className="btn btn-success">Create Source</Link>
                )}
            </div>

            <div className="mb-3">
                <input
                    type="text"
                    className="form-control"
                    placeholder="Search sources..."
                    value={searchTerm}
                    onChange={handleSearchChange}
                />
            </div>

            <div className="d-flex justify-content-between align-items-center mb-3">
                <button className="btn btn-outline-primary" onClick={handleSortToggle}>
                    Sort by Title {sortAsc ? "↓ A–Z" : "↑ Z–A"}
                </button>
                <div className="d-flex align-items-center">
                    <label className="me-2 mb-0">Per page:</label>
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
                <table className="table table-bordered table-hover">
                    <thead className="table-light">
                        <tr>
                            <th>#</th>
                            <th>Title</th>
                            <th>Reference</th>
                            <th>Type</th>
                            <th>Description</th>
                            <th>URL</th>
                            <th>Location</th>
                            <th>Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        {currentItems.length > 0 ? currentItems.map((item, idx) => (
                            <tr key={item.id}>
                                <td>{startIndex + idx + 1}</td>
                                <td>{item.sourceTitle}</td>
                                <td>{item.sourceReference || "-"}</td>
                                <td>{item.sourceType}</td>
                                <td>{item.sourceDescription || "-"}</td>
                                <td>
                                    {item.sourceUrl ? (
                                        <a href={item.sourceUrl} target="_blank" rel="noopener noreferrer">Link</a>
                                    ) : "-"}
                                </td>
                                <td>
                                    {item.sourceLocation ? (
                                        <Link to={`/locations/show/${item.sourceLocation._id}`}>
                                            {item.sourceLocation.locationName}
                                        </Link>
                                    ) : "N/A"}
                                </td>
                                <td>
                                    <div className="btn-group btn-group-sm">
                                        <Link to={`/sources/show/${item._id}`} className="btn btn-info">View</Link>
                                        {isAdmin && (
                                            <>
                                                <Link to={`/sources/edit/${item._id}`} className="btn btn-warning">Edit</Link>
                                                <button
                                                    onClick={() => deleteSource(item._id)}
                                                    className="btn btn-danger"
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
                                <td colSpan="8" className="text-center">No results found.</td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>

            <nav>
                <ul className="pagination justify-content-center">
                    <li className={`page-item ${currentPage === 1 ? "disabled" : ""}`}>
                        <button className="page-link" onClick={() => handlePageChange(currentPage - 1)} disabled={currentPage === 1}>
                            Previous
                        </button>
                    </li>
                    {getPageRange().map((page) => (
                        <li key={page} className={`page-item ${page === currentPage ? "active" : ""}`}>
                            <button className="page-link" onClick={() => handlePageChange(page)}>
                                {page}
                            </button>
                        </li>
                    ))}
                    <li className={`page-item ${currentPage === totalPages ? "disabled" : ""}`}>
                        <button className="page-link" onClick={() => handlePageChange(currentPage + 1)} disabled={currentPage === totalPages}>
                            Next
                        </button>
                    </li>
                </ul>
            </nav>
        </div>
    );
};

export default SourceTable;
