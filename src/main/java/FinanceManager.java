import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FinanceManager {
    private Map<String, User> users;
    private User currentUser;
    private Scanner scanner;

    public FinanceManager() {
        this.users = FileManager.loadUsers();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            if (currentUser == null) {
                showAuthMenu();
            } else {
                showMainMenu();
            }
        }
    }

    private void showAuthMenu() {
        while (true) {
            System.out.println("\n=== Меню авторизации ===");
            System.out.println("1. Вход");
            System.out.println("2. Регистрация");
            System.out.println("3. Выход");
            System.out.print("Выберите действие: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // очистка буфера

                if (choice >= 1 && choice <= 3) {
                    switch (choice) {
                        case 1:
                            login();
                            return;
                        case 2:
                            register();
                            return;
                        case 3:
                            FileManager.saveUsers(users);
                            System.out.println("До свидания!");
                            System.exit(0);
                    }
                } else {
                    System.out.println("Выберите число от 1 до 3!");
                }
            } catch (Exception e) {
                System.out.println("Пожалуйста, введите число!");
                scanner.nextLine(); // очистка буфера после ошибочного ввода
            }
        }
    }

    private void showMainMenu() {
        while (true) {
            System.out.println("\n=== Главное меню ===");
            System.out.println("1. Добавить доход");
            System.out.println("2. Добавить расход");
            System.out.println("3. Установить бюджет");
            System.out.println("4. Показать статистику");
            System.out.println("5. Перевести деньги");
            System.out.println("6. Выйти из аккаунта");
            System.out.print("Выберите действие: ");

            try {
                int choice = scanner.nextInt();
                scanner.nextLine(); // очистка буфера

                if (choice >= 1 && choice <= 6) {
                    switch (choice) {
                        case 1:
                            addIncome();
                            return;
                        case 2:
                            addExpense();
                            return;
                        case 3:
                            setBudget();
                            return;
                        case 4:
                            showStatistics();
                            return;
                        case 5:
                            transferMoney();
                            return;
                        case 6:
                            logout();
                            return;
                    }
                } else {
                    System.out.println("Выберите число от 1 до 6!");
                }
            } catch (Exception e) {
                System.out.println("Пожалуйста, введите число!");
                scanner.nextLine(); // очистка буфера после ошибочного ввода
            }
        }
    }


    private void showUnreadTransfers() {
        List<Transaction> unreadTransfers = currentUser.getUnreadTransfers();
        if (!unreadTransfers.isEmpty()) {
            System.out.println("\nДля вас " + unreadTransfers.size() + " новых переводов:");
            for (int i = 0; i < unreadTransfers.size(); i++) {
                Transaction transfer = unreadTransfers.get(i);
                System.out.println((i + 1) + ". От: " + transfer.getDescription().replace("Получен перевод от ", ""));
                System.out.println("   Сумма: " + transfer.getAmount());
                System.out.println("   Дата и время: " + transfer.getDateTime());
                System.out.println();
            }
            currentUser.clearUnreadTransfers();
            FileManager.saveUsers(users);
        }
    }

    private void login() {
        System.out.print("Введите логин: ");
        String login = scanner.nextLine();
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        User user = users.get(login);
        if (user != null && user.getPassword().equals(password)) {
            if (user.getUnreadTransfers() == null) {
                user.initializeUnreadTransfers();
            }
            currentUser = user;
            System.out.println("Добро пожаловать, " + login + "!");
            showUnreadTransfers();
        } else {
            System.out.println("Неверный логин или пароль!");
        }
    }

    private void register() {
        System.out.print("Введите логин: ");
        String login = scanner.nextLine();

        if (users.containsKey(login)) {
            System.out.println("Пользователь с таким логином уже существует!");
            return;
        }

        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();

        User newUser = new User(login, password);
        newUser.initializeUnreadTransfers(); // добавляем инициализацию
        users.put(login, newUser);
        System.out.println("Регистрация успешна!");
    }


    private void logout() {
        FileManager.saveUsers(users);
        currentUser = null;
        System.out.println("Вы вышли из аккаунта");
    }

    private void addIncome() {
        System.out.print("Введите категорию дохода: ");
        String category = scanner.nextLine().trim();

        if (category.isEmpty()) {
            System.out.println("Категория не может быть пустой!");
            return;
        }

        System.out.print("Введите сумму: ");
        double amount = readAmount();

        System.out.print("Введите описание: ");
        String description = scanner.nextLine().trim();

        if (description.isEmpty()) {
            System.out.println("Описание не может быть пустым!");
            return;
        }

        Transaction transaction = new Transaction(amount, category, TransactionType.INCOME, description);
        currentUser.getWallet().addTransaction(transaction);

        FileManager.saveUsers(users);
        System.out.println("Доход успешно добавлен!");
    }

    private double readAmount() {
        while (true) {
            try {
                double amount = scanner.nextDouble();
                if (amount <= 0) {
                    System.out.print("Сумма должна быть больше 0. Повторите ввод: ");
                    scanner.nextLine();
                    continue;
                }
                scanner.nextLine(); // очистка буфера
                return amount;
            } catch (Exception e) {
                System.out.print("Некорректная сумма. Повторите ввод: ");
                scanner.nextLine();
            }
        }
    }

    private void addExpense() {
        System.out.print("Введите категорию расхода: ");
        String category = scanner.nextLine().trim();

        if (category.isEmpty()) {
            System.out.println("Категория не может быть пустой!");
            return;
        }

        System.out.print("Введите сумму: ");
        double amount = readAmount();

        System.out.print("Введите описание: ");
        String description = scanner.nextLine().trim();

        if (description.isEmpty()) {
            System.out.println("Описание не может быть пустым!");
            return;
        }

        Transaction transaction = new Transaction(amount, category, TransactionType.EXPENSE, description);
        currentUser.getWallet().addTransaction(transaction);

        Budget budget = currentUser.getWallet().getBudgets().get(category);
        if (budget != null) {
            budget.addExpense(amount);
            if (budget.getRemainingBudget() < 0) {
                System.out.println("Внимание! Превышен бюджет по категории " + category);
            }
        }

        FileManager.saveUsers(users);
        System.out.println("Расход успешно добавлен!");
    }

    private void setBudget() {
        System.out.print("Введите категорию: ");
        String category = scanner.nextLine();
        System.out.print("Введите лимит: ");
        double limit = scanner.nextDouble();
        scanner.nextLine(); // очистка буфера

        Budget budget = new Budget(category, limit);
        currentUser.getWallet().getBudgets().put(category, budget);
        System.out.println("Бюджет установлен!");
    }

    private void showStatistics() {
        Wallet wallet = currentUser.getWallet();

        System.out.println("\n=== Статистика ===");
        System.out.println("Текущий баланс: " + wallet.getBalance());

        double totalIncome = 0;
        double totalExpense = 0;
        Map<String, Double> incomeByCategory = new HashMap<>();
        Map<String, Double> expenseByCategory = new HashMap<>();

        for (Transaction t : wallet.getTransactions()) {
            if (t.getType() == TransactionType.INCOME) {
                totalIncome += t.getAmount();
                incomeByCategory.merge(t.getCategory(), t.getAmount(), Double::sum);
            } else {
                totalExpense += t.getAmount();
                expenseByCategory.merge(t.getCategory(), t.getAmount(), Double::sum);
            }
        }

        System.out.println("\nОбщий доход: " + totalIncome);
        System.out.println("Доходы по категориям:");
        incomeByCategory.forEach((category, amount) ->
                System.out.println(category + ": " + amount)
        );

        System.out.println("\nОбщие расходы: " + totalExpense);
        System.out.println("Расходы по категориям:");
        expenseByCategory.forEach((category, amount) ->
                System.out.println(category + ": " + amount)
        );

        System.out.println("\nБюджеты по категориям:");
        wallet.getBudgets().forEach((category, budget) ->
                System.out.println(category + ": лимит " + budget.getLimit() +
                        ", потрачено " + budget.getSpent() +
                        ", осталось " + budget.getRemainingBudget())
        );
    }

    private void transferMoney() {
        System.out.print("Введите логин получателя: ");
        String recipientLogin = scanner.nextLine();

        if (!users.containsKey(recipientLogin)) {
            System.out.println("Пользователь не найден!");
            return;
        }

        if (recipientLogin.equals(currentUser.getLogin())) {
            System.out.println("Нельзя перевести деньги самому себе!");
            return;
        }

        System.out.print("Введите сумму перевода: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // очистка буфера

        System.out.print("Введите описание перевода: ");
        String description = scanner.nextLine();

        if (currentUser.getWallet().transfer(amount, description)) {
            User recipient = users.get(recipientLogin);
            if (recipient.getUnreadTransfers() == null) {
                recipient.initializeUnreadTransfers();
            }
            Transaction receivedTransfer = new Transaction(
                    amount,
                    "Перевод",
                    TransactionType.INCOME,
                    "Получен перевод от " + currentUser.getLogin()
            );
            recipient.getWallet().receive(amount, currentUser.getLogin());
            recipient.addUnreadTransfer(receivedTransfer);
            FileManager.saveUsers(users);
            System.out.println("Перевод выполнен успешно!");
        } else {
            System.out.println("Ошибка перевода! Проверьте баланс и сумму перевода.");
        }
    }

}
