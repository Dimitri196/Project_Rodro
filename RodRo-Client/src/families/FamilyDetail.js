import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { apiGet } from "../utils/api";
import dateStringFormatter from "../utils/dateStringFormatter";

const FamilyDetail = () => {
    const { id } = useParams();
    const [family, setFamily] = useState({});
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchFamily = async () => {
            try {
                const data = await apiGet(`/api/families/${id}`);
                setFamily(data);
            } catch (err) {
                setError("Failed to fetch family details.");
            } finally {
                setLoading(false);
            }
        };

        fetchFamily();
    }, [id]);

    if (loading) return <div>Loading...</div>;
    if (error) return <div>{error}</div>;

    return (
        <div>
            <h1>Family Detail</h1>
            <hr />
            <h3>Family ID: {family._id}</h3>
            <p><strong>Marriage Date:</strong><br />{family.marriageDate && dateStringFormatter(family.marriageDate, true)}</p>
            <p><strong>Marriage Location:</strong><br />{family.marriageLocation ? family.marriageLocation.name : "N/A"}</p>
            <p><strong>Spouse Male:</strong><br />{family.spouseMale ? family.spouseMale.name : "N/A"}</p>
            <p><strong>Marital Status before Marriage (Male):</strong><br />{family.maritalStatusForSpouseMale || "N/A"}</p>
            <p><strong>Spouse Female:</strong><br />{family.spouseFemale ? family.spouseFemale.name : "N/A"}</p>
            <p><strong>Marital Status before Marriage (Female):</strong><br />{family.maritalStatusForSpouseFemale || "N/A"}</p>
            <p><strong>Male Witnesses:</strong><br />
                {family.witnessesMaleSide1 ? family.witnessesMaleSide1.name : "N/A"}
                {family.witnessesMaleSide2 ? `, ${family.witnessesMaleSide2.name}` : ""}
            </p>
            <p><strong>Female Witnesses:</strong><br />
                {family.witnessesFemaleSide1 ? family.witnessesFemaleSide1.name : "N/A"}
                {family.witnessesFemaleSide2 ? `, ${family.witnessesFemaleSide2.name}` : ""}
            </p>
            <p><strong>Source:</strong><br />{family.source || "N/A"}</p>
            <p><strong>Note:</strong><br />{family.note || "N/A"}</p>
        </div>
    );
};

export default FamilyDetail;
