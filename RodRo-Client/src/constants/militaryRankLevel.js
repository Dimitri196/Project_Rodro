// A simple enum-like object for consistency across the frontend.
// The values here should match the string representation of the enum on the backend.

const MilitaryRankLevel = {
    OFFICER: { name: "OFFICER", description: "Officer" },
    NCO: { name: "NCO", description: "Non-Commissioned Officer" },
    ENLISTED: { name: "ENLISTED", description: "Enlisted" }
};

export default MilitaryRankLevel;

