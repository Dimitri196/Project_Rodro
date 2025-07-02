import React from 'react';
import { Link, useNavigate } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import { useSession } from "../src/contexts/session";

function HomePage() {
  const { session, setSession } = useSession();
  const isLoading = session.status === "loading";
  const isLoggedIn = session.status === "authenticated";
  const navigate = useNavigate();

  const handleLogout = () => {
    setSession({ data: null, status: "unauthenticated" });
    navigate("/");
  };

  if (isLoading) {
    return (
      <div className="d-flex justify-content-center align-items-center vh-100 bg-light">
        <div className="text-center">
          <div className="spinner-border text-primary mb-3" role="status" />
          <p>Loading session...</p>
        </div>
      </div>
    );
  }

  return (
    <div className="d-flex flex-column min-vh-100 bg-light">
      {/* Hero Banner */}
      <header className="bg-dark text-white py-5">
        <div className="container text-center">
          <h1 className="display-4 fw-bold">Welcome to Familiarum.eu</h1>
          <p className="lead mt-3">
            <strong>Familia Familiarum – The Family of Families</strong>
          </p>
          <p className="mt-4">
            More than genealogy – this is a scientific, open-access project that maps human destinies across space and time.
            Built with advanced technology and a collaborative spirit, Familiarum preserves family heritage,
            reveals interconnections, and supports historical and sociological research.
          </p>

          <div className="d-flex justify-content-center gap-3 mt-4">
            {!isLoggedIn ? (
              <>
                <Link to="/login" className="btn btn-outline-light">Login</Link>
                <Link to="/register" className="btn btn-primary">Register</Link>
              </>
            ) : (
              <button onClick={handleLogout} className="btn btn-outline-warning">
                Logout
              </button>
            )}
          </div>
        </div>
      </header>

      {/* Section Title */}
      <section className="container my-5">
        <div className="text-center mb-4">
          <h2 className="fw-semibold">Explore the Database</h2>
          <p className="text-muted">Manage people, places, families, and more using the tools below.</p>
        </div>

        {/* Cards Grid */}
        <div className="row g-4">
          {[
            { title: "Manage Persons", text: "Add, edit, and view details of people in your system.", path: "/persons" },
            { title: "Manage Locations", text: "Create, edit, and view your locations.", path: "/locations" },
            { title: "Manage Countries", text: "Create, edit, and view countries.", path: "/countries" },
            { title: "Manage Parishes", text: "Create, edit, and view parishes.", path: "/parishes" },
            { title: "Manage Families", text: "Create, edit, and view families.", path: "/families" },
            { title: "Manage Cemeteries", text: "Create, edit, and view cemeteries.", path: "/cemeteries" },
            { title: "Manage Institutions", text: "Create, edit, and view institutions.", path: "/institutions" },
            { title: "Manage Occupations", text: "Create, edit, and view occupations.", path: "/occupations" },
            { title: "Manage Sources", text: "Create, edit, and view sources.", path: "/sources" },
            { title: "Military Services", text: "Create, edit, and view military service records.", path: "/militaryOrganizations" },
            { title: "Military Structures", text: "Create, edit, and view military structures.", path: "/militaryStructures" },
            { title: "Military Ranks", text: "Create, edit, and view military ranks.", path: "/militaryRanks" },
          ].map(({ title, text, path }, i) => (
            <div className="col-md-4" key={i}>
              <div className="card h-100 shadow-sm border-0">
                <div className="card-body text-center d-flex flex-column">
                  <h5 className="card-title mb-3">{title}</h5>
                  <p className="card-text flex-grow-1">{text}</p>
                  <Link to={path} className="btn btn-outline-primary mt-3">Go to {title.split(" ")[1]}</Link>
                </div>
              </div>
            </div>
          ))}
        </div>
      </section>
    </div>
  );
}

export default HomePage;
