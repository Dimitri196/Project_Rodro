import React from "react";
import InputSelect from "../components/InputSelect";
import InputField from "../components/InputField";

const LocationFilter = (props) => {
    const handleChange = (e) => {
        props.handleChange(e);
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        props.handleSubmit(e);
    };

    const filter = props.filter;

    return (
        <form onSubmit={handleSubmit}>
            <div className="row">
                {/* City */}
                <div className="col">
                    <InputField
                        type="text"
                        name="city"
                        handleChange={handleChange}
                        label="City"
                        prompt="not specified"
                        value={filter.city || ""}
                    />
                </div>

                {/* Country */}
                <div className="col">
                    <InputSelect
                        name="country"
                        items={props.countryList}
                        handleChange={handleChange}
                        label="Country"
                        prompt="not selected"
                        value={filter.country || ""}
                    />
                </div>

                {/* Region */}
                <div className="col">
                    <InputSelect
                        name="region"
                        items={props.regionList}
                        handleChange={handleChange}
                        label="Region"
                        prompt="not selected"
                        value={filter.region || ""}
                    />
                </div>
            </div>

            <div className="row">
                {/* District */}
                <div className="col">
                    <InputSelect
                        name="district"
                        items={props.districtList}
                        handleChange={handleChange}
                        label="District"
                        prompt="not selected"
                        value={filter.district || ""}
                    />
                </div>

                {/* Limit */}
                <div className="col">
                    <InputField
                        type="number"
                        min="1"
                        name="limit"
                        handleChange={handleChange}
                        label="Limit on Location Count"
                        prompt="not specified"
                        value={filter.limit || ""}
                    />
                </div>
            </div>

            <div className="row">
                <div className="col">
                    <input
                        type="submit"
                        className="btn btn-secondary float-right mt-2"
                        value={props.confirm}
                    />
                </div>
            </div>
        </form>
    );
};

export default LocationFilter;
