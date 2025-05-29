import React, { useEffect, useState } from "react";
import { Alert } from "react-bootstrap";
import { apiGet, apiDelete } from "../utils/api";
import MilitaryRankTable from "./MilitaryRankTable";

const MilitaryRankIndex = () => {
    const [ranks, setRanks] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchRanks = async () => {
            try {
                const data = await apiGet("/api/militaryRanks");
                setRanks(data);
            } catch (err) {
                setError(`Error loading ranks: ${err.message || err}`);
            } finally {
                setLoading(false);
            }
        };

        fetchRanks();
    }, []);

    const deleteRank = async (id) => {
        if (window.confirm("Are you sure you want to delete this military rank?")) {
            try {
                await apiDelete(`/api/militaryRanks/${id}`);
                setRanks(prev => prev.filter(rank => rank.id !== id));
            } catch (err) {
                alert(`Delete failed: ${err.message || err}`);
            }
        }
    };

    if (loading) return <p>Loading military ranks...</p>;
    if (error) return <Alert variant="danger">{error}</Alert>;

    return <MilitaryRankTable items={ranks} deleteRank={deleteRank} />;
};

export default MilitaryRankIndex;
