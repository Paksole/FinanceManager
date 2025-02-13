import java.io.Serializable;

public class Budget implements Serializable {
    private static final long serialVersionUID = 1L;
    private String category;
    private double limit;
    private double spent;

    public Budget(String category, double limit) {
        this.category = category;
        this.limit = limit;
        this.spent = 0.0;
    }

    public String getCategory() {
        return category;
    }

    public double getLimit() {
        return limit;
    }

    public double getSpent() {
        return spent;
    }

    public void addExpense(double amount) {
        this.spent += amount;
    }

    public double getRemainingBudget() {
        return limit - spent;
    }
}
