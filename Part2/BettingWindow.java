import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class BettingWindow extends JFrame {
    private final PlayerAccount playerAccount;
    private final Map<Integer,Horse> horses;
    private JComboBox<String> horseSelector;
    private JTextField betAmountField;

    public BettingWindow(PlayerAccount playerAccount, Map<Integer,Horse> horses) {
        this.playerAccount = playerAccount;
        this.horses = horses;

        setTitle("Betting Window");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Create betting panel
        JPanel bettingPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        bettingPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Horse selector
        bettingPanel.add(new JLabel("Select Horse:"));
        horseSelector = new JComboBox<>();
        for (Horse horse : horses.values()) {
            horseSelector.addItem(horse.getName());
        }
        bettingPanel.add(horseSelector);

        // Bet amount
        bettingPanel.add(new JLabel("Bet Amount:"));
        betAmountField = new JTextField();
        bettingPanel.add(betAmountField);

        // Add betting panel to frame
        add(bettingPanel, BorderLayout.CENTER);

        // Add buttons
        JButton placeBetButton = new JButton("Place Bet");
        placeBetButton.addActionListener(e -> placeBet());
        add(placeBetButton, BorderLayout.SOUTH);
    }

    private void placeBet() {
        String selectedHorse = (String) horseSelector.getSelectedItem();
        String betAmountText = betAmountField.getText();

        try {
            double betAmount = Double.parseDouble(betAmountText);
            if (betAmount > playerAccount.getBalance()) {
                JOptionPane.showMessageDialog(this, "Insufficient funds!", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                playerAccount.withdraw(betAmount);
                JOptionPane.showMessageDialog(this, "Bet placed on " + selectedHorse + " for $" + betAmount);
                dispose();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid bet amount!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}