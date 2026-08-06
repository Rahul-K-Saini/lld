package behavioural;

public class Strategy {
  public static void main(String[] args) {
    PaymentContext paymentContext = new PaymentContext(new UPIPayment("user@upi"));
    paymentContext.executePayment(1000);
  }
}

interface PaymentStrategy {
  void pay(int amount);
}

class UPIPayment implements PaymentStrategy {
  private final String upiId;

  public UPIPayment(String upiId) {
    this.upiId = upiId;
  }

  @Override
  public void pay(int amount) {
    System.out.println("Paid " + amount + " using UPI with ID: " + upiId);
  }
}

class CreditCardPayment implements PaymentStrategy {
  private final String cardNumber;

    public CreditCardPayment(String cardNumber, String cardHolderName, String cvv) {
    this.cardNumber = cardNumber;
    }

  @Override
  public void pay(int amount) {
    System.out.println("Paid " + amount + " using Credit Card: " + cardNumber);
  }
}

class PaymentContext {
  private final PaymentStrategy paymentStrategy;

  public PaymentContext(PaymentStrategy paymentStrategy) {
    this.paymentStrategy = paymentStrategy;
  }

  public void executePayment(int amount) {
    paymentStrategy.pay(amount);
  }
}
