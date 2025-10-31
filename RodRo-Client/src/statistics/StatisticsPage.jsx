import React from "react";
import { Container, Row, Col, Card, Spinner, Alert, ListGroup, Form } from "react-bootstrap";
import {
    BarChart, Bar, XAxis, YAxis,
    CartesianGrid, Tooltip, Legend,
    PieChart, Pie, Cell,
    LineChart, Line, ResponsiveContainer
} from "recharts";
import { apiGet } from "../utils/api";

// Helper for color palettes
const COLORS = [
    '#0088FE', '#00C49F', '#FFBB28', '#FF8042', '#AF19FF', '#FF0066', '#8884d8', '#82ca9d', '#ffc658', '#d0ed57'
];

// --- LABEL MAP FOR CLEANER VISUALIZATION (used for the Pie Chart legend) ---
const SHORT_CATEGORY_LABELS = {
    "Unknown/Other": "Unknown",
    "Infectious and Parasitic Diseases: Tuberculosis, Cholera, Measles, COVID-19, etc.": "Infectious Diseases",
    "Historical and Vague Causes: Old Age, Dropsy, Fever, Spanish Flu, etc.": "Historical/Vague",
    "Respiratory System Diseases: Pneumonia, Asthma, Croup, etc.": "Respiratory",
    "Mental and Neurological Disorders: Epilepsy, Dementia, Melancholia, etc.": "Neurological",
    "Cancers and Tumors: Cancer, Body Tumor, etc.": "Cancer/Tumors",
    "External Causes and Injury: Accident, Suicide, Homicide, War Wounds, etc.": "External/Injury",
    "Maternal and Perinatal Causes: Maternal Deaths, Perinatal Complications, etc.": "Maternal/Perinatal",
    "Digestive and Urinary Disorders: Kidney Disease, Liver Disease, Hernia, etc.": "Digestive/Urinary",
    "Cardiovascular Diseases and Stroke: Heart Disease, Stroke, Hypertension, etc.": "Cardiovascular",
    "Chronic Non-Communicable Diseases: Diabetes, Alcoholism, Starvation, etc.": "Chronic Diseases",
};
// ---------------------------------------------------------------------

// ---------------------------------------------------------------------
// DATA FETCHING HOOK - UPDATED TO USE 'deaths-by-location' FOR LIST
// ---------------------------------------------------------------------
const useStatisticsData = (locationFilter) => {
    const [data, setData] = React.useState({});
    const [loading, setLoading] = React.useState(true);
    const [error, setError] = React.useState(null);
    // State to hold dynamically fetched locations
    const [availableLocations, setAvailableLocations] = React.useState(["All Locations"]); 

    React.useEffect(() => {
        // --- 1. Fetch Location List (Run only once on mount) ---
        const fetchLocations = async () => {
            try {
                // Fetch the list of location statistics
                const locationStats = await apiGet("/api/statistics/deaths-by-location");
                
                // Extract the 'category' (location name) from each object
                const locationNames = locationStats.map(stat => stat.category);
                
                // Set the state with "All Locations" first, followed by the fetched names
                setAvailableLocations(["All Locations", ...locationNames]);

            } catch (err) {
                console.error("Failed to fetch available locations:", err);
                // Fallback
                setAvailableLocations(["All Locations"]); 
            }
        };

        // Only fetch if we are still at the initial state
        if (availableLocations.length === 1 && availableLocations[0] === "All Locations") {
            fetchLocations();
        }
    }, []);


    React.useEffect(() => {
        // --- 2. Fetch Filtered Statistics Data (Run on mount AND when locationFilter changes) ---
        let ageGroupUrl = "/api/statistics/deaths-by-age-group";
        if (locationFilter && locationFilter !== "All Locations") {
            // Encode the location name when passing it to the URL
            ageGroupUrl += `?location=${encodeURIComponent(locationFilter)}`;
        }
        
        const STATISTIC_ENDPOINTS = {
            lifespanByGender: "/api/statistics/average-lifespan",
            deathsByAgeGroup: ageGroupUrl, 
            birthsByYear: "/api/statistics/births-by-year",
            deathsByYear: "/api/statistics/deaths-by-year",
            causeOfDeath: "/api/statistics/cause-of-death-category", 
            peopleBySocialStatus: "/api/statistics/people-by-social-status", 
            deathsBySettlementType: "/api/statistics/deaths-by-settlement-type",
            crudeDeathRate: "/api/statistics/crude-death-rate",
            infantMortalityRate: "/api/statistics/infant-mortality-rate",
            deathsByAllDimensions: "/api/statistics/deaths-by-all-dimensions",
            // Include deathsByLocation to make sure it's always fetched for potential use elsewhere
            deathsByLocation: "/api/statistics/deaths-by-location",
        };

        const fetchAll = async () => {
            setLoading(true);
            setError(null);
            
            const fetchPromises = Object.entries(STATISTIC_ENDPOINTS).map(([key, url]) => 
                apiGet(url)
                    .then(result => ({ key, result }))
                    .catch(err => {
                        console.error(`Failed to fetch ${key} from ${url}:`, err);
                        return { key, result: [] }; 
                    })
            );

            try {
                const allResults = await Promise.all(fetchPromises);
                const aggregatedData = allResults.reduce((acc, { key, result }) => {
                    acc[key] = result;
                    return acc;
                }, {});
                
                setData(aggregatedData);
            } catch (err) {
                console.error("Critical failure during concurrent fetching:", err);
                setError("Failed to load statistics. Check network and backend configuration.");
            } finally {
                setLoading(false);
            }
        };
        // Re-run fetch whenever locationFilter changes
        fetchAll(); 
    }, [locationFilter]); 

    return { data, loading, error, availableLocations };
};

