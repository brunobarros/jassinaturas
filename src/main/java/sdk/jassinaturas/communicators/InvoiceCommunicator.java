package sdk.jassinaturas.communicators;

import javax.inject.Named;

import feign.RequestLine;
import sdk.jassinaturas.clients.attributes.Invoice;
import sdk.jassinaturas.clients.attributes.VencimentoBoleto;

public interface InvoiceCommunicator {

    @RequestLine("GET /invoices/{id}/payments")
    Invoice payments(@Named("id") int id);

    @RequestLine("GET /invoices/{id}")
    Invoice show(@Named("id") int id);

    @RequestLine("POST /invoices/{id}/retry")
    Invoice retry(@Named("id") int id);
    
    @RequestLine("POST /invoices/{id}/boleto")
    Invoice newBoleto(@Named("id") int id, VencimentoBoleto vencimentoBoleto);
}
