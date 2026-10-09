public abstract class PaymentMethod {
    private static int counter = 1000;
    private final String transactionId;

    public PaymentMethod() {
        counter++;
        this.transactionId = "TXN-" + counter;
    }

    public String getTransactionId() {
        return transactionId;
    }

    // Abstract method defining child behavior
    public abstract String processPayment(double amount);

    // Overloaded method (Compile-Time Polymorphism) defined on parent
    public String processPayment(double amount, String note) {
        return processPayment(amount) + " (" + note + ")";
    }

    public static void printConfirmation(PaymentMethod payment, double amount) {
        System.out.println(payment.processPayment(amount));
    }

    public static void main(String[] args) {
        System.out.println("=== Problem 1: Online Checkout System ===");

        // Demonstrating that PaymentMethod cannot be instantiated directly:
        // PaymentMethod p = new PaymentMethod(); // COMPILE ERROR!

        CreditCardPayment cc = new CreditCardPayment("4471");
        System.out.println(cc.processPayment(250.0));

        CashPayment cash = new CashPayment();
        System.out.println(cash.processPayment(40.0));

        // Testing overloaded method
        System.out.println(cc.processPayment(250.0, "Birthday gift"));

        // UPCASTING: Storing a CreditCardPayment reference in a PaymentMethod-typed variable
        PaymentMethod ref = cc; 
        printConfirmation(ref, 250.0);
    }
}

class CreditCardPayment extends PaymentMethod {
    private final String cardNumberLastFour;

    public CreditCardPayment(String cardNumberLastFour) {
        super();
        this.cardNumberLastFour = cardNumberLastFour;
    }

    @Override
    public String processPayment(double amount) {
        return "Charged $" + amount + " to card ending " + cardNumberLastFour + " - Txn " + getTransactionId();
    }
}

class CashPayment extends PaymentMethod {
    public CashPayment() {
        super();
    }

    @Override
    public String processPayment(double amount) {
        return "Received $" + amount + " in cash - Txn " + getTransactionId();
    }
}