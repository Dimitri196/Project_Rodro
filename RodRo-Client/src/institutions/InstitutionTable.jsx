import React, { useState, useMemo } from "react";
import { Link } from "react-router-dom";
import { normalizeString } from "../utils/stringUtils";
import { useSession } from "../contexts/session";
import { Button, InputGroup, Form, Pagination, Row, Col } from "react-bootstrap";
import { institutionTypes } from "../constants/institutionTypes";
import "@fortawesome/fontawesome-free/css/all.min.css";

const InstitutionTable = ({ label, items, deleteInstitution }) => {
  const { session } = useSession();
  const isAdmin = session.data?.isAdmin === true;

  const [currentPage, setCurrentPage] = useState(1);
  const [itemsPerPage, setItemsPerPage] = useState(10);
  const [searchTerm, setSearchTerm] = useState("");
  const [sortAsc, setSortAsc] = useState(true);
  // Using 'name' as the primary sort key, aligning with the DTO field
  const [sortKey, setSortKey] = useState("name");

  // --- Styles ---
  const customStyles = `
    .scientific-table {
      --bs-table-bg: #fff;
      --bs-table-color: #343a40;
      --bs-table-border-color: #dee2e6;
      border-radius: 0.5rem;
      overflow: hidden;
      border-collapse: separate;
      border-spacing: 0;
      box-shadow: 0 0.5rem 1rem rgba(0, 0, 0, 0.05);
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

    .scientific-table tbody tr {
      transition: background-color 0.2s ease;
    }

    .scientific-table tbody tr:hover {
      background-color: #f1f7fe;
    }

    .scientific-table td {
      font-size: 0.95rem;
      vertical-align: middle;
      padding: 0.75rem;
    }

    .action-button-group .btn-sm {
      padding: 0.25rem 0.75rem;
      font-size: 0.8rem;
      border-radius: 0.25rem;
    }
  `;

  // --- Filtering + Sorting ---
  const filteredItems = useMemo(() => {
    const filtered = items.filter(
      (item) =>
        // Filtering by name (DTO field: 'name')
        normalizeString(item.name || "").includes(normalizeString(searchTerm)) ||
        // Filtering by country name (NEW)
        normalizeString(item.countryName || "").includes(normalizeString(searchTerm)) ||
        // Filtering by location name (DTO field: 'locationName')
        normalizeString(item.locationName || "").includes(
          normalizeString(searchTerm)
        ) ||
        // Filtering by type (DTO field: 'type')
        normalizeString(item.type || "").includes(normalizeString(searchTerm))
    );

    return filtered.sort((a, b) => {
      let valA = a[sortKey] || "";
      let valB = b[sortKey] || "";

      if (typeof valA === "number" && typeof valB === "number") {
        return sortAsc ? valA - valB : valB - valA;
      }

      valA = valA.toString().toLowerCase();
      valB = valB.toString().toLowerCase();

      return sortAsc ? valA.localeCompare(valB) : valB.localeCompare(valA);
    });
  }, [items, searchTerm, sortAsc, sortKey]);

  // --- Pagination ---
  const totalPages = Math.ceil(filteredItems.length / itemsPerPage) || 1;
  const startIndex = (currentPage - 1) * itemsPerPage;
  const currentItems = filteredItems.slice(startIndex, startIndex + itemsPerPage);

  const handlePageChange = (pageNumber) => setCurrentPage(pageNumber);
  const handleItemsPerPageChange = (e) => {
    setItemsPerPage(parseInt(e.target.value, 10) || 1);
    setCurrentPage(1);
  };

  const handleSort = (key) => {
    if (sortKey === key) {
      setSortAsc((prev) => !prev);
    } else {
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
      if (currentPage <= halfRange) {
        endPage = Math.min(startPage + maxPagesToShow - 1, totalPages);
      } else {
        startPage = Math.max(endPage - maxPagesToShow + 1, 1);
      }
    }
    return Array.from({ length: endPage - startPage + 1 }, (_, i) => startPage + i);
  };

  const renderHeader = (key, label) => (
    <th onClick={() => handleSort(key)}>
      {label}
      {sortKey === key && (
        <i
          className={`fas ms-2 fa-sm ${sortAsc ? "fa-caret-down" : "fa-caret-up"}`}
        ></i>
      )}
    </th>
  );

  // --- Main render ---
  return (
    <div className="my-4 p-4 bg-light rounded shadow-lg">
      <style>{customStyles}</style>

      {/* Control Panel */}
      <Row className="mb-4 align-items-center">
        <Col md={4}>
          <h5 className="mb-0 fw-bold text-dark">
            {label} <span className="text-primary">{filteredItems.length}</span>
          </h5>
        </Col>
        <Col md={5}>
          <InputGroup>
            <InputGroup.Text>
              <i className="fas fa-search"></i>
            </InputGroup.Text>
            <Form.Control
              type="text"
              placeholder="Search by name, location, or type..."
              value={searchTerm}
              onChange={handleSearchChange}
              className="rounded-end"
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
              style={{ width: "80px" }}
              className="shadow-sm"
            >
              {[10, 20, 50, 100, items.length].map((val) => (
                <option key={val} value={val}>
                  {val === items.length ? "All" : val}
                </option>
              ))}
            </Form.Select>
          </div>
          {isAdmin && (
            <Link
              to="/institutions/create"
              className="btn btn-success btn-sm shadow-sm"
            >
              <i className="fas fa-plus me-2"></i>Create
            </Link>
          )}
        </Col>
      </Row>

      {/* Table */}
      <div className="table-responsive">
        <table className="table scientific-table">
          <thead>
            <tr>
              <th style={{ width: "40px" }}>#</th>
              {renderHeader("name", "Institution Name")}
              {renderHeader("countryName", "Country")}
              {renderHeader("locationName", "Location")}
              {renderHeader("type", "Type")}
              {isAdmin && (
                <th className="text-center" style={{ width: "150px" }}>
                  Actions
                </th>
              )}
            </tr>
          </thead>
          <tbody>
            {currentItems.length > 0 ? (
              currentItems.map((item, index) => (
                <tr key={item._id}>
                  <td>{startIndex + index + 1}</td>
                  <td>
                    <Link
                      to={`/institutions/show/${item._id}`}
                      className="fw-bold text-dark text-decoration-none"
                    >
                      {item.name}
                    </Link>
                  </td>
                  <td>
                    {item.countryId ? (
                      <Link
                        to={`/countries/show/${item.countryId}`}
                        className="text-info fw-semibold"
                      >
                        {item.countryName}
                      </Link>
                    ) : (
                      <i className="text-muted">N/A</i>
                    )}
                  </td>
                  <td>
                    {/* Accessing the flattened fields: locationId and locationName */}
                    {item.locationId ? (
                      <Link
                        to={`/locations/show/${item.locationId}`}
                        className="text-primary fw-semibold"
                      >
                        <i className="fas fa-map-marker-alt me-1"></i>{" "}
                        {item.locationName}
                      </Link>
                    ) : (
                      <i className="text-muted">None</i>
                    )}
                  </td>
                  <td>
                    {item.type ? (
                      <span
                        className="badge bg-info text-dark"
                        title={institutionTypes[item.type]}
                      >
                        {item.type.charAt(0) + item.type.slice(1).toLowerCase()}
                      </span>
                    ) : (
                      <i className="text-muted">Unknown</i>
                    )}
                  </td>
                  {isAdmin && (
                    <td className="text-center">
                      <div className="btn-group action-button-group">
                        <Link
                          to={`/institutions/edit/${item._id}`}
                          className="btn btn-sm btn-warning"
                        >
                          <i className="fas fa-edit"></i>
                        </Link>
                        <Button
                          onClick={() => deleteInstitution(item._id)}
                          variant="danger"
                          size="sm"
                        >
                          <i className="fas fa-trash-alt"></i>
                        </Button>
                      </div>
                    </td>
                  )}
                </tr>
              ))
            ) : (
              <tr>
                <td
                  colSpan={isAdmin ? 5 : 4}
                  className="text-center text-muted py-4"
                >
                  <i className="fas fa-exclamation-triangle me-2"></i> No
                  institutions found.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>

      {/* Pagination */}
      <div className="d-flex justify-content-center mt-4">
        <Pagination size="sm">
          <Pagination.First
            onClick={() => handlePageChange(1)}
            disabled={currentPage === 1}
          />
          <Pagination.Prev
            onClick={() => handlePageChange(currentPage - 1)}
            disabled={currentPage === 1}
          />

          {getPageRange().map((page) => (
            <Pagination.Item
              key={page}
              active={page === currentPage}
              onClick={() => handlePageChange(page)}
            >
              {page}
            </Pagination.Item>
          ))}

          <Pagination.Next
            onClick={() => handlePageChange(currentPage + 1)}
            disabled={currentPage === totalPages}
          />
          <Pagination.Last
            onClick={() => handlePageChange(totalPages)}
            disabled={currentPage === totalPages}
          />
        </Pagination>
      </div>

      <p className="text-center text-muted mt-3 mb-0">
        Displaying <b>{currentItems.length}</b> records (Records{" "}
        {startIndex + 1} to {startIndex + currentItems.length} of{" "}
        {filteredItems.length}).
      </p>
    </div>
  );
};

export default InstitutionTable;
