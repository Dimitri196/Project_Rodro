import React from "react";
import { Navigate } from "react-router-dom";
import { useSession } from "../contexts/session";

const ProtectedRoute = ({ children }) => {
  const { session } = useSession();

  if (session.status === "loading") {
    return (
      <div className="d-flex justify-content-center align-items-center vh-100 bg-light">
        <div className="text-center">
          <div className="spinner-border text-primary mb-3" role="status" />
          <p>Checking authentication...</p>
        </div>
      </div>
    );
  }

  if (session.status !== "authenticated") {
    return <Navigate to="/login" replace />;
  }

  return children;
};

export default ProtectedRoute;
