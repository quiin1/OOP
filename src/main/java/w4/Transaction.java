package w4;

public class Transaction {
    private double balance; // account balance
    private double amount; // transaction amount
    private String operation; // transaction type name
    public static final String DEPOSIT = "deposit";
    public static final String WITHDRAW = "withdraw";

    /**
     * Constructor.
     *
     * @param balance   .
     * @param amount    .
     * @param operation .
     */
    public Transaction(String operation, double amount, double balance) {
        this.operation = operation;
        this.amount = amount;
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * getOperation (deposit / withdraw).
     *
     * @return Nap tien / Rut tien.
     */
    public String getOperation() {
        if (operation.equals(DEPOSIT)) {
            return "Nap tien ";
        }
        if (operation.equals(WITHDRAW)) {
            return "Rut tien ";
        }
        return null;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
