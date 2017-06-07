package sdk.jassinaturas.clients.attributes;

import java.util.List;

public class Subscription {
    
	public static final String PAYMENT_METHOD_BOLETO = "BOLETO";
	public static final String PAYMENT_METHOD_CREDIT_CARD = "CREDIT_CARD";
	
    private List<Alerts> alerts;
    private int amount;
    private String code;
    private CreationDate creationDate;
    private Customer customer;
    private ExpirationDate expirationDate;
    private Invoice invoice;
    private List<Invoice> invoices;
    private String message;
    private NextInvoiceDate nextInvoiceDate;
    private Plan plan;
    private SubscriptionStatus status;
    private List<Subscription> subscriptions;
    private Coupon coupon;
	private String paymentMethod = PAYMENT_METHOD_CREDIT_CARD;

    public List<Alerts> getAlerts() {
        return alerts;
    }

    public int getAmount() {
        return amount;
    }

    public String getCode() {
        return code;
    }

    public CreationDate getCreationDate() {
        return creationDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public ExpirationDate getExpirationDate() {
        return expirationDate;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public String getMessage() {
        return message;
    }

    public NextInvoiceDate getNextInvoiceDate() {
        return nextInvoiceDate;
    }

    public Plan getPlan() {
        return plan;
    }

    public SubscriptionStatus getStatus() {
        return status;
    }

    public List<Subscription> getSubscriptions() {

        return this.subscriptions;
    }

    public Coupon getCoupon() {
        return coupon;
    }
    
    public String getPaymentMethod() {
		return paymentMethod;
	}

    public Subscription withAmount(final int amount) {
        this.amount = amount;
        return this;
    }

    public Subscription withCode(final String code) {
        this.code = code;
        return this;
    }

    public Subscription withCustomer(final Customer customer) {
        this.customer = customer;
        return this;
    }

    public Subscription withNextInvoiceDate(final NextInvoiceDate nextInvoiceDate) {
        this.nextInvoiceDate = nextInvoiceDate;
        return this;
    }

    public Subscription withPlan(final Plan plan) {
        this.plan = plan;
        return this;
    }

    public Subscription withCoupon(final Coupon coupon) {
        this.coupon = coupon;
        return this;
    }
    
    public Subscription withPaymentMethod(String paymentMethod) {
    	this.paymentMethod = paymentMethod;
    	return this;
	}

    @Override
    public String toString() {
        return "Subscription [amount=" + amount + ", code=" + code + ", creationDate=" + creationDate + ", customer="
                + customer + ", expirationDate=" + expirationDate + ", invoice=" + invoice + ", invoices=" + invoices
                + ", message=" + message + ", nextInvoiceDate=" + nextInvoiceDate + ", plan=" + plan + ", status="
                + status + ", subscriptions=" + subscriptions + ", coupon=" + coupon + "]";
    }

}
