import React from "react";
import InputSelect from "../components/InputSelect";
import InputField from "../components/InputField";

const InvoiceFilter = (props) => {
  const handleChange = (e) => {
    props.handleChange(e);
  };

  const handleSubmit = (e) => {
    e.preventDefault(); // This prevents the form from submitting the default way
    props.handleSubmit(e); // Pass the 'e' object to props.handleSubmit
  };

  const filter = props.filter;

  return (
    <form onSubmit={handleSubmit}>
      <div className="row">
        <div className="col">
          <InputSelect
            name="buyerID"
            items={props.personList}
            handleChange={handleChange}
            label="Kupující"
            prompt="nevybrán"
            value={filter.buyerID}
          />
        </div>

        <div className="col">
          <InputSelect
            name="sellerID"
            items={props.personList}
            handleChange={handleChange}
            label="Prodávající"
            prompt="nevybrán"
            value={filter.sellerID}
          />
        </div>
      </div>

      <div className="row">
        <div className="col">
          <InputField
            type="number"
            min="0"
            name="minPrice"
            handleChange={handleChange}
            label="Minimální cena"
            prompt="neuvedeno"
            value={filter.minPrice || ""}
          />
        </div>

        <div className="col">
          <InputField
            type="number"
            min="0"
            name="maxPrice"
            handleChange={handleChange}
            label="Maximální cena"
            prompt="neuvedeno"
            value={filter.maxPrice || ""}
          />
        </div>

        <div className="col">
          <InputField
            type="text"
            name="product"
            handleChange={handleChange}
            label="Produkt"
            prompt="neuvedeno"
            value={filter.product || ""}
          />
        </div>

        <div className="col">
          <InputField
            type="number"
            min="1"
            name="limit"
            handleChange={handleChange}
            label="Limit počtu faktur"
            prompt="neuveden"
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

export default InvoiceFilter;
