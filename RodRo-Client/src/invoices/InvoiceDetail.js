import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { apiGet } from "../utils/api";
import dateStringFormatter from "../utils/dateStringFormatter";
import { priceFormatter } from "../utils/priceFormatter";

const InvoiceDetail = () => {
    const { id } = useParams();
    const [invoice, setInvoice] = useState({});
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchInvoice = async () => {
            try {
                const data = await apiGet(`/api/invoices/${id}`);
                setInvoice(data);
            } catch (err) {
                setError("Failed to fetch invoice details.");
            } finally {
                setLoading(false);
            }
        };

        fetchInvoice();
    }, [id]);

    if (loading) return <div>Loading...</div>;
    if (error) return <div>{error}</div>;

    return (
        <div>
            <h1>Detail faktury</h1>
            <hr />
            <h3>Faktura č. {invoice.invoiceNumber}</h3>
            <p><strong>Prodejce:</strong><br />{invoice.seller && invoice.seller.name}</p>
            <p><strong>Kupující:</strong><br />{invoice.buyer && invoice.buyer.name}</p>
            <p><strong>Datum vystavení:</strong><br />{dateStringFormatter(invoice.issued, true)}</p>
            <p><strong>Datum splatnosti:</strong><br />{dateStringFormatter(invoice.dueDate, true)}</p>
            <p><strong>Produkt:</strong><br />{invoice.product}</p>
            <p><strong>Cena:</strong><br />{invoice.price}Kč</p>
            <p><strong>DPH (%):</strong><br />{invoice.vat}%</p>
            <p><strong>Poznámka:</strong><br />{invoice.note}</p>
        </div>
    );
};

export default InvoiceDetail;
