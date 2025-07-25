import React, { useEffect, useState } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";
import { useSession } from "../contexts/session";

const SourceForm = () => {
    const { id } = useParams(); // If ID exists, we are in edit mode
    const navigate = useNavigate();
    const { session } = useSession();
    const isAdmin = session.data?.isAdmin;

    const [source, setSource] = useState({
        sourceTitle: "",
        sourceDescription: "",
        sourceReference: "",
        sourceType: "BAPTISM",
        sourceUrl: "",
        sourceLocationId: ""
    });

    const [locations, setLocations] = useState([]);
    const [errors, setErrors] = useState({});
    const isEditMode = Boolean(id);

    useEffect(() => {
        // Load location options
        axios.get("/api/locations")
            .then(res => setLocations(res.data))
            .catch(() => alert("Failed to load locations"));

        if (isEditMode) {
            axios.get(`/api/sources/${id}`)
                .then(res => {
                    const src = res.data;
                    setSource({
                        sourceTitle: src.sourceTitle,
                        sourceDescription: src.sourceDescription,
                        sourceReference: src.sourceReference,
                        sourceType: src.sourceType,
                        sourceUrl: src.sourceUrl,
                        sourceLocationId: src.sourceLocation?.id || ""
                    });
                })
                .catch(() => alert("Failed to load source"));
        }
    }, [id]);

    const handleChange = e => {
        const { name, value } = e.target;
        setSource(prev => ({ ...prev, [name]: value }));
    };

    const validate = () => {
        const errs = {};
        if (!source.sourceTitle.trim()) errs.sourceTitle = "Title is required";
        if (!source.sourceType) errs.sourceType = "Type is required";
        if (!source.sourceLocationId) errs.sourceLocationId = "Location is required";
        return errs;
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const validationErrors = validate();
        if (Object.keys(validationErrors).length > 0) {
            setErrors(validationErrors);
            return;
        }

        const payload = {
            sourceTitle: source.sourceTitle,
            sourceDescription: source.sourceDescription,
            sourceReference: source.sourceReference,
            sourceType: source.sourceType,
            sourceUrl: source.sourceUrl,
            sourceLocation: {
                id: parseInt(source.sourceLocationId, 10)
            }
        };

        try {
            if (isEditMode) {
                await axios.put(`/api/sources/${id}`, payload);
            } else {
                await axios.post("/api/sources", payload);
            }
            navigate("/sources"); // redirect to source list
        } catch (err) {
            alert("Error saving source");
        }
    };

    if (!isAdmin) return <p>You are not authorized to view this page.</p>;

    return (
        <div className="container my-4">
            <h3>{isEditMode ? "Edit Source" : "Create Source"}</h3>

            <form onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label className="form-label">Source Title</label>
                    <input
                        type="text"
                        name="sourceTitle"
                        className={`form-control ${errors.sourceTitle ? "is-invalid" : ""}`}
                        value={source.sourceTitle}
                        onChange={handleChange}
                    />
                    {errors.sourceTitle && <div className="invalid-feedback">{errors.sourceTitle}</div>}
                </div>

                <div className="mb-3">
                    <label className="form-label">Description</label>
                    <textarea
                        name="sourceDescription"
                        className="form-control"
                        rows={4}
                        value={source.sourceDescription}
                        onChange={handleChange}
                    />
                </div>

                <div className="mb-3">
                    <label className="form-label">Reference</label>
                    <input
                        type="text"
                        name="sourceReference"
                        className="form-control"
                        value={source.sourceReference}
                        onChange={handleChange}
                    />
                </div>

                <div className="mb-3">
                    <label className="form-label">Source Type</label>
                    <select
                        name="sourceType"
                        className={`form-select ${errors.sourceType ? "is-invalid" : ""}`}
                        value={source.sourceType}
                        onChange={handleChange}
                    >
                        <option value="">-- Select Type --</option>
                        <option value="BAPTISM">Baptism</option>
                        <option value="MARRIAGE">Marriage</option>
                        <option value="BURIAL">Burial</option>
                        <option value="OTHER">Other</option>
                    </select>
                    {errors.sourceType && <div className="invalid-feedback">{errors.sourceType}</div>}
                </div>

                <div className="mb-3">
                    <label className="form-label">Source URL</label>
                    <input
                        type="url"
                        name="sourceUrl"
                        className="form-control"
                        value={source.sourceUrl}
                        onChange={handleChange}
                    />
                </div>

                <div className="mb-3">
                    <label className="form-label">Location</label>
                    <select
                        name="sourceLocationId"
                        className={`form-select ${errors.sourceLocationId ? "is-invalid" : ""}`}
                        value={source.sourceLocationId}
                        onChange={handleChange}
                    >
                        <option value="">-- Select Location --</option>
                        {locations.map(loc => (
                            <option key={loc.id} value={loc.id}>
                                {loc.locationName}
                            </option>
                        ))}
                    </select>
                    {errors.sourceLocationId && <div className="invalid-feedback">{errors.sourceLocationId}</div>}
                </div>

                <div className="text-end">
                    <button type="submit" className="btn btn-primary">
                        {isEditMode ? "Update Source" : "Create Source"}
                    </button>
                </div>
            </form>
        </div>
    );
};

export default SourceForm;
