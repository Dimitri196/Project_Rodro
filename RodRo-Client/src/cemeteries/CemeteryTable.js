import React, { useState, useMemo, useEffect } from "react";
import { Link } from "react-router-dom";
import { Card, Button, Form, InputGroup, Pagination } from "react-bootstrap";
import { useSession } from "../contexts/session";
import { normalizeString } from "../utils/stringUtils";

const CemeteryTable = ({ label, items, deleteCemetery }) => {
  const { session } = useSession();
  const isAdmin = session.data?.isAdmin === true;

  const [currentPage, setCurrentPage] = useState(1);
  const [itemsPerPage, setItemsPerPage] = useState(10); // Changed default to 10 for consistency
  const [searchTerm, setSearchTerm] = useState("");
  const [sortAsc, setSortAsc] = useState(true);

  const filteredItems = useMemo(() => {
    const filtered = items.filter(item =>
      normalizeString(item.cemeteryName).includes(normalizeString(searchTerm)) ||
      normalizeString(item.cemeteryLocation?.locationName || "").includes(normalizeString(searchTerm)) ||
      normalizeString(item.cemeteryParish?.parishName || "").includes(normalizeString(searchTerm)) ||
      normalizeString(item.description || "").includes(normalizeString(searchTerm))
    );

    return filtered.sort((a, b) => {
      const nameA = a.cemeteryName?.toLowerCase() || "";
      const nameB = b.cemeteryName?.toLowerCase() || "";
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

  const perPageOptions = [10, 20, 50, items.length].filter(
    (val, index, self) => self.indexOf(val) === index
  );

  return (
    <div className="container my-5">
      {/* Header */}
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h2 className="mb-0 text-primary">{label} ({filteredItems.length})</h2>
        {isAdmin && (
          <Link to="/cemeteries/create" className="btn btn-success shadow-sm">
            <i className="fas fa-plus me-2"></i>Create Cemetery
          </Link>
        )}
      </div>

      {/* Search & Controls */}
      <Card className="mb-4 shadow-sm">
        <Card.Body>
          <div className="d-flex flex-column flex-md-row justify-content-between align-items-center gap-3">
            <InputGroup className="flex-grow-1">
              <Form.Control
                type="text"
                placeholder="Search cemeteries by name, location, parish, or description..."
                value={searchTerm}
                onChange={(e) => {
                  setSearchTerm(e.target.value);
                  setCurrentPage(1);
                }}
                onKeyPress={(e) => {
                  if (e.key === "Enter") setSearchTerm(searchTerm);
                }}
              />
              <Button variant="primary" onClick={() => setSearchTerm(searchTerm)}>
                <i className="fas fa-search me-2"></i>Search
              </Button>
              <Button
                variant="outline-secondary"
                onClick={() => {
                  setSearchTerm("");
                  setCurrentPage(1);
                }}
              >
                <i className="fas fa-times me-2"></i>Clear
              </Button>
            </InputGroup>

            <Form.Group className="d-flex align-items-center mb-0">
              <Form.Label className="me-2 mb-0 text-nowrap">Records per page:</Form.Label>
              <Form.Select
                className="w-auto"
                value={itemsPerPage}
                onChange={(e) => {
                  setItemsPerPage(parseInt(e.target.value, 10));
                  setCurrentPage(1);
                }}
              >
                {perPageOptions.map(val => (
                  <option key={val} value={val}>
                    {val === items.length ? "All" : val}
                  </option>
                ))}
              </Form.Select>
            </Form.Group>
          </div>
        </Card.Body>
      </Card>

      {/* Table */}
      <Card className="shadow-sm">
        <Card.Body className="p-0">
          <div className="table-responsive">
            <table className="table table-hover table-striped mb-0">
              <thead className="bg-light">
                <tr>
                  <th className="py-3 px-4">#</th>
                  <th className="py-3 px-4 clickable" onClick={() => setSortAsc(!sortAsc)}>
                    Cemetery Name{" "}
                    <i className={`fas ${sortAsc ? "fa-sort-alpha-down" : "fa-sort-alpha-up"} ms-1`}></i>
                  </th>
                  <th className="py-3 px-4">Location</th>
                  <th className="py-3 px-4">Parish</th>
                  <th className="py-3 px-4">Description</th>
                  {isAdmin && <th className="py-3 px-4 text-center">Actions</th>}
                </tr>
              </thead>
              <tbody>
                {currentItems.length > 0 ? (
                  currentItems.map((item, index) => (
                    <tr key={item._id}>
                      <td className="py-2 px-4">{startIndex + index + 1}</td>
                      <td className="py-2 px-4">{item.cemeteryName}</td>
                      <td className="py-2 px-4">{item.cemeteryLocation?.locationName || "Unknown"}</td>
                      <td className="py-2 px-4">{item.cemeteryParish?.parishName || "Unknown"}</td>
                      <td className="py-2 px-4">{item.description || "N/A"}</td>
                      {isAdmin && (
                        <td className="py-2 px-4 text-center">
                          <div className="d-flex justify-content-center gap-2">
                            <Link to={`/cemeteries/show/${item._id}`} className="btn btn-sm btn-info">
                              <i className="fas fa-eye"></i> View
                            </Link>
                            <Link to={`/cemeteries/edit/${item._id}`} className="btn btn-sm btn-warning">
                              <i className="fas fa-edit"></i> Edit
                            </Link>
                            <Button
                              onClick={() => {
                                // In a real application, you would replace window.confirm with a custom modal/dialog component
                                // For example: showConfirmationModal("Are you sure you want to delete this cemetery?", () => deleteCemetery(item._id));
                                if (window.confirm("Are you sure you want to delete this cemetery?")) {
                                  deleteCemetery(item._id);
                                }
                              }}
                              className="btn btn-sm btn-danger"
                            >
                              <i className="fas fa-trash-alt"></i> Delete
                            </Button>
                          </div>
                        </td>
                      )}
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan={isAdmin ? 6 : 5} className="text-center py-4 text-muted">
                      No cemeteries found.
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>
        </Card.Body>
      </Card>

      {/* Pagination */}
      <nav className="mt-4">
        <Pagination className="justify-content-center shadow-sm">
          <Pagination.First onClick={() => setCurrentPage(1)} disabled={currentPage === 1} />
          <Pagination.Prev onClick={() => setCurrentPage(currentPage - 1)} disabled={currentPage === 1} />
          {getPageRange().map((page) => (
            <Pagination.Item
              key={page}
              active={page === currentPage}
              onClick={() => setCurrentPage(page)}
            >
              {page}
            </Pagination.Item>
          ))}
          <Pagination.Next onClick={() => setCurrentPage(currentPage + 1)} disabled={currentPage === totalPages} />
          <Pagination.Last onClick={() => setCurrentPage(totalPages)} disabled={currentPage === totalPages} />
        </Pagination>
      </nav>
    </div>
  );
};

export default CemeteryTable;
