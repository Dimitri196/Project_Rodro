import React from 'react';
import { Link, useNavigate } from "react-router-dom";
import { Container, Card, Row, Col, Spinner } from "react-bootstrap";
import { useSession } from "./contexts/session";

function HomePage() {
  const { session, setSession } = useSession();
  const isLoading = session.status === "loading";
  const isLoggedIn = session.status === "authenticated";
  const navigate = useNavigate();

  const handleLogout = () => {
    setSession({ data: null, status: "unauthenticated" });
    navigate("/");
  };

  const cardsData = [
    { title: "Persons", text: "Explore people in database.", path: "/persons", icon: "fas fa-user-circle" },
    { title: "Locations", text: "Create, edit, and view your locations.", path: "/locations", icon: "fas fa-map-marker-alt" },
    { title: "Countries", text: "Create, edit, and view countries.", path: "/countries", icon: "fas fa-globe-americas" },
    { title: "Parishes", text: "Create, edit, and view parishes.", path: "/parishes", icon: "fas fa-church" },
    { title: "Families", text: "Create, edit, and view families.", path: "/families", icon: "fas fa-users" },
    { title: "Cemeteries", text: "Create, edit, and view cemeteries.", path: "/cemeteries", icon: "fas fa-cross" },
    { title: "Institutions", text: "Create, edit, and view institutions.", path: "/institutions", icon: "fas fa-building" },
    { title: "Occupations", text: "Create, edit, and view occupations.", path: "/occupations", icon: "fas fa-briefcase" },
    { title: "Sources", text: "Create, edit, and view sources.", path: "/sources", icon: "fas fa-book" },
    { title: "Military Services", text: "Create, edit, and view military service records.", path: "/militaryOrganizations", icon: "fas fa-medal" },
    { title: "Military Structures", text: "Create, edit, and view military structures.", path: "/militaryStructures", icon: "fas fa-sitemap" },
    { title: "Military Ranks", text: "Create, edit, and view military ranks.", path: "/militaryRanks", icon: "fas fa-star" },
  ];

  const handleSearch = (event) => {
    event.preventDefault();
    const query = event.target.elements.search.value;
    if (query) {
      navigate(`/search?q=${query}`);
    }
  };

  if (isLoading) {
    return (
      <div className="d-flex justify-content-center align-items-center vh-100 bg-light">
        <div className="text-center">
          <Spinner animation="border" variant="primary" className="mb-3" />
          <p className="text-muted">Loading session...</p>
        </div>
      </div>
    );
  }

  return (
    <div className="d-flex flex-column min-vh-100 bg-light">
      <style>
        {`
        .card-hover-effect {
          transition: transform 0.3s ease-in-out, box-shadow 0.3s ease-in-out;
          border: 1px solid #dee2e6;
          background-color: #fff;
          border-radius: 1rem;
        }

        .card-hover-effect:hover {
          transform: translateY(-6px);
          box-shadow: 0 0.75rem 1.5rem rgba(0, 0, 0, 0.1);
          border-color: #0d6efd;
        }

        .card-body-equal {
          display: flex;
          flex-direction: column;
          justify-content: space-between;
          padding: 1.5rem;
          text-align: center;
        }

        .icon-wrapper {
          width: 64px;
          height: 64px;
          margin: 0 auto 1rem;
          display: flex;
          align-items: center;
          justify-content: center;
          background-color: #f8f9fa;
          border: 1px solid #dee2e6;
          border-radius: 50%;
          box-shadow: 0 0.25rem 0.5rem rgba(0, 0, 0, 0.05);
        }

        .card-title {
          font-weight: 600;
          font-size: 1.15rem;
          color: #343a40;
        }

        .card-text {
          color: #6c757d;
          font-size: 0.95rem;
          flex-grow: 1;
          display: flex;
          align-items: center;
          justify-content: center;
        }

        .card-body-equal .btn {
          margin-top: 1rem;
        }
        `}
      </style>

      {/* Hero */}
      <header className="bg-dark text-white py-5 text-center position-relative overflow-hidden"
        style={{
          backgroundImage: 'url("https://placehold.co/1920x400/343a40/ffffff?text=Familial+Connections")',
          backgroundSize: 'cover',
          backgroundPosition: 'center',
          minHeight: '400px',
          display: 'flex',
          alignItems: 'center',
          justifyContent: 'center'
        }}
      >
        <div style={{
          position: 'absolute',
          top: 0,
          left: 0,
          width: '100%',
          height: '100%',
          backgroundColor: 'rgba(0,0,0,0.6)'
        }}></div>

        <Container className="position-relative" style={{ zIndex: 1 }}>
          <h1 className="display-3 fw-bold mb-3">Welcome to Familiarum.eu</h1>
          <p className="lead mt-3 fs-5"><strong>Familia Familiarum – The Family of Families</strong></p>
          <p className="mt-4 mx-auto" style={{ maxWidth: '800px', fontSize: '1.1rem' }}>
            More than genealogy – this is a scientific, open-access project that maps human destinies across space and time.
            Built with advanced technology and a collaborative spirit, Familiarum preserves family heritage,
            reveals interconnections, and supports historical and sociological research.
          </p>

          <div className="d-flex justify-content-center gap-3 mt-5">
            {!isLoggedIn ? (
              <>
                <Link to="/login" className="btn btn-lg btn-outline-light border-2 px-4 py-2 shadow-sm">
                  <i className="fas fa-sign-in-alt me-2"></i>Login
                </Link>
                <Link to="/register" className="btn btn-lg btn-primary border-2 px-4 py-2 shadow-sm">
                  <i className="fas fa-user-plus me-2"></i>Register
                </Link>
              </>
            ) : (
              <p className="lead fs-5 text-white">You are logged in! Explore the database using the navigation above.</p>
            )}
          </div>
        </Container>
      </header>

      {/* Cards Section */}
      <section className="container my-5 py-4">
        <div className="text-center mb-5">
          <h2 className="fw-semibold text-primary display-5">Explore the Database</h2>
          <p className="text-muted fs-5">
            Dive into the rich history and connections. Manage people, places, families, and more using the tools below.
          </p>
        </div>

        <Row xs={1} sm={2} md={3} xl={4} className="g-4">
          {cardsData.map(({ title, text, path, icon }, i) => (
            <Col key={i} className="d-flex">
              <Card className="h-100 w-100 card-hover-effect shadow-sm">
                <Card.Body className="card-body-equal">
                  <div className="icon-wrapper">
                    <i className={`${icon} fa-2x text-primary`}></i>
                  </div>
                  <Card.Title className="card-title">{title}</Card.Title>
                  <Card.Text className="card-text">{text}</Card.Text>
                  <Link to={path} className="btn btn-outline-primary rounded-pill px-4 py-2 shadow-sm">
                    Go to {title}
                  </Link>
                </Card.Body>
              </Card>
            </Col>
          ))}
        </Row>
      </section>
    </div>
  );
}

export default HomePage;
