import React, { useState, useMemo } from "react";
import { Link } from "react-router-dom";
import { Card, Button, Form, InputGroup, Pagination } from "react-bootstrap";
import { normalizeString } from "../utils/stringUtils";
import { useSession } from "../contexts/session";

const MilitaryOrganizationTable = ({ items, deleteOrganization }) => {
  const [currentPage, setCurrentPage] = useState(1);
  const [itemsPerPage, setItemsPerPage] = useState(10);
  const [searchTerm, setSearchTerm] = useState("");
  const [sortAsc, setSortAsc] = useState(true);

  const { session } = useSession();
  const isAdmin = session.data?.isAdmin === true;

  const filteredItems = useMemo(() => {
    const filtered = items.filter(item =>
      normalizeString(item.armyName || "").includes(normalizeString(searchTerm))
    );

    return filtered.sort((a, b) => {
      const nameA = a.armyName?.toLowerCase() || "";
      const nameB = b.armyName?.toLowerCase() || "";
      return sortAsc ? nameA.localeCompare(nameB) : nameB.localeCompare(nameA);
    });
  }, [items, searchTerm, sortAsc]);

  const totalPages = Math.ceil(filteredItems.length / itemsPerPage);
  const startIndex = (currentPage - 1) * itemsPerPage;
  const currentItems = filteredItems.slice(startIndex, startIndex + itemsPerPage);

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
        <h2 className="mb-0 text-primary">Military Organizations ({filteredItems.length})</h2>
        {isAdmin && (
          <Link to="/militaryOrganizations/create" className="btn btn-success shadow-sm">
            <i className="fas fa-plus me-2"></i>Create Organization
          </Link>
        )}
      </div>

      {/* Search and Controls */}
      <Card className="mb-4 shadow-sm">
        <Card.Body>
          <div className="d-flex flex-column flex-md-row justify-content-between align-items-center gap-3">
            <InputGroup className="flex-grow-1">
              <Form.Control
                type="text"
                placeholder="Search by army name..."
                value={searchTerm}
                onChange={(e) => {
                  setSearchTerm(e.target.value);
                  setCurrentPage(1);
                }}
                className="rounded-start-lg"
              />
              <Button variant="primary" onClick={() => setSearchTerm(searchTerm)}>
                <i className="fas fa-search me-2"></i>Search
              </Button>
              <Button variant="outline-secondary" onClick={() => {
                setSearchTerm("");
                setCurrentPage(1);
              }} className="rounded-end-lg">
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
                    Army Name <i className={`fas ${sortAsc ? "fa-sort-alpha-down" : "fa-sort-alpha-up"} ms-1`}></i>
                  </th>
                  <th className="py-3 px-4">Branch</th>
                  <th className="py-3 px-4">Country</th>
                  <th className="py-3 px-4">Active From</th>
                  <th className="py-3 px-4">Active To</th>
                  <th className="py-3 px-4">Notes</th>
                  {isAdmin && <th className="py-3 px-4 text-center">Actions</th>}
                </tr>
              </thead>
              <tbody>
                {currentItems.length > 0 ? (
                  currentItems.map((item, index) => (
                    <tr key={item.id || item._id}>
                      <td className="py-2 px-4">{startIndex + index + 1}</td>
                      <td className="py-2 px-4">{item.armyName}</td>
                      <td className="py-2 px-4">{item.armyBranch?.armyBranchName || "-"}</td>
                      <td className="py-2 px-4">{item.country?.countryNameInPolish || "-"}</td>
                      <td className="py-2 px-4">{item.activeFromYear || "-"}</td>
                      <td className="py-2 px-4">{item.activeToYear || "-"}</td>
                      <td className="py-2 px-4">{item.notes || "-"}</td>
                      {isAdmin && (
                        <td className="py-2 px-4 text-center">
  <div className="d-flex justify-content-center gap-2">
    <Link to={`/militaryOrganizations/show/${item._id}`} className="btn btn-sm btn-info">
      <i className="fas fa-eye"></i> View
    </Link>

    {isAdmin && (
      <>
        <Link to={`/militaryOrganizations/edit/${item._id}`} className="btn btn-sm btn-warning">
          <i className="fas fa-edit"></i> Edit
        </Link>
        <Button onClick={() => deleteOrganization(item._id)} className="btn btn-sm btn-danger">
          <i className="fas fa-trash-alt"></i> Delete
        </Button>
      </>
    )}
  </div>
</td>
                      )}
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan={isAdmin ? 8 : 7} className="text-center py-4 text-muted">
                      No military organizations found.
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

export default MilitaryOrganizationTable;
