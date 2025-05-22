import React from "react";

const InputDate = ({ name, label, handleChange, value, required = false }) => {
  return (
    <div className="form-group">
      <label htmlFor={name}>{label}</label>
      <input
        type="date"
        id={name}
        name={name}
        className="form-control"
        value={value}
        onChange={handleChange}
        required={required}
      />
    </div>
  );
};

export default InputDate;
