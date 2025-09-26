import React, { useState, useEffect } from "react";
import { Container, Alert, Spinner } from "react-bootstrap";
import { apiGet, apiDelete } from "../utils/api";
import LocationTable from "./LocationTable";

const LocationIndex = () => {
  const [locationsPage, setLocationsPage] = useState({
    content: [],
    totalElements: 0,
    totalPages: 0,
    number: 0,
    size: 10,
  });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  // Filters, sorting, pagination
  const [currentPage, setCurrentPage] = useState(0);
  const [itemsPerPage, setItemsPerPage] = useState(10);
  const [searchTerm, setSearchTerm] = useState("");
  const [sortBy, setSortBy] = useState("locationName");
  const [sortOrder, setSortOrder] = useState("asc");

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
      console.error("Error fetching locations:", err);
      setError("Failed to load locations. Please try again later.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchLocations();
  }, [currentPage, itemsPerPage, sortBy, sortOrder, searchTerm]);

  const deleteLocation = async (id) => {
    try {
      await apiDelete(`/api/locations/${id}`);
      // After deletion, refetch to get updated table (clears search automatically if you want)
      fetchLocations();
    } catch (err) {
      console.error("Error deleting location:", err);
      setError("Failed to delete location.");
    }
  };

  if (loading) {
    return (
      <Container className="my-5 text-center">
        <Spinner animation="border" role="status" />
        <p className="mt-2 text-muted">Loading locations...</p>
      </Container>
    );
  }

  return (
    <Container className="mt-5">
      <h1 className="mb-4">Location Records</h1>

      {error && <Alert variant="danger">{error}</Alert>}

      <LocationTable
        label="Number of Locations:"
        items={locationsPage}
        deleteLocation={deleteLocation}
        currentPage={currentPage}
        setCurrentPage={setCurrentPage}
        itemsPerPage={itemsPerPage}
        setItemsPerPage={setItemsPerPage}
        searchTerm={searchTerm}
        setSearchTerm={setSearchTerm}
        sortBy={sortBy}
        setSortBy={setSortBy}
        sortOrder={sortOrder}
        setSortOrder={setSortOrder}
        fetchLocations={fetchLocations}
      />
    </Container>
  );
};

export default LocationIndex;
