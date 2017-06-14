package sdk.jassinaturas.clients;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;

import co.freeside.betamax.Betamax;
import co.freeside.betamax.MatchRule;
import co.freeside.betamax.Recorder;
import sdk.jassinaturas.Assinaturas;
import sdk.jassinaturas.clients.attributes.Address;
import sdk.jassinaturas.clients.attributes.Authentication;
import sdk.jassinaturas.clients.attributes.BillingInfo;
import sdk.jassinaturas.clients.attributes.Birthdate;
import sdk.jassinaturas.clients.attributes.Country;
import sdk.jassinaturas.clients.attributes.Coupon;
import sdk.jassinaturas.clients.attributes.CreditCard;
import sdk.jassinaturas.clients.attributes.Customer;
import sdk.jassinaturas.clients.attributes.Invoice;
import sdk.jassinaturas.clients.attributes.Month;
import sdk.jassinaturas.clients.attributes.NextInvoiceDate;
import sdk.jassinaturas.clients.attributes.Plan;
import sdk.jassinaturas.clients.attributes.State;
import sdk.jassinaturas.clients.attributes.Subscription;
import sdk.jassinaturas.clients.attributes.SubscriptionStatus;
import sdk.jassinaturas.communicators.SandboxCommunicator;
import sdk.jassinaturas.exceptions.ApiResponseErrorException;

public class SubscriptionClientTest {

    private final Assinaturas assinaturas = new Assinaturas(new Authentication("SGPA0K0R7O0IVLRPOVLJDKAWYBO1DZF3",
            "QUJESGM9JU175OGXRFRJIYM0SIFOMIFUYCBWH9WA"), new SandboxCommunicator());

    @Rule
    public Recorder recorder = new Recorder();

    @Betamax(tape = "ACTIVATE_SUBSCRIPTION", match = { MatchRule.method, MatchRule.uri })
    @Test
    public void shouldActivateASubscription() {

        Subscription subscription = assinaturas.subscriptions().activate("assinatura06");

        // There isn't any response from Moip Assinaturas when subscription is
        // activated
        // So, I didn't do any assert

    }

    @Betamax(tape = "CANCEL_SUBSCRIPTION", match = { MatchRule.method, MatchRule.uri })
    @Test
    public void shouldCancelASubscription() {

        Subscription subscription = assinaturas.subscriptions().cancel("assinatura13");

        // There isn't any response from Moip Assinaturas when subscription is
        // canceled
        // So, I didn't do any assert

    }

    @Betamax(tape = "CREATE_SUBSCRIPTION",
            match = { MatchRule.body, MatchRule.method, MatchRule.uri })
    @Test
    public void shouldCreateANewSubscription() {
        Subscription toBeCreated = new Subscription();
        toBeCreated
                .withCode("subscription_with_new_customer_00001")
                .withAmount(100)
                .withPlan(new Plan().withCode("plan001"))
                .withCustomer(
                        new Customer()
                                .withCode("customer_created_with_subscription_0001")
                                .withBirthdate(new Birthdate().withDay(13).withMonth(Month.OCTOBER).withYear(1989))
                                .withCpf("12312312312")
                                .withEmail("teste@teste.com")
                                .withFullname("Danillo Souza")
                                .withPhoneAreaCode("11")
                                .withPhoneNumber("912341234")
                                .withAddress(
                                        new Address().withCity("São Paulo").withComplement("Apto")
                                                .withCountry(Country.BRA).withDistrict("Centro").withNumber("1000")
                                                .withState(State.SP).withStreet("9 de Julho").withZipcode("10012345"))
                                .withBillingInfo(
                                        new BillingInfo().withCreditCard(new CreditCard().withExpirationMonth("10")
                                                .withExpirationYear("18").withHolderName("Danillo Souza")
                                                .withNumber("4111111111111111"))));

        Subscription created = assinaturas.subscriptions().create(toBeCreated);

        assertEquals("Assinatura criada com sucesso", created.getMessage());

        assertEquals(created.getAmount(), 100);
        assertEquals(created.getPlan().getName(), "Plano de Teste Atualizado");
        assertEquals(created.getPlan().getCode(), "plan001");
        assertEquals(created.getStatus(), SubscriptionStatus.ACTIVE);
        assertEquals(created.getInvoice().getAmount(), 1100);
        assertEquals(created.getInvoice().getId(), 12872);
        assertEquals(created.getInvoice().getStatus().getDescription(), "Atrasada");
        assertEquals(created.getInvoice().getStatus().getCode(), 5);
        assertEquals(1, created.getNextInvoiceDate().getDay());
        assertEquals(Month.MAY, created.getNextInvoiceDate().getMonth());
        assertEquals(2014, created.getNextInvoiceDate().getYear());
        assertEquals(created.getCode(), "subscription_with_new_customer_00001");
        assertEquals(created.getCustomer().getEmail(), "teste@teste.com");
        assertEquals(created.getCustomer().getCode(), "customer_created_with_subscription_0001");
        assertEquals(created.getCustomer().getFullname(), "Danillo Souza");
    }

