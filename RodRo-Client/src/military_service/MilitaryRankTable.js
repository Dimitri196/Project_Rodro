import React, { useState, useMemo } from "react";
import { Link } from "react-router-dom";
import { normalizeString } from "../utils/stringUtils";
import { useSession } from "../contexts/session";

const MilitaryRankTable = ({ items, deleteRank }) => {
  const [currentPage, setCurrentPage] = useState(1);
  const [itemsPerPage, setItemsPerPage] = useState(5);
  const [searchTerm, setSearchTerm] = useState("");
  const [sortAsc, setSortAsc] = useState(true);

  const { session } = useSession();

  // safer isAdmin check:
  const isAdmin = session?.data?.isAdmin === true;

  const filteredItems = useMemo(() => {
    const filtered = items.filter(item =>
      normalizeString(item.rankName).includes(normalizeString(searchTerm))
    );

    return filtered.sort((a, b) => {
      const nameA = a.rankName?.toLowerCase() || "";
      const nameB = b.rankName?.toLowerCase() || "";
      return sortAsc ? nameA.localeCompare(nameB) : nameB.localeCompare(nameA);
    });
  }, [items, searchTerm, sortAsc]);

  const totalPages = Math.ceil(filteredItems.length / itemsPerPage);
  const startIndex = (currentPage - 1) * itemsPerPage;
  const currentItems = filteredItems.slice(startIndex, startIndex + itemsPerPage);

  const handlePageChange = (page) => setCurrentPage(page);
  const handleItemsPerPageChange = (e) => {
    setItemsPerPage(parseInt(e.target.value, 10));
    setCurrentPage(1);
  };
  const handleSortToggle = () => setSortAsc(prev => !prev);
  const handleSearchChange = (e) => {
    setSearchTerm(e.target.value);
    setCurrentPage(1);
  };

  const getPageRange = () => {
    const maxPages = 5;
    const half = Math.floor(maxPages / 2);
    let start = Math.max(currentPage - half, 1);
    let end = Math.min(start + maxPages - 1, totalPages);

    if (end - start + 1 < maxPages) {
      start = Math.max(end - maxPages + 1, 1);
    }

    return Array.from({ length: end - start + 1 }, (_, i) => start + i);
  };

  return (
    <div className="container my-4">
      <div className="mb-3 d-flex justify-content-between align-items-center">
        <h4>Military Ranks ({items.length})</h4>
        {isAdmin && (
          <Link to="/militaryRanks/create" className="btn btn-success">
            Create
          </Link>
        )}
      </div>

      {/* Search */}
      <div className="mb-3 d-flex justify-content-center">
        <input
          type="text"
          className="form-control w-50"
          placeholder="Search by rank name..."
          value={searchTerm}
          onChange={handleSearchChange}
        />
      </div>

      {/* Sort and per-page */}
      <div className="mb-3 d-flex justify-content-between align-items-center">
        <button className="btn btn-outline-primary" onClick={handleSortToggle}>
          Sort by Rank Name {sortAsc ? "↓ A–Z" : "↑ Z–A"}
        </button>
        <div className="d-flex align-items-center">
          <label className="me-2 mb-0">Per page:</label>
          <select
            className="form-select w-auto"
            value={itemsPerPage}
            onChange={handleItemsPerPageChange}
          >
            {[5, 10, 20, 50, items.length].map((val) => (
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
              <th>Rank Name</th>
              <th>Description</th>
              <th>Level</th>
              <th>Active From</th>
              <th>Active To</th>
              <th>Notes</th>
              {isAdmin && <th>Actions</th>}
            </tr>
          </thead>
          <tbody>
            {currentItems.length > 0 ? (
              currentItems.map((item, index) => (
                <tr key={item._id /* use _id consistently */}>
                  <td>{startIndex + index + 1}</td>
                  <td>{item.rankName}</td>
                  <td>{item.rankDescription || "-"}</td>
                  <td>{item.rankLevel || "-"}</td>
                  <td>{item.activeFromYear || "-"}</td>
                  <td>{item.activeToYear || "-"}</td>
                  <td>{item.notes || "-"}</td>
                  {isAdmin ? (
                    <td>
                      <div className="btn-group">
                        <Link to={`/militaryRanks/show/${item._id}`} className="btn btn-sm btn-info mx-1">
                          View
                        </Link>
                        <Link to={`/militaryRanks/edit/${item._id}`} className="btn btn-sm btn-warning mx-1">
                          Edit
                        </Link>
                        <button onClick={() => deleteRank(item._id)} className="btn btn-sm btn-danger mx-1">
                          Delete
                        </button>
                      </div>
                    </td>
                  ) : null}
                </tr>
              ))
            ) : (
              <tr>
                <td colSpan={isAdmin ? 8 : 7} className="text-center">
                  No military ranks found.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>

      {/* Pagination */}
      <nav>
        <ul className="pagination justify-content-center">
          <li className={`page-item ${currentPage === 1 ? "disabled" : ""}`}>
            <button className="page-link" onClick={() => handlePageChange(1)}>
              First
            </button>
          </li>
          <li className={`page-item ${currentPage === 1 ? "disabled" : ""}`}>
            <button className="page-link" onClick={() => handlePageChange(currentPage - 1)}>
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
            <button className="page-link" onClick={() => handlePageChange(currentPage + 1)}>
              Next
            </button>
          </li>
          <li className={`page-item ${currentPage === totalPages ? "disabled" : ""}`}>
            <button className="page-link" onClick={() => handlePageChange(totalPages)}>
              Last
            </button>
          </li>
        </ul>
      </nav>
    </div>
  );
};

export default MilitaryRankTable;
