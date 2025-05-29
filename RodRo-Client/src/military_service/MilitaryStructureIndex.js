import React, { useEffect, useState } from "react";
import { apiGet, apiDelete } from "../utils/api";
import { Container, Alert } from "react-bootstrap";
import MilitaryStructureTable from "./MilitaryStructureTable";

const MilitaryStructureIndex = () => {
  const [structures, setStructures] = useState([]);
  const [error, setError] = useState(null);

  const fetchStructures = async () => {
    try {
      const data = await apiGet("/api/militaryStructures");
      setStructures(data);
    } catch (err) {
      console.error("Error fetching military structures:", err);
      setError("Failed to load military structures. Please try again later.");
    }
  };

  const deleteStructure = async (id) => {
    try {
      await apiDelete(`/api/militaryStructures/${id}`);
      setStructures(prev => prev.filter(item => item._id !== id));
    } catch (err) {
      console.error("Error deleting military structure:", err);
      setError("Failed to delete military structure.");
    }
  };

  useEffect(() => {
    fetchStructures();
  }, []);

  return (
    <Container className="mt-5">
      <h1>Military Structure Records</h1>

      {error && <Alert variant="danger">{error}</Alert>}

      <MilitaryStructureTable
        items={structures}
        deleteStructure={deleteStructure}
      />
    </Container>
  );
};

export default MilitaryStructureIndex;