    @Betamax(tape = "CREATE_SUBSCRIPTION_WITHOUT_CUSTOMER", match = { MatchRule.body, MatchRule.method,
            MatchRule.uri })
    @Test
    public void shouldCreateANewSubscriptionWithoutANewCustomer() {
        Subscription toBeCreated = new Subscription();
        toBeCreated.withCode("subscription00001").withAmount(100)
                .withCustomer(new Customer().withCode("customer000000001")).withPlan(new Plan().withCode("plan001"));

        Subscription created = assinaturas.subscriptions().create(toBeCreated);

        assertEquals("Assinatura criada com sucesso", created.getMessage());

        assertEquals(created.getAmount(), 100);
        assertEquals(created.getPlan().getName(), "Plano de Teste Atualizado");
        assertEquals(created.getPlan().getCode(), "plan001");
        assertEquals(created.getStatus(), SubscriptionStatus.ACTIVE);
        assertEquals(created.getInvoice().getAmount(), 1100);
        assertEquals(created.getInvoice().getId(), 12873);
        assertEquals(created.getInvoice().getStatus().getDescription(), "Atrasada");
        assertEquals(created.getInvoice().getStatus().getCode(), 5);
        assertEquals(1, created.getNextInvoiceDate().getDay());
        assertEquals(Month.MAY, created.getNextInvoiceDate().getMonth());
        assertEquals(2014, created.getNextInvoiceDate().getYear());
        assertEquals(created.getCode(), "subscription00001");
        assertEquals(created.getCustomer().getEmail(), "teste@teste.com");
        assertEquals(created.getCustomer().getCode(), "customer000000001");
        assertEquals(created.getCustomer().getFullname(), "Danillo Souza");
    }

    @Betamax(tape = "LIST_ALL_SUBSCRIPTIONS", match = { MatchRule.method, MatchRule.uri })
    @Test
    public void shouldListAllSubscriptions() {

        Assinaturas assinaturas = new Assinaturas(new Authentication("SGPA0K0R7O0IVLRPOVLJDKAWYBO1DZF3",
                "QUJESGM9JU175OGXRFRJIYM0SIFOMIFUYCBWH9WA"), new SandboxCommunicator());

        List<Subscription> subscriptions = assinaturas.subscriptions().list();
        assertEquals(60, subscriptions.size());
    }

    @Betamax(tape = "CREATE_SUBSCRIPTION_RETURNED_ERROR")
    @Test
    public void shouldReturnErrors() {
        Subscription toBeCreated = new Subscription();
        toBeCreated.withCode("subscription00001").withAmount(100)
                .withCustomer(new Customer().withCode("customer000000001")).withPlan(new Plan().withCode("plan001"));

        try {
            Subscription created = assinaturas.subscriptions().create(toBeCreated);
            fail("Should return ApiResponseError");
        } catch (ApiResponseErrorException e) {
            assertEquals("Erro na requisição", e.getApiResponseError().getMessage());
            assertEquals("Código da assinatura já utilizado. Escolha outro código", e.getApiResponseError().getErrors()
                    .get(0).getDescription());
            assertEquals("MA92", e.getApiResponseError().getErrors().get(0).getCode());
        }
    }

