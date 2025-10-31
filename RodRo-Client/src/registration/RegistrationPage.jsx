import { apiPost, HttpRequestError } from "../utils/api";
import { useNavigate } from "react-router-dom";
import { useState } from "react";
import InputField from "../components/InputField";
import FlashMessage from "../components/FlashMessage";

const RegistrationPage = () => {
  const navigate = useNavigate();
  const [errorMessageState, setErrorMessageState] = useState(null);
  const [valuesState, setValuesState] = useState({
    fullName: "",
    password: "",
    confirmPassword: "",
    email: "",
  });

  const handleSubmit = (e) => {
    e.preventDefault();
    if (valuesState.password !== valuesState.confirmPassword) {
      setErrorMessageState("Passwords do not match.");
      return;
    }
    const { confirmPassword, ...registrationData } = valuesState;
    apiPost("/api/user", registrationData)
      .then(() => {
        navigate("/login");
      })
      .catch((e) => {
        if (e instanceof HttpRequestError && e.response.status === 400) {
          e.response.text().then((message) => setErrorMessageState(message));
          return;
        }
        setErrorMessageState(
          "An error occurred while communicating with the server."
        );
      });
  };

  const handleChange = (e) => {
    const fieldName = e.target.name;
    setValuesState({ ...valuesState, [fieldName]: e.target.value });
  };

  return (
    <div className="d-flex justify-content-center align-items-center vh-100 bg-light">
      <div className="col-11 col-sm-8 col-md-6 col-lg-3">
        <div className="card shadow-lg rounded-4 border-0">
          <div className="card-body p-4">
            <h2 className="text-center text-primary mb-2">Register</h2>
            <p className="text-center text-muted mb-4">
              Create your account to get started.
            </p>

            {errorMessageState && (
              <FlashMessage theme="danger" text={errorMessageState} />
            )}

            <form onSubmit={handleSubmit}>
              <InputField
                type="text"
                name="fullName"
                label="Full Name"
                prompt="Enter your full name"
                value={valuesState.fullName}
                handleChange={handleChange}
                required={true}
              />
              <InputField
                type="email"
                name="email"
                label="Email"
                prompt="Enter your email"
                value={valuesState.email}
                handleChange={handleChange}
                required={true}
              />
              <InputField
                type="password"
                name="password"
                label="Password"
                prompt="Enter your password"
                min={6}
                value={valuesState.password}
                handleChange={handleChange}
                required={true}
              />
              <InputField
                type="password"
                name="confirmPassword"
                label="Confirm Password"
                prompt="Re-enter your password"
                value={valuesState.confirmPassword}
                handleChange={handleChange}
                required={true}
              />

              <button
                type="submit"
                className="btn btn-primary w-100 rounded-pill py-2 mt-3 shadow-sm"
              >
                Register
              </button>
            </form>

            <div className="text-center mt-3">
              <span className="text-muted">Already have an account? </span>
              <a
                href="http://localhost:3000/login"
                className="text-decoration-none fw-semibold text-primary"
              >
                Login here
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default RegistrationPage;
