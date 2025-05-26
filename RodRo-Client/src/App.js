import React from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter as Router, Link, Route, Routes, Navigate } from "react-router-dom";

// Import pages
import HomePage from "./HomePage";

// Persons
import PersonIndex from "./persons/PersonIndex";
import PersonDetail from "./persons/PersonDetail";
import PersonForm from "./persons/PersonForm";

// Locations
import LocationIndex from "./locations/LocationIndex";
import LocationDetail from "./locations/LocationDetail";
import LocationForm from "./locations/LocationForm";

// Countries and Provinces
import CountryIndex from "./countries/CountryIndex";
import CountryDetail from "./countries/CountryDetail";
import CountryForm from "./countries/CountryForm";
import ProvinceDetail from "./countries/ProvinceDetail";
import DistrictDetail from "./countries/DistrictDetail";

// Families
import FamilyIndex from "./families/FamilyIndex";
import FamilyDetail from "./families/FamilyDetail";
import FamilyForm from "./families/FamilyForm";

// Parishes
import ParishIndex from "./parishes/ParishIndex";
import ParishDetail from "./parishes/ParishDetail";
import ParishForm from "./parishes/ParishForm";

// Cemeteries
import CemeteryIndex from "./cemetries/CemeteryIndex";
import CemeteryDetail from "./cemetries/CemeteryDetail";
import CemeteryForm from "./cemetries/CemeteryForm";

// Subdivision
import SubdivisionDetail from "./countries/SubdivisionDetail";

// Institutions
import InstitutionIndex from "./institutions/InstitutionIndex";
import InstitutionDetail from "./institutions/InstitutionDetail";

// Occupations
import OccupationIndex from "./occupations/OccupationIndex";
import OccupationDetail from "./occupations/OccupationDetail";

// Sources
import SourceIndex from "./sources/SourceIndex";
import SourceDetail from "./sources/SourceDetail";



export function App() {
  return (
    <Router>
      <div className="d-flex flex-column min-vh-100">
        {/* Navigation Panel */}
        <nav className="navbar navbar-expand-lg navbar-light bg-light">
          <div className="container">
            <Link to="/" className="navbar-brand">Project RodRo</Link>
            <button className="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav">
              <span className="navbar-toggler-icon"></span>
            </button>
            <div className="collapse navbar-collapse" id="navbarNav">
              <ul className="navbar-nav ml-auto">
                <li className="nav-item"><Link to="/home" className="nav-link">Home</Link></li>
                <li className="nav-item"><Link to="/persons" className="nav-link">Persons</Link></li>
                <li className="nav-item"><Link to="/locations" className="nav-link">Locations</Link></li>
                <li className="nav-item"><Link to="/countries" className="nav-link">Countries</Link></li>
                <li className="nav-item"><Link to="/parishes" className="nav-link">Parishes</Link></li>
                <li className="nav-item"><Link to="/families" className="nav-link">Families</Link></li>
                <li className="nav-item"><Link to="/cemeteries" className="nav-link">Cemeteries</Link></li>
                <li className="nav-item"><Link to="/institutions" className="nav-link">Institutions</Link></li>
                <li className="nav-item"><Link to="/occupations" className="nav-link">Occupations</Link></li>
                <li className="nav-item"><Link to="/sources" className="nav-link">Sources</Link></li>
              </ul>
            </div>
          </div>
        </nav>

        {/* Main Content */}
        <div className="container flex-grow-1 d-flex justify-content-center align-items-center py-4">
          <Routes>
            <Route path="/" element={<Navigate to="/home" />} />
            <Route path="/home" element={<HomePage />} />

            {/* Persons */}
            <Route path="/persons">
              <Route index element={<PersonIndex />} />
              <Route path="show/:id" element={<PersonDetail />} />
              <Route path="create" element={<PersonForm />} />
              <Route path="edit/:id" element={<PersonForm />} />
            </Route>

            {/* Families */}
            <Route path="/families">
              <Route index element={<FamilyIndex />} />
              <Route path="show/:id" element={<FamilyDetail />} />
              <Route path="create" element={<FamilyForm />} />
              <Route path="edit/:id" element={<FamilyForm />} />
            </Route>

            {/* Locations */}
            <Route path="/locations">
              <Route index element={<LocationIndex />} />
              <Route path="show/:id" element={<LocationDetail />} />
              <Route path="create" element={<LocationForm />} />
              <Route path="edit/:id" element={<LocationForm />} />
            </Route>

            {/* Countries + Provinces + Districts */}
            <Route path="/countries">
              <Route index element={<CountryIndex />} />
              <Route path="show/:id" element={<CountryDetail />} />
              <Route path="create" element={<CountryForm />} />
              <Route path="edit/:id" element={<CountryForm />} />
              <Route path=":countryId/provinces/:provinceId" element={<ProvinceDetail />} />
              <Route path=":countryId/provinces/:provinceId/districts/:districtId" element={<DistrictDetail />} />
            </Route>

            {/* Parishes */}
            <Route path="/parishes">
              <Route index element={<ParishIndex />} />
              <Route path="show/:id" element={<ParishDetail />} />
              <Route path="create" element={<ParishForm />} />
              <Route path="edit/:id" element={<ParishForm />} />
            </Route>

            {/* Cemeteries */}
            <Route path="/cemeteries">
              <Route index element={<CemeteryIndex />} />
              <Route path="show/:id" element={<CemeteryDetail />} />
              <Route path="create" element={<CemeteryForm />} />
              <Route path="edit/:id" element={<CemeteryForm />} />
            </Route>

            {/* Subdivision */}
            <Route path="/subdivisions/show/:id" element={<SubdivisionDetail />} />

            {/* ✅ Institutions */}
            <Route path="/institutions">
              <Route index element={<InstitutionIndex />} />
              <Route path="show/:id" element={<InstitutionDetail />} />

            </Route>

            {/* ✅ Occupations */}
            <Route path="/occupations">
              <Route index element={<OccupationIndex />} />
              <Route path="show/:id" element={<OccupationDetail />} />
            </Route>

                                {/* ✅ Sources */}
            <Route path="/sources">
              <Route index element={<SourceIndex />} />
              <Route path="show/:id" element={<SourceDetail />} />
            </Route>

          </Routes>
        </div>



        {/* Footer */}
        <footer className="bg-dark text-white text-center py-3 mt-auto">
          <div className="container">
            <p>&copy; 2025 Project RodRo. All Rights Reserved.</p>
            <p>
              <Link to="/privacy-policy" className="text-white">Privacy Policy</Link> |{' '}
              <Link to="/terms-of-service" className="text-white">Terms of Service</Link>
            </p>
          </div>
        </footer>
      </div>
    </Router>
  );
}

export default App;
