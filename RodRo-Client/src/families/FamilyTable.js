import React, { useState } from "react";
import { Link } from "react-router-dom";

const FamilyTable = ({ label, items, deleteFamily }) => {
  const [currentPage, setCurrentPage] = useState(1);
  const [itemsPerPage, setItemsPerPage] = useState(5); // Default number of items per page

  const familyItems = items || [];
  const totalPages = Math.ceil(familyItems.length / itemsPerPage);

  // Get current page items
  const startIndex = (currentPage - 1) * itemsPerPage;
  const currentItems = familyItems.slice(startIndex, startIndex + itemsPerPage);

  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  const handleItemsPerPageChange = (e) => {
    setItemsPerPage(parseInt(e.target.value, 10) || 1); // Ensure the value is valid
    setCurrentPage(1); // Reset to the first page
  };

  return (
    <div>
      <div className="text mb-3">
        <p className="h4">
          {label} {familyItems.length}
        </p>
      </div>

      <div className="d-flex justify-content-between align-items-center mb-3">
        <Link to="/families/create" className="btn btn-success">
          New Family Record
        </Link>

        {/* Dropdown to control items per page */}
        <div>
          <label htmlFor="itemsPerPage" className="me-2">
            Records per page:
          </label>
          <select
            id="itemsPerPage"
            className="form-select d-inline w-auto"
            value={itemsPerPage}
            onChange={handleItemsPerPageChange}
          >
            <option value={5}>5</option>
            <option value={10}>10</option>
            <option value={20}>20</option>
            <option value={50}>50</option>
            <option value={familyItems.length}>All</option>
          </select>
        </div>
      </div>

      <table className="table table-bordered">
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
              // Safe access of nested fields with default values
              const marriageLocation = item.marriageLocation?.locationName || "N/A";
              const spouseMale = item.spouseMale?.givenName + " " + item.spouseMale?.givenSurname  || "N/A";
              const spouseFemale = item.spouseFemale?.givenName  + " " +  item.spouseFemale?.givenSurname || "N/A";
              const source = item.source || "N/A";
              const note = item.note || "N/A";

              return (
                <tr key={item._id}>
                  <td>{startIndex + index + 1}</td>
                  <td>{item.marriageDate || "N/A"}</td>
                  <td>
                    <Link to={`/locations/show/${item.marriageLocation?._id}`} className="text-info">
                      {marriageLocation}

                    </Link>
                  </td>
                  <td>
                    <Link to={`/persons/show/${item.spouseMale?._id}`} className="text-info">
                      {spouseMale}
                    </Link>
                  </td>
                  <td>
                    <Link to={`/persons/show/${item.spouseFemale?._id}`} className="text-info">
                      {spouseFemale}
                    </Link>
                  </td>
                  <td>{source}</td>
                  <td>{note}</td>
                  <td>
                    <div className="btn-group">
                      <Link to={`/families/show/${item._id}`} className="btn btn-sm btn-info">
                        View
                      </Link>
                      <Link to={`/families/edit/${item._id}`} className="btn btn-sm btn-warning">
                        Update
                      </Link>
                      <button
                        onClick={() => {
                          if (
                            window.confirm("Are you sure you want to delete this family record?")
                          ) {
                            deleteFamily(item._id);
                          }
                        }}
                        className="btn btn-sm btn-danger"
                      >
                        Delete
                      </button>
                    </div>
                  </td>
                </tr>
              );
            })
          ) : (
            <tr>
              <td colSpan="8" className="text-center">
                No family records found.
              </td>
            </tr>
          )}
        </tbody>
      </table>

      <div className="d-flex justify-content-between align-items-center">
        {/* Pagination controls */}
        <nav>
          <ul className="pagination">
            <li className={`page-item ${currentPage === 1 ? "disabled" : ""}`}>
              <button
                className="page-link"
                onClick={() => handlePageChange(currentPage - 1)}
              >
                Previous
              </button>
            </li>
            {Array.from({ length: totalPages }, (_, index) => (
              <li
                key={index}
                className={`page-item ${currentPage === index + 1 ? "active" : ""}`}
              >
                <button
                  className="page-link"
                  onClick={() => handlePageChange(index + 1)}
                >
                  {index + 1}
                </button>
              </li>
            ))}
            <li className={`page-item ${currentPage === totalPages ? "disabled" : ""}`}>
              <button
                className="page-link"
                onClick={() => handlePageChange(currentPage + 1)}
              >
                Next
              </button>
            </li>
          </ul>
        </nav>
      </div>
    </div>
  );
};

export default FamilyTable;
