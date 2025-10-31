// src/components/StatisticsMap.jsx
import React, { useEffect, useState, useRef } from "react";
import { MapContainer, TileLayer, CircleMarker, Popup, useMap } from "react-leaflet";
import L from "leaflet";
import "leaflet/dist/leaflet.css";
import "leaflet.heat"; // attaches heat layer to L
import { Card, Button, Spinner, Alert, Form } from "react-bootstrap";
import { apiGet } from "../utils/api";

/**
 * Utility: radius scaler for circle markers (simple, adjustable)
 * value = count (number). returns radius in pixels.
 */
const radiusForCount = (count) => {
  if (!count || count <= 0) return 4;
  if (count < 5) return 6;
  if (count < 20) return 10;
  if (count < 50) return 16;
  if (count < 200) return 22;
  return 30;
};

/**
 * Hook to create and manage heat layer on a leaflet map
 */
function useHeatLayer(points /* [[lat,lng,intensity], ...] */, options = {}) {
  const map = useMap();
  const heatRef = useRef(null);

  useEffect(() => {
    if (!map) return;
    // Remove existing layer
    if (heatRef.current) {
      heatRef.current.remove();
      heatRef.current = null;
    }

    if (points && points.length > 0) {
      // create a heat layer
      // leaflet.heat expects array of [lat, lng, intensity]
      heatRef.current = L.heatLayer(points, {
        radius: options.radius || 25,
        blur: options.blur || 15,
        maxZoom: options.maxZoom || 17,
        // max: options.max || (max intensity)
      }).addTo(map);
    }

    return () => {
      if (heatRef.current) {
        heatRef.current.remove();
        heatRef.current = null;
      }
    };
}, [map, JSON.stringify(points), options.radius, options.blur, options.maxZoom]);
}

/**
 * Small component wrapper that uses the hook (must be inside MapContainer)
 */
const HeatLayerComponent = ({ points, options }) => {
  useHeatLayer(points, options);
  return null;
};

