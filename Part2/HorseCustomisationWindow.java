import javax.swing.*;
import java.awt.*;
import java.util.List;

public class HorseCustomisationWindow extends JFrame {
    private final List<Horse> horses;
    private final DefaultListModel<String> horseListModel;
    private JList<String> horseList;
    private JTextField confidenceField;
    private JLabel imagePreview;

    public HorseCustomisationWindow(List<Horse> horses) {
        this.horses = horses;

        setTitle("Horse Customisation");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Horse list panel
        horseListModel = new DefaultListModel<>();
        for (Horse horse : horses) {
            horseListModel.addElement(horse.getName());
        }
        horseList = new JList<>(horseListModel);
        horseList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        horseList.addListSelectionListener(e -> updateHorseDetails());

        JScrollPane horseListScrollPane = new JScrollPane(horseList);
        horseListScrollPane.setBorder(BorderFactory.createTitledBorder("Horses"));
        add(horseListScrollPane, BorderLayout.WEST);

        // Customisation panel
        JPanel customisationPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        customisationPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Confidence level
        customisationPanel.add(new JLabel("Confidence:"));
        confidenceField = new JTextField();
        customisationPanel.add(confidenceField);

        // Image preview
        customisationPanel.add(new JLabel("Image:"));
        imagePreview = new JLabel();
        imagePreview.setHorizontalAlignment(SwingConstants.CENTER);
        imagePreview.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        customisationPanel.add(imagePreview);

        // Change image button
        JButton changeImageButton = new JButton("Change Image");
        changeImageButton.addActionListener(e -> changeHorseImage());
        customisationPanel.add(changeImageButton);

        add(customisationPanel, BorderLayout.CENTER);

        // Save button
        JButton saveButton = new JButton("Save Changes");
        saveButton.addActionListener(e -> saveChanges());
        add(saveButton, BorderLayout.SOUTH);
    }

    private void updateHorseDetails() {
        int selectedIndex = horseList.getSelectedIndex();
        if (selectedIndex >= 0) {
            Horse selectedHorse = horses.get(selectedIndex);
            confidenceField.setText(String.valueOf(selectedHorse.getConfidence()));
            imagePreview.setIcon(selectedHorse.getImage());
        }
    }

    private void changeHorseImage() {
        int selectedIndex = horseList.getSelectedIndex();
        if (selectedIndex >= 0) {
            Horse selectedHorse = horses.get(selectedIndex);

            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                ImageIcon newImage = new ImageIcon(fileChooser.getSelectedFile().getAbsolutePath());
                selectedHorse.setImage(newImage);
                imagePreview.setIcon(newImage);
            }
        }
    }

    private void saveChanges() {
        int selectedIndex = horseList.getSelectedIndex();
        if (selectedIndex >= 0) {
            try {
                double newConfidence = Double.parseDouble(confidenceField.getText());
                if (newConfidence < 0 || newConfidence > 1) {
                    throw new IllegalArgumentException("Confidence must be between 0 and 1.");
                }
                horses.get(selectedIndex).setConfidence(newConfidence);
                JOptionPane.showMessageDialog(this, "Changes saved successfully!");
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid confidence value!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}