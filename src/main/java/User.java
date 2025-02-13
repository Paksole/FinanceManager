import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    private String login;
    private String password;
    private Wallet wallet;
    private List<Transaction> unreadTransfers;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
        this.wallet = new Wallet();
        this.unreadTransfers = new ArrayList<Transaction>();
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void addUnreadTransfer(Transaction transfer) {
        unreadTransfers.add(transfer);
    }

    public List<Transaction> getUnreadTransfers() {
        return unreadTransfers;
    }

    public void clearUnreadTransfers() {
        unreadTransfers.clear();
    }
    public void initializeUnreadTransfers() {
        this.unreadTransfers = new ArrayList<Transaction>();
    }
}