// ---------------------------------------------------------------------
// CHART DATA TRANSFORMATION HELPERS 
// ---------------------------------------------------------------------

const transformLifespan = (data) => {
    const males = data.find(d => d.category === 'MALE');
    const females = data.find(d => d.category === 'FEMALE');
    return [
        { name: 'Male', value: males?.value ?? 0 },
        { name: 'Female', value: females?.value ?? 0 }
    ].filter(d => d.value > 0);
};

const transformCategoryCount = (data, limit = 10) =>
    data.slice(0, limit).map(d => ({
        // Use the translation map for chart labels
        name: SHORT_CATEGORY_LABELS[d.category] || d.category, 
        value: Math.round(d.value)
    }));

const transformDetailCategory = (data) => 
    data.map(d => ({
        category: d.category,
        value: Math.round(d.value)
    }));


const transformTimeseries = (data, valueKey = 'count') =>
    data.filter(d => d.period && !isNaN(+d.period))
        .sort((a, b) => +a.period - +b.period)
        .map(d => ({ year: +d.period, [valueKey]: d.value }));

const transformAgeGroupData = (data) => 
    data.map(d => ({
        name: d.category.split('. ')[1] || d.category,
        count: Math.round(d.value)
    }));


// ---------------------------------------------------------------------
// NEW COMPONENT: Detailed Category List/Table 
// ---------------------------------------------------------------------

const DetailCategoryTable = ({ data }) => {
    const chartData = transformDetailCategory(data);
    return (
        <ListGroup variant="flush">
            {chartData.map((d, index) => (
                <ListGroup.Item key={index} className="d-flex justify-content-between align-items-center" style={{ padding: '0.4rem 0.6rem' }}>
                    <div className="fw-normal" style={{ flex: 1, fontSize: '0.9em' }}>{d.category}</div>
                    <span className="badge bg-primary rounded-pill ms-2">{d.value}</span>
                </ListGroup.Item>
            ))}
        </ListGroup>
    );
};


// ---------------------------------------------------------------------
// CHART COMPONENTS
// ---------------------------------------------------------------------

const LifespanChart = ({ data }) => (
    <ResponsiveContainer width="100%" height={250}>
        <BarChart data={transformLifespan(data)} margin={{ top: 10, right: 30, left: 0, bottom: 5 }}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="name" />
            <YAxis label={{ value: 'Years', angle: -90, position: 'insideLeft' }} />
            <Tooltip />
            <Legend />
            <Bar dataKey="value" name="Avg. Lifespan" fill="#8884d8" />
        </BarChart>
    </ResponsiveContainer>
);

// Helper function to render percentage inside the slice
const renderCustomizedLabel = ({ cx, cy, midAngle, innerRadius, outerRadius, percent, index }) => {
    const radius = innerRadius + (outerRadius - innerRadius) * 0.5;
    const x = cx + radius * Math.cos(-midAngle * Math.PI / 180);
    const y = cy + radius * Math.sin(-midAngle * Math.PI / 180);

    const percentage = (percent * 100).toFixed(0);

    // Only render the label if the slice is large enough (e.g., > 3%)
    if (percentage < 3) return null;

    return (
        <text 
            x={x} 
            y={y} 
            fill="white" 
            textAnchor="middle" 
            dominantBaseline="central" 
            style={{ fontWeight: 'bold', fontSize: '10px' }}
        >
            {`${percentage}%`}
        </text>
    );
};

