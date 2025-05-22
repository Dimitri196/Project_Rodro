import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { apiGet, apiPost, apiPut } from "../utils/api";
import { Form, Button, Container, Row, Col, Alert } from "react-bootstrap"; // Import Bootstrap components

import InputField from "../components/InputField";
import InputSelect from "../components/InputSelect";
import FlashMessage from "../components/FlashMessage";

const InvoiceForm = () => {
    const navigate = useNavigate();
    const { id } = useParams();
    const [invoice, setInvoice] = useState({
        invoiceNumber: "",
        seller: { _id: "" },
        buyer: { _id: "" },
        issued: "",
        dueDate: "",
        product: "",
        price: "",
        vat: "",
        note: ""
    });
    const [sentState, setSent] = useState(false);
    const [successState, setSuccess] = useState(false);
    const [errorState, setError] = useState(null);
    const [persons, setPersons] = useState([]);

    useEffect(() => {
        const fetchPersons = async () => {
            try {
                const data = await apiGet("/api/persons");
                setPersons(data);
            } catch (error) {
                console.error("Failed to fetch persons:", error);
            }
        };

        fetchPersons();

        if (id) {
            apiGet("/api/invoices/" + id).then((data) => setInvoice(data));
        }
    }, [id]);

    const handleSubmit = async (e) => {
        e.preventDefault();

        if (invoice.vat < 0 || invoice.vat > 100) {
            setError("DPH musí být v rozmezí 0% až 100%.");
            return;
        }

        if (new Date(invoice.issued) > new Date(invoice.dueDate)) {
            setError("Datum vystavení musí být před datem splatnosti.");
            return;
        }

        try {
            const request = id 
                ? apiPut("/api/invoices/" + id, invoice) 
                : apiPost("/api/invoices", invoice);
            await request;

            setSent(true);
            setSuccess(true);
            setTimeout(() => {
                navigate("/invoices");
            }, 1000);
        } catch (error) {
            console.log(error.message);
            setError("Uložení faktury se nezdařilo: " + error.message);
            setSent(true);
            setSuccess(false);
        }
    };

    const sent = sentState;
    const success = successState;

    return (
        <Container className="mt-5">
            <h1 className="mb-4">{id ? "Upravit" : "Vytvořit"} fakturu</h1>
            
            {errorState && <Alert variant="danger">{errorState}</Alert>}

            {sent && (
                <FlashMessage
                    theme={success ? "success" : "danger"}
                    text={success ? "Uložení faktury proběhlo úspěšně." : "Chyba při ukládání faktury."}
                />
            )}

            <Form onSubmit={handleSubmit}>
                <Row className="mb-3">
                    <Col md={6}>
                        <InputField
                            required={true}
                            type="number"
                            name="invoiceNumber"
                            label="Číslo faktury"
                            prompt="Zadejte číslo faktury"
                            value={invoice.invoiceNumber}
                            handleChange={(e) => setInvoice({ ...invoice, invoiceNumber: e.target.value })}
                        />
                    </Col>
                </Row>

                <Row className="mb-3">
                    <Col md={6}>
                        <InputSelect
                            name="sellerId"
                            label="Prodejce"
                            prompt="Vyberte prodejce"
                            value={invoice.seller._id}
                            handleChange={(e) => {
                                const selectedSeller = e.target.value;
                                setInvoice({
                                    ...invoice,
                                    seller: { _id: selectedSeller },
                                    buyer: invoice.buyer._id === selectedSeller ? { _id: "" } : invoice.buyer
                                });
                            }}
                            items={persons}
                            disabledItems={invoice.buyer._id ? [invoice.buyer._id] : []}
                        />
                    </Col>
                    <Col md={6}>
                        <InputSelect
                            name="buyerId"
                            label="Kupující"
                            prompt="Vyberte kupujícího"
                            value={invoice.buyer._id}
                            handleChange={(e) => {
                                const selectedBuyer = e.target.value;
                                setInvoice({
                                    ...invoice,
                                    buyer: { _id: selectedBuyer },
                                    seller: invoice.seller._id === selectedBuyer ? { _id: "" } : invoice.seller
                                });
                            }}
                            items={persons}
                            disabledItems={invoice.seller._id ? [invoice.seller._id] : []}
                        />
                    </Col>
                </Row>

                <Row className="mb-3">
                    <Col md={6}>
                        <InputField
                            required={true}
                            type="date"
                            name="issued"
                            label="Datum vystavení"
                            prompt="Zadejte datum vystavení"
                            value={invoice.issued}
                            handleChange={(e) => setInvoice({ ...invoice, issued: e.target.value })}
                        />
                    </Col>
                    <Col md={6}>
                        <InputField
                            required={true}
                            type="date"
                            name="dueDate"
                            label="Datum splatnosti"
                            prompt="Zadejte datum splatnosti"
                            value={invoice.dueDate}
                            handleChange={(e) => setInvoice({ ...invoice, dueDate: e.target.value })}
                        />
                    </Col>
                </Row>

                <Row className="mb-3">
                    <Col md={6}>
                        <InputField
                            required={true}
                            type="text"
                            name="product"
                            label="Produkt"
                            prompt="Zadejte název produktu"
                            value={invoice.product}
                            handleChange={(e) => setInvoice({ ...invoice, product: e.target.value })}
                        />
                    </Col>
                    <Col md={6}>
                        <InputField
                            required={true}
                            type="number"
                            name="price"
                            label="Cena"
                            prompt="Zadejte cenu"
                            value={invoice.price}
                            handleChange={(e) => setInvoice({ ...invoice, price: e.target.value })}
                        />
                    </Col>
                </Row>

                <Row className="mb-3">
                    <Col md={6}>
                        <InputField
                            required={true}
                            type="number"
                            name="vat"
                            label="DPH (%)"
                            prompt="Zadejte sazbu DPH"
                            value={invoice.vat}
                            handleChange={(e) => setInvoice({ ...invoice, vat: e.target.value })}
                        />
                    </Col>
                </Row>

                <Row className="mb-3">
                    <Col md={12}>
                        <InputField
                            type="text"
                            name="note"
                            label="Poznámka"
                            prompt="Zadejte poznámku"
                            value={invoice.note}
                            handleChange={(e) => setInvoice({ ...invoice, note: e.target.value })}
                        />
                    </Col>
                </Row>

                <Button type="submit" variant="primary" className="mt-3">
                    Uložit
                </Button>
            </Form>
        </Container>
    );
};

export default InvoiceForm;