    @Betamax(tape = "GET_INVOICES_FROM_SUBSCRIPTION", match = { MatchRule.method, MatchRule.uri })
    @Test
    public void shouldReturnInvoicesFromSubscription() {
        List<Invoice> invoices = assinaturas.subscriptions().invoices("subscription00001");
        Invoice invoice = invoices.get(0);

        assertEquals(0, invoice.getCreationDate().getMinute());
        assertEquals(40, invoice.getCreationDate().getSecond());
        assertEquals(Month.JANUARY, invoice.getCreationDate().getMonth());
        assertEquals(2014, invoice.getCreationDate().getYear());
        assertEquals(23, invoice.getCreationDate().getHour());
        assertEquals(21, invoice.getCreationDate().getDay());

        assertEquals(1100, invoice.getAmount());
        assertEquals("subscription00001", invoice.getSubscriptionCode());
        assertEquals(1, invoice.getOccurrence());
        assertEquals(12873, invoice.getId());

        assertEquals("Atrasada", invoice.getStatus().getDescription());
        assertEquals(5, invoice.getStatus().getCode());

    }

    @Betamax(tape = "GET_SINGLE_SUBSCRIPTION", match = { MatchRule.method, MatchRule.uri })
    @Test
    public void shouldShowASubscription() {

        Assinaturas assinaturas = new Assinaturas(new Authentication("SGPA0K0R7O0IVLRPOVLJDKAWYBO1DZF3",
                "QUJESGM9JU175OGXRFRJIYM0SIFOMIFUYCBWH9WA"), new SandboxCommunicator());

        Subscription subscription = assinaturas.subscriptions().show("subscription00001");

        assertEquals(0, subscription.getCreationDate().getMinute());
        assertEquals(40, subscription.getCreationDate().getSecond());
        assertEquals(Month.JANUARY, subscription.getCreationDate().getMonth());
        assertEquals(2014, subscription.getCreationDate().getYear());
        assertEquals(23, subscription.getCreationDate().getHour());
        assertEquals(21, subscription.getCreationDate().getDay());

        assertEquals(100, subscription.getAmount());
        assertEquals(SubscriptionStatus.OVERDUE, subscription.getStatus());
        assertEquals("subscription00001", subscription.getCode());

        assertEquals("teste@teste.com", subscription.getCustomer().getEmail());
        assertEquals("customer000000001", subscription.getCustomer().getCode());
        assertEquals("Danillo Souza", subscription.getCustomer().getFullname());

        assertEquals("Plano de Teste Atualizado", subscription.getPlan().getName());
        assertEquals("plan001", subscription.getPlan().getCode());

        assertEquals(Month.OCTOBER, subscription.getExpirationDate().getMonth());
        assertEquals(2016, subscription.getExpirationDate().getYear());
        assertEquals(17, subscription.getExpirationDate().getDay());

        assertEquals(Month.MAY, subscription.getNextInvoiceDate().getMonth());
        assertEquals(2014, subscription.getNextInvoiceDate().getYear());
        assertEquals(1, subscription.getNextInvoiceDate().getDay());
    }

    @Betamax(tape = "SUSPEND_SUBSCRIPTION", match = { MatchRule.method, MatchRule.uri })
    @Test
    public void shouldSuspendASubscription() {

        Subscription subscription = assinaturas.subscriptions().suspend("assinatura07");

        // There isn't any response from Moip Assinaturas when subscription is
        // suspended
        // So, I didn't do any assert

    }

    @Betamax(tape = "UPDATE_SUBSCRIPTION", match = { MatchRule.method, MatchRule.uri })
    @Test
    public void shouldUpdateASubscription() {

        Subscription toUpdate = new Subscription();
        toUpdate.withCode("assinatura46").withPlan(new Plan().withCode("plano01")).withAmount(990)
                .withNextInvoiceDate(new NextInvoiceDate().withDay(20).withMonth(Month.OCTOBER).withYear(2015));

        Subscription subscription = assinaturas.subscriptions().update(toUpdate);

        // There isn't any response from Moip Assinaturas when subscription is
        // updated
        // So, I didn't do any assert

    }