const PieDistributionChart = ({ data, nameKey, valueKey }) => (
    <ResponsiveContainer width="100%" height={250}>
        <PieChart>
            <Pie
                data={data}
                dataKey={valueKey}
                nameKey={nameKey}
                cx="50%" cy="50%"
                innerRadius={50} 
                outerRadius={100}
                paddingAngle={5}
                
                // Use the custom renderer for internal percentage labels
                labelLine={false} 
                label={renderCustomizedLabel}
            >
                {data.map((entry, idx) => (
                    <Cell key={idx} fill={COLORS[idx % COLORS.length]} />
                ))}
            </Pie>
            <Tooltip formatter={(value) => [Math.round(value), "Count"]} />
            {/* Legend is kept to show the colored labels (now using short names) */}
            <Legend layout="vertical" align="right" verticalAlign="middle" /> 
        </PieChart>
    </ResponsiveContainer>
);

const LineTimeseries = ({ data, lineKey, name, color }) => {
    const chartData = transformTimeseries(data, lineKey);
    return (
        <ResponsiveContainer width="100%" height={300}>
            <LineChart data={chartData} margin={{ top: 5, right: 30, left: 20, bottom: 5 }}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="year" domain={['auto', 'auto']} tickFormatter={val => val.toString()} />
                <YAxis label={{ value: name, angle: -90, position: 'insideLeft' }} />
                <Tooltip />
                <Legend />
                <Line type="monotone" dataKey={lineKey} stroke={color} name={name} dot={false} strokeWidth={2} />
            </LineChart>
        </ResponsiveContainer>
    );
};

const AgeGroupHistogram = ({ data }) => {
    const chartData = transformAgeGroupData(data);
    return (
        <ResponsiveContainer width="100%" height={300}>
            <BarChart data={chartData} margin={{ top: 20, right: 30, left: 20, bottom: 5 }}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="name" interval={0} angle={-35} textAnchor="end" height={80} />
                <YAxis label={{ value: 'Deaths Count', angle: -90, position: 'insideLeft' }} />
                <Tooltip />
                <Legend />
                <Bar dataKey="count" name="Deaths" fill="#8884d8" />
            </BarChart>
        </ResponsiveContainer>
    );
};


// ---------------------------------------------------------------------
// STATISTICS PAGE COMPONENT - MAIN LOGIC
// ---------------------------------------------------------------------