const StatisticsMap = ({ width = "100%", height = 450, initialCenter = [52.2, 21.0], initialZoom = 6 }) => {
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const [stats, setStats] = useState([]); // { category, value, ... }
  const [locations, setLocations] = useState([]); // from /api/locations
  const [points, setPoints] = useState([]); // matched coordinates for heat/markers: {lat,lng,count,category}
  const [unmatched, setUnmatched] = useState([]); // categories without matched coords
  const [mode, setMode] = useState("markers"); // or 'heat'

  // compute map center dynamically (weighted centroid)
  const computeCenter = (pts) => {
    if (!pts || pts.length === 0) return initialCenter;
    const sums = pts.reduce(
      (acc, p) => {
        acc.lat += p.lat * p.count;
        acc.lng += p.lng * p.count;
        acc.weight += p.count;
        return acc;
      },
      { lat: 0, lng: 0, weight: 0 }
    );
    if (sums.weight === 0) return initialCenter;
    return [sums.lat / sums.weight, sums.lng / sums.weight];
  };

  useEffect(() => {
    const fetchAll = async () => {
      setLoading(true);
      setError(null);
      try {
        // 1) fetch statistics
        const statsResp = await apiGet("/api/statistics/deaths-by-location");

        // 2) fetch location list (to map category -> coords)
        // Assume the locations endpoint returns { content: [...], ... } or array
        const locResp = await apiGet("/api/locations?size=10000");

        const availableLocations = locResp.content || locResp || [];
        setStats(statsResp || []);
        setLocations(availableLocations || []);

        // Attempt to match
        const matched = [];
        const unmatchedLocal = [];

        // normalize helper
        const normalize = (s) =>
          (s || "")
            .toString()
            .trim()
            .toLowerCase()
            // remove diacritics roughly (basic)
            .normalize("NFD")
            .replace(/[\u0300-\u036f]/g, "");

        // build a map by normalized locationName => location
        const locMap = new Map();
        for (const l of availableLocations) {
          const nm = normalize(l.locationName || l.name || l.location_name || "");
          if (!locMap.has(nm)) locMap.set(nm, []);
          locMap.get(nm).push(l);
        }

        for (const s of statsResp) {
          const category = s.category || s.name || s.categoryName || "";
          const normalized = normalize(category);

          const candidates = locMap.get(normalized) || [];

          if (candidates.length > 0) {
            // pick first candidate which has coordinates
            const chosen = candidates.find(c => (c.latitude || c.lat || c.locationLat || c.latitude === 0) && (c.longitude || c.lon || c.lng || c.locationLng || c.longitude === 0)) || candidates[0];
            const lat = Number(chosen.latitude ?? chosen.lat ?? chosen.locationLat ?? chosen.latitude);
            const lng = Number(chosen.longitude ?? chosen.lon ?? chosen.lng ?? chosen.locationLng ?? chosen.longitude);

            if (!Number.isFinite(lat) || !Number.isFinite(lng)) {
              // no coordinates in chosen
              unmatchedLocal.push({ category, count: s.value });
            } else {
              matched.push({
                category,
                count: s.value,
                lat,
                lng,
                raw: s
              });
            }
          } else {
            unmatchedLocal.push({ category, count: s.value });
          }
        }

        setPoints(matched);
        setUnmatched(unmatchedLocal);
      } catch (err) {
        console.error("StatisticsMap fetch error", err);
        setError("Failed to load map data: " + (err.message || err));
      } finally {
        setLoading(false);
      }
    };

    fetchAll();
  }, []);

  // Build heat points array for leaflet.heat: [lat, lng, intensity]
  const heatJsPoints = points.map((p) => [p.lat, p.lng, Math.max(0.2, Number(p.count || 1))]);

  // Compute center to focus map if we have points
  const center = points.length > 0 ? computeCenter(points) : initialCenter;

  if (loading) return <div className="text-center py-5"><Spinner animation="border" /> <div>Loading map...</div></div>;
  if (error) return <Alert variant="danger">{error}</Alert>;

  return (
    <Card className="shadow-sm mb-4">
      <Card.Header as="h5" className="d-flex justify-content-between align-items-center">
        <div>Geographic Mortality (Deaths by Location)</div>
        <div className="d-flex align-items-center gap-2">
          <Form.Check
            type="switch"
            id="map-mode-switch"
            label={mode === "heat" ? "Heatmap" : "Markers"}
            checked={mode === "heat"}
            onChange={(e) => setMode(e.target.checked ? "heat" : "markers")}
          />
          <Button size="sm" variant="outline-secondary" onClick={() => { /* maybe future: export */ }}>
            Export
          </Button>
        </div>
      </Card.Header>

      <Card.Body style={{ padding: 0 }}>
        <div style={{ width: width, height: height }}>
          <MapContainer center={center} zoom={initialZoom} style={{ width: "100%", height: "100%" }}>
            <TileLayer
              attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a>'
              url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
            />

            {/* Heat or markers */}
            {mode === "heat" ? (
              // The HeatLayerComponent uses leaflet.heat under the hood
              <HeatLayerComponent points={heatJsPoints} options={{ radius: 25, blur: 15 }} />
            ) : (
              points.map((p, idx) => (
                <CircleMarker
                  key={idx}
                  center={[p.lat, p.lng]}
                  radius={radiusForCount(Number(p.count))}
                  stroke
                  weight={1}
                  opacity={0.9}
                  fillOpacity={0.6}
                >
                  <Popup>
                    <div>
                      <strong>{p.category}</strong><br />
                      Deaths: {p.count}
                      {p.raw && p.raw.period && <div><small>{p.raw.period}</small></div>}
                      {/* Link to location page if known (attempt) */}
                      {/** we may have a matched location object in locations; find by lat/lng */}
                      {(() => {
                        const found = locations.find(l => {
                          const lat = Number(l.latitude ?? l.lat ?? l.locationLat ?? l.latitude);
                          const lng = Number(l.longitude ?? l.lon ?? l.lng ?? l.locationLng ?? l.longitude);
                          return Number(lat) === Number(p.lat) && Number(lng) === Number(p.lng);
                        });
                        if (found) {
                          const id = found._id ?? found.id;
                          if (id) return <div><a href={`/locations/show/${id}`}>Open location</a></div>;
                        }
                        return null;
                      })()}
                    </div>
                  </Popup>
                </CircleMarker>
              ))
            )}
          </MapContainer>
        </div>
      </Card.Body>

      <Card.Footer>
        <div className="d-flex justify-content-between">
          <div>
            <strong>Matched locations:</strong> {points.length}
            {" • "}
            <strong>Unmatched:</strong> {unmatched.length}
          </div>
          <div>
            <small className="text-muted">Toggle heatmap/markers. Click a marker for details.</small>
          </div>
        </div>

        {unmatched.length > 0 && (
          <div className="mt-3">
            <h6>Locations without coordinates (not shown on map)</h6>
            <ul style={{ maxHeight: 140, overflowY: "auto", paddingLeft: 20 }}>
              {unmatched.map((u, i) => (
                <li key={i}>
                  {u.category} — {u.count}
                </li>
              ))}
            </ul>
            <p className="text-muted small">To show these on the map you can add their coordinates to the Locations table (latitude/longitude) or improve name matching.</p>
          </div>
        )}
      </Card.Footer>
    </Card>
  );
};

export default StatisticsMap;
