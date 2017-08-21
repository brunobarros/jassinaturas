package sdk.jassinaturas.clients;

import java.util.List;

import sdk.jassinaturas.clients.attributes.Invoice;
import sdk.jassinaturas.clients.attributes.Payment;
import sdk.jassinaturas.clients.attributes.VencimentoBoleto;
import sdk.jassinaturas.communicators.InvoiceCommunicator;

public class InvoiceClient {

    private final InvoiceCommunicator invoiceCommunicator;

    public InvoiceClient(final InvoiceCommunicator invoiceCommunicator) {
        this.invoiceCommunicator = invoiceCommunicator;
    }

    public List<Payment> payments(final int id) {
        Invoice invoice = invoiceCommunicator.payments(id);
        return invoice.getPayments();
    }

    public Invoice show(final int id) {
        Invoice invoice = invoiceCommunicator.show(id);
        return invoice;
    }

    public Invoice retry(final int id) {
        Invoice invoice = invoiceCommunicator.retry(id);
        return invoice;
    }

    public Invoice newBoleto(final int id, VencimentoBoleto vencimentoBoleto) {
    	Invoice invoice = invoiceCommunicator.newBoleto(id, vencimentoBoleto);
    	return invoice;
    }
}
