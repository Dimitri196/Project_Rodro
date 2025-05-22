import React from "react";

export function InputSelect({
    label,
    name,
    value,
    items = [],
    prompt = "Select...",
    required = false,
    multiple = false,
    handleChange,
    getLabel = (item) => item.name,
    getValue = (item) => item._id,
    enum: enumObj,
    disabledItems = []
}) {
    const isEnum = !!enumObj;

    return (
        <div className="form-group">
            <label>{label}:</label>
            <select
                required={required}
                className="browser-default form-select"
                multiple={multiple}
                name={name}
                onChange={handleChange}
                value={value ?? (multiple ? [] : "")}
            >
                <option value="" disabled={required}>
                    {prompt}
                </option>
                {items.length > 0 ? (
                    isEnum ? (
                        items.map((item, idx) => (
                            <option key={idx} value={item}>
                                {enumObj[item]}
                            </option>
                        ))
                    ) : (
                        items.map((item, idx) => (
                            <option
                                key={idx}
                                value={getValue(item)}
                                disabled={disabledItems.includes(getValue(item))}
                            >
                                {getLabel(item)}
                            </option>
                        ))
                    )
                ) : (
                    <option disabled>No options available</option>
                )}
            </select>
        </div>
    );
}

export default InputSelect;