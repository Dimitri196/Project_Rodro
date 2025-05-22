import React from "react";
import InputSelect from "../components/InputSelect";
import InputField from "../components/InputField";

const FamilyFilter = (props) => {
    const handleChange = (e) => {
        props.handleChange(e);
    };

    const handleSubmit = (e) => {
        e.preventDefault(); // Prevent default form submission
        props.handleSubmit(e); // Pass the 'e' object to props.handleSubmit
    };

    const filter = props.filter;

    return (
        <form onSubmit={handleSubmit}>
            <div className="row">
                {/* Marriage Location */}
                <div className="col">
                    <InputSelect
                        name="marriageLocationID"
                        items={props.locationList}
                        handleChange={handleChange}
                        label="Marriage Location"
                        prompt="not selected"
                        value={filter.marriageLocationID}
                        getLabel={(item) => item.locationName}
                        getValue={(item) => item._id}
                    />
                </div>

                {/* Male Spouse */}
                <div className="col">
                    <InputSelect
                        name="spouseMaleID"
                        items={props.personList}
                        handleChange={handleChange}
                        label="Groom"
                        prompt="not selected"
                        value={filter.spouseMaleID}
                        getLabel={(p) => p ? `${p.givenName || ""} ${p.givenSurname || ""}`.trim() : ""}
                            getValue={(p) => p?._id}
                    />
                </div>

                {/* Female Spouse */}
                <div className="col">
                    <InputSelect
                        name="spouseFemaleID"
                        items={props.personList}
                        handleChange={handleChange}
                        label="Bride"
                        prompt="not selected"
                        value={filter.spouseFemaleID}
                        getLabel={(p) => p ? `${p.givenName || ""} ${p.givenSurname || ""}`.trim() : ""}
                            getValue={(p) => p?._id}
                    />
                </div>
            </div>

            <div className="row">
                {/* Marriage Date */}
                <div className="col">
                    <div className="form-group">
                        <label htmlFor="marriageDate">Marriage Date</label>
                        <input
                            type="date"
                            id="marriageDate"
                            name="marriageDate"
                            className="form-control"
                            value={filter.marriageDate || ""}
                            onChange={handleChange}
                        />
                    </div>
                </div>

                {/* Notes */}
                <div className="col">
                    <InputField
                        type="text"
                        name="note"
                        handleChange={handleChange}
                        label="Note"
                        prompt="not specified"
                        value={filter.note || ""}
                    />
                </div>

                {/* Limit */}
                <div className="col">
                    <InputField
                        type="number"
                        min="1"
                        name="limit"
                        handleChange={handleChange}
                        label="Limit on Family Count"
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

export default FamilyFilter;
