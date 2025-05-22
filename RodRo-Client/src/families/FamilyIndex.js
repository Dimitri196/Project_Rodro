import React, { useEffect, useState } from "react";
import { apiGet, apiDelete } from "../utils/api";
import FamilyTable from "./FamilyTable";
import FamilyFilter from "./FamilyFilter";
import { Container, Card, Alert } from "react-bootstrap"; // React-Bootstrap components
import { Link } from "react-router-dom";

const FamilyIndex = () => {
  const [locationListState, setLocationList] = useState([]);
  const [personListState, setPersonList] = useState([]);
  const [familiesState, setFamilies] = useState([]);
  const [filterState, setFilter] = useState({
    marriageLocationID: undefined,
    spouseMaleID: undefined,
    spouseFemaleID: undefined,
    maritalStatusForSpouseMale: undefined,
    maritalStatusForSpouseFemale: undefined,
    marriageDate: undefined,
    note: undefined,
    limit: 10, // Default limit
  });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const locationData = await apiGet("/api/locations");
        const personData = await apiGet("/api/persons");
        const familyData = await apiGet("/api/families");

        setLocationList(locationData);
        setPersonList(personData);
        setFamilies(familyData);
      } catch (err) {
        setError("Failed to load data. Please try again later.");
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, []);

  const deleteFamily = async (id) => {
    try {
      await apiDelete("/api/families/" + id);
      setFamilies(familiesState.filter((family) => family._id !== id));
    } catch (error) {
      setError("Failed to delete family record. Please try again.");
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFilter((prevState) => ({
      ...prevState,
      [name]: value === "" || value === "false" || value === "true" ? undefined : value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const params = { ...filterState };
    Object.keys(params).forEach((key) => {
      if (!params[key]) delete params[key];
    });

    try {
      const filteredFamilies = await apiGet("/api/families", params);
      setFamilies(filteredFamilies);
    } catch (error) {
      setError("Failed to fetch filtered families.");
    }
  };

  if (loading) return <div>Loading family records...</div>;
  if (error) return <Alert variant="danger">{error}</Alert>;

  return (
    <Container className="mt-5">
      <h1>Family Records</h1>
      <hr />
      <Card className="mb-4 shadow-sm">
        <Card.Body>
          <h3>Filter Family Records</h3>
          <FamilyFilter
            handleChange={handleChange}
            handleSubmit={handleSubmit}
            locationList={locationListState}
            personList={personListState}
            filter={filterState}
            confirm="Filter Families"
          />
        </Card.Body>
      </Card>

      {/* New Family Record Button */}
      <div className="text-center mb-4">
        <Link to="/families/create" className="btn btn-success">
          New Family Record
        </Link>
      </div>

      {familiesState.length === 0 ? (
        <div className="text-center py-4">
          <p>No families found. Use the button above to add a new record.</p>
        </div>
      ) : (
        <FamilyTable
          deleteFamily={deleteFamily}
          items={familiesState}
          label="Number of Families:"
        />
      )}
    </Container>
  );
};

export default FamilyIndex;
