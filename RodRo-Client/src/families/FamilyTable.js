import React, { useState, useMemo } from "react";
import { Link } from "react-router-dom";
import { useSession } from "../contexts/session"; // Only if you want role-based controls
import { normalizeString } from "../utils/stringUtils"; // Optional helper to normalize search

const FamilyTable = ({ label, items, deleteFamily }) => {
  const { session } = useSession();
  const isAdmin = session.data?.isAdmin === true;

  const [currentPage, setCurrentPage] = useState(1);
  const [itemsPerPage, setItemsPerPage] = useState(5);
  const [searchTerm, setSearchTerm] = useState("");

  const filteredItems = useMemo(() => {
    return (items || []).filter(item => {
      const valuesToSearch = [
        item.spouseMale?.givenName,
        item.spouseMale?.givenSurname,
        item.spouseFemale?.givenName,
        item.spouseFemale?.givenSurname,
        item.marriageLocation?.locationName,
        item.note,
        item.source,
      ]
        .join(" ")
        .toLowerCase();

      return normalizeString(valuesToSearch).includes(normalizeString(searchTerm));
    });
  }, [items, searchTerm]);

  const totalPages = Math.ceil(filteredItems.length / itemsPerPage);
  const startIndex = (currentPage - 1) * itemsPerPage;
  const currentItems = filteredItems.slice(startIndex, startIndex + itemsPerPage);

  const handlePageChange = (pageNumber) => setCurrentPage(pageNumber);
  const handleItemsPerPageChange = (e) => {
    setItemsPerPage(parseInt(e.target.value, 10) || 1);
    setCurrentPage(1);
  };
  const handleSearchChange = (e) => {
    setSearchTerm(e.target.value);
    setCurrentPage(1);
  };

  const getPageRange = () => {
    const maxPagesToShow = 5;
    const half = Math.floor(maxPagesToShow / 2);
    let start = Math.max(currentPage - half, 1);
    let end = Math.min(start + maxPagesToShow - 1, totalPages);

    if (end - start + 1 < maxPagesToShow && start > 1) {
      start = Math.max(end - maxPagesToShow + 1, 1);
    }

    return Array.from({ length: end - start + 1 }, (_, i) => start + i);
  };

  return (
    <div className="container my-4">
      {/* Header with count and create button */}
      <div className="mb-3 d-flex justify-content-between align-items-center">
        <h4>{label} {items.length}</h4>
        {isAdmin && (
          <Link to="/families/create" className="btn btn-success">Create Family Record</Link>
        )}
      </div>

      {/* Search bar */}
      <div className="mb-3 d-flex justify-content-center">
        <input
          type="text"
          className="form-control w-50"
          placeholder="Search by names, location, note, or source..."
          value={searchTerm}
          onChange={handleSearchChange}
        />
      </div>

      {/* Pagination and filter controls */}
      <div className="mb-3 d-flex justify-content-between align-items-center">
        <div></div>
        <div className="d-flex align-items-center">
          <label className="me-2 mb-0">Records per page:</label>
          <select
            className="form-select w-auto"
            value={itemsPerPage}
            onChange={handleItemsPerPageChange}
          >
            {[5, 10, 20, 50, items.length].map(val => (
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
              <th>Marriage Date</th>
              <th>Marriage Location</th>
              <th>Spouse Male</th>
              <th>Spouse Female</th>
              <th>Source</th>
              <th>Note</th>
              <th colSpan={3}>Actions</th>
            </tr>
          </thead>
          <tbody>
            {currentItems.length > 0 ? (
              currentItems.map((item, index) => {
                const spouseMale = item.spouseMale
                  ? `${item.spouseMale.givenName || ""} ${item.spouseMale.givenSurname || ""}`.trim()
                  : "N/A";

                const spouseFemale = item.spouseFemale
                  ? `${item.spouseFemale.givenName || ""} ${item.spouseFemale.givenSurname || ""}`.trim()
                  : "N/A";

                const marriageLocation = item.marriageLocation?.locationName || "N/A";
                const source = item.source || "N/A";
                const note = item.note || "N/A";

                return (
                  <tr key={item._id}>
                    <td>{startIndex + index + 1}</td>
                    <td>{item.marriageDate || "N/A"}</td>
                    <td>
                      {item.marriageLocation?._id ? (
                        <Link to={`/locations/show/${item.marriageLocation._id}`} className="text-info">
                          {marriageLocation}
                        </Link>
                      ) : marriageLocation}
                    </td>
                    <td>
                      {item.spouseMale?._id ? (
                        <Link to={`/persons/show/${item.spouseMale._id}`} className="text-info">
                          {spouseMale}
                        </Link>
                      ) : spouseMale}
                    </td>
                    <td>
                      {item.spouseFemale?._id ? (
                        <Link to={`/persons/show/${item.spouseFemale._id}`} className="text-info">
                          {spouseFemale}
                        </Link>
                      ) : spouseFemale}
                    </td>
                    <td>{source}</td>
                    <td>{note}</td>
                    <td>
                      <div className="btn-group">
                        <Link to={`/families/show/${item._id}`} className="btn btn-sm btn-info mx-1">View</Link>
                        {isAdmin && (
                          <>
                            <Link to={`/families/edit/${item._id}`} className="btn btn-sm btn-warning mx-1">Update</Link>
                            <button
                              className="btn btn-sm btn-danger mx-1"
                              onClick={() => {
                                if (window.confirm("Are you sure you want to delete this family record?")) {
                                  deleteFamily(item._id);
                                }
                              }}
                            >
                              Delete
                            </button>
                          </>
                        )}
                      </div>
                    </td>
                  </tr>
                );
              })
            ) : (
              <tr>
                <td colSpan="9" className="text-center">No family records found.</td>
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

export default FamilyTable;
