import React, { useState, useMemo } from "react";
import { Link } from "react-router-dom";
import { normalizeString } from "../utils/stringUtils";
import { useSession } from "../contexts/session";
import { Button, InputGroup, Form, Pagination, Row, Col, Badge } from "react-bootstrap";
import "@fortawesome/fontawesome-free/css/all.min.css";

const MilitaryOrganizationTable = ({ label = "Military Organizations", items, deleteOrganization }) => {
  const [currentPage, setCurrentPage] = useState(1);
  const [itemsPerPage, setItemsPerPage] = useState(10);
  const [searchTerm, setSearchTerm] = useState("");
  const [sortAsc, setSortAsc] = useState(true);
  const [sortKey, setSortKey] = useState("armyName");

  const { session } = useSession();
  const isAdmin = session.data?.isAdmin === true;

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
      background-color: #0d6efd;
      color: #fff;
      font-weight: 600;
      border-bottom: 3px solid #004080;
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

  // --- Filtering & Sorting ---
  const matchesSearch = (item, term) =>
    normalizeString(item.armyName).includes(term) ||
    normalizeString(item.armyBranch?.armyBranchName).includes(term) ||
    normalizeString(item.country?.countryNameInPolish).includes(term) ||
    normalizeString(item.notes).includes(term);

  const filteredItems = useMemo(() => {
    const filtered = items.filter((item) =>
      matchesSearch(item, normalizeString(searchTerm))
    );

    return filtered.sort((a, b) => {
      let valA = "";
      let valB = "";

      if (sortKey === "armyName") {
        valA = a.armyName || "";
        valB = b.armyName || "";
      } else if (sortKey === "armyBranch") {
        valA = a.armyBranch?.armyBranchName || "";
        valB = b.armyBranch?.armyBranchName || "";
      } else if (sortKey === "country") {
        valA = a.country?.countryNameInPolish || "";
        valB = b.country?.countryNameInPolish || "";
      } else if (sortKey === "activeFromYear") {
        valA = a.activeFromYear || 0;
        valB = b.activeFromYear || 0;
      }

      if (typeof valA === "number" && typeof valB === "number") {
        return sortAsc ? valA - valB : valB - valA;
      }

      return sortAsc
        ? valA.toString().localeCompare(valB.toString())
        : valB.toString().localeCompare(valA.toString());
    });
  }, [items, searchTerm, sortKey, sortAsc]);

  // --- Pagination ---
  const totalPages = Math.ceil(filteredItems.length / itemsPerPage);
  const startIndex = (currentPage - 1) * itemsPerPage;
  const currentItems = filteredItems.slice(startIndex, startIndex + itemsPerPage);

  const handleSort = (key) => {
    if (sortKey === key) {
      setSortAsc((prev) => !prev);
    } else {
      setSortKey(key);
      setSortAsc(true);
    }
  };

  const handlePageChange = (page) => setCurrentPage(page);
  const handleItemsPerPageChange = (e) => {
    setItemsPerPage(parseInt(e.target.value, 10) || 1);
    setCurrentPage(1);
  };

  const getPageRange = () => {
    const maxPages = 5;
    const half = Math.floor(maxPages / 2);
    let start = Math.max(currentPage - half, 1);
    let end = Math.min(currentPage + half, totalPages);

    if (end - start + 1 < maxPages) {
      if (currentPage <= half) {
        end = Math.min(start + maxPages - 1, totalPages);
      } else {
        start = Math.max(end - maxPages + 1, 1);
      }
    }
    return Array.from({ length: end - start + 1 }, (_, i) => start + i);
  };

  // --- Helpers ---
  const renderHeader = (key, label) => (
    <th
      onClick={() => handleSort(key)}
      role="button"
      aria-sort={sortKey === key ? (sortAsc ? "ascending" : "descending") : "none"}
      title={`Sort by ${label}`}
    >
      {label}
      {sortKey === key && (
        <i className={`fas ms-2 fa-sm ${sortAsc ? "fa-caret-down" : "fa-caret-up"}`}></i>
      )}
    </th>
  );

  const renderActiveYears = (from, to) => (
    <td>
      <Badge bg="success" className="me-1">{from || "?"}</Badge>
      –
      {to ? (
        <Badge bg="danger" className="ms-1">{to}</Badge>
      ) : (
        <Badge bg="info" className="ms-1">Present</Badge>
      )}
    </td>
  );

  return (
    <div className="my-4 p-4 bg-light rounded shadow-lg">
      <style>{customStyles}</style>

      {/* Header */}
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
              placeholder="🔍 Search army, branch, country, or notes..."
              value={searchTerm}
              onChange={(e) => {
                setSearchTerm(e.target.value);
                setCurrentPage(1);
              }}
              className="rounded-end shadow-sm"
            />
          </InputGroup>
        </Col>
        <Col md={3} className="d-flex justify-content-end">
          {isAdmin && (
            <Link to="/militaryOrganizations/create" className="btn btn-success shadow-sm">
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
              {renderHeader("armyName", "Army Name")}
              {renderHeader("armyBranch", "Branch")}
              {renderHeader("country", "Country")}
              {renderHeader("activeFromYear", "Active Years")}
              <th>Notes</th>
              {isAdmin && <th className="text-center">Actions</th>}
            </tr>
          </thead>
          <tbody>
            {currentItems.length > 0 ? (
              currentItems.map((item, index) => (
                <tr key={item.id || item._id}>
                  <td>{startIndex + index + 1}</td>
                  <td>
                    <Link
                      to={`/militaryOrganizations/show/${item._id}`}
                      className="fw-bold text-dark text-decoration-none"
                    >
                      {item.armyName}
                    </Link>
                  </td>
                  <td>{item.armyBranch?.armyBranchName || <i className="text-muted">-</i>}</td>
                  <td>{item.country?.countryNameInPolish || <i className="text-muted">-</i>}</td>
                  {renderActiveYears(item.activeFromYear, item.activeToYear)}
                  <td>{item.notes || <i className="text-muted">-</i>}</td>
                  {isAdmin && (
                    <td className="text-center">
                      <div className="btn-group action-button-group">
                        <Link to={`/militaryOrganizations/edit/${item._id}`} className="btn btn-sm btn-warning">
                          <i className="fas fa-edit"></i>
                        </Link>
                        <Button
                          onClick={() => deleteOrganization(item._id)}
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
                <td colSpan={isAdmin ? "8" : "7"} className="text-center text-muted py-4">
                  <i className="fas fa-exclamation-triangle me-2"></i>
                  {searchTerm ? `No results match "${searchTerm}".` : "No military organizations available."}
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>

      {/* Pagination */}
      <div className="d-flex justify-content-between align-items-center mt-4">
        <div className="d-flex align-items-center">
          <label className="me-2 mb-0 fw-semibold text-muted">Records:</label>
          <Form.Select
            size="sm"
            value={itemsPerPage}
            onChange={handleItemsPerPageChange}
            style={{ width: "80px" }}
          >
            {[10, 20, 50, 100, items.length].map((val) => (
              <option key={val} value={val}>
                {val === items.length ? "All" : val}
              </option>
            ))}
          </Form.Select>
        </div>

        <Pagination size="sm">
          <Pagination.First onClick={() => handlePageChange(1)} disabled={currentPage === 1} />
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
        Displaying <b>{currentItems.length}</b> records (Records {startIndex + 1}–{startIndex + currentItems.length} of {filteredItems.length}).
      </p>
    </div>
  );
};

export default MilitaryOrganizationTable;
