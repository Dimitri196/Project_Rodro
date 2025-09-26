import React, { useState } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import "leaflet/dist/leaflet.css";
import {
  BrowserRouter as Router,
  Link,
  Route,
  Routes,
  Navigate,
} from "react-router-dom";
import {
  Navbar,
  Nav,
  Container,
  Spinner,
  NavDropdown,
  Image,
  Button,
} from "react-bootstrap";

import SearchResultsPage from "./components/SearchResultsPage";

// Import pages
import HomePage from "./HomePage";
import About from "./About";
import PrivacyPolicy from "./PrivacyPolicy";
import TermsOfService from "./TermsOfService";

// Articles
import ArticleIndex from "./articles/ArticleIndex";
import ArticleDetail from "./articles/ArticleDetail";
import ArticleForm from "./articles/ArticleForm";

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
import CemeteryIndex from "./cemeteries/CemeteryIndex";
import CemeteryDetail from "./cemeteries/CemeteryDetail";
import CemeteryForm from "./cemeteries/CemeteryForm";

// Subdivision
import SubdivisionDetail from "./countries/SubdivisionDetail";

// Institutions
import InstitutionIndex from "./institutions/InstitutionIndex";
import InstitutionDetail from "./institutions/InstitutionDetail";
import InstitutionForm from "./institutions/InstitutionForm";

// Occupations
import OccupationIndex from "./occupations/OccupationIndex";
import OccupationDetail from "./occupations/OccupationDetail";

// Sources
import SourceIndex from "./sources/SourceIndex";
import SourceDetail from "./sources/SourceDetail";

// Military
import MilitaryOrganizationIndex from "./military_service/MilitaryOrganizationIndex";
import MilitaryOrganizationDetail from "./military_service/MilitaryOrganizationDetail";
import MilitaryOrganizationForm from "./military_service/MilitaryOrganizationForm";

import MilitaryStructureIndex from "./military_service/MilitaryStructureIndex";
import MilitaryStructureDetail from "./military_service/MilitaryStructureDetail";

import MilitaryRankIndex from "./military_service/MilitaryRankIndex";
import MilitaryRankDetail from "./military_service/MilitaryRankDetail";
import MilitaryRankForm from "./military_service/MilitaryRankForm";

// Family Tree
import FamilyTreeComponent from "./components/FamilyTreeComponent";


import RegistrationPage from "./registration/RegistrationPage";
import LoginPage  from "./login/LoginPage";



import ContactMessage from "./contact_messages/ContactMessage";

import { useSession } from "./contexts/session";
import { apiDelete } from "./utils/api";

