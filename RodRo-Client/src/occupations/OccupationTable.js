import React, { useState, useMemo } from "react";
import { Link } from "react-router-dom";
import { normalizeString } from "../utils/stringUtils";
import { useSession } from "../contexts/session";
import { Button, InputGroup, Form, Pagination, Row, Col } from "react-bootstrap";
import "@fortawesome/fontawesome-free/css/all.min.css";

const OccupationTable = ({ label, items, deleteOccupation }) => {
  const [currentPage, setCurrentPage] = useState(1);
  const [itemsPerPage, setItemsPerPage] = useState(10);
  const [searchTerm, setSearchTerm] = useState("");
  const [sortAsc, setSortAsc] = useState(true);
  const [sortKey, setSortKey] = useState("occupationName");

  const { session } = useSession();
  const isAdmin = session.data?.isAdmin === true;

  // --- Custom Table Styles ---
  const customStyles = `
    .occupation-table {
      --bs-table-bg: #fff;
      --bs-table-color: #343a40;
      --bs-table-border-color: #dee2e6;
      border-radius: 0.5rem;
      overflow: hidden;
      border-collapse: separate;
      border-spacing: 0;
      box-shadow: 0 0.5rem 1rem rgba(0,0,0,0.05);
    }

    .occupation-table thead th {
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

    .occupation-table tbody tr {
      transition: background-color 0.2s ease;
    }

    .occupation-table tbody tr:hover {
      background-color: #f1f7fe;
    }

    .occupation-table td {
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

  // --- Filter & Sort ---
  const filteredItems = useMemo(() => {
    const filtered = items.filter(
      item =>
        normalizeString(item.occupationName || "").includes(normalizeString(searchTerm)) ||
        normalizeString(item.institution?.institutionName || "").includes(normalizeString(searchTerm))
    );

    return filtered.sort((a, b) => {
      let valA = a[sortKey] || "";
      let valB = b[sortKey] || "";
      valA = valA.toString().toLowerCase();
      valB = valB.toString().toLowerCase();
      return sortAsc ? valA.localeCompare(valB) : valB.localeCompare(valA);
    });
  }, [items, searchTerm, sortAsc, sortKey]);

  const totalPages = Math.ceil(filteredItems.length / itemsPerPage);
  const startIndex = (currentPage - 1) * itemsPerPage;
  const currentItems = filteredItems.slice(startIndex, startIndex + itemsPerPage);

  const handlePageChange = page => setCurrentPage(page);
  const handleItemsPerPageChange = e => {
    setItemsPerPage(parseInt(e.target.value, 10) || 1);
    setCurrentPage(1);
  };
  const handleSort = key => {
    if (sortKey === key) setSortAsc(prev => !prev);
    else {
      setSortKey(key);
      setSortAsc(true);
    }
  };
  const handleSearchChange = e => {
    setSearchTerm(e.target.value);
    setCurrentPage(1);
  };

  const getPageRange = () => {
    const maxPages = 5;
    const half = Math.floor(maxPages / 2);
    let start = Math.max(currentPage - half, 1);
    let end = Math.min(currentPage + half, totalPages);
    if (end - start + 1 < maxPages) start = Math.max(end - maxPages + 1, 1);
    return Array.from({ length: end - start + 1 }, (_, i) => start + i);
  };

  const renderHeader = (key, label) => (
    <th onClick={() => handleSort(key)}>
      {label}{" "}
      {sortKey === key && <i className={`fas ms-2 fa-sm ${sortAsc ? 'fa-caret-down' : 'fa-caret-up'}`}></i>}
    </th>
  );

  return (
    <div className="my-4 p-4 bg-light rounded shadow-lg">
      <style>{customStyles}</style>

      {/* Controls */}
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
              placeholder="Search occupation or institution..."
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
              className="shadow-sm"
            >
              {[10, 20, 50, 100, items.length].map(val => (
                <option key={val} value={val}>{val === items.length ? "All" : val}</option>
              ))}
            </Form.Select>
          </div>
        </Col>
      </Row>

      {/* Table */}
      <div className="table-responsive">
        <table className="table occupation-table">
          <thead>
            <tr>
              <th style={{ width: '40px' }}>#</th>
              {renderHeader("occupationName", "Occupation Name")}
              {renderHeader("institutionName", "Institution")}
              {isAdmin && <th className="text-center" style={{ width: '150px' }}>Actions</th>}
            </tr>
          </thead>
          <tbody>
            {currentItems.length > 0 ? currentItems.map((item, index) => (
              <tr key={item._id}>
                <td>{startIndex + index + 1}</td>
                <td>
                  <Link to={`/occupations/show/${item._id}`} className="fw-bold text-dark text-decoration-none">
                    {item.occupationName}
                  </Link>
                </td>
                <td>
                  {item.institution?._id ? (
                    <Link to={`/institutions/show/${item.institution._id}`} className="text-primary fw-semibold">
                      {item.institution.institutionName}
                    </Link>
                  ) : <i className="text-muted">N/A</i>}
                </td>
                {isAdmin && (
                  <td className="text-center">
                    <div className="btn-group action-button-group">
                      <Link to={`/occupations/edit/${item._id}`} className="btn btn-sm btn-warning">
                        <i className="fas fa-edit"></i>
                      </Link>
                      <Button onClick={() => deleteOccupation(item._id)} variant="danger" size="sm">
                        <i className="fas fa-trash-alt"></i>
                      </Button>
                    </div>
                  </td>
                )}
              </tr>
            )) : (
              <tr>
                <td colSpan={isAdmin ? 4 : 3} className="text-center text-muted py-4">
                  <i className="fas fa-exclamation-triangle me-2"></i>No occupations found.
                </td>
              </tr>
            )}
          </tbody>
        </table>
      </div>

      {/* Pagination */}
      <div className="d-flex justify-content-center mt-4">
        <Pagination size="sm">
          <Pagination.First onClick={() => handlePageChange(1)} disabled={currentPage === 1} />
          <Pagination.Prev onClick={() => handlePageChange(currentPage - 1)} disabled={currentPage === 1} />
          {getPageRange().map(page => (
            <Pagination.Item key={page} active={page === currentPage} onClick={() => handlePageChange(page)}>
              {page}
            </Pagination.Item>
          ))}
          <Pagination.Next onClick={() => handlePageChange(currentPage + 1)} disabled={currentPage === totalPages} />
          <Pagination.Last onClick={() => handlePageChange(totalPages)} disabled={currentPage === totalPages} />
        </Pagination>
      </div>

      <p className="text-center text-muted mt-3 mb-0">
        Displaying {currentItems.length} records (Records {startIndex + 1} to {startIndex + currentItems.length} of {filteredItems.length}).
      </p>
    </div>
  );
};

export default OccupationTable;
