package w4;

import java.util.ArrayList;

public class Account {
    private double balance;
    private ArrayList<Transaction> transitionList = new ArrayList<>();

    /**
     * new Transaction - deposit.
     */
    private void deposit() {
        transitionList.add(new Transaction(Transaction.DEPOSIT, 0, balance));
    }

    /**
     * check invalid and deposit.
     *
     * @param amount .
     */
    private void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("So tien ban nap vao khong hop le!");
            return;
        }
        deposit();
        transitionList.get(transitionList.size() - 1).setAmount(amount);
        balance += amount;
        transitionList.get(transitionList.size() - 1).setBalance(balance);
    }

    /**
     * new Transaction - withdraw.
     */
    private void withdraw() {
        transitionList.add(new Transaction(Transaction.WITHDRAW, 0, balance));
    }

    /**
     * check invalid and withdraw.
     *
     * @param amount .
     */
    private void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("So tien ban rut ra khong hop le!");
        } else if (amount > balance) {
            System.out.println("So tien ban rut vuot qua so du!");
        } else {
            withdraw();
            transitionList.get(transitionList.size() - 1).setAmount(amount);
            balance -= amount;
            transitionList.get(transitionList.size() - 1).setBalance(balance);
        }
    }

    /**
     * add a transaction -> transaction list of an acc.
     */
    public void addTransaction(double amount, String operation) {
        if (operation.equals(Transaction.DEPOSIT)) {
            deposit(amount);
            return;
        }
        if (operation.equals(Transaction.WITHDRAW)) {
            withdraw(amount);
            return;
        }
        System.out.println("Yeu cau khong hop le!");
    }

    /**
     * print transaction list of the account.
     */
    public void printTransaction() {
        for (int i = 0; i < transitionList.size(); i++) {
            System.out.print("Giao dich " + (i + 1) + ": ");
            System.out.print(transitionList.get(i).getOperation());
            System.out.printf("$%.2f. ", round2(transitionList.get(i).getAmount()));
            System.out.printf("So du luc nay: $%.2f.", round2(transitionList.get(i).getBalance()));
            System.out.println();
        }
    }

    /**
     * Math.round(2).
     *
     * @param number .
     * @return .
     */
    private static double round2(double number) {
        return Math.round(number * 100.00) / 100.00;
    }

    /*
    Account acc= new Account();
    acc.addTransaction(2000.255,"deposit");
    acc.addTransaction(1000,"withdraw");
    acc.printTransaction();

    Kết quả trả ra:
    Giao dich 1: Nap tien $2000.26. So du luc nay: $2000.26.
    Giao dich 2: Rut tien $1000.00. So du luc nay: $1000.26.
    */

    /**
     * test.
     *
     * @param args .
     */
    public static void main(String[] args) {
        Account acc = new Account();
        acc.addTransaction(2000.255, "deposit");
        acc.addTransaction(1000, "withdraw");
        acc.printTransaction();
        System.out.printf("%.2f", round2(1000.00));
    }

}
