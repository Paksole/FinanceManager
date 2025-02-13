import java.io.Serializable;
import java.time.LocalDateTime;

public class Transaction implements Serializable {
    private static final long serialVersionUID = 1L;
    private double amount;
    private String category;
    private TransactionType type;
    private LocalDateTime dateTime;
    private String description;

    public Transaction(double amount, String category, TransactionType type, String description) {
        this.amount = amount;
        this.category = category;
        this.type = type;
        this.dateTime = LocalDateTime.now();
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public TransactionType getType() {
        return type;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "amount=" + amount +
                ", category='" + category + '\'' +
                ", type=" + type +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                '}';
    }
}
