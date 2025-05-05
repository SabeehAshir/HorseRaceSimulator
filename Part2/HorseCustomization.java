import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HorseCustomization extends JDialog {
    private Map<Integer, Horse> horses; // Map of horses to customize
    private JTabbedPane tabbedPane;
    private List<String> imagePaths; // List of image paths

    public HorseCustomization(JFrame parent, Map<Integer, Horse> horses) {
        super(parent, "Customize Horses", true);
        this.horses = horses;

        // Load image paths
        imagePaths = loadImagePaths();

        setLayout(new BorderLayout());
        setSize(600, 600);
        setLocationRelativeTo(parent);

        // Create a tabbed pane for each horse
        tabbedPane = new JTabbedPane();
        for (Map.Entry<Integer, Horse> entry : horses.entrySet()) {
            int lane = entry.getKey();
            Horse horse = entry.getValue();
            if (horse != null) { // Skip null horses
                tabbedPane.addTab("Lane " + lane, createHorseCustomizationPanel(horse));
            }
        }

        // Add the tabbed pane to the dialog
        add(tabbedPane, BorderLayout.CENTER);

        // Save Button
        JButton saveButton = new JButton("Save All");
        saveButton.addActionListener(e -> saveAllCustomizations());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createHorseCustomizationPanel(Horse horse) {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2, 10, 10));

        // Breed Selector
        panel.add(new JLabel("Breed:"));
        JComboBox<String> breedSelector = new JComboBox<>(new String[] { "Thoroughbred", "Arabian", "Quarter Horse" });
        breedSelector.setSelectedItem(horse.getBreed());
        breedSelector.addActionListener(e -> horse.setBreed((String) breedSelector.getSelectedItem()));
        panel.add(breedSelector);

        // Coat Color Selector
        panel.add(new JLabel("Coat Color:"));
        JComboBox<String> coatColorSelector = new JComboBox<>(new String[] { "Brown", "Black", "Grey", "White" });
        coatColorSelector.setSelectedItem(horse.getCoatColor());
        coatColorSelector.addActionListener(e -> horse.setCoatColor((String) coatColorSelector.getSelectedItem()));
        panel.add(coatColorSelector);

        // Symbol Image Selector
        panel.add(new JLabel("Symbol Image:"));
        JPanel imageSelectionPanel = createImageSelectionPanel(horse);
        panel.add(imageSelectionPanel);

        // Saddle Selector
        panel.add(new JLabel("Saddle:"));
        JComboBox<String> saddleSelector = new JComboBox<>(new String[] { "Standard", "Lightweight", "Decorative" });
        saddleSelector.setSelectedItem(horse.getSaddle());
        saddleSelector.addActionListener(e -> horse.setSaddle((String) saddleSelector.getSelectedItem()));
        panel.add(saddleSelector);

        // Horseshoe Selector
        panel.add(new JLabel("Horseshoes:"));
        JComboBox<String> horseshoeSelector = new JComboBox<>(new String[] { "Regular", "Lightweight", "Grip" });
        horseshoeSelector.setSelectedItem(horse.getHorseshoes());
        horseshoeSelector.addActionListener(e -> horse.setHorseshoes((String) horseshoeSelector.getSelectedItem()));
        panel.add(horseshoeSelector);

        return panel;
    }

    private JPanel createImageSelectionPanel(Horse horse) {
        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new GridLayout(0, 3, 10, 10)); // Display images in a grid

        ButtonGroup buttonGroup = new ButtonGroup(); // Group buttons for exclusive selection
        for (String imagePath : imagePaths) {
            ImageIcon icon = new ImageIcon(imagePath);
            Image scaledImage = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Scale image
            JRadioButton imageButton = new JRadioButton(new ImageIcon(scaledImage));
            imageButton.addActionListener(e -> horse.setImage(new ImageIcon(imagePath))); // Set the selected image for the horse
            buttonGroup.add(imageButton);
            imagePanel.add(imageButton);
        }

        return imagePanel;
    }

    private List<String> loadImagePaths() {
        List<String> paths = new ArrayList<>();
        File folder = new File("Part2"); // Path to the folder containing images
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".png") || name.toLowerCase().endsWith(".jpg"));

        if (files != null) {
            for (File file : files) {
                paths.add(file.getAbsolutePath());
            }
        }
        return paths;
    }

    private void saveAllCustomizations() {
        JOptionPane.showMessageDialog(this, "All customizations saved!", "Success", JOptionPane.INFORMATION_MESSAGE);
        dispose();
    }
}