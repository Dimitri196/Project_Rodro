import React from 'react';
import { Link } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';

function HomePage() {
  return (
    <div className="d-flex flex-column" style={{ minHeight: '50vh' }}>
      {/* Main Content */}
      <div className="container">
        {/* Jumbotron-like banner for a welcoming message */}
        <div className="jumbotron text-center mt-auto">
          <h1 className="display-4">Welcome to RodRo Web Service</h1>
          <p className="lead">Your one-stop solution for managing persons, locations, and families.</p>
          <hr className="my-4" />
          <p>Choose a section to get started:</p>
        </div>

        {/* Links Section */}
        <div className="row">
          {/* Persons Section */}
          <div className="col-md-4 mb-4">
            <div className="card text-center">
              <div className="card-body" style={{ height: '200px', overflow: 'hidden' }}>
                <h5 className="card-title">Manage Persons</h5>
                <p className="card-text" style={{ overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}>
                  Add, edit, and view details of people in your system.
                </p>
                <Link to="/persons" className="btn btn-primary">Go to Persons</Link>
              </div>
            </div>
          </div>

          {/* Location Section */}
          <div className="col-md-4 mb-4">
            <div className="card text-center">
              <div className="card-body" style={{ height: '200px', overflow: 'hidden' }}>
                <h5 className="card-title">Manage Locations</h5>
                <p className="card-text" style={{ overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}>
                  Create, edit, and view your locations in your system.
                </p>
                <Link to="/locations" className="btn btn-primary">Go to Locations</Link>
              </div>
            </div>
          </div>

          {/* Location Section */}
          <div className="col-md-4 mb-4">
            <div className="card text-center">
              <div className="card-body" style={{ height: '200px', overflow: 'hidden' }}>
                <h5 className="card-title">Manage Countries</h5>
                <p className="card-text" style={{ overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}>
                  Create, edit, and view your countries in your system.
                </p>
                <Link to="/countries" className="btn btn-primary">Go to Countries</Link>
              </div>
            </div>
          </div>

          {/* Parish Section */}
          <div className="col-md-4 mb-4">
            <div className="card text-center">
              <div className="card-body" style={{ height: '200px', overflow: 'hidden' }}>
                <h5 className="card-title">Manage Parishes</h5>
                <p className="card-text" style={{ overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}>
                  Create, edit, and view parishes in your system.
                </p>
                <Link to="/parishes" className="btn btn-primary">Go to Parishes</Link>
              </div>
            </div>
          </div>

          {/* Families Section */}
          <div className="col-md-4 mb-4">
            <div className="card text-center">
              <div className="card-body" style={{ height: '200px', overflow: 'hidden' }}>
                <h5 className="card-title">Manage Families</h5>
                <p className="card-text" style={{ overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}>
                  Create, edit, and view your families in your system.
                </p>
                <Link to="/families" className="btn btn-primary">Go to Families</Link>
              </div>
            </div>
          </div>

          {/* Cemeteries Section */}
          <div className="col-md-4 mb-4">
            <div className="card text-center">
              <div className="card-body" style={{ height: '200px', overflow: 'hidden' }}>
                <h5 className="card-title">Manage Cemeteries</h5>
                <p className="card-text" style={{ overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}>
                  Create, edit, and view your cemeteries in your system.
                </p>
                <Link to="/cemeteries" className="btn btn-primary">Go to Cemeteries</Link>
              </div>
            </div>
          </div>

          {/* Institutions Section */}
          <div className="col-md-4 mb-4">
            <div className="card text-center">
              <div className="card-body" style={{ height: '200px', overflow: 'hidden' }}>
                <h5 className="card-title">Manage Institutions</h5>
                <p className="card-text" style={{ overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}>
                  Create, edit, and view your institutions in your system.
                </p>
                <Link to="/institutions" className="btn btn-primary">Go to Institutions</Link>
              </div>
            </div>
          </div>

          {/* Occupations Section */}
          <div className="col-md-4 mb-4">
            <div className="card text-center">
              <div className="card-body" style={{ height: '200px', overflow: 'hidden' }}>
                <h5 className="card-title">Manage Occupations</h5>
                <p className="card-text" style={{ overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}>
                  Create, edit, and view your occupations in your system.
                </p>
                <Link to="/occupations" className="btn btn-primary">Go to Occupations</Link>
              </div>
            </div>
          </div>

          {/* Sources Section */}
          <div className="col-md-4 mb-4">
            <div className="card text-center">
              <div className="card-body" style={{ height: '200px', overflow: 'hidden' }}>
                <h5 className="card-title">Manage Sources</h5>
                <p className="card-text" style={{ overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}>
                  Create, edit, and view your source in your system.
                </p>
                <Link to="/sources" className="btn btn-primary">Go to Sources</Link>
              </div>
            </div>
          </div>

        </div>
      </div>
    </div>
  );
}

export default HomePage;
