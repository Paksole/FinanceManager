import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Wallet implements Serializable {
    private static final long serialVersionUID = 1L;
    private double balance;
    private List<Transaction> transactions;
    private Map<String, Category> categories;
    private Map<String, Budget> budgets;

    public Wallet() {
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
        this.categories = new HashMap<>();
        this.budgets = new HashMap<>();
    }

    public double getBalance() {
        return balance;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        if (transaction.getType() == TransactionType.INCOME) {
            balance += transaction.getAmount();
        } else {
            balance -= transaction.getAmount();
        }
    }



    public List<Transaction> getTransactions() {
        return transactions;
    }

    public Map<String, Category> getCategories() {
        return categories;
    }

    public Map<String, Budget> getBudgets() {
        return budgets;
    }

    public boolean transfer(double amount, String description) {
        if (amount <= 0 || amount > balance) {
            return false;
        }
        balance -= amount;
        transactions.add(new Transaction(amount, "Перевод", TransactionType.EXPENSE, description));
        return true;
    }

    public void receive(double amount, String fromUser) {
        balance += amount;
        transactions.add(new Transaction(amount, "Перевод", TransactionType.INCOME, "Получен перевод от " + fromUser));
    }



}
