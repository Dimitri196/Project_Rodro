import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { apiPost, HttpRequestError } from "../utils/api";
import InputField from "../components/InputField";
import FlashMessage from "../components/FlashMessage";

const RegistrationPage = () => {
  const navigate = useNavigate();
  const [values, setValues] = useState({
    email: "",
    password: "",
    confirmPassword: "",
  });
  const [errorMessage, setErrorMessage] = useState(null);
  const [loading, setLoading] = useState(false);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setValues((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setErrorMessage(null);

    if (values.password !== values.confirmPassword) {
      setErrorMessage("Passwords do not match.");
      return;
    }

    setLoading(true);
    try {
      const { confirmPassword, ...registrationData } = values;
      await apiPost("/api/user", registrationData);
      navigate("/login");
    } catch (error) {
      if (error instanceof HttpRequestError && error.response.status === 400) {
        const msg = await error.response.text();
        setErrorMessage(msg);
      } else {
        setErrorMessage("An error occurred while communicating with the server.");
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="offset-4 col-sm-6 mt-5">
      <h1>Registration</h1>
      <form onSubmit={handleSubmit} noValidate>
        {errorMessage && <FlashMessage theme="danger" text={errorMessage} />}

        <InputField
          type="email"
          name="email"
          label="Email"
          prompt="Enter your email"
          value={values.email}
          required={true}
          handleChange={handleChange}
          disabled={loading}
        />

        <InputField
          type="password"
          name="password"
          label="Password"
          prompt="Enter your password"
          min={6}
          value={values.password}
          required={true}
          handleChange={handleChange}
          disabled={loading}
        />

        <InputField
          type="password"
          name="confirmPassword"
          label="Confirm Password"
          prompt="Enter your password again"
          value={values.confirmPassword}
          required={true}
          handleChange={handleChange}
          disabled={loading}
        />

        <button type="submit" className="btn btn-primary mt-2" disabled={loading}>
          {loading ? "Registering..." : "Register"}
        </button>
      </form>
    </div>
  );
};

export default RegistrationPage;
