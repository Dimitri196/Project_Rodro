import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import {
  Card,
  Button,
  Form,
  Pagination,
  InputGroup,
  Spinner,
  Alert,
} from "react-bootstrap";
import settlementTypeLabels from "../constants/settlementTypeLabels";
import { normalizeString } from "../utils/stringUtils";
import { useSession } from "../contexts/session";
import { apiGet } from "../utils/api";

const LocationTable = ({ label, deleteLocation }) => {
  const [currentPage, setCurrentPage] = useState(0);
  const [itemsPerPage, setItemsPerPage] = useState(10);
  const [searchTerm, setSearchTerm] = useState("");
  const [localSearchTerm, setLocalSearchTerm] = useState("");
  const [sortBy, setSortBy] = useState("locationName");
  const [sortOrder, setSortOrder] = useState("asc");
  const [locationsPage, setLocationsPage] = useState({
    content: [],
    totalElements: 0,
    totalPages: 0,
  });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const { session } = useSession();
  const isAdmin = session.data?.isAdmin === true;

  useEffect(() => {
    const fetchLocations = async () => {
      setLoading(true);
      setError(null);
      try {
        const params = new URLSearchParams({
          page: currentPage,
          size: itemsPerPage,
          sortBy,
          sortOrder,
        });
        if (searchTerm) {
          params.append("searchTerm", searchTerm);
        }

        const data = await apiGet(`/api/locations?${params.toString()}`);
        setLocationsPage(data);
      } catch (err) {
        console.error("Failed to fetch locations:", err);
        setError("Failed to load locations. Please try again later.");
      } finally {
        setLoading(false);
      }
    };

    fetchLocations();
  }, [currentPage, itemsPerPage, sortBy, sortOrder, searchTerm]);

  const handlePageChange = (pageNumber) => setCurrentPage(pageNumber);

  const handleItemsPerPageChange = (e) => {
    setItemsPerPage(parseInt(e.target.value, 10) || 1);
    setCurrentPage(0);
  };

  const handleHeaderClick = (field) => {
    if (sortBy === field) {
      setSortOrder((prev) => (prev === "asc" ? "desc" : "asc"));
    } else {
      setSortBy(field);
      setSortOrder("asc");
    }
    setCurrentPage(0);
  };

  const handleApplySearch = () => {
    setSearchTerm(normalizeString(localSearchTerm));
    setCurrentPage(0);
  };

  const handleClearSearch = () => {
    setLocalSearchTerm("");
    setSearchTerm("");
    setCurrentPage(0);
  };

  const getPageRange = () => {
    const maxPagesToShow = 5;
    const totalPages = locationsPage.totalPages;
    let startPage = Math.max(0, currentPage - Math.floor(maxPagesToShow / 2));
    let endPage = Math.min(totalPages - 1, startPage + maxPagesToShow - 1);

    if (endPage - startPage + 1 < maxPagesToShow) {
      startPage = Math.max(0, endPage - maxPagesToShow + 1);
    }

    return Array.from({ length: endPage - startPage + 1 }, (_, i) => startPage + i);
  };

  const renderSortIcon = (field) => {
    if (sortBy === field) {
      return sortOrder === "asc" ? (
        <i className="fas fa-sort-up ms-1"></i>
      ) : (
        <i className="fas fa-sort-down ms-1"></i>
      );
    }
    return <i className="fas fa-sort ms-1 text-muted"></i>;
  };

  if (loading) {
    return (
      <div className="container my-5 text-center">
        <Spinner animation="border" role="status">
          <span className="visually-hidden">Loading...</span>
        </Spinner>
        <p className="mt-2">Loading locations...</p>
      </div>
    );
  }

  if (error) {
    return (
      <div className="container my-5">
        <Alert variant="danger">{error}</Alert>
      </div>
    );
  }

  return (
    <div className="container my-5">
      <div className="d-flex justify-content-between align-items-center mb-4">
        <h2 className="mb-0 text-primary">
          {label} ({locationsPage.totalElements})
        </h2>
        {isAdmin && (
          <Link to="/locations/create" className="btn btn-success shadow-sm">
            <i className="fas fa-plus me-2"></i>Create Location
          </Link>
        )}
      </div>

      <Card className="mb-4 shadow-sm">
        <Card.Body>
          <div className="d-flex flex-column flex-md-row justify-content-between align-items-center gap-3">
            <InputGroup className="flex-grow-1">
              <Form.Control
                type="text"
                placeholder="Search locations by name..."
                value={localSearchTerm}
                onChange={(e) => setLocalSearchTerm(e.target.value)}
                className="rounded-start-lg"
                onKeyDown={(e) => {
                  if (e.key === "Enter") handleApplySearch();
                }}
              />
              <Button variant="primary" onClick={handleApplySearch}>
                <i className="fas fa-search me-2"></i>Search
              </Button>
              <Button
                variant="outline-secondary"
                onClick={handleClearSearch}
                className="rounded-end-lg"
              >
                <i className="fas fa-times me-2"></i>Clear
              </Button>
            </InputGroup>

            <Form.Group className="d-flex align-items-center mb-0">
              <Form.Label className="me-2 mb-0 text-nowrap">Records per page:</Form.Label>
              <Form.Select
                className="w-auto"
                value={itemsPerPage}
                onChange={handleItemsPerPageChange}
              >
                {[10, 20, 50, locationsPage.totalElements ?? 0].map((val) => (
                  <option key={val} value={val}>
                    {val === (locationsPage.totalElements ?? 0) ? "All" : val}
                  </option>
                ))}
              </Form.Select>
            </Form.Group>
          </div>
        </Card.Body>
      </Card>

      <Card className="shadow-sm">
        <Card.Body className="p-0">
          <div className="table-responsive">
            <table className="table table-hover table-striped mb-0">
              <thead className="bg-light">
                <tr>
                  <th className="py-3 px-4">#</th>
                  <th className="py-3 px-4 clickable" onClick={() => handleHeaderClick("locationName")}>
                    Location Name {renderSortIcon("locationName")}
                  </th>
                  <th className="py-3 px-4 clickable" onClick={() => handleHeaderClick("establishmentYear")}>
                    Est. Year {renderSortIcon("establishmentYear")}
                  </th>
                  <th className="py-3 px-4 clickable" onClick={() => handleHeaderClick("settlementType")}>
                    Type {renderSortIcon("settlementType")}
                  </th>
                  <th className="py-3 px-4 clickable" onClick={() => handleHeaderClick("gpsLatitude")}>
                    Latitude {renderSortIcon("gpsLatitude")}
                  </th>
                  <th className="py-3 px-4 clickable" onClick={() => handleHeaderClick("gpsLongitude")}>
                    Longitude {renderSortIcon("gpsLongitude")}
                  </th>
                  <th className="py-3 px-4 text-center">Actions</th>
                </tr>
              </thead>
              <tbody>
                {locationsPage.content.length > 0 ? (
                  locationsPage.content.map((item, index) => (
                    <tr key={item.id}>
                      <td className="py-2 px-4">
                        {locationsPage.number * locationsPage.size + index + 1}
                      </td>
                      <td className="py-2 px-4">{item.locationName}</td>
                      <td className="py-2 px-4">{item.establishmentYear || "-"}</td>
                      <td className="py-2 px-4">
                        {settlementTypeLabels[item.settlementType] || item.settlementType || "-"}
                      </td>
                      <td className="py-2 px-4">{item.gpsLatitude || "-"}</td>
                      <td className="py-2 px-4">{item.gpsLongitude || "-"}</td>
                      <td className="py-2 px-4 text-center">
                        <div className="d-flex justify-content-center gap-2">
                          <Link to={`/locations/show/${item.id}`} className="btn btn-sm btn-info">
                            <i className="fas fa-eye"></i> View
                          </Link>
                          {isAdmin && (
                            <>
                              <Link to={`/locations/edit/${item.id}`} className="btn btn-sm btn-warning">
                                <i className="fas fa-edit"></i> Edit
                              </Link>
                              <Button
                                onClick={() => deleteLocation(item.id)}
                                className="btn btn-sm btn-danger"
                              >
                                <i className="fas fa-trash-alt"></i> Delete
                              </Button>
                            </>
                          )}
                        </div>
                      </td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan="7" className="text-center py-4 text-muted">
                      No results found.
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>
        </Card.Body>
      </Card>

      <nav className="mt-4">
        <Pagination className="justify-content-center shadow-sm">
          <Pagination.First onClick={() => handlePageChange(0)} disabled={currentPage === 0} />
          <Pagination.Prev onClick={() => handlePageChange(currentPage - 1)} disabled={currentPage === 0} />
          {getPageRange().map((page) => (
            <Pagination.Item
              key={page}
              active={page === currentPage}
              onClick={() => handlePageChange(page)}
            >
              {page + 1}
            </Pagination.Item>
          ))}
          <Pagination.Next
            onClick={() => handlePageChange(currentPage + 1)}
            disabled={currentPage === locationsPage.totalPages - 1}
          />
          <Pagination.Last
            onClick={() => handlePageChange(locationsPage.totalPages - 1)}
            disabled={currentPage === locationsPage.totalPages - 1}
          />
        </Pagination>
      </nav>
    </div>
  );
};

export default LocationTable;