    @Betamax(tape = "GET_SINGLE_SUBSCRIPTION", match = { MatchRule.method, MatchRule.uri })
    @Test
    public void shouldGetResultFromToString() {
        String subscription = assinaturas.subscriptions().show("subscription00001").toString();

        assertEquals(
                "Subscription [amount=100, code=subscription00001, creationDate=CreationDate [day=21, hour=23, minute=0, month=1, second=40, year=2014], customer=Customer [address=null, billingInfo=null, birthdate=null, code=customer000000001, cpf=null, customers=null, email=teste@teste.com, fullname=Danillo Souza, message=null, phoneAreaCode=null, phoneNumber=null, birthdateDay=0, birthdateMonth=0, birthdateYear=0], expirationDate=ExpirationDate [day=17, month=OCTOBER, year=2016], invoice=null, invoices=null, message=null, nextInvoiceDate=NextInvoiceDate [day=1, month=5, year=2014], plan=Plan [alerts=null, amount=0, billingCycles=0, code=plan001, description=null, interval=null, maxQty=0, message=null, name=Plano de Teste Atualizado, plans=null, setupFee=0, status=null, trial=null], status=OVERDUE, subscriptions=null, coupon=null]",
                subscription);
    }

    @Betamax(tape = "UPDATE_SUBSCRIPTION_ADDING_COUPON", match = { MatchRule.method, MatchRule.uri })
    @Test
    public void addingCouponToASubscription() {
        Subscription toUpdate = new Subscription();
        toUpdate.withCode("assinatura46").withPlan(new Plan().withCode("plano01")).withAmount(990)
                .withNextInvoiceDate(new NextInvoiceDate().withDay(20).withMonth(Month.OCTOBER).withYear(2015))
                .withCoupon(new Coupon().withCode("jassinaturas_coupon_01"));

        Subscription subscription = assinaturas.subscriptions().update(toUpdate);

        // There isn't any response from Moip Assinaturas when subscription is
        // updated
        // So, I didn't do any assert
    }
    
    @Betamax(tape = "CREATE_SUBSCRIPTION_BOLETO",
            match = { MatchRule.body, MatchRule.method, MatchRule.uri })
    @Test
    public void shouldCreateANewSubscriptionWithPaymentMethodBoleto() {
        Subscription toBeCreated = new Subscription();
        toBeCreated
                .withCode("subscription_with_new_customer_00001")
                .withAmount(100)
                .withPlan(new Plan().withCode("plan001"))
                .withCustomer(
                        new Customer()
                                .withCode("customer_created_with_subscription_0001")
                                .withBirthdate(new Birthdate().withDay(13).withMonth(Month.OCTOBER).withYear(1989))
                                .withCpf("12312312312")
                                .withEmail("teste@teste.com")
                                .withFullname("Danillo Souza")
                                .withPhoneAreaCode("11")
                                .withPhoneNumber("912341234")
                                .withAddress(
                                        new Address().withCity("S�o Paulo").withComplement("Apto")
                                                .withCountry(Country.BRA).withDistrict("Centro").withNumber("1000")
                                                .withState(State.SP).withStreet("9 de Julho").withZipcode("10012345")))
                .withPaymentMethod(Subscription.PAYMENT_METHOD_BOLETO);

        Subscription created = assinaturas.subscriptions().create(toBeCreated);

        assertEquals("Assinatura criada com sucesso", created.getMessage());

        assertEquals(created.getAmount(), 100);
        assertEquals(created.getPlan().getName(), "Plano de Teste Atualizado");
        assertEquals(created.getPlan().getCode(), "plan001");
        assertEquals(created.getStatus(), SubscriptionStatus.ACTIVE);
        assertEquals(created.getInvoice().getAmount(), 1100);
        assertEquals(created.getInvoice().getId(), 12872);
        assertEquals(created.getInvoice().getStatus().getDescription(), "Aguardando confirma��o");
        assertEquals(created.getInvoice().getStatus().getCode(), 6);
        assertEquals(1, created.getNextInvoiceDate().getDay());
        assertEquals(Month.MAY, created.getNextInvoiceDate().getMonth());
        assertEquals(2014, created.getNextInvoiceDate().getYear());
        assertEquals(created.getCode(), "subscription_with_new_customer_00001");
        assertEquals(created.getCustomer().getEmail(), "teste@teste.com");
        assertEquals(created.getCustomer().getCode(), "customer_created_with_subscription_0001");
        assertEquals(created.getCustomer().getFullname(), "Danillo Souza");

        assertEquals(created.getPaymentMethod(), Subscription.PAYMENT_METHOD_BOLETO);
    }
}