export function App() {
  const { session, setSession } = useSession();
  const [expanded, setExpanded] = useState(false);

  const handleLogoutClick = () => {
    apiDelete("/api/auth").finally(() =>
      setSession({ data: null, status: "unauthenticated" })
    );
    setExpanded(false);
  };

  return (
    <Router>
      <div className="d-flex flex-column min-vh-100">
        {/* Navbar */}
        <Navbar bg="dark" variant="dark" expand="lg" className="shadow-sm" expanded={expanded} onToggle={() => setExpanded(!expanded)}>
          <Container>
            <Navbar.Brand as={Link} to="/">
              <i className="fas fa-sitemap me-2"></i>Familiarum.eu
            </Navbar.Brand>
            <Navbar.Toggle aria-controls="basic-navbar-nav" />
            <Navbar.Collapse id="basic-navbar-nav">
              <Nav className="me-auto">
                <Nav.Link as={Link} to="/home" onClick={() => setExpanded(false)}>Home</Nav.Link>
                <Nav.Link as={Link} to="/about" onClick={() => setExpanded(false)}>About</Nav.Link>
                <Nav.Link as={Link} to="/articles" onClick={() => setExpanded(false)}>Blog</Nav.Link>
                <Nav.Link as={Link} to="/contact" onClick={() => setExpanded(false)}>Contact</Nav.Link>
              </Nav>

              {/* Auth Links */}
              <Nav className="gap-2 align-items-center">
                {session.status === "loading" ? (
                  <Spinner animation="border" size="sm" variant="light" />
                ) : session.data ? (
                  <NavDropdown
                    title={
                      <>
                        <Image
                          src={`https://ui-avatars.com/api/?name=${encodeURIComponent(
                            session.data.email
                          )}&background=0D8ABC&color=fff&rounded=true`}
                          roundedCircle
                          width={30}
                          height={30}
                          className="me-2"
                          alt="User Avatar"
                        />
                        {session.data.email}
                      </>
                    }
                    id="user-nav-dropdown"
                    align="end"
                    menuVariant="dark"
                  >
                    <NavDropdown.Item as={Link} to={`/profile/${session.data._id}`} onClick={() => setExpanded(false)}>Profile</NavDropdown.Item>
                    <NavDropdown.Divider />
                    <NavDropdown.Item onClick={handleLogoutClick}>Logout</NavDropdown.Item>
                  </NavDropdown>
                ) : (
                  <>
                    <Button as={Link} to="/login" variant="outline-light" className="me-2" onClick={() => setExpanded(false)}>
                      <i className="fas fa-sign-in-alt me-2"></i>Login
                    </Button>
                    <Button as={Link} to="/register" variant="primary" onClick={() => setExpanded(false)}>
                      <i className="fas fa-user-plus me-2"></i>Register
                    </Button>
                  </>
                )}
              </Nav>
            </Navbar.Collapse>
          </Container>
        </Navbar>

        {/* Routes */}
        <div className="flex-grow-1">
          <Routes>
            <Route path="/" element={<Navigate to="/home" />} />
            <Route path="/home" element={<HomePage />} />
            <Route path="/contact" element={<ContactMessage />} />
            <Route path="/search" element={<SearchResultsPage />} />

            <Route path="/articles" element={<ArticleIndex />} />
            <Route path="/articles/show/:id" element={<ArticleDetail />} />
            <Route path="/articles/create" element={<ArticleForm />} />
            <Route path="/articles/edit/:id" element={<ArticleForm />} />

            <Route path="/about" element={<About />} />
            <Route path="/privacy-policy" element={<PrivacyPolicy />} />
            <Route path="/terms-of-service" element={<TermsOfService />} />

            {/* Persons */}
            <Route path="/persons" element={<PersonIndex />} />
            <Route path="/persons/show/:id" element={<PersonDetail />} />
            <Route path="/persons/create" element={<PersonForm />} />
            <Route path="/persons/edit/:id" element={<PersonForm />} />

            {/* Families */}
            <Route path="/families" element={<FamilyIndex />} />
            <Route path="/families/show/:id" element={<FamilyDetail />} />
            <Route path="/families/create" element={<FamilyForm />} />
            <Route path="/families/edit/:id" element={<FamilyForm />} />

            {/* Locations */}
            <Route path="/locations" element={<LocationIndex />} />
            <Route path="/locations/show/:id" element={<LocationDetail />} />
            <Route path="/locations/create" element={<LocationForm />} />
            <Route path="/locations/edit/:id" element={<LocationForm />} />

            {/* Countries */}
            <Route path="/countries" element={<CountryIndex />} />
            <Route path="/countries/show/:id" element={<CountryDetail />} />
            <Route path="/countries/create" element={<CountryForm />} />
            <Route path="/countries/edit/:id" element={<CountryForm />} />
            <Route path="/countries/:countryId/provinces/:provinceId" element={<ProvinceDetail />} />
            <Route path="/countries/:countryId/provinces/:provinceId/districts/:districtId" element={<DistrictDetail />}
            />

            {/* Parishes */}
            <Route path="/parishes" element={<ParishIndex />} />
            <Route path="/parishes/show/:id" element={<ParishDetail />} />
            <Route path="/parishes/create" element={<ParishForm />} />
            <Route path="/parishes/edit/:id" element={<ParishForm />} />

            {/* Cemeteries */}
            <Route path="/cemeteries" element={<CemeteryIndex />} />
            <Route path="/cemeteries/show/:id" element={<CemeteryDetail />} />
            <Route path="/cemeteries/create" element={<CemeteryForm />} />
            <Route path="/cemeteries/edit/:id" element={<CemeteryForm />} />

            {/* Subdivisions */}
            <Route path="/subdivisions/show/:id" element={<SubdivisionDetail />}
            />

            {/* Institutions */}
            <Route path="/institutions" element={<InstitutionIndex />} />
            <Route path="/institutions/show/:id" element={<InstitutionDetail />} />
            <Route path="/institutions/create" element={<InstitutionForm />} />
            <Route path="/institutions/edit/:id" element={<InstitutionForm />} />

            {/* Occupations */}
            <Route path="/occupations" element={<OccupationIndex />} />
            <Route path="/occupations/show/:id" element={<OccupationDetail />}
            />

            {/* Sources */}
            <Route path="/sources" element={<SourceIndex />} />
            <Route path="/sources/show/:id" element={<SourceDetail />} />

            {/* Military */}
            <Route path="/militaryOrganizations" element={<MilitaryOrganizationIndex />} />
            <Route path="/militaryOrganizations/show/:id" element={<MilitaryOrganizationDetail />} />
            <Route path="/militaryOrganizations/create" element={<MilitaryOrganizationForm />} />
            <Route path="/militaryOrganizations/edit/:id" element={<MilitaryOrganizationForm />} />

            <Route path="/militaryStructures" element={<MilitaryStructureIndex />} />
            <Route path="/militaryStructures/show/:id" element={<MilitaryStructureDetail />} />

            <Route path="/militaryRanks" element={<MilitaryRankIndex />} />
            <Route path="/militaryRanks/show/:id" element={<MilitaryRankDetail />} />
            <Route path="/militaryRanks/create/" element={<MilitaryRankForm />} />
            <Route path="/militaryRanks/edit/:id" element={<MilitaryRankForm />} />

            {/* Family Tree */}
            <Route path="/family-tree/:id" element={<FamilyTreeComponent />} />

            {/* Auth */}
                    <Route path="/register" element={<RegistrationPage/>}/>
                    <Route path="/login" element={<LoginPage/>}/>

            {/* Fallback */}
            <Route path="*" element={<div>Page not found</div>} />
          </Routes>
        </div>

        {/* Footer */}
        <footer className="bg-dark text-white py-4 mt-auto">
          <Container className="text-center">
            <p className="mb-0">&copy; {new Date().getFullYear()} Familiarum.eu. All rights reserved.</p>
            <p className="mb-0 text-muted">A scientific, open-access genealogy project.</p>
            <p className="mb-0">
              <Link to="/privacy-policy" className="text-white text-decoration-underline me-2">
                Privacy Policy
              </Link>
              {" | "}
              <Link to="/terms-of-service" className="text-white text-decoration-underline ms-2">
                Terms of Service
              </Link>
            </p>
          </Container>
        </footer>
      </div>
    </Router>
  );
}

export default App;
