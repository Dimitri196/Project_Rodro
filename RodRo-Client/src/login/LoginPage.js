import FlashMessage from "../components/FlashMessage";
import InputField from "../components/InputField";
import { useEffect, useState } from "react";
import { useSession } from "../contexts/session";
import { useNavigate } from "react-router-dom";
import { apiPost, HttpRequestError } from "../utils/api";

const LoginPage = () => {
  const [valuesState, setValuesState] = useState({ email: "", password: "" });
  const [errorMessageState, setErrorMessageState] = useState(null);
  const { session, setSession } = useSession();
  const navigate = useNavigate();

  useEffect(() => {
    if (session.data) {
      navigate("/");
    }
  }, [session]);

  const handleChange = (e) => {
    const fieldName = e.target.name;
    setValuesState({ ...valuesState, [fieldName]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    apiPost("/api/auth", valuesState)
      .then((data) => setSession({ data, status: "authenticated" }))
      .catch((e) => {
        if (e instanceof HttpRequestError) {
          e.response.text().then((message) => setErrorMessageState(message));
          return;
        }
        setErrorMessageState("An error occurred while communicating with the server.");
      });
  };

  return (
    <div className="d-flex justify-content-center align-items-center vh-100 bg-light">
      <div className="col-11 col-sm-8 col-md-6 col-lg-3">
        <div className="card shadow-lg rounded-4 border-0">
          <div className="card-body p-4">
            <h2 className="text-center text-primary mb-2">Login</h2>
            <p className="text-center text-muted mb-4">
              Sign in to your account to continue.
            </p>

            {errorMessageState && (
              <FlashMessage theme="danger" text={errorMessageState} />
            )}

            <form onSubmit={handleSubmit}>
              <InputField
                type="email"
                required={true}
                label="Email"
                handleChange={handleChange}
                value={valuesState.email}
                prompt="Enter your email"
                name="email"
              />
              <InputField
                type="password"
                required={true}
                label="Password"
                handleChange={handleChange}
                value={valuesState.password}
                prompt="Enter your password"
                name="password"
              />

              <button
                type="submit"
                className="btn btn-primary w-100 rounded-pill py-2 mt-3 shadow-sm"
              >
                Login
              </button>
            </form>

            <div className="text-center mt-3">
              <a href="/forgot-password" className="text-decoration-none text-primary">
                Forgot your password?
              </a>
            </div>

            <div className="text-center mt-2">
              <span className="text-muted">Don’t have an account? </span>
              <a
                href="http://localhost:3000/register"
                className="text-decoration-none fw-semibold text-primary"
              >
                Register here
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;
