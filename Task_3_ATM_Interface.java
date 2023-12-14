import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        this.balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public boolean deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            showMessage("Deposit of $" + amount + " successful. Current balance: $" + balance);
            return true;
        } else {
            showMessage("Invalid deposit amount.");
            return false;
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            showMessage("Withdrawal of $" + amount + " successful. Current balance: $" + balance);
            return true;
        } else {
            showMessage("Invalid withdrawal amount or insufficient balance.");
            return false;
        }
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.INFORMATION_MESSAGE);
    }
}

class ATMGUI extends JFrame {
    private BankAccount bankAccount;
    private JTextField amountField;

    public ATMGUI(BankAccount bankAccount) {
        this.bankAccount = bankAccount;

        setTitle("ATM Interface");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        JLabel balanceLabel = new JLabel("Current Balance: $" + bankAccount.getBalance());
        JLabel amountLabel = new JLabel("Enter Amount: $");
        amountField = new JTextField();

        JButton checkBalanceButton = new JButton("Check Balance");
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton exitButton = new JButton("Exit");

        panel.add(balanceLabel);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(amountLabel);
        panel.add(amountField);
        panel.add(checkBalanceButton);
        panel.add(depositButton);
        panel.add(withdrawButton);
        panel.add(exitButton);

        add(panel, BorderLayout.CENTER);

        checkBalanceButton.addActionListener(e -> updateBalance());
        depositButton.addActionListener(e -> performTransaction(true));
        withdrawButton.addActionListener(e -> performTransaction(false));
        exitButton.addActionListener(e -> System.exit(0));
    }

    private void updateBalance() {
        JOptionPane.showMessageDialog(null, "Current Balance: $" + bankAccount.getBalance(), "Balance", JOptionPane.INFORMATION_MESSAGE);
    }

    private void performTransaction(boolean isDeposit) {
        try {
            double amount = Double.parseDouble(amountField.getText());

            if (isDeposit) {
                bankAccount.deposit(amount);
            } else {
                bankAccount.withdraw(amount);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Invalid amount. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

public class Task_3_ATM_Interface {
    public static void main(String[] args) {
        double initialBalance = Double.parseDouble(JOptionPane.showInputDialog("Enter initial account balance: $"));
        BankAccount bankAccount = new BankAccount(initialBalance);

        SwingUtilities.invokeLater(() -> {
            ATMGUI atmGUI = new ATMGUI(bankAccount);
            atmGUI.setSize(400, 200);
            atmGUI.setLocationRelativeTo(null); // Center the frame on the screen
            atmGUI.setVisible(true);
        });
    }
}