const StatisticsPage = () => {
    // State to manage the selected location filter for the age group chart
    const [selectedLocation, setSelectedLocation] = React.useState("All Locations"); 
    
    // Pass the selected location to the hook and receive availableLocations
    const { data, loading, error, availableLocations } = useStatisticsData(selectedLocation);

    const handleLocationChange = (event) => {
        setSelectedLocation(event.target.value);
    };

    if (loading && data.deathsByAgeGroup === undefined) return (
        <div className="text-center py-5">
            <Spinner animation="border" />
            <div>Loading statistics...</div>
        </div>
    );
    if (error) return <Alert variant="danger">{error}</Alert>;
    

    return (
        <Container className="my-5">
            <h1 className="mb-4 text-center">Demographic Statistics & Scientific Mortality Rates</h1>
            <p className="lead text-center text-muted">
                Analyzing mortality, vitality, and social trends across historical records.
            </p>

            {/* ROW 1: CORE DEMOGRAPHICS & SOCIAL STATUS */}
            <Row className="g-4 mt-5">
                <Col md={6} lg={6}>
                    <Card className="h-100 shadow-sm">
                        <Card.Header>Average Lifespan by Gender</Card.Header>
                        <Card.Body className="d-flex justify-content-center">
                            {data.lifespanByGender?.length ? (
                                <LifespanChart data={data.lifespanByGender} />
                            ) : <p className="text-muted">No data</p>}
                        </Card.Body>
                    </Card>
                </Col>

                <Col md={6} lg={6}>
                    <Card className="h-100 shadow-sm">
                        <Card.Header>People by Social Status</Card.Header>
                        <Card.Body className="d-flex justify-content-center">
                            {data.peopleBySocialStatus?.length ? (
                                <PieDistributionChart 
                                    data={transformCategoryCount(data.peopleBySocialStatus, 7)}
                                    nameKey="name"
                                    valueKey="value"
                                />
                            ) : <p className="text-muted">No data</p>}
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
            
            {/* ROW 2: SCIENTIFIC MORTALITY RATES (LINE CHARTS) */}
            <Row className="g-4 mt-5">
                <Col xs={12} lg={6}>
                    <Card className="h-100 shadow-sm">
                        <Card.Header>Historical Births Trend</Card.Header>
                        <Card.Body className="d-flex justify-content-center">
                            {data.birthsByYear?.length ? (
                                <LineTimeseries data={data.birthsByYear} lineKey="count" name="Total Births" color="#00C49F" />
                            ) : <p className="text-muted">No data</p>}
                        </Card.Body>
                    </Card>
                </Col>

                <Col xs={12} lg={6}>
                    <Card className="h-100 shadow-sm">
                        <Card.Header>Infant Mortality Rate (IMR)</Card.Header>
                        <Card.Body className="d-flex justify-content-center">
                            {data.infantMortalityRate?.length ? (
                                <LineTimeseries 
                                    data={data.infantMortalityRate} 
                                    lineKey="count" 
                                    name="IMR per 1,000 Births" 
                                    color="#FF8042" 
                                />
                            ) : <p className="text-muted">No IMR data available.</p>}
                        </Card.Body>
                    </Card>
                </Col>
                
                <Col xs={12}>
                    <Card className="h-100 shadow-sm">
                        <Card.Header>Historical Death Trend (Crude Death Rate Proxy)</Card.Header>
                        <Card.Body className="d-flex justify-content-center">
                            {data.crudeDeathRate?.length ? (
                                <LineTimeseries 
                                    data={data.crudeDeathRate} 
                                    lineKey="count" 
                                    name="Deaths per Year (CDR Proxy)" 
                                    color="#AF19FF" 
                                />
                            ) : <p className="text-muted">No Crude Death Rate data available.</p>}
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
            
            {/* ROW 3: MORTALITY STRUCTURES (BAR CHARTS & DISTRIBUTION) - AGE GROUP UPDATED */}
            <Row className="g-4 mt-5">
                <Col xs={12} lg={7}>
                    <Card className="h-100 shadow-sm">
                        <Card.Header className="d-flex justify-content-between align-items-center">
                            <span>Deaths Distribution by Age Group (Mortality Profile)</span>
                            
                            {/* LOCATION FILTER DROPDOWN: Uses the dynamically fetched list */}
                            <Form.Select 
                                value={selectedLocation} 
                                onChange={handleLocationChange} 
                                style={{ width: '150px' }}
                                size="sm"
                                // Disable while the list is loading
                                disabled={availableLocations.length <= 1 && loading}
                            >
                                {availableLocations.map(loc => (
                                    <option key={loc} value={loc}>{loc}</option>
                                ))}
                            </Form.Select>
                        </Card.Header>
                        <Card.Body className="d-flex justify-content-center">
                            {data.deathsByAgeGroup?.length ? (
                                <AgeGroupHistogram data={data.deathsByAgeGroup} />
                            ) : (
                                <p className="text-muted">
                                    {loading ? "Loading data..." : `No Age Group data for ${selectedLocation}`}
                                </p>
                            )}
                        </Card.Body>
                    </Card>
                </Col>

                <Col xs={12} lg={5}>
                    <Card className="h-100 shadow-sm">
                        <Card.Header>Deaths by Settlement Type</Card.Header>
                        <Card.Body className="d-flex justify-content-center">
                            {data.deathsBySettlementType?.length ? (
                                <PieDistributionChart 
                                    data={transformCategoryCount(data.deathsBySettlementType, 7)}
                                    nameKey="name"
                                    valueKey="value"
                                />
                            ) : <p className="text-muted">No Settlement Type data</p>}
                        </Card.Body>
                    </Card>
                </Col>
            </Row>

            {/* ROW 4: CAUSE OF DEATH DETAIL */}
            <Row className="g-4 mt-5">
                {/* 4A: Top Causes of Death (Pie Chart - 4 units wide) */}
                <Col md={12} lg={4}>
                    <Card className="h-100 shadow-sm">
                        <Card.Header>Top Causes of Death (Aggregated)</Card.Header>
                        <Card.Body className="d-flex justify-content-center">
                            {data.causeOfDeath?.length ? (
                                <PieDistributionChart 
                                    data={transformCategoryCount(data.causeOfDeath, 7)}
                                    nameKey="name"
                                    valueKey="value"
                                />
                            ) : <p className="text-muted">No data</p>}
                        </Card.Body>
                    </Card>
                </Col>

                {/* 4B: Detailed Mortality Category Descriptions (List/Table - 8 units wide) */}
                <Col md={12} lg={8}>
                    <Card className="h-100 shadow-sm">
                        <Card.Header>Detailed Mortality Category Descriptions</Card.Header>
                        <Card.Body style={{ overflowY: 'auto', maxHeight: '350px' }}>
                            {data.causeOfDeath?.length ? (
                                <DetailCategoryTable data={data.causeOfDeath} />
                            ) : <p className="text-muted">No detailed category data available.</p>}
                        </Card.Body>
                    </Card>
                </Col>
            </Row>
        </Container>
    );
};

export default StatisticsPage;