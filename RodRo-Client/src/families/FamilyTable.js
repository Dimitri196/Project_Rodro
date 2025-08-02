import React, { useState, useMemo, useEffect } from "react";
import { Link } from "react-router-dom";
import { Card, Button, Form, InputGroup, Pagination } from "react-bootstrap"; // Import React-Bootstrap components
import { useSession } from "../contexts/session";
import { normalizeString } from "../utils/stringUtils";

const FamilyTable = ({ label, items, deleteFamily }) => {
  const { session } = useSession();
  const isAdmin = session.data?.isAdmin === true;

  const [currentPage, setCurrentPage] = useState(1);
  const [itemsPerPage, setItemsPerPage] = useState(10); // Changed default to 10 for consistency
  const [searchTerm, setSearchTerm] = useState("");
  // No sorting in original, but adding it for consistency if desired later,
  // or remove if family tables aren't typically sorted by a single column.
  // For now, I'll keep the sort state variable as it's common in your other tables.
  const [sortAsc, setSortAsc] = useState(true); // Added for consistency, though not used in sort logic below

  const filteredItems = useMemo(() => {
    const filtered = (items || []).filter(item => {
      const valuesToSearch = [
        item.spouseMale?.givenName,
        item.spouseMale?.givenSurname,
        item.spouseFemale?.givenName,
        item.spouseFemale?.givenSurname,
        item.marriageLocation?.locationName,
        item.note,
        item.source,
      ]
        .filter(Boolean) // Remove undefined/null values
        .join(" ")
        .toLowerCase();

      return normalizeString(valuesToSearch).includes(normalizeString(searchTerm));
    });

    // You might want to add sorting logic here if there's a primary sort field for families
    // For example, by marriage date or one of the spouse's names.
    // For now, it will just filter. If you want sorting, define a default sort key.
    return filtered.sort((a, b) => {
      // Example sort by male spouse surname, if applicable
      const nameA = a.spouseMale?.givenSurname?.toLowerCase() || "";
      const nameB = b.spouseMale?.givenSurname?.toLowerCase() || "";
      return sortAsc ? nameA.localeCompare(nameB) : nameB.localeCompare(nameA);
    });
  }, [items, searchTerm, sortAsc]); // Added sortAsc to dependencies

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
          <Link to="/families/create" className="btn btn-success shadow-sm">
            <i className="fas fa-plus me-2"></i>Create Family Record
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
                placeholder="Search by names, location, note, or source..."
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
                  <th className="py-3 px-4">Marriage Date</th>
                  <th className="py-3 px-4">Marriage Location</th>
                  <th className="py-3 px-4">Spouse Male</th>
                  <th className="py-3 px-4">Spouse Female</th>
                  <th className="py-3 px-4">Source</th>
                  <th className="py-3 px-4">Note</th>
                  {isAdmin && <th className="py-3 px-4 text-center">Actions</th>}
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
                        <td className="py-2 px-4">{startIndex + index + 1}</td>
                        <td className="py-2 px-4">{item.marriageDate || "N/A"}</td>
                        <td className="py-2 px-4">
                          {item.marriageLocation?._id ? (
                            <Link to={`/locations/show/${item.marriageLocation._id}`} className="text-primary">
                              {marriageLocation}
                            </Link>
                          ) : marriageLocation}
                        </td>
                        <td className="py-2 px-4">
                          {item.spouseMale?._id ? (
                            <Link to={`/persons/show/${item.spouseMale._id}`} className="text-primary">
                              {spouseMale}
                            </Link>
                          ) : spouseMale}
                        </td>
                        <td className="py-2 px-4">
                          {item.spouseFemale?._id ? (
                            <Link to={`/persons/show/${item.spouseFemale._id}`} className="text-primary">
                              {spouseFemale}
                            </Link>
                          ) : spouseFemale}
                        </td>
                        <td className="py-2 px-4">{source}</td>
                        <td className="py-2 px-4">{note}</td>
                        {isAdmin && (
                          <td className="py-2 px-4 text-center">
                            <div className="d-flex justify-content-center gap-2">
                              <Link to={`/families/show/${item._id}`} className="btn btn-sm btn-info">
                                <i className="fas fa-eye"></i> View
                              </Link>
                              <Link to={`/families/edit/${item._id}`} className="btn btn-sm btn-warning">
                                <i className="fas fa-edit"></i> Edit
                              </Link>
                              <Button
                                onClick={() => {
                                  // In a real application, you would replace window.confirm with a custom modal/dialog component
                                  // For example: showConfirmationModal("Are you sure you want to delete this family record?", () => deleteFamily(item._id));
                                  if (window.confirm("Are you sure you want to delete this family record?")) {
                                    deleteFamily(item._id);
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
                    );
                  })
                ) : (
                  <tr>
                    <td colSpan={isAdmin ? 8 : 7} className="text-center py-4 text-muted">
                      No family records found.
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

export default FamilyTable;
