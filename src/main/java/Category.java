import java.io.Serializable;

public class Category implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private TransactionType type;

    public Category(String name, TransactionType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public TransactionType getType() {
        return type;
    }
}
